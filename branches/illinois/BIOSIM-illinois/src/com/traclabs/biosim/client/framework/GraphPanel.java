package com.traclabs.biosim.client.framework;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import org.jfree.chart.ChartPanel;

/**
 * 
 * @author Scott Bell
 */
public abstract class GraphPanel extends TimedPanel {

    private JButton trackingButton;

    protected ChartPanel myChartPanel;

    @SuppressWarnings("unused")
	private TrackingAction myTrackingAction;

    /**
     * Default constructor.
     */
    public GraphPanel() {
        super();
        buildGui();
    }

    public GraphPanel(String dataSourceName) {
        super();
        initializeDataSources(dataSourceName);
        buildGui();
    }

    protected void buildGui() {
        createGraph();
        myTrackingAction = new TrackingAction();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        c.weightx = 1.0;
        gridbag.setConstraints(myChartPanel, c);
        add(myChartPanel);
    }

    protected abstract void createGraph();

    protected void initializeDataSources(@SuppressWarnings("unused") String dataSourceName) {
    }

    /**
     * Action that displays the power panel in an internal frame on the desktop.
     */
    private class TrackingAction extends AbstractAction {

		public void actionPerformed(ActionEvent ae) {
            boolean newState = !(isTracking());
            setTracking(newState);
            if (isTracking()) {
                trackingButton.setText("Stop Tracking");
            } else {
                trackingButton.setText("Start Tracking");
            }
        }
    }
}