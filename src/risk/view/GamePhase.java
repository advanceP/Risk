package risk.view;

import risk.model.GameDriver;
import risk.model.Graph;
import risk.model.Node;
import risk.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This is also is a panel for user play the game 
 * @author Hao CHEN
 */
public class GamePhase extends JPanel implements ItemListener, Observer
{
	
	private static Graph graph;
	private int x;
	private int y;
	private List<CountryLabelForGame> labelList;
	private JTextField inputPlayerNumber;
	private JButton setPlayer;
	private GameDriver driver;
	private JTextField phaseText;
	private JComboBox<Node> fortifyFrom;
	private JComboBox<Node> fortifyTo;
	private JComboBox<Integer> fortifyArmies;
	private JButton fortify;
	private JButton endPhase;
	private static GamePhase gamePhase =null;
	
	public static boolean isStartPhase=true;
	
	
	/**
	 * constructor <br/>
	 * using freelayout
	 */
	private GamePhase()
	{
		super();
		setLayout(null);
		graph=Graph.getGraphInstance();
		labelList=new ArrayList<>();
		initial();
		driver=GameDriver.getGameDriverInstance();
	}
	
	/**
	 * this using singleton to get the instance for GamePhase class
	 * @return gamePhase instance for GamePhase
	 */
	public static GamePhase getPanelInstance()
	{
		if(gamePhase ==null)
			gamePhase =new GamePhase();
		return gamePhase;
	}

	/**
	 * add some button the create the button
	 */
	private void initial()
	{
		inputPlayerNumber=new JTextField();
		inputPlayerNumber.setBounds(1150, 200, 150, 30);
		setPlayer=new JButton("setPlayer");
		setPlayer.setBounds(1150, 280, 100, 30);
		phaseText=new JTextField();
		phaseText.setBounds(1120, 200, 200, 200);
		fortifyFrom=new JComboBox<>();
		fortifyFrom.setBounds(1120, 400, 200, 30);
		fortifyTo=new JComboBox<>();
		fortifyTo.setBounds(1120, 460, 200, 30);
		fortifyArmies=new JComboBox<>();
		fortifyArmies.setBounds(1120, 520, 200, 30);
		fortify=new JButton("fortify");
		fortify.setBounds(1120,570, 100, 30);
		endPhase=new JButton("endPhase");
		endPhase.setBounds(1120, 620, 100, 30);
		add(inputPlayerNumber);
		add(setPlayer);
		for(Node country:graph.getGraphNodes()) {
			CountryLabelForGame label =new CountryLabelForGame(country.getName());
			label.setBounds(country.getX(), country.getY()+33,120,30);
			add(label);
			labelList.add(label);
		}
	}

	/**
	 * let the game going to start phase,change some menu
	 */
	public void startPhase()
	{
		remove(inputPlayerNumber);
		remove(setPlayer);
		repaint();
		add(phaseText);
		Player currentplayer=driver.getCurrentPlayer();
		phaseText.setText(currentplayer.getName()+" "+driver.getCurrentPlayer().getReinforcement());
		repaint();
	}

	/**
	 * paint something on the panel
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		g.drawLine(1100,0, 1100, 1000);
		paintPlayer(g);
		paintNode(g);
		paintAdjacency(g);
		paintArmies(g);
		paintPhaseInformation(g);
	}

	/**
	 * show the phase on the text
	 */
	private void paintPhaseInformation(Graphics g)
	{
		if(!GamePhase.isStartPhase)
		{
			Player player=driver.getReinforcementPlayer();
			String playername=player.getName();
			phaseText.setText(playername+" "+"Reinforcement"+" "+player.getReinforcement());
		}
		if(GamePhase.isStartPhase)
		{
			if(driver.getPlayers()!=null)
			{
				Player currentplayer=driver.getCurrentPlayer();
				phaseText.setText(currentplayer.getName()+" "+driver.getCurrentPlayer().getReinforcement());
			}
		}
	}
	
	/**
	 * show the armies for each node
	 */
	private void paintArmies(Graphics g)
	{
		g.setColor(Color.WHITE);
		for(Node country:graph.getGraphNodes()) {
			g.drawString(Integer.toString(country.getArmies()), country.getX()+15, country.getY()+15);
		}
	}

