package model;

import java.io.Serializable;

public abstract class Person extends SystemObject implements Serializable {

	private String lastname;
	private String id;
	private static final long serialVersionUID = 1;

	public Person(String name,User creatorUser,String lastname,String id) {
		super(name,creatorUser);
		this.lastname=lastname;
		this.id=id;
	}

	//Getters

	public String getLastname() {
		return lastname;
	}
	public String getId() {
		return id;
	}

	//Setters

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setId(String id) {
		this.id = id;
	}



}