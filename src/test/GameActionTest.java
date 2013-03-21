package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.Solution;

public class GameActionTest {
	
	static ClueGame game = null;
	static Board board = null;
	static Solution testSolution = null;
	static Solution wrongSolution = null;
	static Card personSuggestion = null;
	static Card roomSuggestion = null;
	static Card weaponSuggestion = null;
	
	@BeforeClass
	public static void setup() {
		board = new Board();
		board.loadConfigFiles();
		game = new ClueGame();
		game.deal();
		testSolution = game.setSolution();  //testSolution is set to Col Mustard, Revolver, Ballroom
		wrongSolution = game.setWrongSolution();
		personSuggestion = new Card("Miss Scarlet", Card.CardType.PERSON);
		roomSuggestion = new Card("Bathroom", Card.CardType.ROOM);
		weaponSuggestion = new Card("Revolver", Card.CardType.WEAPON);
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
		ComputerPlayer compPlayer = new ComputerPlayer();
		compPlayer.setLastRoomVisited('A');
		//Location with previously visited door
		board.calcTargets(20,11,1);
		int previousRoom = 0;
		int location21_11 = 0;
		int location20_10 = 0;
		int location20_12 = 0;
		for(int i = 0; i > 50; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if(selected == board.getCellAt(19,11)) {
				previousRoom++;
			} else if (selected == board.getCellAt(21,11)) {
				location21_11++;
			} else if (selected == board.getCellAt(20, 10)) {
				location20_10++;
			} else {
				location20_12++;
			}
		}
		//Player should randomly select between all avaliable locations
		assertEquals(50, location21_11+location20_10+location20_12+previousRoom);
		assertTrue(previousRoom > 5);
		assertTrue(location20_10 > 5);
		assertTrue(location20_12 > 5);
		assertTrue(location21_11 > 5);
	}
	
	@Test
	public void disproveSuggestionOnePerson() {
		//Test one player, one correct match
		ComputerPlayer p = new ComputerPlayer();
		ArrayList<Card> sixCards = new ArrayList<Card>();
		sixCards.add(new Card("Knife", Card.CardType.WEAPON));
		sixCards.add(new Card("Lead Pipe", Card.CardType.WEAPON));
		sixCards.add(new Card("Ballroom", Card.CardType.ROOM));
		sixCards.add(new Card("Billiards Room", Card.CardType.ROOM));
		sixCards.add(new Card("Colonel Mustard", Card.CardType.PERSON));
		sixCards.add(new Card("Mrs. Peacock", Card.CardType.PERSON));
		p.setMyCards(sixCards);
		
		//Test if no cards are in hand
		assertNull(p.disproveSuggestion(personSuggestion, roomSuggestion, weaponSuggestion));
		
		//Test correct person
		assertEquals(new Card("Mrs. Peacock", Card.CardType.PERSON),
				p.disproveSuggestion(new Card("Mrs. Peacock", Card.CardType.PERSON), roomSuggestion, weaponSuggestion));
		
		//Test correct room
		assertEquals(new Card("Ballroom", Card.CardType.ROOM),
				p.disproveSuggestion(personSuggestion, new Card("Ballroom", Card.CardType.ROOM), weaponSuggestion));
		
		//Test correct weapon
		assertEquals(new Card("Lead Pipe", Card.CardType.WEAPON),
				p.disproveSuggestion(personSuggestion, roomSuggestion, new Card("Lead Pipe", Card.CardType.WEAPON)));
		
	}
	
	@Test
	public void disproveSuggestionMultpleMatches() {
		//Test one player, multiple correct matches
		ComputerPlayer p = new ComputerPlayer();
		ArrayList<Card> threeCards = new ArrayList<Card>();
		threeCards.add(weaponSuggestion);
		threeCards.add(roomSuggestion);
		threeCards.add(personSuggestion);
		p.setMyCards(threeCards);
		
		int room = 0;
		int weapon = 0;
		int person = 0;
		int other = 0;
		for (int i = 0; i < 30; i++) {
			Card suggestion = p.disproveSuggestion(personSuggestion, roomSuggestion, weaponSuggestion);
			if(suggestion == weaponSuggestion) {
				weapon++;
			} else if (suggestion == roomSuggestion) {
				room++;
			} else if (suggestion == personSuggestion) {
				person++;
			} else {
				other++;
			}
		}
		assertEquals(30, weapon+person+room+other);
		assertTrue(weapon > 1);
		assertTrue(room > 1);
		assertTrue(person > 1);
		assertTrue(other == 0);
		
	}
	
	@Test
	public void makeSuggestionTest() {
		fail("Not yet implemented");
	}

}
