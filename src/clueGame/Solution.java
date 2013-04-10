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
	
	public boolean matches(Solution s) {
		return (s.person.equalsIgnoreCase(this.person) &&
				s.weapon.equalsIgnoreCase(this.weapon) &&
				s.room.equalsIgnoreCase(this.room));
	}
	
	public String toString() {
		return person + "; " + weapon + "; " + room;
	}
	
	//Getters for cards
	public Card getPerson() {
		return new Card(person, Card.CardType.PERSON);
	}
	
	public Card getRoom() {
		return new Card(room, Card.CardType.ROOM);
	}
	
	public Card getWeapon() {
		return new Card(weapon, Card.CardType.WEAPON);
	}
}
