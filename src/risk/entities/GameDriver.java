package risk.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameDriver {
	Graph graph;
	List<Player> players;
	List<Color>staticColorList;
	Player currentPlayer;
	public GameDriver() {
		graph=Graph.getGraphInstance();
		staticColorList=new ArrayList<Color>();
		staticColorList.add(Color.BLUE);
		staticColorList.add(Color.GREEN);
		staticColorList.add(Color.RED);
		staticColorList.add(Color.YELLOW);
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
}
