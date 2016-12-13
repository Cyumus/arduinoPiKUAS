package com.cyumus.thingworx.erp.comm;

import java.io.IOException;
import java.io.OutputStream;

import com.cyumus.thingworx.erp.ui.RaspberryPiFrame;

public class RaspberryPiOutputStream extends OutputStream {
	StringBuilder buffer;
	@Override
	public void write(int bytes) throws IOException {
		try {
			RaspberryPiFrame.getSingleton().getPanel().write(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getString(){
		return buffer.toString();
	}

}
