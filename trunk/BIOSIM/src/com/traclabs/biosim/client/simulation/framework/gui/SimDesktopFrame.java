/**
 * SimDesktopFrame is the internal frame that holds the various GUI panels.
 * These are places inside the SimDesktop.
 *
 * @author    Scott Bell
 */
package biosim.client.simulation.framework.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class SimDesktopFrame extends javax.swing.JInternalFrame
{
	//A reference to the desktop this frame is added to.
	SimDesktop myDesktop;
	java.util.List bioPanels;
	
	/**
	* Creates frame's GUI with minimizable, resizable, maxizable, and closeable properties.
	*/
	public SimDesktopFrame(String pTitle, SimDesktop pDesktop){
		super(pTitle, true, true, true, true);
		myDesktop = pDesktop;
		bioPanels = new Vector();
		buildGui();
	}
	
	/**
	* Disables the close operation (we want to hide this frame, not close it)
	*/
	private void buildGui()
	{
		setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
		DesktopFrameCloseListener myFCL = new DesktopFrameCloseListener();
		this.addInternalFrameListener(myFCL);
	}
	
	public void add(UpdatablePanel pPanel){
		bioPanels.add(pPanel);
		getContentPane().add(pPanel, BorderLayout.CENTER);
	}
	
	/**
	* Hides the frame
	*/
	protected void frameClosing()
	{
		setVisible(false);		 // hide the Frame
	}
	
	public void setVisible(boolean b){
		if (b == isVisible())
			return;
		super.setVisible(b);
		if (bioPanels == null)
			return;
		for (Iterator iter = bioPanels.iterator(); iter.hasNext();){
			UpdatablePanel currentPanel = (UpdatablePanel)(iter.next());
			currentPanel.visibilityChange(b);
		}
	}
	
	/**
	* Class to catch closing event.
	*/
	private class DesktopFrameCloseListener extends InternalFrameAdapter
	{
		 public void internalFrameClosing(InternalFrameEvent e) 
		{
			frameClosing();
		}
	}

}



