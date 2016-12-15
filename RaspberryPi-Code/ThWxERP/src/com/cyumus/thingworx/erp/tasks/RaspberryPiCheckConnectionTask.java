package com.cyumus.thingworx.erp.tasks;

import java.util.TimerTask;

import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel;

public class RaspberryPiCheckConnectionTask extends TimerTask {
	private int delay;
	public RaspberryPiCheckConnectionTask(int delay){
		this.delay = delay;
	}
	@Override
	public void run() {
		try{
			while(true){
				RaspberryPiThingworxMainPanel.rdbtnTw.setSelected(RaspberryPiFrame.getSingleton().getClient().isConnected());
				RaspberryPiThingworxMainPanel.rdbtnXbee.setSelected(RaspberryPiFrame.getSingleton().getClient().getXBee().isConnected());
				RaspberryPiThingworxMainPanel.btnScan.setEnabled(!RaspberryPiThingworxMainPanel.btnStop.isEnabled() 
						&& RaspberryPiThingworxMainPanel.rdbtnTw.isSelected()
						&& RaspberryPiThingworxMainPanel.rdbtnXbee.isSelected());
				Thread.sleep(delay);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
