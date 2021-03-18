package model;

import java.util.ArrayList;

public class Product extends SystemObject{
	
	private PlateType pt;
	private ArrayList<Ingredient> ingrdnts;
	private int ingrdntsSize;
	private Sizes productSize;
	private int[] sizesPrices; 

	public Product(String name,PlateType pt,ArrayList<Ingredient> ingrdnts,int[] sizesPrices) {
		super(name);
		this.pt=pt;
		this.ingrdnts=ingrdnts;
		ingrdntsSize = ingrdnts.size();
		this.sizesPrices=sizesPrices;
		
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
		if(searchAnIngredient(check)==-1) {
			ingrdnts.add(ingredient);
			ingrdntsSize++;	
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
	private int searchAnIngredient(String name) {
		int index = -1;
		for(int i=0;i<ingrdntsSize;i++) {
			if(name.equals(ingrdnts.get(i).getName())) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	Deletes an ingredient of the ingredients ArrayList of the product <br>
	<b> pre: </b>It is only useful if the ingredient exists in the ArrayList<br>
	<b> post: </b>The ingredient is deleted if it existed in the first place<br>
	@param name The name of the ingredient that will be deleted
	@return True if the ingredient was deleted, false if not
	*/
	public boolean deleteAnIngredient(String name) {
		int index=searchAnIngredient(name);
		if(index!=-1) {
			ingrdnts.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	Organizes the ingredients in a String <br>
	<b> pre: </b>The ingredients ArrayList has to be initialized<br>
	<b> post: </b>The ingredients are organized in a String<br>
	@return ingredients
	*/
	private String getTheIngredients() {
		StringBuilder ingredients = new StringBuilder();
		for(int i = 0;i<ingrdntsSize;i++) {
			ingredients.append(ingrdnts.get(i)).append(",");
		}
		return ingredients.toString();
	}
	
	public String chooseASize(int indicator) {
		productSize = Sizes.values()[indicator-1];
		String info = productSize+" "+getName();
		return info;
	}
	
	public String priceOfASize(int indicator) {
		int sizePrice = sizesPrices[indicator-1];
		String info = sizePrice+"";
		return info;
	}
	
	@Override
	public String showInformation() {
		String info = "";
		info += getName()+";";	//Name
		info += pt.getName()+";";	//P
		info += getTheIngredients()+";";
		info += Sizes.LARGE+": "+sizesPrices[0]+";";
		info += Sizes.MEDIUM+": "+sizesPrices[1]+";";
		info += Sizes.SMALL+": "+sizesPrices[2]+";";
		
		return info;
	}

	//Getters
	
	/**
	* @return An arrayList that contains the ingredients of the dish.<br>
	*/
	public ArrayList<Ingredient> getIngrdnts() {
		return ingrdnts;
	}
	/**
	* @return An int that indicates the type of the dish.<br>
	*/
	public PlateType getPt() {
		return pt;
	}

	
	//Setters
	
	/**
	 * @param ptIndicator An int that indicates the type of the dish
	 */
	public void setPt(PlateType pt) {
		this.pt = pt;
	}

	
	
	
}