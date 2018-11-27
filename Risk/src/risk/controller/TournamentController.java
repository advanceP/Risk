package risk.controller;

import risk.view.TournamentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TournamentController {

    private TournamentView view;
    private List<String> strategies=new ArrayList<>();
    private List<File> maps=new ArrayList<>();
    int numberofmap;
    int numberofplayer;
    int numberofgames;
    int turns;
    public TournamentController() {
        view=new TournamentView();
    }

    public void showMenu() {
        JFrame frame=new JFrame("choose");
        frame.setSize(800, 800);
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        addListener(frame);
    }

    public void addListener(JFrame frame) {

        view.getConfirm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberofmap = Integer.parseInt(view.getInputMapNumber().getText());
                numberofplayer = Integer.parseInt(view.getInputPlayer().getText());
                numberofgames = Integer.parseInt(view.getInputGames().getText());
                turns = Integer.parseInt(view.getTurns().getText());
                if(!(numberofmap>0&&numberofmap<6)) {
                    throw new RuntimeException("index out of bounds");
                }
                if(!(numberofplayer>1&&numberofmap<5)) {
                    throw new RuntimeException("index out of bounds");
                }
                if(!(numberofgames>0&&numberofmap<6)) {
                    throw new RuntimeException("index out of bounds");
                }
                if(!(turns>0&&turns<6)) {
                    throw new RuntimeException("index out of bounds");
                }
                view.createMenu(numberofmap,numberofplayer);
                addListenerDynamic(frame);
            }
        });

        view.getPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JComboBox<String>> list = view.getStrategies();
                for(JComboBox<String> j : list) {
                    String strategy = j.getSelectedItem().toString();
                    strategies.add(strategy);
                }
                if(maps.size()!=numberofgames) {
                    throw new RuntimeException("numbers of map doesn't match");
                }
                for(int i=0;i<numberofgames;i++) {
                    GameThread gameThread=new GameThread(strategies, maps,numberofplayer, turns);
                    gameThread.start();
                }
            }
        });

    }

    public void addListenerDynamic(JFrame frame) {
        List<JButton> mapButtons = view.getMapButtons();
        for(JButton b : mapButtons) {
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser jFileChooser = new JFileChooser();
                    int select = jFileChooser.showOpenDialog(frame); //this method need a JFrame as parameter
                    if (select == JFileChooser.APPROVE_OPTION) {
                        File file = jFileChooser.getSelectedFile();
                        String name = file.getName();
                        b.setText(name);
                        maps.add(file);
                    } else {
                        System.out.println("file has been cancel");
                    }
                }
            });
        }
    }
}
