package com.cyumus.thingworx.erp.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;

public class StopScanAction extends AbstractAction{

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			RaspberryPiFrame.getSingleton().getClient().stopScanProcess();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
