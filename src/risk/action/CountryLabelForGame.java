package risk.action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import risk.entities.CountryLabel;
import risk.entities.GameDriver;
import risk.entities.Graph;
import risk.entities.Node;
import risk.entities.Player;
/**
 * this show the label display the country's name on UI,and it will react by click the label
 * @author Hao Chen
 */
public class CountryLabelForGame extends JLabel
{
	
	private Graph graph;
	private GameDriver driver;
	private GamePanel gamePanel;
	private MouseAdapter mouseAdapter;
	private MouseAdapter mouseAdapterForPhase;
	
	/**
	 * constructor<br/>
	 * initialise the member in this class
	 * @param text this will give the country name for 
	 */
	public CountryLabelForGame(String text)
	{
		super(text);
		graph=Graph.getGraphInstance();
		driver=GameDriver.getGameDriverInstance();
		gamePanel=GamePanel.getPanelInstance();
		addListener();
	}

	/**
	 * give the button some reaction,for startup phase, reinforcement and switch to fortify phase
	 */
	public void addListener()
	{
		mouseAdapter=new MouseAdapter()
		{	
			
			public void mouseClicked(MouseEvent e)
			{	
				if(GamePanel.isStartPhase)
				{
					if(driver.getAllArmies()>0)
					{	
						CountryLabelForGame label=(CountryLabelForGame)e.getSource();
						String labelName=label.getText();
						for(Node country:graph.getGraphNodes())
						{
							if(labelName.equals(country.getName()))
							{
								if(country.getPlayer()==driver.getCurrentPlayer())
								{
									if(country.getPlayer().getReinforcement()>0)
									{
										country.increaseArmy();
										driver.changeCurrentPlayer();
									}
									else
									{
										driver.changeCurrentPlayer();
									}
								}
							}
						}
						gamePanel.repaint();
					}
					else
					{
						for(int i=0;i<driver.getPlayers().size();i++)
						{
							driver.getPlayers().get(i).setState("StartUp");
						}
						//removeMouseListener(mouseAdapter);
						//change phase
						GamePanel.isStartPhase=false;
						gamePanel.repaint();
					}
				}
				else
				{
					Player player=driver.getReinforcementPlayer();
					if(player!=null)
					{
						int reinforces=player.getReinforcement();
						if(reinforces>0)
						{
							CountryLabelForGame label=(CountryLabelForGame)e.getSource();
							String labelName=label.getText();
							for(Node country:graph.getGraphNodes())
							{
								if(country.getName().equals(labelName))
								{
									if(country.getPlayer()==player)
									{
										country.increaseArmy();
										gamePanel.repaint();
									}
								}
							}
						}
						else
						{
							player.setState("Fortifition");
							gamePanel.fortifitionPhase();
						}	
					}
					
				}
    	    }	
		};
		addMouseListener(mouseAdapter);
		
	}
	
	
	
		
			
			
				
			
		
	
	
	
	
}
