package clueGame;

import java.util.ArrayList;

public class Player {
	private String playerName;
	private String color;
	private int startingLocation;
	private int currentLocation;
	
	protected ArrayList<Card> myCards;
	
	//For testing only
	public Player() {
		this.playerName = "Professor Plum";
		this.color = "purple";
		this.startingLocation = (16*23);
		this.currentLocation = this.startingLocation;
		this.myCards = new ArrayList<Card>();
	}
	
	public Player(String playerName, String color, Integer startingLocation) {
		super();
		this.playerName = playerName;
		this.color = color;
		this.startingLocation = startingLocation;
		this.currentLocation = this.startingLocation;
		this.myCards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion(Card person, Card room, Card weapon) {
		return null;
	}

	/*
	 * Getters and setters for testing purposes
	 */
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getStartingLocation() {
		return startingLocation;
	}

	public void setStartingLocation(Integer startingLocation) {
		this.startingLocation = startingLocation;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}
	
	public int getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(int currentLocation) {
		this.currentLocation = currentLocation;
	}
}
