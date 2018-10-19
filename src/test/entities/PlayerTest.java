package test.entities;

import static org.junit.Assert.assertSame;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD:src/test/entities/PlayerTest.java
import risk.editormodule.Graph;
import risk.gameplayermodule.GameDriver;
=======
import risk.gamePlayer.GameDriver;
import risk.mapEditor.Graph;

>>>>>>> 860504d7c634ade95729dc567bec6a957fe7a872:src/test/PlayerTest.java

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
