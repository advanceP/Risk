package risk.action;
/**
 * 
 * @author Admin
 *This is where game start
 */

import java.io.FileNotFoundException;

import javax.swing.JFrame;

import risk.entities.Graph;

public class RiskGame 
{
	
	public static void main(String[] args) 
	{
		JFrame frame=new JFrame("Risk");
		RiskMainPanel mainPanel=new RiskMainPanel(); //initional map
		
		frame.add(mainPanel);
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		mainPanel.start();
		
	}

	

}
