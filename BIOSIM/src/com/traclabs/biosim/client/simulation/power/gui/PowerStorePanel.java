package biosim.client.simulation.power.gui;

import java.awt.*;
import biosim.idl.simulation.power.*;
import biosim.client.framework.gui.*;
import biosim.client.util.*;
import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;

/**
 * This is the JPanel that displays a chart about the Power Store
 *
 * @author    Scott Bell
 */
public class PowerStorePanel extends GraphPanel
{
	private PowerStore myPowerStore;
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;
	private JFreeChart myChart;

	protected void createGraph(){
		// create the chart...
		myPowerStore = (PowerStore)(BioHolder.getBioModule(BioHolder.powerStoreName));
		refresh();
		myChart = ChartFactory.createBarChart3D(
		                  "Power Store Level",  // chart title
		                  "",              // domain axis label
		                  "Power Level (W)",                 // range axis label
		                  myDataset, PlotOrientation.VERTICAL,                // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myPowerStore.getCapacity());
		CategoryItemRenderer renderer = myPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.ORANGE);
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(200);
		myChartPanel.setMinimumDrawWidth(230);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {} };
			myDataset = new DefaultCategoryDataset();
			myDataset.addValue(myPowerStore.getLevel(),"Power Store", "");
		}
		else{
			float capacity = myPowerStore.getCapacity();
			if ((rangeAxis.getRange().getUpperBound() != capacity) && (capacity > 0)){
				rangeAxis.setRange(0.0, capacity);
				myChartPanel.repaint();
			}
			myDataset.setValue(new Float(myPowerStore.getLevel()), "Power Store", "");
		}
	}
}
