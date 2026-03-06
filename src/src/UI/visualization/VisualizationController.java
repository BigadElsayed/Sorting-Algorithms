package UI.visualization;

import algorithms.*;
import core.ArrayGenerator;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class VisualizationController implements Initializable {
    @FXML
    private ComboBox<String> algorithmCombo;
    @FXML
    private ComboBox<String> arrayTypeCombo;
    @FXML
    private TextField arraySizeField;
    @FXML
    private Slider speedSlider;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseResumeButton;
    @FXML
    private Button stepButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Canvas sortCanvas;
    @FXML
    private Label comparisonsLabel;
    @FXML
    private Label interchangesLabel;

    private Timeline timeline;
    private List<int[]> snapShots;
    private List<int[]> highlightedIndices;
    private int currentFrameCounter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithmCombo.getItems().addAll("Selection Sort", "Bubble Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort");
        arrayTypeCombo.getItems().addAll(ArrayGenerator.RANDOM, ArrayGenerator.INVERSE_SORTED, ArrayGenerator.SORTED);
        algorithmCombo.setValue("Bubble Sort");
        arrayTypeCombo.setValue(ArrayGenerator.RANDOM);

        speedSlider.setMin(50);
        speedSlider.setMax(1000);
        speedSlider.setValue(300);

        resetButton.setDisable(true);
        stepButton.setDisable(true);
        pauseResumeButton.setDisable(true);
        statusLabel.setText("Press Start To Begin");
        arraySizeField.setText("50");
    }


    @FXML
    public void handleStart() {

        if (timeline != null) {
            timeline.stop();
        }

        int size;

        try {
            size = Integer.parseInt(arraySizeField.getText());
            if (size <= 0 || size > 100) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("Size must be between 1 and 100");
            return;
        }

        int[] arr = ArrayGenerator.generateArray(arrayTypeCombo.getValue(), size);
        SortAlgorithm algorithm = getSelectedAlgorithm();

        SortResult result = algorithm.sortWithSteps(arr);

        comparisonsLabel.setText("Comparisons: " + result.getComparisons());
        interchangesLabel.setText("Interchanges: " + result.getInterchanges());


        snapShots = algorithm.getSteps();
        highlightedIndices = algorithm.getHighlightedIndices();

        currentFrameCounter = 0;

        drawBars(snapShots.get(0), new int[]{-1, -1});

        buildTimeLine();
        timeline.play();

        startButton.setDisable(true);
        pauseResumeButton.setDisable(false);
        stepButton.setDisable(false);
        resetButton.setDisable(false);

        statusLabel.setText("Running ...");
    }


    private SortAlgorithm getSelectedAlgorithm() {
        switch (algorithmCombo.getValue()) {
            case "Selection Sort":
                return new SelectionSort();
            case "Bubble Sort":
                return new BubbleSort();
            case "Insertion Sort":
                return new InsertionSort();
            case "Quick Sort":
                return new QuickSort();
            case "Merge Sort":
                return new MergeSort();
            case "Heap Sort":
                return new HeapSort();
            default:
                return new BubbleSort();
        }
    }

    // BOKRA
    private void drawBars(int[] array, int[] highlight) {
        GraphicsContext gc = sortCanvas.getGraphicsContext2D();

        double canvasWidth  = sortCanvas.getWidth();
        double canvasHeight = sortCanvas.getHeight();

        //clear Old bars
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        double barWidth = canvasWidth / array.length;

        int max = 0;
        for (int val : array) {
            if (val > max) max = val;
        }

        for (int i = 0; i < array.length; i++) {

            double barHeight = (array[i] / (double) max) * (canvasHeight - 10);

            if (highlight[0] != -1 && i == highlight[0]) {
                gc.setFill(Color.RED);
            } else if (highlight[1] != -1 && i == highlight[1]) {
                gc.setFill(Color.ORANGE);
            } else {
                gc.setFill(Color.web("#8b0000"));
            }

            gc.fillRect(
                    i * barWidth,
                    canvasHeight - barHeight,
                    barWidth - 1,
                    barHeight
            );
        }
    }

    private void buildTimeLine() {
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(speedSlider.getValue()),

                event -> {
                    if (currentFrameCounter >= snapShots.size()) {
                        timeline.stop();
                        statusLabel.setText("Done");
                        startButton.setDisable(false);
                        return;
                    }

                    drawBars(snapShots.get(currentFrameCounter), highlightedIndices.get(currentFrameCounter));

                    currentFrameCounter++;

                    statusLabel.setText("Step: " + currentFrameCounter + " / " + snapShots.size());

                }
        );

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        // INDEFINITE = keep ticking until we call stop()
    }

    @FXML
    public void handleStep() {
        if(timeline == null || snapShots == null) {}
        if(timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
        }
        if(currentFrameCounter < snapShots.size()) {
            drawBars(snapShots.get(currentFrameCounter), highlightedIndices.get(currentFrameCounter));
            currentFrameCounter++;
            statusLabel.setText("Step: " + currentFrameCounter + " / " + snapShots.size());
            if(currentFrameCounter >= snapShots.size()) {
                timeline.stop();
                statusLabel.setText("Done");
                startButton.setDisable(false);
            }
        }
    }

    @FXML
    public void handleReset() {
        if (timeline != null) {
            timeline.stop();
        }

        currentFrameCounter = 0;
        if (snapShots != null && !snapShots.isEmpty()) {
            drawBars(snapShots.get(0), new int[]{-1, -1});
        }

        startButton.setDisable(false);
        pauseResumeButton.setDisable(true);
        pauseResumeButton.setText("Pause");
        stepButton.setDisable(true);
        resetButton.setDisable(true);

        statusLabel.setText("Reset. Press Start again.");
    }

    @FXML
    public void handlePauseResume() {
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.pause();
            statusLabel.setText("Paused");
            pauseResumeButton.setText("Resume");
        }
        else if (timeline.getStatus() == Animation.Status.PAUSED) {
            timeline.play();
            statusLabel.setText("Running ...");
            pauseResumeButton.setText("Pause");
        }
    }

}
