package com.cyumus.thingworx.erp.ui;

import javax.swing.Action;

import com.cyumus.thingworx.erp.ui.actions.DefaultAction;
import com.cyumus.thingworx.erp.ui.actions.FirstPageConnectAction;

public class RaspberryPiActionFactory {
	public static Action newAction(int id){
		switch(id){
			case RaspberryPiThingworxConnectPanel.CONNECT: return new FirstPageConnectAction();
			default: return new DefaultAction();
		}
	}
}
