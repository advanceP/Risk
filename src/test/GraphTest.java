package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import risk.entities.Graph;

public class GraphTest {
	Graph graph;
	String invalidMap;
	String validMap;
	
	@Before public void before()
	{
		graph=Graph.getGraphInstance();
		invalidMap="b.map";
		validMap="a.map";
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

}
