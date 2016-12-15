package com.cyumus.thingworx.erp.tasks;

import java.util.HashMap;
import java.util.TimerTask;

import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;

public class RaspberryPiScanningTask extends TimerTask {
	private HashMap<String, ItemThing> items;
	private int delay;
	public RaspberryPiScanningTask(HashMap<String, ItemThing> items, int delay){
		this.items = items;
		this.delay = delay;
	}
	@Override
	public void run() {
		String name ="";
		try {
			while(RaspberryPiFrame.getSingleton().getClient().isScanning()){
				for (ItemThing item:items.values()){
					name = item.getName();
					RaspberryPiFrame.getSingleton().getClient().scan(item);
					System.out.println("Scanned item: "+name);
				}
				RaspberryPiFrame.getSingleton().getClient().processAndUpdateAllThings();
				Thread.sleep(delay);
			}
		} catch (Exception e) {
			System.out.println("Unable to scan item "+name);
			e.printStackTrace();
		}
	}
}
