package model;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("Unused")
public class Order extends SystemObject implements Serializable {	

	private static long serialVersionUID = 5;
	
	private Status orderStatus;
	private int statusIndicator;
	private ArrayList<Product> orderProducts;
	private int orderProductsSize;
	private ArrayList<Integer> productsQuantity;
	private Client orderClient;
	private Employee orderEmployee;
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		date = LocalDateTime.now();
		dateString= date.format(formatter);
		this.observations=observations;
	}

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

	public String showProducts() {
		StringBuilder info = new StringBuilder();
		for(int i=0;i<orderProductsSize;i++) {
			info.append(orderProducts.get(i).getName()).append(getSeparator());
			info.append(productsQuantity.get(i)).append(getSeparator());
			info.append(orderProducts.get(i).getProductPrice()).append(getSeparator());
		}
		return info.toString();
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

	public String getObservations() {
		return observations;
	}

	public int getStatusIndicator() {
		return statusIndicator;
	}

	public Client getOrderClient() {
		return orderClient;
	}

	public Employee getOrderEmployee() {
		return orderEmployee;
	}

	public String getOrderEmployeeName() {
		return orderEmployee.name + " " + orderEmployee.getLastname();
	}

	public String getOrderStatus() {
		return orderStatus.toString();
	}

	public String getDateString() {
		return dateString;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public ArrayList<Product> getOrderProducts(){
		return orderProducts;
	}

	public String getOrderProductsString(){
		StringBuilder sb = new StringBuilder();
		for (Product p: orderProducts) {
			sb.append(p.name).append(",");
		}
		return sb.toString();
	}

	public ArrayList<Integer> getProductsQuantity(){
		return productsQuantity;
	}

	public String getProductsQuantityString() {
		StringBuilder sb = new StringBuilder();
		for (int i: productsQuantity) {
			sb.append(i).append(",");
		}
		return sb.toString();
	}
	public Client getOrderclient() {
		return orderClient;
	}

	public String getOrderClientName() {
		return orderClient.name + " " + orderClient.getLastname();
	}

	public double getPriceOfTheOrder() {
		return priceOfTheOrder;
	}

	//Setters

	public void setStatusIndicator(int statusIndicator) {
		if (statusIndicator == 1) {
			this.statusIndicator++;
			System.out.println(this.statusIndicator + "||" + statusIndicator);
		}
		else if (statusIndicator == 0) {
			this.statusIndicator = statusIndicator;
		}
		orderStatus=Status.getS(this.statusIndicator);
	}

	public void setOrderClient(Client orderClient) {
		this.orderClient = orderClient;
	}

	public void setOrderEmployee(Employee orderEmployee) {
		this.orderEmployee = orderEmployee;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
}
