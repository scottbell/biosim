package biosim.client.crew.gui;

import java.awt.*;
import biosim.idl.crew.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the crew and water
 *
 * @author    Scott Bell
 */
public class CrewWaterChartPanel extends GraphPanel
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
		                  "Water Consumed/Produced",  // chart title
		                  "",              // domain axis label
		                  "Liters",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, 2.0);
		myPlot.setSeriesPaint(new Paint[] { Color.BLUE, Color.GRAY, Color.YELLOW });
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myCrewGroup.getPotableWaterConsumed()},
			                    {myCrewGroup.getGreyWaterProduced()},
			                    {myCrewGroup.getDirtyWaterProduced()} 
			};
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"Potable Water Consumed", "Grey Water Produced", "Dirty Water Produced"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			myDataset.setValue(0, "", new Float(myCrewGroup.getPotableWaterConsumed()));
			myDataset.setValue(1, "", new Float(myCrewGroup.getGreyWaterProduced()));
			myDataset.setValue(2, "", new Float(myCrewGroup.getDirtyWaterProduced()));
		}
	}
}
