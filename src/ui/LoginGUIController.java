package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginGUIController implements Initializable {

    @FXML
    private TextField userNameTF;

    @FXML
    private PasswordField passwordPF;

    @FXML
    private Button loginBTN;

    @FXML
    private Button registerBTN;

    @FXML
    void loginPressed(ActionEvent event) {

    }

    @FXML
    void registerPressed(ActionEvent event) {

    }

    Restaurant GH;

    public LoginGUIController(Restaurant GH){
        this.GH = GH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
