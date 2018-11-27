package risk.controller;

import risk.model.Continent;
import risk.model.Graph;
import risk.view.RiskGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is where game start,main methods is here<br/>
 * also this is a controller
 *
 * @author Hao Chen
 */
public class RiskGameController {

    private RiskGame view;
    private Graph graph;
    private MapEditorController mapEditorController;
    private GameDriverController gameDriverController;

    /**
     * conctructor
     * @param view the view
     */
    public RiskGameController(RiskGame view) {
        this.view = view;
        graph = Graph.getGraphInstance();
        mapEditorController = new MapEditorController();
        gameDriverController = GameDriverController.getGameDriverInstance();
    }

    /**
     * return view instance
     * @return view
     */
    public RiskGame getView() {
        return view;
    }

    /**
     * this method is create a frame for UI,and add a panel to the frame,add start the menu
     *
     * @param args
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame("Risk");
        RiskGame mainPanel = new RiskGame(); //initialize map
        frame.add(mainPanel);
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        RiskGameController controller = new RiskGameController(mainPanel);
        controller.addListenerForButtons(frame);
    }

    /**
     * give listener to buttons to mointor them if its be cliecked
     *
     * @param frame
     */
    public void addListenerForButtons(JFrame frame) {
        view.getButtonForEdit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getButtonForEdit()) {
                    view.intoMapEditor();
                }
            }
        });


        view.getLoadExistFile().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getLoadExistFile()) {
                    JFileChooser jFileChooser = new JFileChooser();
                    int select = jFileChooser.showOpenDialog(frame); //this method need a JFrame as parameter
                    if (select == JFileChooser.APPROVE_OPTION) {
                        File file = jFileChooser.getSelectedFile();
                        mapEditorController.loadFile(file.getAbsolutePath());
                        mapEditorController.startEditing();
                    } else {
                        System.out.println("file has been cancel");
                    }
                }
            }
        });

        view.getButtonForGame().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getButtonForGame()) {
                    view.chooseMode();
                }
            }
        });


        view.getCreateNewMap().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getCreateNewMap()) {
                    view.showInputContinent();
                }
            }
        });


        view.getCreateContinent().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == view.getCreateContinent()) {
                    String information = view.getContinentInformation().getText();
                    if (information.equals("")) {
                        return;
                    } else {
                        String[] split = information.split(";");
                        List<Continent> listContinents = new ArrayList<>();
                        for (int i = 0; i < split.length; i++) {
                            String[] str = split[i].split(",");
                            for (int j = 0; j < str[j].length(); j++) {
                                int awardUnit = Integer.valueOf(str[1]);
                                Continent continent = new Continent(str[0], awardUnit);
                                listContinents.add(continent);
                            }
                        }
                        mapEditorController.addContinentsToGraph(listContinents);
                        mapEditorController.startEditing();
                    }
                }
            }
        });

        view.getSingleGameMode().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int select = jFileChooser.showOpenDialog(frame); //this method need a JFrame as parameter
                if (select == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    gameDriverController.loadFile(file.getAbsolutePath());
                    gameDriverController.startGame();
                } else {
                        System.out.println("file has been cancel");
                }
            }
        });

        view.getBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.backtoMainMenu();
            }
        });

        view.getTournamentMode().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TournamentController t=new TournamentController();
                t.showMenu();
            }
        });

        view.getLoadGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                int select = jFileChooser.showOpenDialog(frame); //this method need a JFrame as parameter
                if (select == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                } else {
                    System.out.println("file has been cancel");
                }
            }
        });
    }


}
