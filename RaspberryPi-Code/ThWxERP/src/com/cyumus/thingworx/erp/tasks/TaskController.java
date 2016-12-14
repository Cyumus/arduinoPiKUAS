package com.cyumus.thingworx.erp.tasks;

import java.util.HashMap;
import java.util.Timer;

import com.cyumus.thingworx.erp.things.ItemThing;

public class TaskController {
	private Timer timer;
	public TaskController(){
		this.timer = new Timer();
	}
	public void startScanning(HashMap<String, ItemThing> items, int delay){
		this.timer.schedule(new RaspberryPiScanningTask(items), delay);
	}
	public void stop(){
		this.timer.cancel();
		this.timer = new Timer();
	}
}
