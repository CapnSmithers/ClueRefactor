package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	private char nextToLastVisited;
	private boolean accusationReady;
	private Solution accusation;

	public ComputerPlayer() {
		super();
		accusationReady = false;
		accusation = null;
	}

	public ComputerPlayer(ClueGame clueGame, String playerName, String color, Integer startingLocation) {
		super(clueGame, playerName, color, startingLocation);
		accusationReady = false;
		accusation = null;
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		//pick random location
		
		//check for doorways. if there are doors, computer should always go to them
		ArrayList<BoardCell> possibilities = new ArrayList<BoardCell>();
		for (BoardCell c : targets) {
			if (c.isDoorway()) {
				RoomCell d = (RoomCell) c;
				if (lastRoomVisited != d.getInitial() && nextToLastVisited != d.getInitial()) {  //Make sure room wasn't just visited
					nextToLastVisited = lastRoomVisited;
					lastRoomVisited = d.getInitial();
					return c;
				}
			}
		}
		
		//no doors, use walkways instead
		for (BoardCell c : targets) {
	        possibilities.add(c);
	    }
        
		//choose random possibility
        Collections.shuffle(possibilities);
        if (possibilities.size() <= 0)
        	return null;
        return possibilities.get(0);
	}
	
	public Solution createSuggestion() {
		RoomCell rc = clueGame.getBoard().getRoomCellAt(currentLocation);
		String roomName = clueGame.getBoard().getRoomName(rc.getInitial());
		
		ArrayList<Card> person_possibilities = new ArrayList<Card>();
		ArrayList<Card> weapon_possibilities = new ArrayList<Card>();
		for (Card c : clueGame.getCards()) {
			if (!c.isHasBeenRevealed()) {
				if (c.getCardType() == Card.CardType.PERSON) {
					person_possibilities.add(c);
				}
				if (c.getCardType() == Card.CardType.WEAPON) {
					weapon_possibilities.add(c);
				}
			}
		}
		
		if (person_possibilities.size() <= 0 || weapon_possibilities.size() <= 0)
        	return null;
		
		Collections.shuffle(person_possibilities);
		Collections.shuffle(weapon_possibilities);
		
		Card personCard = person_possibilities.get(0);
		Card weaponCard = weapon_possibilities.get(0);
		Card roomCard = new Card(roomName, Card.CardType.ROOM);
		
		String accusedPerson = personCard.getCardName();
		for (Player p : clueGame.players) {
			if(p.getPlayerName().equalsIgnoreCase(accusedPerson)) {
				p.moveTo(this.currentLocation);
			}
		}
		
		return new Solution(personCard, weaponCard, roomCard);
	}
	
	public void updateSeen(Card seen) {
		
	}
	
	@Override
	public void makeMove() {
		Solution suggestion = null;
		Card result = new Card("No new clues", Card.CardType.PERSON);
		int rows = currentLocation/clueGame.board.getNumColumns();
		int cols = currentLocation%clueGame.board.getNumColumns();
		steps = rollDie();
		if(accusationReady) {
			makeAccusation();
		} else {
			clueGame.board.calcTargets(rows, cols, steps);
			BoardCell target = pickLocation(clueGame.board.getTargets());
			currentLocation = clueGame.board.calcIndex(target.getRow(), target.getCol());
			if(clueGame.board.getCellAt(currentLocation).isDoorway()) {
				suggestion = createSuggestion();
				clueGame.setPlayerGuess(suggestion);
			}
			if(suggestion != null) {
				accusation = suggestion;
				result = clueGame.handleSuggestion(suggestion.person, suggestion.weapon, suggestion.room, this);
				if(result.getCardName().equalsIgnoreCase("No new clues")) {
					accusationReady = true;
				}
			}
			clueGame.board.repaint();
		}
	}
	
	private void makeAccusation() {
		//If any of the cards in accusation are in their hand, exit the fxn.
		if(myCards.contains(accusation.getPerson()) || myCards.contains(accusation.getRoom())
				|| myCards.contains(accusation.getWeapon())) 
		{
			accusationReady = false;
			return;
		}
		boolean correct = clueGame.checkAccusation(accusation);
		if(correct) {
			String message = this.getPlayerName() + " has won the game with a correct guess of: "
					+ accusation.person + " in the " + accusation.room + " with the " + accusation.weapon
					+ "!";
			JOptionPane.showMessageDialog(clueGame, message,
					"Game Over!", JOptionPane.INFORMATION_MESSAGE);
		} else {
			String message = this.getPlayerName() + " has made an incorrect accusation of: "
					+ accusation.person + " in the " + accusation.room + " with the " + accusation.weapon
					+ "! Buh buh buuuuhhhhhh!";
			JOptionPane.showMessageDialog(clueGame, message,
					"Game Not Over!", JOptionPane.WARNING_MESSAGE);
			accusationReady = false;
		}
	}
	
	/*
	 * Getters and Setters for Testing only
	 */

	public char getLastRoomVisited() {
		return lastRoomVisited;
	}

	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

	public boolean isAccusationReady() {
		return accusationReady;
	}

	public void setAccusationReady(boolean accusationReady) {
		this.accusationReady = accusationReady;
	}
}
