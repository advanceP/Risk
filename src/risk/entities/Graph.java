package risk.entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Graph {
	private static Graph graph=null;
	List<Node> graphNodes;
	List<Continent>continents;
	
	
public List<Continent> getContinents() {
		return continents;
	}
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}
public List<Node> getGraphNodes() {
		return graphNodes;
	}
	public void setGraphNodes(List<Node> graphNodes) {
		this.graphNodes = graphNodes;
	}
	private Graph()
	{
		this.graphNodes=new ArrayList<Node>();
		this.continents=new ArrayList<Continent>();
	}
	public static Graph getGraphInstance()
	{
		if(graph==null)
			graph=new Graph();
		return graph;
	}
	public void createContinents(String fileAddress) throws FileNotFoundException
	{
		List<Continent>continentlist=new ArrayList<>();
		File file=new File(fileAddress);
		Scanner input = new Scanner(file);
		String line;
		while(input.hasNextLine())
		{
			line=input.nextLine();
			if(line.contains("[Continents]"))
			{
				while(input.hasNextLine())
				{
				line=input.nextLine();
				if(line.equals(""))
					break;
				List<String>tokens=Arrays.asList(line.split("="));
				Continent continent=new Continent(tokens.get(0), Integer.parseInt(tokens.get(1)));
				continentlist.add(continent);
				}
			}
		}
		setContinents(continentlist);
		
	}
public void createGraph(String fileAddress) throws FileNotFoundException
{
	createContinents(fileAddress);
	List<Node>nodeList = new ArrayList<>() ;
	File file=new File(fileAddress);
	Scanner input = new Scanner(file);
	//ArrayList<String>word=new ArrayList<String>();
	String line;
	while(input.hasNextLine())
	{
		line=input.nextLine();
		if(line.contains("[Territories]"))
		{
			while(input.hasNextLine())
			{
			line=input.nextLine();
			
			List<String>tokens=Arrays.asList(line.split(","));
			List<String>adjacency=tokens.subList(4, tokens.size());
			Continent continent=continents.stream().filter(item->item.getName().equals(tokens.get(3))).findFirst().get();
			Node template=new Node(tokens.get(0),Integer.parseInt(tokens.get(1)),Integer.parseInt(tokens.get(2)),continent,adjacency);
			nodeList.add(template);
		    }
	    }
	}
	setGraphNodes(nodeList);
}
public void addNode(Node node)
{
	graph.graphNodes.add(node);
}
public void connectNodes(Node firstNode,Node secondNode)
{
	firstNode.addToAdjacency(secondNode.getName());
	secondNode.addToAdjacency(firstNode.getName());
}
public void addContinent(Continent continent)
{
	graph.getContinents().add(continent);
}
public void DFS(Graph graph,Node root)
{
	graph.getGraphNodes().stream().filter(item->item.getName().equals(root.getName())).findFirst().get().setVisited(true);
	Iterator<String> i=root.getAdjacencyList().listIterator();
	while(i.hasNext())
	{
		String name=i.next();
		Node src=graph.getGraphNodes().stream().filter(item->item.getName().equals(name)).findFirst().get();
		if(src.isVisited()==false)
		{
			DFS(graph,src);
		}
	}
}
public void setGraphVisited()
{
	for(Node node :Graph.getGraphInstance().getGraphNodes())
	{
		node.setVisited(true);
	}
}
public void setGraphUnvisited()
{
	for(Node node :Graph.getGraphInstance().getGraphNodes())
	{
		node.setVisited(false);
	}
}
public boolean ifGraphVisited()
{
	for(Node node :Graph.getGraphInstance().getGraphNodes())
	{
		if(node.isVisited()==false)
			return false;
	}
	return true;
}

public boolean isGraphConnected()
{
	this.DFS(graph, graphNodes.get(0));
	if(ifGraphVisited())
	{
		setGraphUnvisited();
		return true;
	}
	return false;
}
}

