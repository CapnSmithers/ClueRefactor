package gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class RguessPanel extends JPanel {
	
	private JComboBox<String> guess;
	
	public RguessPanel() {
		guess = new JComboBox<String>();
		
		guess.addItem("Gallery");
		guess.addItem("Billiards Room");
		guess.addItem("Library");
		guess.addItem("Bedroom");
		guess.addItem("Ballroom");
		guess.addItem("Bathroom");
		guess.addItem("Conservatory");
		guess.addItem("Kitchen");
		guess.addItem("Pantry");
		
	}
}