/**
 * A simple driver that creates a SimDesktop, sets the size and visibility, then lets it run.
 *
 * @author    Scott Bell
 */	
package biosim.client.framework;

import biosim.client.framework.gui.*;
import javax.swing.*;

public class TestDriver
{
	/**
	* The method to start the BIOSIM client.
	*/
	public static void main(String args[]) throws java.lang.InterruptedException
	{
		SimDesktop newDesktop = new SimDesktop();
		newDesktop.setSize(1024, 768);
		newDesktop.setVisible(true);
	}
}

