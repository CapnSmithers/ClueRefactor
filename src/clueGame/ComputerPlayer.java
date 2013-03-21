package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;

	

	public ComputerPlayer(String playerName, String color,
			Integer startingLocation) {
		super(playerName, color, startingLocation);
	}

	public void pickLocation(Set<BoardCell> targets) {
		
	}
	
	public void createSuggestion() {
		
	}
	
	public void updateSeen(Card seen) {
		
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
