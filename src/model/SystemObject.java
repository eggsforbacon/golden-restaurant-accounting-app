package model;

public abstract class SystemObject implements Comparable<SystemObject>{
	
	private String SEPARATOR=";";
	private boolean enabled;
	protected String name;
	private User creatorUser;
	private User modifierUser;

	public SystemObject(String name,User creatorUser) {
		enabled = true;
		this.name=name;
		this.creatorUser=creatorUser;
		modifierUser=creatorUser;
	}

	@Override
	public int compareTo(SystemObject o) {
		String nameDownCase = name.toLowerCase();
		String objectNameDownCase = o.getName().toLowerCase();
		return nameDownCase.compareTo(objectNameDownCase);
	}

	//Getters

	/**
	 * @return A boolean that indicates if the object is enabled or not.<br>
	 */
	public boolean getEnabled() {
		return enabled;
	}
	public String getEnabledString() {
		String info = "";
		if(getEnabled()) {
			info+="SI";
		}
		else {
			info += "NO";
		}
		return info;
	}
	/**
	 * @return A String that indicates the name.<br>
	 */
	public String getName() {
		return name;
	}
	public User getCreatorUser() {
		return creatorUser;
	}
	public User getModifierUser() {
		return modifierUser;
	}
	public String getSeparator() {
		return SEPARATOR;
	}


	//Setters

	/**
	 * @param enabled The indication that the object is enabled or not
	 */
	public void setEnabled(boolean enabled) {
		this.enabled=enabled;
	}

	/**
	 * @param name The name of the object
	 */
	public void setName(String name) {
		this.name=name;
	}
	public void setModifierUser(User modifierUser) {
		this.modifierUser = modifierUser;
	}
	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}
	public void setSeparator(String separator) {
		this.SEPARATOR=separator;
	}

	public abstract String showInformation();

}