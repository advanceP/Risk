package risk.entities;
/**
 * 
 * @author Hao Chen <br>
 * This is the class for Continent
 */
public class Continent {

	private String name;	// name of continent
	private int awardUnits; 	// extra number of soldiers got after conquering whole continent
	/** 
	 * constructor 
	 * @param name This param is for continent's name
	 */
	public Continent(String name) {
		super();
		this.name = name;
	}
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
