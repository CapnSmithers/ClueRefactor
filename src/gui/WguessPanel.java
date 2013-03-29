package gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class WguessPanel extends JPanel {
	
	private JComboBox<String> guess;
	
	public WguessPanel() {
		guess = new JComboBox<String>();
		
		guess.addItem("Candlestick");
		guess.addItem("Knife");
		guess.addItem("Lead Pipe");
		guess.addItem("Revolver");
		guess.addItem("Rope");
		guess.addItem("Wrench");
		
	}
}