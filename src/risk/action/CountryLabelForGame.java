package risk.action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import risk.entities.CountryLabel;
import risk.entities.GameDriver;
import risk.entities.Graph;
import risk.entities.Node;

public class CountryLabelForGame extends JLabel{
	
	private Graph graph;
	private GameDriver driver;
	private GamePanel gamePanel;
	public CountryLabelForGame(String text) {
		super(text);
		graph=Graph.getGraphInstance();
		driver=GameDriver.getGameDriverInstance();
		gamePanel=GamePanel.getPanelInstance();
		addListener();
	}

	public void addListener()
	{
		MouseAdapter mouseAdapter=new MouseAdapter()
		{		
			public void mouseClicked(MouseEvent e)
			{	
				CountryLabelForGame label=(CountryLabelForGame)e.getSource();
				String labelName=label.getText();
				for(Node country:graph.getGraphNodes())
				{
					if(labelName==country.getName())
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
		};
		addMouseListener(mouseAdapter);	
	}
	
}
