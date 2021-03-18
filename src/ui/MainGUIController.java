package ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


import java.net.URL;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    /*Splash screen*/
    @FXML
    private BorderPane splashPane;

    public static Label label;

    @FXML
    private Label progress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label = progress;
    }

}
