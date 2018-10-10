package risk.entities;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import risk.action.EditorFrame;

public class CountryLabel extends JLabel{

	public CountryLabel(String text) {
		super(text);
		initial();
	}
	
	
	private void initial() {
		
		MouseAdapter mouseAdapter=new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				clearChoose();
				CountryLabel label=(CountryLabel)e.getSource();
				String labelName=label.getText();
				for(Node node:EditorFrame.getCountries()) {
					if(labelName.equals(node.getName())) {
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
	
	private void clearChoose() {
		for(Node node:EditorFrame.getCountries()) {
			node.setChoose(false);
		}
	}
	
}
