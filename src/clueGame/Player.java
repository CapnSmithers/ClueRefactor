package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
	private String playerName, colorStr;
	private Color color;
	private int startingLocation;
	private int currentLocation;
	protected ArrayList<Card> myCards;
	
	public static final int RADIUS = BoardCell.DIM/2;
	
	//For testing only
	public Player() {
		this.playerName = "Professor Plum";
		this.colorStr = "purple";
		this.color = convertColor(this.colorStr);
		this.startingLocation = (16*23);
		this.currentLocation = this.startingLocation;
		this.myCards = new ArrayList<Card>();
	}
	
	public Player(String playerName, String color, Integer startingLocation) {
		super();
		this.playerName = playerName;
		this.colorStr = color;
		this.color = convertColor(this.colorStr);
		this.startingLocation = startingLocation;
		this.currentLocation = this.startingLocation;
		this.myCards = new ArrayList<Card>();
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
				RADIUS, RADIUS);
		g.setColor(Color.black);
		g.drawOval(b.indexToPixelCol(currentLocation), b.indexToPixelRow(currentLocation),
				RADIUS, RADIUS);
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
}
