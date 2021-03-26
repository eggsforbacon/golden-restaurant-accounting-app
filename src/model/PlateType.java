package model;

import java.io.Serializable;

public class PlateType extends SystemObject implements Serializable{

	private static final long serialVersionUID = 1;
	
	public PlateType(String name,User creatorUser) {
		super(name,creatorUser);
	}

	@Override
	public String showInformation() {
		String info = "";
		info+=getName();
		return info;
	}
}	