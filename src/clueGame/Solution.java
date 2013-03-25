package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	
	public Solution(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public Solution(Card personCard, Card weaponCard, Card roomCard) {
		super();
		this.person = personCard.getCardName();
		this.weapon = weaponCard.getCardName();
		this.room = roomCard.getCardName();
	}
	
	public boolean contains(Card c) {
		if (c.getCardType() == Card.CardType.PERSON && c.getCardName() == person)
			return true;
		if (c.getCardType() == Card.CardType.WEAPON && c.getCardName() == weapon)
			return true;
		if (c.getCardType() == Card.CardType.ROOM && c.getCardName() == room)
			return true;
		
		return false;
	}
}
