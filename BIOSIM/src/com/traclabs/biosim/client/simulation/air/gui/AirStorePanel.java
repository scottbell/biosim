package biosim.client.air.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.air.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the Air Stores
 *
 * @author    Scott Bell
 */
public class AirStorePanel extends JPanel
{
	private O2Store myO2Store;
	private CO2Store myCO2Store;
	private DefaultCategoryDataset myDataset;
	private JButton refreshButton;
	private JButton trackingButton;
	private ChartPanel myChartPanel;
	private RefreshAction myRefreshAction;
	private TrackingAction myTrackingAction;
	private Timer refreshTimer;
	private final static int TIMER_DELAY=500;
	private boolean trackingWanted = false;
	ValueAxis rangeAxis;
	CategoryPlot myPlot;
	JFreeChart myChart;

	/**
	 * Default constructor.
	 */
	public AirStorePanel() {
		myO2Store = (O2Store)(BioHolder.getBioModule(BioHolder.O2StoreName));
		myCO2Store = (CO2Store)(BioHolder.getBioModule(BioHolder.CO2StoreName));
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
		myChart = ChartFactory.createVerticalBarChart3D(
		                  "Air Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Air Level",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myO2Store.getCapacity());
		myPlot.setSeriesPaint(new Paint[] { Color.BLUE, Color.ORANGE});
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(220, 250));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myO2Store.getLevel()}, {myCO2Store.getLevel()} };
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"CO2", "O2"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			if (rangeAxis.getRange().getUpperBound() != myO2Store.getCapacity()){
				rangeAxis.setRange(0.0, myO2Store.getCapacity());
				myChartPanel.repaint();
			}
			myDataset.setValue(0, "", new Float(myO2Store.getLevel()));
			myDataset.setValue(1, "", new Float(myCO2Store.getLevel()));
		}
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
