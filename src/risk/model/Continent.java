package risk.model;

import java.awt.Color;

/**
 * this class is being used for continent. it has following data members:
 * <ul>
 * <li> name
 * <li>awardUnits
 * <li>color
 * </ul>
 * @author Farid Omarzadeh
 *
 */
public class Continent 
{
	private String name;
	private int awardUnits; 
	private Color color;
	
	/** 
	 * constructor 
	 * @param name This param is for name
	 */
	public Continent(String name) 
	{
		super();
		this.name = name;
	}
	
	/**
	 * constructor
	 * @param name
	 * @param awardUnits
	 */
	public Continent(String name, int awardUnits) 
	{
		super();
		this.name = name;
		this.awardUnits = awardUnits;
		
	}
	
	/**
	 * constructor without any parameters
	 */
	public Continent() 
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * the constructor
	 * @param name
	 * @param awardUnits
	 * @param color
	 */
	public Continent(String name, int awardUnits, Color color) 
	{
		this.name = name;
		this.awardUnits = awardUnits;
		this.color=color;
	}
	
	/**
	 * get method for name
	 * @return the name of the continent
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * set method for name
	 * @param name Name of the continent
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * get method for awardUnits
	 * @return the value of awardUnits
	 */
	public int getAwardUnits() 
	{
		return awardUnits;
	}
	
	/**
	 * set method for awardUnits
	 * @param awardUnits AwardUnits of the continents
	 */
	public void setAwardUnits(int awardUnits) 
	{
		this.awardUnits = awardUnits;
	}
	
	/**
	 * @param color Color of the Continent
	 */
	private void chooseColor(String color) 
	{
		Color decode = Color.decode(color);
		this.color=decode;
	}
	
	/**
	 * 
	 * @param color to give the continents a color
 	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	/**
	 * @return color of the continent
	 */
	public Color getColor() 
	{
		return color;
	}

	@Override
	public String toString() {
		return  name;
	}


}
