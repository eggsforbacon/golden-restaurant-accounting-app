package model;

import java.time.LocalDateTime;
import java.util.Comparator;

public class OrderDateComparator implements Comparator<Order>{

	@Override
	public int compare(Order o1, Order o2) {
		int indicator = 0;
		LocalDateTime o1Date = o1.getDate();
		LocalDateTime o2Date = o2.getDate();
		if(o1Date.isBefore(o2Date)) {
			indicator = -1;
		}
		else if(o1Date.equals(o2Date)) {
			indicator = 0;
		}
		else {
			indicator = 1;
		}
		
		return indicator;
	}

}
