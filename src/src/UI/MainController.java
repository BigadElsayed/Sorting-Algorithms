package UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Tab visualizationTab;
    @FXML
    private Tab comparisonTab;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadVisualizationTab();
        loadComparisonTab();
    }

    private void loadVisualizationTab() {
        try {
            Parent content = FXMLLoader.load(getClass().getResource("/UI/visualization/VisualizationTab.fxml"));
            visualizationTab.setContent(content);
        }
        catch (IOException e) {
            System.err.println("Unable to load visualization tab");
        }
    }
    private void loadComparisonTab() {
        try{
            Parent content = FXMLLoader.load(getClass().getResource("/UI/comparison/ComparisonTab.fxml"));
            comparisonTab.setContent(content);
        }
        catch (IOException e) {
            System.err.println("Unable to load comparison tab");
        }
    }
}