package biosim.client.water.gui;

import java.awt.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import biosim.idl.simulation.water.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.axis.*;
import com.jrefinery.chart.plot.*;
import com.jrefinery.chart.renderer.*;

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

	protected void createGraph(){
		// create the chart...
		myPotableWaterStore = (PotableWaterStore)(BioHolder.getBioModule(BioHolder.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(BioHolder.getBioModule(BioHolder.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(BioHolder.getBioModule(BioHolder.greyWaterStoreName));
		refresh();
		JFreeChart myChart = ChartFactory.createVerticalBarChart3D(
		                  "Water Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Water Level (L)",                 // range axis label
		                  myDataset,                 // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		ValueAxis rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myPotableWaterStore.getCapacity());
		Renderer renderer = myPlot.getRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesPaint(1, Color.GRAY);
		renderer.setSeriesPaint(2, Color.YELLOW);
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(300);
		myChartPanel.setMinimumDrawWidth(300);
		myChartPanel.setPreferredSize(new Dimension(200, 300));
	}

	public void refresh() {
		if (myDataset == null){
			myDataset = new DefaultCategoryDataset();
			float myPotableWaterStoreLevel = myPotableWaterStore.getLevel();
			float myGreyWaterStoreLevel = myGreyWaterStore.getLevel();
			float myDirtyWaterStoreLevel = myDirtyWaterStore.getLevel();
			String series1 = "Potable Water";
			String series2 = "Grey Water";
			String series3 = "Dirty Water";
			String category = "";
			myDataset.addValue(myPotableWaterStoreLevel,series1, category);
			myDataset.addValue(myGreyWaterStoreLevel,series2, category);
			myDataset.addValue(myDirtyWaterStoreLevel,series3, category);
		}
		else{
			myDataset.setValue(new Float(myPotableWaterStore.getLevel()), "Potable Water", "");
			myDataset.setValue(new Float(myGreyWaterStore.getLevel()), "Grey Water", "");
			myDataset.setValue(new Float(myDirtyWaterStore.getLevel()), "Dirty Water", "");
		}
	}
}
