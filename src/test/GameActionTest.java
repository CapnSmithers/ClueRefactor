package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
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
		game = new ClueGame();
		board = game.board;
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
		board.calcTargets(15, 2, 2);
		int location15_0 = 0;
		int location14_1 = 0;
		int location14_3 = 0;
		int location15_4 = 0;
		int other = 0;
		for(int i = 0; i < 100; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if(selected == board.getCellAt(15, 0)) {
				location15_0++;
			} else if (selected == board.getCellAt(14, 1)) {
				location14_1++;
			} else if (selected == board.getCellAt(14, 3)) {
				location14_3++;
			} else if (selected == board.getCellAt(15, 4)) {
				location15_4++;
			}
			else {
				other++;
			}
		}
		assertEquals(100, location15_0+location14_1+location14_3+location15_4+other);
		assertTrue(location15_0 > 12);
		assertTrue(location14_1 > 12);
		assertTrue(location14_3 > 12);
		assertTrue(location15_4 > 12);
		assertTrue(other == 0);		
	}
	
	//Tests for location selection when a room that hasn't been visited is present
	//Should always pick room
	@Test
	public void checkTargetRoomLocation() {
		ComputerPlayer compPlayer = new ComputerPlayer();
		//Location with 1 door
		board.calcTargets(17, 5, 3);
		int doorLocation = 0;
		int otherLocation = 0;
		for(int i = 0; i < 10; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(19, 4)) {
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
		for(int i = 0; i < 30; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(15, 20)) {
				door1Location++;
			} else if (selected == board.getCellAt(19, 20)) {
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
		board.calcTargets(19, 11, 1);
		int previousRoom = 0;
		int location20_11 = 0;
		int location19_10 = 0;
		int location19_12 = 0;
		int other = 0;
		for(int i = 0; i < 50; i++) {
			BoardCell selected = compPlayer.pickLocation(board.getTargets());
			if (selected == board.getCellAt(18, 11)) {
				previousRoom++;
			} else if (selected == board.getCellAt(20, 11)) {
				location20_11++;
			} else if (selected == board.getCellAt(19, 10)) {
				location19_10++;
			} else if (selected == board.getCellAt(19, 12)) {
				location19_12++;
			}
			else {
				other++;
			}
		}
		//Player should randomly select between all avaliable locations
		assertEquals(50, location20_11+location19_10+location19_12+previousRoom+other);
		assertTrue(previousRoom > 5);
		assertTrue(location20_11 > 5);
		assertTrue(location19_10 > 5);
		assertTrue(location19_12 > 5);
		assertTrue(other == 0);
	}
	
	//Tests disproveSuggestion for one player when they have only one card to show
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
	
	//Tests for one player when they have multiple correct matches with the suggestion
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
	
	//Tests to ensure that disproveSuggestion works for multiple players
	@Test
	public void disproveSuggestionMultiplePlayers() {
		//Populate players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Colonel Mustard", "Yellow", 0));
		for(int i = 0; i < 3; i++) {
			players.add(new ComputerPlayer());   //Adds 3 computer players on top of the one human player
		}
		
		//Assign players specific cards
		ArrayList<Card> p1Cards = new ArrayList<Card>();
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		ArrayList<Card> p3Cards = new ArrayList<Card>();
		ArrayList<Card> humanCards = new ArrayList<Card>();
		
		p1Cards.add(new Card("Knife", Card.CardType.WEAPON));
		p1Cards.add(new Card("Miss Scarlet", Card.CardType.PERSON));
		p2Cards.add(new Card("Revolver", Card.CardType.WEAPON));
		p2Cards.add(new Card("Ballroom", Card.CardType.ROOM));
		p3Cards.add(new Card("Lead Pipe", Card.CardType.WEAPON));
		p3Cards.add(new Card("Gallery", Card.CardType.ROOM));
		humanCards.add(new Card("Mr. Green", Card.CardType.PERSON));
		humanCards.add(new Card("Conservatory", Card.CardType.ROOM));
		
		players.get(0).setMyCards(humanCards);
		players.get(1).setMyCards(p1Cards);
		players.get(2).setMyCards(p2Cards);
		players.get(3).setMyCards(p3Cards);
		
		//set game players to list defined above
		game.players = players;
		
		//no one can disprove - null should be returned
		Assert.assertNull(game.handleSuggestion("Mrs. White", "Bathroom", "Rope", players.get(2)));
		//only the human player can disprove - Mr. Green should be returned
		Assert.assertEquals(new Card("Mr. Green", Card.CardType.PERSON), game.handleSuggestion("Mr. Green", "Bathroom", "Rope", players.get(2)));
		//only the human player can disprove, but the human player is making the accusation - null should be returned
		Assert.assertNull(game.handleSuggestion("Mr. Green", "Bathroom", "Rope", players.get(0)));
		//only computer player 2 can disprove, but that player is making the accusation - null should be returned
		Assert.assertNull(game.handleSuggestion("Mrs. White", "Ballroom", "Rope", players.get(2)));
	
		int room = 0;
		int weapon = 0;
		int person = 0;
		int other = 0;
		//situation where two players can disprove. Make sure same player isn't queried each time 
		//and same card isn't returned each time
		for (int i = 0; i < 30; i++) {
			Card suggestion = game.handleSuggestion("Mr. Green", "Gallery", "Rope", players.get(1));
			if (suggestion == new Card("Mr. Green", Card.CardType.PERSON)) {
				person++;
			} else if (suggestion == new Card("Gallery", Card.CardType.ROOM)) {
				room++;
			} else if (suggestion == new Card("Rope", Card.CardType.WEAPON)) {
				weapon++;
			} else {
				other++;
			}
		}
		assertEquals(30, weapon+person+room+other);
		assertTrue(person > 1);
		assertTrue(room > 1);
		assertTrue(weapon == 0);
		assertTrue(other == 0);
	}
	
	//Tests to make sure that a computer player does not make a suggestion using any cards that has already
	//been revealed. Only one possible suggestion possibility.
	@Test
	public void makeSuggestionOnePossibilityTest() {
		//Make a mock deck
		Set<Card> deck = new HashSet<Card>();
		deck.add(new Card("Knife", Card.CardType.WEAPON));
		deck.add(personSuggestion);
		deck.add(weaponSuggestion);
		deck.add(new Card("Ballroom", Card.CardType.ROOM));
		deck.add(new Card("Lead Pipe", Card.CardType.WEAPON));
		deck.add(new Card("Gallery", Card.CardType.ROOM));
		deck.add(new Card("Mr. Green", Card.CardType.PERSON));
		deck.add(new Card("Conservatory", Card.CardType.ROOM));
		
		//set all cards except for single weapon and single person to revealed
		for (Card c: deck) {
			if (!c.equals(personSuggestion) && !c.equals(weaponSuggestion))
				c.setHasBeenRevealed(true);
		}
		
		//Populate players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Colonel Mustard", "Yellow", 0));
		for(int i = 0; i < 3; i++) {
			players.add(new ComputerPlayer());   //Adds 3 computer players on top of the one human player
		}
		
		//establish test - set players, board, player 1 location, and make it player 1's turn
		int billiardsCell = board.calcIndex(8, 1);	
		game.board = board;
		game.players = players;
		game.players.get(1).setCurrentLocation(billiardsCell);
		game.curPlayerTurn = 1;
		game.setCards(deck);
		
		//creates suggestion for current player (player 1)
		Solution suggestion = game.createSuggestion();
		
		//result should be current room (ballroom), personSuggestion, and weaponSuggestion
		Assert.assertEquals(personSuggestion, new Card(suggestion.person, Card.CardType.PERSON));
		Assert.assertEquals(weaponSuggestion, new Card(suggestion.weapon, Card.CardType.WEAPON));
		Assert.assertEquals("Billiards Room", suggestion.room);
	}

	//Tests to make sure that a computer player does not make a suggestion using any cards that has already
	//been revealed. Multiple person possibilities will be available
	@Test
	public void makeSuggestionMultiplePossibilityTest() {
		Card personSuggestion2 = new Card("Mr. Green", Card.CardType.PERSON);
		
		//Make a mock deck
		Set<Card> deck = new HashSet<Card>();
		deck.add(new Card("Knife", Card.CardType.WEAPON));
		deck.add(personSuggestion);
		deck.add(weaponSuggestion);
		deck.add(new Card("Ballroom", Card.CardType.ROOM));
		deck.add(new Card("Lead Pipe", Card.CardType.WEAPON));
		deck.add(new Card("Gallery", Card.CardType.ROOM));
		deck.add(personSuggestion2);
		deck.add(new Card("Conservatory", Card.CardType.ROOM));
		
		//set all cards except for single weapon and two persons to revealed
		for (Card c: deck) {
			if (!c.equals(personSuggestion) && !c.equals(personSuggestion2) && !c.equals(weaponSuggestion))
				c.setHasBeenRevealed(true);
		}
		
		//Populate players
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new HumanPlayer("Colonel Mustard", "Yellow", 0));
		for(int i = 0; i < 3; i++) {
			players.add(new ComputerPlayer());   //Adds 3 computer players on top of the one human player
		}
		
		//establish test - set players, board, player 1 location, and make it player 1's turn
		int ballroomCell = board.calcIndex(8, 1);	
		game.board = board;
		game.players = players;
		game.players.get(1).setCurrentLocation(ballroomCell);
		game.curPlayerTurn = 1;
		
		//make sure each person is used at least once and single weapon is used every time
		int person1 = 0;
		int person2 = 0;
		int personother = 0;
		int weapon = 0;
		int weaponother = 0;
		for (int i = 0; i < 30; i++) {
			//creates suggestion for current player (player 1)
			Solution suggestion = game.createSuggestion();
			
			//check person
			if (suggestion.person == personSuggestion.getCardName()) {
				person1++;
			}
			else if (suggestion.person == personSuggestion2.getCardName()) {
				person2++;
			}
			else {
				personother++;
			}
			
			//check weapon
			if (suggestion.weapon == weaponSuggestion.getCardName()) {
				weapon++;
			}
			else {
				weaponother++;
			}
		}
		
		assertEquals(30, person1+person2+personother);
		assertTrue(person1 > 1);
		assertTrue(person2 > 1);
		assertTrue(personother == 0);
		
		assertEquals(30, weapon+weaponother);
		assertTrue(weapon > 1);
		assertTrue(weaponother == 0);
	}
}
