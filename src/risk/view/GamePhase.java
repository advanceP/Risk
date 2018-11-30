package risk.view;

import risk.controller.GameDriverController;
import risk.model.*;
import risk.strategy.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This is also is a panel for user play the game
 *
 * @author Hao CHEN
 */
public class GamePhase extends JPanel implements ItemListener, Observer {
    private static Graph graph;
    private int x;
    private int y;
    private List<GameLabel> labelList;
    private JTextField inputPlayerNumber;
    private JButton setPlayer;
    private JTextArea phaseText;
    private JComboBox<Node> fortifyFrom;
    private JComboBox<Node> fortifyTo;
    private JComboBox<Integer> fortifyArmies;
    private JButton fortify;
    private JButton endPhase;
    private JLabel view1;
    private JLabel view2;
    public JButton attack;
    public JButton retreat;
    public JButton autoFight;
    public JTextArea attackinformation;
    public JButton move;
    public JButton endAttackPhase;
    public JButton exchangeCard;
    private List<JCheckBox> cardList;
    private JComboBox<Integer> attackerDice;
    private JComboBox<Integer> defenderDice;
    private static GamePhase gamePhase = null;
    private ArrayList<JLabel> playerList;
    private JButton startPlay;
    private List<JComboBox> tempStrategie;
    private List<JLabel> tempLabel;
    private JButton saveGame;
    private List<JComboBox> continentList=new ArrayList<>();
    /**
     * constructor <br/>
     * using freelayout
     */
    private GamePhase() {
        super();
        setLayout(null);
        graph = Graph.getGraphInstance();
        labelList = new ArrayList<>();
        cardList = new ArrayList<>();
        playerList = new ArrayList<>();
        initial();
    }

    /**
     * this using singleton to get the instance for GamePhase class
     *
     * @return gamePhase instance for GamePhase
     */
    public static GamePhase getPanelInstance() {
        if (gamePhase == null)
            gamePhase = new GamePhase();
        return gamePhase;
    }


    /**
     * add some button the create the button
     */
    private void initial() {
        inputPlayerNumber = new JTextField();
        inputPlayerNumber.setBounds(1150, 200, 150, 30);
        setPlayer = new JButton("setPlayer");
        setPlayer.setBounds(1150, 280, 100, 30);
        phaseText = new JTextArea();
        phaseText.setBounds(1120, 200, 400, 100);
        fortifyFrom = new JComboBox<>();
        fortifyFrom.setBounds(1120, 400, 200, 30);
        fortifyTo = new JComboBox<>();
        fortifyTo.setBounds(1120, 460, 200, 30);
        fortifyArmies = new JComboBox<>();
        fortifyArmies.setBounds(1120, 520, 200, 30);
        fortify = new JButton("fortify");
        fortify.setBounds(1120, 570, 100, 30);
        endPhase = new JButton("endPhase");
        endPhase.setBounds(1120, 620, 100, 30);
        view1 = new JLabel("players world domination view");
        view1.setBounds(1120, 50, 200, 20);
        view2 = new JLabel("phase view");
        view2.setBounds(1120, 180, 200, 20);
        attack = new JButton("attack");
        attack.setBounds(1100, 350, 100, 20);
        retreat = new JButton("retreat");
        retreat.setBounds(1210, 350, 100, 20);
        autoFight = new JButton("autoFight");
        autoFight.setBounds(1320, 350, 100, 20);
        attackinformation = new JTextArea();
        attackinformation.setLineWrap(true);
        attackinformation.setBounds(1120, 400, 250, 100);
        move = new JButton("move");
        move.setBounds(1120, 570, 100, 30);
        endAttackPhase = new JButton("endAttackPhase");
        endAttackPhase.setBounds(1120, 620, 100, 30);
        exchangeCard = new JButton("exchanged");
        exchangeCard.setBounds(1120, 570, 100, 30);
        attackerDice = new JComboBox<>();
        attackerDice.setBounds(1120, 500, 100, 20);
        defenderDice = new JComboBox();
        defenderDice.setBounds(1220, 500, 100, 20);
        startPlay = new JButton("start Play");
        startPlay.setBounds(1120, 700, 100, 30);
        saveGame=new JButton("save game");
        saveGame.setBounds(1120, 900, 100, 30);
        add(inputPlayerNumber);
        add(setPlayer);
    }


