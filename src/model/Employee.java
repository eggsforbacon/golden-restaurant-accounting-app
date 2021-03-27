package model;

import java.io.Serializable;

public class Employee extends Person implements Serializable{

	private static final long serialVersionUID = 1;
	
	private int ordersDelivered;
	private double totalPriceOfTheOrders;

	public Employee(String name,User creatorUser,String lastname,String id) {
		super(name,creatorUser,lastname,id);
		ordersDelivered=0;
		totalPriceOfTheOrders=0;
	}

	@Override
	public String showInformation() {
		String info = getName()+getSeparator();
		info+= getLastname()+getSeparator();
		info += getId()+getSeparator();
		info += getEmployeeOrdersDelivered()+getSeparator();
		info += getTotalPriceOfTheOrders()+getSeparator();
		return info;
	}
	
	public void addAnOrderDelivered() {
		ordersDelivered++;
	}
	public void addAPriceOfAnOrder(double priceOfAnOrder) {
		totalPriceOfTheOrders+=priceOfAnOrder;
	}
	public int getEmployeeOrdersDelivered() {
		return ordersDelivered;
	}
	public double getTotalPriceOfTheOrders() {
		return totalPriceOfTheOrders;
	}

}