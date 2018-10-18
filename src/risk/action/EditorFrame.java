package risk.action;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import risk.action.EditorFrame.EditMap;
import risk.entities.Continent;
import risk.entities.CountryLabel;
import risk.entities.Graph;
import risk.entities.Node;
/**
 * this frame is for editing or creating a map,it has a inner class implement jpanel
 * @author Hao Chen
 *
 */
public class EditorFrame extends JFrame
{
	private static EditMap panelForEdit;
	private int x;
	private int y;
	private List<JLabel> labelList;
	private static Graph graph;
	/**
	 * To get countries from graph
	 * @return countres in the graph
	 */
	public static List<Node> getCountries()
	{
		return graph.getGraphNodes();
	}
	
	/**
	 * @return return the panel for editor
	 */
	public static EditMap getPanelForEdit()
	{
		return panelForEdit;
	}

	/**
	 * constructor<br/>
	 * initialise the class member and put panel in frame
	 * @throws HeadlessException
	 */
	public EditorFrame()  throws HeadlessException 
	{
		super();
		graph=Graph.getGraphInstance();
		labelList=new ArrayList<>();
		panelForEdit=new EditMap();
		add(panelForEdit);
	}
	
	/**
	 * this is for showing a menu when user click a blank space
	 */
	public void createNode()
	{
		panelForEdit.showMenu();
		panelForEdit.repaint();
	}
	
	/**
	 * inner class,extends from jpanel as  a panel,it is a UI for editing
	 */
	public class EditMap extends JPanel
	{
		
		private JTextField inputName;
		private JLabel nameLable;
		private JLabel selectContinent;
		private JComboBox<String> continents;
		private JButton deleteButton;
		private JButton createButton;
		private JButton changeButton;
		private JComboBox<String> chooseAdjacency;
		private JButton addAdjacency;
		private JButton saveMap;
		
		public EditMap()
		{
			super();
			setLayout(null);
			setBounds(0,0,1200,800);
			initial();
		}
		
		/**
		 * get input name from the textField
		 * @return inputName is the name for choosen country 
		 */
		public JTextField getInputName()
		{
			return inputName;
		}

		/**
		 * get continents from the droplist
		 * @return continents name
		 */
		public JComboBox<String> getContinents()
		{
			return continents;
		}

		/**
		 * initialise some components in the panel 
		 */
		private void initial()
		{
			
			inputName=new JTextField();
			inputName.setBounds(1050,30,120,30);
			nameLable=new JLabel("Name");
			nameLable.setBounds(970, 30,50,30);
			
			continents=new JComboBox<>();
			continents.setBounds(1050,100,120,30);
			selectContinent=new JLabel("Continent");
			selectContinent.setBounds(970,100,120,30);
			for(Continent obj:graph.getContinents())
			{
				continents.addItem(obj.getName());
			}
			
			chooseAdjacency=new JComboBox<>();
			chooseAdjacency.setBounds(970, 200, 150, 30);
			searchForAllCountries();
			addAdjacency=new JButton("addAdjacency");
			addAdjacency.setBounds(950,250,80,30);
			
			deleteButton=new JButton("delete this one");
			deleteButton.setBounds(970, 560, 120, 30);
			
			createButton=new JButton("create this one");
			createButton.setBounds(970, 630, 120, 30);
			
			changeButton=new JButton("save change");
			changeButton.setBounds(970, 630, 120, 30);
			
			saveMap=new JButton("save Map");
			saveMap.setBounds(970, 680, 120, 30);
			add(saveMap);
			addListener();
		}

		/**
		 * this method will search the country in the fraph and add them in the droplist for user to choose
		 */
		public void searchForAllCountries()
		{
			if(graph.getGraphNodes()!=null)
			{
				for(Node node:graph.getGraphNodes())
				{
					chooseAdjacency.addItem(node.getName());
				}
			}
		}

		/**
		 * paint country,line in the panel
		 */
		public void paint(Graphics g)
		{
			super.paint(g);
			g.drawLine(900,0, 900, 800);
			paintNode(g);	
			paintAdjacency(g);
		}
		
		/**
		 * paint a line for adjacence country
		 * @param g is a type of Graphics,it paint something
		 */
		private void paintAdjacency(Graphics g) 
		{
			if(!graph.getGraphNodes().isEmpty())
			{	
				for(Node node:graph.getGraphNodes())
				{
					for(String str:node.getAdjacencyList())
					{
						for(Node adjaency:graph.getGraphNodes())
						{
							if(str.equals(adjaency.getName()))
							{
								if(adjaency.getContinent()==node.getContinent())
								{
									Color color=node.getContinent().getColor();
									g.setColor(color);
									g.drawLine(node.getX()+15, node.getY()+15, adjaency.getX()+15, adjaency.getY()+15);
								}
								g.drawLine(node.getX()+15, node.getY()+15, adjaency.getX()+15, adjaency.getY()+15);
							}
						}
					}
				}
			}
		}

