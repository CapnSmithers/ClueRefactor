package gui;

import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PeoplePanel extends JPanel {
	
	JCheckBox colMustard;
	JCheckBox msScarlet;
	JCheckBox mrsWhite;
	JCheckBox mrGreen;
	JCheckBox mrsPeacock;
	JCheckBox profPlum;
	
	public PeoplePanel() {
		setLayout(new GridLayout(4,4));
		colMustard = new JCheckBox("Colonel Mustard");
		msScarlet = new JCheckBox("Miss Scarlet");
		mrsWhite = new JCheckBox("Mrs. White");
		mrGreen = new JCheckBox("Mr. Green");
		mrsPeacock = new JCheckBox("Mrs. Peacock");
		profPlum = new JCheckBox("Professor Plum");
		
		add(colMustard);
		add(msScarlet);
		add(mrsWhite);
		add(mrGreen);
		add(mrsPeacock);
		add(profPlum);
		
		setBorder(new TitledBorder(new EtchedBorder(), "People"));
	}

}