package model;
public class Ingredient extends SystemObject{

	public Ingredient(String name) {
		super(name);
	}

	@Override
	public String showInformation() {
		String info = "";
		info+=getName();
		return info;
	}
}