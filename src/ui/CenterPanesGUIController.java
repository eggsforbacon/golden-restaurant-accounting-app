package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import model.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CenterPanesGUIController implements Initializable {

    private static final String SEPARATOR = ";";
    private static final LocalDateTime START = LocalDateTime.of(Year.now().getValue(), YearMonth.now().getMonth(), MonthDay.now().getDayOfMonth(), 0, 0, 0);
    /*Delete Pane (General use)*/
    @FXML
    private Label deleteMessageLBL;

    /*Product pane*/
    @FXML
    private BorderPane productPane;

    @FXML
    private TableView<Product> productTBV = new TableView<>();

    @FXML
    private TableColumn<Product, String> nameProdCol = new TableColumn<>();

    @FXML
    private TableColumn<Product, String> enabledProdCol = new TableColumn<>();

    @FXML
    private TableColumn<Product, String> ingredientsProdCol = new TableColumn<>();

    @FXML
    private TableColumn<Product, String> typeProdCol = new TableColumn<>();

    @FXML
    private TableColumn<Product, String> sizesProdCol = new TableColumn<>();

    @FXML
    private TableColumn<Product, Double> pricesProdCol = new TableColumn<>();

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
    private TableView<Ingredient> ingredientsTBV = new TableView<>();

    @FXML
    private TableColumn<Ingredient, String> nameIngCol = new TableColumn<>();

    @FXML
    private TableColumn<Ingredient, String> enabledIngCol = new TableColumn<>();

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
    private TableView<Client> clientsTBV = new TableView<>();

    @FXML
    private TableColumn<Client, String> nameCliCol = new TableColumn<>();

    @FXML
    private TableColumn<Client, String> lastNameCliCol = new TableColumn<>();

    @FXML
    private TableColumn<Client, String> idCliCol = new TableColumn<>();

    @FXML
    private TableColumn<Client, String> addressCliCol = new TableColumn<>();

    @FXML
    private TableColumn<Client, String> teleCliCol = new TableColumn<>();

    @FXML
    private TableColumn<Client, String> enabledCliCol = new TableColumn<>();

    @FXML
    private TableColumn<Client, String> obsCliCol = new TableColumn<>();

    @FXML
    private Label spacer4;

    @FXML
    private TextField searchUserTF;

    @FXML
    private Label searchTimeLBL;

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

    /*Types Pane*/
    @FXML
    private BorderPane typesPane;

    @FXML
    private TableView<PlateType> typesTBV = new TableView<>();

    @FXML
    private TableColumn<PlateType, String> nameTypeCol = new TableColumn<>();

    @FXML
    private TableColumn<PlateType, String> enabledTypeCol = new TableColumn<>();

    //Add Pane
    @FXML
    private BorderPane typePane;

    @FXML
    private TextField newTypeNameTF;

    //Info Pane
    @FXML
    private Label typeNameInfoLBL;

    @FXML
    private Label typeEnabledInfoLBL;

    @FXML
    private Label typeCreatorInfoLBL;

    @FXML
    private Label typeEditorInfoLBL;

    /*Order Pane*/
    @FXML
    private TableView<Order> orderTBV = new TableView<>();

    @FXML
    private TableColumn<Order, String> codeCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> statusOrderCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> prodOrderCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> quantityOrderCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> ordererCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> serverCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> orderedDateCol = new TableColumn<>();

    @FXML
    private TableColumn<Order, String> obsOrderCol = new TableColumn<>();

    @FXML
    private Label spacer5;

    @FXML
    private Button queueBTN = new Button();

    //Add Pane
    @FXML
    private BorderPane orderPane;

    @FXML
    private TextField clientOrderTF;

    @FXML
    private ListView<String> selectedProductsLV;

    @FXML
    private Label productsInfoLabel;

    @FXML
    private TextArea newProdQuantitiesTA;

    @FXML
    private TextArea newOrderObsTA;

    @FXML
    private TextField employeeOrderTF;

    //Info Pane
    @FXML
    private Label orderCodeInfoLBL;

    @FXML
    private Label orderEnabledInfoLBL;

    @FXML
    private Label orderStatusInfoLBL;

    @FXML
    private Label orderDateInfoLBL;

    @FXML
    private Label orderProdInfoLBL;

    @FXML
    private Label orderClientInfoLBL;

    @FXML
    private Label orderEmployeeInfoLBL;

    @FXML
    private Label orderObservationsInfoLBL;

    @FXML
    private Label orderCreatorInfoLBL;

    @FXML
    private Label orderEditorInfoLBL;

    /*Employee pane*/
    @FXML
    private BorderPane employeesPane;

    @FXML
    private TableView<User> employeesTBV = new TableView<>();

    @FXML
    private TableColumn<User, String> nameEmpCol = new TableColumn<>();

    @FXML
    private TableColumn<User, String> lastNameEmpCol = new TableColumn<>();

    @FXML
    private TableColumn<User, String> idEmpCol = new TableColumn<>();

    @FXML
    private TableColumn<User, String> enabledEmpCol = new TableColumn<>();

    @FXML
    private TableColumn<User, String> usernameEmpCol = new TableColumn<>();

    //Add Pane
    @FXML
    private BorderPane userPane;

    @FXML
    private TextField newEmpNameTF;

    @FXML
    private TextField newEmpLastNameTF;

    @FXML
    private TextField newUserNameTF;

    @FXML
    private PasswordField userPWF;

    @FXML
    private TextField newEmpIDTF;

    //Info Pane
    @FXML
    private Label empFullNameInfoLBL;

    @FXML
    private Label empEnabledInfoLBL;

    @FXML
    private Label empDInfoLBL;

    @FXML
    private Label empUserInfoLBL;

    @FXML
    private Label empCreatorInfoLBL;

    @FXML
    private Label empEditorInfoLBL;

    //Edit pane
    @FXML
    private BorderPane editUser;

    @FXML
    private TextField userNameTF;

    @FXML
    private PasswordField oldPassPWF;

    @FXML
    private PasswordField newPassPWF;

    @FXML
    private Label wrongLBL;

    /*Login pane*/
    @FXML
    private TextField userNameLoginTF;

    @FXML
    private PasswordField passwordLoginPF;

    @FXML
    private Button loginBTN;

    @FXML
    private Button registerBTN;

    @FXML
    private BorderPane loginPaneOrsmnt;

    /*Reports Pane*/
    @FXML
    private BorderPane reportPane;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label destinationLBL;

    private  Restaurant GH;

    public CenterPanesGUIController(Restaurant GH) {
        this.GH = GH;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductPane();
        initIngredientPane();
        initClientPane();
        initPlateTypePane();
        initOrderPane();
        initEmployeePane();
    }

    @FXML
    void editNameProd(CellEditEvent<Product, String> event) throws IOException {
        GH.changeProductName(GH.productIndexWithName(event.getRowValue().getName()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
        GH.saveAllData();
    }

    @FXML
    void editEnabledProd(CellEditEvent<Product, String> event) throws IOException {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableProduct(GH.productIndexWithName(event.getRowValue().getName()));
        else GH.disableProduct(GH.productIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
        GH.saveAllData();
    }

    @FXML
    void editIngredientProd(CellEditEvent<Product, String> event) throws IOException {
       /*
    	for (String s : event.getRowValue().getTheIngredients().split(",")) {
            GH.deleteAnIngredientOfAProduct(GH.productIndexWithName(event.getRowValue().getName()), s);
        }
        */
    	boolean done = false;
        StringBuilder unAddedIngredients = new StringBuilder();
        for (String s : event.getNewValue().split(",")) {
            if (GH.getRestaurantIngredientsString().contains(s) && GH.ingredientIndexWithName(s) != -1) {
            	if(!done) {
            		GH.deleteAllIngredients(GH.productIndexWithName(event.getRowValue().getName()));
            		done = true;
            	}
                GH.addAnIngredientToAProduct(GH.productIndexWithName(event.getRowValue().getName()), GH.getRestaurantIngredients().get(GH.ingredientIndexWithName(s)));
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
                e.printStackTrace();
            }
        }
        initProductPane();
        GH.saveAllData();
    }

    @FXML
    void editTypeProd(CellEditEvent<Product, String> event) throws IOException {
        event.getRowValue().setPt(new PlateType(event.getNewValue(), GH.getCurrentUser()));
        int newPtIndex = GH.plateTypeIndexWithName(event.getNewValue());
        PlateType newPlateType;
        if (newPtIndex == -1) {
            GH.addAPlateTypeToTheRestaurant(event.getNewValue());
            newPtIndex = GH.plateTypeIndexWithName(event.getNewValue());
        }
        newPlateType = GH.getRestaurantPlateTypes().get(newPtIndex);
        GH.changeProductPlateType(GH.productIndexWithName(event.getRowValue().getName()), newPlateType);
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
        GH.saveAllData();
    }

    @FXML
    void editSizesProd(CellEditEvent<Product, String> event) throws IOException {
        GH.changeSizeOfTheProduct(GH.productIndexWithName(event.getRowValue().getName()),event.getRowValue().getProductActualSize(),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        GH.saveAllData();
    }

    @FXML
    void editPricesProd(CellEditEvent<Product, Double> event) throws IOException {
        Double db = event.getNewValue();
        GH.changePriceOfTheProduct(GH.productIndexWithName(event.getRowValue().getName()),event.getRowValue().getProductActualSize(),db);
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        GH.saveAllData();
    }

    @FXML
    void sortProducts(ActionEvent event) {
        GH.sortProductsByPrice();
        initProductPane();
    }

    @FXML
    void addProductCLicked(ActionEvent event) throws IOException {
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
            e.printStackTrace();
        }
        GH.saveAllData();
    }

    @FXML
    void cancelProduct(ActionEvent event) throws IOException {
        try {
            ((Stage) productPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void confirmProduct(ActionEvent event) throws IOException {
        String newProdName = newProdNameTF.getText();
        ArrayList<Ingredient> newProdIngredients = new ArrayList<>();
        for (String ingName : selectedItemsLV.getSelectionModel().getSelectedItems()) {
            Ingredient ingtoadd = GH.getRestaurantIngredients().get(GH.ingredientIndexWithName(ingName));
            newProdIngredients.add(ingtoadd);
        }
        ArrayList<String> newProdSizes = new ArrayList<>(Arrays.asList(newProdSizesTA.getText().split(",")));
        ArrayList<Double> newProdPrices = new ArrayList<>();
        for (String s : newProdPricesTA.getText().split(",")) {
            if (!s.isEmpty()) newProdPrices.add(Double.parseDouble(s));
        }
        boolean productNameValid = !newProdName.isEmpty() && GH.productIndexWithName(newProdName) == -1;
        boolean productIngredientsValid = !selectedItemsLV.getItems().isEmpty();
        boolean ptIsValid = !newProdTypeTF.getText().isEmpty();
        boolean sizesAndPricesValid = newProdPrices.size() == newProdSizes.size() && !newProdPrices.isEmpty();
        if (productNameValid && productIngredientsValid && ptIsValid && sizesAndPricesValid) {
            GH.addAPlateTypeToTheRestaurant(newProdTypeTF.getText());
            GH.addProduct(newProdName, GH.getRestaurantPlateTypes().get(GH.plateTypeIndexWithName(newProdTypeTF.getText())), newProdIngredients, newProdSizes, newProdPrices);
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
                System.out.println("Can't load window at  mothement.");
                e.printStackTrace();
            }
        }
        GH.saveAllData();
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
            e.printStackTrace();
        }
    }

    @FXML
    void deleteProductClicked(ActionEvent event) throws IOException {
        boolean noSelection = productTBV.getSelectionModel().getSelectedItems().isEmpty();
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
                e.printStackTrace();
            }
        }
        GH.saveAllData();
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
        ((Stage) prodNameInfoLBL.getScene().getWindow()).setMaxWidth(520.0);
        ((Stage) prodNameInfoLBL.getScene().getWindow()).setMinHeight(520.0);
    }

    private void initProductPane() {
        nameProdCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        enabledProdCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        ingredientsProdCol.setCellValueFactory(new PropertyValueFactory<>("theIngredients"));
        typeProdCol.setCellValueFactory(new PropertyValueFactory<>("pt"));
        sizesProdCol.setCellValueFactory(new PropertyValueFactory<>("productActualSize"));
        pricesProdCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
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
                if (!row.isEmpty()) {
                    Product rowData = row.getItem();
                    fullProductDetails(rowData);
                }
            });
            return row;
        });
    }

    private void initAddProductPane() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Ingredient ing : GH.getRestaurantIngredients()) items.add(ing.getName());
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
        GH.generateProductReport("src/data/generatedData/PRODUCT_GENERATED_DATA.csv", SEPARATOR, START, LocalDateTime.now());
    }

    @FXML
    void importProductData(ActionEvent event) throws IOException {
        GH.importProductInformation("src/data/generatedData/PRODUCT_GENERATED_DATA.csv");
        initProductPane();
        GH.saveAllData();
    }

    private void initIngredientPane() {
        nameIngCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        enabledIngCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        ObservableList<Ingredient> ingredientsList = FXCollections.observableArrayList(GH.getRestaurantIngredients());
        ingredientsTBV.setItems(ingredientsList);

        nameIngCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledIngCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ingredientsTBV.setRowFactory(tv -> {
            TableRow<Ingredient> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    Ingredient rowData = row.getItem();
                    fullIngredientDetails(rowData);
                }
            });
            return row;
        });
    }

    @FXML
    void addIngredientCLicked(ActionEvent event) throws IOException {
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
            e.printStackTrace();
        }
        GH.saveAllData();
    }

    @FXML
    void cancelIngredient(ActionEvent event) throws IOException {
        try {
            ((Stage) ingredientPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void confirmIngredient(ActionEvent event) throws IOException {
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
        } else {
            try {
                ((Stage) ingredientPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        initIngredientPane();
        GH.saveAllData();
    }


    @FXML
    void deleteIngredientClicked(ActionEvent event) throws IOException {
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
                e.printStackTrace();
            }
        } else if (GH.deleteIngredient(GH.ingredientIndexWithIngredient(removed))) {
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
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    @FXML
    void editEnabledIng(CellEditEvent<Ingredient, String> event) throws IOException {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableIngredient(GH.ingredientIndexWithName(event.getRowValue().getName()));
        else GH.disableIngredient(GH.ingredientIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initIngredientPane();
        GH.saveAllData();
    }

    @FXML
    void editNameIng(CellEditEvent<Ingredient, String> event) throws IOException {
        GH.changeingredientName(GH.ingredientIndexWithName(event.getRowValue().getName()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initIngredientPane();
        GH.saveAllData();
    }

    @FXML
    void sortIngredients(ActionEvent event) {
        GH.descendantSortIngredients();
        initIngredientPane();
    }

    @FXML
    void exportIngredientData(ActionEvent event) throws FileNotFoundException {
        String fileName = "src/data/INGREDIENTS.csv";
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < GH.getRestaurantIngredientsSize(); i++) {
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
        while (line != null) {
            GH.addAnIngredientToTheRestaurant(line);
            line = br.readLine();
        }
        br.close();
        initIngredientPane();
        GH.saveAllData();
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
            e.printStackTrace();
        }
    }

    private void initIngredientInfo(Ingredient rowData) {
        ingNameInfoLBL.setText(rowData.getName());
        ingEnabledInfoLBL.setText((rowData.getEnabled()) ? "(Habilitado)" : "(Deshabilitado)");
        ingEnabledInfoLBL.setId("enabled-label");
        ingCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        ingEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage) ingNameInfoLBL.getScene().getWindow()).setResizable(false);
    }

    private void initClientPane() {
        nameCliCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        enabledCliCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        lastNameCliCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        idCliCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressCliCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        teleCliCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        obsCliCol.setCellValueFactory(new PropertyValueFactory<>("observations"));
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
                if (!row.isEmpty()) {
                    Client rowData = row.getItem();
                    fullClientDetails(rowData);
                }
            });
            return row;
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
            e.printStackTrace();
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
        ((Stage) cliFullNameInfoLBL.getScene().getWindow()).setMaxWidth(590.0);
        ((Stage) cliFullNameInfoLBL.getScene().getWindow()).setMinHeight(458.0);
    }

    @FXML
    void addClientClicked(ActionEvent event) throws IOException {
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
            e.printStackTrace();
        }
        GH.saveAllData();
    }

    @FXML
    void deleteClientClicked(ActionEvent event) throws IOException {
        boolean noSelection = clientsTBV.getSelectionModel().getSelectedItems().isEmpty();
        if (!noSelection) {
            for (int i = 0; i < clientsTBV.getSelectionModel().getSelectedItems().size(); i++) {
                Client removed = clientsTBV.getSelectionModel().getSelectedItems().get(i);
                GH.deleteClient(GH.clientIndexWithNameAndLastname(removed.getName(), removed.getLastname()));
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
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    @FXML
    void editAddressCli(CellEditEvent<Client, String> event) throws IOException {
        GH.changeClientAddress(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void editEnabledCli(CellEditEvent<Client, String> event) throws IOException {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableClient(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()));
        else
            GH.disableClient(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        GH.saveAllData();
    }

    @FXML
    void editIdCli(CellEditEvent<Client, String> event) throws IOException {
        GH.changeClientId(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void editLastNameCli(CellEditEvent<Client, String> event) throws IOException {
        GH.changeClientLastname(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void editNameCli(CellEditEvent<Client, String> event) throws IOException {
        GH.changeClientName(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void editObsCli(CellEditEvent<Client, String> event) throws IOException {
        GH.changeClientObservations(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void editTeleCli(CellEditEvent<Client, String> event) throws IOException {
        GH.changeClientPhoneNumber(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void searchPressed(ActionEvent event) {
        GH.setSearchResults(searchUserTF.getText().trim());
        ObservableList<Client> clientEmpty = FXCollections.emptyObservableList();
        clientsTBV.setItems(clientEmpty);
        nameCliCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        enabledCliCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        lastNameCliCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        idCliCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addressCliCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        teleCliCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        obsCliCol.setCellValueFactory(new PropertyValueFactory<>("observations"));
        ObservableList<Client> clientsList = FXCollections.observableArrayList(GH.getSearchResults());
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
            row.setOnContextMenuRequested(e -> {
                if (!row.isEmpty()) {
                    Client rowData = row.getItem();
                    fullClientDetails(rowData);
                }
            });
            return row;
        });
        String[] nameAndLastName = searchUserTF.getText().trim().split(" ");
        if(nameAndLastName.length<=1) {
        	searchTimeLBL.setText(Long.toString(GH.getTimeOfSearch()));
        }
        else {
        	GH.binarySearchForClients(GH.getRestaurantClients(),nameAndLastName[1],nameAndLastName[0]);
            searchTimeLBL.setText(Long.toString(GH.getTimeOfSearch()));
        }
    }

    @FXML
    void cancelClient(ActionEvent event) throws IOException {
        try {
            ((Stage) clientPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void confirmClient(ActionEvent event) throws IOException {
        String newName = newCliNameTF.getText();
        String newLastName = newCliLastNameTF.getText();
        String newAddress = newCliAddressTF.getText();
        String newId = newCliIDTF.getText();
        String newTelephone = newCliTeleTF.getText();
        String newObs = (newCliObsTA.getText().isEmpty()) ? "-" : newCliObsTA.getText();
        boolean canCreateClient = !newName.isEmpty() && !newLastName.isEmpty() && !newAddress.isEmpty() && !newId.isEmpty() && !newTelephone.isEmpty();
        if (canCreateClient) {
            boolean userCreated = GH.addAClientToTheRestaurant(newName, newLastName, newId, newAddress, newTelephone, newObs);
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
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    @FXML
    void exportClientData(ActionEvent event) throws FileNotFoundException {
        String fileName = "src/data/CLIENTS.csv";
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < GH.getRestaurantClientsSize(); i++) {
            Client client = GH.getRestaurantClients().get(i);
            pw.println(client.showInformation());
        }
        pw.close();
    }

    @FXML
    void importClientData(ActionEvent event) throws IOException {
        GH.importClientInformation("src/data/generatedData/CLIENT_MOCK_DATA.csv");
        initClientPane();
        GH.saveAllData();
    }

    @FXML
    void addTypeClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("typesGUI/add-type.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createIngredient = new Stage();
            createIngredient.setScene(new Scene(root));
            createIngredient.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createIngredient.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createIngredient.getIcons().add(icon);
            createIngredient.setTitle("Golden Restaurant: Añadir Tipo");
            createIngredient.setResizable(false);
            createIngredient.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
        GH.saveAllData();
    }

    @FXML
    void deleteTypeClicked(ActionEvent event) throws IOException {
        PlateType removed = typesTBV.getSelectionModel().getSelectedItems().get(0);
        if (removed == null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage error = new Stage();
                error.setScene(new Scene(root));
                error.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                error.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                error.getIcons().add(icon);
                error.setTitle("Error");
                deleteMessageLBL.setText("No hay selección. Intente de nuevo.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                error.setResizable(false);
                error.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                e.printStackTrace();
            }
        } else if (GH.deletePlateType(GH.plateTypeIndexWithplateType(removed))) {
            typesTBV.getItems().removeAll(typesTBV.getSelectionModel().getSelectedItems());
            typesTBV.refresh();
        } else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage error = new Stage();
                error.setScene(new Scene(root));
                error.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                error.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                error.getIcons().add(icon);
                error.setTitle("Error");
                deleteMessageLBL.setText("El tipo de plato no pudo ser eliminado.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                error.setResizable(false);
                error.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    @FXML
    void editEnabledType(CellEditEvent<PlateType, String> event) throws IOException {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enablePlateType(GH.plateTypeIndexWithName(event.getRowValue().getName()));
        else GH.disablePlateType(GH.plateTypeIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        GH.saveAllData();
    }

    @FXML
    void editNameType(CellEditEvent<PlateType, String> event) throws IOException {
        GH.changePlateTypeName(GH.plateTypeIndexWithName(event.getRowValue().getName()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        GH.saveAllData();
    }

    @FXML
    void cancelType(ActionEvent event) throws IOException {
        try {
            ((Stage) typePane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void confirmType(ActionEvent event) throws IOException {
        String newTypeName = newTypeNameTF.getText();
        if (!GH.addAPlateTypeToTheRestaurant(newTypeName)) {
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
                deleteMessageLBL.setText("El tipo no pudo ser agregado");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        } else {
            try {
                ((Stage) typePane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        initPlateTypePane();
        GH.saveAllData();
    }

    private void initPlateTypePane() {
        nameTypeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        enabledTypeCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));

        ObservableList<PlateType> typesList = FXCollections.observableArrayList(GH.getRestaurantPlateTypes());
        typesTBV.setItems(typesList);

        nameTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        typesTBV.setRowFactory(tv -> {
            TableRow<PlateType> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    PlateType rowData = row.getItem();
                    fullPLateTypeDetails(rowData);
                }
            });
            return row;
        });
    }

    private void fullPLateTypeDetails(PlateType rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("typesGUI/typeInfo.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage plateTypeInfo = new Stage();
            plateTypeInfo.setScene(new Scene(root));
            plateTypeInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            plateTypeInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            plateTypeInfo.getIcons().add(icon);
            plateTypeInfo.setTitle("Información de Ingrediente");
            initTypeInfo(rowData);
            plateTypeInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
    }

    private void initTypeInfo(PlateType rowData) {
        typeNameInfoLBL.setText(rowData.getName());
        typeEnabledInfoLBL.setText((rowData.getEnabled()) ? "Habilitado" : "Deshabilitado");
        typeCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        typeEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage) typeNameInfoLBL.getScene().getWindow()).setResizable(false);
    }

    private void initOrderPane() {
        queueBTN.setText("-");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusOrderCol.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        prodOrderCol.setCellValueFactory(new PropertyValueFactory<>("orderProductsString"));
        quantityOrderCol.setCellValueFactory(new PropertyValueFactory<>("productsQuantityString"));
        ordererCol.setCellValueFactory(new PropertyValueFactory<>("orderClientName"));
        serverCol.setCellValueFactory(new PropertyValueFactory<>("orderEmployeeName"));
        orderedDateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        obsOrderCol.setCellValueFactory(new PropertyValueFactory<>("observations"));

        ObservableList<Order> ordersList = FXCollections.observableArrayList(GH.getRestaurantOrders());
        orderTBV.setItems(ordersList);

        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        statusOrderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        prodOrderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        quantityOrderCol.setCellFactory(TextFieldTableCell.forTableColumn());
        ordererCol.setCellFactory(TextFieldTableCell.forTableColumn());
        serverCol.setCellFactory(TextFieldTableCell.forTableColumn());
        orderedDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        obsOrderCol.setCellFactory(TextFieldTableCell.forTableColumn());

        orderTBV.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    Order rowData = row.getItem();
                    fullOrderDetails(rowData);
                }
            });
            return row;
        });
    }

    private void fullOrderDetails(Order rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ordersGUI/orderInfo.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage orderInfo = new Stage();
            orderInfo.setScene(new Scene(root));
            orderInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            orderInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            orderInfo.getIcons().add(icon);
            orderInfo.setTitle("Información de Orden");
            initOrderInfo(rowData);
            orderInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
    }

    private void initOrderInfo(Order rowData) {
        orderCodeInfoLBL.setText(rowData.getName());
        orderEnabledInfoLBL.setText((rowData.getEnabled()) ? "Habilitado" : "Deshabilitado");
        orderDateInfoLBL.setText(rowData.getDateString());
        StringBuilder prodAndQuant = new StringBuilder();
        for (int i = 0, size = rowData.getOrderProducts().size(); i < size; i++) {
            String s = rowData.getOrderProducts().get(i).getName();
            int q = rowData.getProductsQuantity().get(i);
            prodAndQuant.append(s).append(" (").append(q).append(")\n");
        }
        orderProdInfoLBL.setText(prodAndQuant.toString());
        orderClientInfoLBL.setText(rowData.getOrderClientName());
        orderEmployeeInfoLBL.setText(rowData.getOrderEmployeeName());
        orderObservationsInfoLBL.setText(rowData.getObservations());
        orderCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        orderEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage) orderCodeInfoLBL.getScene().getWindow()).setResizable(false);
    }

    @FXML
    void createOrderClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ordersGUI/add-order.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createOrder = new Stage();
            createOrder.setScene(new Scene(root));
            createOrder.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createOrder.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createOrder.getIcons().add(icon);
            createOrder.setTitle("Golden Restaurant: Añadir orden");
            createOrder.setResizable(false);
            initAddOrderPane();
            createOrder.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
    }

    private void initAddOrderPane() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Product p : GH.getProductsWithTheirSizes()) items.add(p.getName() + " (" + p.getProductActualSize() + ")");
        selectedProductsLV.setItems(items);
        selectedProductsLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void cancelOrderClicked(ActionEvent event) throws IOException {
        try {
            ((Stage) orderPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void confirmOrder(ActionEvent event) throws IOException {
        ArrayList<Product> newProducts = new ArrayList<>();
        ObservableList<String> selectedProducts = selectedProductsLV.getSelectionModel().getSelectedItems();
        for (String selectedItem : selectedProducts) {
            String[] prodName = selectedItem.split("[()]");
            prodName[0]= prodName[0].substring(0, prodName[0].length()-1);
            Product prodtoadd = GH.getProductsWithTheirSizes().get(GH.productWithSizeIndexWithNameAndSize(prodName[0], prodName[1]));
            newProducts.add(prodtoadd);
        }
        ArrayList<Integer> newProdQuantities = new ArrayList<>();
        try {
            for (String s : newProdQuantitiesTA.getText().split(",")) {
                newProdQuantities.add(Integer.parseInt(s));
            }
        } catch (Exception e) {
            newProdQuantities = new ArrayList<>();
        }
        String observations = newOrderObsTA.getText();
        String[] clientNames = clientOrderTF.getText().split(",");
        int newClientIndex;
        try {
             newClientIndex = GH.clientIndexWithNameAndLastname(clientNames[0], clientNames[1]);
        } catch (Exception e) {
            newClientIndex = -1;
        }
        int newEmployeeIndex = GH.employeeIndexWithId(employeeOrderTF.getText());
        boolean canAdd = !newProducts.isEmpty() && !newProdQuantities.isEmpty() && newClientIndex != -1 && newEmployeeIndex != -1;
        if (canAdd) {
            Employee newEmployee = GH.getRestaurantEmployees().get(newEmployeeIndex);
            Client newClient = GH.getRestaurantClients().get(newClientIndex);
            GH.createAnOrder(newProducts,newProdQuantities,newClient,newEmployee,observations);
            initOrderPane();
        } else {
            try {
                ((Stage) orderPane.getScene().getWindow()).close();
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
                deleteMessageLBL.setText("No fue posible crear la orden.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    @FXML
    void hideProdInfo(MouseEvent event) {
        productsInfoLabel.setVisible(false);
    }

    @FXML
    void showProdInfo(MouseEvent event) {
        productsInfoLabel.setVisible(true);
    }

    @FXML
    void advanceStatusClicked(ActionEvent event) {
        System.out.println("idk");
    }

    @FXML
    void cancelStatusClicked(ActionEvent event) {
        System.out.println("tis me, mario");
    }

    @FXML
    void exportOrderData(ActionEvent event) throws IOException {
        GH.generateOrderReport("src/data/generatedData/ORDER_GENERATED_DATA.csv",SEPARATOR,START,LocalDateTime.now());
    }

    @FXML
    void importOrderData(ActionEvent event) throws IOException {
        GH.importProductInformation("src/data/generatedData/PRODUCT_GENERATED_DATA.csv");
        GH.importClientInformation("src/data/generatedData/CLIENT_MOCK_DATA.csv");
        GH.importOrderInformation("src/data/generatedData/ORDER_GENERATED_DATA.csv");
        initOrderPane();
        GH.saveAllData();
    }

    @FXML
    void addEmployeeClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personnelGUI/add-users.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createUsers = new Stage();
            createUsers.setScene(new Scene(root));
            createUsers.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createUsers.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createUsers.getIcons().add(icon);
            createUsers.setTitle("Golden Restaurant: Añadir Empleado");
            createUsers.setResizable(false);
            createUsers.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
        GH.saveAllData();
    }

    @FXML
    void changeUserDeetsClicked(ActionEvent event) throws IOException {
        if (!employeesTBV.getSelectionModel().getSelectedItems().isEmpty()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personnelGUI/user-passDeets.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage userDetails = new Stage();
                userDetails.setScene(new Scene(root));
                userDetails.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                userDetails.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                userDetails.getIcons().add(icon);
                userDetails.setTitle("Golden Restaurant: Cambiar Datos de Usuario");
                userDetails.setResizable(false);
                userDetails.show();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                e.printStackTrace();
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
                deleteMessageLBL.setText("No hay selección. Intente de nuevo.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        GH.saveAllData();
    }

    @FXML
    void deleteEmployeeClicked(ActionEvent event) throws IOException {
        boolean noSelection = employeesTBV.getSelectionModel().getSelectedItems().isEmpty();
        if (!noSelection) {
            for (int i = 0; i < employeesTBV.getSelectionModel().getSelectedItems().size(); i++) {
                User removed = employeesTBV.getSelectionModel().getSelectedItems().get(i);
                GH.deleteEmployee(GH.employeeIndexWithId(removed.getId()));
            }
            employeesTBV.getItems().removeAll(employeesTBV.getSelectionModel().getSelectedItems());
            initEmployeePane();
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
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    @FXML
    void cancelUserAndPass(ActionEvent event) throws IOException {
        try {
            ((Stage) editUser.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void saveUserAndPass(ActionEvent event) throws IOException {
        if (userNameTF.getText().equals(employeesTBV.getSelectionModel().getSelectedItem().getUsername())) {
            GH.changePassword(GH.userIndexWithUsername(employeesTBV.getSelectionModel().getSelectedItem().getUsername()),newPassPWF.getText());
            employeesTBV.getSelectionModel().getSelectedItem().setModifierUser(GH.getCurrentUser());
            try {
                ((Stage) editUser.getScene().getWindow()).close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage productInfo = new Stage();
                productInfo.setScene(new Scene(root));
                productInfo.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                productInfo.getIcons().add(icon);
                productInfo.setTitle("Datos guardados");
                deleteMessageLBL.setText("Datos guardados con éxito!");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        } else {
            try {
                ((Stage) editUser.getScene().getWindow()).close();
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
                deleteMessageLBL.setText("Datos erroneos.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        GH.saveAllData();
    }

    @FXML
    void editLastNameEmp(CellEditEvent<User, String> event) throws IOException {
        GH.changeEmployeeLastname(GH.employeeIndexWithId(event.getRowValue().getId()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();
        GH.saveAllData();
    }

    @FXML
    void editNameEmp(CellEditEvent<User, String> event) throws IOException {
        GH.changeEmployeeName(GH.employeeIndexWithId(event.getRowValue().getId()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();
        GH.saveAllData();
    }

    @FXML
    void editUsernameEmp(CellEditEvent<User, String> event) throws IOException {
        GH.changeUsername(GH.userIndexWithId(event.getRowValue().getId()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();
        GH.saveAllData();
    }

    @FXML
    void editEnabledEmp(CellEditEvent<User, String> event) throws IOException {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableUser(GH.userIndexWithUsername(event.getRowValue().getUsername()));
        else GH.disableUser(GH.userIndexWithUsername(event.getRowValue().getUsername()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        GH.saveAllData();
    }

    @FXML
    void editIdEmp(CellEditEvent<User, String> event) throws IOException {
        GH.changeEmployeeId(GH.employeeIndexWithId(event.getOldValue()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();
        GH.saveAllData();
    }

    @FXML
    void cancelEmployee(ActionEvent event) throws IOException {
        try {
            ((Stage) userPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
        GH.saveAllData();
    }

    @FXML
    void confirmEmployee(ActionEvent event) throws IOException {
        String newName = newEmpNameTF.getText();
        String newLastName = newEmpLastNameTF.getText();
        String newUserName = newUserNameTF.getText();
        String newId = newEmpIDTF.getText();
        String newPassword = userPWF.getText();
        boolean canCreateClient = !newName.isEmpty() && !newLastName.isEmpty() && !newUserName.isEmpty() && !newId.isEmpty() && !newPassword.isEmpty();
        if (canCreateClient) {
            boolean employeeCreated = GH.addAnEmployeeToTheRestaurant(newName,newLastName,newId);
            boolean userCreated = GH.initializeUser(GH.employeeIndexWithId(newId),newUserName,newPassword);
            initEmployeePane();
            try {
                ((Stage) userPane.getScene().getWindow()).close();
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
                deleteMessageLBL.setText("El empleado no pudo ser creado.");
                deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                productInfo.setResizable(false);
                productInfo.show();
                ((Stage) clientPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Can't load window at the moment.");
                e.printStackTrace();
            }
        }
        GH.saveAllData();
    }

    private void initEmployeePane() {
        nameEmpCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        enabledEmpCol.setCellValueFactory(new PropertyValueFactory<>("enabledString"));
        lastNameEmpCol.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        idEmpCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameEmpCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        ObservableList<User> userList = FXCollections.observableArrayList(GH.getRestaurantUsers());
        employeesTBV.setItems(userList);
        nameEmpCol.setCellFactory(TextFieldTableCell.forTableColumn());
        enabledEmpCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idEmpCol.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameEmpCol.setCellFactory(TextFieldTableCell.forTableColumn());

        employeesTBV.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    User rowData = row.getItem();
                    fullUserDetails(rowData);
                }
            });
            return row;
        });
    }

    private void fullUserDetails(User rowData) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personnelGUI/userInfo.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage userInfo = new Stage();
            userInfo.setScene(new Scene(root));
            userInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            userInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            userInfo.getIcons().add(icon);
            userInfo.setTitle("Información de Usuario");
            initUserInfo(rowData);
            userInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
    }

    private void initUserInfo(User rowData) {
        empFullNameInfoLBL.setText(rowData.getName() + " " + rowData.getLastname());
        empEnabledInfoLBL.setText(rowData.getEnabled() ? "(Habilitado)":"(Deshabilitado)");
        empDInfoLBL.setText(rowData.getId());
        empUserInfoLBL.setText(rowData.getUsername());
    }

    @FXML
    void loginPressed(ActionEvent event) {
        switch (GH.login(userNameLoginTF.getText(),passwordLoginPF.getText())) {
            case -1:
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
                    deleteMessageLBL.setText("Datos erroneos. Intente de nuevo.");
                    deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                    productInfo.setResizable(false);
                    productInfo.show();
                    //((Stage) loginPaneOrsmnt.getScene().getWindow()).close();
                } catch (Exception e) {
                    System.out.println("Can't load window at the moment.");
                    e.printStackTrace();
                }
                break;
            case -2:
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
                    productInfo.setTitle("Advertencia");
                    deleteMessageLBL.setText("El uso del usuario root no es recomendado. Proceder con precaución.");
                    deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                    productInfo.setResizable(false);
                    productInfo.show();
                    //((Stage) loginPaneOrsmnt.getScene().getWindow()).close();
                } catch (Exception e) {
                    System.out.println("Can't load window at the moment.");
                    e.printStackTrace();
                }
                loginPaneOrsmnt.setVisible(false);
                break;
            default:
                GH.setCurrentUser(GH.getRestaurantUsers().get(GH.userIndexWithUsername(userNameLoginTF.getText())));
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
                    productInfo.setTitle("Bienvenido");
                    deleteMessageLBL.setText("Inicio de sesión correcto. Bienvenid@!\nPresione el ícono de la casa para comenzar.");
                    deleteMessageLBL.setStyle("\n-fx-font-style: italic;");
                    productInfo.setResizable(false);
                    productInfo.show();
                    //((Stage) loginPaneOrsmnt.getScene().getWindow()).close();
                } catch (Exception e) {
                    System.out.println("Can't load window at the moment.");  //-_-
                    e.printStackTrace();
                }
                loginPaneOrsmnt.setVisible(false);
        }
    }

    @FXML
    void registerPressed(ActionEvent event) throws IOException {
        addEmployeeClicked(event);
    }

    @FXML
    void genEmpReport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("reportGen.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage productInfo = new Stage();
            productInfo.setScene(new Scene(root));
            productInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            productInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            productInfo.getIcons().add(icon);
            productInfo.setResizable(false);
            productInfo.show();
        } catch (Exception e) {
            System.out.println("Can't load window at the moment.");
            e.printStackTrace();
        }
    }

    @FXML
    void confirmReport(ActionEvent event) {
        LocalDateTime startingDate;
        LocalDateTime endingDate;
    }
}

