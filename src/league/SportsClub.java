package league;

public abstract class SportsClub {
	private String name;
	private String location;
	
	//Constructor
	public SportsClub(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}
	
	//Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
