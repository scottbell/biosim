package biosim.client.environment.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import biosim.idl.environment.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the Environment
 *
 * @author    Scott Bell
 */
public class EnvironmentPieChartPanel extends JPanel
{
	private SimEnvironment mySimEnvironment;
	private JButton refreshButton;
	private JButton trackingButton;
	private ChartPanel myChartPanel;
	private RefreshAction myRefreshAction;
	private TrackingAction myTrackingAction;
	private Timer refreshTimer;
	private final static int TIMER_DELAY=500;
	private boolean trackingWanted = false;
	private Pie3DPlot myPlot;
	private JFreeChart myChart;
	private DefaultPieDataset myDataset;
	
	private String O2Category = "O2";
	private String CO2Category = "CO2";
	private String otherCategory = "Other";

	/**
	 * Default constructor.
	 */
	public EnvironmentPieChartPanel() {
		mySimEnvironment = (SimEnvironment)(BioHolder.getBioModule(BioHolder.simEnvironmentName));
		createGraph();
		myRefreshAction = new RefreshAction("Refresh");
		refreshButton = new JButton(myRefreshAction);
		myTrackingAction = new TrackingAction("Start Tracking");
		trackingButton = new JButton(myTrackingAction);
		refreshTimer = new Timer (TIMER_DELAY, myRefreshAction);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		
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
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		gridbag.setConstraints(myChartPanel, c);
		add(myChartPanel);
	}

	private void createGraph(){
		// create the chart...
		refresh();
		myChart = ChartFactory.createPie3DChart(
		                  "Environment Gas Composition",  // chart title
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = (Pie3DPlot)(myChart.getPlot());
		myPlot.setSeriesPaint(new Paint[] { Color.BLUE, Color.GREEN, Color.RED});
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(250, 250));
	}

	public void refresh() {
		if (myDataset == null)
			myDataset = new DefaultPieDataset();
		myDataset.setValue(O2Category, new Float(mySimEnvironment.getO2Level()));
		myDataset.setValue(CO2Category, new Float(mySimEnvironment.getO2Level()));
		myDataset.setValue(otherCategory, new Float(mySimEnvironment.getOtherLevel()));
	}

	public void visibilityChange(boolean nowVisible){
		if (nowVisible && trackingWanted){
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

	/**
	* Action that displays the power panel in an internal frame on the desktop.
	*/
	private class TrackingAction extends AbstractAction{
		public TrackingAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			if (refreshTimer.isRunning()){
				refreshTimer.stop();
				trackingButton.setText("Start Tracking");
				trackingWanted = false;
			}
			else{
				refreshTimer.start();
				trackingButton.setText("Stop Tracking");
				trackingWanted = true;
			}
		}
	}
}
