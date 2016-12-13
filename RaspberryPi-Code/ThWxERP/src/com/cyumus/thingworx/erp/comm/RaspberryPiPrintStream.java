package com.cyumus.thingworx.erp.comm;

import java.io.OutputStream;
import java.io.PrintStream;

public class RaspberryPiPrintStream extends PrintStream {
	public RaspberryPiPrintStream(OutputStream out) {
		super(out);
	}

}
