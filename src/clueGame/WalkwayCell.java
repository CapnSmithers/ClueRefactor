package clueGame;

import java.awt.Color;
import java.awt.Graphics;

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
		g.setColor(Color.yellow);
		g.fillRect(colToPixels(), rowToPixels(), DIM, DIM);
		g.setColor(Color.black);
		g.drawRect(colToPixels(), rowToPixels(), DIM, DIM);
	}
}
