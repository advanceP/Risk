package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.model.Card;
import risk.model.Graph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
	@Test public void testSameExchangeCard()
	{
		driver.setPlayers(2);
		int expectedvalue=7;
		int cardnumbers=1;
		List<Card>cards=new ArrayList<Card>();
		cards.add(Card.Artillery);
		cards.add(Card.Artillery);
		cards.add(Card.Artillery);
		cards.add(Card.Infantry);
		graph.getGraphNodes().get(0).getPlayer().setCards(cards);
		graph.getGraphNodes().get(0).getPlayer().exchangeCardToArmies(cards);
		assertSame(expectedvalue,graph.getGraphNodes().get(0).getPlayer().getReinforcement());
		assertSame(cardnumbers, graph.getGraphNodes().get(0).getPlayer().getCards().size());
	}
	@Test public void testFiveExchangeCard()
	{
		driver.setPlayers(2);
		int expectedvalue=7;
		int cardnumbers=2;
		List<Card>cards=new ArrayList<Card>();
		cards.add(Card.Artillery);
		cards.add(Card.Artillery);
		cards.add(Card.Artillery);
		cards.add(Card.Infantry);
		cards.add(Card.Cavalry);
		graph.getGraphNodes().get(0).getPlayer().setCards(cards);
		graph.getGraphNodes().get(0).getPlayer().exchangeCardToArmies(cards);
		assertSame(expectedvalue,graph.getGraphNodes().get(0).getPlayer().getReinforcement());
		assertSame(cardnumbers, graph.getGraphNodes().get(0).getPlayer().getCards().size());
	}

}
