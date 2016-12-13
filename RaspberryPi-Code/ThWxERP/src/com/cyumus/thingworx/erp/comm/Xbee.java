package com.cyumus.thingworx.erp.comm;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
 
public class Xbee {
	
	/**
	 * This is just an example main class,
	 * which calls MyDataReceiveListener one time
	 * at second.
	 * 
	 * Notice, that you have to implement a XBeeDevice class
	 * at class, which calls the listener.
	 * 
	 * Constants PORT and BAUD_RATE are already correct to
	 * RPi, so copy and paste the init method to your project
	 * and call listener.getDistance() to find out the last received
	 * distance.
	 */
	
    private String PORT;
    private int BAUD_RATE;
    private MyDataReceiveListener listener;
    private static Xbee xbee;
    private Xbee(){
    	this.PORT = "/dev/ttyUSB0";
    	this.BAUD_RATE = 9600;
    	this.listener = new MyDataReceiveListener();
    }
    
    public static Xbee getSingleton(){if(xbee==null) xbee = new Xbee(); return xbee;} 
    
    public void init() {    	
        XBeeDevice myDevice = new XBeeDevice(this.PORT, this.BAUD_RATE);
        try {
        	myDevice.open();
			myDevice.addDataListener(this.listener);
        } catch (XBeeException e) {
        	e.printStackTrace();
        }
    }
    
    public float getDistance() {
    	return this.listener.getNewestDistance();
    }
}
