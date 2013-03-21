package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.ClueGame;
import clueGame.Solution;

public class GameActionTest {
	
	ClueGame game = null;
	Solution testSolution = null;
	Solution wrongSolution = null;
	
	@BeforeClass
	public void setup() {
		game = new ClueGame();
		game.deal();
		testSolution = game.setSolution();  //testSolution is set to Col Mustard, Revolver, Ballroom
		wrongSolution = game.setWrongSolution();
	}
	
	@Test
	public void checkAccusationTest() {
		//Test if solution is correct
		Assert.assertEquals(true, game.checkAccusation(testSolution));
		
		//Test solution is not correct
		Assert.assertEquals(false, game.checkAccusation(wrongSolution));
	}
	
	@Test
	public void checkTargetLocationTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void disproveSuggestionTest() {
		fail("Not yet implemented");
	}
	
	@Test
	public void makeSuggestionTest() {
		fail("Not yet implemented");
	}

}
