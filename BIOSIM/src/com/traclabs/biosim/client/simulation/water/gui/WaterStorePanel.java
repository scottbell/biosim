package biosim.client.water.gui;

import java.awt.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import biosim.idl.water.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

/**
 * This is the JPanel that displays a chart about the WaterStores
 *
 * @author    Scott Bell
 */
public class WaterStorePanel extends GraphPanel
{
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;
	private CategoryPlot myPlot;
	private JFreeChart myChart;

	protected void createGraph(){
		// create the chart...
		myPotableWaterStore = (PotableWaterStore)(BioHolder.getBioModule(BioHolder.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(BioHolder.getBioModule(BioHolder.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(BioHolder.getBioModule(BioHolder.greyWaterStoreName));
		refresh();
		myChart = ChartFactory.createVerticalBarChart3D(
		                  "Water Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Water Level (L)",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myPotableWaterStore.getCapacity());
		myPlot.setSeriesPaint(new Paint[] { Color.BLUE, Color.GRAY, Color.YELLOW });
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(300);
		myChartPanel.setMinimumDrawWidth(300);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myPotableWaterStore.getLevel()}, {myGreyWaterStore.getLevel()}, {myDirtyWaterStore.getLevel()}};
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"Potable Water", "Grey Water", "Dirty Water"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			if (rangeAxis.getRange().getUpperBound() != myPotableWaterStore.getCapacity()){
				rangeAxis.setRange(0.0, myPotableWaterStore.getCapacity());
				myChartPanel.repaint();
			}
			myDataset.setValue(0, "", new Float(myPotableWaterStore.getLevel()));
			myDataset.setValue(1, "", new Float(myGreyWaterStore.getLevel()));
			myDataset.setValue(2, "", new Float(myDirtyWaterStore.getLevel()));
		}
	}
}
