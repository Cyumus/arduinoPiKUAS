package com.cyumus.thingworx.erp.tasks;

import java.util.HashMap;
import java.util.TimerTask;

import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;

public class RaspberryPiScanningTask extends TimerTask {
	private HashMap<String, ItemThing> items;
	public RaspberryPiScanningTask(HashMap<String, ItemThing> items){
		this.items = items;
	}
	@Override
	public void run() {
		String name ="";
		try {
			for (ItemThing item:items.values()){
				name = item.getName();
				RaspberryPiFrame.getSingleton().getClient().scan(item);
				System.out.println("Scanned item: "+name);
			}
			RaspberryPiFrame.getSingleton().getClient().processAndUpdateAllThings();
		} catch (Exception e) {
			System.out.println("Unable to scan item "+name);
			e.printStackTrace();
		}
	}
}
