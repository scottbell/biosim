package biosim.client.framework.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 
 * @author    Scott Bell
 */
public abstract class TimedPanel extends UpdatablePanel
{
	protected RefreshAction myRefreshAction;
	private Timer refreshTimer;
	private final static int TIMER_DELAY=500;
	protected boolean tracking = false; 

	/**
	 * Default constructor.
	 */
	public TimedPanel() {
		myRefreshAction = new RefreshAction("Refresh");
		refreshTimer = new Timer (TIMER_DELAY, myRefreshAction);
	}
	
	public boolean isTracking(){
		return tracking;
	}
	
	public void setTracking(boolean pTrackingWanted){
		if (pTrackingWanted == tracking)
			return;
		tracking = pTrackingWanted;
		if (tracking)
			refreshTimer.start();
		else
			refreshTimer.stop();
	}
	
	public abstract void refresh();

	public void visibilityChange(boolean nowVisible){
		if (nowVisible && tracking){
			refreshTimer.start();
		}
		else{
			refreshTimer.stop();
		}
	}

	/**
	* Action that displays the power panel in an internal frame on the desktop.
	*/
	private class RefreshAction extends AbstractAction{
		public RefreshAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			refresh();
		}
	}
}
