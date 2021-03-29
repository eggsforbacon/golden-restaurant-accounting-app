package ui;

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
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private TableColumn<Product, String> nameProdCol;

    @FXML
    private TableColumn<Product, String> enabledProdCol;

    @FXML
    private TableColumn<Product, String> ingredientsProdCol;

    @FXML
    private TableColumn<Product, String> typeProdCol;

    @FXML
    private TableColumn<Product, String> sizesProdCol;

    @FXML
    private TableColumn<Product, Double> pricesProdCol;

    @FXML
    private Label spacer2;

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

    /*Ingredients Pane*/
    @FXML
    private BorderPane ingredientsPane;

    @FXML
    private TableView<Ingredient> ingredientsTBV;

    @FXML
    private TableColumn<Ingredient, String> nameIngCol;

    @FXML
    private TableColumn<Ingredient, String> enabledIngCol;

    @FXML
    private Label spacer3;

    //Add Pane
    @FXML
    private BorderPane ingredientPane;

    @FXML
    private TextField newIngNameTF;

    //Info Pane
    @FXML
    private Label ingNameInfoLBL;

    @FXML
    private Label ingEnabledInfoLBL;

    @FXML
    private Label ingCreatorInfoLBL;

    @FXML
    private Label ingEditorInfoLBL;

    /*Clients Pane*/
    @FXML
    private BorderPane clientsPane;

    @FXML
    private TableView<Client> clientsTBV;

    @FXML
    private TableColumn<Client, String> nameCliCol;

    @FXML
    private TableColumn<Client, String> lastNameCliCol;

    @FXML
    private TableColumn<Client, String> idCliCol;

    @FXML
    private TableColumn<Client, String> addressCliCol;

    @FXML
    private TableColumn<Client, String> teleCliCol;

    @FXML
    private TableColumn<Client, String> enabledCliCol;

    @FXML
    private TableColumn<Client, String> obsCliCol;

    @FXML
    private Label spacer4;

    //Add Pane
    @FXML
    private BorderPane clientPane;

    @FXML
    private TextField newCliNameTF;

    @FXML
    private TextField newCliLastNameTF;

    @FXML
    private TextField newCliAddressTF;

    @FXML
    private TextField newCliIDTF;

    @FXML
    private TextField newCliTeleTF;

    @FXML
    private TextArea newCliObsTA;

    //Info Pane
    @FXML
    private Label cliFullNameInfoLBL;

    @FXML
    private Label cliEnabledInfoLBL;

    @FXML
    private Label cliIDInfoLBL;

    @FXML
    private Label cliTeleInfoLBL;

    @FXML
    private Label cliAddressInfoLBL;

    @FXML
    private Label cliObsInfoLBL;

    @FXML
    private Label cliCreatorInfoLBL;

    @FXML
    private Label cliEditorInfoLBL;


    private final Restaurant GH;
    public CenterPanesGUIController(Restaurant GH) {
        this.GH = GH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductPane();
        initIngredientPane();
        initClientPane();
    }

    @FXML
    void editNameProd(CellEditEvent<Product, String> event) {
        GH.changeProductName(GH.productIndexWithName(event.getRowValue().getName()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
    }

    @FXML
    void editEnabledProd(CellEditEvent<Product, String> event) {
        if (event.getNewValue().equalsIgnoreCase("SI")) GH.enableProduct(GH.productIndexWithName(event.getRowValue().getName()));
        else GH.disableProduct(GH.productIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
    }

    @FXML
    void editIngredientProd(CellEditEvent<Product, String> event) {
        for (String s:event.getRowValue().getTheIngredients().split(",")) {
            GH.deleteAnIngredientOfAProduct(GH.productIndexWithName(event.getRowValue().getName()),s);
        }
        StringBuilder unAddedIngredients = new StringBuilder();
        for (String s: event.getNewValue().split(",")) {
            if (GH.getRestaurantIngredientsString().contains(s)) {
                GH.addAnIngredientToAProduct(GH.productIndexWithName(event.getRowValue().getName()),GH.getRestaurantIngredients().get(GH.ingredientIndexWithName(s)));
            } else {
                unAddedIngredients.append(s).append("\n");
            }
        }

        if (unAddedIngredients.length() != 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Error");
                deleteMessageLBL.setText("Los siguientes ingredientes no existen en el restaurante:\n" + unAddedIngredients.toString());
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                System.out.println(e.getMessage());
            }
        }
        initProductPane();
    }

    @FXML
    void editTypeProd(CellEditEvent<Product, String> event) {
        event.getRowValue().setPt(new PlateType(event.getNewValue(),GH.getCurrentUser()));
        int newPtIndex = GH.plateTypeIndexWithName(event.getNewValue());
        PlateType newPlateType;
        if (newPtIndex == -1) {
            GH.addAPlateTypeToTheRestaurant(event.getNewValue());
            newPtIndex = GH.plateTypeIndexWithName(event.getNewValue());
        }
        newPlateType = GH.getRestaurantPlateTypes().get(newPtIndex);
        GH.changeProductPlateType(GH.productIndexWithName(event.getRowValue().getName()),newPlateType);
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productGUI/add-product.fxml"));
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
            System.out.println(GH.ingredientIndexWithName(ingName) + "\n" + ingName + "\n" + GH.getRestaurantIngredientsString()+GH.getRestaurantIngredients().toString());
            Ingredient ingtoadd = GH.getRestaurantIngredients().get(GH.ingredientIndexWithName(ingName));
            newProdIngredients.add(ingtoadd);
        }
        PlateType newProdPlateType = new PlateType(newProdTypeTF.getText(),GH.getCurrentUser());
        ArrayList<String> newProdSizes = new ArrayList<>(Arrays.asList(newProdSizesTA.getText().split(",")));
        ArrayList<Double> newProdPrices = new ArrayList<>();
        for (String s: newProdPricesTA.getText().split(",")) {
            if (!s.isEmpty()) newProdPrices.add(Double.parseDouble(s));
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
        } else {
            try {
                ((Stage) productPane.getScene().getWindow()).close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Error");
                deleteMessageLBL.setText("No fue posible agregar el producto.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment. EEEEEEEE");
                e.printStackTrace();
            }
        }
    }

    void fullProductDetails(Product rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productGUI/prodInfo.fxml"));
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
        boolean noSelection = productTBV.getSelectionModel().getSelectedItems().isEmpty();
        System.out.println(noSelection);
        if (!noSelection) {
            for (int i = 0; i < productTBV.getSelectionModel().getSelectedItems().size(); i++) {
                Product removed = productTBV.getSelectionModel().getSelectedItems().get(i);
                GH.deleteProduct(GH.productIndexWithName(removed.getName()));
            }
            productTBV.getItems().removeAll(productTBV.getSelectionModel().getSelectedItems());
            initProductPane();
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
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
        prodCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        prodEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage)prodNameInfoLBL.getScene().getWindow()).setMaxWidth(520.0);
        ((Stage)prodNameInfoLBL.getScene().getWindow()).setMinHeight(520.0);
    }

    private void initProductPane() {
        nameProdCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameProdCol.setStyle( "\n-fx-alignment: CENTER;");
        enabledProdCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        enabledProdCol.setStyle( "\n-fx-alignment: CENTER;");
        ingredientsProdCol.setCellValueFactory(new PropertyValueFactory<>("theIngredients"));
        ingredientsProdCol.setStyle( "\n-fx-alignment: CENTER;");
        typeProdCol.setCellValueFactory(new PropertyValueFactory<>("pt"));
        typeProdCol.setStyle( "\n-fx-alignment: CENTER;");
        sizesProdCol.setCellValueFactory(new PropertyValueFactory<>("productActualSize"));
        sizesProdCol.setStyle( "\n-fx-alignment: CENTER;");
        pricesProdCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        pricesProdCol.setStyle("\n-fx-alignment: CENTER;");
        ObservableList<Product> productsList = FXCollections.observableArrayList(GH.getProductsWithTheirSizes());
        productTBV.setItems(productsList);

        nameProdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledProdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ingredientsProdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeProdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        sizesProdCol.setCellFactory(TextFieldTableCell.forTableColumn());
        pricesProdCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

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
        while(line!=null) {
            String[] parts = line.split(SEPARATOR);
            GH.addAPlateTypeToTheRestaurant(parts[1]);
            ArrayList<Ingredient> newIng = new ArrayList<>();
            for (String s: parts[2].split(",")) {
                GH.addAnIngredientToTheRestaurant(s);
                newIng.add(GH.getRestaurantIngredients().get(GH.ingredientIndexWithName(s)));
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


    private void initIngredientPane() {
        nameIngCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameIngCol.setStyle( "\n-fx-alignment: CENTER;");
        enabledIngCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        enabledIngCol.setStyle( "\n-fx-alignment: CENTER;");

        ObservableList<Ingredient> ingredientsList = FXCollections.observableArrayList(GH.getRestaurantIngredients());
        ingredientsTBV.setItems(ingredientsList);

        nameIngCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledIngCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ingredientsTBV.setRowFactory(tv -> {
            TableRow<Ingredient> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (! row.isEmpty()) {
                    Ingredient rowData = row.getItem();
                    fullIngredientDetails(rowData);
                }
            });
            return row ;
        });
    }

    @FXML
    void addIngredientCLicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingredientsGUI/add-ingredient.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createIngredient = new Stage();
            createIngredient.setScene(new Scene(root));
            createIngredient.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createIngredient.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createIngredient.getIcons().add(icon);
            createIngredient.setTitle("Golden Restaurant: Añadir Ingrediente");
            createIngredient.setResizable(false);
            createIngredient.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void cancelIngredient(ActionEvent event) {
        try {
            ((Stage) ingredientPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmIngredient(ActionEvent event) {
        String newIngName = newIngNameTF.getText();
        if (!GH.addAnIngredientToTheRestaurant(newIngName)) {
            try {
                ((Stage) ingredientPane.getScene().getWindow()).close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Error");
                deleteMessageLBL.setText("El ingrediente no pudo ser agregado");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        else {
            try {
                ((Stage) ingredientPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        initIngredientPane();
    }


    @FXML
    void deleteIngredientClicked(ActionEvent event) {
        Ingredient removed = ingredientsTBV.getSelectionModel().getSelectedItems().get(0);
        if (removed == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
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
        } else if (GH.deleteIngredient(GH.ingredientIndexWithIngredient(removed))){
            ingredientsTBV.getItems().removeAll(ingredientsTBV.getSelectionModel().getSelectedItems());
            ingredientsTBV.refresh();
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Error");
                deleteMessageLBL.setText("El ingrediente no pudo ser eliminado.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void editEnabledIng(CellEditEvent<Ingredient, String> event) {
        if (event.getNewValue().equalsIgnoreCase("SI")) GH.enableIngredient(GH.ingredientIndexWithName(event.getRowValue().getName()));
        else GH.disableIngredient(GH.ingredientIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initIngredientPane();
    }

    @FXML
    void editNameIng(CellEditEvent<Ingredient, String> event) {
        GH.changeingredientName(GH.ingredientIndexWithName(event.getRowValue().getName()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initIngredientPane();
    }

    @FXML
    void exportIngredientData(ActionEvent event) throws FileNotFoundException {
        String fileName = "src/data/INGREDIENTS.csv";
        PrintWriter pw = new PrintWriter(fileName);
        for(int i = 0; i < GH.getRestaurantIngredientsSize(); i++){
            Ingredient ing = GH.getRestaurantIngredients().get(i);
            pw.println(ing.getName());
        }
        pw.close();
    }

    @FXML
    void importIngredientData(ActionEvent event) throws IOException {
        String fileName = "src/data/INGREDIENTS.csv";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while(line!=null) {
            GH.addAnIngredientToTheRestaurant(line);
            line = br.readLine();
        }
        br.close();
        initIngredientPane();
    }

    public void fullIngredientDetails(Ingredient rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ingredientsGUI/ingInfo.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage ingredientInfo = new Stage();
            ingredientInfo.setScene(new Scene(root));
            ingredientInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            ingredientInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            ingredientInfo.getIcons().add(icon);
            ingredientInfo.setTitle("Información de Ingrediente");
            initIngredientInfo(rowData);
            ingredientInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            System.out.println(e.getMessage());
        }
    }

    private void initIngredientInfo(Ingredient rowData) {
        ingNameInfoLBL.setText(rowData.getName());
        ingEnabledInfoLBL.setText((rowData.getEnabled()) ? "(Habilitado)" : "(Deshabilitado)");
        ingEnabledInfoLBL.setId("enabled-label");
        ingCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        ingEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage)ingNameInfoLBL.getScene().getWindow()).setResizable(false);
    }

    private void initClientPane() {
        nameCliCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCliCol.setStyle( "\n-fx-alignment: CENTER;");
        enabledCliCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        enabledCliCol.setStyle( "\n-fx-alignment: CENTER;");
        lastNameCliCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        lastNameCliCol.setStyle( "\n-fx-alignment: CENTER;");
        idCliCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCliCol.setStyle( "\n-fx-alignment: CENTER;");
        addressCliCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCliCol.setStyle( "\n-fx-alignment: CENTER;");
        teleCliCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        teleCliCol.setStyle("\n-fx-alignment: CENTER;");
        obsCliCol.setCellValueFactory(new PropertyValueFactory<>("observations"));
        obsCliCol.setStyle("\n-fx-alignment: CENTER;");
        ObservableList<Client> clientsList = FXCollections.observableArrayList(GH.getRestaurantClients());
        clientsTBV.setItems(clientsList);

        nameCliCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledCliCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCliCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCliCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCliCol.setCellFactory(TextFieldTableCell.forTableColumn());
        teleCliCol.setCellFactory(TextFieldTableCell.forTableColumn());
        obsCliCol.setCellFactory(TextFieldTableCell.forTableColumn());

        clientsTBV.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (! row.isEmpty()) {
                    Client rowData = row.getItem();
                    fullClientDetails(rowData);
                }
            });
            return row ;
        });
    }

    private void fullClientDetails(Client rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsGUI/clientsInfo.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage ingredientInfo = new Stage();
            ingredientInfo.setScene(new Scene(root));
            ingredientInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            ingredientInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            ingredientInfo.getIcons().add(icon);
            ingredientInfo.setTitle("Información de Cliente");
            initClientInfo(rowData);
            ingredientInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            System.out.println(e.getMessage());
        }
    }

    private void initClientInfo(Client rowData) {
        cliFullNameInfoLBL.setText(rowData.getName() + " " + rowData.getLastname());
        cliEnabledInfoLBL.setText((rowData.getEnabled()) ? "Habilitado" : "Deshabilitado");
        cliIDInfoLBL.setText(rowData.getId());
        cliTeleInfoLBL.setText(rowData.getPhoneNumber());
        cliAddressInfoLBL.setText(rowData.getAddress());
        cliObsInfoLBL.setText(rowData.getObservations());
        cliCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        cliEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage)cliFullNameInfoLBL.getScene().getWindow()).setMaxWidth(590.0);
        ((Stage)cliFullNameInfoLBL.getScene().getWindow()).setMinHeight(458.0);
    }

    @FXML
    void addClientClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsGUI/add-client.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createIngredient = new Stage();
            createIngredient.setScene(new Scene(root));
            createIngredient.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createIngredient.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createIngredient.getIcons().add(icon);
            createIngredient.setTitle("Golden Restaurant: Añadir Ingrediente");
            createIngredient.setResizable(false);
            createIngredient.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void deleteClientClicked(ActionEvent event) {
        boolean noSelection = clientsTBV.getSelectionModel().getSelectedItems().isEmpty();
        System.out.println(noSelection);
        if (!noSelection) {
            for (int i = 0; i < clientsTBV.getSelectionModel().getSelectedItems().size(); i++) {
                Client removed = clientsTBV.getSelectionModel().getSelectedItems().get(i);
                GH.deleteClient(GH.clientIndexWithNameAndLastname(removed.getName(),removed.getLastname()));
            }
            clientsTBV.getItems().removeAll(clientsTBV.getSelectionModel().getSelectedItems());
            initProductPane();
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
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
        }
    }

    @FXML
    void editAddressCli(CellEditEvent<Client, String> event) {
        GH.changeClientAddress(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editEnabledCli(CellEditEvent<Client, String> event) {
        if (event.getNewValue().equalsIgnoreCase("SI")) GH.enableClient(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()));
        else GH.disableClient(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editIdCli(CellEditEvent<Client, String> event) {
        GH.changeClientId(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editLastNameCli(CellEditEvent<Client, String> event) {
        GH.changeClientLastname(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editNameCli(CellEditEvent<Client, String> event) {
        GH.changeClientName(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editObsCli(CellEditEvent<Client, String> event) {
        GH.changeClientObservations(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editTeleCli(CellEditEvent<Client, String> event) {
        GH.changeClientPhoneNumber(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(),event.getRowValue().getLastname()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void cancelClient(ActionEvent event) {
        try {
            ((Stage) clientPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmClient(ActionEvent event) {
        String newName = newCliNameTF.getText();
        String newLastName = newCliLastNameTF.getText();
        String newAddress = newCliAddressTF.getText();
        String newId = newCliIDTF.getText();
        String newTelephone = newCliTeleTF.getText();
        String newObs = (newCliObsTA.getText().isEmpty()) ? "-" : newCliObsTA.getText();
        boolean canCreateClient = !newName.isEmpty() && !newLastName.isEmpty() && !newAddress.isEmpty() && ! newId.isEmpty() && !newTelephone.isEmpty();
        if (canCreateClient) {
            boolean userCreated = GH.addAClientToTheRestaurant(newName,newLastName,newId,newAddress,newTelephone,newObs);
            System.out.println("User created: " + userCreated);
            initClientPane();
            try {
                ((Stage) clientPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Error");
                deleteMessageLBL.setText("El cliente no pudo ser creado.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
                ((Stage) clientPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    void exportClientData(ActionEvent event) throws FileNotFoundException {
        String fileName = "src/data/CLIENTS.csv";
        PrintWriter pw = new PrintWriter(fileName);
        for(int i = 0; i < GH.getRestaurantClientsSize(); i++){
            Client client = GH.getRestaurantClients().get(i);
            pw.println(client.showInformation());
        }
        pw.close();
    }

    @FXML
    void importClientData(ActionEvent event) throws IOException {
        String fileName = "src/data/CLIENTS.csv";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while(line!=null) {
            String[] parts = line.split(SEPARATOR);
            GH.addAClientToTheRestaurant(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5]);
            line = br.readLine();
        }
        br.close();
        initClientPane();
    }
}

