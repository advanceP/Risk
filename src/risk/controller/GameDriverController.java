package risk.controller;

import risk.model.Card;
import risk.model.Graph;
import risk.model.Node;
import risk.model.Player;
import risk.view.GameLabel;
import risk.view.GamePhase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

/**
 * this class controls the flow of the game based on the risk game rules, this class uses singleton design pattern
 * it has following data memebers:
 * <ul>
 * <li> graph
 * <li> players
 * <li>staticColorList colors of the players
 * <li>gameDriver a dataType of GameDriverController class
 * <li>index contains the index of current player in round robin fashion
 * </ul>
 *
 * @author Farid Omarzadeh
 */
public class GameDriverController {
    private static GameDriverController gameDriver = null;

    Graph graph;
    List<Player> players;
    List<Color> staticColorList;
    GamePhase view;
    int index;
    private String state;
    Node[] countriesForAttack = new Node[2];

    /**
     * Constructor , initialize the graph,index,staticColorList
     */
    private GameDriverController() {
        index = 0;
        graph = Graph.getGraphInstance();
        staticColorList = new ArrayList<Color>();
        staticColorList.add(Color.BLUE);
        staticColorList.add(Color.GREEN);
        staticColorList.add(Color.RED);
        staticColorList.add(Color.YELLOW);
        view = GamePhase.getPanelInstance();
    }


