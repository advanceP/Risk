package risk.entities;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import risk.action.FileController;

/**
 * this class holds the graph for the game, this class implements sigleton design pattern.
 * data members:
 * <ul>
 * <li>graphNodes
 * <li>continents
 * </ul>
 * @author Farid Omarzadeh
 *
 */
public class Graph 
{
	private static Graph graph=null;
		
	List<Node> graphNodes;
	List<Continent>continents;
		
	
	/**
	 * constructor
	 */
	private Graph()
	{
			this.graphNodes=new ArrayList<Node>();
			this.continents=new ArrayList<Continent>();
	}
	
	
	/**
	 * this is the set method for continents
	 * @param continents continents of the graph
	 */
	public void setContinents(List<Continent> continents) 
	{
			this.continents = continents;
	}
	
	/**
	 * returns the continents of the graph
	 * @return a list of continents;
	 */
	public List<Continent> getContinents() 
	{
			return continents;
	}
	
	
	/**
	 * this method returns the nodes of the graph
	 * @return a List of graph nodes
	 */
	public List<Node> getGraphNodes() 
	{
			return graphNodes;
	}
	
	
	/**
	 * set the graphs nodes
	 * @param graphNodes a List of Nodes for the graph
	 */
	public void setGraphNodes(List<Node> graphNodes) 
	{
			this.graphNodes = graphNodes;
	}
	
	
	/**
	 * this method returns an instance of the graph class,based on singleton design pattern
	 * @return the graph
	 */
	public static Graph getGraphInstance()
	{
			if(graph==null)
				graph=new Graph();
			return graph;
	}
	
	
	/**
	 * this method reads the text file and creates the graph
	 * @param fileAddress address of the file
	 * @throws FileNotFoundException
	 */
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
	
	
	/**
	 * this method reads a map file and creates continents , it's being used inside CreateGraph method
	 * @param fileAddress address of the file
	 * @throws FileNotFoundException
	 */
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
	
	
	/**
	 * this method adds a node to the graph
	 * @param node Node that will be added to the graph
	 */
	public void addNode(Node node)
	{
		graph.graphNodes.add(node);
	}
	
	
	/**
	 * this methods connects to nodes to each other by calling addToAdjacency method
	 * @param firstNode the First Node
	 * @param secondNode the second Node
	 */
	public void connectNodes(Node firstNode,Node secondNode)
	{
		firstNode.addToAdjacency(secondNode.getName());
		secondNode.addToAdjacency(firstNode.getName());
	}
	
	
	/**
	 * this method add continent to the graph
	 * @param continent A Continent that will be added to the graph
	 */
	public void addContinent(Continent continent)
	{
		graph.getContinents().add(continent);
	}
	
	
	/**
	 * this nodes performs Depth First Search on the Graph
	 * @param graph the graph that DFS will be performed on
	 * @param root the start Node
	 */
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
	
	
	/**
	 * this method mark the nodes of the graph visited
	 */
	public void setGraphVisited()
	{
		for(Node node :Graph.getGraphInstance().getGraphNodes())
		{
			node.setVisited(true);
		}
	}
	
	
	/**
	 * this method mark the nodes of the graph unvisited
	 */
	public void setGraphUnvisited()
	{
		for(Node node :Graph.getGraphInstance().getGraphNodes())
		{
			node.setVisited(false);
		}
	}
	
	
	/**
	 * this method checks whether the graph is visited
	 * @return a boolean
	 */
	public boolean ifGraphVisited()
	{
		for(Node node :Graph.getGraphInstance().getGraphNodes())
		{
			if(node.isVisited()==false)
				return false;
		}
		return true;
	}

	
	/** 
	 * this method checks whether the graph is connected or not
	 * @return a boolean
	 */
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
	
	
	/**
	 * this method checks whether a continent is connected
	 * @param continent Continent to be checked
	 * @return a boolean
	 */
	public boolean ifContinentConquered(Continent continent)
	{
		List<Node>continentnodes=graph.getGraphNodes().stream().filter(item->item.getContinent().getName().equals(continent.getName())).collect(Collectors.toList());
		List<Player>continentPlayers =new ArrayList<Player>();
		
		for(int i=0;i<continentnodes.size();i++)
		{
			continentPlayers.add(continentnodes.get(i).getPlayer());
		}
		if(continentPlayers.size()<1)
		{
			return false;
		}
		boolean allEqual = continentPlayers.stream().distinct().limit(continentPlayers.size()).count() <= 1;
		
		return allEqual;
	}

	
	
