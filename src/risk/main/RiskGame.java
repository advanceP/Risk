package risk.main;
/**
 * 
 * @author Admin
 *This is where game start
 */

import javax.swing.JFrame;
/**
 * This is where game start,main methods is here
 * @author Hao Chen
 *
 */
public class RiskGame 
{
	/**
	 *this method is create a frame for UI,and add a panel to the frame,add start the menu
	 * @param args
	 */
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
