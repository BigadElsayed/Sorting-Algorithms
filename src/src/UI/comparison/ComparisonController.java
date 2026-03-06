package UI.comparison;

import algorithms.*;
import core.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComparisonController implements Initializable {
    @FXML
    private CheckBox bubbleCheck;
    @FXML
    private CheckBox selectionCheck;
    @FXML
    private CheckBox mergeCheck;
    @FXML
    private CheckBox heapCheck;
    @FXML
    private CheckBox quickCheck;
    @FXML
    private ComboBox<String> arrayTypeCombo;
    @FXML
    private TextField arraySizeField;
    @FXML
    private TextField numRunsField;
    @FXML
    private Button addFileButton;
    @FXML
    private ListView<String> fileListView;
    @FXML
    private Button runButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button exportButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label rowCountLabel;

    // we use generic types to avoid casting later
    @FXML
    private TableView<RunStats> resultsTable;
    @FXML
    private TableColumn<RunStats, String> algoCol;
    @FXML
    private TableColumn<RunStats, Integer> sizeCol;
    @FXML
    private TableColumn<RunStats, String> sourceCol;
    @FXML
    private TableColumn<RunStats, Integer> runsCol;
    @FXML
    private TableColumn<RunStats, String> avgCol;
    @FXML
    private TableColumn<RunStats, String> minCol;
    @FXML
    private TableColumn<RunStats, String> maxCol;
    @FXML
    private TableColumn<RunStats, Long> compCol;
    @FXML
    private TableColumn<RunStats, Long> interCol;
    @FXML
    private CheckBox insertionCheck;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        arrayTypeCombo.getItems().addAll(ArrayGenerator.RANDOM, ArrayGenerator.INVERSE_SORTED, ArrayGenerator.SORTED);
        arrayTypeCombo.setValue(ArrayGenerator.RANDOM);

        arraySizeField.setText("1000");
        numRunsField.setText("5");

        setupTableColumns();
    }

    private void setupTableColumns() {
        algoCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getAlgorithmName()
                )
        );

        sourceCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getArraySource()
                )
        );

        avgCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getAvgRunTimeFormatted()
                )
        );

        minCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getMinRunTimeFormatted()
                )
        );

        maxCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().getMaxRunTimeFormatted()
                )
        );
        sizeCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(
                        data.getValue().getArraySize()
                ).asObject()
        );
        runsCol.setCellValueFactory(
                data -> new SimpleIntegerProperty(
                        data.getValue().getNumberOfRuns()
                ).asObject()
        );
        compCol.setCellValueFactory(
                data -> new SimpleLongProperty(
                        data.getValue().getComparisons()
                ).asObject()
        );
        interCol.setCellValueFactory(
                data -> new SimpleLongProperty(
                        data.getValue().getInterchanges()
                ).asObject()
        );

    }

    private List<String> loadedFilePaths = new ArrayList<>();


    // BOKRA
    public void handleAddFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Input File");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        File file = fileChooser.showOpenDialog(
                addFileButton.getScene().getWindow()
        );

        if (file != null) {

            loadedFilePaths.add(file.getAbsolutePath());
            fileListView.getItems().add(file.getName());
        }
    }

    public void handleRun() {
        int size;
        try {
            size = Integer.parseInt(arraySizeField.getText());
            if (size < 1 || size > 10000) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.err.println("Array Size Must be between 1 and 10000");
            return;

        }
        int runs;
        try {
            runs = Integer.parseInt(numRunsField.getText());
            if (runs < 1) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.err.println("Number of Runs Must be positive");
            return;
        }

        List<SortAlgorithm> selectedAlgorithms = new ArrayList<>();

        if (bubbleCheck.isSelected()) {
            selectedAlgorithms.add(new BubbleSort());
        } if (selectionCheck.isSelected()) {
            selectedAlgorithms.add(new SelectionSort());
        } if (insertionCheck.isSelected()) {
            selectedAlgorithms.add(new InsertionSort());
        } if (mergeCheck.isSelected()) {
            selectedAlgorithms.add((new MergeSort()));
        } if (quickCheck.isSelected()) {
            selectedAlgorithms.add(new QuickSort());
        } if (heapCheck.isSelected()) {
            selectedAlgorithms.add(new HeapSort());
        }

        if (selectedAlgorithms.isEmpty()) {
            System.err.println("No Algorithms Selected");
            return;
        }

        runButton.setDisable(true);

        String arrType = arrayTypeCombo.getValue();

        // Run in background thread so UI doesnt freeze
        Task<List<RunStats>> task = new Task<>() {
            @Override
            protected List<RunStats> call() throws Exception {

                List<RunStats> runStats = new ArrayList<>();
                int[] array = ArrayGenerator.generateArray(arrType, size);

                for (SortAlgorithm algorithm : selectedAlgorithms) {
                    RunStats stats = ComparisonRunner.run(algorithm, array, arrType, runs);
                    runStats.add(stats);
                }

                // BOKRA
                for (String filePath : loadedFilePaths) {
                    int[] fileArray = FileLoader.loadFromFile(filePath);
                    String fileName = FileLoader.getFileName(filePath);

                    for (SortAlgorithm algo : selectedAlgorithms) {
                        RunStats stats = ComparisonRunner.run(
                                algo, fileArray, fileName, runs
                        );
                        runStats.add(stats);
                    }
                }

                return runStats;
            }
        };

        task.setOnSucceeded(event -> {
            List<RunStats> results = task.getValue();
            for (RunStats runStats : results) {
                resultsTable.getItems().add(runStats);
            }
            runButton.setDisable(false);
        });

        new Thread(task).start();
    }


    public void handleClear() {
        resultsTable.getItems().clear();
    }

    // BOKRA
    public void handleExport() throws FileNotFoundException {
        if (resultsTable.getItems().isEmpty()) {
            System.err.println("No Results Selected");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Results as CSV");
        fileChooser.setInitialFileName("comparison_results.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(
                exportButton.getScene().getWindow()
        );

        if (file != null) {
            try (PrintWriter out = new PrintWriter(file)) {
                out.println("Algorithm,Array Size,Array Type,Runs," +
                        "Avg Runtime,Min Runtime,Max Runtime," +
                        "Comparisons,Interchanges");
                for (RunStats r : resultsTable.getItems()) {
                    out.println(
                            r.getAlgorithmName() + "," +
                                    r.getArraySize() + "," +
                                    r.getArraySource() + "," +
                                    r.getNumberOfRuns() + "," +
                                    r.getAvgRunTimeFormatted() + "," +
                                    r.getMinRunTimeFormatted() + "," +
                                    r.getMaxRunTimeFormatted() + "," +
                                    r.getComparisons() + "," +
                                    r.getInterchanges()
                    );
                }
            } catch (IOException e) {
                System.err.println("Error Saving Results as CSV");
            }

        }

    }
}
