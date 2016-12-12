package com.cyumus.thingworx.erp.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.cyumus.thingworx.erp.RaspberryPiClient;
import com.cyumus.thingworx.erp.comm.Xbee;
import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.common.SecurityClaims;

public class RaspberryPiFrame {
	private RaspberryPiClient client;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RaspberryPiFrame window = new RaspberryPiFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public RaspberryPiFrame() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		// TODO Implement all this stuff
		// We create the configuration stuff and blablabla~
		ClientConfigurator config = new ClientConfigurator();
		// Websocket~
		config.setUri("ws://localhost:80/Thingworx/WS");
		// Reconnect every 15 seconds...
		config.setReconnectInterval(15);
		
		// Using of credentials...
		SecurityClaims claims = SecurityClaims.fromCredentials("Arduino", "1234");
		config.setSecurityClaims(claims);
		
		// The name of the Gateway
		config.setName("RaspberryPiGateway");
		// It's a SDK Type
		config.setAsSDKType();
		
		// We ignore all these bothering SSL errors. 
		config.ignoreSSLErrors(true);
		
		// The delay of the Thing.
		int delay = 1000;
		
		// We create  client that will use the Sensor.
		// In this case, this is a virtual representation of the Raspberry Pi.
		RaspberryPiClient client = new RaspberryPiClient(config);
		
		// We create all things from the config file.
		client.getThingsFromConfig();
		
		// We bind all things
		client.bindThings();
		
		// We set the communication with the XBee
		client.setXBee(Xbee.getSingleton());
		
		// We initialize the communication with the XBee
		client.getXBee().init();
		
		try {
			// We say to the client to start working.
			client.start();
		}
		catch(Exception eStart) {
			System.out.println("Initial Start Failed : " + eStart.getMessage());
		}
		
		// And we start the scan process
		client.startScanProcess(delay);
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
