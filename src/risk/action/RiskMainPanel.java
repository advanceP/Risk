package risk.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import risk.entities.Continent;
import risk.entities.Graph;

/**
 * This is the panel for map editor<br/>
 * using free layout for the whole project
 * @author Hao Chen
 */
public class RiskMainPanel extends JPanel
{
	
	private JButton buttonForEdit;
	private JButton buttonForGame;
	private JButton createNewMap;
	private JButton loadExistFile;
	private JButton createContinent;
	private JTextField continentInformation;
	private List<Continent> listContinents;
	private Graph graph;
	/**
	 * overwrite the constructor<br/>
	 * use free layout for the whole project
	 */
	public RiskMainPanel()
	{
		super();
		setLayout(null);
		initial();	
	}

	/**
	 * initialise some buttons,and show them on the menu
	 */
	private void initial() 
	{
		
		graph=Graph.getGraphInstance();
		buttonForEdit=new JButton("map editor");
		buttonForEdit.setBounds(280, 100, 400, 100);
		buttonForGame=new JButton("start game");
		buttonForGame.setBounds(280, 280, 400, 100);
		createNewMap=new JButton("create a new map");
		createNewMap.setBounds(280, 100, 400, 100);
		loadExistFile=new JButton("load from existing file");
		loadExistFile.setBounds(280, 280, 400, 100);
		createContinent=new JButton("create now");
		createContinent.setBounds(730, 250, 150, 30);
		continentInformation=new JTextField();
		continentInformation.setBounds(280, 250, 320, 30);
		add(buttonForEdit);
		add(buttonForGame);
		
	}

	/**
	 * This is for start the game
	 * @param frame we need to use this frame in the later method
	 */
	public void start(JFrame frame)
	{	

		addListenerForButtons(frame);
		
	}

	/**
	 * give listener to buttons to mointor them if its be cliecked
	 * @param frame
	 */
	public void addListenerForButtons(JFrame frame)
	{
		//click the button
		buttonForEdit.addActionListener(new ActionListener()
		{
					
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource()==buttonForEdit) 
				{
					intoMapEditor();
				}
						
			}
			//redraw buttons
			private void intoMapEditor()
			{
				remove(buttonForEdit);
				remove(buttonForGame);
				add(createNewMap);
				add(loadExistFile);
				repaint();
			}
		});
				
		loadExistFile.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==loadExistFile) 
				{
					JFileChooser jFileChooser=new JFileChooser();
					int select=jFileChooser.showOpenDialog(frame); //this method need a JFrame as parameter
					if(select==JFileChooser.APPROVE_OPTION)
					{
						File file=jFileChooser.getSelectedFile();
						try {
							graph.createGraph(file.getAbsolutePath());
							startEditing(graph);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else 
					{
						System.out.println("file has been cancel");
					}
				}		
			}
		});
		
		
		buttonForGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource()==buttonForGame) 
				{
					JFileChooser jFileChooser=new JFileChooser();
					int select=jFileChooser.showOpenDialog(frame); //this method need a JFrame as parameter
					if(select==JFileChooser.APPROVE_OPTION)
					{
						File file=jFileChooser.getSelectedFile();
						try
						{
							graph.createGraph(file.getAbsolutePath());
							startGame(graph);
						} catch (FileNotFoundException e1)
						  {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						  }
					}
					else 
					{
						System.out.println("file has been cancel");
					}
				}
				
			}
		});
		
		createNewMap.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==createNewMap) {
					JLabel label=new JLabel("please create your continent and separate by ','");
					label.setBounds(280, 100, 400, 100);
					remove(createNewMap);
					remove(loadExistFile);
					add(label);
					add(continentInformation);
					add(createContinent);
					repaint();
				}
			}
		});
		
		createContinent.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==createContinent) {
					String information=continentInformation.getText();
					if(information.equals("")) {
						return;
					}else {
						String[] split = information.split(";");
						listContinents=new ArrayList<>();
						for(int i=0;i<split.length;i++) {						
							String[] str = split[i].split(",");
							for(int j=0;j<str[j].length();j++) {
								int awardUnit=Integer.valueOf(str[1]);
								Continent continent=new Continent(str[0],awardUnit);
								listContinents.add(continent);
							}
						}
						startEditing();
					}
					
				}	
			}
		});
	}

	/**
	 * this is a for start to play the game,to create a new frame for game 
	 * @param graph2
	 */
	protected void startGame(Graph graph2) 
	{
		JFrame risk=new JFrame();
		GamePanel panel=GamePanel.getPanelInstance();
		graph.getColorTOContinent();
		risk.add(panel);
		risk.setSize(1400,1000);
		//risk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		risk.setVisible(true);
	}

	/**
	 * This method is to create a map from empty
	 * @param graph
	 */
	protected void startEditing(Graph graph) 
	{
		EditorFrame editorFrame=new EditorFrame();
		graph.getColorTOContinent();
		editorFrame.setSize(1200, 800);
		editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorFrame.setVisible(true);
		
	}

	/**
	 * this methos is editing map from a existing file
	 */
	protected void startEditing() 
	{
		graph.setContinents(listContinents);
		graph.getColorTOContinent();
		EditorFrame editorFrame=new EditorFrame();
		editorFrame.setSize(1200, 800);
		editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editorFrame.setVisible(true);
	}


	
	
	
}
