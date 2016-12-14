package com.cyumus.thingworx.erp.ui.panels.info;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class RaspberryPiDefaultInfoPanel extends JPanel {
	protected JTextField nameField;
	protected JPanel iconPanel, varsPanel;
	protected JTextArea descArea;
	protected JTextField amountField;
	public RaspberryPiDefaultInfoPanel() {
		setLayout(null);
		setSize(545,267);
		setBorder(new TitledBorder(new EtchedBorder(), "Information"));
		iconPanel = new JPanel();
		iconPanel.setBounds(10, 15, 75, 75);
		add(iconPanel);
		
		nameField = new JTextField();
		nameField.setBounds(95, 15, 200, 20);
		nameField.setColumns(10);
		nameField.setEditable(false);
		add(nameField);
		
		
		descArea = new JTextArea();
		descArea.setBounds(95, 42, 440, 44);
		descArea.setEditable(false);
		add(descArea);
		
		varsPanel = new JPanel();
		varsPanel.setBounds(10, 97, 525, 159);
		varsPanel.setLayout(null);
		varsPanel.setBorder(new TitledBorder(new EtchedBorder(), "About"));
		add(varsPanel);
	}
}
