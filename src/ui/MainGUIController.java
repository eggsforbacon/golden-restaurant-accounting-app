package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    /*Splash screen*/
    @FXML
    private BorderPane splashPane;

    public static Label label;

    @FXML
    private Label progress;

    /*Main pane*/
    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView randomIMV;

    @FXML
    private VBox sidebarVBOX;

    @FXML
    private Label spacer1;

    @FXML
    private Label space2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        label = progress;
        spacer1.prefHeightProperty().bind(mainPane.heightProperty());
        space2.prefWidthProperty().bind(mainPane.widthProperty());
        randomIMV.setImage(new Image(String.valueOf(getClass().getResource(randomImage()))));
    }

    @FXML
    void clientsClicked(ActionEvent event) {

    }

    @FXML
    void ingredientsClicked(ActionEvent event) {

    }

    @FXML
    void personalClicked(ActionEvent event) {

    }

    @FXML
    void productsClicked(ActionEvent event) {

    }

    @FXML
    void usersClicked(ActionEvent event) {

    }

    @FXML
    void hideMenuClicked(MouseEvent event) {

    }

    @FXML
    void popOutClicked(MouseEvent event) {

    }

    String randomImage() {
        Random r = new Random();
        int max  = 4; //Exclusive
        int min = 1; //Inclusive
        switch (r.nextInt(max - min) + min) {
            case 1:
                return "resources/gh-food1.png";
            case 2:
                return "resources/gh-food2.png";
            case 3:
                return "resources/gh-food3.png";
            default:
                return null;
        }
    }

}
