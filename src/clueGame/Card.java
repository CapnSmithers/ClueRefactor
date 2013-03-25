package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class Card {
	public enum CardType {
		PERSON, WEAPON, ROOM
	}
	
	private String cardName;
	private CardType cardType;
	private boolean hasBeenRevealed;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

    public boolean isHasBeenRevealed() {
		return hasBeenRevealed;
	}

	public void setHasBeenRevealed(boolean hasBeenRevealed) {
		this.hasBeenRevealed = hasBeenRevealed;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        
        Card c = (Card) obj;
        if (this.cardName == c.cardName && this.cardType == c.cardType) {
        	return true;
        }
        
        return false;
    }
	
	public static Card getRandomCard(Set<Card> myCards, CardType ct) {
		ArrayList<Card> possibilities = new ArrayList<Card>();
		Iterator<Card> it = myCards.iterator();
        while (it.hasNext()) {
        	Card c = it.next();
            if(c != null && c.cardType == ct){
            	possibilities.add(c);
            }
        }
        
        Collections.shuffle(possibilities);
        return possibilities.get(0);
	}
}
