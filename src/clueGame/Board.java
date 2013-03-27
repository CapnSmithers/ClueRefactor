package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel {
	
	private ArrayList<BoardCell> cells; //stores board
	private Map<Character, String> rooms; //legend codes
	private Map<String, Point> roomPositions; //room name positions
	private Map<Integer, LinkedList<Integer>> adjMtx; //adjacency list
	private Set<BoardCell> targets; //list of targets- gets cleared for every new calcTargets
	private boolean[] visited; //visited matrix
	private int numRows;
	private int numColumns;
	private String mapConfigFileName, legendConfigFilename; //csv file with board initials; txt with legend file info
	
	public Board() {
		mapConfigFileName = "ClueMap.csv"; //default map name
		legendConfigFilename = "legend.txt"; //default legend name
		//initializations
		cells = new ArrayList<BoardCell>(); 
		rooms = new HashMap<Character, String>();
		roomPositions = new HashMap<String, Point>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		
		loadConfigFiles();
		calcAdjacencies();
	}
	
	public Board(String mapName, String legendName) { //instantiator with filenames
		mapConfigFileName = mapName;
		legendConfigFilename = legendName;
		//initializations
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		roomPositions = new HashMap<String, Point>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		
		loadConfigFiles();
		calcAdjacencies();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Iterates through every cell, calls cell draw function
		for(BoardCell c: cells) {
			c.draw(g, this);
		}
		g.setColor(Color.blue);
		for(Map.Entry<String, Point> entry: roomPositions.entrySet()) {
			Point p = entry.getValue();
			String roomName = entry.getKey();
			g.drawString(roomName, (int) p.getX(), (int) p.getY());
		}
	}
	
	public void loadConfigFiles() { //loads legend, then board, calcs adjacencies
		try {
			loadRoomConfig();      //Load legend
			loadBoardConfig();	   //Load board
			visited = new boolean[numRows * numColumns];
			calcAdjacencies();
		} catch (Exception e) {
			e.printStackTrace(); //if exception is found
		}
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		File f = new File(mapConfigFileName);
		Scanner in = new Scanner(f);
		int numRows = 0;
		//preloader config: runs the first row to find out the number of columns
		int numCols = loadRoomLine(in, numRows);
		numRows++;
		while (in.hasNextLine()) {
			int currentCols = loadRoomLine(in, numRows);
			if (currentCols != numCols) //throws exceptions if number of cols is different
				throw new BadConfigFormatException(mapConfigFileName);
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
		File f = new File(legendConfigFilename);
		Scanner in = new Scanner(f);
		Scanner s = null;
		while (in.hasNextLine()) {
			s = new Scanner(in.nextLine());
			s.useDelimiter(",");
			String buffer = s.next();
			if (buffer.length() > 1) //exception if legend is more than one letter initial
				throw new BadConfigFormatException(legendConfigFilename);
			Character key = buffer.charAt(0); //grabs first character
			if (!(s.hasNext())) //exception if there's a lack of information
				throw new BadConfigFormatException(legendConfigFilename);
			String value = s.next().trim(); //cuts off spaces (safety feature)
			
			if (!(s.hasNext())) //exception if there's a lack of information
				throw new BadConfigFormatException(legendConfigFilename);
			Integer x = s.nextInt(); //cuts off spaces (safety feature)
			
			if (!(s.hasNext())) //exception if there's a lack of information
				throw new BadConfigFormatException(legendConfigFilename);
			Integer y = s.nextInt(); //cuts off spaces (safety feature)
			
			if (s.hasNext()) //exception if there's extra information
				throw new BadConfigFormatException(legendConfigFilename);
			rooms.put(key, value);
			roomPositions.put(value, new Point(x,y));
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
		LinkedList<Integer> adjList = new LinkedList<Integer>();
		BoardCell currentCell = getCellAt(index);
		if (currentCell.isRoom() && !currentCell.isDoorway()) 
			return adjList;
		
		int above = index - numColumns;
		int below = index + numColumns;
		int left = index - 1;
		int right = index + 1;
		
		if (currentCell.isDoorway()) {
			DoorDirection dir = getRoomCellAt(index).getDoorDirection();
			switch (dir) { //cases for different door directions
			case DOWN: 
				adjList.add(below);
				return adjList;
			case UP:
				adjList.add(above);
				return adjList;
			case LEFT:
				adjList.add(left);
				return adjList;
			case RIGHT:
				adjList.add(right);
				return adjList;
			default: 
				return adjList;
			}
		}

		if (checkAdjInBoard(above) && (checkAdjDoor(index, above) || getCellAt(above).isWalkway())) 
			adjList.add(above); //box above checklist
		if (checkAdjInBoard(left) && checkInRow(index, left) && (checkAdjDoor(index, left) || getCellAt(left).isWalkway())) 
			adjList.add(left); //box to left checklist
		if (checkAdjInBoard(right) && checkInRow(index, right) && (checkAdjDoor(index, right) || getCellAt(right).isWalkway())) 
			adjList.add(right); //box to right checklist
		if (checkAdjInBoard(below) && (checkAdjDoor(index, below) || getCellAt(below).isWalkway())) 
			adjList.add(below); //box below checklist
		return adjList;
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
				if (index == indexAdj + numColumns) 
				return true;
			case UP:
				if (index == indexAdj - numColumns) 
				return true;
			case LEFT:
				if (index == indexAdj - 1) 
				return true;
			case RIGHT:
				if (index == indexAdj + 1) 
				return true;
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
	
	public int indexToPixelRow(int index) {
		return index/numColumns;
	}
	
	public int indexToPixelCol(int index) {
		return index%numColumns;
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
	
	public String getRoomName(Character key) {
		return rooms.get(key);
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
}
