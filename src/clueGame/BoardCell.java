package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class BoardCell {
	private int row;
	private int col;
	public static final int DIM = 30;  //Pixel dimension of the cell
	
	public BoardCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	//Helper functions to calculate position of object in pixels
	public int rowToPixels() {
		return row*DIM;
	}
	
	public int colToPixels() {
		return col*DIM;
	}
	
	abstract public void draw(Graphics g, Board b);
}
