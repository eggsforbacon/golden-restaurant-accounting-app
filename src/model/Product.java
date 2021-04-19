package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Product extends SystemObject implements Serializable {

	private static final long serialVersionUID = 4;

	private PlateType pt;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<String> productSizes;
	private int productSizesSize;
	private ArrayList<Double> sizesPrices;
	private double productPrice;
	private String productActualSize;
	private ArrayList<Integer> timesItWasRequested;
	private int timesItWasRequestedSize;
	private ArrayList<LocalDateTime> datesOfTimeItWasRequested;
	private ArrayList<Double> pricesPaidForProduct;

	public Product(String name, User creatorUser, PlateType pt, ArrayList<Ingredient> ingrdnts, ArrayList<String> productSizes, ArrayList<Double> sizesPrices, int indicator) {
		super(name, creatorUser);
		this.pt = pt;
		this.ingredients = ingrdnts;
		this.productSizes = productSizes;
		productSizesSize = productSizes.size();
		this.sizesPrices = sizesPrices;
		setProductActualSize(indicator);
		setProductPrice(indicator);
		timesItWasRequested = new ArrayList<>();
		timesItWasRequestedSize = timesItWasRequested.size();
		datesOfTimeItWasRequested = new ArrayList<>();
		pricesPaidForProduct = new ArrayList<>();
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

	public double[] getTotalTimesAndPrice(LocalDateTime startDate, LocalDateTime endDate) {
		double[] timesAndPrice = new double[2];
		int times = 0;
		double price = 0;
		for (int i = 0; i < timesItWasRequestedSize; i++) {
			if (datesOfTimeItWasRequested.get(i).isAfter(startDate) && datesOfTimeItWasRequested.get(i).isBefore(endDate)) {
				times += timesItWasRequested.get(i);
				price += pricesPaidForProduct.get(i);
			}
		}
		timesAndPrice[0] = times;
		timesAndPrice[1] = price;
		return timesAndPrice;
	}

	public boolean addAnIngredient(Ingredient ingredient) {
		String check = ingredient.getName();
		if (binarySearchAnIngredient(check) == -1 && ingredient.getEnabled()) {
			ingredients.add(ingredient);
			return true;
		}
		return false;
	}

	private int binarySearchAnIngredient(String name) {
		ingredientsBubbleSort();
		int pos = -1;
		int i = 0;
		int j = ingredients.size() - 1;
		while (i <= j && pos < 0) {
			int m = (i + j) / 2;
			if (ingredients.get(m).getName().equalsIgnoreCase(name)) {
				pos = m;
			} else if ((ingredients.get(m).getName().compareTo(name)) > 0) {
				j = m - 1;
			} else {
				i = m + 1;
			}
		}
		return pos;
	}

	private void ingredientsBubbleSort() {
		int changes = 0;
		for (int i = 1; i < ingredients.size() && changes > 0; i++) {
			changes = 0;
			for (int j = 0; j < ingredients.size() - i; j++) {
				if (ingredients.get(j).compareTo(ingredients.get(j + 1)) > 0) {
					Ingredient temp = ingredients.get(j);
					ingredients.set(j, ingredients.get(j + 1));
					ingredients.set(j + 1, temp);
					changes++;
				}
			}
		}
	}

	public boolean deleteAnIngredient(String name) {
		if (ingredients.size() > 1) {
			int index = binarySearchAnIngredient(name);
			if (index != -1) {
				ingredients.remove(index);
				return true;
			}
		}
		return false;
	}

	public void deleteAllIngredients() {
		ingredients.clear();
	}

	public String getTheIngredients() {
		StringBuilder ingredients = new StringBuilder();
		for (Ingredient ingrdnt : this.ingredients) {
			ingredients.append(ingrdnt.getName()).append(",");
		}
		ingredients = new StringBuilder(ingredients.substring(0, ingredients.length() - 1));
		return ingredients.toString();
	}

	public String chooseASize() {
		return productActualSize + " " + getName();
	}

	public String sizesInformation() {
		StringBuilder info = new StringBuilder();
		for (int i = 0; i < productSizesSize; i++) {
			info.append(productSizes.get(i)).append(",");
			info.append(sizesPrices.get(i)).append(getSeparator());
		}
		return info.toString();
	}

	@Override
	public String showInformation() {
		String info = "";
		info += getName() + getSeparator();    //Name
		info += pt.getName() + getSeparator();    //P
		info += getTheIngredients() + getSeparator();
		info += sizesInformation();
		info += getProductActualSize();

		return info;
	}

	public String showReportInformation(LocalDateTime startDate, LocalDateTime endDate) {
		String info = "";
		info += getName() + getSeparator();    //Name
		info += pt.getName() + getSeparator();    //Plate Type
		info += getTheIngredients() + getSeparator();  //Ingredients
		double[] pyt = getTotalTimesAndPrice(startDate, endDate);
		info += ((int) pyt[0]) + getSeparator();   //Times requested
		info += pyt[1] + getSeparator();        //Total price
		return info;
	}

	//Getters

	public ArrayList<String> getProductsSizes() {
		return productSizes;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public String getPt() {
		return pt.getName();
	}

	public PlateType getPlateType() {
		return pt;
	}

	public ArrayList<Double> getSizesPrices() {
		return sizesPrices;
	}

	public String getProductActualSize() {
		return productActualSize;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public int getProductSizesSize() {
		return productSizesSize;
	}

	//Setters

	public void setPt(PlateType pt) {
		this.pt = pt;
	}
	public void setProductActualSize(int index) {
		if(index==-1) {
			productActualSize="Aun sin definir";
		}
		else {
			productActualSize=productSizes.get(index);
		}
	}
	public void setProductPrice(int index) {
		if(index==-1) {
			productPrice=0;
		}
		else {
			productPrice=sizesPrices.get(index);
		}
	}
}