	/**
	 * this method returns a list of nodes that can be reached based on their player.
	 * @param root the starting node
	 * @return a List of nodes that can be reached
	 */
	public List<Node>reachableNodes(Node root)
	{
		String playername=root.getPlayer().getName();
		List <Node>reachnodes=new ArrayList<Node>();
		Queue<Node> queue = new LinkedList();	
		Set<Node> visited=new LinkedHashSet<Node>();
		List<Node>adjacencylist=new ArrayList<Node>();
		for(int i=0;i<root.getAdjacencyList().size();i++)
		{
			String adjacentname=root.getAdjacencyList().get(i);
			Node adjacentNode=this.getGraphNodes().stream().filter(item->item.getName().equals(adjacentname)).findFirst().get();
			if(adjacentNode.getPlayer().getName().equals(playername))
			{
		    	adjacencylist.add(adjacentNode);
			}
		}
		queue.addAll(adjacencylist);
		visited.addAll(adjacencylist);
		while(!queue.isEmpty()) 
	    {
	        Node node = queue.poll();
	        List<Node> neighbors = new ArrayList<Node>();
	        for(int i=0;i<node.getAdjacencyList().size();i++)
	    	{
	        	String adjacentname=node.getAdjacencyList().get(i);
	    		Node adjacentNode=this.getGraphNodes().stream().filter(item->item.getName().equals(adjacentname)).findFirst().get();
	    		if(adjacentNode.getPlayer().getName().equals(playername))
	    		{
	    		neighbors.add(adjacentNode);
	    		}
	    	}
	        
	        for (Node neighbor : neighbors)
	        {
	            if (!visited.contains(neighbor))
	            {
	                queue.offer(neighbor);
	                visited.add(neighbor);
	            }
	        }
	    }
	     reachnodes.addAll(visited);
	     reachnodes.remove(root);
	     return reachnodes;
	}
	
	
	/**
	 * this method performs the DFS search on a subset graph
	 * @param nodeList List of the graph's nodes that DFS will be performed on
	 * @param root the starting node
	 */
	public void subSetDFS(List<Node>nodeList,Node root)
	{
		nodeList.stream().filter(item->item.getName().equals(root.getName())).findFirst().get().setVisited(true);
		Iterator<String> i=root.getAdjacencyList().listIterator();
		while(i.hasNext())
		{
			String name=i.next();
			Node src=null;
			if(nodeList.stream().filter(item->item.getName().equals(name)).findAny().isPresent())
			{
				src=nodeList.stream().filter(item->item.getName().equals(name)).findAny().get();
			}
			if(src!=null)
			{
				if(src.isVisited()==false)
				{
					subSetDFS(nodeList,src);
				}
			}
			
		}
	}
	
	
	/**
	 * this method verify whether the graph is valid or not it checks three aspects
	 *  1_ if the graph is connected 2_ if its continents are connected 3_ if the number of countries => number of continents
	 *  @return a boolean
	 */
	public boolean verifyGraph()
	{
		if(checkContinentsNodesNumbers()==false)
		{
		  return false;
		}
		
		if(isGraphConnected()==false)
		{
			return false;
		}
		for(int i=0;i<this.getContinents().size();i++)
		{
			List<Node> continentnodes=getContinentNodes(this.getContinents().get(i));
			if(ifSetConnected(continentnodes)==false)
			{
				return false;
			}
		}
		if(checkContinentsNodesNumbers()==false)
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * this method checks whether a subset graph is connected or not
	 * @param nodeList a subset graph that will be checked.
	 * @return a boolean
	 */
	public boolean ifSetConnected(List<Node>nodeList)
	{
		this.subSetDFS(nodeList, nodeList.get(0));
		if(ifSetVisited(nodeList))
		{
			setSetUnvisited(nodeList);
			return true;
		}
		return false;
	}
	
	
	/**
	 * this method checks whether a sub graph is connected or not
	 * @param nodelist the sub graph that will be checked
	 * @return a boolean
	 */
	public boolean ifSetVisited(List<Node>nodelist)
	{
		for(Node node :nodelist)
		{
			if(node.isVisited()==false)
				return false;
		}
		return true;
	}
	
	
	/**
	 * this method is set a sub graph to unvisited
	 * @param nodelist set the sub graph to unvisited
	 */
	public void setSetUnvisited(List<Node>nodelist)
	{
		for(Node node :nodelist)
		{
			node.setVisited(false);
		}
	}
	
	
	/**
	 * this method returns the countries of a specific continent
	 * @param continent the Continent whose countries will be returned
	 * @return a list of nodes
	 */
	public List<Node> getContinentNodes(Continent continent)
	{
		return this.graphNodes.stream().filter(item->item.getContinent().getName().equals(continent.getName())).collect(Collectors.toList());
	}
	
	
	/**
	 * this method checks whether the number of continents/countries are reasonable
	 * @return a boolean
	 */
	public boolean checkContinentsNodesNumbers()
	{
		int numberofcontinents=this.getContinents().size();
		int numberofnodes=this.getGraphNodes().size();
		if(numberofcontinents<1)
			return false;
		if(numberofnodes<2)
			return false;
		if(numberofnodes>=numberofcontinents)
			return true;
		
		return false;
	}
	
	
	/**
	 * in the initial phase,give every continent a color
	 */
	public void getColorTOContinent()
	{
		ArrayList<Color> colors=new ArrayList<>();
		colors.add(Color.BLACK);
		colors.add(Color.cyan);
		colors.add(Color.DARK_GRAY);
		colors.add(Color.GRAY);
		colors.add(Color.MAGENTA);
		colors.add(Color.PINK);
		colors.add(Color.ORANGE);
		colors.add(Color.LIGHT_GRAY);
		for(int i=0;i<graph.getContinents().size();i++)
		{
			graph.getContinents().get(i).setColor(colors.get(i));
		}
	}
}

