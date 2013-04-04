package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;

	public ComputerPlayer() {
		super();
	}

	public ComputerPlayer(String playerName, String color,
			Integer startingLocation) {
		super(playerName, color, startingLocation);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		//pick random location
		
		//check for doorways. if there are doors, computer should always go to them
		ArrayList<BoardCell> possibilities = new ArrayList<BoardCell>();
		for (BoardCell c : targets) {
			if (c.isDoorway()) {
				RoomCell d = (RoomCell) c;
				if (this.lastRoomVisited != d.getInitial()) {
					possibilities.add(c);
				}
			}
		}
		
		if (possibilities.size() <= 0) {
			//no doors, use walkways instead
			for (BoardCell c : targets) {
	            possibilities.add(c);
	        }
		}
        
		//choose random possibility
        Collections.shuffle(possibilities);
        if (possibilities.size() <= 0)
        	return null;
        return possibilities.get(0);
	}
	
	public void createSuggestion() {

	}
	
	public void updateSeen(Card seen) {
		
	}
	
	@Override
	public void makeMove() {
		steps = rollDie();
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
