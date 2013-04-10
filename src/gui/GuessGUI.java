package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.RoomCell;
import clueGame.Solution;

public class GuessGUI extends JDialog {
	Board board;
	Solution suggestion;
	PguessPanel pGuess;
	WguessPanel wGuess;
	
	
	public GuessGUI(Board board, RoomCell cell) {
		suggestion = new Solution("", "", "");
		this.board = board;
		suggestion.room = board.getRoomName(cell.getInitial());
		
		setTitle("Make a Suggestion");
		setSize(300, 300);
		setLayout(new GridLayout(4, 1));
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		pGuess = new PguessPanel();
		wGuess = new WguessPanel();
		RoomText rGuess = new RoomText(suggestion.room);
		JButton submit = new JButton("Suggest");
		
		submit.addActionListener(new SubmitListener());
		
		add(pGuess);
		add(wGuess);
		add(rGuess);
		add(submit);
	}
	
	class RoomText extends JPanel {
		
		public RoomText(String room) {
			JTextField field = new JTextField(20);
			field.setEditable(false);
			field.setText(room);
			
			add(field);
		}
	}
	
	private class SubmitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			suggestion.person = pGuess.returnString();
			suggestion.weapon = wGuess.returnString();
			System.out.println(suggestion);
			
			

			HumanPlayer p = (HumanPlayer) board.getClueGame().players.get(board.getClueGame().curPlayerTurn);
			for (Player player : board.getClueGame().players) {
				if(player.getPlayerName().equalsIgnoreCase(suggestion.person)) {
					player.moveTo(p.getCurrentLocation());
				}
			}
			p.setGuess(suggestion);
			dispose();
		}
		
	}
}
