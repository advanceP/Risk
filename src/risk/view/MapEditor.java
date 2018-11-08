package risk.view;

import risk.controller.FileController;
import risk.model.Continent;
import risk.model.Node;
import risk.model.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * this frame is for editing or creating a map,it has a inner class implement jpanel
 *
 * @author Hao Chen
 */
public class MapEditor extends JPanel {
    public int x;
    public int y;
    private List<JLabel> labelList;
    private static Graph graph;

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


    /**
     * constructor<br/>
     * initialise the class member and put panel in frame
     *
     * @throws HeadlessException
     */
    public MapEditor() {
        super();
        setLayout(null);
        setBounds(0, 0, 1200, 800);
        graph = Graph.getGraphInstance();
        labelList = new ArrayList<>();
        initial();
    }

    /**
     * To get countries from graph
     *
     * @return countres in the graph
     */
    public static List<Node> getCountries() {
        return graph.getGraphNodes();
    }


    /**
     * get input name from the textField
     *
     * @return inputName is the name for choosen country
     */
    public JTextField getInputName() {
        return inputName;
    }

    /**
     * get continents from the droplist
     *
     * @return continents name
     */
    public JComboBox<String> getContinents() {
        return continents;
    }

    /**
     * initialise some components in the panel
     */
    public void initial() {
        inputName = new JTextField();
        inputName.setBounds(1050, 30, 120, 30);
        nameLable = new JLabel("Name");
        nameLable.setBounds(970, 30, 50, 30);

        continents = new JComboBox<>();
        continents.setBounds(1050, 100, 120, 30);
        selectContinent = new JLabel("Continent");
        selectContinent.setBounds(970, 100, 120, 30);
        for (Continent obj : graph.getContinents()) {
            continents.addItem(obj.getName());
        }

        chooseAdjacency = new JComboBox<>();
        chooseAdjacency.setBounds(970, 200, 150, 30);
        searchForAllCountries();
        addAdjacency = new JButton("addAdjacency");
        addAdjacency.setBounds(950, 250, 80, 30);

        deleteButton = new JButton("delete this one");
        deleteButton.setBounds(970, 560, 120, 30);

        createButton = new JButton("create this one");
        createButton.setBounds(970, 630, 120, 30);

        changeButton = new JButton("save change");
        changeButton.setBounds(970, 630, 120, 30);

        saveMap = new JButton("save Map");
        saveMap.setBounds(970, 680, 120, 30);
    }

    /**
     * this method will search the country in the fraph and add them in the droplist for user to choose
     */
    public void searchForAllCountries() {
        if (graph.getGraphNodes() != null) {
            for (Node node : graph.getGraphNodes()) {
                chooseAdjacency.addItem(node.getName());
            }
        }
    }

    /**
     * paint country,line in the panel
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(900, 0, 900, 800);
        paintNode(g);
        paintAdjacency(g);
    }

    /**
     * paint a line for adjacence country
     *
     * @param g is a type of Graphics,it paint something
     */
    private void paintAdjacency(Graphics g) {
        if (!graph.getGraphNodes().isEmpty()) {
            for (Node node : graph.getGraphNodes()) {
                for (String str : node.getAdjacencyList()) {
                    for (Node adjaency : graph.getGraphNodes()) {
                        if (str.equals(adjaency.getName())) {
                            if (adjaency.getContinent() == node.getContinent()) {
                                Color color = node.getContinent().getColor();
                                g.setColor(color);
                                g.drawLine(node.getX() + 15, node.getY() + 15, adjaency.getX() + 15, adjaency.getY() + 15);
                            }
                            g.drawLine(node.getX() + 15, node.getY() + 15, adjaency.getX() + 15, adjaency.getY() + 15);
                        }
                    }
                }
            }
        }
    }

    /**
     * paint country as a oval
     *
     * @param g
     */
    public void paintNode(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        if (x != 0 && y != 0 && x < 900) g2.fillOval(x, y, 30, 30);
        for (Node country : graph.getGraphNodes()) {
            g2.fillOval(country.getX(), country.getY(), 30, 30);
            MapEditorLabel label = new MapEditorLabel(country.getName());
            label.setBounds(country.getX(), country.getY() + 33, 120, 30);
            add(label);
            labelList.add(label);
        }
    }


    /**
     * show the create country Menu(the some components visible)
     */
    public void showMenu() {
        inputName.setText("");
        add(inputName);
        add(nameLable);
        add(continents);
        add(selectContinent);
        add(createButton);
        add(chooseAdjacency);
        add(addAdjacency);
        repaint();
    }

    /**
     * if user want to chang the name or continent about the coutry,it will show this menu
     */
    public void showUpdateMenu() {
        remove(createButton);
        add(deleteButton);
        add(changeButton);
        repaint();
    }

    /**
     * if user doesn't want change,the updated menu will hide
     */
    public void hideUpdateMenu() {
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
    public void hideMenu() {
        remove(chooseAdjacency);
        remove(inputName);
        remove(nameLable);
        remove(continents);
        remove(selectContinent);
        remove(createButton);
        remove(addAdjacency);
    }

    public List<JLabel> getLabelList() {
        return labelList;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getChangeButton() {
        return changeButton;
    }

    public JButton getAddAdjacency() {
        return addAdjacency;
    }

    public JButton getSaveMap() {
        return saveMap;
    }

    public JComboBox<String> getChooseAdjacency() {
        return chooseAdjacency;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void removeLable(String countryName) {
        for (JLabel label : labelList) {
            if (label.getText().equals(countryName)) remove(label);
        }
        hideUpdateMenu();
        repaint();
    }
}