    /**
     * load the map file
     * @param absolutePath
     */
    public void loadFile(String absolutePath) {
        try {
            if (FileController.verifyMapFile(absolutePath) == false) {
                throw new RuntimeException("invalid file");
            }
            graph.createGraph(absolutePath);
            if (graph.verifyGraph() == false) {
                graph.setContinents(null);
                graph.setGraphNodes(null);
                throw new RuntimeException("invalid graph");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * start game by button
     */
    public void startGame() {
        JFrame risk = new JFrame();
        view = GamePhase.getPanelInstance();
        graph.getColorTOContinent();
        view.addLabel();
        risk.add(view);
        risk.setSize(1600, 1000);
        risk.setVisible(true);
        startupPhase();
        addListener();
    }

    /**
     * put some button listener on the view
     */
    public void addListener() {

        addButtonListenerForAttack();

        for (GameLabel label : view.getLabelList()) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (state.equals("Reinforcement")) {
                        addReinforement(e);
                    }
                }
            });

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (state.equals("Attack")) {
                        getAttacker(e);
                        if (countriesForAttack[0] != null) {
                            getDefender(e);
                        }
                        if (countriesForAttack[0] != null && countriesForAttack[1] != null) {
                            view.showAttackMenu(countriesForAttack[0], countriesForAttack[1]);
                        }
                    }
                }
            });
        }

        view.getFortify().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fortify(getCurrentPlayer());
            }
        });

        view.getEndPhase().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.hideFortifitionPhase();
                changeCurrentPlayer();
                state = "Reinforcement";
                reinforcementPhase();
            }
        });

        view.exchangeCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = getCurrentPlayer();
                List<Card> cards = view.getSelectCard();
                player.exchangeCardToArmies(cards);
            }
        });
    }

    /**
     * go in to start up phase
     */
    public void startupPhase() {
        state = "StartUp";
        view.getSetPlayer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer number = Integer.valueOf(view.getInputPlayerNumber().getText());
                setPlayers(number);
                view.removeButtonSetPlayer();
            }
        });

        for (GameLabel label : view.getLabelList()) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (state.equals("StartUp")) {
                        addArmyByPlayer(e);
                    }
                }
            });
        }
    }

    /**
     * in startup phase,click them by using mouse
     * @param e mouse event
     */
    public void addArmyByPlayer(MouseEvent e) {
        if (getAllArmies() > 0) {
            GameLabel label = (GameLabel) e.getSource();
            String labelName = label.getText();
            for (Node country : graph.getGraphNodes()) {
                if (labelName.equals(country.getName())) {
                    if (country.getPlayer() == getCurrentPlayer()) {
                        if (country.getPlayer().getReinforcement() > 0) {
                            country.getPlayer().addReinforcementToNode(country);
                            changeCurrentPlayer();
                            if (getAllArmies() == 0) {
                                state = "Reinforcement";
                                reinforcementPhase();
                            }
                        } else {
                            changeCurrentPlayer();
                        }
                    }
                }
            }
        }
    }

    /**
     * go into reinforcementphase
     */
    public void reinforcementPhase() {
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.Reinforcement();
        view.createCardView(currentPlayer);
        currentPlayer.checkPlayerCard();
    }

    /**
     * choose to add reinforcecment
     * @param e mouse event
     */
    public void addReinforement(MouseEvent e) {
        Player player = getCurrentPlayer();
        int reinforces = player.getReinforcement();
        if (reinforces > 0) {
            GameLabel label = (GameLabel) e.getSource();
            String labelName = label.getText();
            for (Node country : graph.getGraphNodes()) {
                if (country.getName().equals(labelName)) {
                    if (country.getPlayer() == player) {
                        player.addReinforcementToNode(country);
                    }
                }
            }
        } else {
            view.remove(view.exchangeCard);
            attckPhase(player);
        }
    }

    /**
     * go in to attack phase
     * @param player
     */
    public void attckPhase(Player player) {
        view.hideCardView();
        player.setState("Attack");
        state = "Attack";
        view.add(view.endAttackPhase);
    }

    /**
     * get defender
     * @param e mouse event
     */
    private void getDefender(MouseEvent e) {
        GameLabel label = (GameLabel) e.getSource();
        String labelName = label.getText();
        for (Node country : graph.getGraphNodes()) {
            if (labelName.equals(country.getName()) && country.getPlayer() != getCurrentPlayer()) {
                Node attacker = countriesForAttack[0];
                for (String adjacencyname : attacker.getAdjacencyList()) {
                    if (labelName.equals(adjacencyname)) {
                        countriesForAttack[1] = country;
                    }
                }
            }
        }
    }

    /**
     * get attacker
     * @param e mouse event
     */
    public void getAttacker(MouseEvent e) {
        GameLabel label = (GameLabel) e.getSource();
        String labelName = label.getText();
        for (Node country : graph.getGraphNodes()) {
            if (labelName.equals(country.getName()) && country.getPlayer() == getCurrentPlayer() && country.getArmies() > 1) {
                countriesForAttack[0] = country;
            }
        }
    }

    /**
     * go into fortifition
     * @param player the current player
     */
    public void playerFortifition(Player player) {
        player.setState("Fortifition");
        searchNodeByPlyaer();
    }

    /**
     * select fortify army from view
     * @param player current player
     */
    public void fortify(Player player) {
        Node from = (Node) view.getFortifyFrom().getSelectedItem();
        Node to = (Node) view.getFortifyTo().getSelectedItem();
        int number = (Integer) view.getFortifyArmies().getSelectedItem();
        player.fortification(from, to, number);
    }

    /**
     * search the fortify army by player
     */
    public void searchNodeByPlyaer() {
        view.getFortifyFrom().removeAllItems();
        Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        for (Node node : player.getNodeList()) {
            view.getFortifyFrom().addItem(node);
        }
    }

    /**
     * these listener only use in attack phase
     */
    public void addButtonListenerForAttack() {
        view.attack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Node attacker = countriesForAttack[0];
                Node defender = countriesForAttack[1];
                Player player = getCurrentPlayer();
                if (attacker.getArmies() <= 1) {
                    retreat();
                }
                int attackerdice = (Integer) view.getAttackerDice().getSelectedItem();
                int defenderdice = (Integer) view.getDefenderDice().getSelectedItem();
                List<List<Integer>> numberofdice = player.getDiceNumList(attacker, defender, attackerdice, defenderdice);
                view.showAttackResult(numberofdice);
                boolean flag = player.attackResult(attacker, defender, numberofdice);
                view.showAttackMenu(attacker,defender);
                if (flag) {
                    view.hideAttackMenu();
                    moveArmriesToConquest(attacker, defender);
                }else{
                    if(attacker.getArmies()==1){
                        retreat();
                    }
                }
                List<Node> nodes = graph.getGraphNodes();
                if (player.isWin(nodes)) {
                    view.showWin();
                }
                
            }
        });


        view.retreat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retreat();
            }
        });


        view.move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = getCurrentPlayer();
                fortify(player);
                view.hideMoveArmyToQuestMenu();
            }
        });

        view.endAttackPhase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.remove(view.endAttackPhase);
                retreat();
                Player player = getCurrentPlayer();
                playerFortifition(player);
            }
        });

        view.autoFight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Node attacker = countriesForAttack[0];
                Node defender = countriesForAttack[1];
                Player player = getCurrentPlayer();
                if (attacker.getArmies() == 1) {
                    retreat();
                }
                boolean flag = false;
                while (!flag) {
                    List<List<Integer>> numberofdice = player.getDiceNumList(attacker, defender);
                    view.showAttackResult(numberofdice);
                    flag = player.attackResult(attacker, defender, numberofdice);
                }
                if (flag) {
                    view.hideAttackMenu();
                    moveArmriesToConquest(attacker, defender);
                }
                List<Node> nodes = graph.getGraphNodes();
                if (player.isWin(nodes)) {
                    view.showWin();
                }
            }
        });
    }

    /**
     * when a attcked success,move your army
     * @param attacker the country attacking
     * @param defender the attacked country
     */
    public void moveArmriesToConquest(Node attacker, Node defender) {
        view.moveArmiesToQuest(attacker, defender);
        retreat();
    }

    /**
     * can't attack
     */
    public void retreat() {
        for (int i = 0; i < countriesForAttack.length; i++) {
            countriesForAttack[i] = null;
        }
        view.hideAttackMenu();
    }


    /**
     * this method returns an instance of the GameDriverController class, if the instance is null the object will be newed,
     * otherwise it will return the already existing instance of the class.
     *
     * @return gameDriver an instance of the class's dataType
     */
    public static GameDriverController getGameDriverInstance() {
        if (gameDriver == null)
            gameDriver = new GameDriverController();
        return gameDriver;
    }

    /**
     * this method create players for the game ,randomly assign countries to them, and initialize number of armies for them.
     *
     * @param numberOfPlayers number of players.
     */
    public void setPlayers(int numberOfPlayers) {
        if (numberOfPlayers > 4)
            throw new RuntimeException("number of players should be less than 4");
        int colorindex = 0;
        Random rnd = new Random();
        int numberofarmies = (graph.getGraphNodes().size()) / (numberOfPlayers - 1);
        players = new ArrayList<Player>();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player temporaryplayer = new Player();
            temporaryplayer.setName("Player_" + i);
            temporaryplayer.addObserver(view);
            players.add(temporaryplayer);
        }
        view.createPlayerLabel(players);
        for (int i = 0; i < players.size(); i++)
            players.get(i).setReinforcement(numberofarmies);
        int playerindex = 0;
        Collections.shuffle(graph.getGraphNodes());
        for (int i = 0; i < graph.getGraphNodes().size(); i++) {
            graph.getGraphNodes().get(i).setPlayer(players.get(playerindex));
            if (playerindex < players.size() - 1) {
                playerindex++;
            } else {
                playerindex = 0;
            }
        }
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setColor(staticColorList.get(colorindex));
            colorindex++;
        }
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < graph.getGraphNodes().size(); j++)
                if (graph.getGraphNodes().get(j).getPlayer().getName().equals(players.get(i).getName()))
                    players.get(i).increaseNumberOfCountries();
        }
        for (Player player : players) {
            player.setState("StartUp");
        }


    }


    /**
     * get method for players
     *
     * @return List of the Game's players
     */
    public List<Player> getPlayers() {
        return players;
    }


    /**
     * this method returns the current player who is going to play
     *
     * @return Player that is going to play
     */
    public Player getCurrentPlayer() {
        return players.get(index);
    }


    /**
     * this method changes the current player in a round robin fashion
     */
    public void changeCurrentPlayer() {
        if (index < players.size() - 1)
            index++;
        else {
            index = 0;
        }
        view.repaint();
    }


    /**
     * this method returns the sum of the all armies that each player has
     *
     * @return integer number of armies
     */
    public int getAllArmies() {
        int allarmies = 0;
        for (int i = 0; i < players.size(); i++) {
            allarmies += players.get(i).getReinforcement();
        }
        return allarmies;
    }


}
