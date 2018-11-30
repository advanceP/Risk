package risk.controller;

import risk.model.*;
import risk.strategy.Human;
import risk.view.GameLabel;
import risk.view.GamePhase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * this class controls the flow of the game based on the risk game rules, this class uses singleton design pattern
 * it has following data members:
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
    private String state = "";
    Node[] countriesForAttack = new Node[2];
    int turns = 9999;

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
        players = new ArrayList<Player>();
    }

    /*private GameDriverController(File file){
        graph = Graph.getGraphInstance();
        loadFile(file.getAbsolutePath());

    }*/
    /**
     * reset driver
     */
    public void reset() {
        index = 0;
        players = new ArrayList<Player>();
    }

    /**
     * load the map file
     * @param absolutePath the file path
     */
    public void loadFile(String absolutePath) {
        try {
            if (!FileController.verifyMapFile(absolutePath)) {
                throw new RuntimeException("invalid file");
            }
            graph.createGraph(absolutePath);
            if (!graph.verifyGraph()) {
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
        risk.setLocationRelativeTo(null);
        risk.setVisible(true);
        startupPhase();
        addListener();
    }

    public String startGame(int turns) {
        this.turns = turns * players.size();
        playStartup(false);
        String winner = roundsOfComputer(false);
        return winner;
    }

    public void continueGame() {
        JFrame risk = new JFrame();
        view = GamePhase.getPanelInstance();
        view.addLabel();
        risk.add(view);
        risk.setSize(1600, 1000);
        risk.setLocationRelativeTo(null);
        risk.setVisible(true);
        for(Player p: players){
            p.addObserver(view);
        }
        view.addContinent(players);
        view.add(view.getSaveGame());
        addListener();
        view.createPlayerLabel(players);
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
                    Player player = getCurrentPlayer();
                    if (state.equals("Reinforcement") && player.getStrategy() instanceof Human) {
                        addReinforcement(e);
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
            //add army by human players
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (state.equals("StartUp")) {
                        Player player = getCurrentPlayer();
                        if (player != null && player.getStrategy() instanceof Human) {
                            addArmyByPlayer(e);
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
                changeCurrentPlayer(true);
                if (getCurrentPlayer().getStrategy() instanceof Human) {
                    reinforcementPhase(true);
                } else {
                    String result = roundsOfComputer(true);
                    if (result != null && !result.isEmpty()) {
                        view.showWin();
                    }
                }
            }
        });

        view.exchangeCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = getCurrentPlayer();
                List<Card> cards = view.getSelectCard();
                view.removeCards(cards);
                player.exchangeCardToArmies(cards);
            }
        });
        //after choosing strategy ,start game
        view.getStartPlay().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> strategies = view.getstrategieInformation();
                giveStrategyToPlayer(strategies, true);
                view.removeChooseStrategie();
                state = "StartUp";
                playStartup(true);
            }
        });

        view.getSaveGame().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

    }


    /**
     * go in to start up phase,initial player
     */
    public void startupPhase() {
        view.getSetPlayer().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer number = Integer.valueOf(view.getInputPlayerNumber().getText());
                setPlayers(number, true);
                view.removeButtonSetPlayer();
                view.chooseStrategieForPlayer(players);
            }
        });
    }

    /**
     * <p>Description: decide which player is playing the game,computer or human</p>
     * @param needView true: need to show view in game
     * @return  String of winner
     */
    public String playStartup(boolean needView) {
        Player currentPlayer = getCurrentPlayer();
        while (!(currentPlayer.getStrategy() instanceof Human) && getAllReinforcement() > 0) {
            if (currentPlayer.getReinforcement() > 0) {
                currentPlayer.addArmyRandomly();
            }
            changeCurrentPlayer(needView);
            currentPlayer = getCurrentPlayer();
        }

        return null;
    }


    /**
     * in startup phase,click them by using mouse
     *
     * @param e mouse event
     */
    public void addArmyByPlayer(MouseEvent e) {
        if (getAllReinforcement() > 0) {
            GameLabel label = (GameLabel) e.getSource();
            String labelName = label.getText();
            for (Node country : graph.getGraphNodes()) {
                if (labelName.equals(country.getName())) {
                    if (country.getPlayer() == getCurrentPlayer()) {
                        if (country.getPlayer().getReinforcement() > 0) {
                            country.getPlayer().increaseArmy(country);
                            changeCurrentPlayer(true);
                            playStartup(true);
                            if (getAllReinforcement() == 0) {
                                roundsOfComputer(true);
                            }
                        } else {
                            changeCurrentPlayer(true);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * <p>Description: what computer do in it's turn</p>
     *
     * @param needView true: need to show view in game
     * @return winner's name   if it ran out of turns ,it will return "Draw"
     * @author Yiying Liu
     */
    public String roundsOfComputer(boolean needView) {
        Player currentPlayer = getCurrentPlayer();
        while (turns > 0) {
            GameWriter.getGameWriterInstance().Write("It's " + currentPlayer.getName() +"'s turn: \r\n");
            GameWriter.getGameWriterInstance().flush();
            currentPlayer.checkCountries();
            if (!(currentPlayer.getStrategy() instanceof Human)) {
                reinforcementPhase(needView);
                attackPhase(currentPlayer, needView);
                if (currentPlayer.isWin(graph.getGraphNodes())) {
                    GameWriter.getGameWriterInstance().Write("winner: " + currentPlayer.getName() + "\n");
                    GameWriter.getGameWriterInstance().flush();
                    return currentPlayer.getName();
                }
                fortificationPhase(currentPlayer, needView);
                changeCurrentPlayer(needView);
                currentPlayer = getCurrentPlayer();
                turns--;
                System.out.println(turns);
            } else {
                reinforcementPhase(needView);
                return null;
            }

        }
        GameWriter.getGameWriterInstance().Write("Result: Draw\n");
        GameWriter.getGameWriterInstance().flush();
        return "Draw";
    }

    /**
     * <p>Description: go into reinforcement phase</p>
     * @param needView true: need to show view in game
     * @return  string of winner
     */
    public String reinforcementPhase(boolean needView) {
        state = "Reinforcement";
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.calculateReinforcement();
        if (needView) {
            view.createCardView(currentPlayer);
        }
        currentPlayer.checkPlayerCard();
        if (!(currentPlayer.getStrategy() instanceof Human)) {
            if (currentPlayer.getReinforcement() > 0) {
                currentPlayer.executeStrategyRein(null);
                if (needView) {
                    view.remove(view.exchangeCard);
                }

            }
        }
        return null;
    }

    /**
     * choose to add reinforcement
     *
     * @param e mouse event
     */
    public void addReinforcement(MouseEvent e) {
        Player player = getCurrentPlayer();
        int reinforces = player.getReinforcement();
        if (reinforces > 0) {
            GameLabel label = (GameLabel) e.getSource();
            String labelName = label.getText();
            for (Node country : graph.getGraphNodes()) {
                if (country.getName().equals(labelName)) {
                    if (country.getPlayer() == player) {
                        player.executeStrategyRein(country);
                    }
                }
            }
        } else {
            view.remove(view.exchangeCard);
            attackPhase(player, true);
        }
    }


    /**
     * <p>Description: go in to attack phase</p>
     * @param player   current player
     * @param needView true: need to show view in game
     * @return winner
     */
    public String attackPhase(Player player, boolean needView) {
        if (needView) {
            view.hideCardView();
            view.add(view.endAttackPhase);
        }
        player.setState("Attack");
        state = "Attack";
        boolean isWin = false;
        if (!(player.getStrategy() instanceof Human)) {
            isWin = player.attack(null, null, null, null);
            checkPlayerAlive();
            if (isWin) {
                if (needView) {
                    view.remove(view.endAttackPhase);
                }
            } else if (needView) {
                view.showWin();
                return player.getStrategy().toString();
            }
        }
        return null;
    }


    /**
     * get defender
     *
     * @param e mouse event
     */
    private void getDefender(MouseEvent e) {
        GameLabel label = (GameLabel) e.getSource();
        String labelName = label.getText();
        Node defender = null;
        Node attacker = countriesForAttack[0];
        for (Node country : graph.getGraphNodes()) {
            if (labelName.equals(country.getName()) && country.getPlayer() != getCurrentPlayer()) {
                defender = country;
                break;
            }
        }
        for (String adjacencyName : attacker.getAdjacencyList()) {
            if (labelName.equals(adjacencyName)) {
                countriesForAttack[1] = defender;
                break;
            }
        }

    }

    /**
     * get attacker
     *
     * @param e mouse event
     */
    public void getAttacker(MouseEvent e) {
        GameLabel label = (GameLabel) e.getSource();
        String labelName = label.getText();
        for (Node country : graph.getGraphNodes()) {
            Player player = country.getPlayer();
            if (labelName.equals(country.getName()) && player == getCurrentPlayer()
                    && player.getStrategy().toString().equals("Human") && country.getArmies() > 1) {
                countriesForAttack[0] = country;
                break;
            }
        }
    }

    /**
     * <p>Description: go into fortification</p>
     * @param player   the current player
     * @param needView true: need to show view in game
     * @return String of winner
     */
    public String fortificationPhase(Player player, boolean needView) {
        player.setState("Fortification");
        searchNodeByPlayer(needView);
        if (!(player.getStrategy() instanceof Human)) {
            player.fortification(null, null, null);
        }
        return null;
    }


    /**
     * select fortify army from view
     *
     * @param player current player
     */
    public void fortify(Player player) {
        Node from = (Node) view.getFortifyFrom().getSelectedItem();
        Node to = (Node) view.getFortifyTo().getSelectedItem();
        int number = (Integer) view.getFortifyArmies().getSelectedItem();
        if (from.getArmies() > number) {
            player.fortification(from, to, number);
        } else {
            throw new RuntimeException("There is no more armies");
        }

    }

    /**
     * when a attack is end,check player is alive,if not,remove the player
     */
    public void checkPlayerAlive() {
        Player temp = null;
        for (Player player : players) {
            if (player.getNodeList().size() == 0) {
                temp = player;
            }
        }
        players.remove(temp);
    }

    /**
     * @param needView true or false
     * search the fortify army by player
     */
    public void searchNodeByPlayer(boolean needView) {

        Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        if (needView) {
            view.getFortifyFrom().removeAllItems();
            for (Node node : player.getNodeList()) {
                view.getFortifyFrom().addItem(node);
            }
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
                if (player.getStrategy() instanceof Human) {
                    if (attacker.getArmies() <= 1) {
                        retreat();
                    }
                    //user choose how many dice to roll
                    int attackerdice = (Integer) view.getAttackerDice().getSelectedItem();
                    List<Integer> defendDices = defender.getPlayer().Defend(attackerdice, defender);
                    List<List<Integer>> numberofdice = player.getDiceNumList(attacker, defender, attackerdice);
                    numberofdice.add(defendDices);
                    view.showAttackResult(numberofdice);
                    boolean flag = player.attack(attacker, defender, numberofdice.get(0), numberofdice.get(1));
                    view.showAttackMenu(attacker, defender);
                    if (flag) {
                        view.hideAttackMenu();
                        moveArmriesToConquest(attacker, defender);
                    } else {
                        if (attacker.getArmies() == 1) {
                            retreat();
                        }
                    }
                    List<Node> nodes = graph.getGraphNodes();
                    if (player.isWin(nodes)) {
                        view.showWin();
                    }

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
                fortificationPhase(player, true);
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
                    flag = player.attack(attacker, defender, numberofdice.get(0), numberofdice.get(1));
                    //when you get defeated
                    if (attacker.getArmies() == 1) {
                        retreat();
                        break;
                    }
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
     *
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
        if (gameDriver == null) {
            gameDriver = new GameDriverController();
        }

        return gameDriver;
    }

    /**
     * this method will add player to our game
     *
     * @param player a player to be added to the Game
     */
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * this method create players for the game ,randomly assign countries to them, and initialize number of armies for them.
     * @param needView true or false
     * @param numberOfPlayers number of players.
     */
    public void setPlayers(int numberOfPlayers, boolean needView) {
        if (numberOfPlayers > 4)
            throw new RuntimeException("number of players should be less than 4");
        int colorindex = 0;
        Random rnd = new Random();
        int numberofarmies = (graph.getGraphNodes().size()) / (numberOfPlayers - 1);
        for (int i = 0; i < numberOfPlayers; i++) {
            Player temporaryplayer = new Player();
            temporaryplayer.setName("Player_" + i);
            players.add(temporaryplayer);
        }
        if (needView) {
            view.createPlayerLabel(players);
        }

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
        if (needView) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).setColor(staticColorList.get(colorindex));
                colorindex++;
            }
        }

        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < graph.getGraphNodes().size(); j++)
                if (graph.getGraphNodes().get(j).getPlayer().getName().equals(players.get(i).getName()))
                    players.get(i).increaseNumberOfCountries();
        }
    }

    /**
     * <p>Description: give a strategy to each player</p>
     *
     * @param strategies strategies List of players
     * @param needView   true: need to show view in game
     */
    public void giveStrategyToPlayer(List<String> strategies, boolean needView) {
        int index = 0;
        for (Player player : players) {

            player.setStrategy(strategies.get(index));
            if (needView) {
                player.addObserver(view);
            }
            player.setState("StartUp");
            index++;
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
        //emergency surgey,why removing the player,index somehow went wrong
        if (index == players.size()) {
            index--;
        }
        return players.get(index);
    }

    /**
     * <p>Description: this method changes the current player in a round robin fashion</p>
     * @param needView true: need to show view in game
     */
    public void changeCurrentPlayer(boolean needView) {
        if (index < players.size() - 1)
            index++;
        else {
            index = 0;
        }
        if (needView) {
            view.repaint();
        }

    }


    public void setIndex(int index)
    {
    	this.index=index;
    }

    public void setState(String state)
    {
    	this.state=state;
    }

    public void setPlayers(List<Player>players)
    {
    	this.players=players;
    }


    /**
     * this method returns the sum of the all armies that each player has
     *
     * @return integer number of armies
     */
    public int getAllReinforcement() {
        int allarmies = 0;
        for (int i = 0; i < players.size(); i++) {
            allarmies += players.get(i).getReinforcement();
        }
        return allarmies;
    }

    public void saveGame()
    {
    	try {
			FileWriter fw=new FileWriter("game.txt");
			fw.write("[Players]\r\n");
			for(int i=0;i<players.size();i++)
			{
				fw.write(players.get(i).getName()+","+players.get(i).getReinforcement()+","+players.get(i).getState()+","+players.get(i).getStrategy().toString()+","+players.get(i).getNumberOfCountries()+"\r\n");
			}
			fw.write("[CurrentPlayer]\r\n");
			fw.write(getCurrentPlayer().getName());
			fw.write("\r\n");

			fw.write("[Continents]\r\n");
			for(int i=0;i<Graph.getGraphInstance().getContinents().size();i++)
			{
				fw.write(graph.getContinents().get(i).getName()+"="+graph.getContinents().get(i).getAwardUnits()+"\r\n");
			}
			fw.write("[Territories]\r\n");
			for(int i=0;i<graph.getGraphInstance().getGraphNodes().size();i++)
			{
				Node node=graph.getGraphInstance().getGraphNodes().get(i);
				fw.write(node.getPlayer().getName()+","+node.getArmies()+","+node.getName()+","+node.getX()+","+node.getY()+","+node.getContinent().getName());
				for (String adj : node.getAdjacencyList()) {
					fw.append(","+adj);
				}
				fw.write("\r\n");
			}
			fw.write("[Other]\r\n");
			fw.write(index+","+this.state+"\r\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * return view
     * @return GamePhase view
     */
    public GamePhase getView() {
        return view;
    }

    public void decide() {
        Player player=getCurrentPlayer();
        state=player.getState();
        switch (state) {
            case "StartUp": playStartup(true); break;
            case "Reinforcement": reinforcementPhase(true);break;
            case "Attack": attackPhase(player,true);break;
            case "Fortification":fortificationPhase(player, true); break;
        }
    }
}
