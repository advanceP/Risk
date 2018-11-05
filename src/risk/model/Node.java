package risk.model;

import java.util.ArrayList;
import java.util.List;

/**
 *This is the node
 */

/**
 * this class represent nodes in our graph it contains following data members:
 * <ul>
 * <li>name
 * <li>armies
 * <li>continent
 * <li>player
 * <li> x horizontal coordination of node
 * <li>y vertical coordination of node
 * <li>adjacencyList list of its neighbors
 * <li>choose
 * <li>isVisited
 * </ul>
 * @author Farid Omarzadeh
 *
 */
public class Node
{
	private String name;
	private int armies;
	private Continent continent;
	private Player player;
	
	private int x;  //coordinate for graph
	private int y;  //coordinate for graph
	private List<String> adjacencyList=new ArrayList<>();
	
	private boolean choose;
	private boolean isVisited=false;

	
	/**
	 * constructor
	 * @param name Name of the Country
	 * @param continent Continent blongs to node
	 * @param x x coordination
	 * @param y y coordination
	 */
	public Node(String name, Continent continent, int x, int y)
	{
		super();
		this.name = name;
		this.continent = continent;
		this.x = x;
		this.y = y;
		this.armies=1;
	}
	
	
	/**
	 * constructor
	 * @param name Name of the Country
	 * @param x coordination
	 * @param y coordination
	 * @param continent Continent of the Node
	 * @param adjacencyList List of Neighbors
	 */
	public Node(String name, int x, int y, Continent continent, List<String> adjacencyList) {
		super();
		this.name = name;
		this.continent = continent;
		this.x = x;
		this.y = y;
		this.adjacencyList = adjacencyList;
		this.armies=1;
	}
	
	
	/**
	 * return the node's player
	 * @return a Player
	 */
	public Player getPlayer() 
	{
		return player;
	}
	
	
	/**
	 * set the node's player
	 * @param player Player of Node
	 */
	public void setPlayer(Player player) 
	{
		this.player = player;
	}
	
	
	/**
	 * returns the Name of the Country
	 * @return a String
	 */
	public String getName() 
	{
		return name;
	}
	
	
	/**
	 * set the Name of the Country
	 * @param name Name of the Country
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	
	/**
	 * returns number of armies for the Node
	 * @return an Integer
	 */
	public int getArmies() 
	{
		return armies;
	}
	
	
	/**
	 * set the Number of Armies
	 * @param armies an Integer
	 */
	public void setArmies(int armies) 
	{
		this.armies = armies;
	}
	
	
	/**
	 * Increase the Node's Army by One
	 */
	public void increaseArmy()
	{
		this.armies++;
	}
	
	
	/**
	 * Increase the Node's Army
	 * @param armies number of armies to be added
	 */
	public void increaseArmy(int armies)
	{
		this.armies+=armies;
	}
	
	
	/**
	 * return the continent of the Node
	 * @return a Continent
	 */
	public Continent getContinent() 
	{
		return continent;
	}
	
	
	/**
	 * set the Continent for the Node
	 * @param continent Continent for the Node
	 */
	public void setContinent(Continent continent) 
	{
		this.continent = continent;
	}
	
	
	/**
	 * Returns X coordination
	 * @return an Integer
	 */
	public int getX() 
	{
		return x;
	}
	
	
	/**
	 * set x Coordination of the Node
	 * @param x Integer
	 */
	public void setX(int x) 
	{
		this.x = x;
	}
	
	
	/**
	 * Returns Y coordination of Node
	 * @return an Integer
	 */
	public int getY() 
	{
		return y;
	}
	
	
	/**
	 * set Y coordination of the Node
	 * @param y Integer
	 */
	public void setY(int y) 
	{
		this.y = y;
	}
	

	/**
	 * Returns a List of Neighbors Names
	 * @return List<String>
	 */
	public List<String> getAdjacencyList() 
	{
		return adjacencyList;
	}
	
	
	/**
	 * set the Neighbor list of the node
	 * @param adjacencyList List of Neighbors
	 */
	public void setAdjacencyList(List<String> adjacencyList) 
	{
		this.adjacencyList = adjacencyList;
	}
	
	
	/**
	 * checks whether a node is chosen when clicking
	 * @return a boolean
	 */
	public boolean isChoose() 
	{
		return choose;
	}
	
	
	/**
	 * set the choose attribute
	 * @param choose a boolean
	 */
	public void setChoose(boolean choose) 
	{
		this.choose = choose;
	}
	
	
	/**
	 * checks whether a node is visited in Traversal search
	 * @return a boolean
	 */
	public boolean isVisited() 
	{
		return isVisited;
	}

	
	/**
	 * set the Visibility of a node
	 * @param isVisited boolean
	 */
	public void setVisited(boolean isVisited) 
	{
		this.isVisited = isVisited;
	}
	
	
	/**
	 * add Country name to the nodes neighbors
	 * @param node Country Name
	 */
	public void addToAdjacency(String node)
	{
		this.adjacencyList.add(node);
	}
	
	/**
	 * returns the name of the node
	 * @return a String
	 */
	@Override
	public String toString() 
	{
		return name;
	}
	
	
}
