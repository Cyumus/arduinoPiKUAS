package com.cyumus.thingworx.erp.ui.actions;

import javax.swing.Action;

import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxConnectPanel;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel;

public class RaspberryPiActionFactory {
	public static Action newAction(int id){
		switch(id){
			case RaspberryPiThingworxConnectPanel.CONNECT: return new FirstPageConnectAction();
			case RaspberryPiThingworxMainPanel.SCAN: return new StartScanAction();
			case RaspberryPiThingworxMainPanel.STOP: return new StopScanAction();
			default: return new DefaultAction();
		}
	}
}
