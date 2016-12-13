package com.cyumus.thingworx.erp;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import com.cyumus.thingworx.erp.comm.Xbee;
import com.cyumus.thingworx.erp.things.BinThing;
import com.cyumus.thingworx.erp.things.ItemThing;
import com.cyumus.thingworx.erp.things.LocationThing;
import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.common.SecurityClaims;

public class RaspberryPiClient extends ConnectedThingClient {
	private HashMap<String,LocationThing> locs;
	private HashMap<String,BinThing> bins;
	private HashMap<String,ItemThing> items;
	private Xbee xbee;
	
	public RaspberryPiClient(ClientConfigurator config) throws Exception {
		super(config);
		this.locs = new HashMap<String,LocationThing>();
		this.bins = new HashMap<String,BinThing>();
		this.items = new HashMap<String,ItemThing>();
	}
	
	/**
	 * This function scans, process and updates all things while the value is active.
	 * @throws Exception 
	 */
	public void startScanProcess(int delay) throws Exception{
		synchronized (this){
			// While the client is working
			while(!this.isShutdown()) {
				// If the client is connected to Thingworx
				if(this.isConnected()) {
					for (ItemThing item:this.items.values()){
						// It makes the sensor to scan
						this.scan(item);
					}
					// And it updates its values to Thingworx
					this.processAndUpdateAllThings();
				}
				Thread.sleep(delay);
			}
		}
	}
	
	/**
	 * This function makes all things to process and update their current state to Thingworx.
	 * @throws Exception
	 */
	private void processAndUpdateAllThings() throws Exception{
		for (LocationThing loc:this.locs.values()) loc.processScanRequest();
		for (BinThing bin:this.bins.values()) bin.processScanRequest();
		for (ItemThing item:this.items.values()) item.processScanRequest();
	}
	
	/**
	 * This function gets all the configuration from a file and creates the things.
	 * @throws Exception 
	 */
	public void getThingsFromConfig() throws Exception{
		List<String> lines = Files.readAllLines(Paths.get("./things/things.txt"));
		LocationThing currLocation = null;
		BinThing currBin = null;
		ItemThing currItem = null;
		
		for (String line:lines){
			String [] str = line.split("\t");
			String [] argv;
			switch(str.length){
				case 1: // If you don't use the tab, it means that it's a Location
					argv = str[0].split("/");
					currLocation = new LocationThing(argv[0], argv[1], argv[2], this);
					this.locs.put(currLocation.getName(), currLocation);
					break;
				case 2: // If you use the tab once, it means that it's a Bin
					argv = str[1].split("/");
					currBin = new BinThing(argv[0], argv[1], argv[2], Float.parseFloat(argv[3]), this);
					this.bins.put(currBin.getName(), currBin);
					currLocation.addBin(currBin);
					break;
				case 3: // If you use the tab twice, it means that it's an Item
					argv = str[2].split("/");
					currItem = new ItemThing(argv[0], argv[1], argv[2], Float.parseFloat(argv[3]), this);
					this.items.put(currItem.getName(), currItem);
					currBin.addItem(currItem);
					break;
				default:
					throw new Exception("Error when parsing config file");
			}
		}
	}
	
	/**
	 * This function binds all things to the client
	 * @throws Exception 
	 */
	public void bindThings() throws Exception{
		for (LocationThing loc:this.locs.values()) this.bindThing(loc);
		for (BinThing bin:this.bins.values()) this.bindThing(bin);
		for (ItemThing item:this.items.values()) this.bindThing(item);
	}
	
	/**
	 * This function makes Raspberry Pi to communicate with Arduino board and obtain all
	 * values of the item.
	 * @param item The item binded with the Arduino board
	 */
	private void scan(ItemThing item){
		float distanceToNextItem = this.xbee.getDistance();
		float distanceToDeepest = item.getBin().getDepth();
		float itemSize = item.getSize();
		// (Scanner)<====================[][][]|
		//          | distanceToNextItem |
		//			|	distanceToDeepest      |
		//                               |delta|
		
		float delta = distanceToDeepest - distanceToNextItem;
		int amount = (int) (delta / itemSize);
		item.setAmount(amount);
	}
	
	/**
	 * This function sets the communication XBee device.
	 * @param xbee
	 */
	public void setXBee(Xbee xbee){
		this.xbee = xbee;
	}	
	public Xbee getXBee(){return this.xbee;}
	
	public HashMap<String,LocationThing> getLocations(){return this.locs;}
	public HashMap<String,BinThing> getBins(){return this.bins;}
	public HashMap<String,ItemThing> getItems(){return this.items;}
}
