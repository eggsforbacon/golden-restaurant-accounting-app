package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class Restaurant{
	
	public final static String PRODUCT_SAVE_PATH_FILE = "data/savedfiles/product.z&1";
	public final static String INGREDIENT_SAVE_PATH_FILE = "data/savedfiles/ingredient.z&1";
	public final static String PLATETYPE_SAVE_PATH_FILE = "data/savedfiles/platetype.z&1";
	public final static String CLIENT_SAVE_PATH_FILE = "data/savedfiles/client.z&1";
	public final static String EMPLOYEE_SAVE_PATH_FILE = "data/savedfiles/employee.z&1";
	public final static String USER_SAVE_PATH_FILE = "data/savedfiles/user.z&1";
	public final static String ORDER_SAVE_PATH_FILE = "data/savedfiles/order.z&1";
	
	private User rootUser;
	private boolean firstTime;
	private ArrayList<User> restaurantUsers;
	private int restaurantUsersSize;
	private User currentUser;
	private ArrayList<Product> restaurantProducts;
	private ArrayList<Product> productsWithTheirSizes;
	private int restaurantProductsSize;
	private int productsWithTheirSizesSize;
	private ArrayList<Ingredient> restaurantIngredients;
	private int restaurantIngredientsSize;
	private ArrayList<PlateType> restaurantPlateTypes;
	private int restaurantPlateTypesSize;
	private ArrayList<Client> restaurantClients;
	private int restaurantClientsSize;
	private long timeOfSearch;
	private ArrayList<Order> restaurantOrders;
	private int restaurantOrdersSize;
	private ArrayList<Employee> restaurantEmployees;
	private int restaurantEmployeesSize;
	private ArrayList<String> orderIDs;
	public Restaurant() {
		rootUser = new User("Generic",null,"user","none","Root","admin");
		firstTime = true;
		currentUser = rootUser;
		restaurantUsers = new ArrayList<User>();
		restaurantUsersSize=restaurantUsers.size();
		restaurantProducts = new ArrayList<Product>();
		productsWithTheirSizes = new ArrayList<Product>();
		restaurantProductsSize = restaurantProducts.size();
		productsWithTheirSizesSize=0;
		restaurantIngredients = new ArrayList<Ingredient>();
		restaurantIngredientsSize=restaurantIngredients.size();
		restaurantPlateTypes = new ArrayList<PlateType>();
		PlateType mainDish = new PlateType("Main dish",currentUser);
		PlateType sideDish = new PlateType("Side dish",currentUser);
		PlateType drink = new PlateType("Drink",currentUser);
		restaurantPlateTypes.add(mainDish);
		restaurantPlateTypes.add(sideDish);
		restaurantPlateTypes.add(drink);
		restaurantPlateTypesSize=restaurantPlateTypes.size();
		restaurantClients = new ArrayList<Client>();
		restaurantClientsSize = restaurantClients.size();
		timeOfSearch=0;
		restaurantOrders=new ArrayList<Order>();
		restaurantOrdersSize=restaurantOrders.size();
		restaurantEmployees = new ArrayList<Employee>();
		restaurantEmployeesSize = restaurantEmployees.size();
		orderIDs=new ArrayList<String>();
		
	}

	//Import methods
	public void importClientInformation(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		br.readLine();
		String line = br.readLine();
		while(line!=null){
			String[] parts = line.split(";");
			String name = parts[0];
			String lastname = parts[1];
			String id = parts[2];
			String addresss = parts[3];
			String phoneNumber = parts[4];
			String observations = parts[5];
			addAClientToTheRestaurant(name, lastname, id, addresss, phoneNumber, observations);
			line = br.readLine();
		}
		br.close();
	}
	public void importProductInformation(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		br.readLine();
		String line = br.readLine();
		while(line!=null){
			String[] parts = line.split(";");
			String productName = parts[0];
			PlateType productPlateType;
			String plateTypeName = parts[1];
			if(plateTypeIndexWithName(plateTypeName)!=-1) {
				productPlateType=restaurantPlateTypes.get(plateTypeIndexWithName(plateTypeName));
			}
			else {
				productPlateType = new PlateType(plateTypeName,currentUser);
				addAPlateTypeToTheRestaurant(plateTypeName);
			}
			String[] ingredientsParts = parts[2].split(",");
			ArrayList<Ingredient> ingredientsOfProduct = new ArrayList<Ingredient>();
			for(int i=0;i<ingredientsParts.length;i++) {
				String ingredientName = ingredientsParts[i];
				if(ingredientIndexWithName(ingredientName)!=-1) {
					ingredientsOfProduct.add(restaurantIngredients.get(ingredientIndexWithName(ingredientName)));
				}
				else {
					ingredientsOfProduct.add(new Ingredient(ingredientName,currentUser));
					addAnIngredientToTheRestaurant(ingredientName);
				}
			}
			
			String[] sizesStringArray = parts[3].split(",");
			ArrayList<String> productsSizes = (ArrayList<String>)Arrays.asList(sizesStringArray);
			
			String[] pricesStringArray = parts[4].split(",");
			ArrayList<Double> productsPrices = new ArrayList<Double>();
			for(int i=0;i<pricesStringArray.length;i++) {
				productsPrices.add(Double.parseDouble(pricesStringArray[i]));
			}
			addProduct(productName,productPlateType,ingredientsOfProduct,productsSizes,productsPrices);
			line = br.readLine();
		}
		br.close();
	}
	
	public void importOrderInformation(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		br.readLine();
		String line = br.readLine();
		while(line!=null){
			String[] parts = line.split(";");
			String[] productsString = parts[0].split(",");
			String[] productsSizes = parts[1].split(",");
			String[] productsQuantitys = parts[2].split(",");
			ArrayList<Product> ordProd = new ArrayList<Product>();
			ArrayList<Integer> ordPQuantitys = new ArrayList<Integer>();
			for(int i=0;i<productsString.length;i++) {
				if(productIndexWithName(productsString[i])!=-1) {
					Product auxProd = restaurantProducts.get(productIndexWithName(productsString[i]));
					int sizeIndex = sizeIndexWithName(auxProd.getProductsSizes(), productsSizes[i]);
					ordProd.add(new Product(auxProd.getName(),currentUser,auxProd.getPlateType(),auxProd.getIngrdnts(),auxProd.getProductsSizes(),auxProd.getSizesPrices(),sizeIndex));
				}
			}
			for(int i=0;i<productsQuantitys.length;i++) {
				ordPQuantitys.add(Integer.parseInt(productsQuantitys[i]));
			}
			String[] clientNameAndLastName = parts[3].split(" ");
			Client clientOfOrder=null;
			if(clientIndexWithNameAndLastname(clientNameAndLastName[0], clientNameAndLastName[0])!=-1) {
				clientOfOrder=restaurantClients.get(clientIndexWithNameAndLastname(clientNameAndLastName[0], clientNameAndLastName[0]));
			}
			
			String employeeName = parts[4];
			String employeeLastname = parts[5];
			String employeeId = parts[6];
			Employee employeeOfTheOrder = new Employee(employeeName,currentUser,employeeLastname,employeeId);
			String observations = parts[7];
			createAnOrder(ordProd, ordPQuantitys, clientOfOrder, employeeOfTheOrder, observations);
			line = br.readLine();
		}
		br.close();
	}
	
	public int sizeIndexWithName(ArrayList<String> al,String name) {
		for(int i=0;i<al.size();i++) {
			if(al.get(i).equalsIgnoreCase(name)) {
				return i;
			}
		}
		return -1;
	}
	
	//Export methods
	
	public void generateOrderReport(String fileName,String separator,LocalDateTime startDate,LocalDateTime endDate) throws FileNotFoundException{
	    PrintWriter pw = new PrintWriter(fileName);
	    sortOrdersByDate();
	    String columns = "Codigo"+separator+"Nombre del cliente"+separator+"Direccion del cliente"+separator+
	    "Telefono del cliente"+separator+"Nombre del empleado que entrega el producto"+separator+
	    "Estado del pedido"+separator+"Fecha y hora"+separator+"Observaciones";
	    pw.println(columns);
	    for(int i=0;i<restaurantOrdersSize;i++){
	    	Order auxOrder = restaurantOrders.get(i);
	    	LocalDateTime dateOfOrder = auxOrder.getDate();
	    	if(dateOfOrder.isAfter(startDate) && dateOfOrder.isBefore(endDate)) {
	    		auxOrder.setSeparator(separator);
	    		pw.println(auxOrder.showInformation());
	    	}
	    }

	    pw.close();
	  }
	
	public void generateEmployeeReport(String fileName,String separator,LocalDateTime startDate,LocalDateTime endDate) throws FileNotFoundException{
		int totalDeliveries = 0;
		double totalPrices = 0;
		PrintWriter pw = new PrintWriter(fileName);
		sortEmployeesById();
		String columns = "Nombre"+separator+"Apellido"+separator+"ID"+separator+"Pedidos entregados"+
		separator+"Valor total de los pedidos";
		pw.println(columns);
		 for(int i=0;i<restaurantEmployeesSize;i++) {
			 pw.println(restaurantEmployees.get(i).showReportInformation(startDate, endDate));
			 totalPrices+=restaurantEmployees.get(i).getTotalPriceOfTheOrders(startDate, endDate);
			 totalDeliveries+=restaurantEmployees.get(i).getSpecifiedOrdersDelivered();
		 }
		String finalColumns = restaurantEmployeesSize+" empleados"+separator+""+separator+""+separator+
		totalDeliveries+" pedidos entregados"+separator+totalPrices+" generados";
		pw.println(finalColumns);
		pw.close();
	}
	
	public void generateProductReport(String fileName,String separator,LocalDateTime startDate,LocalDateTime endDate) throws FileNotFoundException {
		int allTimesRequired = 0;
		double totalPrice = 0;
		PrintWriter pw = new PrintWriter(fileName);
		ProductInsertionSortByName();
		String columns = "Nombre del producto"+separator+"Tipo de producto"+separator+"Ingredientes"+separator+
		"Numero de veces que se pidio"+separator+"Total pagado";
		pw.println(columns);
		for(int i=0;i<restaurantProductsSize;i++) {
			pw.println(restaurantProducts.get(i).showReportInformation(startDate, endDate));
			double[] typ = restaurantProducts.get(i).getTotalTimesAndPrice(startDate, endDate);
			allTimesRequired += typ[0];
			totalPrice += typ[1];
		}
		String finalColumns = restaurantProductsSize+" productos en el restaurante"
		+separator+""+separator+""+separator+allTimesRequired+" productos pedidos"+totalPrice;
		pw.println(finalColumns);
		pw.close();
	}
	
	//Serialization methods
	
	 public void saveProductData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCT_SAVE_PATH_FILE));
		 oos.writeObject(restaurantProducts);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadProductData() throws IOException, ClassNotFoundException{
		 File f = new File(PRODUCT_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantProducts = (ArrayList<Product>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 public void saveIngredientData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(INGREDIENT_SAVE_PATH_FILE));
		 oos.writeObject(restaurantIngredients);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadIngredientData() throws IOException, ClassNotFoundException{
		 File f = new File(INGREDIENT_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantIngredients = (ArrayList<Ingredient>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 
	 public void savePlateTypeData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLATETYPE_SAVE_PATH_FILE));
		 oos.writeObject(restaurantProducts);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadPlateTypeData() throws IOException, ClassNotFoundException{
		 File f = new File(PLATETYPE_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantPlateTypes = (ArrayList<PlateType>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 
	 public void saveClientData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CLIENT_SAVE_PATH_FILE));
		 oos.writeObject(restaurantClients);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadClientData() throws IOException, ClassNotFoundException{
		 File f = new File(CLIENT_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantClients = (ArrayList<Client>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 
	 public void saveEmployeeData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_SAVE_PATH_FILE));
		 oos.writeObject(restaurantEmployees);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadEmployeesData() throws IOException, ClassNotFoundException{
		 File f = new File(EMPLOYEE_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantEmployees = (ArrayList<Employee>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 
	 public void saveUserData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_SAVE_PATH_FILE));
		 oos.writeObject(restaurantUsers);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadUserData() throws IOException, ClassNotFoundException{
		 File f = new File(USER_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantUsers = (ArrayList<User>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 
	 public void saveOrderData() throws IOException{
		 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ORDER_SAVE_PATH_FILE));
		 oos.writeObject(restaurantOrders);
		 oos.close();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public boolean loadOrderData() throws IOException, ClassNotFoundException{
		 File f = new File(PRODUCT_SAVE_PATH_FILE);
		 boolean loaded = false;
		 if(f.exists()){
			 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			 restaurantOrders = (ArrayList<Order>)ois.readObject();
			 ois.close();
			 loaded = true;
		 }
		 return loaded;
	 }
	 
	 public void saveAllData() throws IOException{
		 saveProductData();
		 saveIngredientData();
		 savePlateTypeData();
		 saveClientData();
		 saveEmployeeData();
		 saveUserData();
		 saveOrderData();
	 }



	/**
	Does a search in the restaurant <br>
	<b> pre: </b>The arrayList it receives must be sorted<br>
	<b> post: </b>The object is found or doesn't exist<br>
	@param aL The ArrayList in which the object will be searched
	@param name The name of the object that will be searched
	@return index
	 */
	public int binarySearch(ArrayList<?> aL,String name) {
		int pos = -1;
		int i = 0;
		int j = aL.size()-1;
		
		while(i<=j && pos<0) {
			int m = (i+j)/2;
			String objectNameLowerCase = ((SystemObject) aL.get(m)).getName().toLowerCase();
			String nameLowerCase = name.toLowerCase();
			if(objectNameLowerCase.equals(nameLowerCase)) {
				pos=m;
			}
			else if((objectNameLowerCase.compareTo(nameLowerCase))>0){
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		return pos;
	}

	//User options

	public boolean initializeUser(int index,String username,String password) throws IOException {
		if(index!=-1) {
			restaurantUsers.get(index).setUsername(username);
			restaurantUsers.get(index).setPassword(password);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	/**
	 * Given the id, returns the index of a user of the user ArrayList<br>
	 * <b>Pre: </b>The arrayList "restaurantUsers" must be sorted. To be useful, there must be at least one user in the ArrayList<br>
	 * <b>Post: </b>The index of the user is obtained if it exists<br>
	 * @param id The id of the user
	 * @return index
	 */
	public int userIndexWithId(String id) {	//Use it when you have the user id but not the user itself
		int index = binarySearchForEmployees(restaurantUsers,id);
		return index;
	}
	/**
	 * Given the user, returns the index of that user in the users ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one user in the ArrayList<br>
	 * <b>Post: </b>The index of the user is obtained if it exists<br>
	 * @param user The user searched
	 * @return index
	 */
	public int UserIndexWithUser(User user) { //Use it when you have the user itself
		int index = restaurantUsers.indexOf(user);
		return index;
	}
	public boolean changeUsername(int index,String newUsername) {
		if(index!=-1) {
			restaurantUsers.get(index).setUsername(newUsername);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	public boolean changePassword(int index,String newPassword) {
		if(index!=-1) {
			restaurantUsers.get(index).setPassword(newPassword);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	public boolean enableUser(int index) {
		if(index!=-1) {
			restaurantUsers.get(index).setEnabled(true);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	public boolean disableUser(int index) {
		if(index!=-1) {
			restaurantUsers.get(index).setEnabled(false);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	/**
	 * Checks if the user that is being used isn't the root user.<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>It is stated whether or not the root user is being used<br>
	 * @return true if the current user is the root user, false if not
	 */
	public boolean checkFirstTime() {
		if(firstTime&&currentUser.equals(rootUser)) {
			return true;
		}
		return false;
	}
	/**
	 * Changes the user that is being used.<br>
	 * <b>Pre: </b>Needs an index indicating what user will be used<br>
	 * <b>Post: </b>The user is changed<br>
	 * @param index The index of the user in the users ArrayList
	 * @return true if the user was changed, false if not
	 */
	public boolean changeUser(int index) {
		if(index!=-1) {
			currentUser=restaurantUsers.get(index);
			return true;
		}
		else if(index==-2) {
			currentUser=rootUser;
			return true;
		}
		return false;

	}
	/**
	 * Searchs for a user from the ArrayList or uses the rootUser<br>
	 * <b>Pre: </b>There must be at least one user <br>
	 * <b>Post: </b>Finds the user if it existed<br>
	 * @param username The username of the user that will be searched
	 * @param password The password of the user that will be searched
	 * @return index
	 */
	public int login(String username,String password) {
		int index = -1;
		boolean band = false;
		for(int i=0;i<restaurantUsersSize&&!band;i++) {
			if((restaurantUsers.get(i).getUsername().equals(username))&&(restaurantUsers.get(i).getPassword().equals(password))) {
				index = i;
				band = true;
			}
		}
		if(rootUser.getUsername().equals(username)&&rootUser.getPassword().equals(password)) {
			index = -2;
		}
		return index;
	}


	//Product Options


	/**
	 * Adds a product to the products ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds a product to the products ArrayList if there isn't conflicts with it<br>
	 * @param name The name of the product
	 * @param pt The plate type of the product
	 * @param ingrdnts The ingredients of the product
	 * @param productSizes The sizes of the product
	 * @param sizesPrices The respective prices of the sizes of the product
	 * @return True if the product was added, false if not
	 */
	public boolean addProduct(String name,PlateType pt,ArrayList<Ingredient> ingrdnts,ArrayList<String> productSizes,ArrayList<Double> sizesPrices) {
		int index = productIndexWithName(name);
		if(index==-1&&pt.getEnabled()) {
			Product toAdd = new Product(name,currentUser,pt,ingrdnts,productSizes,sizesPrices,-1);
			restaurantProducts.add(toAdd);
			addToproductsWithTheirSizes(name,pt,ingrdnts,productSizes,sizesPrices);
			restaurantProductsSize++;
			return true;
		}
		return false;
	}
	/**
	 * Adds a product for each size to the productsWithTheirSizes ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds a product for each size to the productsWithTheirSizes ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 */
	private void addToproductsWithTheirSizes(String name,PlateType pt,ArrayList<Ingredient> ingrdnts,ArrayList<String> productSizes,ArrayList<Double> sizesPrices) {
		for(int i=0;i<productSizes.size();i++) {
			productsWithTheirSizes.add(new Product(name,currentUser,pt,ingrdnts,productSizes,sizesPrices,i));
			productsWithTheirSizesSize++;
		}
	}


	/**
	 * Given the name, returns the index of a product of the products ArrayList<br>
	 * <b>Pre: </b>The arrayList "restaurantProducts" must be sorted. To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>The index of the product is obtained if it exists<br>
	 * @param name The name of the product
	 * @return index
	 */
	public int productIndexWithName(String name) {	//Use it when you have the product name but not the product itself
		ProductInsertionSortByName();
		int index =  binarySearch(restaurantProducts,name);
		return index;
	}

	/**
	 * Given the product, returns the index of that product in the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>The index of the product is obtained if it exists<br>
	 * @param product The product searched
	 * @return index
	 */
	public int productIndexWithProduct(Product product) { //Use it when you have the product itself
		int index = restaurantProducts.indexOf(product);
		return index;
	}

	/**
	 * Deletes a product of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Deletes a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param The index of the product that is going to be deleted
	 * @return True if the product was deleted, false if not
	 */
	public boolean deleteProduct(int index) {
		if(index!=-1) {
			Product deleted = restaurantProducts.get(index);
			if(!OrderHasTheProduct(deleted)) {
				deleteInproductsWithTheirSizes(deleted);
				restaurantProducts.remove(index);
				restaurantProductsSize--;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a product exists in the orders ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one order in the ArrayList<br>
	 * <b>Post: </b>Indicates if the product is used or not<br>
	 * @param product The product that will be searched
	 * @return True if the product was found, false if not
	 */
	public boolean OrderHasTheProduct(Product product) {
		for(int i=0;i<restaurantOrdersSize;i++) {
			ArrayList<Product> pdcts = restaurantOrders.get(i).getOrderProducts();
			if(pdcts.contains(product)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Deletes products of the productsWithTheirSizes ArrayList for each size of the product in the original ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Deletes products of the productsWithTheirSizes ArrayList for each size of the product in the original ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 */
	private void deleteInproductsWithTheirSizes(Product product) {
		ArrayList<Product> productsToDelete = new ArrayList<Product>();
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsToDelete.add(productsWithTheirSizes.get(i));
			}
		}
		for(int i=0;i<productsToDelete.size();i++) {
			productsWithTheirSizes.remove(productsToDelete.get(i));
			productsWithTheirSizesSize--;
		}
	}

	/**
	 * Disables a product of the restaurantProducts ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Disables a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param The index of the product that is going to be disabled
	 * @return True if the product was disabled, false if not
	 */
	public boolean disableProduct(int index) {
		if(index!=-1) {
			Product disabled = restaurantProducts.get(index);
			restaurantProducts.get(index).setEnabled(false);
			restaurantProducts.get(index).setModifierUser(currentUser);
			disableInproductsWithTheirSizes(disabled);
			return true;
		}
		return false;
	}
	/**
	 * Disables products of the productsWithTheirSizes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Disables products of the productsWithTheirSizes ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 */
	private void disableInproductsWithTheirSizes(Product product) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setEnabled(false);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	/**
	 * Enables a product of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Enables a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param The index of the product that is going to be enabled
	 * @return True if the product was enabled, false if not
	 */
	public boolean enableProduct(int index) {
		if(index!=-1) {
			Product enabled = restaurantProducts.get(index);
			restaurantProducts.get(index).setEnabled(true);
			restaurantProducts.get(index).setModifierUser(currentUser);
			enableInproductsWithTheirSizes(enabled);
			return true;
		}
		return false;
	}

	/**
	 * Enables products of the productsWithTheirSizes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Enables products of the productsWithTheirSizes ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 */
	private void enableInproductsWithTheirSizes(Product product) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setEnabled(true);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	/**
	 * Sorts the products in the ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Now the products in the ArrayList are sorted <br>
	 */
	public void ProductInsertionSortByName(){                                            
		int i=0;
		int j=0;
		Product aux;
		for (i = 1; i < restaurantProductsSize; i++){
			aux = restaurantProducts.get(i);        
			j = i - 1;           
			while ((j >= 0) && (aux.compareTo(restaurantProducts.get(j))<0)){                             
				restaurantProducts.set(j+1,restaurantProducts.get(j));
				j--;        
			}
			restaurantProducts.set(j+1, aux);
		}
	}


	public void sortProductsByPrice() {
		Comparator<Product> priceComparator = new priceComparator();
		Collections.sort(productsWithTheirSizes,priceComparator);
	}

	/**
	 * Changes the name of a product of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Changes the name of a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the product whose name will be changed
	 * @param newName The new name of the product
	 * @return True if the product's name was changed, false if not
	 */
	public boolean changeProductName(int index,String newName) {
		if(index!=-1) {
			int nameIsRepeated = productIndexWithName(newName);
			if(nameIsRepeated==-1) {
				Product productToChangeName=restaurantProducts.get(index);
				changeNameInproductsWithTheirSizes(productToChangeName, newName);
				restaurantProducts.get(index).setName(newName);
				restaurantProducts.get(index).setModifierUser(currentUser);
				return true;
			}
		}
		return false;
	}
	/**
	 * Changes names of the products of the productsWithTheirSizes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Changes names of the products of the productsWithTheirSizes ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 * @param name The new name for the products
	 */
	private void changeNameInproductsWithTheirSizes(Product product,String name) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setName(name);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	/**
	 * Changes the Plate Type of a product of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Changes the plate type of a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the product whose name will be changed
	 * @param newPlateType The new plate type of the product
	 * @return True if the product's name was changed, false if not
	 */
	public boolean changeProductPlateType(int index,PlateType newPlateType) {
		if(index!=-1 && newPlateType.getEnabled()) {
			Product productToChangePlateType=restaurantProducts.get(index);
			restaurantProducts.get(index).setPt(newPlateType);
			restaurantProducts.get(index).setModifierUser(currentUser);
			changePlateTypeInproductsWithTheirSizes(productToChangePlateType, newPlateType);
			return true;
		}
		return false;
	}
	/**
	 * Changes the plate type of the products of the productsWithTheirSizes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Changes the plate type of the products of the productsWithTheirSizes ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 * @param pt The new plate type for the products
	 */
	private void changePlateTypeInproductsWithTheirSizes(Product product,PlateType pt) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setPt(pt);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	/**
	 * Adds an ingredient to a product of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Adds an ingredient to a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the product to which an ingredient will be added
	 * @param ingredient The ingredient that will be added
	 * @return b True if the ingredient was added, false if not
	 */
	public boolean addAnIngredientToAProduct(int index,Ingredient ingredient) {
		boolean b=false;
		if(index!=-1) {
			b=restaurantProducts.get(index).addAnIngredient(ingredient);
			if(b) {
				restaurantProducts.get(index).setModifierUser(currentUser);
			}
		}
		return b;
	}

	

	/**
	 * Deletes an ingredient of a product of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Deletes an ingredient of a product of the products ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the product to which an ingredient will be added
	 * @param ingredient The name of the ingredient that will be deleted
	 * @return b True if the ingredient was deleted, false if not
	 */
	public boolean deleteAnIngredientOfAProduct(int index,String ingredientName) {
		boolean b=false;
		if(index!=-1) {
			Product productToDeleteAnIngredient = restaurantProducts.get(index);
			b=restaurantProducts.get(index).deleteAnIngredient(ingredientName);
			if(b) {
				restaurantProducts.get(index).setModifierUser(currentUser);
				deletesAnIngredientInproductsWithTheirSizes(productToDeleteAnIngredient, ingredientName);
			}
		}
		return b;
	}

	/**
	 * Adds an ingredient to the products of the productsWithTheirSizes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Adds an ingredient to the products of the productsWithTheirSizes ArrayList if there isn't conflicts with it<br>
	 * @param product The original product in the restaurantProducts ArrayList
	 * @param ingredient The ingredient that will be added
	 */
	private void deletesAnIngredientInproductsWithTheirSizes(Product product,String name) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).deleteAnIngredient(name);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
				
			}
		}
	}

	//Ingredients Methods

	/**
	 * Adds an ingredient to the ingredients ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds an ingredient to the ingredients ArrayList if there isn't conflicts with it<br>
	 * @param name The name of the ingredient
	 * @return True if the product was added, false if not
	 */
	public boolean addAnIngredientToTheRestaurant(String name) {
		int index = ingredientIndexWithName(name);
		if(index==-1) {
			Ingredient toAdd = new Ingredient(name,currentUser);
			restaurantIngredients.add(toAdd);
			restaurantIngredientsSize++;
			return true;
		}
		return false;
	}

	/**
	 * Given the name, returns the index of a ingredient of the ingredient ArrayList<br>
	 * <b>Pre: </b>The arrayList "restaurantIngredients" must be sorted. To be useful, there must be at least one ingredient in the ArrayList<br>
	 * <b>Post: </b>The index of the ingredient is obtained if it exists<br>
	 * @param name The name of the ingredient
	 * @return index
	 */
	public int ingredientIndexWithName(String name) {	//Use it when you have the ingredient name but not the ingredient itself
		selectionSortIngredients();
		int index =  binarySearch(restaurantIngredients,name);
		return index;
	}

	/**
	 * Given the ingredient, returns the index of that ingredient in the ingredients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one ingredient in the ArrayList<br>
	 * <b>Post: </b>The index of the ingredient is obtained if it exists<br>
	 * @param ingredient The ingredient searched
	 * @return index
	 */
	public int ingredientIndexWithIngredient(Ingredient ingredient) { //Use it when you have the ingredient itself
		int index = restaurantIngredients.indexOf(ingredient);
		return index;
	}

	/**
	 * Sorts the ingredients in the ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Now the products in the ArrayList are sorted <br>
	 */
	public void selectionSortIngredients() { 
		int n = restaurantIngredientsSize;
		for (int i = 0; i < n-1; i++){ 
			int min_idx = i; 
			for (int j = i+1; j < n; j++) {
				if (restaurantIngredients.get(j).compareTo(restaurantIngredients.get(min_idx))<0 ) {
					min_idx = j;	
				} 
			}	
			Ingredient temp = restaurantIngredients.get(min_idx); 
			restaurantIngredients.set(min_idx,restaurantIngredients.get(i)); 
			restaurantIngredients.set(i, temp);    
		} 
	} 

	public void descendantSortIngredients() {
		Comparator<Ingredient> nameComparator = new IngredientNameComparator();
		Collections.sort(restaurantIngredients,nameComparator);
	}
	
	/**
	 * Deletes an ingredient of the ingredients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one ingredient in the ArrayList<br>
	 * <b>Post: </b>Deletes a ingredient of the ingredients ArrayList if there isn't conflicts with it<br>
	 * @param The index of the ingredient that is going to be deleted
	 * @return True if the ingredient was deleted, false if not
	 */
	public boolean deleteIngredient(int index) {
		if(index!=-1) {
			Ingredient deleted = restaurantIngredients.get(index);
			if(!productHasTheIngredient(deleted)) {
				restaurantIngredients.remove(index);
				restaurantIngredientsSize--;
				return true;
			}

		}
		return false;
	}
	/**
	 * Checks if a ingredient is in one of the ingredients ArrayList of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Indicates if the ingredient is used or not<br>
	 * @param ingredient The ingredient that will be searched
	 * @return True if the ingredient was found, false if not
	 */
	public boolean productHasTheIngredient(Ingredient ingredient) {
		for(int i=0;i<restaurantProductsSize;i++) {
			ArrayList<Ingredient> ingredients = restaurantProducts.get(i).getIngrdnts();
			if(ingredients.contains(ingredient)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Enables an ingredient of the ingredients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one ingredient in the ArrayList<br>
	 * <b>Post: </b>Enables a ingredient of the ingredients ArrayList if there isn't conflicts with it<br>
	 * @param The index of the ingredient that is going to be enabled
	 * @return True if the ingredient was enabled, false if not
	 */
	public boolean enableIngredient(int index) {
		if(index!=-1) {
			restaurantIngredients.get(index).setEnabled(true);
			restaurantIngredients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/*
	 * Disables an ingredient of the ingredients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one ingredient in the ArrayList<br>
	 * <b>Post: </b>Disables a ingredient of the ingredients ArrayList if there isn't conflicts with it<br>
	 * @param The index of the ingredient that is going to be disabled
	 * @return True if the ingredient was disabled, false if not
	 */
	public boolean disableIngredient(int index) {
		if(index!=-1) {
			restaurantIngredients.get(index).setEnabled(false);
			restaurantIngredients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/**
	 * Changes the name of a ingredient of the ingredients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one ingredient in the ArrayList<br>
	 * <b>Post: </b>Changes the name of a ingredient of the ingredients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the ingredient whose name will be changed
	 * @param newName The new name of the ingredient
	 * @return True if the ingredient's name was changed, false if not
	 */
	public boolean changeingredientName(int index,String newName) {
		if(index!=-1) {
			int ingredientIsRepeated = ingredientIndexWithName(newName);
			if(ingredientIsRepeated==-1) {
				restaurantIngredients.get(index).setName(newName);
				restaurantIngredients.get(index).setModifierUser(currentUser);
				return true;
			}
		}
		return false;
	}


	//PlateType methods
	/**
	 * Adds a plateType to the plateTypes ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds a plateType to the plateTypes ArrayList if there isn't conflicts with it<br>
	 * @param name The name of the plateType
	 * @return True if the product was added, false if not
	 */
	public boolean addAPlateTypeToTheRestaurant(String name) {
		int index = plateTypeIndexWithName(name);
		if(index==-1) {
			PlateType toAdd = new PlateType(name,currentUser);
			restaurantPlateTypes.add(toAdd);
			restaurantPlateTypesSize++;
			return true;
		}
		return false;
	}

	public void collectionSortPlateTypes() {
		Comparator<PlateType> PlateTypeNameComparator = new PlateTypeNameComparator();
		Collections.sort(restaurantPlateTypes,PlateTypeNameComparator);
	}

	/**
	 * Given the name, returns the index of a plateType of the plateType ArrayList<br>
	 * <b>Pre: </b>The arrayList "restaurantPlateTypes" must be sorted. To be useful, there must be at least one plateType in the ArrayList<br>
	 * <b>Post: </b>The index of the plateType is obtained if it exists<br>
	 * @param name The name of the plateType
	 * @return index
	 */
	public int plateTypeIndexWithName(String name) {	//Use it when you have the plateType name but not the plateType itself
		collectionSortPlateTypes();
		int index =  binarySearch(restaurantPlateTypes,name);
		return index;
	}

	/**
	 * Given the plateType, returns the index of that plateType in the plateTypes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one plateType in the ArrayList<br>
	 * <b>Post: </b>The index of the plateType is obtained if it exists<br>
	 * @param plateType The plateType searched
	 * @return index
	 */
	public int plateTypeIndexWithplateType(PlateType plateType) { //Use it when you have the plateType itself
		int index = restaurantPlateTypes.indexOf(plateType);
		return index;
	}

	/**
	 * Deletes a plateType of the plateTypes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one plateType in the ArrayList<br>
	 * <b>Post: </b>Deletes a plateType of the plateTypes ArrayList if there isn't conflicts with it<br>
	 * @param The index of the plateType that is going to be deleted
	 * @return True if the plateType was deleted, false if not
	 */
	public boolean deletePlateType(int index) {
		if(index!=-1) {
			PlateType deleted = restaurantPlateTypes.get(index);
			if(!productHasThePlateType(deleted)) {
				restaurantPlateTypes.remove(index);
				restaurantPlateTypesSize--;
				return true;
			}

		}
		return false;
	}
	/**
	 * Checks if a plateType is in one of the plateTypes ArrayList of the products ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	 * <b>Post: </b>Indicates if the plateType is used or not<br>
	 * @param plateType The plateType that will be searched
	 * @return True if the plateType was found, false if not
	 */
	public boolean productHasThePlateType(PlateType plateType) {
		for(int i=0;i<restaurantProductsSize;i++) {
			if(restaurantProducts.get(i).getPt().equalsIgnoreCase(plateType.getName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Enables a plateType of the plateTypes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one plateType in the ArrayList<br>
	 * <b>Post: </b>Enables a plateType of the plateTypes ArrayList if there isn't conflicts with it<br>
	 * @param The index of the plateType that is going to be enabled
	 * @return True if the plateType was enabled, false if not
	 */
	public boolean enablePlateType(int index) {
		if(index!=-1) {
			restaurantPlateTypes.get(index).setEnabled(true);
			restaurantPlateTypes.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/*
	 * Disables a plateType of the plateTypes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one plateType in the ArrayList<br>
	 * <b>Post: </b>Disables a plateType of the plateTypes ArrayList if there isn't conflicts with it<br>
	 * @param The index of the plateType that is going to be disabled
	 * @return True if the plateType was disabled, false if not
	 */
	public boolean disablePlateType(int index) {
		if(index!=-1) {
			restaurantPlateTypes.get(index).setEnabled(false);
			restaurantPlateTypes.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/**
	 * Changes the name of a plateType of the plateTypes ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one plateType in the ArrayList<br>
	 * <b>Post: </b>Changes the name of a plateType of the plateTypes ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the plateType whose name will be changed
	 * @param newName The new name of the plateType
	 * @return True if the plateType's name was chaged, false if not
	 */
	public boolean changePlateTypeName(int index,String newName) {
		if(index!=-1) {
			int plateTypeIsRepeated = plateTypeIndexWithName(newName);
			if(plateTypeIsRepeated==-1) {
				restaurantPlateTypes.get(index).setName(newName);
				return true;
			}
		}
		return false;
	}

	//Clients
	/**
	 * Adds a client to the clients ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds a client to the clients ArrayList if there isn't conflicts with it<br>
	 * @param name The name of the client
	 * @param lastname The lastname of the client
	 * @param id The id of the client
	 * @param address The address of the client
	 * @param phoneNumber The phone number of the client
	 * @param observations The observations of the client
	 * @return True if the product was added, false if not
	 */
	public boolean addAClientToTheRestaurant(String name,String lastname,String id,String address,String phoneNumber,String observations) {
		if(restaurantClients.isEmpty()) {
			Client toAdd = new Client(name,currentUser,lastname,id,address,phoneNumber,observations);
			restaurantClients.add(toAdd);
			restaurantClientsSize++;
			return true;
		}
		else {
			int index = neatlyInsertClients(name,lastname);
			if(index!=-1) {
				Client toAdd = new Client(name,currentUser,lastname,id,address,phoneNumber,observations);
				restaurantClients.add(index,toAdd);
				restaurantClientsSize++;
				return true;
			}
		}
		return false;

	}

	public int neatlyInsertClients(String name,String lastname) {
		int indexToInsert = 0;
		boolean found = false;
		String valueToSearch = lastname+name;
		for (int i = 0; i < restaurantClientsSize; i++){
			String valueOfArrayList = restaurantClients.get(i).getLastname()+restaurantClients.get(i).getName();
			if ( !found && (valueToSearch.compareTo(valueOfArrayList)) == 0){
				found = true;
				indexToInsert = -1;
			}
			else if(!found && (valueToSearch.compareTo(valueOfArrayList)) >= 0) {
				found = true;
				indexToInsert = i;
				i--;
			}
		}
		if(!found){
			indexToInsert = restaurantClientsSize;
		}
		return indexToInsert;
	}

	public int binarySearchForClients(ArrayList<?> aL,String name,String lastname) {
		long startTime = System.nanoTime();
		int pos = -1;
		int i = 0;
		int j = aL.size()-1;
		String valueToSearch = lastname+name;
		while(i<=j && pos<0) {
			int m = (i+j)/2;
			String stringOfArrayList = ((Person) aL.get(m)).getLastname()+((Person) aL.get(m)).getName();
			if(stringOfArrayList.equalsIgnoreCase(valueToSearch)) {
				pos=m;
			}
			else if(stringOfArrayList.compareTo(valueToSearch)<0){   ///Changed because is descending
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		timeOfSearch=timeElapsed;
		return pos;
	}

	/**
	 * Given the name, returns the index of a client of the client ArrayList<br>
	 * <b>Pre: </b>The arrayList "restaurantClients" must be sorted. To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>The index of the client is obtained if it exists<br>
	 * @param name The name of the client
	 * @return index
	 */
	public int clientIndexWithNameAndLastname(String name,String lastname) {	//Use it when you have the client name but not the client itself
		int index =  binarySearchForClients(restaurantClients,name,lastname);
		return index;
	}

	/**
	 * Given the client, returns the index of that client in the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>The index of the client is obtained if it exists<br>
	 * @param client The client searched
	 * @return index
	 */
	public int clientIndexWithclient(Client client) { //Use it when you have the client itself
		int index = restaurantClients.indexOf(client);
		return index;
	}

	/**
	 * Deletes a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Deletes a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param The index of the client that is going to be deleted
	 * @return True if the client was deleted, false if not
	 */
	public boolean deleteClient(int index) {
		if(index!=-1) {
			Client deleted = restaurantClients.get(index);
			if(!orderHasTheClient(deleted)) {
				restaurantClients.remove(index);
				restaurantClientsSize--;
				return true;
			}
		}
		return false;
	}

	public boolean orderHasTheClient(Client client) {
		String aux1 = client.getLastname()+client.getName();
		for(int i=0;i<restaurantOrdersSize;i++) {
			Client check = restaurantOrders.get(i).getOrderclient();
			String aux2 = check.getLastname()+check.getName();
			if(aux1.equalsIgnoreCase(aux2)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Enables a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Enables a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param The index of the client that is going to be enabled
	 * @return True if the client was enabled, false if not
	 */
	public boolean enableClient(int index) {
		if(index!=-1) {
			restaurantClients.get(index).setEnabled(true);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/*
	 * Disables a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Disables a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param The index of the client that is going to be disabled
	 * @return True if the client was disabled, false if not
	 */
	public boolean disableClient(int index) {
		if(index!=-1) {
			restaurantClients.get(index).setEnabled(false);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	/**
	 * Changes the name of a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Changes the name of a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the client whose name will be changed
	 * @param newName The new name of the client
	 * @return True if the client's name was changed, false if not
	 */
	public boolean changeClientName(int index,String newName) {
		if(index!=-1) {
			int clientIsRepeated = clientIndexWithNameAndLastname(newName,restaurantClients.get(index).getLastname());
			if(clientIsRepeated==-1) {
				restaurantClients.get(index).setName(newName);
				restaurantClients.get(index).setModifierUser(currentUser);
				return true;
			}
		}
		return false;
	}

	/**
	 * Changes the lastname of a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Changes the lastname of a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the client whose lastname will be changed
	 * @param newLastname The new lastname of the client
	 * @return True if the client's lastname was changed, false if not
	 */
	public boolean changeClientLastname(int index,String newLastname) {
		if(index!=-1) {
			int clientIsRepeated = clientIndexWithNameAndLastname(restaurantClients.get(index).getName(),newLastname);
			if(clientIsRepeated==-1) {
				restaurantClients.get(index).setLastname(newLastname);
				restaurantClients.get(index).setModifierUser(currentUser);
				return true;	
			}
		}
		return false;
	}

	/**
	 * Changes the id of a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Changes the id of a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the client whose id will be changed
	 * @param newId The new id of the client
	 * @return True if the client's id was changed, false if not
	 */
	public boolean changeClientId(int index,String newId) {
		if(index!=-1) {
			restaurantClients.get(index).setId(newId);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/**
	 * Changes the address of a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Changes the address of a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the client whose address will be changed
	 * @param newAddress The new address of the client
	 * @return True if the client's address was changed, false if not
	 */
	public boolean changeClientAddress(int index,String newAddress) {
		if(index!=-1) {
			restaurantClients.get(index).setAddress(newAddress);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/**
	 * Changes the phoneNumber of a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Changes the phoneNumber of a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the client whose phoneNumber will be changed
	 * @param newPhoneNumber The new phoneNumber of the client
	 * @return True if the client's phoneNumber was changed, false if not
	 */
	public boolean changeClientPhoneNumber(int index,String newPhoneNumber) {
		if(index!=-1) {
			restaurantClients.get(index).setPhoneNumber(newPhoneNumber);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	/**
	 * Changes the observations of a client of the clients ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one client in the ArrayList<br>
	 * <b>Post: </b>Changes the observations of a client of the clients ArrayList if there isn't conflicts with it<br>
	 * @param index the index of the client whose observations will be changed
	 * @param newObservations The new observations of the client
	 * @return True if the client's observations was changed, false if not
	 */
	public boolean changeClientObservations(int index,String newObservations) {
		if(index!=-1) {
			restaurantClients.get(index).setObservations(newObservations);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	//Employee methods
	/**
	 * Adds a employee to the employees ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds a employee to the employees ArrayList if there isn't conflicts with it<br>
	 * @param name The name of the employee
	 * @param lastname The lastname of the employee
	 * @param id The id of the employee
	 * @return True if the product was added, false if not
	 */
	public boolean addAnEmployeeToTheRestaurant(String name,String lastname,String id) { //Remember to work in this
		sortEmployeesById();
		int index = employeeIndexWithId(id);
		if(index==-1) {
			Employee toAdd = new Employee(name,currentUser,lastname,id);
			restaurantEmployees.add(toAdd);
			User employeeUser = new User(name,currentUser,lastname,id,name,id);
			restaurantUsers.add(employeeUser);
			restaurantUsersSize++;
			restaurantEmployeesSize++;
			return true;	
		}
		return false;
	}
	/**
	 * Given the id, returns the index of a employee of the employee ArrayList<br>
	 * <b>Pre: </b>The arrayList "restaurantEmployees" must be sorted. To be useful, there must be at least one employee in the ArrayList<br>
	 * <b>Post: </b>The index of the employee is obtained if it exists<br>
	 * @param id The id of the employee
	 * @return index
	 */
	public int employeeIndexWithId(String id) {
		int index = binarySearchForEmployees(restaurantEmployees,id);
		return index;
	}
	/**
	 * Given the employee, returns the index of that employee in the employees ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	 * <b>Post: </b>The index of the employee is obtained if it exists<br>
	 * @param employee The employee searched
	 * @return index
	 */
	public int employeeIndexWithemployee(Employee employee) { //Use it when you have the employee itself
		int index = restaurantEmployees.indexOf(employee);
		return index;
	}
	public int binarySearchForEmployees(ArrayList<?> aL,String id) {
		int pos = -1;
		int i = 0;
		int j = aL.size()-1;
		while(i<=j && pos<0) {
			int m = (i+j)/2;
			if((((Employee)aL.get(m)).getId().equalsIgnoreCase(id))) {
				pos=m;
			}
			else if(((((Employee)aL.get(m)).getId()).compareTo(id))>0){
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		return pos;
	}
	public void sortEmployeesById() {
		Comparator<Employee> idComparator = new IDComparator();
		Collections.sort(restaurantEmployees,idComparator);
	}

	/**
	 * Deletes a employee of the employees ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	 * <b>Post: </b>Deletes a employee of the employees ArrayList if there isn't conflicts with it<br>
	 * @param The index of the employee that is going to be deleted
	 * @return True if the employee was deleted, false if not
	 */
	public boolean deleteEmployee(int index) {
		if(index!=-1) {
			Employee deleted = restaurantEmployees.get(index);
			if(!orderHasTheEmployee(deleted)) {
				restaurantEmployees.remove(index);
				restaurantUsers.remove(deleted);
				restaurantUsers.remove(indexOfAnUser(deleted));
				restaurantUsersSize--;
				restaurantEmployeesSize--;
				return true;
			}
		}
		return false;
	}
	
	public int indexOfAnUser(Employee employee) {
		String aux = employee.getId();
		for(int i=0;i<restaurantUsersSize;i++) {
			if(aux.equalsIgnoreCase(restaurantUsers.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}

	public boolean orderHasTheEmployee(Employee employee) {
		String aux1 = employee.getId();
		for(int i=0;i<restaurantOrdersSize;i++) {
			Employee check = restaurantOrders.get(i).getOrderEmployee();
			String aux2 = check.getId();
			if(aux1.equalsIgnoreCase(aux2)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Enables a employee of the employees ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	 * <b>Post: </b>Enables a employee of the employees ArrayList if there isn't conflicts with it<br>
	 * @param The index of the employee that is going to be enabled
	 * @return True if the employee was enabled, false if not
	 */
	public boolean enableEmployee(int index) {
		if(index!=-1) {
			Employee enabled = restaurantEmployees.get(index);
			restaurantEmployees.get(index).setEnabled(true);
			restaurantEmployees.get(index).setModifierUser(currentUser);
			restaurantUsers.get(indexOfAnUser(enabled)).setEnabled(true);
			restaurantUsers.get(indexOfAnUser(enabled)).setModifierUser(currentUser);
			
			return true;
		}
		return false;
	}
	
	/*
	 * Disables a employee of the employees ArrayList<br>
	 * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	 * <b>Post: </b>Disables a employee of the employees ArrayList if there isn't conflicts with it<br>
	 * @param The index of the employee that is going to be disabled
	 * @return True if the employee was disabled, false if not
	 */
	public boolean disableEmployee(int index) {
		if(index!=-1) {
			Employee disabled = restaurantEmployees.get(index);
			restaurantEmployees.get(index).setEnabled(false);
			restaurantEmployees.get(index).setModifierUser(currentUser);
			restaurantUsers.get(indexOfAnUser(disabled)).setEnabled(false);
			restaurantUsers.get(indexOfAnUser(disabled)).setModifierUser(currentUser);
			return true;
		}
		return false;
	}
	/**
	  * Changes the name of a employee of the employees ArrayList<br>
	  * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	  * <b>Post: </b>Changes the name of a employee of the employees ArrayList if there isn't conflicts with it<br>
	  * @param index the index of the employee whose name will be changed
	  * @param newName The new name of the employee
	  * @return True if the employee's name was changed, false if not
	  */
	 public boolean changeEmployeeName(int index,String newName) {
		 if(index!=-1) {
			 Employee changeName=restaurantEmployees.get(index);
			 restaurantEmployees.get(index).setName(newName);
			 restaurantEmployees.get(index).setModifierUser(currentUser);
			 restaurantUsers.get(indexOfAnUser(changeName)).setName(newName);
			 restaurantUsers.get(indexOfAnUser(changeName)).setModifierUser(currentUser);
			 return true;
		 }
		 return false;
	 }
	 
	 /**
	  * Changes the lastname of a employee of the employees ArrayList<br>
	  * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	  * <b>Post: </b>Changes the lastname of a employee of the employees ArrayList if there isn't conflicts with it<br>
	  * @param index the index of the employee whose lastname will be changed
	  * @param newLastname The new lastname of the employee
	  * @return True if the employee's lastname was changed, false if not
	  */
	 public boolean changeEmployeeLastname(int index,String newLastname) {
		 if(index!=-1) {
			 Employee changeLastname=restaurantEmployees.get(index);
			 restaurantEmployees.get(index).setLastname(newLastname);
			 restaurantEmployees.get(index).setModifierUser(currentUser);
			 restaurantUsers.get(indexOfAnUser(changeLastname)).setLastname(newLastname);
			 restaurantUsers.get(indexOfAnUser(changeLastname)).setModifierUser(currentUser);
			 return true;
		 }
		 return false;
	 }
	
	 /**
	  * Changes the id of a employee of the employees ArrayList<br>
	  * <b>Pre: </b>To be useful, there must be at least one employee in the ArrayList<br>
	  * <b>Post: </b>Changes the id of a employee of the employees ArrayList if there isn't conflicts with it<br>
	  * @param index the index of the employee whose id will be changed
	  * @param newId The new id of the employee
	  * @return True if the employee's id was changed, false if not
	  */
	 public boolean changeEmployeeId(int index,String newId) {
		 if(index!=-1) {
			 int employeeIsRepeated = employeeIndexWithId(newId);
			 if(employeeIsRepeated==-1) {
				 Employee changeId=restaurantEmployees.get(index);
				 restaurantUsers.get(indexOfAnUser(changeId)).setId(newId);
				 restaurantUsers.get(indexOfAnUser(changeId)).setModifierUser(currentUser);
				 restaurantEmployees.get(index).setId(newId);
				 restaurantEmployees.get(index).setModifierUser(currentUser);
				 return true; 
			 }
		 }
		 return false;
	 }
	 
	 //Order methods
	 
	 public boolean createAnOrder(ArrayList<Product> orderProducts,ArrayList<Integer> productsQuantity,Client orderClient,Employee orderEmployee,String observations) {
		 boolean validQuantitys = productsQuantity.size()==orderProducts.size();
		 if(orderProductsAreEnabled(orderProducts) && validQuantitys && orderClient.getEnabled() && orderEmployee.getEnabled()) {
			 Order newOrder = new Order(currentUser, orderIDs,orderProducts,productsQuantity,orderClient,orderEmployee,observations);
			 restaurantOrders.add(newOrder);
			 orderIDs.add(newOrder.getName());
			 restaurantOrdersSize++;
			 return true;
		 }
		 return false;
	 }
	 
	 public void sortOrdersByDate() {
		 Comparator<Order> orderComparator = new OrderDateComparator();
		 Collections.sort(restaurantOrders,orderComparator);
	 }
	 public boolean orderProductsAreEnabled(ArrayList<Product> p) {
		 for(int i=0;i<p.size();i++) {
			 if(!p.get(i).getEnabled()) {
				 return false;
			 }
		 }
		 return true;
	 }
	 public boolean changeOrderStatus(int index,int indicator) { 
		 if(index!=-1) {
			 String check = restaurantOrders.get(index).getOrderStatus();
			 if(!check.equals("ENTREGADO") && !check.equals("CANCELADO")) {
				 restaurantOrders.get(index).setStatusIndicator(indicator);
				 restaurantOrders.get(index).setModifierUser(currentUser);
				 if(restaurantOrders.get(index).getOrderStatus()=="ENTREGADO") {
					 double priceOfTheOrder = restaurantOrders.get(index).getPriceOfTheOrder();
					 int employeeIndex = employeeIndexWithemployee(restaurantOrders.get(index).getOrderEmployee());
					 restaurantEmployees.get(employeeIndex).addAnOrderDelivered();
					 restaurantEmployees.get(employeeIndex).addAPriceOfAnOrder(priceOfTheOrder);
					 ArrayList<Product> products = restaurantOrders.get(index).getOrderProducts();
					 ArrayList<Integer> quantitys = restaurantOrders.get(index).getProductsQuantity();
					 addTimesRequestedToProducts(products, quantitys);
				 }
				 return true;
			 }
		 }
		 return false;
	 }
	 public void addTimesRequestedToProducts(ArrayList<Product> products,ArrayList<Integer> quantity) {
		 for(int i=0;i<products.size();i++) {
			 String productSearched = products.get(i).getName();
			 int productIndex = productIndexWithName(productSearched);
			 restaurantProducts.get(productIndex).addDateOfRequest();
			 restaurantProducts.get(productIndex).addTimesItWasRequested(quantity.get(i));
			 restaurantProducts.get(productIndex).addPricesPaidForProduct(products.get(i).getProductPrice()*quantity.get(i));
		 }
	 }
	 public int searchAnOrder(String id) {
		 for(int i=0;i<restaurantOrdersSize;i++) {
			 if(id.equals(restaurantOrders.get(i).getName())) {
				 return i;
			 }
		 }
		 return -1;
	 }



	//Getters
	public ArrayList<Product> getRestaurantProducts() {
		return restaurantProducts;
	}
	public String getRestaurantProductsString() {
		String info="";
		for(int i = 0;i<restaurantProductsSize;i++) {
			info += (restaurantProducts.get(i).showInformation()) + (",");
		}
		return info;
	}
	public ArrayList<Product> getProductsWithTheirSizes() {
		return productsWithTheirSizes;
	}
	public int getRestaurantProductsSize() {
		return restaurantProductsSize;
	}
	public int getProductsWithTheirSizesSize() {
		return productsWithTheirSizesSize;
	}
	public ArrayList<Ingredient> getRestaurantIngredients() {
		return restaurantIngredients;
	}
	public String getRestaurantIngredientsString() {
		String info="";
		for(int i = 0;i<restaurantIngredientsSize;i++) {
			info += (restaurantIngredients.get(i).showInformation()) + (",");
		}
		return info;
	}
	public int getRestaurantIngredientsSize() {
		return restaurantIngredientsSize;
	}
	public ArrayList<PlateType> getRestaurantPlateTypes() {
		return restaurantPlateTypes;
	}
	public String getRestaurantPlateTypesString() {
		String info="";
		for(int i = 0;i<restaurantPlateTypesSize;i++) {
			info += (restaurantPlateTypes.get(i).showInformation()) + (",");
		}
		return info;
	}
	public int getRestaurantPlateTypesSize() {
		return restaurantPlateTypesSize;
	}
	public long getTimeOfSearch() {
		return timeOfSearch;
	}
	public int getRestaurantEmployeesSize() {
		return restaurantEmployeesSize;
	}
	public ArrayList<User> getRestaurantUsers(){
		return restaurantUsers;
	}
	public ArrayList<Client> getRestaurantClients() {
		return restaurantClients;
	}
	public ArrayList<Order> getRestaurantOrders(){
		return restaurantOrders;
	}
	public User getRootUser() {
		return rootUser;
	}
	public User getCurrentUser() {
		return currentUser;
	}

	//Setters
	public void setRestaurantProducts(ArrayList<Product> restaurantProducts) {
		this.restaurantProducts = restaurantProducts;
	}
	public void setProductsWithTheirSizes(ArrayList<Product> productsWithTheirSizes) {
		this.productsWithTheirSizes = productsWithTheirSizes;
	}
	public void setRestaurantProductsSize(int restaurantProductsSize) {
		this.restaurantProductsSize = restaurantProductsSize;
	}
	public void setProductsWithTheirSizesSize(int productsWithTheirSizesSize) {
		this.productsWithTheirSizesSize = productsWithTheirSizesSize;
	}
	public void setRestaurantIngredients(ArrayList<Ingredient> restaurantIngredients) {
		this.restaurantIngredients = restaurantIngredients;
	}
	public void setRestaurantIngredientsSize(int restaurantIngredientsSize) {
		this.restaurantIngredientsSize = restaurantIngredientsSize;
	}
	public void setRestaurantPlateTypes(ArrayList<PlateType> restaurantPlateTypes) {
		this.restaurantPlateTypes = restaurantPlateTypes;
	}
	public void setRestaurantPlateTypesSize(int restaurantPlateTypesSize) {
		this.restaurantPlateTypesSize = restaurantPlateTypesSize;
	}
	public void setRootUser(User rootUser) {
		this.rootUser=rootUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser=currentUser;
	}
}