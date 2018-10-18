package risk.action;
/**
 * 
 * @author Admin
 *This is where game start
 */

import javax.swing.JFrame;

public class RiskGame 
{
	
	public static void main(String[] args) 
	{
		JFrame frame=new JFrame("Risk");
		RiskMainPanel mainPanel=new RiskMainPanel(); //initialize map
		
		frame.add(mainPanel);
		frame.setSize(1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		mainPanel.start(frame);
		
	}

	

}
