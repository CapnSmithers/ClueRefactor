package clueGame;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

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
	
	//Draws room cells, including door direction if necessary
	@Override
	public void draw(Graphics g, Board b) {
		if(b.getTargets().contains(this) && b.getClueGame().checkHumanPlayerMove()) {
			g.setColor(Color.cyan);
		} else {
			g.setColor(Color.gray);
		}
		g.fillRect(colToPixels(), rowToPixels(), DIM, DIM);
		g.setColor(Color.green);
		switch(doorDirection){
			case UP: g.fillRect(colToPixels(), rowToPixels(), DIM, DIM/5);
				break;
			case LEFT: g.fillRect(colToPixels(), rowToPixels(), DIM/5, DIM);
				break;
			case RIGHT: g.fillRect(colToPixels() + DIM*4/5, rowToPixels(), DIM/5, DIM);	
				break;
			case DOWN: g.fillRect(colToPixels(), rowToPixels() + DIM*4/5, DIM, DIM/5);
				break;
			case NONE:
		}
	}
	
	//Can only move into room cell if it's a doorway
	@Override
	public boolean containsClick(int x, int y) {
		if(this.isDoorway()) {
			Rectangle rect = new Rectangle(x, y, DIM, DIM);
			if (rect.contains(new Point(x, y))) 
				return true;
			return false;
		} else {
			return false;
		}
		

	}
	
}
