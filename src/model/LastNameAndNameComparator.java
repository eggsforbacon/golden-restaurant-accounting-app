package model;

import java.util.Comparator;

public class LastNameAndNameComparator implements Comparator<Client>{

	@Override
	public int compare(Client o1, Client o2) {
		String aux1 = (o1.getLastname()+o1.getName()).toLowerCase();
		String aux2 = (o2.getLastname()+o2.getName()).toLowerCase();
		return (aux1.compareTo(aux2))*-1;
	}

}
