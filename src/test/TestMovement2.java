//DO NOT use these tests for the basic movement tests
//advanced functionality tested
//See TestMovement for actual tests


package test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class TestMovement2 {
	private static Board board;
	
	@BeforeClass
	public static void setup() {
		board = new Board();
		board.loadConfigFiles();
	}
	
	@Test
	public void testAdjEdges() {
		LinkedList<Integer> testList = board.getAdjList(0);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(0, 1)));
		assertTrue(testList.contains(board.calcIndex(1, 0)));
		testList = board.getAdjList(14, 0);
		assertEquals(3,testList.size());
		assertTrue(testList.contains(board.calcIndex(14, 1)));
		assertTrue(testList.contains(board.calcIndex(13, 0)));
		assertTrue(testList.contains(board.calcIndex(15, 0)));
		testList = board.getAdjList(23, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(22, 5)));
		assertTrue(testList.contains(board.calcIndex(23, 4)));
		assertTrue(testList.contains(board.calcIndex(23, 6)));
		testList = board.getAdjList(0, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(18, 0)));
		assertTrue(testList.contains(board.calcIndex(20, 0)));
		assertTrue(testList.contains(board.calcIndex(19, 1)));
		testList = board.getAdjList(13, 23);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(12, 23)));
		assertTrue(testList.contains(board.calcIndex(14, 23)));
		assertTrue(testList.contains(board.calcIndex(13, 22)));
	}
	
	@Test
	public void testHallways() {
		LinkedList<Integer> testList = board.getAdjList(7, 6); //only H around
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(7, 7)));
		assertTrue(testList.contains(board.calcIndex(7, 5)));
		assertTrue(testList.contains(board.calcIndex(6, 6)));
		assertTrue(testList.contains(board.calcIndex(8, 6)));
		testList = board.getAdjList(15, 5);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(15, 4)));
		assertTrue(testList.contains(board.calcIndex(15, 6)));
		assertTrue(testList.contains(board.calcIndex(16, 5)));
		testList = board.getAdjList(16, 14);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.calcIndex(16, 13)));
		assertTrue(testList.contains(board.calcIndex(16, 15)));
	}
	
	@Test
	public void testNextToDoor() {
		LinkedList<Integer> testList = board.getAdjList(5, 19);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.calcIndex(5, 18)));
		assertTrue(testList.contains(board.calcIndex(4, 19)));
		assertTrue(testList.contains(board.calcIndex(6, 19)));
		testList = board.getAdjList(8, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(8, 2)));
		assertTrue(testList.contains(board.calcIndex(8, 4)));
		assertTrue(testList.contains(board.calcIndex(7, 3)));
		assertTrue(testList.contains(board.calcIndex(9, 3)));
	}
	
	@Test
	public void testDoorway() {
		LinkedList<Integer> testList = board.getAdjList(4, 19);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(4, 18)));
		assertTrue(testList.contains(board.calcIndex(4, 20)));
		assertTrue(testList.contains(board.calcIndex(3, 19)));
		assertTrue(testList.contains(board.calcIndex(5, 19)));
		testList = board.getAdjList(12, 21);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.calcIndex(12, 20)));
		assertTrue(testList.contains(board.calcIndex(12, 22)));
		assertTrue(testList.contains(board.calcIndex(11, 21)));
		assertTrue(testList.contains(board.calcIndex(13, 21)));
	}
	
	@Test
	public void testTargetsHallway() {
		board.calcTargets(8, 10, 2);
		Set targets = board.getTargets();
		board.calcTargets(15, 8, 3);
		targets = board.getTargets();
		board.calcTargets(1, 14, 3);
		targets = board.getTargets();
		board.calcTargets(18, 17, 3);
		targets = board.getTargets();
	}
	
	@Test
	public void testTargetsEnterRoom() {
		board.calcTargets(17, 20, 3);
		Set targets = board.getTargets();
		board.calcTargets(5, 11, 1);
		targets = board.getTargets();
	}
	
	@Test
	public void testTargetsLeaveRoom() {
		board.calcTargets(1, 8, 2);
		Set targets = board.getTargets();
		board.calcTargets(4, 20, 2);
		targets = board.getTargets();
	}
}
