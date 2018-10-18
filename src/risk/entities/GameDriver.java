package risk.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameDriver
{
	private static GameDriver gameDriver=null;
	
	Graph graph;
	List<Player> players;
	List<Color>staticColorList;
	
	int index;
	
	
	private GameDriver() 
	{
		index=0;
		graph=Graph.getGraphInstance();
		staticColorList=new ArrayList<Color>();
		staticColorList.add(Color.BLUE);
		staticColorList.add(Color.GREEN);
		staticColorList.add(Color.RED);
		staticColorList.add(Color.YELLOW);
	}
	
	
	public Player getReinforcementPlayer()
	{
		Player reinforcement=getCurrentPlayer();
	//	System.out.println("from begining:"+reinforcement.getName()+"  "+reinforcement.getReinforcement());
		if(reinforcement.getState().equals("StartUp"))
			reinforcement.setReinforcement(0);
		if(reinforcement.getState().equals("Reinforcement")) 
			return reinforcement;
		if(reinforcement.getState().equals("Fortification"))
			return null;
		reinforcement.setState("Reinforcement");
		int additionalreinforcement=reinforcement.getNumberOfCountries()/3+1;
		for(int i=0;i<graph.getContinents().size();i++)
		{
			if(graph.ifContinentConquered(graph.getContinents().get(i)))
			{
				String continentname=graph.getContinents().get(i).getName();
				Node continentnode=graph.getGraphNodes().stream().filter(item->item.getContinent().getName().equals(continentname)).findAny().get();
				if(continentnode.getPlayer().getName().equals(reinforcement.getName()))
					additionalreinforcement+=graph.getContinents().get(i).getAwardUnits();
			}
		}
			
		reinforcement.increaseReinforcement(additionalreinforcement);
		//System.out.println("after:"+reinforcement.getName()+"  "+reinforcement.getReinforcement());
		return reinforcement;
	}
	
	public static GameDriver getGameDriverInstance()
	{
		if(gameDriver==null)
			gameDriver=new GameDriver();
		return gameDriver;
	}
	
	public void setPlayers(int numberOfPlayers) 
	{
		if(numberOfPlayers>4)
			throw new RuntimeException("number of players should be less than 4");
		int colorindex=0;
		Random rnd=new Random();
		int numberofarmies=(graph.getGraphNodes().size())/(numberOfPlayers-1);
		players=new ArrayList<Player>();
		for(int i=0;i<numberOfPlayers;i++)
		{
			Player temporaryplayer=new Player();
			temporaryplayer.setName("Player_"+i);
			players.add(temporaryplayer);
		}
		
		for(int i=0;i<players.size();i++)
			players.get(i).setReinforcement(numberofarmies);
		int playerindex=0;
		Collections.shuffle(graph.getGraphNodes());
		for(int i=0;i<graph.getGraphNodes().size();i++)
		{
			//int index=rnd.nextInt(players.size());
			graph.getGraphNodes().get(i).setPlayer(players.get(playerindex));
			if(playerindex<players.size()-1)
			{
				playerindex++;
			}
			else
			{
				playerindex=0;
			}
		}
		for(int i=0;i<players.size();i++)
		{
			players.get(i).setColor(staticColorList.get(colorindex));
			colorindex++;
		}
		for(int i=0;i<players.size();i++)
		{
			for(int j=0;j<graph.getGraphNodes().size();j++)
				if(graph.getGraphNodes().get(j).getPlayer().getName().equals(players.get(i).getName()))
					players.get(i).increaseNumberOfCountries();
		}
	}

	
	public List<Player> getPlayers() 
	{
		return players;
	}
	
	
	public Player getCurrentPlayer()
	{
		return players.get(index);
	}
	
	
	public void changeCurrentPlayer()
	{
		if(index<players.size()-1)
			index++;
		else {
			index=0;
		}
	}
	
	
	public int getAllArmies()
	{
		int allarmies = 0;
		for(int i=0;i<players.size();i++)
		{
			allarmies+=players.get(i).getReinforcement();
		}
		return allarmies;
	}
	
	
	public void fortify(Node node1, Node node2, int armies) 
	{
		node1.setArmies(node1.getArmies()-armies);
		node2.setArmies(node2.getArmies()+armies);
	}
	
	
	
	
}
