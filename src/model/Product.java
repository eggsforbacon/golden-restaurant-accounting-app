package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Product extends SystemObject implements Serializable{

	private static final long serialVersionUID = 2;
	
	private PlateType pt;
	private ArrayList<Ingredient> ingrdnts;
	private ArrayList<String> productSizes;
	private int productSizesSize;
	private ArrayList<Double> sizesPrices;
	private double productPrice;
	private String productActualSize;
	private ArrayList<Integer> timesItWasRequested;
	private int timesItWasRequestedSize;
	private ArrayList<LocalDateTime> datesOfTimeItWasRequested;
	private ArrayList<Double> pricesPaidForProduct;

	public Product(String name,User creatorUser,PlateType pt,ArrayList<Ingredient> ingrdnts,ArrayList<String> productSizes,ArrayList<Double> sizesPrices,int indicator) {
		super(name,creatorUser);
		this.pt=pt;
		this.ingrdnts=ingrdnts;
		this.productSizes=productSizes;
		productSizesSize = productSizes.size();
		this.sizesPrices=sizesPrices;
		setProductActualSize(indicator);
		setProductPrice(indicator);
		timesItWasRequested=new ArrayList<Integer>();
		timesItWasRequestedSize=timesItWasRequested.size();
		datesOfTimeItWasRequested=new ArrayList<LocalDateTime>();
		pricesPaidForProduct=new ArrayList<Double>();
	}

	
	public void addDateOfRequest() {
		datesOfTimeItWasRequested.add(LocalDateTime.now());
	}
	public void addTimesItWasRequested(int requested) {
		timesItWasRequested.add(requested);
		timesItWasRequestedSize++;
	}
	public void addPricesPaidForProduct(double price) {
		pricesPaidForProduct.add(price);
	}
	public double[] getTotalTimesAndPrice(LocalDateTime startDate,LocalDateTime endDate) {
		double timesAndPrice[] = new double[2];
		int times = 0;
		double price = 0;
		for(int i=0;i<timesItWasRequestedSize;i++) {
			if(datesOfTimeItWasRequested.get(i).isAfter(startDate) && datesOfTimeItWasRequested.get(i).isBefore(endDate)) {
				times+= timesItWasRequested.get(i);
				price+= pricesPaidForProduct.get(i);
			}
		}
		timesAndPrice[0]=times;
		timesAndPrice[1]=price;
		return timesAndPrice;
	}

	/**
	Adds an ingredient to the ingredients ArrayList <br>
	<b> pre: </b>The ingredient doesn't already exist in the ArrayList<br>
	<b> post: </b>The ingredient is added if the precondition is fulfilled<br>
	@param ingredient An ingredient from the restaurant
	@return true If the ingredient was added, false if not
	 */
	public boolean addAnIngredient(Ingredient ingredient) {
		String check = ingredient.getName();
		if(binarySearchAnIngredient(check)==-1 && ingredient.getEnabled()) {
			ingrdnts.add(ingredient);
			return true;
		}
		return false;
	}

	/**
	Searchs an ingredient in the ingredients ArrayList of the product <br>
	<b> pre: </b><br>
	<b> post: </b>The ingredient is found or doesn't exist<br>
	@param name The name of the ingredient that will be searched
	@return index
	 */
	private int binarySearchAnIngredient(String name) {
		ingredientsBubbleSort();
		int pos = -1;
		int i = 0;
		int j = ingrdnts.size()-1;
		while(i<=j && pos<0) {
			int m = (i+j)/2;
			if(ingrdnts.get(m).getName().equalsIgnoreCase(name)) {
				pos=m;
			}
			else if((ingrdnts.get(m).getName().compareTo(name))>0){
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		return pos;
	}

	/**
	Sorts the ingredients ArrayList of the product <br>
	<b> pre: </b><br>
	<b> post: </b>The ingredients are sorted<br>
	 */
	private void ingredientsBubbleSort() {
		int changes = 0;
		for(int i = 1;i<ingrdnts.size()&&changes>0;i++){ 
			changes = 0;
			for(int j=0;j<ingrdnts.size()-i;j++){
				if(ingrdnts.get(j).compareTo(ingrdnts.get(j+1))>0){
					Ingredient temp = ingrdnts.get(j);
					ingrdnts.set(j,ingrdnts.get(j+1));
					ingrdnts.set(j+1,temp);
					changes++;
				}
			}
		} 
	}

	/**
	Deletes an ingredient of the ingredients ArrayList of the product <br>
	<b> pre: </b>It is only useful if the ingredient exists in the ArrayList<br>
	<b> post: </b>The ingredient is deleted if it existed in the first place<br>
	@param name The name of the ingredient that will be deleted
	@return True if the ingredient was deleted, false if not
	 */
	public boolean deleteAnIngredient(String name) {
		if(ingrdnts.size()>1) {
			int index=binarySearchAnIngredient(name);
			if(index!=-1) {
				ingrdnts.remove(index);
				return true;
			}
		}
		return false;
	}

	/**
	Organizes the ingredients in a String <br>
	<b> pre: </b>The ingredients ArrayList has to be initialized<br>
	<b> post: </b>The ingredients are organized in a String<br>
	@return ingredients
	 */
	public String getTheIngredients() {
		String ingredients = "";
		for(int i = 0;i<ingrdnts.size();i++) {
			ingredients += (ingrdnts.get(i).getName()) + (",");
		}
		ingredients = ingredients.substring(0, ingredients.length()-1);
		return ingredients;
	}
	/**
	Defines product size <br>
	<b> pre: </b><br>
	<b> post: </b>Now the product has a specific size <br>
	@return info
	 */
	public String chooseASize() {
		String info = productActualSize+" "+getName();
		return info;
	}


	/**
	Shows the information of the Sizes and their respective prices <br>
	<b> pre: </b><br>
	<b> post: </b>The sizes and their prices are now in a String<br>
	@return info
	 */
	public String sizesInformation() {
		String info = "";
		for(int i=0;i<productSizesSize;i++) {
			info += productSizes.get(i)+",";
			info += sizesPrices.get(i)+getSeparator();
		}
		return info;
	}

	@Override
	public String showInformation() {
		String info = "";
		info += getName()+getSeparator();	//Name
		info += pt.getName()+getSeparator();	//P
		info += getTheIngredients()+getSeparator();
		info += sizesInformation();
		info += getProductActualSize();

		return info;
	}
	
	public String showReportInformation(LocalDateTime startDate,LocalDateTime endDate) {
		String info = "";
		info += getName()+getSeparator();	//Name
		info += pt.getName()+getSeparator();	//Plate Type
		info += getTheIngredients()+getSeparator();  //Ingredients
		double[] pyt = getTotalTimesAndPrice(startDate,endDate);
		info += ((int) pyt[0])+getSeparator();   //Times requested
		info += pyt[1]+getSeparator();		//Total price
		return info;
	}

	//Getters
	
	public ArrayList<String> getProductsSizes(){
		return productSizes;
	}
	
	/**
	 * @return An arrayList that contains the ingredients of the dish.<br>
	 */
	public ArrayList<Ingredient> getIngrdnts() {
		return ingrdnts;
	}
	/**
	 * @return An object of type PlateType that indicates the type of the product.<br>
	 */
	public String getPt() { 
		return pt.getName();
	}
	public PlateType getPlateType() {
		return pt;
	}
	public ArrayList<Double> getSizesPrices(){
		return sizesPrices;
	}
	/**
	 * @return productActualSize A String that indicates the size chosen for the product
	 */
	public String getProductActualSize() {
		return productActualSize;
	}
	/**
	 * @return productPrice A double that indicates the price chosen for the product
	 */
	public double getProductPrice() {
		return productPrice;
	}

	/**
	 * @return productPrice An int that indicates the size of the product sizes ArrayList
	 */
	public int getProductSizesSize() {
		return productSizesSize;
	}


	//Setters

	/**
	 * @param pt An object of type PlateType that indicates the type of the product
	 */
	public void setPt(PlateType pt) {
		this.pt = pt;
	}
	/**
	 * @param index An index that indicates the size chosen for the product
	 */
	public void setProductActualSize(int index) {
		if(index==-1) {
			productActualSize="Aun sin definir";
		}
		else {
			productActualSize=productSizes.get(index);
		}

	}
	/**
	 * @param index An index that indicates the price chosen for the product
	 */
	public void setProductPrice(int index) {
		if(index==-1) {
			productPrice=0;
		}
		else {
			productPrice=sizesPrices.get(index);
		}
	}
	








}