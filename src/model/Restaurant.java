package model;

import java.util.ArrayList;

public class Restaurant{
	private User rootUser;
	private boolean firstTime;
	private ArrayList<User> users;
	private int usersSize;
	private User currentUser;
	private ArrayList<Product> restaurantProducts;
	private int restaurantProductsSize;
	private ArrayList<Ingredient> restaurantIngredients;
	private ArrayList<PlateType> restaurantPlateTypes;
	public Restaurant() {
		rootUser = new User("Generic","user","none","Root","admin");
		firstTime = true;
		currentUser = rootUser;
		users = new ArrayList<User>();
		usersSize=users.size();
		restaurantProducts = new ArrayList<Product>();
		restaurantProductsSize = restaurantProducts.size();
		restaurantIngredients = new ArrayList<Ingredient>();
		restaurantPlateTypes = new ArrayList<PlateType>();
		PlateType mainDish = new PlateType("Main dish");
		PlateType sideDish = new PlateType("Side dish");
		PlateType drink = new PlateType("Drink");
		restaurantPlateTypes.add(mainDish);
		restaurantPlateTypes.add(sideDish);
		restaurantPlateTypes.add(drink);
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
	
	/**
	 * Adds a product to the products ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Adds a product to the products ArrayList if there isn't conflicts with it<br>
	 * @return True if the product was added, false if not
	 */
	public boolean addProduct(String name,PlateType pt,ArrayList<Ingredient> ingrdnts,ArrayList<String> productSizes,ArrayList<Integer> sizesPrices) {
		productInsertionSort();
		int index = binarySearch(restaurantProducts,name);
		if(index==-1) {
			Product toAdd = new Product(name,pt,ingrdnts,productSizes,sizesPrices);
			restaurantProducts.add(toAdd);
			restaurantProductsSize++;
			return true;
		}
		return false;
	}
	
	
	public boolean deleteProduct(String name) {
		int index=binarySearch(restaurantProducts,name);
		if(index!=-1) {
			restaurantProducts.remove(index);
			restaurantProductsSize--;
			return true;
		}
		return false;
	}
	
	/**
	 * Sorts the products in the ArrayList<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>Now the products in the ArrayList are sorted <br>
	 */
	 public void productInsertionSort(){                                            
		    int i=0;
		    int j=0;
		    Product aux;
		    for (i = 1; i < restaurantProductsSize; i++){
		    	aux = restaurantProducts.get(i);        
		    	j = i - 1;           
		    	while ((j >= 0) && (aux.getName().compareTo(restaurantProducts.get(j).getName())<0)){                             
		    		restaurantProducts.set(j+1,restaurantProducts.get(j));
		    		j--;        
		    	}
		    	restaurantProducts.set(j+1, aux);
		    }
		}
	 
	 /**
		Does a search in the restaurant <br>
		<b> pre: </b><br>
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
}