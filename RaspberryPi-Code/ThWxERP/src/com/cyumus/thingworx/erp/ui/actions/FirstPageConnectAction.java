package com.cyumus.thingworx.erp.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.Status;

public class FirstPageConnectAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			RaspberryPiFrame.getSingleton().update(Status.CONNECTING);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}	
}
