package risk.model;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * this class represent the player of the Game it contains :
 * <ul>
 * <li> name
 * <li> numberOfCountries
 * <li>state
 * <li>color
 * <li>reinforcement
 * </ul>
 *
 * @author Farid Omarzadeh
 */
public class Player extends Observable {
    private String name;
    private int numberOfCountries;
    private String state;
    private Color color;
    private int reinforcement;
    List<Card> cards = new ArrayList<Card>();

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    List<Continent> continents = new ArrayList<Continent>();


    public List<Card> getCards() {
        return cards;
    }


    public void addCards(Card card) {
        this.cards.add(card);
    }


    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }
    
    
    /**
     * returns the number of reinforcement for the player
     *
     * @return an Integer
     */
    public int getReinforcement() {
        return reinforcement;
    }


    /**
     * Decrease the Number of Player's Reinforcement
     */
    public void decreaseReinforcement() {
        this.reinforcement--;
        setChanged();
        notifyObservers();
    }


    /**
     * Increase the Number of Player's reinforcement by One
     */
    public void increaseReinforcement() {
        this.reinforcement++;
    }


    /**
     * increase the Number of Reinforcement
     *
     * @param army number of reinforcement to be increased
     */
    public void increaseReinforcement(int army) {
        this.reinforcement += army;
    }

    /**
     * set Number of Reinforcement
     *
     * @param reinforcement Integer
     */
    public void setReinforcement(int reinforcement) {
        this.reinforcement = reinforcement;
    }


    /**
     * returns the Player's Name
     *
     * @return a String
     */
    public String getName() {
        return name;
    }


    /**
     * set the Player's Name
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * returns the number of Countries that player owns
     *
     * @return an Integer
     */
    public int getNumberOfCountries() {
        return numberOfCountries;
    }


    /**
     * Increases the Number of Countries that player owns by One
     */
    public void increaseNumberOfCountries() {
        this.numberOfCountries++;
        setChanged();
        notifyObservers();
    }


    /**
     * Increase the Number of Countries a Player owns
     *
     * @param numberOfCountries How much should be increased
     */
    public void setNumberOfCountries(int numberOfCountries) {
        this.numberOfCountries = numberOfCountries;
    }


    /**
     * returns the Player's State i.e. Reinforcement, StartUp, Fortification, Attack etc.
     *
     * @return a String
     */
    public String getState() {
        return state;
    }


    /**
     * set the Player's State. i.e Reinforcement, StartUp, Fortification, Attack etc.
     *
     * @param state Player's state
     */
    public void setState(String state) {
        this.state = state;
        setChanged();
        notifyObservers();
    }


    /**
     * returns the Player's Color
     *
     * @return Color
     */
    public Color getColor() {
        return color;
    }


    /**
     * set the Player's Color
     *
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Returns the List of Countries that Player Owns
     *
     * @return a List<Node>
     */
    public List<Node> getNodeList() {
        return Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getPlayer().getName().equals(this.name)).collect(Collectors.toList());
    }

    public List<Continent> getContinents() {
        for (int i = 0; i < Graph.getGraphInstance().getContinents().size(); i++) {
            if (Graph.getGraphInstance().ifContinentConquered(Graph.getGraphInstance().getContinents().get(i))) {
                String continentname = Graph.getGraphInstance().getContinents().get(i).getName();
                Node continentnode = Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getContinent().getName().equals(continentname)).findAny().get();
                if (continentnode.getPlayer().getName().equals(this.name))
                    continents.add(Graph.getGraphInstance().getContinents().get(i));
            }
        }
        return continents;
    }


    /**
     *
     */
    public void Reinforcement() {
        if (this.state.equals("StartUp"))
            this.setReinforcement(0);
        if (this.state.equals("Reinforcement"))
            this.setReinforcement(reinforcement);

        this.setState("Reinforcement");
        int additionalreinforcement = this.getNumberOfCountries() / 3 + 1;
        for (int i = 0; i < Graph.getGraphInstance().getContinents().size(); i++) {
            if (Graph.getGraphInstance().ifContinentConquered(Graph.getGraphInstance().getContinents().get(i))) {
                String continentname = Graph.getGraphInstance().getContinents().get(i).getName();
                Node continentnode = Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getContinent().getName().equals(continentname)).findAny().get();
                if (continentnode.getPlayer().getName().equals(this.name))
                    additionalreinforcement += Graph.getGraphInstance().getContinents().get(i).getAwardUnits();
            }
        }

        increaseReinforcement(additionalreinforcement);
    }

    /**
     * @param attacker the country which attacks others
     * @param defender the country which is attacked
     * @Description: get the arrayList of numbers that attacker and defender rolling the dice
     * @return: the result of dice number lists of two countries. the first list is dice numbers of attacker, and second
     * is defender's
     * @Author: Yiying Liu
     * @Date: 2018-11-04
     */
    public List<List<Integer>> getDiceNumList(Node attacker, Node defender) {
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> attackerList = new ArrayList<>();
        List<Integer> defenderList = new ArrayList<>();
        resultList.add(attackerList);
        resultList.add(defenderList);
        Random random = new Random();
        int[] numbers = getDiceNumbers(attacker, defender);

        for (int i = 0; i < numbers[0]; i++) {
            attackerList.add(random.nextInt(6) + 1);
        }
        for (int j = 0; j < numbers[1]; j++) {
            defenderList.add(random.nextInt(6) + 1);
        }
        if (attackerList.size() > 1) {
            Collections.sort(attackerList, Collections.reverseOrder());
        }
        if (defenderList.size() > 1) {
            Collections.sort(defenderList, Collections.reverseOrder());
        }
        return resultList;

    }

    /**
     * @param diceNumberList diceNumberList of attacker( list.get(0) ) and defender( list.get(1) )
     * @param attacker       the node of attacker
     * @param defender       the node of defender
     * @Description: get the result of attacking(includes changing the armies of nodes)
     * @return: a list of numbers that node lost	list.get(0) is attacker's and list.get(1) is defender's
     * @Author: Yiying Liu
     * @Date: 2018-11-04
     */
    public boolean attackResult(Node attacker, Node defender, List<List<Integer>> diceNumberList) {
        if (diceNumberList.isEmpty()) {
            return false;
        }
        List<Integer> attackerList = diceNumberList.get(0);
        List<Integer> defenderList = diceNumberList.get(1);
        if (attackerList.isEmpty() || defenderList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < attackerList.size() && i < defenderList.size() && i < 2; i++) {
            if (attackerList.get(i) > defenderList.get(i)) {
                defender.setArmies(defender.getArmies() - 1);
            } else {
                attacker.setArmies(attacker.getArmies() - 1);
            }
            if (attacker.getArmies() == 1) {
                return false;
            }
            if (defender.getArmies() == 0) {
                increaseNumberOfCountries();
                defender.setPlayer(this);
                List<Card> list = Collections.unmodifiableList(Arrays.asList(Card.values()));
                int size = list.size();
                Random rnd = new Random();
                attacker.getPlayer().addCards(list.get(rnd.nextInt(size)));
                
                if(defender.getPlayer().getNodeList().size()<1)
                {
                	attacker.getPlayer().addCards(defender.getPlayer().getCards());
                	defender.getPlayer().setCards(null);
                }
                defender.setPlayer(attacker.getPlayer());
                return true;
            }
        }
        setChanged();
        notifyObservers();
        return false;
    }

    /**
     * @param attacker
     * @param defender
     * @return
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
     * @return
     */
    public int getPercentage() {
        float result = 0;
        result = ((float) (this.getNodeList().size()) / Graph.getGraphInstance().getGraphNodes().size());
        return (Math.round(result * 100));
    }

    /**
     * @param country
     */
    public void addReinforcementToNode(Node country) {
        country.increaseArmy();
        decreaseReinforcement();
    }


    /**
     * this method is being used in fortification phase it transfer armies from one country to another.
     *
     * @param from   the country whose armies will be moved
     * @param to     the country who will receive armies
     * @param armies number of armies that will be transfered
     */
    public void Fortification(Node from, Node to, int armies) {
        from.setArmies(from.getArmies() - armies);
        to.setArmies(to.getArmies() + armies);
        setChanged();
        notifyObservers();
    }

    /**
     * @return
     */
    public int getTotalArmies() {
        List<Node> nodes = getNodeList();
        int armies = 0;
        for (Node node : nodes) {
            armies += node.getArmies();
        }
        return armies;
    }

    /**
     * @param cards
     */
    public void exchangeCardToArmies(List<Card> cards) {
        List<Card> different = new ArrayList<Card>();
        different.add(Card.Artillery);
        different.add(Card.Infantry);
        different.add(Card.Cavalry);
        List<Card> artillery = new ArrayList<Card>();
        artillery.add(Card.Artillery);
        artillery.add(Card.Artillery);
        artillery.add(Card.Artillery);

        List<Card> cavalry = new ArrayList<Card>();
        cavalry.add(Card.Cavalry);
        cavalry.add(Card.Cavalry);
        cavalry.add(Card.Cavalry);

        List<Card> infantry = new ArrayList<Card>();
        infantry.add(Card.Infantry);
        infantry.add(Card.Infantry);
        infantry.add(Card.Infantry);
        if (cards.size() >= 3) {
            if (cards.containsAll(different)) {
                cards.remove(different.get(0));
                cards.remove(different.get(1));
                cards.remove(different.get(2));
                increaseReinforcement(3);
            } else {
                if (cards.containsAll(artillery)) {
                    cards.remove(artillery.get(0));
                    cards.remove(artillery.get(0));
                    cards.remove(artillery.get(0));
                    increaseReinforcement(3);
                } else if (cards.containsAll(cavalry)) {
                    cards.remove(cavalry.get(0));
                    cards.remove(cavalry.get(0));
                    cards.remove(cavalry.get(0));
                    increaseReinforcement(3);
                } else if (cards.containsAll(infantry)) {
                    cards.remove(infantry.get(0));
                    cards.remove(infantry.get(0));
                    cards.remove(infantry.get(0));
                    increaseReinforcement(3);
                } else {
                    throw new RuntimeException("cannot be exchanged");
                }
            }
        } else {
            throw new RuntimeException("cannot be exchanged");
        }
    }

    /**
     * @param nodes
     * @return
     */
    public boolean isWin(List<Node> nodes) {
        if (numberOfCountries == nodes.size()) {
            this.setState("Win");
            return true;
        }
        return false;
    }

    /**
     * @param attacker
     * @param defender
     * @param attackerdice
     * @param defenderdice
     * @return
     */
    public List<List<Integer>> getDiceNumList(Node attacker, Node defender, int attackerdice, int defenderdice) {
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> attackerList = new ArrayList<>();
        List<Integer> defenderList = new ArrayList<>();
        resultList.add(attackerList);
        resultList.add(defenderList);
        Random random = new Random();
        int[] numbers = getDiceNumbers(attacker, defender);

        for (int i = 0; i < attackerdice; i++) {
            attackerList.add(random.nextInt(6) + 1);
        }
        for (int j = 0; j < defenderdice; j++) {
            defenderList.add(random.nextInt(6) + 1);
        }
        if (attackerList.size() > 1) {
            Collections.sort(attackerList, Collections.reverseOrder());
        }
        if (defenderList.size() > 1) {
            Collections.sort(defenderList, Collections.reverseOrder());
        }
        return resultList;
    }


    /**
     * @return how many card in the player
     */
    public int checkPlayerCard() {
        List<Card> cardlist = getCards();
        Card Artillery = Card.Artillery;
        Card cavalry = Card.Cavalry;
        Card infantry = Card.Infantry;
        ArrayList<Card> type = new ArrayList<>();
        type.add(Artillery);
        type.add(cavalry);
        type.add(infantry);
        while (cardlist.size() > 5) {
            int[] flag=new int[3];
            int[] index={-1,-1,-1};
            for(Card c:cardlist){
                if(c==Artillery){
                    flag[0]=1;
                    index[0]=cardlist.indexOf(c);
                }
                if(c==cavalry){
                    flag[1]=1;
                    index[1]=cardlist.indexOf(c);
                }
                if(c==infantry){
                    flag[2]=1;
                    index[2]=cardlist.indexOf(c);
                }
            }
            if (index[0]!=-1&&index[1]!=-1&&index[2]!=-1) {
                exchangeCardToArmies(type);
                for(int i=0;i<index.length;i++){
                    cardlist.remove(index[i]);
                }
            }
        }
        int result =  cardlist.size();
        return result;
    }
}

