package biosim.client.air.gui;

import biosim.idl.air.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import java.awt.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the Air Stores
 *
 * @author    Scott Bell
 */
public class AirStorePanel extends GraphPanel
{
	private O2Store myO2Store;
	private CO2Store myCO2Store;
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;
	private CategoryPlot myPlot;
	private JFreeChart myChart;

	protected void createGraph(){
		// create the chart...
		myO2Store = (O2Store)(BioHolder.getBioModule(BioHolder.O2StoreName));
		myCO2Store = (CO2Store)(BioHolder.getBioModule(BioHolder.CO2StoreName));
		refresh();
		myChart = ChartFactory.createVerticalBarChart3D(
		                  "Air Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Air Level (L)",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myO2Store.getCapacity());
		myPlot.setSeriesPaint(new Paint[] { Color.BLUE, Color.GREEN});
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(250);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myO2Store.getLevel()}, {myCO2Store.getLevel()} };
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"O2", "CO2"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			myDataset.setValue(0, "", new Float(myO2Store.getLevel()));
			myDataset.setValue(1, "", new Float(myCO2Store.getLevel()));
		}
	}

}
