package test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.ClueGame;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTest {
	
	static ClueGame game;
	
	@BeforeClass
	public static void setup() {
		game = new ClueGame();
	}
	
	@Test
	public void loadPeopleTests() {
		//Name Tests
		HumanPlayer human = game.humanPlayer;
		Assert.assertEquals("Colonel Mustard", human.getPlayerName());
		Assert.assertEquals("Mrs. White", game.players.get(2).getPlayerName());
		Assert.assertEquals("Professor Plum", game.players.get(5).getPlayerName());
		
		//Color Tests
		Assert.assertEquals("yellow", human.getColorStr());
		Assert.assertEquals("white", game.players.get(2).getColorStr());
		Assert.assertEquals("purple", game.players.get(5).getColorStr());
		
		//Start location tests -- startingLocation is of type Integer, so we must cast calcIndex to Integer
		Assert.assertEquals((Integer)game.getBoard().calcIndex(23, 6), human.getStartingLocation());
		Assert.assertEquals((Integer)game.getBoard().calcIndex(17, 23), game.players.get(2).getStartingLocation());
		Assert.assertEquals((Integer)game.getBoard().calcIndex(16, 0), game.players.get(5).getStartingLocation());
	}
	
	@Test
	public void loadCardTests() {
		//Test for number of cards in deck - There are 21 cards in deck --> 9 rooms, 6 weapons, 6 people
		Assert.assertEquals(21, game.getCards().size());
		
		//Card type tests - iterate through card deck, count each instance of each card
		int weaponCards = 0;
		int personCards = 0;
		int roomCards = 0;
		for(Card c: game.getCards()) {
			if(c.getCardType() == Card.CardType.WEAPON) {
				weaponCards++;
			} else if(c.getCardType() == Card.CardType.PERSON) {
				personCards++;
			} else {
				roomCards++;
			}
		}
		Assert.assertEquals(6, weaponCards);
		Assert.assertEquals(9, roomCards);
		Assert.assertEquals(6, personCards);
		
		//Card name tests - Select 1 of each type of card, ensure name is correct
		Assert.assertEquals("Revolver", game.pickWeaponCard().getCardName());
		Assert.assertEquals("Ballroom", game.pickRoomCard().getCardName());
		Assert.assertEquals("Mrs. Peacock", game.pickPersonCard().getCardName());
	}
	
	@Test
	public void dealCardTests() {
		//Test all cards have been dealt - each player should have exactly 3 cards + 3 cards in solution
		//Also tests that all players have the same number of cards
		for(Player p: game.players) {
			int test = p.getMyCards().size();
			Assert.assertEquals(3, p.getMyCards().size());
		}
		Assert.assertNotSame("", game.getSolution().person);
		Assert.assertNotSame("", game.getSolution().weapon);
		Assert.assertNotSame("", game.getSolution().room);
		
		//Test no duplicate cards dealt
		//NOTE: there has to be a better way to do this, but I'm tired.
		boolean duplicate = false;
		for(Player p: game.players) {
			for(Player other: game.players) {
				if(p.getPlayerName().equalsIgnoreCase(other.getPlayerName())) {
					continue;
				}
				for(Card c: p.getMyCards()) {
					if(other.getMyCards().contains(c)) {
						duplicate = true;
						break;
					}
				}
			}
		}
		Assert.assertEquals(false, duplicate);
	}
}
