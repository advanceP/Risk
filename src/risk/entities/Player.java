package risk.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
	private String name;
	private int numberOfCountries;
	private String state;
	private Color color;
	private int reinforcement;
//	private ArrayList<Node> nodeList=new ArrayList<>();
	
	public int getReinforcement() {
		return reinforcement;
	}
	public void decreaseReinforcement()
	{
		this.reinforcement--;
	}
	public void increaseReinforcement()
	{
		this.reinforcement++;
	}
	public void increaseReinforcement(int army)
	{
		this.reinforcement+=army;
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
	public List<Node> getNodeList() {
		return Graph.getGraphInstance().getGraphNodes().stream().filter(item->item.getPlayer().getName().equals(this.name)).collect(Collectors.toList());
	}
	/*public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}*/
	
}

