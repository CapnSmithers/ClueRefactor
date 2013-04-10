package gui;

import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import clueGame.ClueGame;
import clueGame.Solution;


public class AccuseGUI extends javax.swing.JDialog {
	private ClueGame clueGame;
	PguessPanel pGuess;
	WguessPanel wGuess;
	RguessPanel rGuess;
	
	public AccuseGUI(ClueGame clueGame) {
		this.clueGame = clueGame;
		
		setTitle("Make an Accusation");
		setSize(300,300);
		setLayout(new GridLayout(4, 1));
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		pGuess = new PguessPanel();
		wGuess = new WguessPanel();
		rGuess = new RguessPanel();
		JButton submit = new JButton("Suggest");
		
		submit.addActionListener(new SubmitListener());
		
		add(pGuess);
		add(wGuess);
		add(rGuess);
		add(submit);
	}
	
	private class SubmitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String person = pGuess.returnString();
			String room = rGuess.returnString();
			String weapon = wGuess.returnString();
			Solution s = new Solution(person, weapon, room);
			
			clueGame.humanPlayer.setGuess(s);
			dispose();
		}
		
	}
}
