/**
 * SimDesktopFrame is the internal frame that holds the various GUI panels.
 * These are places inside the SimDesktop.
 *
 * @author    Scott Bell
 */
package biosim.client.framework.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class SimDesktopFrame extends javax.swing.JInternalFrame
{
	//A reference to the desktop this frame is added to.
	SimDesktop myDesktop;
	Vector bioTabPanels;
	
	/**
	* Creates frame's GUI with minimizable, resizable, maxizable, and closeable properties.
	*/
	public SimDesktopFrame(String pTitle, SimDesktop pDesktop){
		super(pTitle, true, true, true, true);
		myDesktop = pDesktop;
		bioTabPanels = new Vector();
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
	
	public void addBioTabbedPanel(BioTabbedPanel newPanel){
		getContentPane().add(newPanel, BorderLayout.CENTER);
		bioTabPanels.add(newPanel);
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
		if (bioTabPanels == null)
			return;
		for (Enumeration e = bioTabPanels.elements(); e.hasMoreElements();){
			BioTabbedPanel currentPanel = (BioTabbedPanel)(e.nextElement());
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



