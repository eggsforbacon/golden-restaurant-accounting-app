package model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Order extends SystemObject {	//The attribute "name" of the SystemObject class will act as the attribute "ID"

	private Status orderStatus;
	private int statusIndicator;
	private ArrayList<Product> orderProducts;
	private ArrayList<Integer> productsSizes;
	private ArrayList<Integer> productsQuantity;
	private Client orderClient;
	private Employee orderEmployee;
	private String date;

	public Order(ArrayList<String> IDs,int statusIndicator,ArrayList<Product> orderProducts,ArrayList<Integer> productsSizes,ArrayList<Integer> productsQuantity,Client orderClient,Employee orderEmployee) {
		super("");
		generateID(IDs);
		this.statusIndicator=statusIndicator;
		orderStatus = Status.values()[statusIndicator-1];
		this.orderProducts=orderProducts;
		this.productsSizes = productsSizes;
		this.productsQuantity=productsQuantity;
		this.orderClient=orderClient;
		this.orderEmployee=orderEmployee;
		date= LocalDateTime.now().toString();
	}

	/**
	 * Generates a unique 8 digit ID for each order.<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>The ID is generated.<br>
	 */
	private void generateID(ArrayList<String> IDs) {
		final char[] ALPHABET = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz").toCharArray();
		StringBuilder sb = new StringBuilder();
		final int LENGTH = 8;
		for (int i = 0; i < LENGTH; i++) {
			sb.append(ALPHABET[new Random().nextInt(ALPHABET.length)]);
		}
		if(IDs.contains(sb.toString())) generateID(IDs);
		else name = sb.toString();
	}

	@Override
	public String showInformation() {
		return null; //Have to work in it
	}
	
	//Getters
	
	public int getStatusIndicator() {
		return statusIndicator;
	}

	public Client getOrderClient() {
		return orderClient;
	}

	public Employee getOrderEmployee() {
		return orderEmployee;
	}
	
	public String getOrderStatus() {
		return orderStatus.toString();
	}
	
	//Setters
	
	public void setStatusIndicator(int statusIndicator) {
		this.statusIndicator = statusIndicator;
	}
	
	public void setOrderClient(Client orderClient) {
		this.orderClient = orderClient;
	}
	
	public void setOrderEmployee(Employee orderEmployee) {
		this.orderEmployee = orderEmployee;
	}
	
	

}
