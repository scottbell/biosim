package biosim.client.framework.gui;

import javax.swing.Timer;
import java.awt.event.*;


/**
 *
 * @author    Scott Bell
 */

public class BioSimulator
{
	private static Timer myTimer;
	private static int myDelay = 500;
	
	public static void addDisplayListener(ActionListener listener){
		if (myTimer == null)
			myTimer = new Timer(myDelay, listenter);
		else
			myTimer.addActionListener(listener);
	}
	
	public static void removeDisplayListener(ActionListener listener){
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
