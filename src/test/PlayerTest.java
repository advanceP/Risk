package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.model.Card;
import risk.model.Graph;
import risk.model.Node;
import risk.model.Player;
import risk.model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;


public class PlayerTest 

{
    GameDriverController driver;
    Graph graph;
    int numberOfPlayers;
    String fileName;

    @Before
    public void before() 
    
    {
        fileName = "src/test/a.map";
        graph = Graph.getGraphInstance();
        try {
            graph.createGraph(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver = GameDriverController.getGameDriverInstance();
    }

    
    @Test
    public void testNumberOfCountries() 
    
    {
        int expectedvalue = 2;
        driver.setPlayers(2);
        assertSame(expectedvalue, driver.getPlayers().get(0).getNumberOfCountries());
    }

    @Test
    public void testSameExchangeCard() 
    {
        driver.setPlayers(2);
        int expectedvalue = 7;
        int cardnumbers = 1;
        List<Card> cards = new ArrayList<Card>();
        cards.add(Card.Artillery);
        cards.add(Card.Artillery);
        cards.add(Card.Artillery);
        cards.add(Card.Infantry);
        graph.getGraphNodes().get(0).getPlayer().setCards(cards);
        graph.getGraphNodes().get(0).getPlayer().exchangeCardToArmies(cards);
        assertSame(expectedvalue, graph.getGraphNodes().get(0).getPlayer().getReinforcement());
        assertSame(cardnumbers, graph.getGraphNodes().get(0).getPlayer().getCards().size());
    }

    @Test
    public void testFiveExchangeCard() 
    {
        driver.setPlayers(2);
        int expectedvalue = 7;
        int cardnumbers = 2;
        List<Card> cards = new ArrayList<Card>();
        cards.add(Card.Artillery);
        cards.add(Card.Artillery);
        cards.add(Card.Artillery);
        cards.add(Card.Infantry);
        cards.add(Card.Cavalry);
        graph.getGraphNodes().get(0).getPlayer().setCards(cards);
        graph.getGraphNodes().get(0).getPlayer().exchangeCardToArmies(cards);
        assertSame(expectedvalue, graph.getGraphNodes().get(0).getPlayer().getReinforcement());
        assertSame(cardnumbers, graph.getGraphNodes().get(0).getPlayer().getCards().size());
    }

    @Test
    public void testSetNumberOfCountries() throws FileNotFoundException
    {
        int playerCoutries = 0;
        int expectedNumberOfCountries = 0;

        driver.setPlayers(2);
        driver.getPlayers().get(0).setNumberOfCountries(3);
        expectedNumberOfCountries = 3;
        playerCoutries = driver.getPlayers().get(0).getNumberOfCountries();

        assertSame(expectedNumberOfCountries, playerCoutries);
    }

    @Test
    public void testReinforcement() throws FileNotFoundException 
    {
        int initialReinforcement = 0;
        int expectedReinforcment = 1;
        int toTestReinforcment = 0;


        driver.setPlayers(2);
        driver.getPlayers().get(0).setReinforcement(initialReinforcement);
        driver.getPlayers().get(0).increaseReinforcement();
        toTestReinforcment = driver.getPlayers().get(0).getReinforcement();

        assertSame(expectedReinforcment, toTestReinforcment);
    }

    @Test
    public void testReinforcementStartupState() throws FileNotFoundException 
    {
        int expectedReinforcment = 1;
        int toTestReinforcment = 0;


        driver.setPlayers(2);
        driver.getPlayers().get(0).setState("StartUp");
        driver.getPlayers().get(0).Reinforcement();
        toTestReinforcment = driver.getPlayers().get(0).getReinforcement();

        assertSame(expectedReinforcment, toTestReinforcment);

    }

    @Test
    public void testReinforcementReinforcementState() throws FileNotFoundException 
    {
        int expectedReinforcment = 11;
        int toTestReinforcment = 0;


        driver.setPlayers(2);
        driver.getPlayers().get(0).setState("Reinforcement");
        driver.getPlayers().get(0).setReinforcement(10);
        driver.getPlayers().get(0).Reinforcement();
        toTestReinforcment = driver.getPlayers().get(0).getReinforcement();

        assertSame(expectedReinforcment, toTestReinforcment);

    }


    @Test
    public void testAditionalReinforcement() throws FileNotFoundException 
    {
        int expectedAdditionalreinforcement = 14;
        int toTestReinforcment = 0;
        driver.setPlayers(2);
        driver.getPlayers().get(0).setState("Reinforcement");
        driver.getPlayers().get(0).setReinforcement(10);
        driver.getPlayers().get(0).setNumberOfCountries(10);
        driver.getPlayers().get(0).Reinforcement();
        toTestReinforcment = driver.getPlayers().get(0).getReinforcement();
        assertSame(expectedAdditionalreinforcement, toTestReinforcment);
    }
    
    
    @Test
    public void testPercentage()
    {
    	int expectedvalue=50;
    	driver.setPlayers(2);
    	assertSame(expectedvalue, driver.getPlayers().get(0).getPercentage());
    	assertSame(expectedvalue, driver.getPlayers().get(1).getPercentage());
    }
    
    @Test
    public void testFortification()
    {
    	int[] expectedvalue= {2,0};
    	driver.setPlayers(2);
    	Node from=new Node("from",driver.getCurrentPlayer().getContinents().get(0),110,120);
    	from.setArmies(1);
    	Node to=new Node("from",driver.getCurrentPlayer().getContinents().get(0),110,130);
    	to.setArmies(1);
    	driver.getCurrentPlayer().Fortification(from, to, 1);
    	assertSame(expectedvalue[0], to.getArmies());
    	assertSame(expectedvalue[1], from.getArmies());
    }

    @Test
    public void testAttackResult()
    {
        driver.setPlayers(2);
        Player p1=driver.getPlayers().get(0);
        Player p2=driver.getPlayers().get(1);
        Continent con=new Continent("asia", 2);
        Node attacker=new Node("a", con, 11, 22);
        Node defender=new Node("b", con, 15, 24);
        attacker.addToAdjacency(defender.getName());
        defender.addToAdjacency(attacker.getName());
        attacker.setPlayer(p1);
        defender.setPlayer(p2);
        attacker.setArmies(8);
        defender.setArmies(3);
        List<List<Integer>> diceNumList = p1.getDiceNumList(attacker, defender);
        assertFalse(p1.attackResult(attacker,defender ,diceNumList ));
    }


}
