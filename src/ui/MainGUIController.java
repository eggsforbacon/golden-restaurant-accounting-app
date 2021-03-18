package ui;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGUIController {

    /*Splash screen*/
    @FXML
    private BorderPane splashPane;

    @FXML
    public void initialize() {
        Stage stage = (Stage) splashPane.getScene().getWindow();
        stage.close();
    }

}
