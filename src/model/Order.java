package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Order extends SystemObject implements Serializable {	

	private static final long serialVersionUID = 1;
	
	private Status orderStatus;
	private int statusIndicator;
	private ArrayList<Product> orderProducts;
	private int orderProductsSize;
	private ArrayList<Integer> productsQuantity;
	private Client orderClient;
	private Employee orderEmployee;
	DateTimeFormatter formatter;
	private LocalDateTime date;
	private String dateString;
	private String observations;
	private double priceOfTheOrder;

	public Order(User creatorUser,ArrayList<String> IDs,ArrayList<Product> orderProducts,ArrayList<Integer> productsQuantity,Client orderClient,Employee orderEmployee,String observations) {
		super("",creatorUser); //The attribute "name" of the SystemObject class will act as the attribute "ID"
		generateID(IDs);
		statusIndicator=1;
		orderStatus = Status.values()[statusIndicator];
		this.orderProducts=orderProducts;
		orderProductsSize= orderProducts.size();
		this.productsQuantity=productsQuantity;
		this.orderClient=orderClient;
		this.orderEmployee=orderEmployee;
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		date = LocalDateTime.now();
		dateString= date.format(formatter);
		this.observations=observations;
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
	
	public void calculatePriceOfTheOrder() {
		double price = 0;
		for(int i=0;i<orderProductsSize;i++) {
			price += orderProducts.get(i).getProductPrice() * productsQuantity.get(i);
		}
		this.priceOfTheOrder=price;
	}
	
	/**
	 * Shows the products of the order with their respective size, quantity and size.<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>The String with the organized data is returned<br>
	 * @return info
	 */
	public String showProducts() {
		String info = "";
		for(int i=0;i<orderProductsSize;i++) {
			info += orderProducts.get(i).getName()+getSeparator();
			info += productsQuantity.get(i)+getSeparator();
			info += orderProducts.get(i).getProductPrice()+getSeparator();
		}
		return info;
	}
	@Override
	public String showInformation() {
		Client client = getOrderClient();
		Employee employee = getOrderEmployee();
		String info = "";
		info += getName()+getSeparator();
		info += client.getName()+getSeparator();
		info += client.getAddress()+getSeparator();
		info += client.getPhoneNumber()+getSeparator();
		info += employee.getName()+getSeparator();
		info += getOrderStatus()+getSeparator();
		info += getDateString()+getSeparator();
		info += getObservations()+getSeparator();
		info += showProducts();

		return info;
	}

	//Getters
	/**
	 * @return The observations of the order<br>
	 */
	public String getObservations() {
		return observations;
	}
	/**
	 * @return The status indicator of the order.<br>
	 */
	public int getStatusIndicator() {
		return statusIndicator;
	}
	/**
	 * @return The client who requested the order.<br>
	 */
	public Client getOrderClient() {
		return orderClient;
	}
	/**
	 * @return The employee who made the order.<br>
	 */
	public Employee getOrderEmployee() {
		return orderEmployee;
	}
	/**
	 * @return The status of the order.<br>
	 */
	public String getOrderStatus() {
		return orderStatus.toString();
	}
	/**
	 * @return The date of the order.<br>
	 */
	public String getDateString() {
		return dateString;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public ArrayList<Product> getOrderProducts(){
		return orderProducts;
	}
	public Client getOrderclient() {
		return orderClient;
	}
	public double getPriceOfTheOrder() {
		return priceOfTheOrder;
	}
	//Setters
	/**
	 * @param statusIndicator The status indicator of the order
	 */
	public void setStatusIndicator(int statusIndicator) {
		if(statusIndicator==1) {
			this.statusIndicator = statusIndicator+1;
			orderStatus=Status.values()[statusIndicator];
		}
		else {
			this.statusIndicator = 0;
			orderStatus=Status.values()[statusIndicator];
		}
	}
	/**
	 * @param orderClient The client who requested the order
	 */
	public void setOrderClient(Client orderClient) {
		this.orderClient = orderClient;
	}
	/**
	 * @param orderEmployee The employee who made the order
	 */
	public void setOrderEmployee(Employee orderEmployee) {
		this.orderEmployee = orderEmployee;
	}
	/**
	 * @param observations The observations of the order
	 */
	public void setObservations(String observations) {
		this.observations = observations;
	}
	


}
