package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Restaurant;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    CenterPanesGUIController cenPaneController;

    public MainGUIController(Restaurant GH) {
        this.GH = GH;
        cenPaneController = new CenterPanesGUIController(GH);
    }

    /*Splash screen*/
    @FXML
    private BorderPane splashPane;

    public static Label label;

    @FXML
    private Label progress;

    /*Main pane*/
    @FXML
    private ImageView homeScreenIMV;

    @FXML
    private BorderPane currentScene;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ImageView randomIMV;

    @FXML
    private VBox sidebarVBOX;

    @FXML
    private Label spacer1;

    private Restaurant GH;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label = progress;
        spacer1.prefHeightProperty().bind(mainPane.heightProperty());
        randomIMV.setImage(new Image(String.valueOf(getClass().getResource(randomImage()))));

        homeScreenIMV.fitHeightProperty().bind(mainPane.heightProperty());
        homeScreenIMV.fitWidthProperty().bind(mainPane.widthProperty());
        currentScene.prefHeightProperty().bind(mainPane.heightProperty());
        currentScene.prefWidthProperty().bind(mainPane.widthProperty());
    }

    @FXML
    void clientsClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clients-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void ingredientsClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingredients-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void personnelClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personnel-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void productsClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void usersClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("users-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void houseClicked(MouseEvent event) {
        try {
            currentScene.setCenter(homeScreenIMV);
            currentScene.setStyle("\n-fx-background-color: black;");
        } catch (Exception e) {
            System.out.println("Can't load image at the moment");
        }
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