    /**
     * paint something on the panel
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(1100, 0, 1100, 1000);
        paintPlayer(g);
        paintNode(g);
        paintAdjacency(g);
        paintArmies(g);
        paintPhaseInformation(g);
    }

    /**
     * show the phase on the text
     */
    private void paintPhaseInformation(Graphics g) {
        if (!GameDriverController.getGameDriverInstance().getPlayers().isEmpty()) {
            Player currentplayer = GameDriverController.getGameDriverInstance().getCurrentPlayer();
            phaseText.setText(currentplayer.getState() + " " + currentplayer.getName() +
                    " " + GameDriverController.getGameDriverInstance().getCurrentPlayer().getReinforcement());
        }
    }

    /**
     * show the armies for each node
     */
    private void paintArmies(Graphics g) {
        g.setColor(Color.WHITE);
        for (Node country : graph.getGraphNodes()) {
            g.drawString(Integer.toString(country.getArmies()), country.getX() + 15, country.getY() + 15);
        }
    }

    /**
     * show players on the panel
     */
    private void paintPlayer(Graphics g) {
        List<Player> players = GameDriverController.getGameDriverInstance().getPlayers();
        if (players != null) {
            int x = 1120;
            int y = 110;
            for (Player player : players) {
                g.setColor(player.getColor());
                g.fillOval(x, y, 30, 30);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(player.getTotalArmies()), x + 15, y + 15);
                x = x + 60;
            }

        }
    }

    /**
     * show countries on the node
     */
    private void paintNode(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        if (x != 0 && y != 0 && x < 900) g2.fillOval(x, y, 30, 30);
        for (Node country : graph.getGraphNodes()) {
            if (country.getPlayer() != null) g2.setColor(country.getPlayer().getColor());
            g2.fillOval(country.getX(), country.getY(), 30, 30);
        }
    }

    /**
     * draw lines for adjacency country
     */
    private void paintAdjacency(Graphics g) {
        if (!graph.getGraphNodes().isEmpty()) {
            for (Node node : graph.getGraphNodes()) {
                for (String str : node.getAdjacencyList()) {
                    for (Node adjaency : graph.getGraphNodes()) {
                        if (str.equals(adjaency.getName())) {
                            if (adjaency.getContinent() == node.getContinent()) {
                                Color color = node.getContinent().getColor();
                                g.setColor(color);
                                g.drawLine(node.getX() + 15, node.getY() + 15, adjaency.getX() + 15, adjaency.getY() + 15);
                            }
                            g.drawLine(node.getX() + 15, node.getY() + 15, adjaency.getX() + 15, adjaency.getY() + 15);
                        }
                    }
                }
            }
        }
    }


    /**
     * when in the reinforcement,show which player and what phase and how many armies on the text
     */
    public void showReinforementMenu() {
        add(phaseText);
        remove(fortifyFrom);
        remove(fortifyTo);
        remove(fortifyArmies);
        remove(fortify);
        remove(endPhase);
        repaint();
    }

    public void removeButtonSetPlayer() {
        remove(setPlayer);
        remove(inputPlayerNumber);
    }

    /**
     * going to fortifitionPhase in the menu
     */
    public void showFortifitionPhase() {
        add(fortifyFrom);
        fortifyFrom.addItemListener(this);
        add(fortifyTo);
        add(fortifyArmies);
        add(fortify);
        add(endPhase);
        repaint();
    }

    /**
     * hide the fortifition menu from view
     */
    public void hideFortifitionPhase() {
        remove(fortifyFrom);
        remove(fortifyTo);
        remove(fortifyArmies);
        remove(fortify);
        remove(endPhase);
        repaint();
    }

    /**
     * when checbox's item changed,it changed
     * @param e ItemEvent
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Node node = (Node) fortifyFrom.getSelectedItem();
            getFortifyArmy(node);
            getReachableNode(node);
            repaint();
        }
    }

    /**
     * in fortification,it will get the adjancy node
     * @param node the node for which the accessible nodes will be returned
     */
    private void getReachableNode(Node node) {
        fortifyTo.removeAllItems();
        List<Node> reachableNodes = graph.reachableNodes(node);
        for (Node country : reachableNodes) {
            fortifyTo.addItem(country);
        }
    }

    /**
     * return the fortifyArmy from country
     * @param node country
     */
    public void getFortifyArmy(Node node) {
        fortifyArmies.removeAllItems();
        int armies = node.getArmies();
        switch (armies) {
            case 3:
                fortifyArmies.addItem(2);
                break;
            case 2:
                fortifyArmies.addItem(1);
                break;
            default:
                for (int i = 2; i <= armies - 1; i++) {
                    fortifyArmies.addItem(i);
                }
                break;
        }
    }

    /**
     * read the text field
     * @return text field
     */
    public JTextField getInputPlayerNumber() {
        return inputPlayerNumber;
    }

    /**
     * @return button
     */
    public JButton getSetPlayer() {
        return setPlayer;
    }

    /**
     * @return testfield
     */
    public JTextArea getPhaseText() {
        return phaseText;
    }

    /**
     * @return JComboBox
     */
    public JComboBox<Node> getFortifyFrom() {
        return fortifyFrom;
    }

    /**
     * @return JComboBox
     */
    public JComboBox<Node> getFortifyTo() {
        return fortifyTo;
    }

    /**
     * @return JComboBox
     */
    public JComboBox<Integer> getFortifyArmies() {
        return fortifyArmies;
    }

    /**
     * @return Jbutton
     */
    public JButton getFortify() {
        return fortify;
    }

    /**
     * @return JButton
     */
    public JButton getEndPhase() {
        return endPhase;
    }

    /**
     * @return List
     */
    public List<GameLabel> getLabelList() {
        return labelList;
    }

    /**
     * @return JComboBox
     */
    public JComboBox<Integer> getAttackerDice() {
        return attackerDice;
    }

    /**
     * @return JComboBox
     */
    public JComboBox<Integer> getDefenderDice() {
        return defenderDice;
    }

    /**
     * return strategy
     * @return return a button from view
     */
    public JButton getStartPlay() {
        return startPlay;
    }

    /**
     * @return  saveGameButton
     */
    public JButton getSaveGame() {
        return saveGame;
    }

    /**
     * draw the country name using label
     */
    public void addLabel() {
        for (Node country : graph.getGraphNodes()) {
            GameLabel label = new GameLabel(country.getName());
            label.setBounds(country.getX(), country.getY() + 33, 120, 30);
            add(label);
            labelList.add(label);
        }
    }

    /**
     * show user the attack menu
     * @param attacker attack country
     * @param defender attacked country
     */
    public void showAttackMenu(Node attacker, Node defender) {
        int[] playerdice = getDiceNumbers(attacker, defender);
        add(attack);
        add(retreat);
        add(autoFight);
        add(attackerDice);
        attackinformation.setText(attacker.getName() + " has " + playerdice[0] + "dice " + defender.getName() + " has " + playerdice[1] + " dice");
        add(attackinformation);
        int[] dices = getDiceNumbers(attacker, defender);
        attackerDice.removeAllItems();
        //defenderDice.removeAllItems();
        for (int i = 1; i <= dices[0]; i++) {
            attackerDice.addItem(i);
        }
        /*for (int i = 1; i <= dices[1]; i++) {
            defenderDice.addItem(i);
        }*/
        repaint();
    }

    /**
     * return two player's dices
     * @param attacker attacker node
     * @param defender defender node
     * @return two player's dices
     */
    public int[] getDiceNumbers(Node attacker, Node defender) {
        int[] list = new int[2];
        int attackerdicenumber = 0;
        int defenderdicenumber = 0;
        if (attacker.getArmies() < 2)
            return null;
        switch (attacker.getArmies()) {
            case 3:
                attackerdicenumber = 2;
                break;
            case 2:
                attackerdicenumber = 1;
                break;
            default:
                attackerdicenumber = 3;
        }
        switch (defender.getArmies()) {
            case 1:
                defenderdicenumber = 1;
                break;
            default:
                defenderdicenumber = 2;
        }
        list[0] = attackerdicenumber;
        list[1] = defenderdicenumber;
        return list;
    }

    /**
     * using observale pattern
     * @param obj the generdate player
     * @param o no use
     */
    @Override
    public void update(Observable obj, Object o) {
        List<Player> players = GameDriverController.getGameDriverInstance().getPlayers();
        if (!players.isEmpty()) {
            int x = 1120;
            int y = 80;
            int i=0;
            for (Player player : players) {
                playerList.get(i).setText(player.getName() + " " + player.getPercentage() + "%");
                if(!continentList.isEmpty()) {
                    continentList.get(i).removeAllItems();
                    for (Continent con : player.getContinents()) {
                        continentList.get(i).addItem(con);
                    }
                }
                x = x + 100;
                i++;
            }
        }
        add(view1);
        add(view2);
        add(phaseText);
        Player currentplayer=GameDriverController.getGameDriverInstance().getCurrentPlayer();
        if (currentplayer.getState() != null) {
            if (currentplayer.getState().equals("Fortification") && currentplayer.getStrategy() instanceof Human) {
                showFortifitionPhase();
            }else{
                hideFortifitionPhase();
            }
        }
        repaint();
    }

    /**
     * tell the user how it goes
     * @param numberofdice the dice given by controller
     */
    public void showAttackResult(List<List<Integer>> numberofdice) {
        String text = "";
        for (List<Integer> countrysequence : numberofdice) {
            for (Integer number : countrysequence) {
                text += (number + " ");
            }
            text += "\r\n";
        }
        attackinformation.setText(text);
        repaint();
    }

    /**
     * hide some menu when attack end
     */
    public void hideAttackMenu() {
        remove(attack);
        remove(retreat);
        remove(autoFight);
        remove(attackinformation);
        remove(attackerDice);
        remove(defenderDice);
        repaint();
    }

    /**
     * if a attack success,show fortify menu
     * @param attacker attacker node
     * @param defender defender node
     */
    public void moveArmiesToQuest(Node attacker, Node defender) {
        add(fortifyFrom);
        add(fortifyTo);
        add(fortifyArmies);
        add(move);
        fortifyFrom.removeAllItems();
        fortifyFrom.addItem(attacker);
        fortifyTo.removeAllItems();
        fortifyTo.addItem(defender);
        getFortifyArmy(attacker);
        repaint();
    }

    /**
     * hide some buttons
     */
    public void hideMoveArmyToQuestMenu() {
        remove(fortifyFrom);
        remove(fortifyTo);
        remove(fortifyArmies);
        remove(move);
        repaint();
    }

    /**
     * show the card view
     * @param player the current player
     */
    public void createCardView(Player player) {
        List<Card> cards = player.getCards();
        int x = 1120;
        int y = 500;
        if (cards != null) {
            if(!cardList.isEmpty()) {
                for(JCheckBox box:cardList) {
                    remove(box);
                }
                cardList.removeAll(cardList);
            }
            for (Card card : cards) {
                JCheckBox box = new JCheckBox(card.toString());
                box.setBounds(x, y, 60, 20);
                cardList.add(box);
                x += 70;
                add(box);
            }
            add(exchangeCard);
        }
    }

    /**
     * hide card view
     */
    public void hideCardView() {
        if(!cardList.isEmpty()) {
            for(JCheckBox box:cardList) {
                remove(box);
            }
        }
    }

    /**
     * select the card
     * @return a card list
     */
    public List<Card> getSelectCard() {
        List<Card> result = new ArrayList<>();
        for (JCheckBox box : cardList) {
            if (box.isSelected()) {
                String cardname = box.getText();
                Card card = Card.valueOf(cardname);
                result.add(card);
            }
        }
        return result;
    }

    /**
     * show who win
     */
    public void showWin() {
        phaseText.setText("win");
    }

    /**
     * emergy
     * @param players players
     */
    public void createPlayerLabel(List<Player> players) {
        int x = 1120;
        int y = 80;
        for (Player player : players) {
            JLabel playername = new JLabel(player.getName());
            playername.setBounds(x, y, 100, 30);
            add(playername);
            x = x + 100;
            playerList.add(playername);
        }
    }

    /**
     * chooseStrategieForPlayer
     * @param players List player
     */
    public void chooseStrategieForPlayer(List<Player> players) {
        int x=1150;
        int y=500;
        add(startPlay);
        tempStrategie=new ArrayList<JComboBox>();
        tempLabel=new ArrayList<>();
        for(Player player:players) {
            JLabel label=new JLabel(player.getName());
            label.setBounds(x, y, 100, 30);
            add(label);
            JComboBox strategiebox=new JComboBox();
            strategiebox.addItem("Human");
            strategiebox.addItem("Aggressive");
            strategiebox.addItem("Benevolent");
            strategiebox.addItem("RandomPlayer");
            strategiebox.addItem("Cheater");
            strategiebox.setBounds(x+110, y, 150,30 );
            add(strategiebox);
            y+=50;
            tempStrategie.add(strategiebox);
            tempLabel.add(label);
        }
        addContinent(players);
        repaint();
    }

    public void addContinent(List<Player> players) {
        int x=1120;
        for(Player player : players) {
            JComboBox<Continent> continentsOwnByPlayer = new JComboBox<>();
            continentsOwnByPlayer.setBounds(x, 150, 100, 30);
            continentList.add(continentsOwnByPlayer);
            add(continentsOwnByPlayer);
            x+=120;
        }
    }

    public void removeChooseStrategie() {
        remove(startPlay);
        for(JComboBox jb:tempStrategie) {
           remove(jb);
        }
        for(JLabel label:tempLabel) {
            remove(label);
        }
        add(saveGame);
    }

    public List<String> getstrategieInformation() {
        ArrayList<String> list=new ArrayList<>();
        for(JComboBox jb:tempStrategie) {
            list.add((String) jb.getSelectedItem());
        }
        return list;
    }


    public void removeCards(List<Card> cards) {
        if(!cards.isEmpty()) {
            for(JCheckBox box:cardList) {
                remove(box);
            }
        }
    }
}
