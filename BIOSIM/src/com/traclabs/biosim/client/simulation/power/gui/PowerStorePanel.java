package biosim.client.power.gui;

import java.awt.*;
import biosim.idl.power.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.axis.*;
import com.jrefinery.chart.plot.*;
import com.jrefinery.chart.renderer.*;

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
		myChart = ChartFactory.createVerticalBarChart3D(
		                  "Power Store Level",  // chart title
		                  "",              // domain axis label
		                  "Power Level (W)",                 // range axis label
		                  myDataset,                 // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myPowerStore.getCapacity());
		Renderer renderer = myPlot.getRenderer();
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
				rangeAxis.setRange(0.0, myPowerStore.getCapacity());
				myChartPanel.repaint();
			}
			myDataset.setValue(new Float(myPowerStore.getLevel()), "Power Store", "");
		}
	}
}
