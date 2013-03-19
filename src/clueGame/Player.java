package clueGame;

import java.util.ArrayList;

public class Player {
	private String playerName;
	protected ArrayList<Card> myCards;
	
	public Player(String playerName) {
		super();
		this.playerName = playerName;
		this.myCards = new ArrayList<Card>();
	}
	
	public Card disproveSuggestion() {
		return new Card("", Card.CardType.PERSON);
	}
}
