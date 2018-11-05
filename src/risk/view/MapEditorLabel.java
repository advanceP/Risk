package risk.view;

import risk.controller.MapEditorController;
import risk.model.Node;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapEditorLabel extends JLabel
{

	public MapEditorLabel(String countryname) {
		super(countryname);
		initial();
	}


	private void initial() 
	{
		
		MouseAdapter mouseAdapter=new MouseAdapter() 
		{
			
			public void mouseClicked(MouseEvent e) 
			{
				clearChoose();
				MapEditorLabel label=(MapEditorLabel)e.getSource();
				String labelName=label.getText();
				for(Node node: MapEditor.getCountries())
				{
					if(labelName.equals(node.getName())) 
					{
						MapEditorController.getMapEditor().getInputName().setText(node.getName());
						MapEditorController.getMapEditor().getContinents().setSelectedItem(node.getContinent().getName());
						MapEditorController.getMapEditor().showUpdateMenu();
						node.setChoose(true);
					}
				}
    	    }
			
		};
		addMouseListener(mouseAdapter);
	}

	
	public void clearChoose()
	{
		for(Node node: MapEditor.getCountries())
		{
			node.setChoose(false);
		}
	}
	
}
