package biosim.client.framework;

import javax.swing.Timer;
import java.awt.event.*;

/**
 *
 * @author    Scott Bell
 */

public class SimTimer
{
	private static Timer myTimer;
	private static int myDelay = 500;
	
	public static void addListener(ActionListener listener){
		if (myTimer == null){
			myTimer = new Timer(myDelay, listener);
			myTimer.start();
		}
		else
			myTimer.addActionListener(listener);
	}
	
	public static void removeListener(ActionListener listener){
		if (myTimer == null)
			return;
		myTimer.removeActionListener(listener);
	}
	
	public static void setDelay(int pDelay){
		myDelay = pDelay;
	}
	
	public static int getDelay(){
		return myDelay;
	}

}
