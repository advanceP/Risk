package risk.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *This is the node
 */
public class Node {
	private String name;
	private int armies;
	private Continent continent;
	private int x;  //coordinate for graph
	private int y;  //coordinate for graph
	private List<String> adjacencyList=new ArrayList<>();
	private boolean choose;
	private boolean isVisited=false;
	public Node(String name, Continent continent, int x, int y) {
		super();
		this.name = name;
		this.continent = continent;
		this.x = x;
		this.y = y;
	}
	public Node(String name, int x, int y, Continent continent, List<String> adjacencyList) {
		super();
		this.name = name;
		this.continent = continent;
		this.x = x;
		this.y = y;
		this.adjacencyList = adjacencyList;
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
	
	public Continent getContinent() {
		return continent;
	}
	public void setContinent(Continent continent) {
		this.continent = continent;
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

	public List<String> getAdjacencyList() {
		return adjacencyList;
	}
	public void setAdjacencyList(List<String> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	public boolean isChoose() {
		return choose;
	}
	public void setChoose(boolean choose) {
		this.choose = choose;
	}
	
	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}
	public void addToAdjacency(String node){
		this.adjacencyList.add(node);
	}
	
	
}
