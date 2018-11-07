package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import risk.model.Graph;


public class GraphTest {
	Graph graph;
	String invalidMap;
	String validMap;
	String colorsMap;
	
	@Before public void before()
	{
		graph=Graph.getGraphInstance();
		invalidMap="src/test/d.map";
		validMap="src/test/a.map";
		colorsMap="src/test/colors.map";
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
////	public void getColorTOContinent()
////	{
////		ArrayList<Color> colors=new ArrayList<>();
////		colors.add(Color.BLACK);
////		colors.add(Color.cyan);
////		colors.add(Color.DARK_GRAY);
////		colors.add(Color.GRAY);
////		colors.add(Color.MAGENTA);
////		colors.add(Color.PINK);
////		colors.add(Color.ORANGE);
////		colors.add(Color.LIGHT_GRAY);
////		for(int i=0;i<graph.getContinents().size();i++)
////		{
////			graph.getContinents().get(i).setColor(colors.get(i));
////		}
////	}
//	
//	@test public void hasColor()
//	{
//		ArrayList<Color> colors=new ArrayList<>();
//		try {
//			graph.createGraph(validMap);
//			
//			colors.add(graph.getContinents().get(1).getColor());
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		assertTrue(graph.verifyGraph());
//	}
	@Test public void hasColor()
	{
		ArrayList<Color> expectedColors=new ArrayList<>();
		ArrayList<Color> initiatedColor=new ArrayList<>();
		expectedColors.add(Color.BLACK);
		expectedColors.add(Color.cyan);
		expectedColors.add(Color.DARK_GRAY);
		expectedColors.add(Color.GRAY);
		expectedColors.add(Color.MAGENTA);
		expectedColors.add(Color.PINK);
		expectedColors.add(Color.ORANGE);
		expectedColors.add(Color.LIGHT_GRAY);
		//Color black  = Color.BLACK; 
		//Color color = null;
		
		try {
			
			graph.createGraph(colorsMap);
			graph.getColorTOContinent();
			
			for	(int i = 0; i < graph.getContinents().size(); i++)
			{
				initiatedColor.add(graph.getContinents().get(i).getColor());
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		//assertNull(color);
		for	(int j = 0; j < graph.getContinents().size(); j++) 
		{
		assertEquals(expectedColors.get(j),initiatedColor.get(j));
		}
		
	}

}
