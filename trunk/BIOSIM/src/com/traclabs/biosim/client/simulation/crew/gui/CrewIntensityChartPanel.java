package biosim.client.simulation.crew.gui;

import java.awt.*;
import javax.swing.*;
import biosim.idl.simulation.crew.*;
import biosim.client.framework.gui.*;
import biosim.client.util.*;
import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;

/**
 * This is the JPanel that displays a chart about the crew and intensity levels
 *
 * @author    Scott Bell
 */
public class CrewIntensityChartPanel extends GraphPanel
{
	private DefaultCategoryDataset myDataset;
	private CrewPerson[] myCrewPeople;
	private CrewGroup myCrewGroup;

	protected void buildGui(){
		myCrewGroup = (CrewGroup)(BioHolder.getBioModule(BioHolder.crewName));
		if (myCrewGroup.getCrewSize() == 0){
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
		myCrewPeople = myCrewGroup.getCrewPeople();
		myDataset = new DefaultCategoryDataset();
		for (int i = 0; i < myCrewPeople.length; i ++){
			myDataset.addValue(myCrewPeople[i].getCurrentActivity().getActivityIntensity(),myCrewPeople[i].getName(), "");
		}
		JFreeChart myChart = ChartFactory.createBarChart3D(
		                  "Crew Activity Intensities",  // chart title
		                  "",              // domain axis label
		                  "Intensity",                 // range axis label
		                  myDataset, PlotOrientation.VERTICAL,                // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		ValueAxis rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, 5.0);
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myChartPanel == null){
			int numberOfCrew = myCrewGroup.getCrewSize();
			if (numberOfCrew > 0){
				buildRealGui();
			}
		}
		else{
			myCrewPeople = myCrewGroup.getCrewPeople();
			for (int i = 0; i < myCrewPeople.length; i ++){
				myDataset.setValue(myCrewPeople[i].getCurrentActivity().getActivityIntensity(),myCrewPeople[i].getName(), "");
			}
		}
	}
}


