package biosim.client.gui;

import java.awt.*;
import javax.swing.*;

public class BaseJFrame extends javax.swing.JFrame
{
	private boolean showCloseDialog = true;
	
	public BaseJFrame(){
		buildGui();
	}

	public BaseJFrame( String newTitle )
	{
		super( newTitle );
		buildGui();
	}
	
	public BaseJFrame( String newTitle, boolean show_close_dialog )
	{
		super( newTitle );
		showCloseDialog = showCloseDialog;
		buildGui();
	}

	private void buildGui()
	{
		setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
		WindowCloseListener myWCL = new WindowCloseListener();
		this.addWindowListener(myWCL);
	}

	protected void frameClosing()
	{
		if( !showCloseDialog || JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
		                this, "Do you really want to quit?",
		                "Quit Dialog", JOptionPane.YES_NO_OPTION))
		{
			setVisible(false);		 // hide the Frame
			dispose();	            // free the system resources
			frameExiting();
			System.exit(0);         // close the application
		}
	}
	
	protected void frameExiting(){
	}
	
	private class WindowCloseListener extends java.awt.event.WindowAdapter
	{
		public void windowClosing(java.awt.event.WindowEvent event)
		{
			frameClosing();
		}
	}

}



