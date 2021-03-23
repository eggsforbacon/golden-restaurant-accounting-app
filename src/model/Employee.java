package model;
public class Employee extends Person{

	public Employee(String name,String lastname,String id) {
		super(name,lastname,id);
	}

	@Override
	public String showInformation() {
		String info = getName()+";";
		info+= getLastname()+";";
		info += getId()+";";
		return info;
	}

}