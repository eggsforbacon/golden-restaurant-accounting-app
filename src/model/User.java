package model;

import java.io.Serializable;

public class User extends Employee implements Serializable{

	private static final long serialVersionUID = 1;
	
	private String username;
	private String password;

	public User(String name,User creatorUser,String lastname,String id,String username,String password) {
		super(name,creatorUser,lastname,id);
		this.username=username;
		this.password=password;
	}


	@Override
	public String showInformation() {
		String info = getName()+getSeparator();
		info+= getLastname()+getSeparator();
		info += getId()+getSeparator();
		info += getUsername()+getSeparator();
		info += getPassword()+getSeparator();
		return info;
	}

	//Getters

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	//Setters

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}