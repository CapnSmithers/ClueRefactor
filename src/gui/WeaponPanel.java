package gui;

import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class WeaponPanel extends JPanel {
	
	JCheckBox candlestick;
	JCheckBox knife;
	JCheckBox leadPipe;
	JCheckBox revolver;
	JCheckBox rope;
	JCheckBox wrench;
	
	public WeaponPanel() {
		setLayout(new GridLayout(4,4));
		candlestick = new JCheckBox("Candlestick");
		knife = new JCheckBox("Knife");
		leadPipe = new JCheckBox("Lead Pipe");
		revolver = new JCheckBox("Revolver");
		rope = new JCheckBox("Rope");
		wrench = new JCheckBox("Wrench");
		
		add(candlestick);
		add(knife);
		add(leadPipe);
		add(revolver);
		add(rope);
		add(wrench);
		
		setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
	}

}