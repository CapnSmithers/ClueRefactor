package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.ClueGame;
import clueGame.HumanPlayer;

public class GameSetupTest {
	
	ClueGame game;
	
	@BeforeClass
	public void setup() {
		game = new ClueGame();
	}
	
	@Test
	public void loadPeopleTests() {
		HumanPlayer human = game.humanPlayer;
		Assert.assertEquals("Col Mustard", human.getPlayerName());
		Assert.assertEquals("", game.players[2].getPlayerName());
		Assert.assertEquals("", game.players[5].getPlayerName());
		
	
	}
	
	@Test
	public void loadCardTests() {
		
	}
	
	@Test
	public void dealCardTests() {
		
	}
}
