package risk.controller;

import risk.model.GameDriver;
import risk.model.Graph;
import risk.model.Node;
import risk.view.GamePhase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class GamePhaseController {
    private Graph graph;
    private GamePhase gamePhase;
    private GameDriver driver;

    public GamePhaseController() {
        this.graph = Graph.getGraphInstance();
        this.driver=GameDriver.getGameDriverInstance();
    }


    public void loadFile(String absolutePath) {
        try {
            graph.createGraph(absolutePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void startGame() {
        JFrame risk=new JFrame();
        gamePhase= GamePhase.getPanelInstance();
        graph.getColorTOContinent();
        risk.add(gamePhase);
        risk.setSize(1400,1000);
        risk.setVisible(true);
        driver.addObserver(gamePhase);
        startupPhase();
    }

    private void startupPhase()
    {
        boolean flag=true;
        gamePhase.getSetPlayer().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Integer number=Integer.valueOf(gamePhase.getInputPlayerNumber().getText());
                driver.setPlayers(number);
                gamePhase.removeButtonSetPlayer();
            }
        });




    }

    public void addListener() {



        gamePhase.getFortify().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==gamePhase.getFortify())
                {
                    Node node1=(Node)gamePhase.getFortifyFrom().getSelectedItem();
                    Node node2=(Node)gamePhase.getFortifyTo().getSelectedItem();
                    int armies=(Integer)gamePhase.getFortifyArmies().getSelectedItem();
                    driver.fortify(node1,node2,armies);
                    gamePhase.repaint();
                }
            }
        });

        gamePhase.getEndPhase().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==gamePhase.getEndPhase())
                {
                    driver.getCurrentPlayer().setState("StartUp");
                    driver.changeCurrentPlayer();
                    gamePhase.showReinforementMenu();
                }
            }
        });





    }
}
