package com.cyumus.thingworx.erp.ui.panels;

import java.util.HashMap;

import javax.swing.JPanel;

import com.cyumus.thingworx.erp.things.ItemThing;

public class RaspberryPiAbstractPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HashMap<String, String> getText(){
		return new HashMap<String,String>();
	}
	public void setProgress(int i){}
	public void write(int i){}
	public void load() throws Exception{}
	public HashMap<String, ItemThing> getItems(){return null;}
	public int getTime(){return 0;}
}
