package model;
public class PlateType extends SystemObject{

	public PlateType(String name) {
		super(name);
	}

	@Override
	public String showInformation() {
		String info = "";
		info+=getName();
		return info;
	}
}	