package ui;

import javafx.application.HostServices;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;
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
import sun.security.action.OpenFileInputStreamAction;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

public class CenterPanesGUIController implements Initializable {

    private static final String SEPARATOR = ";";
    /*Delete Pane (General use)*/
    @FXML
    private Label deleteMessageLBL;

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
    private TableColumn<Product, String> sizesCol;

    @FXML
    private TableColumn<Product, Double> pricesCol;

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
    private TextField newProdTypeTF;

    @FXML
    private TextArea newProdSizesTA;

    @FXML
    private TextArea newProdPricesTA;

    @FXML
    private ListView<String> selectedItemsLV;

    @FXML
    private Label ingredientsInfoLabel;

    //Info Pane
    @FXML
    private Label prodNameInfoLBL;

    @FXML
    private Label prodEnabledInfoLBL;

    @FXML
    private Label prodPTInfoLBL;

    @FXML
    private Label prodIngInfoLBL;

    @FXML
    private Label prodSizeInfoLBL;

    @FXML
    private Label prodPricesInfoLBL;

    @FXML
    private Label prodCreatorInfoLBL;

    @FXML
    private Label prodEditorInfoLBL;

    private Restaurant GH;
    public CenterPanesGUIController(Restaurant GH) {
        this.GH = GH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductPane();
    }

    @FXML
    void editNameProd(CellEditEvent<Product, String> event) {
        (event.getRowValue()).setName(event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editEnabledProd(CellEditEvent<Product, String> event) {
        (event.getRowValue()).setEnabled(event.getNewValue().equals("SI"));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editIngredientProd(ActionEvent event) {
        //WIP
    }

    @FXML
    void editTypeProd(CellEditEvent<Product, String> event) {
        event.getRowValue().setPt(new PlateType(event.getNewValue(),GH.getCurrentUser()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editSizesProd(CellEditEvent<Product, String> event) {
        //WIP
    }

    @FXML
    void editPricesProd(CellEditEvent<Product, String> event) {
        //WIP
    }

    @FXML
    void addProductCLicked(ActionEvent event) {
        try {
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
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void cancelProduct(ActionEvent event) {
        try {
            ((Stage) productPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmProduct(ActionEvent event) {
        String newProdName = newProdNameTF.getText();
        ArrayList<Ingredient> newProdIngredients = new ArrayList<>();
        for (String ingName: selectedItemsLV.getSelectionModel().getSelectedItems()) {
            Ingredient ingtoadd = GH.getRestaurantIngredients().get(GH.ingredientIndexWithName(ingName));
            newProdIngredients.add(ingtoadd);
        }
        PlateType newProdPlateType = new PlateType(newProdTypeTF.getText(),GH.getCurrentUser());
        ArrayList<String> newProdSizes = new ArrayList<>(Arrays.asList(newProdSizesTA.getText().split(",")));
        ArrayList<Double> newProdPrices = new ArrayList<>();
        for (String s: newProdPricesTA.getText().split(",")) {
            newProdPrices.add(Double.parseDouble(s));
        }
        boolean productNameValid = !newProdName.isEmpty() && GH.productIndexWithName(newProdName) == -1;
        boolean productIngredientsValid = !selectedItemsLV.getItems().isEmpty();
        boolean ptIsValid = !newProdPlateType.getName().isEmpty();
        boolean sizesAndPricesValid = newProdPrices.size() == newProdSizes.size() && !newProdPrices.isEmpty();
        System.out.println(newProdPrices.toString());
        System.out.println(newProdSizes.toString());
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

    void fullProductDetails(Product rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prodInfo.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage productInfo = new Stage();
            productInfo.setScene(new Scene(root));
            productInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            productInfo.getIcons().add(icon);
            productInfo.setTitle("Información de Producto");
            initProductInfo(rowData);
            productInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void deleteProductClicked(ActionEvent event) {
        Product removed = productTBV.getSelectionModel().getSelectedItems().get(0);
        if (removed == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("delete.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Error");
                deleteMessageLBL.setText("No hay selección. Intente de nuevo.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                System.out.println(e.getMessage());
            }
        } else {
            productTBV.getItems().removeAll(productTBV.getSelectionModel().getSelectedItems());
            productTBV.refresh();
            //WIP
        }
    }

    private void initProductInfo(Product rowData) {
        prodNameInfoLBL.setText(rowData.getName());
        prodEnabledInfoLBL.setText((rowData.getEnabled()) ? "(Habilitado)" : "(Deshabilitado)");
        prodEnabledInfoLBL.setId("enabled-label");
        prodPTInfoLBL.setText(rowData.getPt());
        prodIngInfoLBL.setText(rowData.getTheIngredients());
        String[] split = rowData.sizesInformation().split(";");
        StringBuilder sizes = new StringBuilder();
        StringBuilder prices = new StringBuilder();
        for (String s : split) {
            sizes.append(s.split(",")[0]).append("\n");
            prices.append(s.split(",")[1]).append("\n");
        }
        prodSizeInfoLBL.setText(sizes.toString());
        prodPricesInfoLBL.setText(prices.toString());
        ((Stage)prodNameInfoLBL.getScene().getWindow()).setMaxWidth(520.0);
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

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ingredientsCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        sizesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pricesCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        productTBV.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (! row.isEmpty()) {
                    Product rowData = row.getItem();
                    fullProductDetails(rowData);
                }
            });
            return row ;
        });
    }

    private void initAddProductPane() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Ingredient ing: GH.getRestaurantIngredients()) items.add(ing.getName());
        selectedItemsLV.setItems(items);
        selectedItemsLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void hideIngInfo(MouseEvent event) {
        ingredientsInfoLabel.setVisible(false);
    }

    @FXML
    void showIngInfo(MouseEvent event) {
        ingredientsInfoLabel.setVisible(true);
    }

    @FXML
    void exportProductData(ActionEvent event) throws Exception {
        String fileName = "src/data/PRODUCTS.csv";
        PrintWriter pw = new PrintWriter(fileName);
        for(int i = 0; i < GH.getRestaurantProductsSize(); i++){
            Product p = GH.getRestaurantProducts().get(i);
            pw.println(p.showInformation());
        }
        pw.close();
    }

    @FXML
    void importProductData(ActionEvent event) throws IOException {
        String fileName = "src/data/PRODUCTS.csv";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while(line!=null){
            String[] parts = line.split(SEPARATOR);
            ArrayList<Ingredient> newIng = new ArrayList<>();
            for (String s: parts[2].split(",")) {
                newIng.add(new Ingredient(s,GH.getCurrentUser()));
            }
            ArrayList<String> newSizes = new ArrayList<>();
            ArrayList<Double> newPrices = new ArrayList<>();
            for (int i = 3; i < parts.length; i++) {
                newSizes.add(parts[i].split(",")[0]);
                newPrices.add(Double.parseDouble(parts[i].split(",")[1]));
            }
            GH.addProduct(parts[0],new PlateType(parts[1],GH.getCurrentUser()),newIng,newSizes,newPrices);
            line = br.readLine();
        }
        br.close();
        initProductPane();
    }
}
