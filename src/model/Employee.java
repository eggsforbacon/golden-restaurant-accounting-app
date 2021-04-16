package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Employee extends Person implements Serializable{

	private static final long serialVersionUID = 2;

	private ArrayList<LocalDateTime> dateOfTheOrdersDelivered;
	private ArrayList<Double> priceOfTheOrdersDelivered;
	private int allOrdersDelivered;
	private int specifiedOrdersDelivered;

	public Employee(String name,User creatorUser,String lastname,String id) {
		super(name,creatorUser,lastname,id);
		dateOfTheOrdersDelivered = new ArrayList<>();
		priceOfTheOrdersDelivered = new ArrayList<>();
		allOrdersDelivered=0;
		specifiedOrdersDelivered=0;
	}

	@Override
	public String showInformation() {
		String info = getName()+getSeparator();
		info+= getLastname()+getSeparator();
		info += getId()+getSeparator();
		return info;
	}

	public String showReportInformation(LocalDateTime startDate,LocalDateTime endDate) {
		String info = getName()+getSeparator();
		info+= getLastname()+getSeparator();
		info += getId()+getSeparator();
		double aux = getTotalPriceOfTheOrders(startDate,endDate);
		info += getSpecifiedOrdersDelivered()+getSeparator();
		info += aux+getSeparator();
		return info;
	}
	
	public void addAnOrderDelivered() {
		dateOfTheOrdersDelivered.add(LocalDateTime.now());
		allOrdersDelivered++;
	}

	public void addAPriceOfAnOrder(double priceOfAnOrder) {
		priceOfTheOrdersDelivered.add(priceOfAnOrder);
	}

	public int getAllEmployeeOrdersDelivered() {
		return allOrdersDelivered;
	}

	public double getTotalPriceOfTheOrders(LocalDateTime startDate,LocalDateTime endDate) {
		double total = 0;
		specifiedOrdersDelivered=0;
		for(int i=0;i<allOrdersDelivered;i++) {
			if(dateOfTheOrdersDelivered.get(i).isAfter(startDate) && dateOfTheOrdersDelivered.get(i).isBefore(endDate)) {
				total += priceOfTheOrdersDelivered.get(i);
				specifiedOrdersDelivered++;
			}
		}
		return total;
	}

	public ArrayList<LocalDateTime> getDateOfTheOrdersDelivered(){
		return dateOfTheOrdersDelivered;
	}

	public int getSpecifiedOrdersDelivered() {
		return specifiedOrdersDelivered;
	}
}