package risk.view;

import risk.controller.GameDriverController;
import risk.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

/**
 * This is also is a panel for user play the game 
 * @author Hao CHEN
 */
public class GamePhase extends JPanel implements ItemListener, Observer
{
	private static Graph graph;
	private int x;
	private int y;
	private List<GameLabel> labelList;
	private JTextField inputPlayerNumber;
	private JButton setPlayer;
	private JTextField phaseText;
	private JComboBox<Node> fortifyFrom;
	private JComboBox<Node> fortifyTo;
	private JComboBox<Integer> fortifyArmies;
	private JButton fortify;
	private JButton endPhase;
	private JLabel view1;
	private JLabel view2;
	public JButton attack;
	public JButton retreat;
	public JButton autoFight;
	public JTextArea attackinformation;
	public JButton move;
	public JButton endAttackPhase;
	public JButton exchangeCard;
	private List<JCheckBox> cardList;
	private static GamePhase gamePhase=null;
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
		cardList=new ArrayList<>();
		initial();
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
		phaseText.setBounds(1120, 200, 400, 100);
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
		view1=new JLabel("players world domination view");
		view1.setBounds(1120, 50, 200, 20);
		view2=new JLabel("phase view");
		view2.setBounds(1120, 180, 200, 20);
		attack=new JButton("attack");
		attack.setBounds(1100, 350, 100, 20);
		retreat=new JButton("retreat");
		retreat.setBounds(1210,350, 100, 20);
		autoFight=new JButton("autoFight");
		autoFight.setBounds(1320, 350, 100, 20);
		attackinformation=new JTextArea();
		attackinformation.setLineWrap(true);
		attackinformation.setBounds(1120, 400, 250, 100);
		move=new JButton("move");
		move.setBounds(1120,570, 100, 30);
		endAttackPhase=new JButton("endAttackPhase");
		endAttackPhase.setBounds(1120, 620, 100, 30);
		exchangeCard=new JButton("exchanged");
		exchangeCard.setBounds(1120,570, 100, 30);
		add(inputPlayerNumber);
		add(setPlayer);
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

