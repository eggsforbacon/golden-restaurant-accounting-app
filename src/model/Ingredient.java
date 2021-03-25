package model;
public class Ingredient extends SystemObject{

	public Ingredient(String name,User creatorUser) {
		super(name,creatorUser);
	}

	@Override
	public String showInformation() {
		String info = "";
		info+=getName();
		return info;
	}
}