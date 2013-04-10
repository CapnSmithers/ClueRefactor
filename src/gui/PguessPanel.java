package gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
		
		add(guess);
		
		setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
	}
	
	public String returnString() {
		return (String) guess.getSelectedItem();
	}
}
