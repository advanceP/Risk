package test;


import org.junit.After;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.controller.RiskGameController;
import risk.model.Graph;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


/**
 * test gamedriver
 * @author Farid Omarzadeh
 */
public class GameDriverTest 
{
    GameDriverController driver;
    Graph graph;
    int numberOfPlayers;
    String firstFileName;
    String secondFileName;


    /**
     * test reinforement
     */
    @Test
    public void testReinforcementWithNoConqueredContinents() 
    {
        firstFileName = "src/test/a.map";
        graph = Graph.getGraphInstance();
        try 
        {
            graph.createGraph(firstFileName);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        driver = GameDriverController.getGameDriverInstance();
        int expectedvalue = 3;
        driver.setPlayers(2, false);
        driver.getCurrentPlayer().setState("StartUp");
        driver.getCurrentPlayer().calculateReinforcement();
        assertSame(expectedvalue, driver.getCurrentPlayer().getReinforcement());
    }

    /**
     * test reinforement
     */
    @Test
    public void testReinforcementWithConqueredContinents() 
    {
        secondFileName = "src/test/c.map";
        graph = Graph.getGraphInstance();
        try {
            graph.createGraph(secondFileName);
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver = GameDriverController.getGameDriverInstance();
        int expectedvalue = 5;
        driver.setPlayers(2,false);
        driver.getCurrentPlayer().setState("StartUp");
        driver.getCurrentPlayer().calculateReinforcement();
        assertSame(expectedvalue, driver.getCurrentPlayer().getReinforcement());
    }

    /**
     * testInitial
     */
    @Test
    public void testInitialArmy() 
    {

        firstFileName = "src/test/a.map";
        graph = Graph.getGraphInstance();
        try 
        {
            graph.createGraph(firstFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        driver = GameDriverController.getGameDriverInstance();
        int expectedvalue = 4;
        driver.setPlayers(2,false);
        driver.getCurrentPlayer().setState("StartUp");
        assertSame(expectedvalue, driver.getCurrentPlayer().getReinforcement());
    }

    /**
     * test GetAllArmy
     */
    @Test
    public void testGetAllArmy() 
    {
        firstFileName = "src/test/a.map";
        graph = Graph.getGraphInstance();
        try 
        {
            graph.createGraph(firstFileName);
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        driver = GameDriverController.getGameDriverInstance();
        driver.reset();
        int expectedvalue = 8;
        driver.setPlayers(2,false);
        assertSame(expectedvalue, driver.getAllReinforcement());
    }
    
    @Test
	public void testPlayerNumberSavedGame()
	{
		int expectedvalue=2;
		RiskGameController controller=new RiskGameController();
		File file=new File("src/test/game.txt");
		GameDriverController driver=controller.getSavedGame(file);
		assertSame(expectedvalue, driver.getPlayers().size());
		
	}

    /**
     * clear node
     */
    @After
    public void after() 
    {
        Graph.getGraphInstance().setGraphNodes(null);
    }
}
