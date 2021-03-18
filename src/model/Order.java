package model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class Order extends SystemObject {	//The attribute "name" of the SystemObject class will act as the attribute "ID"

	private Status orderStatus;
	private int statusIndicator;
	private ArrayList<Product> orderProducts;
	private int arraylistSize;
	private ArrayList<Integer> productsSizes;
	private ArrayList<Integer> productsQuantity;
	private Client orderClient;
	private Employee orderEmployee;
	private String date;
	private String observations;

	public Order(ArrayList<String> IDs,int statusIndicator,ArrayList<Product> orderProducts,ArrayList<Integer> productsSizes,ArrayList<Integer> productsQuantity,Client orderClient,Employee orderEmployee,String observations) {
		super("");
		generateID(IDs);
		this.statusIndicator=statusIndicator;
		orderStatus = Status.values()[statusIndicator-1];
		this.orderProducts=orderProducts;
		arraylistSize = orderProducts.size();
		this.productsSizes = productsSizes;
		this.productsQuantity=productsQuantity;
		this.orderClient=orderClient;
		this.orderEmployee=orderEmployee;
		date= LocalDateTime.now().toString();
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

	/**
	 * Shows the products of the order with their respective size, quantity and size.<br>
	 * <b>Pre: </b><br>
	 * <b>Post: </b>The String with the organized data is returned<br>
	 * @return info
	 */
	public String showProducts() {
		String info = "";
		int indicator = 0;
		for(int i=0;i<arraylistSize;i++) {
			indicator = productsSizes.get(i);
			info += orderProducts.get(i).chooseASize(indicator)+";";
			info += productsQuantity.get(i)+";";
			info += orderProducts.get(i).priceOfASize(indicator)+";";
		}
		return info;
	}
	@Override
	public String showInformation() {
		Client client = getOrderClient();
		Employee employee = getOrderEmployee();
		String info = "";
		info += client.getName()+";";
		info += client.getAddress()+";";
		info += client.getPhoneNumber()+";";
		info += employee.getName()+";";
		info += getDate()+";";
		info += getObservations()+";";
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
	private String getDate() {
		return date;
	}
	
	//Setters
	/**
	 * @param statusIndicator The status indicator of the order
	 */
	public void setStatusIndicator(int statusIndicator) {
		this.statusIndicator = statusIndicator;
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
