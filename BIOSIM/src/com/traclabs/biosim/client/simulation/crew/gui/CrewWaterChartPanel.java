package biosim.client.simulation.crew.gui;

import java.awt.*;
import biosim.idl.simulation.crew.*;
import biosim.client.framework.gui.*;
import biosim.client.util.*;
import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;

/**
 * This is the JPanel that displays a chart about the crew and water
 *
 * @author    Scott Bell
 */
public class CrewWaterChartPanel extends GraphPanel
{
	private CrewGroup myCrewGroup;
	private DefaultCategoryDataset myDataset;

	protected void createGraph(){
		// create the chart...
		myCrewGroup = (CrewGroup)(BioHolderInitializer.getBioHolder().theCrewGroups.get(0));
		refresh();
		JFreeChart myChart = ChartFactory.createBarChart3D(
		                  "Water Consumed/Produced",  // chart title
		                  "",              // domain axis label
		                  "Liters",                 // range axis label
		                  myDataset, PlotOrientation.VERTICAL,                // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		ValueAxis rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, 2.0);
		CategoryItemRenderer renderer = myPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesPaint(1, Color.GRAY);
		renderer.setSeriesPaint(2, Color.YELLOW);
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			myDataset = new DefaultCategoryDataset();
			float myPotableWaterConsumed = myCrewGroup.getPotableWaterConsumed();
			float myGreyWaterProduced = myCrewGroup.getGreyWaterProduced();
			float myDirtyWaterProduced = myCrewGroup.getDirtyWaterProduced();
			String series1 = "Potable Water Consumed";
			String series2 = "Grey Water Produced";
			String series3 = "Dirty Water Produced";
			String category = "";
			myDataset.addValue(myPotableWaterConsumed,series1, category);
			myDataset.addValue(myGreyWaterProduced,series2, category);
			myDataset.addValue(myDirtyWaterProduced,series3, category);
		}
		else{
			myDataset.setValue(new Float(myCrewGroup.getPotableWaterConsumed()), "Potable Water Consumed", "");
			myDataset.setValue(new Float(myCrewGroup.getGreyWaterProduced()), "Grey Water Produced", "");
			myDataset.setValue(new Float(myCrewGroup.getDirtyWaterProduced()), "Dirty Water Produced", "");
		}
	}
}