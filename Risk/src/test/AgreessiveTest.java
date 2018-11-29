package test;

import org.junit.Before;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.model.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
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
        driver.setPlayers(2);
        List<String> str = new ArrayList<>();
        str.add("Aggressive");
        str.add("Aggressive");
        driver.giveStrategieToPlayer(str);
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
    /**
     * test to see if weather benevolent player attack or not(should not attack)
     */
    @Test
    public void testBenevolentAttack() {
        driver.setPlayers(2);
        List<String> str = new ArrayList<>();
        str.add("Benevolent");
        str.add("Benevolent");
        driver.giveStrategieToPlayer(str);
        Player currentPlayer = driver.getCurrentPlayer();
        List<Node> nodeList = currentPlayer.getNodeList();
        nodeList.get(0).setArmies(10);
        //currentPlayer.executeStrategyRein(null);
        boolean attack = currentPlayer.attack(null, null, null, null);
        assertFalse(attack);
        
    }
    }