	/**
	 * show players on the panel
	 */
	private void paintPlayer(Graphics g)
	{
		List<Player> players=driver.getPlayers();
		if(players!=null) {
			int x=1120;
			int y=80;
			for(Player player:players) {
				JLabel playername=new JLabel(player.getName());
				playername.setBounds(x, y, 50, 30);
				add(playername);
				g.setColor(player.getColor());
				g.fillOval(x, y+40, 20, 20); 
				x=x+60;
			}
		}	
	}

	/**
	 * show countries on the node
	 */
	private void paintNode(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		if(x!=0&&y!=0&&x<900) g2.fillOval(x,y, 30, 30);
		for(Node country:graph.getGraphNodes()) {
			if(country.getPlayer()!=null)  g2.setColor(country.getPlayer().getColor());
			g2.fillOval(country.getX(), country.getY(), 30, 30);
		}
	}
	
	/**
	 * draw lines for adjacency country
	 */
	private void paintAdjacency(Graphics g)
	{
		if(!graph.getGraphNodes().isEmpty())
		{	
			for(Node node:graph.getGraphNodes())
			{
				for(String str:node.getAdjacencyList())
				{
					for(Node adjaency:graph.getGraphNodes())
					{
						if(str.equals(adjaency.getName()))
						{
							if(adjaency.getContinent()==node.getContinent())
							{
								Color color=node.getContinent().getColor();
								g.setColor(color);
								g.drawLine(node.getX()+15, node.getY()+15, adjaency.getX()+15, adjaency.getY()+15);
							}
							g.drawLine(node.getX()+15, node.getY()+15, adjaency.getX()+15, adjaency.getY()+15);
						}
					}
				}
			}
		}
	}

	
	/**
	 * when in the repforement,show which palyer and what phase  and how many armies on the text
	 */
	public void showReinforementMenu()
	{
		add(phaseText);
		remove(fortifyFrom);
		remove(fortifyTo);
		remove(fortifyArmies);
		remove(fortify);
		remove(endPhase);
		repaint();
		
	}
	
	/**
	 * going to fortifitionPhase in the menu
	 */
	public void fortifitionPhase()
	{
		remove(phaseText);
		add(fortifyFrom);
		fortifyFrom.addItemListener(this);
		add(fortifyTo);
		add(fortifyArmies);
		searchNodeByPlyaer();
		add(fortify);
		add(endPhase);
		repaint();
	}
	
	
	private void searchNodeByPlyaer()
	{
		fortifyFrom.removeAllItems();
		Player player=driver.getCurrentPlayer();
		for(Node node:player.getNodeList())
		{
			fortifyFrom.addItem(node);
		}
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if(e.getStateChange()==ItemEvent.SELECTED)
		{
			Node node=(Node)fortifyFrom.getSelectedItem();
			getFortifyArmy(node);
			getReachableNode(node);
			repaint();
		}
	}
	
	
	private void getReachableNode(Node node)
	{
		fortifyTo.removeAllItems();
		List<Node> reachableNodes = graph.reachableNodes(node);
		for(Node country:reachableNodes)
		{
			fortifyTo.addItem(country);
		}
	}
	
	
	private void getFortifyArmy(Node node)
	{
		fortifyArmies.removeAllItems();
		int armies=node.getArmies();
		switch (armies)
		{
			case 3:
				fortifyArmies.addItem(2);
			break;
			case 2:
				fortifyArmies.addItem(1);
				break;
			default:
				for(int i=2;i<=armies-1;i++)
				{
					fortifyArmies.addItem(i);
				}
			break;
		}	
	}

	public JTextField getInputPlayerNumber() {
		return inputPlayerNumber;
	}

	public JButton getSetPlayer() {
		return setPlayer;
	}

	public JTextField getPhaseText() {
		return phaseText;
	}

	public JComboBox<Node> getFortifyFrom() {
		return fortifyFrom;
	}

	public JComboBox<Node> getFortifyTo() {
		return fortifyTo;
	}

	public JComboBox<Integer> getFortifyArmies() {
		return fortifyArmies;
	}

	public JButton getFortify() {
		return fortify;
	}

	public JButton getEndPhase() {
		return endPhase;
	}

	public static boolean isIsStartPhase() {
		return isStartPhase;
	}

	public List<CountryLabelForGame> getLabelList() {
		return labelList;
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
