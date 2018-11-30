package risk.controller;

import risk.model.GameWriter;
import risk.view.TournamentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * this countroller for tournament mode
 */
public class TournamentController {

    private TournamentView view;
    private List<String> strategies = new ArrayList<>();
    private List<File> maps = new ArrayList<>();
    int mapNum;
    int playerNum;
    int times;
    int turns;

    public TournamentController() {
        view = new TournamentView();
    }

    /**
     * show the menu
     */
    public void showMenu() {
        JFrame frame = new JFrame("choose");
        frame.setSize(800, 800);
        frame.add(view);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        addListener(frame);
    }

    /**
     * add listener for buttons
     *
     * @param frame this frame
     */
    public void addListener(JFrame frame) {

        view.getConfirm().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapNum = Integer.parseInt(view.getInputMapNumber().getText());
                playerNum = Integer.parseInt(view.getInputPlayer().getText());
                times = Integer.parseInt(view.getInputGames().getText());
                turns = Integer.parseInt(view.getTurns().getText());
                if (!(mapNum > 0 && mapNum < 6)) {
                    throw new RuntimeException("index out of bounds");
                }
                if (!(playerNum > 1 && playerNum < 5)) {
                    throw new RuntimeException("index out of bounds");
                }
                if (!(times > 0 && times < 6)) {
                    throw new RuntimeException("index out of bounds");
                }
                if (!(turns > 9 && turns < 51)) {
                    throw new RuntimeException("index out of bounds");
                }
                view.createMenu(mapNum, playerNum);
                addListenerDynamic(frame);
            }
        });

        view.getPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<JComboBox<String>> list = view.getStrategies();
                for (JComboBox<String> j : list) {
                    String strategy = j.getSelectedItem().toString();
                    strategies.add(strategy);
                }
                if (maps.size() != mapNum) {
                    throw new RuntimeException("numbers of map doesn't match");
                }
                startPlay();
            }
        });

    }

    /**
     * this method is be called when the number of maps and player is set
     *
     * @param frame this frame
     */
    public void addListenerDynamic(JFrame frame) {
        List<JButton> mapButtons = view.getMapButtons();
        for (JButton b : mapButtons) {
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

    /**
     * <p>Description: computers play several games in several maps</p>
     * @author Yiying Liu
     */
    public void startPlay() {
        List<String> winners = new ArrayList<>();
        for (File file : maps) {
            for (int i = 0; i < times; i++) {
                GameWriter.getGameWriterInstance().Write("The " + (i + 1) + " times in map : " + file.getName());
                GameDriverController driver = GameDriverController.getGameDriverInstance();
                driver.loadFile(file.getAbsolutePath());
                driver.setPlayers(playerNum, false);
                driver.giveStrategyToPlayer(strategies, false);

                String winner = driver.startGame(turns);
                winners.add(winner);
                driver.reset();
                GameWriter.getGameWriterInstance().flush();
            }
        }
        view.createCell(mapNum, times, winners);
        GameWriter.getGameWriterInstance().close();
    }
}
