package biosim.client.crew.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import biosim.idl.crew.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the Crew and food
 *
 * @author    Scott Bell
 */
public class CrewFoodChartPanel extends JPanel
{
	private CrewGroup myCrewGroup;
	private DefaultCategoryDataset myDataset;
	private JButton refreshButton;
	private JButton trackingButton;
	private ChartPanel myChartPanel;
	private RefreshAction myRefreshAction;
	private TrackingAction myTrackingAction;
	private Timer refreshTimer;
	private final static int TIMER_DELAY=500;
	private boolean trackingWanted = false;
	private ValueAxis rangeAxis;
	private CategoryPlot myPlot;
	private JFreeChart myChart;

	/**
	 * Default constructor.
	 */
	public CrewFoodChartPanel() {
		myCrewGroup = (CrewGroup)(BioHolder.getBioModule(BioHolder.crewName));
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
		                  "Food Consumption",  // chart title
		                  "",              // domain axis label
		                  "Kilograms",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, .5);
		myPlot.setSeriesPaint(new Paint[] { new Color(10,204,102)});
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myCrewGroup.getFoodConsumed()} };
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"Food"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			myDataset.setValue(0, "", new Float(myCrewGroup.getFoodConsumed()));
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