		/**
		 * paint country as a oval
		 * @param g
		 */
		public void paintNode(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.blue);
			if(x!=0&&y!=0&&x<900) g2.fillOval(x,y, 30, 30);
			for(Node country:graph.getGraphNodes())
			{
				g2.fillOval(country.getX(), country.getY(), 30, 30);
				//JLabel label = new JLabel(country.getName());
				CountryLabel label =new CountryLabel(country.getName());
				label.setBounds(country.getX(), country.getY()+33,120,30);
				add(label);
				labelList.add(label);
			}
		}

		/**
		 * add Listener to buttons
		 */
		public void addListener()
		{
			createButton.addActionListener(new ActionListener() 
			{		
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource()==createButton)
					{
						String countryname=panelForEdit.inputName.getText();
						String contientname=(String)panelForEdit.getContinents().getSelectedItem();
						if(countryname.equals("")||contientname.equals(""))
						{
				           throw new RuntimeException("empty name or continent");
						}
						Continent continent=null;
						for(Continent c:graph.getContinents())
						{
							if(c.getName().equals(contientname)) continent=c;
						}
						Node country=new Node(countryname, continent, x, y);
						graph.getGraphNodes().add(country);
						hideMenu();
						chooseAdjacency.addItem(countryname);
						panelForEdit.repaint();
					}
				}
			});
			
			changeButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource()==changeButton)
					{
						String countryName=panelForEdit.inputName.getText();
						String contientName=(String)panelForEdit.getContinents().getSelectedItem();
						if(countryName.equals("")||contientName.equals(""))
						{
					           throw new RuntimeException("empty name or continent");
						}
						for(Node node:graph.getGraphNodes())
						{
							if(node.isChoose())
							{
								node.setName(countryName);
								String continentName=(String)panelForEdit.getContinents().getSelectedItem();
								for(Continent c:graph.getContinents())
								{
									if(c.getName().equals(contientName)) node.setContinent(c);
								}
							}
						}
						hideUpdateMenu();
					}
					
				}
			});
			
			MouseAdapter mouseAdapter=new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
	    	    	x=e.getX()-15;
	    	    	y=e.getY()-15;
	    	    	hideUpdateMenu();
	    	    	createNode();
	    	    	repaint();
	    	    }
			};
			
			addAdjacency.addActionListener(new ActionListener()
			{		
				@Override
				public void actionPerformed(ActionEvent e)
				{
					Node node1=null;
					Node node2=null;
					if(e.getSource()==addAdjacency)
					{
						for(Node node:graph.getGraphNodes())
						{
							if(node.isChoose())
							{
								node1=node;
							}
						}
						String adjaency = (String)chooseAdjacency.getSelectedItem();
						for(Node node:graph.getGraphNodes())
						{
							if(node.getName().equals(adjaency)) node2=node;
						}
						if(node1!=node2)
						{
							node1.getAdjacencyList().add(node2.getName());
							node2.getAdjacencyList().add(node1.getName());
							repaint();
						}
					}	
				}
			});
			
			deleteButton.addActionListener(new ActionListener()
			{	
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if(e.getSource()==deleteButton)
					{
						Node temp=null;
						String countryName=panelForEdit.inputName.getText();
						for(Node node:graph.getGraphNodes())
						{
							if(node.isChoose())
							{
								temp=node;
							}
						}
						/*for(String str:temp.getAdjacencyList()) {
							for(Node n:graph.getGraphNodes()) {
								if(n.getName().equals(str)) {
									n.getAdjacencyList().remove(countryName);	
								}
							}
						}*/
						graph.getGraphNodes().remove(temp);
						for(JLabel label:labelList)
						{
							if(label.getText().equals(countryName)) panelForEdit.remove(label);
						}
						hideUpdateMenu();
						repaint();
					}	
				}
			});
			
			saveMap.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					FileController fileController=new FileController();
					StringBuffer mapInfo = fileController.getMapInfo(graph);
					fileController.writeFile("a.map", mapInfo.toString());
				}
			});
			addMouseListener(mouseAdapter);
		}
		
		/**
		 * show the create country Menu(the some components visible) 
		 */
		public void showMenu()
		{
			inputName.setText("");
			add(inputName);
			add(nameLable);
			add(continents);
			add(selectContinent);
			add(createButton);
			add(chooseAdjacency);
			add(addAdjacency);
		}	
		
		/**
		 * if user want to chang the name or continent about the coutry,it will show this menu
		 */
		public void showUpdateMenu()
		{
			remove(createButton);
			add(deleteButton);
			add(changeButton);
			repaint();
		}
		
		/**
		 * if user doesn't want change,the updated menu will hide
		 */
		public void hideUpdateMenu()
		{
			remove(inputName);
			remove(nameLable);
			remove(continents);
			remove(selectContinent);
			remove(deleteButton);
			remove(changeButton);
			remove(chooseAdjacency);
			remove(addAdjacency);
			repaint();
		}
		
		/**
		 * hide menu
		 */
		public void hideMenu()
		{
			remove(chooseAdjacency);
			remove(inputName);
			remove(nameLable);
			remove(continents);
			remove(selectContinent);
			remove(createButton);
			remove(addAdjacency);
		}
			
	}
	
}
