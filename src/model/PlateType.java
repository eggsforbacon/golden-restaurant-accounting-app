package model;
public class PlateType extends SystemObject{

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