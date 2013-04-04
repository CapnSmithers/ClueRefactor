package clueGame;

public class HumanPlayer extends Player {
	private boolean hasMoved = false;

	public HumanPlayer(ClueGame clueGame, String playerName, String color, Integer startingLocation) {
		super(clueGame, playerName, color, startingLocation);
	}

	public boolean getHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	@Override
	public void makeMove() {
		steps = rollDie();
		hasMoved = false;
	}

	

}
