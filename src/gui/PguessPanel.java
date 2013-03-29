package gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class PguessPanel extends JPanel {
	
	private JComboBox<String> guess;
	
	public PguessPanel() {
		guess = new JComboBox<String>();
		
		guess.addItem("Colonel Mustard");
		guess.addItem("Miss Scarlet");
		guess.addItem("Mrs. White");
		guess.addItem("Mr. Green");
		guess.addItem("Mrs. Peacock");
		guess.addItem("Professor Plum");
		
	}
}
