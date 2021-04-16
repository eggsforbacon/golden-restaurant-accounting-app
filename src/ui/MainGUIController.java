package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Restaurant;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    CenterPanesGUIController cenPaneController;
    Restaurant GH;

    public MainGUIController(Restaurant GH) {
        this.GH = GH;
        cenPaneController = new CenterPanesGUIController(this.GH);
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
    private BorderPane mainPane = new BorderPane();

    @FXML
    private ImageView randomIMV;

    @FXML
    private VBox sidebarVBOX;

    @FXML
    private Button mainOrderBTN;

    @FXML
    private Button mainProdBTN;

    @FXML
    private Button mainIngBTN;

    @FXML
    private Button mainTpBTN;

    @FXML
    private Button mainClientBTN;

    @FXML
    private Button mainEmpBTN;

    @FXML
    private Button loginBTN;

    @FXML
    private Button reportsBTN;

    @FXML
    private Label spacer1;

    @FXML
    private Label currentUserLBL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label = progress;
        spacer1.prefHeightProperty().bind(mainPane.heightProperty());
        randomIMV.setImage(new Image(String.valueOf(getClass().getResource(randomImage()))));
        System.out.println(GH.checkFirstTime());
        if (GH.checkFirstTime()) {
            toggleButtons(true);
            login();
            System.out.println("Primera vez");
            GH.setFirstTime(false);
        } else {
            currentScene.setCenter(homeScreenIMV);
            homeScreenIMV.setVisible(true);
            toggleButtons(false);
        }
        homeScreenIMV.fitHeightProperty().bind(mainPane.heightProperty());
        homeScreenIMV.fitWidthProperty().bind(mainPane.widthProperty());
        currentScene.prefHeightProperty().bind(mainPane.heightProperty());
        currentScene.prefWidthProperty().bind(mainPane.widthProperty());


    }

    public void toggleButtons(boolean state) {
        mainOrderBTN.setDisable(state);
        mainClientBTN.setDisable(state);
        mainEmpBTN.setDisable(state);
        mainIngBTN.setDisable(state);
        mainTpBTN.setDisable(state);
        mainProdBTN.setDisable(state);
        reportsBTN.setDisable(state);
    }

    @FXML
    void clientsClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsGUI/clients-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }


    @FXML
    void ingredientsClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingredientsGUI/ingredients-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void personnelClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personnelGUI/user-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void productsClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productGUI/product-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void loginClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    void login() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void ordersClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ordersGUI/order-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    void plateTypesClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("typesGUI/types-center.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
        }
    }

    @FXML
    public void houseClicked(MouseEvent event) {
        credentials();
        try {
            currentScene.setCenter(homeScreenIMV);
            if (!GH.checkFirstTime()) {
                homeScreenIMV.setVisible(true);
            }
            if (cenPaneController.getLoginSuccesful()) {
            	toggleButtons(false);
            }
            currentScene.setStyle("\n-fx-background-color: black;");
        } catch (Exception e) {
            System.out.println("Can't load image at the moment");
        }
    }

    @FXML
    void genReportClicked(ActionEvent event) {
        credentials();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reportGen.fxml"));
            fxmlLoader.setController(cenPaneController);
            Parent root = fxmlLoader.load();
            currentScene.setCenter(root);
        } catch (Exception e) {
            System.out.println("Can't load scene at the moment");
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

    void credentials() {
        try {
            if (GH.getCurrentUser().getUsername().equals(GH.getRootUser().getUsername())) {
                currentUserLBL.setText("Administrador");
                currentUserLBL.setStyle("\n-fx-text-fill: #de260d;");
            } else {
                currentUserLBL.setText(cenPaneController.getCurrentUser());
                currentUserLBL.setStyle("" +
                        "-fx-text-fill: #e1be66;\n" +
                        "-fx-font-weight: bold;");
            }
        } catch (NullPointerException npe) {
            currentUserLBL.setText("Sin iniciar sesión");
            npe.fillInStackTrace();
        }
    }
}
