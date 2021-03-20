package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.PlateType;
import model.Product;
import model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CenterPanesGUIController implements Initializable {
    /*Product pane*/
    @FXML
    private TableView<Product> productTBV;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> enabledCol;

    @FXML
    private TableColumn<Product, String> ingredientsCol;

    @FXML
    private TableColumn<Product, PlateType> typeCol;

    @FXML
    private TableColumn<Product, ArrayList<String>> sizesCol;

    @FXML
    private TableColumn<Product, ArrayList<String>> pricesCol;

    @FXML
    private Label spacer2;

    @FXML
    private Label spacer3;

    @FXML
    private Label lastUserLBL;

    public CenterPanesGUIController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("We're in");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle( "\n-fx-alignment: CENTER;");
        enabledCol.setCellValueFactory(new PropertyValueFactory<>("enabled"));
        enabledCol.setStyle( "\n-fx-alignment: CENTER;");
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("ingrdnts"));
        ingredientsCol.setStyle( "\n-fx-alignment: CENTER;");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("pt"));
        typeCol.setStyle( "\n-fx-alignment: CENTER;");

    }

    @FXML
    void editEnabled(ActionEvent event) {
    }

    @FXML
    void editIngredient(ActionEvent event) {

    }

    @FXML
    void editName(ActionEvent event) {

    }

    @FXML
    void editPriceLarge(ActionEvent event) {

    }

    @FXML
    void editPriceMedium(ActionEvent event) {

    }

    @FXML
    void editPriceSmall(ActionEvent event) {

    }
}
