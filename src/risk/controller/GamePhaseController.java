package risk.controller;

import risk.model.Graph;
import risk.model.Node;
import risk.model.Player;
import risk.view.GameLabel;
import risk.view.GamePhase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

public class GamePhaseController {
    private Graph graph;
    private GamePhase gamePhase;
    private GameDriverController driver;
    private String state;

    public GamePhaseController() {
        this.graph = Graph.getGraphInstance();
        this.driver= GameDriverController.getGameDriverInstance();
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
        gamePhase.addLabel();
        risk.add(gamePhase);
        risk.setSize(1400,1000);
        risk.setVisible(true);
        startupPhase();
    }

    public void startupPhase()
    {
        state="StartUp";
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

        for (GameLabel label:gamePhase.getLabelList())
        {
            label.addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    super.mouseClicked(e);
                    if(state.equals("StartUp"))
                    {
                        addArmyByPlayer(e);
                    }
                }
            });
        }
    }

    public void addArmyByPlayer(MouseEvent e)
    {
        if(driver.getAllArmies()>0)
        {
            GameLabel label=(GameLabel)e.getSource();
            String labelName=label.getText();
            for(Node country:graph.getGraphNodes())
            {
                if(labelName.equals(country.getName()))
                {
                    if(country.getPlayer()==driver.getCurrentPlayer())
                    {
                        if(country.getPlayer().getReinforcement()>0)
                        {
                            country.getPlayer().addReinforcementToNode(country);
                            driver.changeCurrentPlayer();
                            if(driver.getAllArmies()==0)
                            {
                                state="Reinforcement";
                                reinforcementPhase();
                            }
                        }
                        else
                        {
                            driver.changeCurrentPlayer();
                        }
                    }
                }
            }
        }
    }

    public void reinforcementPhase() {
        Player currentPlayer=driver.getCurrentPlayer();
        currentPlayer.Reinforcement();
        for (GameLabel label:gamePhase.getLabelList())
        {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(state.equals("Reinforcement"))
                    {
                        addReinforement(e,currentPlayer);
                    }
                }
            });
        }
    }

    public void addReinforement(MouseEvent e,Player player)
    {
        int reinforces=player.getReinforcement();
        if(reinforces>0)
        {
            GameLabel label=(GameLabel)e.getSource();
            String labelName=label.getText();
            for(Node country:graph.getGraphNodes())
            {
                if(country.getName().equals(labelName))
                {
                    if(country.getPlayer()==player)
                    {
                        //country.increaseArmy();
                        player.addReinforcementToNode(country);
                    }
                }
            }
        }
        else
        {
            playerFortifition(player);

        }
    }

    public void playerFortifition(Player player) {
        player.setState("Fortifition");
    }


}

