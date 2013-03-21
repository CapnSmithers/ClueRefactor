package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class GameActionTest {
	
	static ClueGame game = null;
	static Board board = null;
	static Solution testSolution = null;
	static Solution wrongSolution = null;
	
	@BeforeClass
	public static void setup() {
		board = new Board();
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
	
	//Tests for random location selection when no door is present
	@Test
	public void checkTargetRandomLocation() {
		ComputerPlayer compPlayer = new ComputerPlayer();
		//Location with no doors
		board.calcTargets(16, 2, 2);
		int location16_0 = 0;
		int location15_1 = 0;
		int location15_3 = 0;
		int location16_4 = 0;
		for(int i = 0; i > 100; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if(selected == board.getCellAt(16, 0)) {
				location16_0++;
			} else if (selected == board.getCellAt(15,1)) {
				location15_1++;
			} else if (selected == board.getCellAt(15, 3)) {
				location15_3++;
			} else {
				location16_4++;
			}
		}
		assertEquals(100, location16_0+location15_1+location15_3+location16_4);
		assertTrue(location16_0 > 12);
		assertTrue(location16_4 > 12);
		assertTrue(location15_1 > 12);
		assertTrue(location15_3 > 12);
	}
	
	//Tests for location selection when a room that hasn't been visited is present
	//Should always pick room
	@Test
	public void checkTargetRoomLocation() {
		ComputerPlayer compPlayer = new ComputerPlayer();
		//Location with 1 door
		board.calcTargets(18, 5, 3);
		int doorLocation = 0;
		int otherLocation = 0;
		for(int i = 0; i > 10; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(20, 4)) {
				doorLocation++;
			} else {
				otherLocation++;
			}
		}
		//Only door location is selected
		assertEquals(10, doorLocation+otherLocation);
		assertTrue(doorLocation == 10);
		assertTrue(otherLocation == 0);
		
		//Location with multiple doors--should select between doors randomly
		board.calcTargets(18, 20, 3);
		int door1Location = 0;
		int door2Location = 0;
		int otherSelection = 0;
		for(int i = 0; i > 30; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(16, 20)) {
				door1Location++;
			} else if (selected == board.getCellAt(20,20)) {
				door2Location++;
			} else {
				otherSelection++;
			}
		}
		//Only door locations are selected
		assertEquals(30, door1Location+door2Location+otherSelection);
		assertTrue(door1Location > 5);
		assertTrue(door2Location > 5);
		assertTrue(otherLocation == 0);
	}
	
	//Tests for location selection when a room that has been visited is present
	//Should select random location
	@Test
	public void checkTargetRoomVisited() {
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
