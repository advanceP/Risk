package risk.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * this class represent the player of the Game it contains :
 * <ul>
 * <li> name
 * <li> numberOfCountries
 * <li>state
 * <li>color
 * <li>reinforcement
 * </ul>
 * @author Farid Omarzadeh
 *
 */
public class Player 
{
	private String name;
	private int numberOfCountries;
	private String state;
	private Color color;
	private int reinforcement;

	
	/**
	 * returns the number of reinforcement for the player
	 * @return an Integer
	 */
	public int getReinforcement() 
	{
		return reinforcement;
	}
	
	
	/**
	 * Decrease the Number of Player's Reinforcement
	 */
	public void decreaseReinforcement()
	{
		this.reinforcement--;
	}
	
	
	/**
	 * Increase the Number of Player's reinforcement by One
	 */
	public void increaseReinforcement()
	{
		this.reinforcement++;
	}
	
	
	/**
	 * increase the Number of Reinforcement
	 * @param army number of reinforcement to be increased
	 */
	public void increaseReinforcement(int army)
	{
		this.reinforcement+=army;
	}
	
	
	/**
	 * set Number of Reinforcement
	 * @param reinforcement Integer
	 */
	public void setReinforcement(int reinforcement) 
	{
		this.reinforcement = reinforcement;
	}
	
	
	/**
	 * returns the Player's Name
	 * @return a String
	 */
	public String getName() 
	{
		return name;
	}
	
	
	/**
	 * set the Player's Name
	 * @param name String
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	
	/**
	 * returns the number of Countries that player owns
	 * @return an Integer
	 */
	public int getNumberOfCountries() 
	{
		return numberOfCountries;
	}
	
	
	/**
	 * Increases the Number of Countries that player owns by One
	 */
	public void increaseNumberOfCountries()
	{
		this.numberOfCountries++;
		
	}
	
	
	/**
	 * Increase the Number of Countries a Player owns
	 * @param numberOfCountries How much should be increased
	 */
	public void setNumberOfCountries(int numberOfCountries) 
	{
		this.numberOfCountries = numberOfCountries;
	}
	
	
	/**
	 * returns the Player's State i.e. Reinforcement, StartUp, Fortification, Attack etc.
	 * @return a String
	 */
	public String getState() 
	{
		return state;
	}
	
	
	/**
	 * set the Player's State. i.e Reinforcement, StartUp, Fortification, Attack etc.
	 * @param state Player's state
	 */
	public void setState(String state) 
	{
		this.state = state;
	}
	
	
	/**
	 * returns the Player's Color
	 * @return Color
	 */
	public Color getColor() 
	{
		return color;
	}
	
	
	/**
	 * set the Player's Color
	 * @param color Color
	 */
	public void setColor(Color color) 
	{
		this.color = color;
	}
	
	
	/**
	 * Returns the List of Countries that Player Owns
	 * @return a List<Node>
	 */
	public List<Node> getNodeList() 
	{
		return Graph.getGraphInstance().getGraphNodes().stream().filter(item->item.getPlayer().getName().equals(this.name)).collect(Collectors.toList());
	}
	
	
}

