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
 * This is the JPanel that displays a chart about the crew and intensity levels
 *
 * @author    Scott Bell
 */
public class CrewIntensityChartPanel extends JPanel
{
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
	private CrewPerson[] myCrewPeople;
	private CrewGroup myCrewGroup;

	/**
	 * Default constructor.
	 */
	public CrewIntensityChartPanel() {
		myCrewGroup = (CrewGroup)(BioHolder.getBioModule(BioHolder.crewName));
		myCrewPeople = myCrewGroup.getCrewPeople();
		myRefreshAction = new RefreshAction("Refresh");
		refreshTimer = new Timer (TIMER_DELAY, myRefreshAction);
		if (myCrewPeople.length == 0){
			setLayout(new BorderLayout());
			JPanel noCrewPanel = new JPanel();
			noCrewPanel.setLayout(new BorderLayout());
			JLabel noCrewLabel = new JLabel("No crew to display");
			noCrewPanel.add(noCrewLabel, BorderLayout.CENTER);
			add(noCrewPanel, BorderLayout.CENTER);
			refreshTimer.start();
		}
		else
			buildGUI();
	}

	private void buildGUI(){
		removeAll();
		createGraph();
		refreshButton = new JButton(myRefreshAction);
		myTrackingAction = new TrackingAction("Start Tracking");
		trackingButton = new JButton(myTrackingAction);
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
		repaint();
		revalidate();
	}

	private void createGraph(){
		// create the chart...
		double[][] data = new double[myCrewPeople.length][1];
		String[] theSeries = new String [myCrewPeople.length];
		for (int i = 0; i < myCrewPeople.length; i ++){
			data[i][0] = myCrewPeople[i].getCurrentActivity().getActivityIntensity();
			theSeries[i] = myCrewPeople[i].getName();
		}
		myDataset = new DefaultCategoryDataset(data);
		String[] theCategory = {""};
		myDataset.setSeriesNames(theSeries);
		myDataset.setCategories(theCategory);
		myChart = ChartFactory.createVerticalBarChart3D(
		                  "Crew Activity Intensities",  // chart title
		                  "",              // domain axis label
		                  "Intensity",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, 5.0);
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myChartPanel == null){
			myCrewPeople = myCrewGroup.getCrewPeople();
			if (myCrewPeople.length > 0){
				refreshTimer.stop();
				buildGUI();
			}
		}
		else{
			myCrewPeople = myCrewGroup.getCrewPeople();
			for (int i = 0; i < myCrewPeople.length; i ++){
				myDataset.setValue(i, "", new Float(myCrewPeople[i].getCurrentActivity().getActivityIntensity()));
			}
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
