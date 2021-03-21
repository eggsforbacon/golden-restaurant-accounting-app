package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Restaurant{
	private User rootUser;
	private boolean firstTime;
	private ArrayList<User> users;
	private int usersSize;
	private User currentUser;
	private ArrayList<Product> restaurantProducts;
	private ArrayList<Product> productsWithTheirSizes;
	private int restaurantProductsSize;
	private int productsWithTheirSizesSize;
	private ArrayList<Ingredient> restaurantIngredients;
	private int restaurantIngredientsSize;
	private ArrayList<PlateType> restaurantPlateTypes;
	private int restaurantPlateTypesSize;
	public Restaurant() {
		rootUser = new User("Generic","user","none","Root","admin");
		firstTime = true;
		currentUser = rootUser;
		users = new ArrayList<User>();
		usersSize=users.size();
		restaurantProducts = new ArrayList<Product>();
		productsWithTheirSizes = new ArrayList<Product>();
		restaurantProductsSize = restaurantProducts.size();
		productsWithTheirSizesSize=0;
		restaurantIngredients = new ArrayList<Ingredient>();
		restaurantIngredientsSize=restaurantIngredients.size();
		restaurantPlateTypes = new ArrayList<PlateType>();
		PlateType mainDish = new PlateType("Main dish");
		PlateType sideDish = new PlateType("Side dish");
		PlateType drink = new PlateType("Drink");
		restaurantPlateTypes.add(mainDish);
		restaurantPlateTypes.add(sideDish);
		restaurantPlateTypes.add(drink);
		restaurantPlateTypesSize=restaurantPlateTypes.size();
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
			if(((SystemObject) aL.get(m)).getName().equalsIgnoreCase(name)) {
				pos=m;
			}
			else if((((SystemObject) aL.get(m)).getName().compareTo(name))>0){
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		return pos;
 }
	
	//User options
	
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
			this.currentUser=users.get(index);
			return true;
		}
		else if(index==-2) {
			this.currentUser=rootUser;
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
		for(int i=0;i<usersSize&&!band;i++) {
			if((users.get(i).getUsername().equals(username))&&(users.get(i).getPassword().equals(password))) {
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
			ProductInsertionSortByName();
			int index = productIndexWithName(name);
			if(index==-1&&pt.getEnabled()) {
				Product toAdd = new Product(name,pt,ingrdnts,productSizes,sizesPrices);
				restaurantProducts.add(toAdd);
				addToproductsWithTheirSizes(toAdd);
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
	private void addToproductsWithTheirSizes(Product product) {
		Product pToAdd=product;
		for(int i=0;i<pToAdd.getProductSizesSize();i++) {
			pToAdd.setProductActualSize(i);
			pToAdd.setProductPrice(i);
			productsWithTheirSizes.add(pToAdd);
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
			deleteInproductsWithTheirSizes(deleted);
			restaurantProducts.remove(index);
			restaurantProductsSize--;
			return true;
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
		for(int i=0;i<productsWithTheirSizesSize;i++) {
			if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
				productsWithTheirSizes.remove(i);
				productsWithTheirSizesSize--;
			}
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
			 Product productToChangeName=restaurantProducts.get(index);
			 restaurantProducts.get(index).setName(newName);
			 changeNameInproductsWithTheirSizes(productToChangeName, newName);
			 return true;
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
			 Product productToAddAnIngredient = restaurantProducts.get(index);
			 b=restaurantProducts.get(index).addAnIngredient(ingredient);
			 if(b) {
				 addAnIngredientInproductsWithTheirSizes(productToAddAnIngredient, ingredient); 
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
		private void addAnIngredientInproductsWithTheirSizes(Product product,Ingredient ingredient) {
			for(int i=0;i<productsWithTheirSizesSize;i++) {
				if(product.getName().equalsIgnoreCase(productsWithTheirSizes.get(i).getName())) {
					productsWithTheirSizes.get(i).addAnIngredient(ingredient);
				}
			}
		}
	 
	 /**
	  * Deletes an ingredient of a product of the products ArrayList<br>
	  * <b>Pre: </b>To be useful, there must be at least one product in the ArrayList<br>
	  * <b>Post: </b>Deletes an ingredient of a product of the products ArrayList if there isn't conflicts with it<br>
	  * @param index the index of the product to which an ingredient will be added
	  * @param ingredient The name of the ingredient that will be deleted
	  * @return b True if the ingredient was deleted, false if not
	  */
	 public boolean deleteAnIngredientToAProduct(int index,String ingredientName) {
		 boolean b=false;
		 if(index!=-1) {
			 Product productToDeleteAnIngredient = restaurantProducts.get(index);
			 b=restaurantProducts.get(index).deleteAnIngredient(ingredientName);
			 if(b) {
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
		 selectionSortIngredients();
		 int index = ingredientIndexWithName(name);
		 if(index==-1) {
			 Ingredient toAdd = new Ingredient(name);
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
				 restaurantIngredients.get(index).setName(newName);
				 return true;
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
			 collectionSortPlateTypes();
			 int index = plateTypeIndexWithName(name);
			 if(index==-1) {
				 PlateType toAdd = new PlateType(name);
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
					if(restaurantProducts.get(i).getName().equalsIgnoreCase(plateType.getName())) {
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
					 restaurantPlateTypes.get(index).setName(newName);
					 return true;
				 }
				 return false;
			 }



			
			//Getters
			public ArrayList<Product> getRestaurantProducts() {
				return restaurantProducts;
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
			public int getRestaurantIngredientsSize() {
				return restaurantIngredientsSize;
			}
			public ArrayList<PlateType> getRestaurantPlateTypes() {
				return restaurantPlateTypes;
			}
			public int getRestaurantPlateTypesSize() {
				return restaurantPlateTypesSize;
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
}