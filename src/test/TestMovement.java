package test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class TestMovement {
	private static Board board;
	
	@BeforeClass
	public static void setup() {
		board = new Board();
		board.loadConfigFiles();
	}
	
	@Test
	public void testAdjEdges() { //olive green
		LinkedList<Integer> testList = board.getAdjList(0);
		assertEquals(0, testList.size());
		testList = board.getAdjList(0, 14);
		assertEquals(0,testList.size());
		testList = board.getAdjList(6, 23);
		assertEquals(0, testList.size());
		testList = board.getAdjList(19, 0);
		assertEquals(0, testList.size());
		testList = board.getAdjList(23, 13);
		assertEquals(0, testList.size());
	}
	
	@Test
	public void testHallways() { //light blue
		LinkedList<Integer> testList = board.getAdjList(6, 7); //only H around
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(7, 7)));
		assertTrue(testList.contains(board.calcIndex(6, 8)));
		assertTrue(testList.contains(board.calcIndex(5, 7)));
		assertTrue(testList.contains(board.calcIndex(6, 6)));
		testList = board.getAdjList(5, 15); //adjacent to room, not door
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(4, 15)));
		assertTrue(testList.contains(board.calcIndex(6, 15)));
		assertTrue(testList.contains(board.calcIndex(5, 16)));
		testList = board.getAdjList(14, 16);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(13, 16)));
		assertTrue(testList.contains(board.calcIndex(15, 16)));
	}
	
	@Test
	public void testNextToDoor() { //orange
		LinkedList<Integer> testList = board.getAdjList(19, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(19, 4)));
		assertTrue(testList.contains(board.calcIndex(18, 5)));
		assertTrue(testList.contains(board.calcIndex(19, 6)));
		testList = board.getAdjList(3, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(2, 8)));
		assertTrue(testList.contains(board.calcIndex(4, 8)));
		assertTrue(testList.contains(board.calcIndex(3, 7)));
		assertTrue(testList.contains(board.calcIndex(3, 9)));
	}
	
	@Test
	public void testDoorway() { //pink
		LinkedList<Integer> testList = board.getAdjList(19, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.calcIndex(19, 5)));
		testList = board.getAdjList(21, 12);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.calcIndex(20, 12)));
	}
	
	@Test
	public void testTargetsHallway() { //grey
		board.calcTargets(10, 8, 2);
		Set targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 8))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 8))));
		board.calcTargets(8, 15, 3);
		targets = board.getTargets();
		Assert.assertEquals(10, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(5, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 16))));
		board.calcTargets(14, 1, 3);
		targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 2))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 4))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 3))));
		board.calcTargets(17, 18, 3);
		targets = board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(16, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 21))));
	}
	
	@Test
	public void testTargetsEnterRoom() { //purple
		board.calcTargets(17, 20, 3);
		Set targets = board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 17))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 23))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(15, 20))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 20))));
		board.calcTargets(11, 5, 1);
		targets = board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 5))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 6))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 4))));
	}
	
	@Test
	public void testTargetsLeaveRoom() { //bright blue
		board.calcTargets(8, 1, 3);
		Set targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 0))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 2))));
		board.calcTargets(18, 12, 1);
		targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(19, 12))));
	}
}
