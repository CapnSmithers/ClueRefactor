package clueGame;

import java.util.HashSet;
import java.util.Set;

public class ClueGame {
	private Set<Card> cards;
	private Player[] players;
	private HumanPlayer humanPlayer;
	private int curPlayerTurn;
	private Solution solution;
	private Board board;
	
	public ClueGame() {
		super();
		cards = new HashSet<Card>();
		players = new Player[6];
		players[0] = new HumanPlayer("");
		humanPlayer = (HumanPlayer) players[0];
		for (int i = 1; i < 6; i++) players[i] = new ComputerPlayer("");
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
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		
	}
	
	public boolean checkAccusation(Solution solution) {
		return false;
	}
}
