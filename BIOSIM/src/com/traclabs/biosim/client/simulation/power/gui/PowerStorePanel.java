package biosim.client.power.gui;

import java.awt.*;
import biosim.idl.power.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

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
	private CategoryPlot myPlot;
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
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myPowerStore.getCapacity());
		myPlot.setSeriesPaint(new Paint[] { Color.ORANGE});
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(200);
		myChartPanel.setMinimumDrawWidth(230);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myPowerStore.getLevel()} };
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"Power Store"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			if (rangeAxis.getRange().getUpperBound() != myPowerStore.getCapacity()){
				rangeAxis.setRange(0.0, myPowerStore.getCapacity());
				myChartPanel.repaint();
			}
			myDataset.setValue(0, "", new Float(myPowerStore.getLevel()));
		}
	}
}
