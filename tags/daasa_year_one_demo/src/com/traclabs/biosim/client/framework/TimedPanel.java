package com.traclabs.biosim.client.framework;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 * 
 * @author Scott Bell
 */
public abstract class TimedPanel extends UpdatablePanel {
    protected RefreshAction myRefreshAction;

    private Timer myRefreshTimer;

    private final static int DEFAULT_TIMER_DELAY = 300;

    protected boolean tracking = true;

    /**
     * Default constructor.
     */
    public TimedPanel() {
        myRefreshAction = new RefreshAction("Refresh");
        myRefreshTimer = new Timer(DEFAULT_TIMER_DELAY, myRefreshAction);
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setDelay(int pDelay) {
        myRefreshTimer.setDelay(pDelay);
    }

    public int getDelay() {
        return myRefreshTimer.getDelay();
    }

    public void setTracking(boolean pTrackingWanted) {
        if (pTrackingWanted == tracking)
            return;
        tracking = pTrackingWanted;
        if (tracking)
            myRefreshTimer.start();
        else
            myRefreshTimer.stop();
    }

    public abstract void refresh();

    public void visibilityChange(boolean nowVisible) {
        if (nowVisible && tracking) {
            myRefreshTimer.start();
        } else {
            myRefreshTimer.stop();
        }
    }

    /**
     * Action that displays the power panel in an internal frame on the desktop.
     */
    private class RefreshAction extends AbstractAction {
        public RefreshAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            refresh();
        }
    }
}