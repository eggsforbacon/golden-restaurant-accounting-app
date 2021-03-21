package model;
import java.util.Comparator;
public class PlateTypeNameComparator implements Comparator<PlateType> {

	@Override
	public int compare(PlateType o1, PlateType o2) {
		return o1.getName().compareTo(o2.getName());
	}	
}
