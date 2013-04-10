package gui;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
		
		add(guess);
		
		setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
	}
	
	public String returnString() {
		return (String) guess.getSelectedItem();
	}
}