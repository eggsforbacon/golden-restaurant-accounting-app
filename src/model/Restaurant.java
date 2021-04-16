package model;

import java.io.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class Restaurant implements Serializable {

	private static final long serialVersionUID = 2;
	
	private User rootUser;
	private boolean firstTime;
	private final ArrayList<User> restaurantUsers;
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
	private final ArrayList<Client> restaurantClients;
	private int restaurantClientsSize;
	private long timeOfSearch;
	private final ArrayList<Order> restaurantOrders;
	private int restaurantOrdersSize;
	private final ArrayList<Employee> restaurantEmployees;
	private int restaurantEmployeesSize;
	private final ArrayList<String> orderIDs;
	private ArrayList<Client> searchResults;
	public Restaurant() {
		rootUser = new User("Generic",null,"user","none","Root","admin");
		firstTime = true;
		currentUser = rootUser;
		restaurantUsers = new ArrayList<>();
		restaurantUsersSize=restaurantUsers.size();
		restaurantProducts = new ArrayList<>();
		productsWithTheirSizes = new ArrayList<>();
		restaurantProductsSize = restaurantProducts.size();
		productsWithTheirSizesSize=0;
		restaurantIngredients = new ArrayList<>();
		restaurantIngredientsSize=restaurantIngredients.size();
		restaurantPlateTypes = new ArrayList<>();
		PlateType mainDish = new PlateType("Plato principal",currentUser);
		PlateType sideDish = new PlateType("Plato adicional",currentUser);
		PlateType drink = new PlateType("Bebida",currentUser);
		restaurantPlateTypes.add(mainDish);
		restaurantPlateTypes.add(sideDish);
		restaurantPlateTypes.add(drink);
		restaurantPlateTypesSize=restaurantPlateTypes.size();
		restaurantClients = new ArrayList<>();
		restaurantClientsSize = restaurantClients.size();
		timeOfSearch=0;
		restaurantOrders=new ArrayList<>();
		restaurantOrdersSize=restaurantOrders.size();
		restaurantEmployees = new ArrayList<>();
		restaurantEmployeesSize = restaurantEmployees.size();
		orderIDs=new ArrayList<>();
		searchResults = new ArrayList<>();
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
			ArrayList<Ingredient> ingredientsOfProduct = new ArrayList<>();
			for (String ingredientName : ingredientsParts) {
				if (ingredientIndexWithName(ingredientName) != -1) {
					ingredientsOfProduct.add(restaurantIngredients.get(ingredientIndexWithName(ingredientName)));
				} else {
					ingredientsOfProduct.add(new Ingredient(ingredientName, currentUser));
					addAnIngredientToTheRestaurant(ingredientName);
				}
			}
			
			String[] sizesStringArray = parts[3].split(",");
			ArrayList<String> productsSizes = new ArrayList<>(Arrays.asList(sizesStringArray));
			String[] pricesStringArray = parts[4].split(",");
			ArrayList<Double> productsPrices = new ArrayList<>();
			for (String s : pricesStringArray) {
				productsPrices.add(Double.parseDouble(s));
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
			ArrayList<Product> ordProd = new ArrayList<>();
			ArrayList<Integer> ordPQuantitys = new ArrayList<>();
			for(int i=0;i<productsString.length;i++) {
				if(productIndexWithName(productsString[i])!=-1) {
					Product auxProd = restaurantProducts.get(productIndexWithName(productsString[i]));
					int sizeIndex = sizeIndexWithName(auxProd.getProductsSizes(), productsSizes[i]);
					ordProd.add(new Product(auxProd.getName(),currentUser,auxProd.getPlateType(),auxProd.getIngrdnts(),auxProd.getProductsSizes(),auxProd.getSizesPrices(),sizeIndex));
				}
			}
			for (String productsQuantity : productsQuantitys) {
				ordPQuantitys.add(Integer.parseInt(productsQuantity));
			}
			String[] clientNameAndLastName = parts[3].split(" ");
			Client clientOfOrder=null;
			if(clientIndexWithNameAndLastname(clientNameAndLastName[0], clientNameAndLastName[1])!=-1) {
				clientOfOrder=restaurantClients.get(clientIndexWithNameAndLastname(clientNameAndLastName[0], clientNameAndLastName[1]));
			}
			
			
			String employeeName = parts[4];
			String employeeLastname = parts[5];
			String employeeId = parts[6];
			Employee employeeOfTheOrder = new Employee(employeeName,currentUser,employeeLastname,employeeId);
			String observations = parts[7];
			createAnOrder(ordProd, ordPQuantitys, clientOfOrder, employeeOfTheOrder, observations) ;
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

	@SuppressWarnings("UnusedReturnValue")
	public boolean initializeUser(int index,String username,String password) {
		if(index!=-1) {
			int userIsRepeated = userIndexWithUsername(username);
			if(userIsRepeated==-1) {
				restaurantUsers.get(index).setUsername(username);
				restaurantUsers.get(index).setPassword(password);
				restaurantUsers.get(index).setModifierUser(currentUser);
				return true;
			}
		}
		return false;
	}

	public int userIndexWithId(String id) {	//Use it when you have the user id but not the user itself
		return binarySearchForEmployees(restaurantUsers,id);
	}
	public int userIndexWithUsername(String userName) {
		for(int i=0;i<restaurantUsersSize;i++) {
			if(restaurantUsers.get(i).getUsername().equalsIgnoreCase(userName)) {
				return i;
			}
		}
		return -1;
	}

	public int UserIndexWithUser(User user) { //Use it when you have the user itself
		return restaurantUsers.indexOf(user);
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeUsername(int index,String newUsername) {
		if(index!=-1) {
			int userIsRepeated = userIndexWithUsername(newUsername);
			if(userIsRepeated==-1) {
				restaurantUsers.get(index).setUsername(newUsername);
				restaurantUsers.get(index).setModifierUser(currentUser);
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changePassword(int index,String newPassword) {
		if(index!=-1) {
			restaurantUsers.get(index).setPassword(newPassword);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean enableUser(int index) {
		if(index!=-1) {
			restaurantUsers.get(index).setEnabled(true);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean disableUser(int index) {
		if(index!=-1) {
			restaurantUsers.get(index).setEnabled(false);
			restaurantUsers.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	public boolean checkFirstTime() {
		return firstTime && currentUser.equals(rootUser);
	}

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

	public int login(String username,String password) {
		int index = -1; //Not found
		boolean band = false;
		for(int i=0;i<restaurantUsersSize&&!band;i++) {
			if((restaurantUsers.get(i).getUsername().equals(username))&&(restaurantUsers.get(i).getPassword().equals(password))) {
				index = i;
				band = true;
			}
		}
		if(rootUser.getUsername().equals(username)&&rootUser.getPassword().equals(password)) {
			index = -2; // The user is the rootUser
		}
		return index;
	}


	//Product Options

	@SuppressWarnings("UnusedReturnValue")
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

	private void addToproductsWithTheirSizes(String name,PlateType pt,ArrayList<Ingredient> ingrdnts,ArrayList<String> productSizes,ArrayList<Double> sizesPrices) {
		for(int i=0;i<productSizes.size();i++) {
			productsWithTheirSizes.add(new Product(name,currentUser,pt,ingrdnts,productSizes,sizesPrices,i));
			productsWithTheirSizesSize++;
		}
	}

	public int productIndexWithName(String name) {	//Use it when you have the product name but not the product itself
		ProductInsertionSortByName();
		return binarySearch(restaurantProducts,name);
	}
	public int productWithSizeIndexWithNameAndSize(String name,String size) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(productsWithTheirSizes.get(i).getName().equalsIgnoreCase(name) && productsWithTheirSizes.get(i).getProductActualSize().equalsIgnoreCase(size)) {
				return 1;
			}
		}
		return -1;
	}

	public int productIndexWithProduct(Product product) { //Use it when you have the product itself
		return restaurantProducts.indexOf(product);
	}

	@SuppressWarnings("UnusedReturnValue")
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

	public boolean OrderHasTheProduct(Product product) {
		for(int i=0;i<restaurantOrdersSize;i++) {
			ArrayList<Product> pdcts = restaurantOrders.get(i).getOrderProducts();
			if(pdcts.contains(product)) {
				return true;
			}
		}
		return false;
	}

	private void deleteInproductsWithTheirSizes(Product product) {
		ArrayList<Product> productsToDelete = new ArrayList<>();
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsToDelete.add(productsWithTheirSizes.get(i));
			}
		}
		for (Product value : productsToDelete) {
			productsWithTheirSizes.remove(value);
			productsWithTheirSizesSize--;
		}
	}

	@SuppressWarnings("UnusedReturnValue")
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

	private void disableInproductsWithTheirSizes(Product product) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setEnabled(false);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	@SuppressWarnings("UnusedReturnValue")
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

	private void enableInproductsWithTheirSizes(Product product) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setEnabled(true);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	public void ProductInsertionSortByName(){                                            
		int i;
		int j;
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

	@SuppressWarnings("UnusedReturnValue")
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

	private void changeNameInproductsWithTheirSizes(Product product,String name) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setName(name);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	@SuppressWarnings("UnusedReturnValue")
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

	private void changePlateTypeInproductsWithTheirSizes(Product product,PlateType pt) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).setPt(pt);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
			}
		}
	}

	@SuppressWarnings("UnusedReturnValue")
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
	
	public void deleteAllIngredients(int index) {
		restaurantProducts.get(index).deleteAllIngredients();
	}

	private void deletesAnIngredientInproductsWithTheirSizes(Product product,String name) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.get(i).deleteAnIngredient(name);
				productsWithTheirSizes.get(i).setModifierUser(currentUser);
				
			}
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeSizeOfTheProduct(int index,String oldSize,String newSize) {
		if(index!=-1) {
			int sizeIndex = sizeIndexWithName(restaurantProducts.get(index).getProductsSizes(),oldSize);
			if(sizeIndex!=-1) {
				int sizeIsRepeated = sizeIndexWithName(restaurantProducts.get(index).getProductsSizes(),newSize);
				if(sizeIsRepeated==-1) {
					restaurantProducts.get(index).getProductsSizes().set(sizeIndex, newSize);
					changeRespectiveSize(restaurantProducts.get(index).getName(),oldSize,sizeIndex);
				}
			}
		}
		return false;
	}
	
	private void changeRespectiveSize(String name,String size,int sizeIndex) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(productsWithTheirSizes.get(i).getName().equalsIgnoreCase(name)&&productsWithTheirSizes.get(i).getProductActualSize().equalsIgnoreCase(size)) {
				productsWithTheirSizes.get(i).setProductActualSize(sizeIndex);
			}
		}
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changePriceOfTheProduct(int index,String size,Double newPrice) {
		if(index!=-1) {
			int sizeIndex = sizeIndexWithName(restaurantProducts.get(index).getProductsSizes(),size);
			if(sizeIndex!=-1) {
				restaurantProducts.get(index).getSizesPrices().set(sizeIndex, newPrice);
				//ch
			}
		}
		return false;
	}
	
	public void changeRespectivePrice(String name,String size,int sizeIndex) {
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(productsWithTheirSizes.get(i).getName().equalsIgnoreCase(name)&&productsWithTheirSizes.get(i).getProductActualSize().equalsIgnoreCase(size)) {
				productsWithTheirSizes.get(i).setProductPrice(sizeIndex);
			}
		}
	}
	

	//Ingredients Methods

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

	public int ingredientIndexWithName(String name) {	//Use it when you have the ingredient name but not the ingredient itself
		selectionSortIngredients();
		return binarySearch(restaurantIngredients,name);
	}

	public int ingredientIndexWithIngredient(Ingredient ingredient) { //Use it when you have the ingredient itself
		return restaurantIngredients.indexOf(ingredient);
	}

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

	public boolean productHasTheIngredient(Ingredient ingredient) {
		for(int i=0;i<restaurantProductsSize;i++) {
			ArrayList<Ingredient> ingredients = restaurantProducts.get(i).getIngrdnts();
			if(ingredients.contains(ingredient)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean enableIngredient(int index) {
		if(index!=-1) {
			restaurantIngredients.get(index).setEnabled(true);
			restaurantIngredients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean disableIngredient(int index) {
		if(index!=-1) {
			restaurantIngredients.get(index).setEnabled(false);
			restaurantIngredients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
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

	public int plateTypeIndexWithName(String name) {	//Use it when you have the plateType name but not the plateType itself
		collectionSortPlateTypes();
		return binarySearch(restaurantPlateTypes,name);
	}

	public int plateTypeIndexWithplateType(PlateType plateType) { //Use it when you have the plateType itself
		return restaurantPlateTypes.indexOf(plateType);
	}

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

	public boolean productHasThePlateType(PlateType plateType) {
		for(int i=0;i<restaurantProductsSize;i++) {
			if(restaurantProducts.get(i).getPt().equalsIgnoreCase(plateType.getName())) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean enablePlateType(int index) {
		if(index!=-1) {
			restaurantPlateTypes.get(index).setEnabled(true);
			restaurantPlateTypes.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean disablePlateType(int index) {
		if(index!=-1) {
			restaurantPlateTypes.get(index).setEnabled(false);
			restaurantPlateTypes.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
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
		String auxValueToSearch = valueToSearch.toLowerCase();
		for (int i = 0; i < restaurantClientsSize; i++){
			String valueOfArrayList = restaurantClients.get(i).getLastname()+restaurantClients.get(i).getName();
			String auxValueOfArrayList = valueOfArrayList.toLowerCase();
			if ( !found && (auxValueToSearch.compareTo(auxValueOfArrayList)) == 0){
				found = true;
				indexToInsert = -1;
			}
			else if(!found && (auxValueToSearch.compareTo(auxValueOfArrayList)) >= 0) {
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

	public void sortClientsByLastnameAndName() {   //Note: This method should be used because when the first or last name is modified, the order of these in the arrayList is changed.
		Comparator<Client> nylnComparator = new LastNameAndNameComparator();
		Collections.sort(restaurantClients,nylnComparator);
	}

	public int binarySearchForClients(ArrayList<Client> aL,String name,String lastname) {
		sortClientsByLastnameAndName();
		long startTime = System.nanoTime();
		int pos = -1;
		int i = 0;
		int j = aL.size()-1;
		String valueToSearch = (lastname+name).toLowerCase();
		while(i<=j) { //<--
			int m = (i+j)/2;
			String stringOfArrayList = (aL.get(m).getLastname()+aL.get(m).getName()).toLowerCase();
			if(stringOfArrayList.equalsIgnoreCase(valueToSearch)) {
				return m;
				
			}
			else if(stringOfArrayList.compareTo(valueToSearch)<0){   ///Changed because is descending
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		long endTime = System.nanoTime();
		timeOfSearch= endTime - startTime;
		return pos;
	}

	public int clientIndexWithNameAndLastname(String name,String lastname) {	//Use it when you have the client name but not the client itself
		return binarySearchForClients(restaurantClients,name,lastname);
	}

	public int clientIndexWithClient(Client client) { //Use it when you have the client itself
		return restaurantClients.indexOf(client);
	}

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

	@SuppressWarnings("UnusedReturnValue")
	public boolean enableClient(int index) {
		if(index!=-1) {
			restaurantClients.get(index).setEnabled(true);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean disableClient(int index) {
		if(index!=-1) {
			restaurantClients.get(index).setEnabled(false);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeClientId(int index,String newId) {
		if(index!=-1) {
			restaurantClients.get(index).setId(newId);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}


	public boolean changeClientAddress(int index,String newAddress) {
		if(index!=-1) {
			restaurantClients.get(index).setAddress(newAddress);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeClientPhoneNumber(int index,String newPhoneNumber) {
		if(index!=-1) {
			restaurantClients.get(index).setPhoneNumber(newPhoneNumber);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeClientObservations(int index,String newObservations) {
		if(index!=-1) {
			restaurantClients.get(index).setObservations(newObservations);
			restaurantClients.get(index).setModifierUser(currentUser);
			return true;
		}
		return false;
	}

	//Employee methods

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
	public int employeeIndexWithId(String id) {
		return binarySearchForEmployees(restaurantEmployees,id);
	}

	public int employeeIndexWithemployee(Employee employee) { //Use it when you have the employee itself
		return restaurantEmployees.indexOf(employee);
	}

	public int binarySearchForEmployees(ArrayList<?> aL,String id) {
		int pos = -1;
		int i = 0;
		int j = aL.size()-1;
		while(i<=j && pos<0) {
			int m = (i+j)/2;
			if (((Employee)aL.get(m)).getId().equalsIgnoreCase(id)) pos = m;
			else if(((((Employee)aL.get(m)).getId()).compareTo(id))>0) j = m - 1;
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

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
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

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeEmployeeId(int index,String newId) {
		if(index!=-1) {
			int employeeIsRepeated = employeeIndexWithId(newId);
			if(employeeIsRepeated==-1) {
				Employee changeId=restaurantEmployees.get(index);
				restaurantUsers.get(indexOfAnUser(changeId)).setModifierUser(currentUser);
				restaurantUsers.get(indexOfAnUser(changeId)).setId(newId);
				restaurantEmployees.get(index).setId(newId);
				restaurantEmployees.get(index).setModifierUser(currentUser);
				return true;
			}
		}
		return false;
	}
	 
	//Order methods

	@SuppressWarnings("UnusedReturnValue")
	public boolean createAnOrder(ArrayList<Product> orderProducts,ArrayList<Integer> productsQuantity,Client orderClient,Employee orderEmployee,String observations) {
		//boolean validQuantitys = productsQuantity.size()==orderProducts.size();
		int count1 = productsQuantity.size();
		int count2 = orderProducts.size();
		int areTheSame = count1-count2;
		if(orderProductsAreEnabled(orderProducts) && areTheSame==0 && orderClient.getEnabled() && orderEmployee.getEnabled()) {
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
		for (Product product : p) {
			if (!product.getEnabled()) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean changeOrderStatus(int index,int indicator) {
		if(index!=-1) {
			String check = restaurantOrders.get(index).getOrderStatus().trim();
			boolean unCanceledAndUnDelivered = !check.equals("ENTREGADO") && !check.equals("CANCELADO");
			if(unCanceledAndUnDelivered) {
				restaurantOrders.get(index).setStatusIndicator(indicator);
				if(restaurantOrders.get(index).getOrderStatus().equals("ENTREGADO")) {
					double priceOfTheOrder = restaurantOrders.get(index).getPriceOfTheOrder();
					int employeeIndex = employeeIndexWithemployee(restaurantOrders.get(index).getOrderEmployee());
					restaurantEmployees.get(employeeIndex).addAnOrderDelivered();
					restaurantEmployees.get(employeeIndex).addAPriceOfAnOrder(priceOfTheOrder);
					ArrayList<Product> products = restaurantOrders.get(index).getOrderProducts();
					ArrayList<Integer> quantities = restaurantOrders.get(index).getProductsQuantity();
					addTimesRequestedToProducts(products, quantities);
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

	public void setSearchResults(String match) {
		long startTime = System.nanoTime();
		searchResults = new ArrayList<>();
		for (Client c : restaurantClients) {
			String compare = c.getName() + " " + c.getLastname();
		 	if (compare.contains(match)) searchResults.add(c);
		}
		long endTime = System.nanoTime();
		timeOfSearch= endTime-startTime;
	}


	//Getters

	public ArrayList<Client> getSearchResults() { 
		return searchResults;
	}
	public ArrayList<Product> getRestaurantProducts() {
		return restaurantProducts;
	}
	public String getRestaurantProductsString() {
		StringBuilder info= new StringBuilder();
		for(int i = 0;i<restaurantProductsSize;i++) {
			info.append(restaurantProducts.get(i).showInformation()).append(",");
		}
		return info.toString();
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
		StringBuilder info= new StringBuilder();
		for(int i = 0;i<restaurantIngredientsSize;i++) {
			info.append(restaurantIngredients.get(i).showInformation()).append(",");
		}
		return info.toString();
	}
	public int getRestaurantIngredientsSize() {
		return restaurantIngredientsSize;
	}
	public ArrayList<PlateType> getRestaurantPlateTypes() {
		return restaurantPlateTypes;
	}
	public String getRestaurantPlateTypesString() {
		StringBuilder info= new StringBuilder();
		for(int i = 0;i<restaurantPlateTypesSize;i++) {
			info.append(restaurantPlateTypes.get(i).showInformation()).append(",");
		}
		return info.toString();
	}
	public int getRestaurantPlateTypesSize() {
		return restaurantPlateTypesSize;
	}
	public long getTimeOfSearch() {
		return timeOfSearch;
	}
	public ArrayList<Employee> getRestaurantEmployees() {return restaurantEmployees;}
	public int getRestaurantEmployeesSize() {
		return restaurantEmployeesSize;
	}
	public ArrayList<User> getRestaurantUsers(){
		return restaurantUsers;
	}
	
	public ArrayList<Client> getRestaurantClients() {
		return restaurantClients;
	}
	public int getRestaurantClientsSize() {
		return restaurantClientsSize;
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

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
}