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
	/**
	 * @return An String with the address of a client.<br>
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return An String with the phone number of a client.<br>
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @return An String with the observations of a client.<br>
	 */
	public String getObservations() {
		return observations;
	}

	//Setters
	/**
	 * @param address An String with the address of a person.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @param phoneNumber An String with the phone number of a client.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @param observations An String with the observations of a client.
	 */
	public void setObservations(String observations) {
		this.observations = observations;
	}

}