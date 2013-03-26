package clueGame;

import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char initial;
	
	public RoomCell(int row, int col, char initial, char doorDir) { //takes in door direction, uses for assignment
		super(row, col);
		this.initial = initial;
		if (doorDir == 'U')
			doorDirection = DoorDirection.UP;
		else if (doorDir == 'D')
			doorDirection = DoorDirection.DOWN;
		else if (doorDir == 'L')
			doorDirection = DoorDirection.LEFT;
		else if (doorDir == 'R')
			doorDirection = DoorDirection.RIGHT;
		else
			doorDirection = DoorDirection.NONE;
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE)
			return false;
		else
			return true;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	@Override
	public String toString() {
		return "RoomCell [doorDirection=" + doorDirection + ", initial="
				+ initial + ", isDoorway()=" + isDoorway()
				+ ", getDoorDirection()=" + getDoorDirection() + "]";
	}

	public char getInitial() {
		return initial;
	}
	
	@Override
	public void draw(Graphics g, Board b) {
		
	}
	
}
