package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class TestBoard {
	private static Board board;
	static final int NUM_ROWS = 24;
	static final int NUM_COLS = 24;
	
	
	@BeforeClass
	public static void initialize() {
		board = new Board();
		board.loadConfigFiles();
	}
	
	@Test
	public void testRooms() {
		Map actual = board.getRooms();
		assertEquals(11, actual.size());
	}
	
	@Test
	public void testLegend() {
		Map actual = board.getRooms();
		Assert.assertEquals("Gallery", actual.get('G'));
		Assert.assertEquals("Billiards Room", actual.get('B'));
		Assert.assertEquals("Library", actual.get('L'));
		Assert.assertEquals("Bedroom", actual.get('E'));
		Assert.assertEquals("Wine Cellar", actual.get('W'));
		Assert.assertEquals("Ballroom", actual.get('A'));
		Assert.assertEquals("Bathroom", actual.get('T'));
		Assert.assertEquals("Conservatory", actual.get('C'));
		Assert.assertEquals("Kitchen", actual.get('K'));
		Assert.assertEquals("Pantry", actual.get('P'));
		Assert.assertEquals("Hallway", actual.get('H'));
	}
	
	@Test
	public void testRows() {
		int actual = board.getNumRows();
		assertEquals(NUM_ROWS, actual);
	}
	
	@Test
	public void testCols() {
		int actual = board.getNumColumns();
		assertEquals(NUM_COLS, actual);
	}
	
	@Test
	public void testDoors() {
		RoomCell actual = board.getRoomCellAt(1, 5);
		assertEquals(true, actual.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN,actual.getDoorDirection());
		actual = board.getRoomCellAt(4, 9);
		assertEquals(true, actual.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, actual.getDoorDirection());
		actual = board.getRoomCellAt(13, 7);
		assertEquals(true, actual.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, actual.getDoorDirection());
		actual = board.getRoomCellAt(18, 12);
		assertEquals(true, actual.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, actual.getDoorDirection());
		actual = board.getRoomCellAt(11,4);
		assertEquals(true, actual.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, actual.getDoorDirection());
		actual = board.getRoomCellAt(1, 10);
		assertEquals(false, actual.isDoorway());
		assertEquals(RoomCell.DoorDirection.NONE, actual.getDoorDirection());
		BoardCell actual2 = board.getCellAt(6, 0);
		assertFalse(actual2.isDoorway());
	}
	
	@Test
	public void testNumOfDoors() {
		int numDoors = 0;
		int ttlCells = board.getNumColumns() * board.getNumRows();
		assertEquals(NUM_COLS * NUM_ROWS, ttlCells);
		for (int i = 0; i < ttlCells; i++) {
			BoardCell cell = board.getRoomCellAt(i);
			if (cell == null) continue;
			if (cell.isDoorway()) numDoors++;
		}
		assertEquals(19, numDoors);
	}
	
	@Test
	public void testInitials() {
		RoomCell actual = board.getRoomCellAt(1, 6);
		assertEquals('G', actual.getInitial());
		actual = board.getRoomCellAt(1, 10);
		assertEquals('E', actual.getInitial());
		actual = board.getRoomCellAt(6, 7);
		assertEquals(null, actual);
		actual = board.getRoomCellAt(9, 11);
		assertEquals('W', actual.getInitial());
		actual = board.getRoomCellAt(23, 23);
		assertEquals('P', actual.getInitial());
	}
	
	@Test
	public void testCalcIndex0_0() {
		int expected = 0;
		int actual = board.calcIndex(0,0);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCalcIndex1_3() {
		int expected = 1*NUM_COLS + 3;
		int actual = board.calcIndex(1,3);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCalcIndex3_2() {
		int expected = 3*NUM_COLS + 2;
		int actual = board.calcIndex(3, 2);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCalcIndex10_12() {
		int expected = 10*NUM_COLS + 12;
		int actual = board.calcIndex(10, 12);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCalcIndexFarCorner() {
		int expected = (NUM_ROWS - 1)*NUM_COLS + NUM_COLS - 1;
		int actual = board.calcIndex(NUM_ROWS - 1, NUM_COLS - 1);
		assertEquals(expected, actual);
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadCols() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("BadCols.csv", "legend.txt");
		b.loadBoardConfig();
		b.loadRoomConfig();
	}

	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("BadRoom.csv", "legend.txt");
		b.loadBoardConfig();
		b.loadRoomConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadFormat() throws BadConfigFormatException, FileNotFoundException {
		Board b = new Board("ClueMap.csv", "badLegend.txt");
		b.loadBoardConfig();
		b.loadRoomConfig();
	}
}
