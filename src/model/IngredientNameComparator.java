package model;
import java.util.Comparator;
public class IngredientNameComparator implements Comparator<Ingredient> {

	@Override
	public int compare(Ingredient o1, Ingredient o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
