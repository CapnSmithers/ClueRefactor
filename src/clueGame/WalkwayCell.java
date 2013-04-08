package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class WalkwayCell extends BoardCell {
	
	
	public WalkwayCell(int row, int col) {
		super(row, col);
	}
	
	@Override
	public boolean isWalkway() {
		return true;
	}
	
	@Override
	public void draw(Graphics g, Board b) {
		if(b.getTargets().contains(this) && b.getClueGame().checkHumanPlayerMove()) {
			g.setColor(Color.cyan);
		} else {
			g.setColor(Color.yellow);
		}
		g.fillRect(colToPixels(), rowToPixels(), DIM, DIM);
		g.setColor(Color.black);
		g.drawRect(colToPixels(), rowToPixels(), DIM, DIM);
	}

	@Override
	public boolean containsClick(int x, int y) {
		Rectangle rect = new Rectangle(this.colToPixels(), this.rowToPixels(), DIM, DIM);
		if (rect.contains(new Point(x, y))) 
			return true;
		else;
			return false;
	}
}
