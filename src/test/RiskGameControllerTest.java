package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import risk.controller.GameDriverController;
import risk.controller.RiskGameController;
import risk.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class RiskGameControllerTest {
	String fileaddress="src/test/game.txt";
	
	/**
	 * this method test whether number of saved player is correct
	 */
 @Test
	public void testPlayerNumberSavedGame()
	{
		int expectedvalue=2;
		RiskGameController controller=new RiskGameController();
		File file=new File(fileaddress);
		GameDriverController driver=controller.getSavedGame(file);
		assertSame(expectedvalue, driver.getPlayers().size());
		
	}
 
   /**
    * this method tests whether current player is correct in saved game
    */
	@Test
	public void testCurrentPlayerSavedGame()
	{
		String expectedvalue="Player_1";
		String name= GameDriverController.getGameDriverInstance().getCurrentPlayer().getName();
		assertTrue(expectedvalue.equals(name));
	}
	
	/**
	 * this method test number of reinforcement for current player
	 */
	@Test
	public void testSavedCurrentPlayerReinforcement()
	{
		int expectedvalue=3;
		int size=GameDriverController.getGameDriverInstance().getCurrentPlayer().getReinforcement();
		assertSame(expectedvalue,size);
	}
	
	/**
	 * this method tests number of reinforcement for all nodes
	 */
	@Test
	public void testSavedAllPlayerReinforcement()
	{
		int expectedvalue=3;
		int size=GameDriverController.getGameDriverInstance().getAllReinforcement();
		assertSame(expectedvalue,size);
	}
	
	/**
     * clear node
     */
    @After
    public void after() 
    {
    }

}
