package risk.model;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * this class controls the flow of the game based on the risk game rules, this class uses singleton design pattern
 * it has following data memebers:
 * <ul>
 * <li> graph
 * <li> players
 * <li>staticColorList colors of the players
 * <li>gameDriver a dataType of GameDriver class
 * <li>index contains the index of current player in round robin fashion
 * </ul>
 * @author Farid Omarzadeh
 *
 */
public class GameDriver extends Observable
{
	private static GameDriver gameDriver=null;
	
	Graph graph;
	List<Player> players;
	List<Color>staticColorList;
	
	int index;
	
	
	/**
	 * Constructor , initialize the graph,index,staticColorList
	 */
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
	
	
	/**
	 * this method calculate the amount of armies each player receives based on the number of countries they own
	 *  and continents they conquered
	 * @return Player that will be used in reinforcement phase
	 */
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
		return reinforcement;
	}
	
	/**
	 * this method returns an instance of the GameDriver class, if the instance is null the object will be newed,
	 *  otherwise it will return the already existing instance of the class.
	 * @return gameDriver an instance of the class's dataType
	 */
	public static GameDriver getGameDriverInstance()
	{
		if(gameDriver==null)
			gameDriver=new GameDriver();
		return gameDriver;
	}
	
	/**
	 * this method create players for the game ,randomly assign countries to them, and initialize number of armies for them.
	 * @param numberOfPlayers number of players.
	 */
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
		setChanged();
		notifyObservers();
	}

	
	/**
	 * get method for players
	 * @return List of the Game's players
	 */
	public List<Player> getPlayers() 
	{
		return players;
	}
	
	
	/**
	 * this method returns the current player who is going to play
	 * @return Player that is going to play
	 */
	public Player getCurrentPlayer()
	{
		return players.get(index);
	}
	
	
	/**
	 * this method changes the current player in a round robin fashion
	 */
	public void changeCurrentPlayer()
	{
		if(index<players.size()-1)
			index++;
		else {
			index=0;
		}
	}
	
	
	/**
	 * this method returns the sum of the all armies that each player has
	 * @return integer number of armies
	 */
	public int getAllArmies()
	{
		int allarmies = 0;
		for(int i=0;i<players.size();i++)
		{
			allarmies+=players.get(i).getReinforcement();
		}
		return allarmies;
	}
	
	
	
	
}
