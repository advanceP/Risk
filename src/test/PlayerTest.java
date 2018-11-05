package test;

import org.junit.Before;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.model.Graph;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertSame;


public class PlayerTest {
	GameDriverController driver;
	Graph graph;
	int numberOfPlayers;
	String fileName;
	@Before public void before()
	{
		fileName="src/test/a.map";
		graph=Graph.getGraphInstance();
		try {
			graph.createGraph(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = GameDriverController.getGameDriverInstance();
	}
	@Test public void testNumberOfCountries()
	{
		int expectedvalue=2;
		driver.setPlayers(2);
		assertSame(expectedvalue, driver.getPlayers().get(0).getNumberOfCountries());
	}

}
