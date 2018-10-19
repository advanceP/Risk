package test;

import static org.junit.Assert.assertSame;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import risk.entities.GameDriver;
import risk.entities.Graph;

public class PlayerTest {
	GameDriver driver;
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
		driver =GameDriver.getGameDriverInstance();
	}
	@Test public void testNumberOfCountries()
	{
		int expectedvalue=2;
		driver.setPlayers(2);
		assertSame(expectedvalue, driver.getPlayers().get(0).getNumberOfCountries());
	}

}
