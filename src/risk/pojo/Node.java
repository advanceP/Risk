package risk.pojo;

import java.util.List;

/**
 *This is the node
 */
public class Node {
	private String name;
	private int armies;
	private Continent continentName;
	private int x;  //coordinate for graph
	private int y;  //coordinate for graph
	private List<Node> adjacencyList;
	public Node(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArmies() {
		return armies;
	}
	public void setArmies(int armies) {
		this.armies = armies;
	}
	public Continent getContinentName() {
		return continentName;
	}
	public void setContinentName(Continent continentName) {
		this.continentName = continentName;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public List<Node> getAdjacencyList() {
		return adjacencyList;
	}

	public void setAdjacencyList(List<Node> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	
	
}
