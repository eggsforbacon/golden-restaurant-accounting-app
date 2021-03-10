package model;

import java.util.ArrayList;

public class Product extends SystemObject{
	
	private PlateType pt;
	private int ptIndicator;
	private ArrayList<Ingredient> ingrdnts;
	private int ingrdntsSize;
	//The attribute "Sizes with their respective prices" has not yet been designed

	public Product(String name,int ptIndicator,ArrayList<Ingredient> ingrdnts) {
		super(name);
		this.setPtIndicator(ptIndicator);
		pt=PlateType.values()[ptIndicator-1];
		this.ingrdnts=ingrdnts;
		ingrdntsSize = ingrdnts.size();
		
	}
	
	public void updatePt() {
		pt=PlateType.values()[ptIndicator-1];
	}
	
	public void addAnIngredient(Ingredient ingredient) {
		ingrdnts.add(ingredient);
		ingrdntsSize++;
	}
	private int searchAnIngredient(String name) {    //Still have to think how are we going to do the add / delete ingredient function
		int index = -1;
		for(int i=0;i<ingrdntsSize;i++) {
			if(name.equals(ingrdnts.get(i).getName())) {
				index = i;
			}
		}
		return index;
	}
	public boolean deleteAnIngredient(String name) {
		int index=searchAnIngredient(name);
		if(index!=-1) {
			ingrdnts.remove(index);
			return true;
		}
		return false;
	}
	
	@Override
	public String showInformation() {
		// WIP
		return null;
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
	public int getPtIndicator() {
		return ptIndicator;
	}

	
	//Setters
	
	/**
	 * @param  An int that indicates the type of the dish
	 */
	public void setPtIndicator(int ptIndicator) {
		this.ptIndicator = ptIndicator;
	}

	
	
	
}