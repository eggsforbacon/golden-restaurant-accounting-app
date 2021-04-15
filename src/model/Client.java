package model;

import java.io.Serializable;

public class Client extends Person implements Serializable{

	private static final long serialVersionUID = 1;
	private String address;
	private String phoneNumber;
	private String observations;

	public Client(String name,User creatorUser,String lastname,String id,String address,String phoneNumber,String observations) {
		super(name,creatorUser,lastname,id);
		this.phoneNumber=phoneNumber;
		this.address=address;
		this.observations=observations;
	}	

	@Override
	public String showInformation() {
		String info = getName()+getSeparator();
		info+= getLastname()+getSeparator();
		info += getId()+getSeparator();
		info += getAddress()+getSeparator();
		info += getPhoneNumber()+getSeparator();
		info += getObservations()+getSeparator();
		return info;
	}

	//Getters

	public String getAddress() {
		return address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getObservations() {
		return observations;
	}

	//Setters

	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}

}