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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import model.*;
import java.io.*;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CenterPanesGUIController implements Initializable {

    private static final String SEPARATOR = ";";
    private static final LocalDateTime START = LocalDateTime.of(Year.now().getValue(), YearMonth.now().getMonth(), MonthDay.now().getDayOfMonth(), 0, 0, 0);
    private boolean loginSuccessful;

    /*Delete Pane*/

    @FXML
    private Label errorLBL;

    /*Order Fields*/

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

    @FXML
    private Button cancelOrderBTN = new Button();

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

    /*Product Fields*/

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

    /*Ingredients Fields*/

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

    /*Types Fields*/

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

    @FXML
    private Label currentUserLBL;

    /*Reports Pane*/

    @FXML
    private BorderPane reportPane;

    @FXML
    private Label destinationLBL;

    @FXML
    private DatePicker startDate = new DatePicker();

    @FXML
    private DatePicker endDate = new DatePicker();

    @FXML
    private TextField startTimeTB;

    @FXML
    private TextField endDateTB;

    @FXML
    private Label dateTitLBL = new Label();

    @FXML
    private Label timeTitLBL = new Label();

    @FXML
    private ChoiceBox<String> reportCBX = new ChoiceBox<>();

    private Restaurant GH;
    private final Tooltip tableViewTTP;
    private final Tooltip listViewTTP;

    public CenterPanesGUIController(Restaurant GH) {
        this.GH = GH;
        loginSuccessful =false;
        tableViewTTP = new Tooltip("Un click para SELECCIONAR\nDoble click para EDITAR\nClick derecho para DETALLES\nCTRL + Click sobre seleccion para deseleccionar");
        listViewTTP = new Tooltip("Presione CTRL + Click para seleccionar varios elementos,\no para deseleccionar cada elemento");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProductPane();
        initIngredientPane();
        initClientPane();
        initPlateTypePane();
        initOrderPane();
        initEmployeePane();
        initReportsPane();
        initLogin();
    }

    public void launchError(String message, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("error.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage errorPane = new Stage();
            errorPane.setScene(new Scene(root));
            errorPane.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            errorPane.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            errorPane.getIcons().add(icon);
            errorPane.setTitle(title);
            errorLBL.setText(message);
            errorLBL.setStyle("\n-fx-font-style: italic;");
            errorPane.setResizable(false);
            errorPane.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.printStackTrace();
        }
    }

    /***************************ORDER PANE***************************/

    private void initOrderPane() {
        orderTBV.setTooltip(tableViewTTP);
        queueBTN.setText("-");
        queueBTN.setTooltip(new Tooltip("(Avanzar (>>) estado de la orden a: "));
        orderTBV.setOnMouseClicked(event -> {
            if (!orderTBV.getSelectionModel().getSelectedItems().isEmpty()) {
                int current = orderTBV.getSelectionModel().getSelectedItem().getStatusIndicator();
                if (current != 0 && current != 4) {
                    queueBTN.setText(">>" + Status.get(current + 1));
                    queueBTN.setDisable(false);
                    cancelOrderBTN.setDisable(false);
                } else {
                    queueBTN.setText(Status.get(current));
                    queueBTN.setDisable(true);
                    cancelOrderBTN.setDisable(true);
                    if (current == 0) cancelOrderBTN.setDisable(true);
                }
            } else {
                queueBTN.setText("-");
                queueBTN.setDisable(true);
            }
        });
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
            orderInfo.setTitle("Información de la Orden");
            initOrderInfo(rowData);
            orderInfo.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void initOrderInfo(Order rowData) {
        orderCodeInfoLBL.setText(rowData.getName());
        orderEnabledInfoLBL.setText((rowData.getEnabled()) ? "Habilitado" : "Deshabilitado");
        orderEnabledInfoLBL.setId("enabled-label");
        orderStatusInfoLBL.setText(Status.get(rowData.getStatusIndicator()));
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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void initAddOrderPane() {
        clientOrderTF.setTooltip(new Tooltip("Separar NOMBRE(S) de APELLIDO(S) con \",\""));
        selectedProductsLV.setTooltip(listViewTTP);
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Product p : GH.getProductsWithTheirSizes()) items.add(p.getName() + " (" + p.getProductActualSize() + ")");
        selectedProductsLV.setItems(items);
        selectedProductsLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void cancelOrderClicked(ActionEvent event)  {
        try {
            ((Stage) orderPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

    }

    @FXML
    void confirmOrder(ActionEvent event)  {
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
        String[] clientNames = clientOrderTF.getText().trim().split(",");
        int newClientIndex;
        try {
            newClientIndex = GH.clientIndexWithNameAndLastname(clientNames[0], clientNames[1]);
        } catch (Exception e) {
            newClientIndex = -1;
        }
        int newEmployeeIndex = GH.employeeIndexWithId(employeeOrderTF.getText().trim());
        boolean canAdd = !newProducts.isEmpty() && !newProdQuantities.isEmpty() && newClientIndex != -1 && newEmployeeIndex != -1;
        if (canAdd) {
            Employee newEmployee = GH.getRestaurantEmployees().get(newEmployeeIndex);
            Client newClient = GH.getRestaurantClients().get(newClientIndex);
            GH.createAnOrder(newProducts,newProdQuantities,newClient,newEmployee,observations);
            initOrderPane();
            try {
                ((Stage) (clientOrderTF.getScene().getWindow())).close();
                launchError("Orden creada con exito!", "Orden creada");
            } catch (Exception ignored) {}
        } else launchError("No fue posible crear la orden.", "Error");

    }

    @FXML
    void advanceStatusClicked(ActionEvent event) {
        if (!orderTBV.getSelectionModel().getSelectedItems().isEmpty()) {
            GH.changeOrderStatus(GH.searchAnOrder(orderTBV.getSelectionModel().getSelectedItem().getName()),1);
        }
        if (orderTBV.getSelectionModel().getSelectedItem().getStatusIndicator() == 4) {
            cancelOrderBTN.setDisable(true);
        }
        initOrderPane();
    }

    @FXML
    void cancelStatusClicked(ActionEvent event) {
        GH.changeOrderStatus(GH.searchAnOrder(orderTBV.getSelectionModel().getSelectedItem().getName()),0);
        queueBTN.setDisable(true);
        cancelOrderBTN.setDisable(true);
        initOrderPane();
    }

    @FXML
    void exportOrderData(ActionEvent event) throws IOException {
        GH.generateOrderReport("src/data/reports/ORDER_REPORT.csv",SEPARATOR,START,LocalDateTime.now());
    }

    @FXML
    void importOrderData(ActionEvent event) throws IOException {
        GH.importProductInformation("src/data/generatedData/PRODUCT_GENERATED_DATA.csv");
        GH.importClientInformation("src/data/generatedData/CLIENT_MOCK_DATA.csv");
        GH.importOrderInformation("src/data/generatedData/ORDER_GENERATED_DATA.csv");
        initOrderPane();
    }

    /*****************PRODUCTS*******************/

    private void initProductPane() {
        productTBV.setTooltip(tableViewTTP);
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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
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
        ((Stage) prodNameInfoLBL.getScene().getWindow()).setMaxWidth(520.0);
        ((Stage) prodNameInfoLBL.getScene().getWindow()).setMinHeight(520.0);
    }

    @FXML
    void addProductCLicked(ActionEvent event)  {
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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void initAddProductPane() {
        ObservableList<String> items = FXCollections.observableArrayList();
        for (Ingredient ing : GH.getRestaurantIngredients()) items.add(ing.getName());
        selectedItemsLV.setItems(items);
        selectedItemsLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectedItemsLV.setTooltip(listViewTTP);
    }

    @FXML
    void cancelProduct(ActionEvent event)  {
        try {
            ((Stage) productPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmProduct(ActionEvent event)  {
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
        } else launchError("No fue posible agregar el producto.","Error");
    }

    @FXML
    void editNameProd(CellEditEvent<Product, String> event) {
        GH.changeProductName(GH.productIndexWithName(event.getRowValue().getName()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
    }

    @FXML
    void editEnabledProd(CellEditEvent<Product, String> event)  {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableProduct(GH.productIndexWithName(event.getRowValue().getName()));
        else GH.disableProduct(GH.productIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initProductPane();
    }

    @FXML
    void editIngredientProd(CellEditEvent<Product, String> event)  {
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
            launchError("Los siguientes ingredientes no existen en el restaurante:\n" + unAddedIngredients.toString(),"Error");
        }
        initProductPane();
    }

    @FXML
    void editTypeProd(CellEditEvent<Product, String> event)  {
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
    }

    @FXML
    void editSizesProd(CellEditEvent<Product, String> event)  {
        GH.changeSizeOfTheProduct(GH.productIndexWithName(event.getRowValue().getName()),event.getRowValue().getProductActualSize(),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editPricesProd(CellEditEvent<Product, Double> event)  {
        Double db = event.getNewValue();
        GH.changePriceOfTheProduct(GH.productIndexWithName(event.getRowValue().getName()),event.getRowValue().getProductActualSize(),db);
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void sortProducts(ActionEvent event) {
        GH.sortProductsByPrice();
        initProductPane();
    }

    @FXML
    void deleteProductClicked(ActionEvent event)  {
        boolean noSelection = productTBV.getSelectionModel().getSelectedItems().isEmpty();
        if (!noSelection) {
            for (int i = 0; i < productTBV.getSelectionModel().getSelectedItems().size(); i++) {
                Product removed = productTBV.getSelectionModel().getSelectedItems().get(i);
                GH.deleteProduct(GH.productIndexWithName(removed.getName()));
            }
            productTBV.getItems().removeAll(productTBV.getSelectionModel().getSelectedItems());
            initProductPane();
        } else launchError("No hay selección. Intente de nuevo.", "Error");
    }

    @FXML
    void exportProductData(ActionEvent event) throws Exception {
        GH.generateProductReport("src/data/reports/PRODUCT_REPORT.csv", SEPARATOR, START, LocalDateTime.now());
    }

    @FXML
    void importProductData(ActionEvent event) throws IOException {
        GH.importProductInformation("src/data/generatedData/PRODUCT_GENERATED_DATA.csv");
        initProductPane();
    }

    /******************INGREDIENTS PANE**********************/

    private void initIngredientPane() {
        ingredientsTBV.setTooltip(tableViewTTP);
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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
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

    @FXML
    void addIngredientCLicked(ActionEvent event)  {
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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    @FXML
    void cancelIngredient(ActionEvent event)  {
        try {
            ((Stage) ingredientPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmIngredient(ActionEvent event)  {
        String newIngName = newIngNameTF.getText();
        if (!GH.addAnIngredientToTheRestaurant(newIngName)) launchError("El ingrediente no pudo ser agregado.", "Error");
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
    void editEnabledIng(CellEditEvent<Ingredient, String> event)  {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableIngredient(GH.ingredientIndexWithName(event.getRowValue().getName()));
        else GH.disableIngredient(GH.ingredientIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initIngredientPane();
    }

    @FXML
    void editNameIng(CellEditEvent<Ingredient, String> event)  {
        GH.changeIngredientName(GH.ingredientIndexWithName(event.getRowValue().getName()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initIngredientPane();
    }

    @FXML
    void sortIngredients(ActionEvent event) {
        GH.descendantSortIngredients();
        initIngredientPane();
    }

    @FXML
    void deleteIngredientClicked(ActionEvent event)  {
        Ingredient removed = ingredientsTBV.getSelectionModel().getSelectedItems().get(0);
        if (removed == null) launchError("No hay selección. Intente de nuevo.", "Error");
        else if (GH.deleteIngredient(GH.ingredientIndexWithIngredient(removed))) {
            ingredientsTBV.getItems().removeAll(ingredientsTBV.getSelectionModel().getSelectedItems());
            ingredientsTBV.refresh();
        } else launchError("El ingrediente no pudo ser eliminado.", "Error");
    }

    @FXML
    void exportIngredientData(ActionEvent event) throws FileNotFoundException {
        String fileName = "src/data/otherExports/INGREDIENTS.csv";
        PrintWriter pw = new PrintWriter(fileName);
        for (int i = 0; i < GH.getRestaurantIngredientsSize(); i++) {
            Ingredient ing = GH.getRestaurantIngredients().get(i);
            pw.println(ing.getName());
        }
        pw.close();
    }

    @FXML
    void importIngredientData(ActionEvent event) throws IOException {
        String fileName = "src/data/OtherExports/INGREDIENTS.csv";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line = br.readLine();
        while (line != null) {
            GH.addAnIngredientToTheRestaurant(line);
            line = br.readLine();
        }
        br.close();
        initIngredientPane();
    }

    /*********************TYPES PANE***********************/

    private void initPlateTypePane() {
        typesTBV.setTooltip(tableViewTTP);
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
            plateTypeInfo.setTitle("Información del Plato");
            initTypeInfo(rowData);
            plateTypeInfo.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void initTypeInfo(PlateType rowData) {
        typeNameInfoLBL.setText(rowData.getName());
        typeEnabledInfoLBL.setText((rowData.getEnabled()) ? "Habilitado" : "Deshabilitado");
        typeEnabledInfoLBL.setId("enabled-label");
        typeCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        typeEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
        ((Stage) typeNameInfoLBL.getScene().getWindow()).setResizable(false);
    }

    @FXML
    void addTypeClicked(ActionEvent event)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("typesGUI/add-type.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage createType = new Stage();
            createType.setScene(new Scene(root));
            createType.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            createType.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            createType.getIcons().add(icon);
            createType.setTitle("Golden Restaurant: Añadir Tipo");
            createType.setResizable(false);
            createType.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    @FXML
    void cancelType(ActionEvent event)  {
        try {
            ((Stage) typePane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmType(ActionEvent event)  {
        String newTypeName = newTypeNameTF.getText();
        if (!GH.addAPlateTypeToTheRestaurant(newTypeName)) launchError("El tipo no pudo ser agregado", "Error");
        else {
            try {
                ((Stage) typePane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
        initPlateTypePane();
    }

    @FXML
    void editEnabledType(CellEditEvent<PlateType, String> event)  {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enablePlateType(GH.plateTypeIndexWithName(event.getRowValue().getName()));
        else GH.disablePlateType(GH.plateTypeIndexWithName(event.getRowValue().getName()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editNameType(CellEditEvent<PlateType, String> event)  {
        GH.changePlateTypeName(GH.plateTypeIndexWithName(event.getRowValue().getName()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void deleteTypeClicked(ActionEvent event)  {
        PlateType removed = typesTBV.getSelectionModel().getSelectedItems().get(0);
        if (removed == null) launchError("No hay selección. Intente de nuevo.", "Error");
        else if (GH.deletePlateType(GH.plateTypeIndexWithPlateType(removed))) typesTBV.getItems().removeAll(typesTBV.getSelectionModel().getSelectedItems());
        else launchError("El tipo de plato no pudo ser eliminado.", "Error");
        typesTBV.refresh();
    }

    /***********************CLIENTS PANE****************************/

    private void initClientPane() {
        clientsTBV.setTooltip(tableViewTTP);
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
            Stage clientInfo = new Stage();
            clientInfo.setScene(new Scene(root));
            clientInfo.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            clientInfo.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            clientInfo.getIcons().add(icon);
            clientInfo.setTitle("Información de Cliente");
            initClientInfo(rowData);
            clientInfo.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void initClientInfo(Client rowData) {
        cliFullNameInfoLBL.setText(rowData.getName() + " " + rowData.getLastname());
        cliEnabledInfoLBL.setText((rowData.getEnabled()) ? "(Habilitado)" : "(Deshabilitado)");
        cliEnabledInfoLBL.setId("enabled-label");
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
    void addClientClicked(ActionEvent event)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clientsGUI/add-client.fxml"));
            fxmlLoader.setController(this);
            Parent root = fxmlLoader.load();
            Stage addClient = new Stage();
            addClient.setScene(new Scene(root));
            addClient.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
            addClient.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
            addClient.getIcons().add(icon);
            addClient.setTitle("Golden Restaurant: Añadir Cliente");
            addClient.setResizable(false);
            addClient.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    @FXML
    void cancelClient(ActionEvent event)  {
        try {
            ((Stage) clientPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }
    }

    @FXML
    void confirmClient(ActionEvent event)  {
        String newName = newCliNameTF.getText();
        String newLastName = newCliLastNameTF.getText();
        String newAddress = newCliAddressTF.getText();
        String newId = newCliIDTF.getText();
        String newTelephone = newCliTeleTF.getText();
        String newObs = (newCliObsTA.getText().isEmpty()) ? "-" : newCliObsTA.getText();
        boolean canCreateClient = !newName.isEmpty() && !newLastName.isEmpty() && !newAddress.isEmpty() && !newId.isEmpty() && !newTelephone.isEmpty();
        if (canCreateClient) {
            GH.addAClientToTheRestaurant(newName, newLastName, newId, newAddress, newTelephone, newObs);
            initClientPane();
            try {
                ((Stage) clientPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        } else {
            launchError("El cliente no pudo ser creado.","Error");
        }
    }

    @FXML
    void editAddressCli(CellEditEvent<Client, String> event)  {
        GH.changeClientAddress(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editEnabledCli(CellEditEvent<Client, String> event)  {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableClient(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()));
        else
            GH.disableClient(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());
    }

    @FXML
    void editIdCli(CellEditEvent<Client, String> event)  {
        GH.changeClientId(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editLastNameCli(CellEditEvent<Client, String> event)  {
        GH.changeClientLastname(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editNameCli(CellEditEvent<Client, String> event)  {
        GH.changeClientName(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editObsCli(CellEditEvent<Client, String> event)  {
        GH.changeClientObservations(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
    }

    @FXML
    void editTeleCli(CellEditEvent<Client, String> event)  {
        GH.changeClientPhoneNumber(GH.clientIndexWithNameAndLastname(event.getRowValue().getName(), event.getRowValue().getLastname()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initClientPane();
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
        if (nameAndLastName.length > 1) {
            GH.binarySearchForClients(GH.getRestaurantClients(), nameAndLastName[1], nameAndLastName[0]);
        }
        searchTimeLBL.setText(Long.toString(GH.getTimeOfSearch()));
    }

    @FXML
    void deleteClientClicked(ActionEvent event)  {
        boolean noSelection = clientsTBV.getSelectionModel().getSelectedItems().isEmpty();
        boolean canDelete = true;
        if (!noSelection) {
            for (int i = 0; i < clientsTBV.getSelectionModel().getSelectedItems().size(); i++) {
                Client removed = clientsTBV.getSelectionModel().getSelectedItems().get(i);
                canDelete=GH.deleteClient(GH.clientIndexWithNameAndLastname(removed.getName(), removed.getLastname()));
            }
            if (canDelete) {
                clientsTBV.getItems().removeAll(clientsTBV.getSelectionModel().getSelectedItems());
                initProductPane();
            } else launchError("El cliente no pudo ser eliminado", "Error");


        } else launchError("No hay selección. Intente de nuevo.", "Error");
    }

    @FXML
    void exportClientData(ActionEvent event) throws FileNotFoundException {
        String fileName = "src/data/otherExports/CLIENTS.csv";
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
    }

    /******************************EMPLOYEES PANE*********************************/

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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void initUserInfo(User rowData) {
        empFullNameInfoLBL.setText(rowData.getName() + " " + rowData.getLastname());
        empEnabledInfoLBL.setText(rowData.getEnabled() ? "(Habilitado)":"(Deshabilitado)");
        empEnabledInfoLBL.setId("enabled-label");
        empDInfoLBL.setText(rowData.getId());
        empUserInfoLBL.setText(rowData.getUsername());
        empCreatorInfoLBL.setText(rowData.getCreatorUser().getUsername());
        empEditorInfoLBL.setText(rowData.getModifierUser().getUsername());
    }

    @FXML
    void addEmployeeClicked(ActionEvent event)  {
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
            createUsers.setTitle("Golden Restaurant: Añadir Usuario");
            createUsers.setResizable(false);
            createUsers.show();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    @FXML
    void cancelEmployee(ActionEvent event)  {
        try {
            ((Stage) userPane.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

    }

    @FXML
    void confirmEmployee(ActionEvent event) {
        String newName = newEmpNameTF.getText();
        String newLastName = newEmpLastNameTF.getText();
        String newUserName = newUserNameTF.getText();
        String newId = newEmpIDTF.getText();
        String newPassword = userPWF.getText();
        boolean canCreateClient = !newName.isEmpty() && !newLastName.isEmpty() && !newUserName.isEmpty() && !newId.isEmpty() && !newPassword.isEmpty();
        if (canCreateClient) {
            GH.addAnEmployeeToTheRestaurant(newName,newLastName,newId);
            GH.initializeUser(GH.employeeIndexWithId(newId),newUserName,newPassword);
            initEmployeePane();
            try {
                ((Stage) userPane.getScene().getWindow()).close();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        } else launchError("El empleado no pudo ser creado.", "Error");
    }

    @FXML
    void changeUserDeetsClicked(ActionEvent event)  {
        if (!employeesTBV.getSelectionModel().getSelectedItems().isEmpty()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personnelGUI/user-passDeets.fxml"));
                fxmlLoader.setController(this);
                Parent root = fxmlLoader.load();
                Stage userCredentials = new Stage();
                userCredentials.setScene(new Scene(root));
                userCredentials.initModality(Modality.APPLICATION_MODAL);
                Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
                userCredentials.getScene().getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
                userCredentials.getIcons().add(icon);
                userCredentials.setTitle("Golden Restaurant: Cambiar credenciales de Usuario");
                userCredentials.setResizable(false);
                userCredentials.show();
            } catch (Exception e) {
                System.out.println("Something went wrong.");
                e.fillInStackTrace();
            }
        } else launchError("No hay selección. Intente de nuevo.", "Error");
    }

    @FXML
    void cancelUserAndPass(ActionEvent event)  {
        try {
            ((Stage) editUser.getScene().getWindow()).close();
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

    }

    @FXML
    void saveUserAndPass(ActionEvent event)  {
        if (userNameTF.getText().equals(employeesTBV.getSelectionModel().getSelectedItem().getUsername())) {
            GH.changePassword(GH.userIndexWithUsername(employeesTBV.getSelectionModel().getSelectedItem().getUsername()),newPassPWF.getText());
            employeesTBV.getSelectionModel().getSelectedItem().setModifierUser(GH.getCurrentUser());
            launchError("Datos guardados con éxito!", "Datos guardados");
        } else launchError("Datos erroneos.", "Error");

    }

    @FXML
    void editLastNameEmp(CellEditEvent<User, String> event)  {
        GH.changeEmployeeLastname(GH.employeeIndexWithId(event.getRowValue().getId()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();

    }

    @FXML
    void editNameEmp(CellEditEvent<User, String> event)  {
        GH.changeEmployeeName(GH.employeeIndexWithId(event.getRowValue().getId()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();

    }

    @FXML
    void editUsernameEmp(CellEditEvent<User, String> event)  {
        GH.changeUsername(GH.userIndexWithId(event.getRowValue().getId()),event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();

    }

    @FXML
    void editEnabledEmp(CellEditEvent<User, String> event)  {
        if (event.getNewValue().equalsIgnoreCase("SI"))
            GH.enableUser(GH.userIndexWithUsername(event.getRowValue().getUsername()));
        else GH.disableUser(GH.userIndexWithUsername(event.getRowValue().getUsername()));
        event.getRowValue().setModifierUser(GH.getCurrentUser());

    }

    @FXML
    void editIdEmp(CellEditEvent<User, String> event)  {
        GH.changeEmployeeId(GH.employeeIndexWithId(event.getOldValue()), event.getNewValue());
        event.getRowValue().setModifierUser(GH.getCurrentUser());
        initEmployeePane();

    }

    @FXML
    void deleteEmployeeClicked(ActionEvent event)  {
        boolean noSelection = employeesTBV.getSelectionModel().getSelectedItems().isEmpty();
        if (!noSelection) {
            for (int i = 0; i < employeesTBV.getSelectionModel().getSelectedItems().size(); i++) {
                User removed = employeesTBV.getSelectionModel().getSelectedItems().get(i);
                GH.deleteEmployee(GH.employeeIndexWithId(removed.getId()));
            }
            employeesTBV.getItems().removeAll(employeesTBV.getSelectionModel().getSelectedItems());
            initEmployeePane();
        } else launchError("No hay selección. Intente de nuevo.", "Error");
    }

    /**********LOGIN*********/

    private void initLogin() {
        try {
            if (!GH.getCurrentUser().getUsername().equals("Root"))
                currentUserLBL.setText("Usuario actual: " + GH.getCurrentUser().getUsername());
            else if (GH.checkFirstTime()) currentUserLBL.setText("Por favor iniciar sesión");
            else {
                currentUserLBL.setText("Usuario actual: Administrador");
                currentUserLBL.setStyle("\n-fx-text-fill: #de260d;");
            }
        } catch (NullPointerException npe) {
            npe.fillInStackTrace();
        }
    }

    @FXML
    void loginPressed(ActionEvent event) {
        switch (GH.login(userNameLoginTF.getText(),passwordLoginPF.getText())) {
            case -1:
                launchError("Datos erroneos o incompletos. Intente de nuevo.", "Error");
                break;
            case -2:
                launchError("El uso del usuario root no es recomendado. Proceder con precaución.\nPresione el ícono de la casa para comenzar. ", "Advertencia");
                loginSuccessful = true;
                break;
            default:
                GH.setCurrentUser(GH.getRestaurantUsers().get(GH.userIndexWithUsername(userNameLoginTF.getText())));
                launchError("Inicio de sesión correcto. Bienvenid@!\nPresione el ícono de la casa para comenzar.", "Bienvenid@");
                loginSuccessful = true;
                currentUserLBL.setText("Usuario actual: " + GH.getCurrentUser().getUsername());
                initLogin();
                break;
        }
    }

    public boolean getLoginSuccessful() {
        return loginSuccessful;
    }

    public String getCurrentUser() throws NullPointerException {
        return GH.getCurrentUser().getUsername();
    }

    @FXML
    void registerPressed(ActionEvent event) {
        addEmployeeClicked(event);
    }

    /*************************REPORTS****************************/

    public void initReportsPane() {

        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if(localDate == null) return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if(dateString == null || dateString.trim().isEmpty()) return null;
                return LocalDate.parse(dateString,dateTimeFormatter);
            }
        };

        startDate.setConverter(converter);
        endDate.setConverter(converter);
        dateTitLBL.setId("reports-titles");
        timeTitLBL.setId("reports-titles");
        ObservableList<String> reports = FXCollections.observableArrayList("Empleados","Ordenes","Productos");
        reportCBX.setItems(reports);
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
            System.out.println("Something went wrong.");
            e.fillInStackTrace();
        }
    }

    private void generate(String type, LocalDateTime start, LocalDateTime end) throws NullPointerException, FileNotFoundException {
        String file;
        switch (type) {
            case "Empleados":
                file = "src/data/reports/EMPLOYEE_REPORT.csv";
                GH.generateEmployeeReport(file,";",start,end);
                break;
            case "Ordenes":
                file = "src/data/reports/ORDER_REPORT.csv";
                GH.generateOrderReport(file,";",start,end);
                break;
            case "Productos":
                file = "src/data/reports/PRODUCT_REPORT.csv";
                GH.generateProductReport(file,";",start,end);
                break;
            default:
                throw new NullPointerException("The string is either out of range. " + type);
        }
    }

    private String fileLoc(String file) {
        switch (file) {
            case "Empleados":
                return "src/data/reports/EMPLOYEE_REPORT.csv";
            case "Ordenes":
                return "src/data/reports/ORDER_REPORT.csv";
            case "Productos":
                return "src/data/reports/PRODUCT_REPORT.csv";
            default:
                return "No hay selección";
        }
    }

    @FXML
    void confirmReport(ActionEvent event) {
        String[] stParts = startDate.getValue().toString().split("-");
        String[] endParts = endDate.getValue().toString().split("-");
        String starto = stParts[2] + "/" + stParts[1] + "/" + stParts[0] + " " + startTimeTB.getText();
        String endo = endParts[2] + "/" + endParts[1] + "/" + endParts[0] + " " + endDateTB.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime startingDate = LocalDateTime.parse(starto, formatter);
        LocalDateTime endingDate = LocalDateTime.parse(endo, formatter);
        String message;
        String title;
        String file = reportCBX.getSelectionModel().getSelectedItem().trim();
        try {
            generate(file,startingDate,endingDate);
            title = "Reporte generado";
            message = "El reporte fue generado con exito (Guarado en:\n" + fileLoc(file);
            launchError(message, title);
        } catch (FileNotFoundException | NullPointerException fnf) {
            fnf.fillInStackTrace();
            title = "Error";
            message = "No se pudo generar el reporte";
            launchError(message, title);
        }
    }
}