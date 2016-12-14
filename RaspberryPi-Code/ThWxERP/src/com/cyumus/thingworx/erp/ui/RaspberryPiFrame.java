package com.cyumus.thingworx.erp.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.PrintStream;
import java.util.HashMap;

import javax.swing.JFrame;

import com.cyumus.thingworx.erp.RaspberryPiClient;
import com.cyumus.thingworx.erp.comm.RaspberryPiOutputStream;
import com.cyumus.thingworx.erp.comm.RaspberryPiPrintStream;
import com.cyumus.thingworx.erp.comm.Xbee;
import com.cyumus.thingworx.erp.tasks.TaskController;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiAbstractPanel;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxConnectPanel;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxLoggingConnectPanel;
import com.cyumus.thingworx.erp.ui.panels.RaspberryPiThingworxMainPanel;
import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.common.SecurityClaims;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RaspberryPiFrame extends JFrame implements Runnable{
	private RaspberryPiClient client;
	private static RaspberryPiFrame singleton;
	private JFrame frame;
	private RaspberryPiAbstractPanel panel;
	public static final int DEFAULT = -1;
	private volatile Status status = Status.LOGGING;
	private HashMap<String, String> args;
	private PrintStream defaultOut, customOut, defaultErr;
	private TaskController tc;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EventQueue.invokeLater(new RaspberryPiFrame());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	private RaspberryPiFrame() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("ERP System Interface");
		
		panel = new RaspberryPiThingworxConnectPanel();
		panel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(panel);
		
		args = new HashMap<String, String>();
		defaultOut = System.out;
		customOut = new RaspberryPiPrintStream(new RaspberryPiOutputStream());
		defaultErr = System.err;
	}
	public static RaspberryPiFrame getSingleton() throws Exception{if(singleton==null) singleton=new RaspberryPiFrame(); return singleton;} 

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	/*private void initialize() throws Exception {
		// TODO Implement all this stuff
		// We create the configuration stuff and blablabla~
		/*ClientConfigurator config = new ClientConfigurator();
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
		
		//System.setOut(new RaspberryPiPrintStream(new RaspberryPiOutputStream()));
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("ERP System Interface");
		
		panel = new RaspberryPiThingworxConnectPanel();
		panel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(panel);
	}*/
	
	public void update(Status status){
		setStatus(status);
		SwingUtilities.invokeLater(this);
	}
	public synchronized void setStatus(Status status){
		this.status = status;
	}
	public void setClient(RaspberryPiClient client){
		this.client = client;
	}
	public Status getStatus(){
		return this.status;
	}
	public RaspberryPiClient getClient(){
		return this.client;
	}
	public void show() {
		this.panel.setVisible(true);		
	}
	public RaspberryPiAbstractPanel getPanel(){
		return this.panel;
	}

	@Override
	public void run() {
		try{
			switch(status){
				case LOGGING: 
					RaspberryPiFrame window = RaspberryPiFrame.getSingleton();
					window.frame.setVisible(true);
				break;
				case CONNECTING:
					args.putAll(panel.getText());
					this.setNewPanel(new RaspberryPiThingworxLoggingConnectPanel());
					System.setOut(customOut);
					System.setErr(customOut);
					update(Status.CONNECTING2);
				break;
				case CONNECTING2:
					Thread.sleep(100);
					initializeConnection();
					update(Status.CONNECTED);
				break;
				case SLEEPING:
					this.wait();
				break;
				case CONNECTED:
					args.putAll(panel.getText());
					this.setNewPanel(new RaspberryPiThingworxMainPanel());
					System.setOut(customOut);
					frame.setBounds(100,100,800,600);
					System.out.println(args.get("logs"));
				break;
				case WORKING:
					
				break;
				default:
					System.exit(1);
				break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setNewPanel(RaspberryPiAbstractPanel panel){
		frame.remove(panel);
		this.panel = panel;
		frame.setContentPane(panel);
		frame.revalidate();
		frame.repaint();
	}
	private void initializeConnection() throws Exception{
		ClientConfigurator config = new ClientConfigurator();
		panel.setProgress(0);
		// Websocket~
		config.setUri("ws://"+args.get("host")+":"+args.get("port")+"/Thingworx/WS");
		panel.setProgress(5);
		// Reconnect every 15 seconds...
		config.setReconnectInterval(15);
		panel.setProgress(10);
		
		// Using of credentials...
		SecurityClaims claims = SecurityClaims.fromCredentials(args.get("uname"), args.get("password"));
		config.setSecurityClaims(claims);
		panel.setProgress(15);
		
		// The name of the Gateway
		config.setName(args.get("name"));
		panel.setProgress(20);
		// It's a SDK Type
		config.setAsSDKType();
		panel.setProgress(25);
		
		// We ignore all these bothering SSL errors. 
		config.ignoreSSLErrors(true);
		panel.setProgress(30);
		
		// We create the client with the given configuration
		RaspberryPiClient client = new RaspberryPiClient(config);
		panel.setProgress(50);
		// And we set this client to the main frame.
		this.client = client;	
		
		// We create all things from the config file.
		client.getThingsFromConfig();
		panel.setProgress(60);
		
		// We bind all things
		client.bindThings();
		panel.setProgress(70);
		
		client.setXBee(Xbee.getSingleton());
		panel.setProgress(80);
		
		// We initialize the communication with the XBee
		client.getXBee().init();
		panel.setProgress(90);
		
		try {
			// We say to the client to start working.
			client.start();
		}
		catch(Exception eStart) {
			System.out.println("Initial Start Failed : " + eStart.getMessage());
		}
		panel.setProgress(100);
		System.out.println("CONNECTED");
	}
}
