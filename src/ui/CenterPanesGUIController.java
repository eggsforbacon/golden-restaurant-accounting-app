package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.PlateType;
import model.Product;
import model.Restaurant;
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

    private Restaurant GH;
    public CenterPanesGUIController(Restaurant GH) {
        this.GH = GH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle( "\n-fx-alignment: CENTER;");
        enabledCol.setCellValueFactory(new PropertyValueFactory<>("enabled"));
        enabledCol.setStyle( "\n-fx-alignment: CENTER;");
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("theIngredients"));
        ingredientsCol.setStyle( "\n-fx-alignment: CENTER;");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("pt"));
        typeCol.setStyle( "\n-fx-alignment: CENTER;");

        ObservableList<Product> productsList = FXCollections.observableArrayList(GH.getProductsWithTheirSizes());
        productTBV.setItems(productsList);
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
    void editPrices(ActionEvent event) {

    }

    @FXML
    void editSizes(ActionEvent event) {

    }

    @FXML
    void editType(ActionEvent event) {

    }

    @FXML
    void fullProductDetails(MouseEvent event) {
        System.out.println("It works?");
    }
}
