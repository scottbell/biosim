package biosim.client.crew.gui;

import java.awt.*;
import javax.swing.*;
import biosim.idl.crew.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the crew and intensity levels
 *
 * @author    Scott Bell
 */
public class CrewIntensityChartPanel extends GraphPanel
{
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;
	private CategoryPlot myPlot;
	private JFreeChart myChart;
	private CrewPerson[] myCrewPeople;
	private CrewGroup myCrewGroup;

	protected void buildGui(){
		myCrewGroup = (CrewGroup)(BioHolder.getBioModule(BioHolder.crewName));
		myCrewPeople = myCrewGroup.getCrewPeople();
		if (myCrewGroup.getCrewPeople().length == 0){
			setLayout(new BorderLayout());
			JPanel noCrewPanel = new JPanel();
			noCrewPanel.setLayout(new BorderLayout());
			JLabel noCrewLabel = new JLabel("No crew to display");
			noCrewPanel.add(noCrewLabel, BorderLayout.CENTER);
			add(noCrewPanel, BorderLayout.CENTER);
		}
		else{
			buildRealGui();
		}
		
	}
	
	private void buildRealGui(){
		removeAll();
		createGraph();
		super.buildGui();
		repaint();
		revalidate();
	}

	protected void createGraph(){
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
		System.out.println("CrewIntensityChartPanel: refreshing");
		if (myChartPanel == null){
			System.out.println("CrewIntensityChartPanel: myChartPanel is null");
			myCrewPeople = myCrewGroup.getCrewPeople();
			if (myCrewPeople.length > 0){
				System.out.println("Found crew!");
				buildRealGui();
			}
		}
		else{
			myCrewPeople = myCrewGroup.getCrewPeople();
			for (int i = 0; i < myCrewPeople.length; i ++){
				myDataset.setValue(i, "", new Float(myCrewPeople[i].getCurrentActivity().getActivityIntensity()));
			}
		}
	}
}
