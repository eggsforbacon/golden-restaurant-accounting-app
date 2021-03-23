package model;

import java.util.Comparator;
public class priceComparator implements Comparator<Product> {

	@Override
	public int compare(Product o1, Product o2) {
		if(o1.getProductPrice()>o2.getProductPrice()) {
			return 1;
		}
		else if(o1.getProductPrice()<o2.getProductPrice()) {
			return -1;
		}
		else {
			return o1.getName().compareTo(o2.getName());
		}
	}

}
