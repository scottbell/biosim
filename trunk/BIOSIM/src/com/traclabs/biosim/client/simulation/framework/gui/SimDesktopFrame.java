package biosim.client.framework.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class SimDesktopFrame extends javax.swing.JInternalFrame
{
	SimDesktop myDesktop;
	
	public SimDesktopFrame(String pTitle, SimDesktop pDesktop){
		super(pTitle, true, true, true, true);
		myDesktop = pDesktop;
		buildGui();
	}

	private void buildGui()
	{
		setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
		DesktopFrameCloseListener myFCL = new DesktopFrameCloseListener();
		this.addInternalFrameListener(myFCL);
	}

	protected void frameClosing()
	{
		setVisible(false);		 // hide the Frame
	}
	
	private class DesktopFrameCloseListener extends InternalFrameAdapter
	{
		 public void internalFrameClosing(InternalFrameEvent e) 
		{
			frameClosing();
		}
	}

}



