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
    private Strategy strategy;
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
    
    
    public void setStrategy(String strategy)
    {
        switch (strategy) {
            case "Human":
                this.strategy = new Human();
                break;
            case "Aggressive":
                this.strategy = new Aggressive();
                break;
            case "Benevolent":
                this.strategy = new Benevolent();
                break;
            case "RandomPlayer":
                this.strategy = new RandomPlayer();
                break;
            case "Cheater":
                this.strategy = new Cheater();
                break;
            default:
                this.strategy = new Human();
        }
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
     * returns the Player's State i.e. Reinforcement, StartUp, fortification, Attack etc.
     *
     * @return a String
     */
    public String getState() {
        return state;
    }


    /**
     * set the Player's State. i.e Reinforcement, StartUp, fortification, Attack etc.
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
        return Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getPlayer().getName().
                equals(this.name)).collect(Collectors.toList());
    }

    public List<Continent> getContinents() {
        for (int i = 0; i < Graph.getGraphInstance().getContinents().size(); i++) {
            if (Graph.getGraphInstance().ifContinentConquered(Graph.getGraphInstance().getContinents().get(i))) {
                String continentname = Graph.getGraphInstance().getContinents().get(i).getName();
                Node continentnode = Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getContinent().
                                        getName().equals(continentname)).findAny().get();
                if (continentnode.getPlayer().getName().equals(this.name))
                    continents.add(Graph.getGraphInstance().getContinents().get(i));
            }
        }
        return continents;
    }



    /**
     *this method calculate the number of reinforcement for each player
     */
    public void calculateReinforcement() {
        if (this.state.equals("StartUp"))
            this.setReinforcement(0);
        if (this.state.equals("Reinforcement"))
            this.setReinforcement(reinforcement);

        this.setState("Reinforcement");
        int additionalreinforcement = Math.round((float)this.getNumberOfCountries() / 3);
        if(additionalreinforcement<3)
        	additionalreinforcement=3;
        for (int i = 0; i < Graph.getGraphInstance().getContinents().size(); i++) {
            if (Graph.getGraphInstance().ifContinentConquered(Graph.getGraphInstance().getContinents().get(i))) {
                String continentname = Graph.getGraphInstance().getContinents().get(i).getName();
                Node continentnode = Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getContinent().getName().
                                    equals(continentname)).findAny().get();
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
     * @description get the result of attacking(includes changing the armies of nodes)
     * @param diceNumberList diceNumberList of attacker( list.get(0) ) and defender( list.get(1) )
     * @param attacker       the node of attacker
     * @param defender       the node of defender
     * @return a list of numbers that node lost	list.get(0) is attacker's and list.get(1) is defender's
     * @author Yiying Liu
     * @date 2018-11-04
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
                List<Card> list = Collections.unmodifiableList(Arrays.asList(Card.values()));
                int size = list.size();
                Random rnd = new Random();
                attacker.getPlayer().addCards(list.get(rnd.nextInt(size)));
                
                if(defender.getPlayer().getNodeList().size()<1)
                {
                	attacker.getPlayer().addCards(defender.getPlayer().getCards());
                	defender.getPlayer().setCards(null);
                }
                defender.setPlayer(this);
                setChanged();
                notifyObservers();
                return true;
            }
        }
        setChanged();
        notifyObservers();
        return false;
    }

    /**
     * @param attacker the country used by attacker to attack the other one
     * @param defender the country which is being attacked
     * @return array the first element is number of dices for attacker and second element number of dices for defender
     */
    public int[] getDiceNumbers(Node attacker, Node defender) {

        int[] list = new int[2];
        int attackerDiceNum = 0;
        int defenderDiceNum = 0;

        if (attacker.getArmies() < 2) {
            return null;
        } else if (attacker.getArmies() <= 3) {
            attackerDiceNum = attacker.getArmies() - 1;
        } else {
            attackerDiceNum = 3;
        }

        if (defender.getArmies() < 2){
            defenderDiceNum = defender.getArmies();
        }  else{
            defenderDiceNum = 2;
        }

        list[0] = attackerDiceNum;
        list[1] = defenderDiceNum;

        return list;
    }

    /**
     * @return the percentage of map being controlled for each player
     */
    public int getPercentage() {
        float result = 0;
        result = ((float) (this.getNodeList().size()) / Graph.getGraphInstance().getGraphNodes().size());
        return (Math.round(result * 100));
    }

    /**
     * increase the number of armies in each country and decrease its player's reinforcement armies
     */
    public void reinforcement() {
    	//strategy.reinforcement();
    }

    /**
     * give strategy
     * @return instance of Strategy
     */
    public Strategy getStrategy()
    {
    	return this.strategy;
    }

    /**
     * this method is being used in fortification phase it transfer armies from one country to another.
     *
     * @param from   the country whose armies will be moved
     * @param to     the country who will receive armies
     * @param armies number of armies that will be transfered
     */
    public void fortification(Node from, Node to, int armies) {
        from.setArmies(from.getArmies() - armies);
        to.setArmies(to.getArmies() + armies);
        setChanged();
        notifyObservers();
    }

    /**
     * @return the overall number of armies for the player
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
     * @param card list of cards to be exchanged with armies according to risk rules
     */
    public void exchangeCardToArmies(List<Card> card) {
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
        if (card.size() >= 3) {
            if (card.containsAll(different)) {
                this.cards.remove(different.get(0));
                this.cards.remove(different.get(1));
                this.cards.remove(different.get(2));
                increaseReinforcement(3);
            } else {
                if (card.containsAll(artillery)) {
                    this.cards.remove(artillery.get(0));
                    this.cards.remove(artillery.get(0));
                    this.cards.remove(artillery.get(0));
                    increaseReinforcement(3);
                } else if (card.containsAll(cavalry)) {
                    this.cards.remove(cavalry.get(0));
                    this.cards.remove(cavalry.get(0));
                    this.cards.remove(cavalry.get(0));
                    increaseReinforcement(3);
                } else if (card.containsAll(infantry)) {
                    this.cards.remove(infantry.get(0));
                    this.cards.remove(infantry.get(0));
                    this.cards.remove(infantry.get(0));
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
     * this method determine whether a player won the game or not
     * @param nodes number of nodes
     * @return boolean
     */
    public boolean isWin(List<Node> nodes) {
        if (numberOfCountries == nodes.size()) {
            this.setState("Win");
            return true;
        }
        return false;
    }

    /**
     * @param attacker country being used by the player for attack 
     * @param defender country that is being attacked
     * @param attackerDice number of dices for attacker
     * @param defenderDice number of armies for defender
     * @return the results of rolled dices
     */
    public List<List<Integer>> getDiceNumList(Node attacker, Node defender, int attackerDice, int defenderDice) {
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> attackerList = new ArrayList<>();
        List<Integer> defenderList = new ArrayList<>();
        resultList.add(attackerList);
        resultList.add(defenderList);
        Random random = new Random();
        int[] numbers = getDiceNumbers(attacker, defender);

        for (int i = 0; i < attackerDice; i++) {
            attackerList.add(random.nextInt(6) + 1);
        }
        for (int j = 0; j < defenderDice; j++) {
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

    public void addArmyRandomly() {
        Random random=new Random();
        List<Node> nodes = getNodeList();
        Node node = nodes.get(random.nextInt(nodes.size()));
        increaseArmy(node);
    }

    public void increaseArmy(Node node) {
        node.increaseArmy();
        decreaseReinforcement();
    }
    /** 
     * <p>Description:  using strategy pattern to execute reinforcement</p>
     * @param country the node of player which add army in reinforcement phase
     * @author Yiying Liu
     * @date 2018-11-19
     */
    public void executeStrategyRein(Node country){
        this.getStrategy().reinforcement(country);
    }
}

