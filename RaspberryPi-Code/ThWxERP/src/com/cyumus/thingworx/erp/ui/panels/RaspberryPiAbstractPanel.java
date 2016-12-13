package com.cyumus.thingworx.erp.ui.panels;

import java.util.HashMap;

import javax.swing.JPanel;

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
}
