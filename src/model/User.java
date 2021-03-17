package model;

public class User extends Employee{

	private String username;
	private String password;
	
	public User(String name,String lastname,String id,String username,String password) {
		super(name,lastname,id);
		this.username=username;
		this.password=password;
	}


	@Override
	public String showInformation() {
		String info = getName()+";";
		info+= getLastname()+";";
		info += getId()+";";
		info += getUsername()+";";
		info += getPassword()+";";
		return info;
	}
	
	//Getters
	/**
	* @return A String that indicates the username.<br>
	*/
	public String getUsername() {
		return username;
	}
	/**
	* @return A String that indicates the password.<br>
	*/
	public String getPassword() {
		return password;
	}
	
	//Setters
	/**
	 * @param username A String that indicates the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @param password A String that indicates the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}