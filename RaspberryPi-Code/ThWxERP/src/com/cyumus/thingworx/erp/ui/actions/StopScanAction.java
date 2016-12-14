package com.cyumus.thingworx.erp.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.Status;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel;

public class StopScanAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			RaspberryPiFrame.getSingleton().getClient().stopScanProcess();
			RaspberryPiFrame.getSingleton().update(Status.SLEEPING);
			RaspberryPiThingworxMainPanel.btnScan.setEnabled(true);
			RaspberryPiThingworxMainPanel.btnStop.setEnabled(false);
			System.out.println("SCANNING HAS STOPPED");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
