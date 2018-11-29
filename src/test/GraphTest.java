package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import risk.model.Graph;
import risk.model.Node;


/**
 * @author Farid Omarzadeh
 *
 */
public class GraphTest 
{
    Graph graph;
    String invalidMap;
    String validMap;
    String colorsMap;

    /**
     * initial graph
     */
    @Before
    public void before() 
    {
        graph = Graph.getGraphInstance();
        invalidMap = "src/test/d.map";
        validMap = "src/test/a.map";
        colorsMap = "src/test/colors.map";
    }

    /**
     * test validVerify
     */
    @Test
    public void testUnValidVerify() 
    {
        try {
            graph.createGraph(invalidMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertFalse(graph.verifyGraph());
    }

    /**
     * test validVerify
     */
    @Test
    public void testValidVerify() 
    {
        try {
            graph.createGraph(validMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertTrue(graph.verifyGraph());
    }

    /**
     * test is Graphconnected
     */
    @Test
    public void testIsGraphConnected() 
    {
        try {
            graph.createGraph(validMap);
            assertTrue(graph.isGraphConnected());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * test is visited
     */
    @Test
    public void testIsGraphVisited() 
    {
        try {
            graph.createGraph(validMap);
            graph.setGraphVisited();
            assertTrue(graph.ifGraphVisited());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * test ContinentColor
     */
    @Test
    public void testContinentsColor() 
    {
        ArrayList<Color> expectedColors = new ArrayList<>();
        ArrayList<Color> initiatedColor = new ArrayList<>();
        expectedColors.add(Color.BLACK);
        expectedColors.add(Color.cyan);
        expectedColors.add(Color.DARK_GRAY);
        expectedColors.add(Color.GRAY);
        expectedColors.add(Color.MAGENTA);
        expectedColors.add(Color.PINK);
        expectedColors.add(Color.ORANGE);
        expectedColors.add(Color.LIGHT_GRAY);


        try {

            graph.createGraph(colorsMap);
            graph.getColorTOContinent();

            for (int i = 0; i < graph.getContinents().size(); i++) {
                initiatedColor.add(graph.getContinents().get(i).getColor());
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        for (int j = 0; j < graph.getContinents().size(); j++) {
            assertEquals(expectedColors.get(j), initiatedColor.get(j));
        }

    }

    @Test
    public void testGetAdjacencyNodes() {
        try {
            graph.createGraph(validMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Node node = graph.getGraphNodes().get(0);
        List<Node> adjacencyNodes = node.getAdjacencyNodes();
        List<String> names=new ArrayList<>();
        for(Node n:adjacencyNodes) {
            String name = n.getName();
            names.add(name);
        }
        assertTrue(node.getAdjacencyList().containsAll(names));

    }

}
