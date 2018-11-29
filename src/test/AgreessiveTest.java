package test;

import org.junit.Before;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.model.*;
import risk.strategy.Aggressive;
import risk.strategy.Cheater;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AgreessiveTest {

    GameDriverController driver;
    Graph graph;
    int numberOfPlayers;
    String fileName;

    /**
     * initial params
     */
    @Before
    public void before() {
        fileName = "src/test/Montreal.map";
        graph = Graph.getGraphInstance();
        try {
            graph.createGraph(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver = GameDriverController.getGameDriverInstance();
    }

    @Test
    public void testReachableNodes() {
        driver.setPlayers(2, true);
        List<String> str = new ArrayList<>();
        str.add("Aggressive");
        str.add("Aggressive");
        driver.giveStrategyToPlayer(str, true);
        List<Player> players = driver.getPlayers();
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        nodeList.get(0).setArmies(4);
        currentPlayer.executeStrategyRein(null);
        Aggressive strategy = (Aggressive) currentPlayer.getStrategy();
        Node to = strategy.getStrongestNode(nodeList);
        List<Node> nodes = new ArrayList<>();
        strategy.reachableNodes(to, nodeList, nodes);
        List<Node> nodes2 = graph.reachableNodes(to);
        assertTrue(nodes.containsAll(nodes2));
        assertTrue(nodes2.containsAll(nodes));
    }


    @Test
    public void testGetStrongestNode() {
        driver.setPlayers(2, true);
        List<String> str = new ArrayList<>();
        str.add("Aggressive");
        str.add("Aggressive");
        driver.giveStrategyToPlayer(str,true);
        List<Player> players = driver.getPlayers();
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        nodeList.get(0).setArmies(4);
        Aggressive strategy = (Aggressive) currentPlayer.getStrategy();
        Node strongestNode = strategy.getStrongestNode(currentPlayer.getNodeList());
        assertSame(nodeList.get(0), strongestNode);
    }

    /**
     * test to see if weather benevolent player attack or not(it should not)
     */
    @Test
    public void testBenevolentAttack() {
        driver.setPlayers(2,true);
        List<String> str = new ArrayList<>();
        str.add("Benevolent");
        str.add("Benevolent");
        driver.giveStrategyToPlayer(str, true);
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        nodeList.get(0).setArmies(10);
        //currentPlayer.executeStrategyRein(null);
        boolean attack = currentPlayer.attack(null, null, null, null);
        assertFalse(attack);

    }

    /**
     * test to see if the Cheater player reinforcement double the number of armies
     */
    @Test
    public void testCheaterReinforcement() {
        driver.setPlayers(2,true);
        List<String> str = new ArrayList<>();
        str.add("Cheater");
        str.add("Cheater");
        driver.giveStrategyToPlayer(str, true);
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        nodeList.get(0).setArmies(10);
        Cheater strategy = (Cheater) currentPlayer.getStrategy();
        strategy.reinforcement(null);
        int cheaterArmies = nodeList.get(0).getArmies();
        int expectedArmeis =20;
        assertSame(expectedArmeis,cheaterArmies);
    }



    /**
     * test to see if the Cheater player reinforcement double the number of armies and it's in all countries
     */
    @Test
    public void testCheaterReinforcementAll() {
        driver.setPlayers(2, true);
        List<String> str = new ArrayList<>();
        str.add("Cheater");
        str.add("Cheater");
        driver.giveStrategyToPlayer(str, true);
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        nodeList.get(0).setArmies(10);
        nodeList.get(1).setArmies(2);
        Cheater strategy = (Cheater) currentPlayer.getStrategy();
        strategy.reinforcement(null);
        int cheaterArmies = nodeList.get(0).getArmies();
        int expectedArmeis =20;
        int cheaterArmies2 = nodeList.get(1).getArmies();
        int expectedArmeis2 =4;
        assertSame(expectedArmeis,cheaterArmies);
        assertSame(expectedArmeis2,cheaterArmies2);
    }


    /**
     * test to see if the Aggressive player reinforcement only increase the strongest Country
     */
    @Test
    public void testAgressive() {
        driver.setPlayers(2, true);
        List<String> str = new ArrayList<>();
        str.add("Aggressive");
        str.add("Aggressive");
        driver.giveStrategyToPlayer(str, true);
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        Node node1 = nodeList.get(0);
        Node anotherNode = nodeList.get(1);
        node1.setArmies(10);
        anotherNode.setArmies(3);
        int strongestNodeArmiesBefore = node1.getArmies();
        int anotherNodeArmiesBefore = anotherNode.getArmies();
        Aggressive strategy = (Aggressive) currentPlayer.getStrategy();
        strategy.reinforcement(null);
        Node strongestNode = strategy.getStrongestNode(nodeList);
        int strongestNodeArmiesAfter = strongestNode.getArmies();
        int anotherNodeArmiesAfter = anotherNode.getArmies();
        assertSame(anotherNodeArmiesBefore,anotherNodeArmiesAfter);
        assertThat(strongestNodeArmiesAfter, not(equalTo(strongestNodeArmiesBefore)));
    }
}
