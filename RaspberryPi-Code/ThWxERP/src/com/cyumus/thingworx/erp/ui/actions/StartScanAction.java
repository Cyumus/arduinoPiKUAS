package com.cyumus.thingworx.erp.ui.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;

import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.Status;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel;

public class StartScanAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			HashMap<String, ItemThing> items = 
					RaspberryPiFrame.getSingleton().getPanel().getItems();
			int delay = 
					RaspberryPiFrame.getSingleton().getPanel().getTime();
			RaspberryPiFrame.getSingleton().getClient().startScanProcess(items, delay);
			RaspberryPiThingworxMainPanel.btnScan.setEnabled(false);
			RaspberryPiThingworxMainPanel.btnStop.setEnabled(true);
			System.out.println("STARTED TO SCAN "+items.size()+" ITEMS.");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
