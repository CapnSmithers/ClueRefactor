package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClueGame {
	private Set<Card> cards;
	public ArrayList<Player> players;    //Public to test
	public HumanPlayer humanPlayer;
	private int curPlayerTurn;
	private Solution solution;
	private Board board;
	
	public ClueGame() {
		super();
		cards = new HashSet<Card>();
		players = new ArrayList<Player>();
		players.add(new HumanPlayer("","",0));
		humanPlayer = (HumanPlayer) players.get(0);
		for (int i = 1; i < 6; i++) 
			players.add(new ComputerPlayer("","",0));
		curPlayerTurn = 0;
		solution = new Solution("", "", "");
		board = new Board();
	}

	public void deal() {
	
	}

	public void loadConfigFiles() {
		
	}
	
	public void selectAnswer() {
		
	}
	
	public Card handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		return null;
	}
	
	public boolean checkAccusation(Solution solution) {
		return false;
	}
	
	/*
	 * Getters and setters for testing
	 */
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	//TESTING ONLY
	//Functions to select specific cards -- picks Ballroom, Aliens, and Mrs. Peacock
	public Card pickRoomCard() {
		for(Card c: cards) {
			if (c.getCardName().equalsIgnoreCase("Ballroom")) {
				return c;
			}
		}
		return null;
	}
	
	public Card pickWeaponCard() {
		for(Card c: cards) {
			if (c.getCardName().equalsIgnoreCase("Revolver")) {
				return c;
			}
		}
		return null;
	}
	
	public Card pickPersonCard() {
		for(Card c: cards) {
			if (c.getCardName().equalsIgnoreCase("Mrs. Peacock")) {
				return c;
			}
		}
		return null;
	}

	public Solution setSolution() {
		Solution solution = new Solution("Colonel Mustard", "Revolver", "Ballroom");
		return solution;				
	}
	
	public Solution setWrongSolution() {
		Solution solution = new Solution("Mrs. Peacock", "Lead Pipe", "Pantry");
		return solution;
	}
}
