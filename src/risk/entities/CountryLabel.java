package risk.entities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import risk.action.EditorFrame;
/**
 * IT will show the country's name on the label
 *
 */
public class CountryLabel extends JLabel
{
	/**
	 * constructor
	 */
	public CountryLabel(String text) 
	{
		super(text);
		initial();
	}
	
	/**
	 * add some action listener on the button
	 */
	private void initial() 
	{
		
		MouseAdapter mouseAdapter=new MouseAdapter() 
		{
			
			public void mouseClicked(MouseEvent e) 
			{
				clearChoose();
				CountryLabel label=(CountryLabel)e.getSource();
				String labelName=label.getText();
				for(Node node:EditorFrame.getCountries()) 
				{
					if(labelName.equals(node.getName())) 
					{
						EditorFrame.EditMap editor=EditorFrame.getPanelForEdit();
						editor.getInputName().setText(node.getName());
						editor.getContinents().setSelectedItem(node.getContinent().getName());
						editor.showUpdateMenu();
						node.setChoose(true);
					}
				}
    	    }
			
		};
		addMouseListener(mouseAdapter);
	}

	/**
	 * clear the choose for all label
	 */
	private void clearChoose() 
	{
		for(Node node:EditorFrame.getCountries()) 
		{
			node.setChoose(false);
		}
	}
	
}
