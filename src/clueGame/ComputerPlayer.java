package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	private char nextToLastVisited;

	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(ClueGame clueGame, String playerName, String color, Integer startingLocation) {
		super(clueGame, playerName, color, startingLocation);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		//pick random location
		
		//check for doorways. if there are doors, computer should always go to them
		ArrayList<BoardCell> possibilities = new ArrayList<BoardCell>();
		for (BoardCell c : targets) {
			if (c.isDoorway()) {
				RoomCell d = (RoomCell) c;
				System.out.println(d.getInitial());
				if (lastRoomVisited != d.getInitial() && nextToLastVisited != d.getInitial()) {  //Make sure room wasn't just visited

					System.out.println(lastRoomVisited + " " + nextToLastVisited+ " " + getPlayerName());
					nextToLastVisited = lastRoomVisited;
					lastRoomVisited = d.getInitial();
					System.out.println(lastRoomVisited + " " + nextToLastVisited+ " " + getPlayerName());
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
		
		return new Solution(personCard, weaponCard, roomCard);
	}
	
	public void updateSeen(Card seen) {
		
	}
	
	@Override
	public void makeMove() {
		int rows = currentLocation/clueGame.board.getNumColumns();
		int cols = currentLocation%clueGame.board.getNumColumns();
		steps = rollDie();
		clueGame.board.calcTargets(rows, cols, steps);
		BoardCell target = pickLocation(clueGame.board.getTargets());
		currentLocation = clueGame.board.calcIndex(target.getRow(), target.getCol());
		clueGame.board.repaint();
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
}
