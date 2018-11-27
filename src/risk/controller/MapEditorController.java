package risk.controller;

import risk.model.Continent;
import risk.model.Graph;
import risk.model.Node;
import risk.view.MapEditor;
import risk.view.MapEditorLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 *this class is the controller for maoEditor
 * <li/> graph
 * <li/>mapEditor view
 * <li/> labelList
 */
public class MapEditorController {

    private Graph graph;
    private static MapEditor mapEditor;
    private List<MapEditorLabel> labelList;


    /**
     * add continents on the graph
     * @param listContinents
     */
    public void addContinentsToGraph(List<Continent> listContinents) {
        graph.getContinents().addAll(listContinents);
    }


    /**
     * constructor,initial some feature
     */
    public MapEditorController() {
        graph = Graph.getGraphInstance();
        mapEditor = new MapEditor();
        labelList = new ArrayList<>();
    }


    /**
     * load the file
     * @param absolutePath
     */
    public void loadFile(String absolutePath) {
        try {
            graph.createGraph(absolutePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * start the program
     */
    public void startEditing() {
        graph.getColorTOContinent();
        JFrame frame = new JFrame("map editor");
        frame.setSize(1200, 800);
        frame.add(mapEditor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        mapEditor.initial();
        addListener();
        mapEditor.add(mapEditor.getSaveMap());
    }

    /**
     * give the MapEditor instance
     * @return static mapEditor,the view
     */
    public static MapEditor getMapEditor() {
        return mapEditor;
    }

    /**
     * add Listener on the game
     */
    public void addListener() {
        mapEditor.getCreateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mapEditor.getCreateButton()) {
                    String countryName = mapEditor.getInputName().getText();
                    //nodes can not have the same name
                    for (Node country: graph.getGraphNodes()){
                        if (country.getName().equals(countryName)){
                            throw new RuntimeException("the country already exists in the graph");
                        }
                    }
                    Continent continent = (Continent) mapEditor.getContinents().getSelectedItem();
                    Node country = new Node(countryName, continent, mapEditor.x, mapEditor.y);
                    MapEditorLabel label = new MapEditorLabel(countryName);
                    labelList.add(label);
                    graph.getGraphNodes().add(country);
                    mapEditor.hideMenu();
                    mapEditor.getChooseAdjacency().addItem(country);
                    mapEditor.repaint();
                }
            }
        });

        mapEditor.getChangeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mapEditor.getChangeButton()) {
                    String countryName = mapEditor.getInputName().getText();
                    String continentName = mapEditor.getContinents().getSelectedItem().toString();
                    if (countryName.equals("") || continentName.equals("")) {
                        throw new RuntimeException("empty name or continent");
                    }
                    for (Node node : graph.getGraphNodes()) {
                        if (node.isChoose()) {
                            node.setName(countryName);
                            //String continentName = (String) mapEditor.getContinents().getSelectedItem();
                            node.setContinent((Continent) mapEditor.getContinents().getSelectedItem());
                        }
                    }
                    mapEditor.hideUpdateMenu();
                }

            }
        });

        mapEditor.getAddAdjacency().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Node node1 = null;
                if (e.getSource() == mapEditor.getAddAdjacency()) {
                    for (Node node : graph.getGraphNodes()) {
                        if (node.isChoose()) {
                            node1 = node;
                        }
                    }
                    Node adjaency = (Node) mapEditor.getChooseAdjacency().getSelectedItem();
                    if (node1 != adjaency) {
                        node1.getAdjacencyList().add(adjaency.getName());
                        adjaency.getAdjacencyList().add(node1.getName());
                        mapEditor.repaint();
                    }
                }
            }
        });

        mapEditor.getSaveMap().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileController fileController = new FileController();
                StringBuffer mapInfo = fileController.getMapInfo(graph);
                if (graph.verifyGraph() == false) {
                    throw new RuntimeException("invalid graph");
                }
                fileController.writeFile("a.map", mapInfo.toString());
            }
        });

        mapEditor.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == mapEditor.getDeleteButton()) {
                    Node temp = null;
                    String countryName = mapEditor.getInputName().getText();
                    for (Node node : graph.getGraphNodes()) {
                        if (node.isChoose()) {
                            temp = node;
                        }
                    }
                    graph.getGraphNodes().remove(temp);
                    mapEditor.removeLable(countryName);
                }
            }
        });


        MouseAdapter mouseAdapter = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mapEditor.setX(e.getX() - 15);
                mapEditor.setY(e.getY() - 15);
                mapEditor.hideUpdateMenu();
                mapEditor.showMenu();
            }
        };
        mapEditor.addMouseListener(mouseAdapter);
    }
}
