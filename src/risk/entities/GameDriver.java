package risk.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameDriver {
	Graph graph;
	List<Player> players;
	Player currentPlayer;
	public GameDriver() {
		graph=Graph.getGraphInstance();
	}
	
	public void setPlayers(int numberOfPlayers) {
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
	}
}