			if(GameDriverController.getGameDriverInstance().getPlayers()!=null)
			{
				Player currentplayer= GameDriverController.getGameDriverInstance().getCurrentPlayer();
				phaseText.setText(currentplayer.getState()+" "+currentplayer.getName()+
						" "+ GameDriverController.getGameDriverInstance().getCurrentPlayer().getReinforcement());
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
		List<Player> players= GameDriverController.getGameDriverInstance().getPlayers();
		if(players!=null) {
			int x=1120;
			int y=110;
			for(Player player:players)
			{
				g.setColor(player.getColor());
				g.fillOval(x, y, 30, 30);
				g.setColor(Color.WHITE);
				g.drawString(Integer.toString(player.getTotalArmies()), x+15, y+15);
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
	 * when in the repforement,show which palyer and what phase and how many armies on the text
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

	public void removeButtonSetPlayer()
	{
		remove(setPlayer);
		remove(inputPlayerNumber);
	}
	
	/**
	 * going to fortifitionPhase in the menu
	 */
	public void showFortifitionPhase()
	{
		add(fortifyFrom);
		fortifyFrom.addItemListener(this);
		add(fortifyTo);
		add(fortifyArmies);
		add(fortify);
		add(endPhase);
		repaint();
	}

	public void hideFortifitionPhase()
	{
		remove(fortifyFrom);
		remove(fortifyTo);
		remove(fortifyArmies);
		remove(fortify);
		remove(endPhase);
		repaint();
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
	
	
	public void getFortifyArmy(Node node)
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

	public JButton getFortify()
	{
		return fortify;
	}

	public JButton getEndPhase()
	{
		return endPhase;
	}

	public static boolean isIsStartPhase()
	{
		return isStartPhase;
	}

	public List<GameLabel> getLabelList()
	{
		return labelList;
	}


	public void addLabel() {
		for(Node country:graph.getGraphNodes())
		{
			GameLabel label =new GameLabel(country.getName());
			label.setBounds(country.getX(), country.getY()+33,120,30);
			add(label);
			labelList.add(label);
		}
	}


	public void showAttackMenu(Node attacker,Node defender) {
		int[] playerdice=getDiceNumbers(attacker,defender);
		add(attack);
		add(retreat);
		add(autoFight);
		attackinformation.setText(attacker.getName()+" has "+playerdice[0]+"dice "+defender.getName()+" has "+playerdice[1]+" dice");
		add(attackinformation);
		repaint();
	}


	public int[] getDiceNumbers(Node attacker,Node defender)
	{
		int[] list=new int[2];
		int attackerdicenumber=0;
		int defenderdicenumber=0;
		if(attacker.getArmies()<2)
			return null;
		switch(attacker.getArmies())
		{
			case 3:attackerdicenumber=2;break;
			case 2:attackerdicenumber=1;break;
			default:
				attackerdicenumber=3;
		}
		switch(defender.getArmies())
		{
			case 1: defenderdicenumber=1;break;
			default : defenderdicenumber=2;
		}
		list[0]=attackerdicenumber;
		list[1]=defenderdicenumber;
		return list;
	}


	@Override
	public void update(Observable obj, Object o)
	{
		List<Player> players= GameDriverController.getGameDriverInstance().getPlayers();
		if(players!=null) {
			int x = 1120;
			int y = 80;
			for (Player player : players) {
				JLabel playername = new JLabel(player.getName() +" "+ player.getPercentage()+"%");
				playername.setBounds(x, y, 100, 30);
				add(playername);
				JComboBox<Continent> continentsOwnByPlayer=new JComboBox<>();
				continentsOwnByPlayer.setBounds(x, 150, 50, 30);
				for(Continent con:player.getContinents()){
					continentsOwnByPlayer.addItem(con);
				}
				add(continentsOwnByPlayer);
				x=x+100;
			}
		}
		add(view1);
		add(view2);
		add(phaseText);

		if(GameDriverController.getGameDriverInstance().getCurrentPlayer().getState()!=null)
		{
			if(GameDriverController.getGameDriverInstance().getCurrentPlayer().getState().equals("Fortifition"))
			{
				showFortifitionPhase();
			}
		}
		repaint();
	}


	public void showAttackResult(List<List<Integer>> numberofdice) {
		String text="";
		for(List<Integer> countrysequence:numberofdice){
			for(Integer number:countrysequence){
				text+=(number+" ");
			}
			text+="\r\n";
		}
		attackinformation.setText(text);
		repaint();
	}

	public void hideAttackMenu() {
		remove(attack);
		remove(retreat);
		remove(autoFight);
		remove(attackinformation);
		repaint();
	}

	public void moveArmiesToQuest(Node attacker, Node defender) {
		add(fortifyFrom);
		add(fortifyTo);
		add(fortifyArmies);
		add(move);
		fortifyFrom.addItem(attacker);
		fortifyTo.addItem(defender);
		getFortifyArmy(attacker);
		repaint();
	}

	public void hideMoveArmyToQuestMenu() {
		remove(fortifyFrom);
		remove(fortifyTo);
		remove(fortifyArmies);
		remove(move);
		repaint();
	}

    public void createCardView(Player player) {
		List<Card> cards =  player.getCards();
		int x=1120;
		int y=500;
		if(cards!=null) {
			for (Card card : cards) {
				JCheckBox box = new JCheckBox(card.toString());
				box.setBounds(x, y, 60, 20);
				cardList.add(box);
				x+=70;

			}
			add(exchangeCard);
		}
    }

	public List<Card> getSelectCard() {
		List<Card> result=new ArrayList<>();
		for(JCheckBox box:cardList)
		{
			if(box.isSelected())
			{
				String cardname=box.getText();
				Card card=Card.valueOf(cardname);
				result.add(card);
			}
		}
		return result;
	}
}
