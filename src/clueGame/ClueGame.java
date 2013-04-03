package clueGame;

import gui.ControlGUI;
import gui.NotesGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class ClueGame extends JFrame {
	
	private String playerConfigFilename, weaponConfigFilename;
	private String mapConfigFilename, legendConfigFilename; //csv file with board initials; txt with legend file info
	private Set<Card> cards;
	public ArrayList<Player> players;    //Public to test
	private ArrayList<String> weapons;
	public HumanPlayer humanPlayer;
	public int curPlayerTurn;
	private Solution solution;
	public Board board;
	public static final int WINDOW_HEIGHT = 850;
	public static final int WINDOW_WIDTH = 900;	
	
	public ClueGame() {
		//Initialize JFrame object
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createMenuBar());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue");
		setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		
		playerConfigFilename = "playerConfig.txt"; //default player config name
		weaponConfigFilename = "weaponConfig.txt"; //default weapon config name
		
		players = new ArrayList<Player>();
		board = new Board(this);
		cards = new HashSet<Card>();
		weapons = new ArrayList<String>();
		solution = new Solution("", "", "");
		
		createGame(0);  
	}

	public ClueGame(String mapFname, String legendFname, String playerFname, String weaponFname) {
		mapConfigFilename = mapFname;
		legendConfigFilename = legendFname;
		playerConfigFilename = playerFname; //default player config name
		weaponConfigFilename = weaponFname; //default weapon config name
		
		board = new Board(mapConfigFilename, legendConfigFilename);
		cards = new HashSet<Card>();
		players = new ArrayList<Player>();
		weapons = new ArrayList<String>();
		solution = new Solution("", "", "");
		
		createGame(0);
	}
	
	public void createGame(int humanPlayerIndex) {	
		//load players and weapons
		loadConfigFiles(humanPlayerIndex);
		curPlayerTurn = 0;
		
		//create new deck and solution
		createDeck();	
		createSolution();
		
		//deal cards
		deal();
		
		//Adds GUI components to JFrame
		ControlGUI controlGUI = new ControlGUI(this);
		add(controlGUI, BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		add(displayPlayerCards(), BorderLayout.EAST);
	}

	public void deal() {
		int i = 0;
		for (Card c : cards) {
			if (!solution.contains(c)) {
				int p = i % players.size();
				players.get(p).addCard(c);
				i++;
			}
		}
	}
	
	
	//Creates menu bar objects in JFrame
	private JMenu createMenuBar() {
		JMenu menu = new JMenu("File"); 
		menu.add(createFileExitItem());
		menu.add(createDetectiveNotes());
		return menu;

	}

	private JMenuItem createDetectiveNotes() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class DetectiveNotesListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				NotesGUI detectiveNotes = new NotesGUI();
				detectiveNotes.setVisible(true);
			}
		}
		item.addActionListener(new DetectiveNotesListener());
		return item;
	}

	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;

	}
	
	//Function to display player cards
	//Still need to work on formatting
	private JPanel displayPlayerCards() {
		JPanel playerCards = new JPanel();
		JPanel peopleCards = new JPanel();
		JPanel weaponCards = new JPanel();
		JPanel roomCards = new JPanel();
		JLabel myCards = new JLabel("My Cards:");
		
		playerCards.setLayout(new GridLayout(4,1));
		peopleCards.setLayout(new GridLayout(0, 1));
		weaponCards.setLayout(new GridLayout(0, 1));
		roomCards.setLayout(new GridLayout(0, 1));
		
		for(Card c : humanPlayer.myCards) {
			if (c.getCardType() == Card.CardType.ROOM) {		//Add to roomCards panel
				JTextField room = new JTextField(c.getCardName());
				room.setEditable(false);
				roomCards.add(room);
			} else if (c.getCardType() == Card.CardType.WEAPON) {  //Add to weaponCards panel
				JTextField weapon = new JTextField(c.getCardName());
				weapon.setEditable(false);
				weaponCards.add(weapon);
			} else {												//Add to peopleCards panel
				JTextField people = new JTextField(c.getCardName());
				people.setEditable(false);
				peopleCards.add(people);
			}
		}
		
		peopleCards.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		roomCards.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		weaponCards.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		
		playerCards.add(myCards);
		playerCards.add(peopleCards);
		playerCards.add(weaponCards);
		playerCards.add(roomCards);
		return playerCards;
	}

	private void createDeck() {
		//use players array for players cards
		for (Player p : players)
			cards.add(new Card(p.getPlayerName(), Card.CardType.PERSON));
		
		//use weapons array for weapons cards
		for (String w : weapons)
			cards.add(new Card(w, Card.CardType.WEAPON));
		
		//use rooms array in board class for room cards
		Map<Character, String> rooms = board.getRooms();
		for (Character key : rooms.keySet()) {
			//ignore walkway and hall
			if (key != 'W' && key != 'H') {
				cards.add(new Card(rooms.get(key), Card.CardType.ROOM));
			}
		}
	}

	public void loadConfigFiles(int humanPlayerIndex) {
		try {
			loadPlayerConfig(humanPlayerIndex);
			loadWeaponConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	private void loadPlayerConfig(int humanPlayerIndex) throws FileNotFoundException {
		File f = new File(playerConfigFilename);
		Scanner in = new Scanner(f);
		int numPlayers = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			line = line.replace("(",  "").replace(")", ""); //remove parenthesis from start coords
			String[] pInfo = line.split(",");
			String pName = pInfo[0].trim();
			String pColor = pInfo[1].trim();
			int pRow = Integer.parseInt(pInfo[2].trim());
			int pCol = Integer.parseInt(pInfo[3].trim());
			Player p = null;
			if (humanPlayerIndex == numPlayers) {
				p = new HumanPlayer(pName, pColor, board.calcIndex(pRow, pCol));
				humanPlayer = (HumanPlayer) p;
			}
			else {
				p = new ComputerPlayer(pName, pColor, board.calcIndex(pRow, pCol));
			}
			players.add(p);
			numPlayers++;		
		}
		in.close();
	}
	
	private void loadWeaponConfig() throws FileNotFoundException {
		File f = new File(weaponConfigFilename);
		Scanner in = new Scanner(f);
		int numWeapons = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			weapons.add(line);
			numWeapons++;		
		}
		in.close();
	}
	
	public void createSolution() {
		Card personCard = Card.getRandomCard(cards, CardType.PERSON);
		Card weaponCard = Card.getRandomCard(cards, CardType.WEAPON);
		Card roomCard = Card.getRandomCard(cards, CardType.ROOM);
		
		solution = new Solution(personCard, weaponCard, roomCard);
	}
	
	public Card handleSuggestion(String person, String weapon, String room, Player accusingPerson) {
		Card personCard = new Card(person, Card.CardType.PERSON);
		Card weaponCard = new Card(weapon, Card.CardType.WEAPON);
		Card roomCard = new Card(room, Card.CardType.ROOM);
		
		//built list of possible cards to return. they may not come from players that are making the accusation
		ArrayList<Card> possibilities = new ArrayList<Card>();
		for (Player p : players) {
			Card c = p.disproveSuggestion(personCard, weaponCard, roomCard);
			
			if (c != null && (accusingPerson.getPlayerName() != p.getPlayerName())) {
				possibilities.add(c);
			}
		}
		
		//choose random possibility
        if (possibilities.size() <= 0) //no possible cards
        	return null;
        Collections.shuffle(possibilities);
        return possibilities.get(0);
	}
	
	public Solution createSuggestion() {
		//create a suggestion for the current player
		
		Player p = players.get(curPlayerTurn);
		RoomCell rc = (RoomCell) board.getRoomCellAt(p.getCurrentLocation());
		String roomName = board.getRoomName(rc.getInitial());
		
		ArrayList<Card> person_possibilities = new ArrayList<Card>();
		ArrayList<Card> weapon_possibilities = new ArrayList<Card>();
		for (Card c : cards) {
			if (!c.isHasBeenRevealed()) {
				if (c.getCardType() == Card.CardType.PERSON) {
					person_possibilities.add(c);
				}
				if (c.getCardType() == Card.CardType.WEAPON) {
					weapon_possibilities.add(c);
				}
			}
		}
		
		if (person_possibilities.size() <= 0 || weapon_possibilities.size() <= 0)
        	return null;
		
		Collections.shuffle(person_possibilities);
		Collections.shuffle(weapon_possibilities);
		
		Card personCard = person_possibilities.get(0);
		Card weaponCard = weapon_possibilities.get(0);
		Card roomCard = new Card(roomName, Card.CardType.ROOM);
		
		return new Solution(personCard, weaponCard, roomCard);
	}
	
	public boolean checkAccusation(Solution proposed) {
		return solution.matches(proposed);
	}
	
	public void nextTurn() {
		if(!humanPlayerHasMoved())
			return;
		curPlayerTurn = (curPlayerTurn + 1)%players.size();
		players.get(curPlayerTurn).makeMove();
	}
	
	public Player getCurrentPlayer() {
		return players.get(curPlayerTurn);
	}
	
	//Checks if it is human's turn
	public boolean checkHumanPlayerMove() {
		if (players.get(curPlayerTurn) == humanPlayer) {
			return true;
		}
		return false;
	}
	
	//Checks if human player has moved
	public boolean humanPlayerHasMoved() {
		return humanPlayer.getHasMoved();
	}
	
	//Main function to display board
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.setVisible(true);
		String message = "You are " + game.humanPlayer.getPlayerName() + ". Press Next Player to start.";
		JOptionPane.showMessageDialog(game, message, "Let's Play Clue!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*
	 * Getters and setters for testing
	 */
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Set<Card> getCards() {
		return cards;
	}

	public void setCards(Set<Card> cards) {
		this.cards = cards;
	}
	
	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	//TESTING ONLY
	//Functions to select specific cards -- picks Ballroom, Aliens, and Mrs. Peacock
	public Card pickRoomCard() {
		for(Card c: cards) {
			if (c.getCardName().equalsIgnoreCase("Ballroom")) {
				return c;
			}
		}
		return null;
	}
	
	public Card pickWeaponCard() {
		for(Card c: cards) {
			if (c.getCardName().equalsIgnoreCase("Revolver")) {
				return c;
			}
		}
		return null;
	}
	
	public Card pickPersonCard() {
		for(Card c: cards) {
			if (c.getCardName().equalsIgnoreCase("Mrs. Peacock")) {
				return c;
			}
		}
		return null;
	}

	public Solution setSolution() {
		solution = new Solution("Colonel Mustard", "Revolver", "Ballroom");
		return solution;				
	}
	
	public Solution setWrongSolution() {
		Solution solution = new Solution("Mrs. Peacock", "Lead Pipe", "Pantry");
		return solution;
	}

}
