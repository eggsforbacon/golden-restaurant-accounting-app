package model;
public abstract class Person extends SystemObject{

	private String lastname;
	private String id;

	public Person(String name,String lastname,String id) {
		super(name);
		this.lastname=lastname;
		this.id=id;
	}


	//Getters
	/**
	 * @return An String with the lastname of a person.<br>
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @return An String with the id of a person.<br>
	 */
	public String getId() {
		return id;
	}


	//Setters
	/**
	 * @param lastname An String with the lastname of a person.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @param id An String with the id of a person.
	 */
	public void setId(String id) {
		this.id = id;
	}



}