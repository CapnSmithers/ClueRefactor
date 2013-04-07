package clueGame;

public class HumanPlayer extends Player {
	private boolean hasMoved = true;

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
		int rows = currentLocation/clueGame.board.getNumColumns();
		int cols = currentLocation%clueGame.board.getNumColumns();
		steps = rollDie();
		hasMoved = false;
		clueGame.board.calcTargets(rows, cols, steps);
		clueGame.board.repaint();
		//Mouse Listener that selects board cell
		hasMoved = true;
		
	}

	

}
