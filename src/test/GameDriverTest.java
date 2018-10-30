package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import risk.model.GameDriver;
import risk.model.Graph;


public class GameDriverTest {
	GameDriver driver;
	Graph graph;
	int numberOfPlayers;
	String firstFileName;
	String secondFileName;
	@Before public void before()
	{
		
	}
	@Test public void testReinforcementWithNoConqueredContinents()
	{
		firstFileName="src/test/a.map";
		graph=Graph.getGraphInstance();
		try {
			graph.createGraph(firstFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver =GameDriver.getGameDriverInstance();
		int expectedvalue=1;
		driver.setPlayers(2);
		driver.getCurrentPlayer().setState("StartUp");
		assertSame(expectedvalue, driver.getReinforcementPlayer().getReinforcement());
	}
	@Test public void testReinforcementWithConqueredContinents()
	{
		secondFileName="src/test/c.map";
		graph=Graph.getGraphInstance();
		try {
			graph.createGraph(secondFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver =GameDriver.getGameDriverInstance();
		int expectedvalue=3;
		driver.setPlayers(2);
		driver.getCurrentPlayer().setState("StartUp");
		assertSame(expectedvalue, driver.getReinforcementPlayer().getReinforcement());
	}
	
	@Test public void testInitialArmy()
	{

		firstFileName="src/test/a.map";
		graph=Graph.getGraphInstance();
		try {
			graph.createGraph(firstFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver =GameDriver.getGameDriverInstance();
		int expectedvalue=4;
		driver.setPlayers(2);
		driver.getCurrentPlayer().setState("StartUp");
		assertSame(expectedvalue, driver.getCurrentPlayer().getReinforcement());
	}
	@Test public void testGetAllArmy()
	{
		firstFileName="src/test/a.map";
		graph=Graph.getGraphInstance();
		try {
			graph.createGraph(firstFileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver =GameDriver.getGameDriverInstance();
		int expectedvalue=8;
		driver.setPlayers(2);
		assertSame(expectedvalue, driver.getAllArmies());
	}
	
	@After public void after()
	{
		Graph.getGraphInstance().setGraphNodes(null);
	}
}
