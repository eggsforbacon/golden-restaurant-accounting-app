package model;
public class Client extends SystemObject{

	public Client(String name) {
		super(name);
	}

	@Override
	public String showInformation() {
		String info = getName();
		return info;
	}
	
}