package risk.entities;

import java.awt.Color;

/**
 * 
 * @author Hao Chen <br>
 * This is the class for Continent
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
	
	public Continent(String name, int awardUnits) 
	{
		super();
		this.name = name;
		this.awardUnits = awardUnits;
		
	}
	
	public Continent() 
	{
		// TODO Auto-generated constructor stub
	}

	public Continent(String name, int awardUnits, Color color) 
	{
		this.name = name;
		this.awardUnits = awardUnits;
		this.color=color;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public int getAwardUnits() 
	{
		return awardUnits;
	}
	
	public void setAwardUnits(int awardUnits) 
	{
		this.awardUnits = awardUnits;
	}
	
	private void chooseColor(String color) 
	{
		Color decode = Color.decode(color);
		this.color=decode;
	}
	
	public Color getColor() 
	{
		return color;
	}
	
}
