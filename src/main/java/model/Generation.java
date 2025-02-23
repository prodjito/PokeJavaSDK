package main.java.model;

public class Generation {
	private int id;
	private String name;
	
	public Generation() {}
	
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
	
	@Override
	public String toString() {
		return String.format("Generation{id=%d, name ='%s'}", id, name);
	}
}
