package test.editormodule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD:src/test/editormodule/GraphTest.java
import risk.editormodule.Graph;
=======
import risk.mapEditor.Graph;

>>>>>>> 860504d7c634ade95729dc567bec6a957fe7a872:src/test/GraphTest.java

public class GraphTest {
	Graph graph;
	String invalidMap;
	String validMap;
	
	@Before public void before()
	{
		graph=Graph.getGraphInstance();
		invalidMap="src/test/d.map";
		validMap="src/test/a.map";
	}
	@Test public void testUnValidVerify()
	{
		try {
			graph.createGraph(invalidMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(graph.verifyGraph());
	}
	@Test public void testValidVerify()
	{
		try {
			graph.createGraph(validMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(graph.verifyGraph());
	}
	
	@Test public void testIsGraphConnected()
	{
		try {
			graph.createGraph(validMap);
			assertTrue(graph.isGraphConnected());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test public void testIsGraphVisited()
	{
		try {
			graph.createGraph(validMap);
			graph.setGraphVisited();
			assertTrue(graph.ifGraphVisited());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
