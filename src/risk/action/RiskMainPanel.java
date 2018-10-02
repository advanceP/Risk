package risk.action;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author Admin
 * This is the panel for map editor
 *
 */
public class RiskMainPanel extends JPanel {
	
	private JButton buttonForEdit;
	private JButton buttonForGame;
	private JButton createNewMap;
	private JButton loadExistFile;

	public RiskMainPanel() {
		super();
		setLayout(null);
		initial();
	}

	private void initial() {
		buttonForEdit=new JButton("map editor");
		buttonForEdit.setBounds(280, 100, 400, 100);
		buttonForGame=new JButton("strat game");
		buttonForGame.setBounds(280, 280, 400, 100);
		createNewMap=new JButton("create a new map");
		createNewMap.setBounds(280, 100, 400, 100);
		loadExistFile=new JButton("load from existing file");
		loadExistFile.setBounds(280, 280, 400, 100);
		add(buttonForEdit);
		add(buttonForGame);
		
	}

	public void start() {
		
		buttonForEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==buttonForEdit) {
					intoMapEditor();
				}
				
			}

			private void intoMapEditor() {
				remove(buttonForEdit);
				remove(buttonForGame);
				add(createNewMap);
				add(loadExistFile);
				repaint();
			}
		});
		
		
		
	}

	
	
	
	
}
