package main.java.model;

public class Pokemon {
	
	private int id;
	private String name;
	private int baseExperience;
	private int height;
	private boolean isDefault;
	
	public Pokemon() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBaseExperience() {
		return baseExperience;
	}

	public void setBaseExperience(int baseExperience) {
		this.baseExperience = baseExperience;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	@Override
	public String toString() {
		return String.format("Pokemon{id=%d, name ='%s', base_experience=%d, height=%d}", id, name, baseExperience, height);
	}

}
