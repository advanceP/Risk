package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import risk.entities.GameDriver;
import risk.entities.Graph;
import risk.entities.Player;

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
		firstFileName="a.map";
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
		secondFileName="c.map";
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
	@After public void after()
	{
		Graph.getGraphInstance().setGraphNodes(null);
	}
}
