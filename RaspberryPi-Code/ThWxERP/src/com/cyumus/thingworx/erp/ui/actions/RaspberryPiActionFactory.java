package com.cyumus.thingworx.erp.ui.actions;

import javax.swing.Action;

import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxConnectPanel;

public class RaspberryPiActionFactory {
	public static Action newAction(int id){
		switch(id){
			case RaspberryPiThingworxConnectPanel.CONNECT: return new FirstPageConnectAction();
			default: return new DefaultAction();
		}
	}
}
