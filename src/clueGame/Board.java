package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.RoomCell.DoorDirection;

public class Board {
	
	private ArrayList<BoardCell> cells; //stores board
	private Map<Character, String> rooms; //legend codes
	private Map<Integer, LinkedList<Integer>> adjMtx; //adjacency list
	private Set<BoardCell> targets; //list of targets- gets cleared for every new calcTargets
	private boolean[] visited; //visited matrix
	private int numRows;
	private int numColumns;
	private String mapName; //csv file with board initials
	private String legendName; //txt with legend file info
	
	public Board() {
		mapName = "ClueMap.csv"; //default names- change to your own
		legendName = "legend.txt";
		cells = new ArrayList<BoardCell>(); //initializations
		rooms = new HashMap<Character, String>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
	}
	
	public Board(String mapName, String legendName) { //instantiator with filenames
		this.mapName = mapName;
		this.legendName = legendName;
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
	}
	
	public void loadConfigFiles() { //loads legend, then board, calcs adjacencies
		try {
			loadRoomConfig();
			loadBoardConfig();
			visited = new boolean[numRows * numColumns];
			calcAdjacencies();
		} catch (Exception e) {
			System.out.println("Woops"); //if exception is found
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		File f = new File(mapName);
		Scanner in = new Scanner(f);
		int numRows = 0;
		//preloader config: runs the first row to find out the number of columns
		int numCols = loadRoomLine(in, numRows);
		numRows++;
		while (in.hasNextLine()) {
			int currentCols = loadRoomLine(in, numRows);
			if (currentCols != numCols) //throws exceptions if number of cols is different
				throw new BadConfigFormatException();
			numRows++;
		}
		this.numRows = numRows; //stores # of rows and cols
		numColumns = numCols;
		in.close();
	}
	
	public int loadRoomLine(Scanner in, int curRow) throws BadConfigFormatException{ //loads one line of csv file
		Scanner s = new Scanner(in.nextLine());
		s.useDelimiter(",");
		int curCol = 0;
		while (s.hasNext()) {
			String buffer = s.next().trim();
			if (buffer.length() > 2)
				throw new BadConfigFormatException();
			char initial = buffer.charAt(0);
			char doorDir = 'N';
			if (buffer.length() == 2) //reads if there's a door at location
				doorDir = buffer.charAt(1);
			if (rooms.get(initial) == null) //if not in legend, throw exception
				throw new BadConfigFormatException();
			if (rooms.get(initial).equalsIgnoreCase("Hallway") || rooms.get(initial).equalsIgnoreCase("Walkway")) {
				//change if your walkway has a different name 
				cells.add(new WalkwayCell(curRow, curCol)); //add walkway cell
			} else 
				cells.add(new RoomCell(curRow, curCol, initial, doorDir)); //add room cell (see RoomCell for specifics)
			curCol++;
		}
		
		return curCol;
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {  //loads legend
		File f = new File(legendName);
		Scanner in = new Scanner(f);
		Scanner s = null;
		while (in.hasNextLine()) {
			s = new Scanner(in.nextLine());
			s.useDelimiter(",");
			String buffer = s.next();
			if (buffer.length() > 1) //exception if legend is more than one letter initial
				throw new BadConfigFormatException();
			Character key = buffer.charAt(0); //grabs first character
			if (!(s.hasNext())) //exception if there's more information
				throw new BadConfigFormatException();
			String value = s.next().trim(); //cuts off spaces (safety feature)
			rooms.put(key, value);
		}
		in.close();
	}
	
	public void calcAdjacencies() { //same as CluePaths
		for (int i = 0; i < numRows * numColumns; i++) {
			adjMtx.put(i, calcCellAdjacency(i));
			visited[i] = false;
		}
	}
	
	private LinkedList<Integer> calcCellAdjacency(int index) { //gets adjacencies for index location
		LinkedList<Integer> list = new LinkedList<Integer>();
		BoardCell cell = getCellAt(index);
		if (cell.isRoom() && !cell.isDoorway()) 
			return list;
		
		int above = index - numColumns;
		int below = index + numColumns;
		int left = index - 1;
		int right = index + 1;
		
		if (cell.isDoorway()) {
			DoorDirection dir = getRoomCellAt(index).getDoorDirection();
			switch (dir) { //cases for different door directions
			case DOWN: 
				list.add(below);
				return list;
			case UP:
				list.add(above);
				return list;
			case LEFT:
				list.add(left);
				return list;
			case RIGHT:
				list.add(right);
				return list;
			default: 
				return list;
			}
		}

		if (checkAdjInBoard(above) && (checkAdjDoor(index, above) || getCellAt(above).isWalkway())) 
			list.add(above); //box above checklist
		if (checkAdjInBoard(left) && checkInRow(index, left) && (checkAdjDoor(index, left) || getCellAt(left).isWalkway())) 
			list.add(left); //box to left checklist
		if (checkAdjInBoard(right) && checkInRow(index, right) && (checkAdjDoor(index, right) || getCellAt(right).isWalkway())) 
			list.add(right); //box to right checklist
		if (checkAdjInBoard(below) && (checkAdjDoor(index, below) || getCellAt(below).isWalkway())) 
			list.add(below); //box below checklist
		return list;
	}
	
	public LinkedList<Integer> getAdjList(int row, int col) { //gets adjacencies for row/col set
		int index = calcIndex(row, col);
		return getAdjList(index); //uses index method
	}
	
	public LinkedList<Integer> getAdjList(int index) {
		return adjMtx.get(index);
	}
	
	public boolean checkAdjInBoard(int index) { //checks if index is within board limits
		if ((index >= 0) && (index < numColumns * numRows)) 
			return true;
		else 
			return false;
	}
	
	public boolean checkInRow(int index, int indexAdj) { //checks if the index is on the current row
		if (indexAdj / numColumns == index / numColumns) 
			return true;
		else 
			return false;
	}
	
	public boolean checkAdjDoor(int index, int indexAdj) { //index = original location, indexAdj = index to check
		if (getCellAt(indexAdj).isDoorway()) {
			RoomCell cell = getRoomCellAt(indexAdj);
			DoorDirection dir = cell.getDoorDirection();
			switch (dir) { //door cases
			case DOWN: 
				if (index == indexAdj + numColumns) return true;
			case UP:
				if (index == indexAdj - numColumns) return true;
			case LEFT:
				if (index == indexAdj - 1) return true;
			case RIGHT:
				if (index == indexAdj + 1) return true;
			default: 
				return false;
			}
		}
		return false;
	}
	
	public void calcTargets(int row, int col, int steps) { //initialization for calcTargets, uses row/col
		int index = calcIndex(row, col);
		for (int i = 0; i < numRows * numColumns; i++) visited[i] = false;
		visited[index] = true;
		targets.clear();
		calcTargets(index, steps);
	}
	
	public void calcTargets(int index, int steps) { //recursive fn, same as CluePaths
		try {
			if (adjMtx.get(index).size() == 0) 
				throw new RuntimeException("Invalid location");
			
			for (Integer i : adjMtx.get(index)) {
				if (visited[i] == false) {
					visited[i] = true;
					if ((steps == 1 || getCellAt(i).isDoorway())) 
						targets.add(getCellAt(i)); //adds to potential targets if last step
					else 
						calcTargets(i, steps - 1); //recursive call if more steps are left
					visited[i] = false;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Set<BoardCell> getTargets() { //returns possible targets from instance variable
		return targets;
	}
	
	public int calcIndex(int row, int col) { //calculates location index from row/col
		return row * numColumns + col;
	}
	
	public RoomCell getRoomCellAt(int row, int col) { //gets cell if it's room, otherwise gives null
		int index = calcIndex(row, col);
		return getRoomCellAt(index);
	}
	
	public RoomCell getRoomCellAt(int index) { //index form of getRoomCellAt
		if (cells.get(index) instanceof RoomCell)
			return (RoomCell) cells.get(index);
		else
			return null;
	}
	
	public BoardCell getCellAt(int row, int col) { //gets cell regardless of walkway/room definition
		int index = calcIndex(row, col);
		return getCellAt(index); //uses index method
	}
	
	public BoardCell getCellAt(int index) {//gets cell regardless of walkway/room definition
		return cells.get(index);
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
}
