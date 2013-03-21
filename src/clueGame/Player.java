package clueGame;

import java.util.ArrayList;

public class Player {
	private String playerName;
	private String color;
	private Integer startingLocation;
	
	protected ArrayList<Card> myCards;
	
	//For testing only
	public Player() {
		this.playerName = "Professor Plum";
		this.color = "purple";
		this.startingLocation = (16*23);
	}
	
	public Player(String playerName, String color, Integer startingLocation) {
		super();
		this.playerName = playerName;
		this.color = color;
		this.startingLocation = startingLocation;
		this.myCards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion() {
		return new Card("", Card.CardType.PERSON);
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
	
	
	
	
}
