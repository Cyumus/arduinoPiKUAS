package com.cyumus.thingworx.erp.ui.panels.info;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel.ItemThingNode;
import javax.swing.DropMode;

public class RaspberryPiItemInfoPanel extends RaspberryPiDefaultInfoPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ItemThingNode node;
	private ItemThing item;
	private JTextField amountField;
	private JLabel lblAmount;
	private JTextField sizeField;
	public RaspberryPiItemInfoPanel(ItemThingNode thing) {
		super();
		this.node = thing;
		this.item = thing.getItem();
		this.nameField.setText(item.getName());
		this.descArea.setText(item.getDescription());
		
		amountField = new JTextField();
		amountField.setColumns(10);
		amountField.setBounds(425, 11, 110, 20);
		amountField.setEditable(false);
		this.amountField.setText(""+item.getAmount());
		add(amountField);
		
		lblAmount = new JLabel("Amount");
		lblAmount.setBounds(339, 14, 46, 14);
		add(lblAmount);
		
		JLabel lblSize = new JLabel("Size (cm)");
		lblSize.setBounds(10, 14, 77, 14);
		varsPanel.add(lblSize);
		
		sizeField = new JTextField();
		sizeField.setBounds(85, 11, 86, 20);
		sizeField.setColumns(10);
		sizeField.setText(""+item.getSize());
		sizeField.setEditable(false);
		varsPanel.add(sizeField);
		
	}
}
