package com.cyumus.thingworx.erp.ui.panels.info;

import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cyumus.thingworx.erp.things.BinThing;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel.BinThingNode;

public class RaspberryPiBinInfoPanel extends RaspberryPiDefaultInfoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BinThingNode node;
	private BinThing bin;
	private JTextField sizeField;
	public RaspberryPiBinInfoPanel(BinThingNode thing) {
		super();
		this.node = thing;
		this.bin = thing.getBin();
		this.nameField.setText(bin.getName());
		this.descArea.setText(bin.getDescription());
		
		JLabel lblSize = new JLabel("Depth (cm)");
		lblSize.setBounds(10, 14, 63, 14);
		varsPanel.add(lblSize);
		
		sizeField = new JTextField();
		sizeField.setBounds(83, 11, 86, 20);
		sizeField.setColumns(10);
		sizeField.setText(""+bin.getDepth());
		sizeField.setEditable(false);
		varsPanel.add(sizeField);
		
	}

}
