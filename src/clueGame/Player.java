package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
	protected ClueGame clueGame;
	private String playerName, colorStr;
	private Color color;
	private int startingLocation;
	protected int currentLocation;
	private Integer steps;
	protected ArrayList<Card> myCards;
	
	public static final int DIM = BoardCell.DIM;
	
	//For testing only
	public Player() {
		this.playerName = "Professor Plum";
		this.colorStr = "purple";
		this.color = convertColor(this.colorStr);
		this.startingLocation = (16*23);
		this.currentLocation = this.startingLocation;
		this.myCards = new ArrayList<Card>();
		steps = 0;
	}
	
	public Player(ClueGame clueGame, String playerName, String color, Integer startingLocation) {
		super();
		this.clueGame = clueGame;
		this.playerName = playerName;
		this.colorStr = color;
		this.color = convertColor(this.colorStr);
		this.startingLocation = startingLocation;
		this.currentLocation = this.startingLocation;
		this.myCards = new ArrayList<Card>();
		steps = 0;
	}
	
	public Card disproveSuggestion(Card person, Card weapon, Card room) {
		//check for any unrevealed cards that match
		ArrayList<Card> possibilities = new ArrayList<Card>();
		for (Card c : myCards) {
			if (c.equals(person) || c.equals(room) || c.equals(weapon)) {
				if (!c.isHasBeenRevealed()) {		
					possibilities.add(c);
				}
			}
		}
        
		//choose random possibility
        if (possibilities.size() <= 0) //no unrevealed cards
        	return null;
        Collections.shuffle(possibilities);
        return possibilities.get(0);
	}
	
	private Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			java.lang.reflect.Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
	}
	
	public void addCard(Card c) {
		myCards.add(c);
	}
	
	public void draw(Graphics g, Board b) {
		g.setColor(color);
		g.fillOval(b.indexToPixelCol(currentLocation), b.indexToPixelRow(currentLocation),
				DIM, DIM);
		g.setColor(Color.black);
		g.drawOval(b.indexToPixelCol(currentLocation), b.indexToPixelRow(currentLocation),
				DIM, DIM);
	}

	/*
	 * Getters and setters for testing purposes
	 */
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public String getColorStr() {
		return colorStr;
	}

	public Integer getStartingLocation() {
		return startingLocation;
	}

	public void setStartingLocation(Integer startingLocation) {
		this.startingLocation = startingLocation;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}
	
	public int getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(int currentLocation) {
		this.currentLocation = currentLocation;
	}

	public void makeMove() {
		steps = rollDie();
	}
	
	public int rollDie() {
		Random random = new Random();
		return random.nextInt(5) + 1;  //Ensures that roll will be at least 1
	}
	
	public String getSteps() {
		return steps.toString();
	}
		
		
}
