package com.cyumus.thingworx.erp.ui.panels.info;

import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cyumus.thingworx.erp.things.LocationThing;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel.LocationThingNode;
import com.thingworx.types.primitives.structs.Location;

public class RaspberryPiLocationInfoPanel extends RaspberryPiDefaultInfoPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocationThingNode node;
	private LocationThing loc;
	private Location location;
	private JTextField xField;
	private JTextField yField;
	private JTextField zField;
	public RaspberryPiLocationInfoPanel(LocationThingNode thing) {
		super();
		this.node = thing;
		this.loc = thing.getLocation();
		this.nameField.setText(loc.getName());
		this.descArea.setText(loc.getDescription());
		
		location = loc.getGPS();
		
		xField = new JTextField();
		xField.setBounds(100, 22, 86, 20);
		xField.setColumns(10);
		xField.setText(""+location.getLatitude());
		xField.setEditable(false);
		varsPanel.add(xField);
		
		JLabel lblSize = new JLabel("Latitude");
		lblSize.setBounds(10, 25, 74, 14);
		varsPanel.add(lblSize);
		
		yField = new JTextField();
		yField.setText(""+location.getLongitude());
		yField.setEditable(false);
		yField.setColumns(10);
		yField.setBounds(100, 50, 86, 20);
		varsPanel.add(yField);
		
		JLabel lblY = new JLabel("Longitude");
		lblY.setBounds(10, 53, 74, 14);
		varsPanel.add(lblY);
		
		zField = new JTextField();
		zField.setText(location.getElevation()+"");
		zField.setEditable(false);
		zField.setColumns(10);
		zField.setBounds(100, 81, 86, 20);
		varsPanel.add(zField);
		
		JLabel lblZ = new JLabel("Elevation");
		lblZ.setBounds(10, 84, 74, 14);
		varsPanel.add(lblZ);
		
	}

}
