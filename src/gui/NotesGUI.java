package gui;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class NotesGUI extends JDialog {
	
	public NotesGUI() {
		setTitle("Dectective Notes");
		setSize(200, 300);
		setLayout(new GridLayout(3, 2));
		JPanel people = new JPanel();
		JPanel pGuess = new JPanel();
		JPanel rooms = new JPanel();
		JPanel rGuess = new JPanel();
		JPanel weapons = new JPanel();
		JPanel wGuess = new JPanel();
	}

}
