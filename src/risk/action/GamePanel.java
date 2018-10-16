package risk.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import risk.entities.CountryLabel;
import risk.entities.GameDriver;
import risk.entities.Graph;
import risk.entities.Node;
import risk.entities.Player;

public class GamePanel extends JPanel{
	
	private static Graph graph;
	private int x;
	private int y;
	private List<CountryLabelForGame> labelList;
	private JTextField inputPlayerNumber;
	private JButton setPlayer;
	private GameDriver driver;
	private JTextField phaseText;
	private static GamePanel gamePanel=null;
	private  GamePanel() {
		super();
		setLayout(null);
		graph=Graph.getGraphInstance();
		labelList=new ArrayList<>();
		initial();
		addButtonListener();
		driver=GameDriver.getGameDriverInstance();
	}
	public static GamePanel getPanelInstance()
	{
		if(gamePanel==null)
			gamePanel=new GamePanel();
		return gamePanel;
	}

	private void initial() {
		inputPlayerNumber=new JTextField();
		inputPlayerNumber.setBounds(1150, 200, 150, 30);
		setPlayer=new JButton("setPlayer");
		setPlayer.setBounds(1150, 280, 100, 30);
		phaseText=new JTextField();
		phaseText.setBounds(1120, 200, 200, 200);
		add(inputPlayerNumber);
		add(setPlayer);	
	}
	
	private void startPhase()
	{
		add(phaseText);
		Player currentplayer=driver.getCurrentPlayer();
		phaseText.setText(currentplayer.getName()+" "+driver.getCurrentPlayer().getReinforcement());
		repaint();
		
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawLine(1100,0, 1100, 1000);
		paintPlayer(g);
		paintNode(g);
		paintAdjacency(g);
		paintArmies(g);
		paintPhaseInformation(g);
	}

	private void paintPhaseInformation(Graphics g) {
		if(driver.getPlayers()!=null)
		{
			Player currentplayer=driver.getCurrentPlayer();
			phaseText.setText(currentplayer.getName()+" "+driver.getCurrentPlayer().getReinforcement());
		}
	}
	private void paintArmies(Graphics g) {
		g.setColor(Color.WHITE);
		for(Node country:graph.getGraphNodes()) {
			g.drawString(Integer.toString(country.getArmies()), country.getX()+15, country.getY()+15);
		}
	}


	private void paintPlayer(Graphics g) {
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
				repaint();
			}
		}	
	}


	private void paintNode(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.blue);
		if(x!=0&&y!=0&&x<900) g2.fillOval(x,y, 30, 30);
		for(Node country:graph.getGraphNodes()) {
			if(country.getPlayer()!=null)  g2.setColor(country.getPlayer().getColor());
			g2.fillOval(country.getX(), country.getY(), 30, 30);
			CountryLabelForGame label =new CountryLabelForGame(country.getName());
			label.setBounds(country.getX(), country.getY()+33,120,30);
			add(label);
			labelList.add(label);
		}
	}
	
	private void paintAdjacency(Graphics g) {
		if(!graph.getGraphNodes().isEmpty()) {	
			for(Node node:graph.getGraphNodes()) {
				for(String str:node.getAdjacencyList()) {
					for(Node adjaency:graph.getGraphNodes()) {
						if(str.equals(adjaency.getName())) {
							if(adjaency.getContinent()==node.getContinent()) {
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
	
	
	private void addButtonListener() {
		setPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Integer number=Integer.valueOf(inputPlayerNumber.getText());
				driver.setPlayers(number);
				remove(inputPlayerNumber);
				remove(setPlayer);
				repaint();
				startPhase();
			}
		});
		
	}
	
	
	
	

}
