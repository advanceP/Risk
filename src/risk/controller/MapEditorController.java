package risk.controller;

import risk.model.Continent;
import risk.model.Node;
import risk.model.Graph;
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

public class MapEditorController {

    private Graph graph;
    private static MapEditor mapEditor;
    private List<MapEditorLabel> labelList;


    public void addContinentsToGraph(List<Continent> listContinents) {
        graph.getContinents().addAll(listContinents);
    }


    public MapEditorController() {
        graph= Graph.getGraphInstance();
        mapEditor=new MapEditor();
        labelList=new ArrayList<>();
    }


    public void loadFile(String absolutePath) {
        try {
            graph.createGraph(absolutePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void startEditing() {
        graph.getColorTOContinent();
        JFrame frame=new JFrame("map editor");
        frame.setSize(1200, 800);
        frame.add(mapEditor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        mapEditor.initial();
        addListener();
    }

    public static MapEditor getMapEditor() {
        return mapEditor;
    }

    public void addListener() {
        mapEditor.getCreateButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()== mapEditor.getCreateButton())
                {
                    String countryname=mapEditor.getInputName().getText();
                    String contientname=(String)mapEditor.getContinents().getSelectedItem();
                    if(countryname.equals("")||contientname.equals(""))
                    {
                        throw new RuntimeException("empty name or continent");
                    }
                    Continent continent=null;
                    for(Continent c:graph.getContinents())
                    {
                        if(c.getName().equals(contientname)) continent=c;
                    }
                    Node country=new Node(countryname, continent, mapEditor.x,mapEditor.y);
                    MapEditorLabel label=new MapEditorLabel(countryname);
                    labelList.add(label);
                    graph.getGraphNodes().add(country);
                    mapEditor.hideMenu();
                    mapEditor.getChooseAdjacency().addItem(countryname);
                    mapEditor.repaint();
                }
            }
        });

        mapEditor.getChangeButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()== mapEditor.getChangeButton())
                {
                    String countryName=mapEditor.getInputName().getText();
                    String contientName=(String)mapEditor.getContinents().getSelectedItem();
                    if(countryName.equals("")||contientName.equals(""))
                    {
                        throw new RuntimeException("empty name or continent");
                    }
                    for(Node node:graph.getGraphNodes())
                    {
                        if(node.isChoose())
                        {
                            node.setName(countryName);
                            String continentName=(String)mapEditor.getContinents().getSelectedItem();
                            for(Continent c:graph.getContinents())
                            {
                                if(c.getName().equals(contientName)) node.setContinent(c);
                            }
                        }
                    }
                    mapEditor.hideUpdateMenu();
                }

            }
        });

        mapEditor.getAddAdjacency().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Node node1=null;
                Node node2=null;
                if(e.getSource()==mapEditor.getAddAdjacency())
                {
                    for(Node node:graph.getGraphNodes())
                    {
                        if(node.isChoose())
                        {
                            node1=node;
                        }
                    }
                    String adjaency = (String)mapEditor.getChooseAdjacency().getSelectedItem();
                    for(Node node:graph.getGraphNodes())
                    {
                        if(node.getName().equals(adjaency)) node2=node;
                    }
                    if(node1!=node2)
                    {
                        node1.getAdjacencyList().add(node2.getName());
                        node2.getAdjacencyList().add(node1.getName());
                        mapEditor.repaint();
                    }
                }
            }
        });

        mapEditor.getDeleteButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==mapEditor.getDeleteButton())
                {
                    Node temp=null;
                    String countryName=mapEditor.getInputName().getText();
                    for(Node node:graph.getGraphNodes())
                    {
                        if(node.isChoose())
                        {
                            temp=node;
                        }
                    }
                    graph.getGraphNodes().remove(temp);
                    mapEditor.removeLable(countryName);
                }
            }
        });

        mapEditor.getSaveMap().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                FileController fileController=new FileController();
                StringBuffer mapInfo = fileController.getMapInfo(graph);
                fileController.writeFile("a.map", mapInfo.toString());
            }
        });

        MouseAdapter mouseAdapter=new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                mapEditor.setX(e.getX()-15);
                mapEditor.setY(e.getY()-15);
                mapEditor.hideUpdateMenu();
                mapEditor.showMenu();
            }
        };
        mapEditor.addMouseListener(mouseAdapter);
    }
}
