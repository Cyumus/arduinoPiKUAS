 
import java.util.concurrent.TimeUnit;

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
	
    private static final String PORT = "/dev/ttyUSB0";
    private static final int BAUD_RATE = 9600;
    private static MyDataReceiveListener listener;

    /**
     * Application main method.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
    	listener = new MyDataReceiveListener();
    	
		init();
	}
    
    public static void init() {
    	
            XBeeDevice myDevice = new XBeeDevice(PORT, BAUD_RATE);

            try {
                    myDevice.open();

                    myDevice.addDataListener(listener);

                    System.out.println("\n>> Waiting for data...");
                    

            } catch (XBeeException e) {
                    e.printStackTrace();
                    System.exit(1);
            }
            
            //DEBUG
            while(true) {
            	System.out.println(getDistance());
            	try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
    }
    
    public static float getDistance() {
    	return listener.getNewestDistance();
    }
}
