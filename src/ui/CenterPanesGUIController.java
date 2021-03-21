package ui;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import  model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CenterPanesGUIController implements Initializable {
    /*Product pane*/
    @FXML
    private BorderPane productPane;

    @FXML
    private TableView<Product> productTBV;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> enabledCol;

    @FXML
    private TableColumn<Product, String> ingredientsCol;

    @FXML
    private TableColumn<Product, String> typeCol;

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

    //Add Pane
    @FXML
    private TextField newProdNameTF;

    @FXML
    private MenuButton newProdIngredientsMB;

    @FXML
    private TextField newProdTypeTF;

    @FXML
    private TextArea newProdSizesTA;

    @FXML
    private TextArea newProdPricesTA;

    @FXML
    private ListView<Ingredient> selectedItemsLV;

    private Restaurant GH;
    public CenterPanesGUIController(Restaurant GH) {
        this.GH = GH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductPane();
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
    void addProductCLicked(ActionEvent event) {
        try {
            System.out.println("We tried");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("add-product.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createProduct = new Stage();
            createProduct.setScene(new Scene(root));
            createProduct.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createProduct.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createProduct.getIcons().add(icon);
            createProduct.setTitle("Golden Restaurant: Añadir producto");
            createProduct.setResizable(false);
            initAddProductPane();
            createProduct.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            System.out.println(e);
        }
    }

    @FXML
    void cancelProduct(ActionEvent event) {

    }

    @FXML
    void confirmProduct(ActionEvent event) {
        System.out.println("We're in");
        String newProdName = newProdNameTF.getText();
        ArrayList<CheckMenuItem> items = new ArrayList<>();
        for (Ingredient ing: GH.getRestaurantIngredients()) items.add(new CheckMenuItem(ing.getName()));
        for (CheckMenuItem item : items) {
            item.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                Ingredient itemIng = GH.getRestaurantIngredients().get(GH.binarySearch(GH.getRestaurantIngredients(), item.getText()));
                if (newValue) {
                    System.out.println("true");
                    selectedItemsLV.getItems().add(itemIng);
                }
                else selectedItemsLV.getItems().remove(itemIng);
            });
        }
        ArrayList<Ingredient> newProdIngredients = new ArrayList<>(selectedItemsLV.getItems());
        System.out.println(newProdIngredients.toString());
        PlateType newProdPlateType = new PlateType(newProdTypeTF.getText());
        ArrayList<String> newProdSizes = new ArrayList<>(Arrays.asList(newProdSizesTA.getText().split(",")));
        ArrayList<Double> newProdPrices = new ArrayList<>();
        for (String s: newProdPricesTA.getText().split(",")) {
            newProdPrices.add(Double.parseDouble(s));
        }
        boolean productNameValid = !newProdName.isEmpty() && GH.productIndexWithName(newProdName) == -1;
        boolean productIngredientsValid = !selectedItemsLV.getItems().isEmpty();
        boolean ptIsValid = !newProdPlateType.getName().isEmpty();
        boolean sizesAndPricesValid = newProdPrices.size() == newProdSizes.size() && !newProdPrices.isEmpty();
        System.out.println(productIngredientsValid);
        if (productNameValid && productIngredientsValid && ptIsValid && sizesAndPricesValid) {
            GH.addProduct(newProdName,newProdPlateType,newProdIngredients,newProdSizes,newProdPrices);
            initProductPane();
            try {
                ((Stage) productPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
    }


    @FXML
    void fullProductDetails(MouseEvent event) {
        System.out.println("It works?");
    }

    private void initProductPane() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setStyle( "\n-fx-alignment: CENTER;");
        enabledCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        enabledCol.setStyle( "\n-fx-alignment: CENTER;");
        ingredientsCol.setCellValueFactory(new PropertyValueFactory<>("theIngredients"));
        ingredientsCol.setStyle( "\n-fx-alignment: CENTER;");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("pt"));
        typeCol.setStyle( "\n-fx-alignment: CENTER;");
        sizesCol.setCellValueFactory(new PropertyValueFactory<>("productActualSize"));
        sizesCol.setStyle( "\n-fx-alignment: CENTER;");
        pricesCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        pricesCol.setStyle("\n-fx-alignment: CENTER;");
        ObservableList<Product> productsList = FXCollections.observableArrayList(GH.getProductsWithTheirSizes());
        productTBV.setItems(productsList);
    }

    private void initAddProductPane() {
        ArrayList<CheckMenuItem> items = new ArrayList<>();
        for (Ingredient ing: GH.getRestaurantIngredients()) {
            items.add(new CheckMenuItem(ing.getName()));
        }
        newProdIngredientsMB.getItems().addAll(items);
    }
}
