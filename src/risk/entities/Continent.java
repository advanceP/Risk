package risk.entities;

public class Continent {
	
	private String name;
	private int awardUnits; //When player conqur a continent,he will receive extra units
	
	
	
	public Continent(String name, int awardUnits) {
		super();
		this.name = name;
		this.awardUnits = awardUnits;
	}
	public Continent() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAwardUnits() {
		return awardUnits;
	}
	public void setAwardUnits(int awardUnits) {
		this.awardUnits = awardUnits;
	}
	
	
	
}
