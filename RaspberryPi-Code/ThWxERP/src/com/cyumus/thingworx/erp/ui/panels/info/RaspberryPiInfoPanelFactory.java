package com.cyumus.thingworx.erp.ui.panels.info;

import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel.BinThingNode;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel.ItemThingNode;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel.LocationThingNode;

public abstract class RaspberryPiInfoPanelFactory {
	public static RaspberryPiDefaultInfoPanel newPanel(Object thing){
		if (thing instanceof ItemThingNode) 
			return new RaspberryPiItemInfoPanel((ItemThingNode) thing);
		else if (thing instanceof BinThingNode) 
			return new RaspberryPiBinInfoPanel((BinThingNode) thing);
		else if (thing instanceof LocationThingNode)
			return new RaspberryPiLocationInfoPanel((LocationThingNode) thing);
		else return new RaspberryPiDefaultInfoPanel();
	}
}
