package risk.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameDriver {
	private static GameDriver gameDriver=null;
	Graph graph;
	List<Player> players;
	List<Color>staticColorList;
	int index;
	private GameDriver() {
		index=0;
		graph=Graph.getGraphInstance();
		staticColorList=new ArrayList<Color>();
		staticColorList.add(Color.BLUE);
		staticColorList.add(Color.GREEN);
		staticColorList.add(Color.RED);
		staticColorList.add(Color.YELLOW);
	}
	public static GameDriver getGameDriverInstance()
	{
		if(gameDriver==null)
			gameDriver=new GameDriver();
		return gameDriver;
	}
	
	public void setPlayers(int numberOfPlayers) {
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
		for(int i=0;i<graph.getGraphNodes().size();i++)
		{
			int index=rnd.nextInt(players.size());
			graph.getGraphNodes().get(i).setPlayer(players.get(index));
		}
		for(int i=0;i<players.size();i++)
		{
			players.get(i).setColor(staticColorList.get(colorindex));
			colorindex++;
		}
	}

	public List<Player> getPlayers() {
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
	
	
}
