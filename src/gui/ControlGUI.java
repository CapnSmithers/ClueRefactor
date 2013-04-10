package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.ClueGame;
import clueGame.Player;
import clueGame.Solution;

public class ControlGUI extends JPanel {
	
	private JTextField whoseTurn;
	JTextField dieRoll, guessResultText, guessText;

	public ControlGUI(ClueGame clueGame) {
		
		//Main panel, contains all components
		JPanel mainPanel = new JPanel();
		
		//Whose Turn? section
		JPanel whoseTurnPanel = new JPanel();
		whoseTurnPanel.setLayout(new GridLayout(2, 0));
		JLabel whoseTurnLabel = new JLabel("Whose Turn?");
		whoseTurnPanel.add(whoseTurnLabel);
		whoseTurn = new JTextField(15);
		whoseTurnPanel.add(whoseTurn);
		mainPanel.add(whoseTurnPanel);
		
		//Next Player and Make Accusation buttons
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		nextPlayer.addActionListener(new nextButtonListener(clueGame));
		makeAccusation.addActionListener(new accuseButtonListener(clueGame));
		mainPanel.add(nextPlayer);
		mainPanel.add(makeAccusation);
		
		//Die roll
		JPanel dieRollPanel = new JPanel();
		dieRoll = new JTextField(2);
		dieRoll.setEditable(false);  //Can't edit this field
		JLabel dieRollLabel = new JLabel("Roll:");
		dieRollPanel.add(dieRollLabel);
		dieRollPanel.add(dieRoll);
		dieRollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die Roll"));
		mainPanel.add(dieRollPanel);
		
		//Guess panel
		JPanel guessPanel = new JPanel();
		guessText = new JTextField(20);
		guessText.setEditable(false);  //Can't edit this field
		JLabel guess = new JLabel("");
		guessPanel.add(guess);
		guessPanel.add(guessText);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Last Guess"));
		mainPanel.add(guessPanel);
		
		//Guess Result panel
		JPanel guessResultPanel = new JPanel();
		guessResultText = new JTextField(20);
		guessResultText.setEditable(false);  //Can't edit this field
		JLabel guessResult = new JLabel("");
		guessResultPanel.add(guessResult);
		guessResultPanel.add(guessResultText);
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Last Guess Result"));
		mainPanel.add(guessResultPanel);
		
		add(mainPanel, BorderLayout.CENTER);
	}
	
	class nextButtonListener implements ActionListener {
		ClueGame clueGame;
		
		public nextButtonListener(ClueGame clueGame) {
			this.clueGame = clueGame;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			guessText.setText(clueGame.getPlayerGuess());
			guessResultText.setText(clueGame.getPlayerGuessResult());
			clueGame.nextTurn();
			whoseTurn.setText(clueGame.getCurrentPlayer().getPlayerName());
			dieRoll.setText(clueGame.getCurrentPlayer().getSteps());
		}
		
	}
	
	class accuseButtonListener implements ActionListener {
		ClueGame clueGame;
		
		public accuseButtonListener(ClueGame clueGame) {
			this.clueGame = clueGame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(clueGame.getCurrentPlayer() == clueGame.humanPlayer && !clueGame.humanPlayerHasMoved()) {
				AccuseGUI accuse = new AccuseGUI(clueGame);
				accuse.setVisible(true);
				
				Solution accusation = clueGame.humanPlayer.getGuess();
				boolean correct = clueGame.checkAccusation(accusation);
				if(correct) {
					String message = clueGame.humanPlayer.getPlayerName() + " has won the game with a correct guess of: "
							+ accusation.person + " in the " + accusation.room + " with the " + accusation.weapon
							+ "!";
					JOptionPane.showMessageDialog(clueGame, message,
							"Game Over!", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String message = clueGame.humanPlayer.getPlayerName() + " has made an incorrect accusation of: "
							+ accusation.person + " in the " + accusation.room + " with the " + accusation.weapon
							+ "! Buh buh buuuuhhhhhh!";
					JOptionPane.showMessageDialog(clueGame, message,
							"Game Not Over!", JOptionPane.WARNING_MESSAGE);
				}
				clueGame.humanPlayer.setHasMoved(true);
			} else {
				JOptionPane.showMessageDialog(clueGame, "It's Not Your Turn!",
						"Mr. T says 'I pity da foo that do that'", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	public void updateDisplay() {
		
	}
}
