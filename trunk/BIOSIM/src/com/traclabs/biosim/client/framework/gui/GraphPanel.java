package biosim.client.framework.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * 
 * @author    Scott Bell
 */
public abstract class GraphPanel extends TimedPanel
{
	private JButton refreshButton;
	private JButton trackingButton;
	protected ChartPanel myChartPanel;
	private TrackingAction myTrackingAction;

	/**
	 * Default constructor.
	 */
	public GraphPanel() {
		super();
		buildGui();
	}

	protected void buildGui(){
		createGraph();
		myTrackingAction = new TrackingAction();
		refreshButton = new JButton(myRefreshAction);
		trackingButton = new JButton(myTrackingAction);
		if (isTracking()){
			trackingButton.setText("Stop Tracking");
		}
		else{
			trackingButton.setText("Start Tracking");
		}
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		
		/*
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.weightx = 0.1;
		gridbag.setConstraints(refreshButton, c);
		add(refreshButton);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.1;
		c.weightx = 0.1;
		gridbag.setConstraints(trackingButton, c);
		add(trackingButton);
		*/
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		gridbag.setConstraints(myChartPanel, c);
		add(myChartPanel);
	}

	protected abstract void createGraph();

	/**
	* Action that displays the power panel in an internal frame on the desktop.
	*/
	private class TrackingAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			boolean newState = !(isTracking());
			setTracking(newState);
			if (isTracking()){
				trackingButton.setText("Stop Tracking");
			}
			else{
				trackingButton.setText("Start Tracking");
			}
		}
	}
}
