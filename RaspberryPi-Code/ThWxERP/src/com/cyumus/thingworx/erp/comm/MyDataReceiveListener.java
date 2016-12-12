package com.cyumus.thingworx.erp.comm;
/**

 * Copyright (c) 2014-2015 Digi International Inc.,
 * All rights not expressly granted are reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * Digi International Inc. 11001 Bren Road East, Minnetonka, MN 55343
 * =======================================================================
 */

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;

/**
 * This class has the XBee-listener functionality.
 * Please configure your project like this:
 * http://docs.digi.com/display/XBJLIB/Configure+the+project
 * 
 * 
 * 
 */
public class MyDataReceiveListener implements IDataReceiveListener {
	/*
	 * (non-Javadoc)
	 * @see com.digi.xbee.api.listeners.IDataReceiveListener#dataReceived(com.digi.xbee.api.models.XBeeMessage)
	 */
	private float newestDistance = 0;
	
	private void setNewestDistance(float val) {
		newestDistance = val;
	}
	
	public float getNewestDistance() {
		return newestDistance;
	}
	
	@Override
	public void dataReceived(XBeeMessage xbeeMessage) {
		setNewestDistance(Float.parseFloat(new String(xbeeMessage.getData())));
	}
}
