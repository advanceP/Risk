package risk.controller;

import risk.model.Graph;
import risk.model.Node;
import risk.model.Player;
import risk.view.GameLabel;
import risk.view.GamePhase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

/**
 * this class controls the flow of the game based on the risk game rules, this class uses singleton design pattern
 * it has following data memebers:
 * <ul>
 * <li> graph
 * <li> players
 * <li>staticColorList colors of the players
 * <li>gameDriver a dataType of GameDriverController class
 * <li>index contains the index of current player in round robin fashion
 * </ul>
 * @author Farid Omarzadeh
 *
 */
public class GameDriverController
{
	private static GameDriverController gameDriver=null;
	
	Graph graph;
	List<Player> players;
	List<Color> staticColorList;
	GamePhase view;
	int index;
	private GamePhase gamePhase;
	private String state;
	
	
	
	/**
	 * Constructor , initialize the graph,index,staticColorList
	 */
	private GameDriverController()
	{
		index=0;
		graph=Graph.getGraphInstance();
		staticColorList=new ArrayList<Color>();
		staticColorList.add(Color.BLUE);
		staticColorList.add(Color.GREEN);
		staticColorList.add(Color.RED);
		staticColorList.add(Color.YELLOW);
		view=GamePhase.getPanelInstance();
	}
	
	
	 public void loadFile(String absolutePath) {
	        try {
	            graph.createGraph(absolutePath);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
	 public void startGame() {
	        JFrame risk=new JFrame();
	        gamePhase= GamePhase.getPanelInstance();
	        graph.getColorTOContinent();
	        gamePhase.addLabel();
	        risk.add(gamePhase);
	        risk.setSize(1400,1000);
	        risk.setVisible(true);
	        startupPhase();
	    }

	    public void startupPhase()
	    {
	        state="StartUp";
	        gamePhase.getSetPlayer().addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	                Integer number=Integer.valueOf(gamePhase.getInputPlayerNumber().getText());
	                setPlayers(number);
	                gamePhase.removeButtonSetPlayer();
	            }
	        });

	        for (GameLabel label:gamePhase.getLabelList())
	        {
	            label.addMouseListener(new MouseAdapter()
	            {
	                @Override
	                public void mouseClicked(MouseEvent e)
	                {
	                    super.mouseClicked(e);
	                    if(state.equals("StartUp"))
	                    {
	                        addArmyByPlayer(e);
	                    }
	                }
	            });
	        }
	    }

	    public void addArmyByPlayer(MouseEvent e)
	    {
	        if(getAllArmies()>0)
	        {
	            GameLabel label=(GameLabel)e.getSource();
	            String labelName=label.getText();
	            for(Node country:graph.getGraphNodes())
	            {
	                if(labelName.equals(country.getName()))
	                {
	                    if(country.getPlayer()==getCurrentPlayer())
	                    {
	                        if(country.getPlayer().getReinforcement()>0)
	                        {
	                            country.getPlayer().addReinforcementToNode(country);
	                            changeCurrentPlayer();
	                            if(getAllArmies()==0)
	                            {
	                                state="Reinforcement";
	                                reinforcementPhase();
	                            }
	                        }
	                        else
	                        {
	                            changeCurrentPlayer();
	                        }
	                    }
	                }
	            }
	        }
	    }

	    public void reinforcementPhase() {
	        Player currentPlayer=getCurrentPlayer();
	        currentPlayer.Reinforcement();
	        for (GameLabel label:gamePhase.getLabelList())
	        {
	            label.addMouseListener(new MouseAdapter() {
	                @Override
	                public void mouseClicked(MouseEvent e) {
	                    super.mouseClicked(e);
	                    if(state.equals("Reinforcement"))
	                    {
	                        addReinforement(e,currentPlayer);
	                    }
	                }
	            });
	        }
	    }

	    public void addReinforement(MouseEvent e,Player player)
	    {
	        int reinforces=player.getReinforcement();
	        if(reinforces>0)
	        {
	            GameLabel label=(GameLabel)e.getSource();
	            String labelName=label.getText();
	            for(Node country:graph.getGraphNodes())
	            {
	                if(country.getName().equals(labelName))
	                {
	                    if(country.getPlayer()==player)
	                    {
	                        //country.increaseArmy();
	                        player.addReinforcementToNode(country);
	                    }
	                }
	            }
	        }
	        else
	        {
	            playerFortifition(player);

	        }
	    }

	    public void playerFortifition(Player player) {
	        player.setState("Fortifition");
	    }
	
	
	/**
	 * this method returns an instance of the GameDriverController class, if the instance is null the object will be newed,
	 *  otherwise it will return the already existing instance of the class.
	 * @return gameDriver an instance of the class's dataType
	 */
	public static GameDriverController getGameDriverInstance()
	{
		if(gameDriver==null)
			gameDriver=new GameDriverController();
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
			temporaryplayer.addObserver(view);
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
		for(Player player:players)
		{
			player.setState("StartUp");
		}

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
		view.repaint();
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
