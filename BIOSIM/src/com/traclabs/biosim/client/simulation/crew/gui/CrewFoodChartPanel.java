package biosim.client.crew.gui;

import java.awt.*;
import biosim.idl.crew.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the Crew and food
 *
 * @author    Scott Bell
 */
public class CrewFoodChartPanel extends GraphPanel
{
	private CrewGroup myCrewGroup;
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;
	private CategoryPlot myPlot;
	private JFreeChart myChart;

	protected void createGraph(){
		// create the chart...
		myCrewGroup = (CrewGroup)(BioHolder.getBioModule(BioHolder.crewName));
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
}
