package risk.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
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
public class Player extends Observable
{
	private String name;
	private int numberOfCountries;
	private String state;
	private Color color;
	private int reinforcement;
	List<Card> cards=new ArrayList<Card>();
	List<Continent> continents=new ArrayList<Continent>();

	
	public List<Card> getCards() {
		return cards;
	}
	
	
	public void addCards(Card card)
	{
		this.cards.add(card);
	}


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
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
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
		return Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getPlayer().getName().equals(this.name)).collect(Collectors.toList());
	}
	
	public List<Continent>getContinents()
	{
		for(int i=0;i<Graph.getGraphInstance().getContinents().size();i++)
		{
			if(Graph.getGraphInstance().ifContinentConquered(Graph.getGraphInstance().getContinents().get(i)))
			{
				String continentname=Graph.getGraphInstance().getContinents().get(i).getName();
				Node continentnode=Graph.getGraphInstance().getGraphNodes().stream().filter(item->item.getContinent().getName().equals(continentname)).findAny().get();
				if(continentnode.getPlayer().getName().equals(this.name))
					continents.add(Graph.getGraphInstance().getContinents().get(i));
			}
		}
		return continents;
	}
	public void Reinforcement()
	{
		if(this.state.equals("StartUp"))
			this.setReinforcement(0);
		if(this.state.equals("Reinforcement"))
			this.setReinforcement(reinforcement);

		this.setState("Reinforcement");
		int additionalreinforcement=this.getNumberOfCountries()/3+1;
		for(int i=0;i<Graph.getGraphInstance().getContinents().size();i++)
		{
			if(Graph.getGraphInstance().ifContinentConquered(Graph.getGraphInstance().getContinents().get(i)))
			{
				String continentname=Graph.getGraphInstance().getContinents().get(i).getName();
				Node continentnode=Graph.getGraphInstance().getGraphNodes().stream().filter(item->item.getContinent().getName().equals(continentname)).findAny().get();
				if(continentnode.getPlayer().getName().equals(this.name))
					additionalreinforcement+=Graph.getGraphInstance().getContinents().get(i).getAwardUnits();
			}
		}

		increaseReinforcement(additionalreinforcement);
	}
	
	
	public int getPercentage() {
		float result=0;
		result=((float)(this.getNodeList().size())/Graph.getGraphInstance().getGraphNodes().size());
		return (Math.round(result*100));
	}

	public void addReinforcementToNode(Node country) {
		country.increaseArmy();
		decreaseReinforcement();
	}
	
	
	/**
	 * this method is being used in fortification phase it transfer armies from one country to another.
	 * @param node1 the country whose armies will be moved
	 * @param node2 the country who will receive armies
	 * @param armies number of armies that will be transfered
	 */
	public void Fortification(Node from,Node to,int armies)
	{
		from.setArmies(from.getArmies()-armies);
		to.setArmies(to.getArmies()+armies);
		setChanged();
		notifyObservers();
	}
}

