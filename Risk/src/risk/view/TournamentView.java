package risk.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentView extends JPanel {
    private JTextField inputMapNumber;
    private JTextField inputPlayer;
    private JTextField inputGames;
    private JTextField turns;
    private JLabel hidenMap;
    private JLabel hidenPlayer;
    private JLabel hidenTurns;
    private JLabel hidenGames;
    private JButton confirm;
    private List<JComboBox<String>> strategies = new ArrayList<>();
    private List<JButton> mapButtons = new ArrayList<>();
    private JButton play;
    public TournamentView() {
        super();
        setLayout(null);
        hidenMap=new JLabel("input number of map");
        hidenMap.setBounds(20,20,120,20);
        inputMapNumber=new JTextField();
        inputMapNumber.setBounds(20, 60, 30, 20);

        hidenPlayer=new JLabel("input number of player");
        hidenPlayer.setBounds(20, 90, 150, 20);
        inputPlayer=new JTextField();
        inputPlayer.setBounds(20, 120, 30, 20);

        hidenTurns=new JLabel("input humber of turns");
        hidenTurns.setBounds(20, 150, 150, 20);
        turns=new JTextField();
        turns.setBounds(20, 170, 30, 20);

        hidenGames=new JLabel("input number of Games");
        hidenGames.setBounds(20, 200, 150, 20);
        inputGames=new JTextField();
        inputGames.setBounds(20, 230, 30, 20);
        confirm=new JButton("confirm");
        confirm.setBounds(20, 300, 100, 20);

        play=new JButton("play");
        play.setBounds(20, 400, 100,30 );
        add(hidenMap);
        add(inputMapNumber);
        add(hidenGames);
        add(inputGames);
        add(hidenPlayer);
        add(inputPlayer);
        add(hidenTurns);
        add(hidenTurns);
        add(turns);
        add(confirm);
    }


    public JButton getConfirm() {
        return confirm;
    }


    public JTextField getInputMapNumber() {
        return inputMapNumber;
    }

    public JTextField getInputPlayer() {
        return inputPlayer;
    }

    public JTextField getTurns() {
        return turns;
    }

    public JTextField getInputGames() {
        return inputGames;
    }

    public List<JComboBox<String>> getStrategies() {
        return strategies;
    }

    public List<JButton> getMapButtons() {
        return mapButtons;
    }

    public JButton getPlay() {
        return play;
    }

    public void createMenu(int numberofmap, int numberofplayer) {
        removeAll();
        int x=20;
        int y=100;
        for(int i=0;i<numberofmap;i++) {
            JButton j=new JButton("select");
            j.setBounds(x, y, 100, 20);
            add(j);
            mapButtons.add(j);
            x+=120;
        }

        for(int i=0;i<numberofplayer;i++) {
            JComboBox<String> j=new JComboBox<>();
            addStrategies(j);
            j.setBounds(x, y, 100, 20);
            add(j);
            strategies.add(j);
            x+=120;
        }
        add(play);
        repaint();
    }

    private void addStrategies(JComboBox<String> j) {
        j.addItem("Aggressive");
        j.addItem("Benevolent");
        j.addItem("Cheater");
        j.addItem("RandomPlayer");
    }
}
