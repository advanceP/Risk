package risk.entities;

import java.awt.Color;

public class Player {
	private String name;
	private int numberOfCountries;
	private String state;
	private Color color;
	private int reinforcement;
	
	public int getReinforcement() {
		return reinforcement;
	}
	public void setReinforcement(int reinforcement) {
		this.reinforcement = reinforcement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfCountries() {
		return numberOfCountries;
	}
	public void increaseNumberOfCountries(){
		this.numberOfCountries++;
		
	}
	public void setNumberOfCountries(int numberOfCountries) {
		this.numberOfCountries = numberOfCountries;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}

