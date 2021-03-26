package model;

import java.io.Serializable;

public class Employee extends Person implements Serializable{

	private static final long serialVersionUID = 1;

	public Employee(String name,User creatorUser,String lastname,String id) {
		super(name,creatorUser,lastname,id);
	}

	@Override
	public String showInformation() {
		String info = getName()+";";
		info+= getLastname()+";";
		info += getId()+";";
		return info;
	}

}