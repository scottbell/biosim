package biosim.client.simulation.water.gui;

import java.awt.*;
import biosim.client.framework.gui.*;
import biosim.client.util.*;
import biosim.idl.simulation.water.*;
import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.*;

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

	protected void createGraph(){
		// create the chart...
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		myPotableWaterStore = (PotableWaterStore)(myBioHolder.thePotableWaterStores.get(0));
		myDirtyWaterStore = (DirtyWaterStore)(myBioHolder.theDirtyWaterStores.get(0));
		myGreyWaterStore = (GreyWaterStore)(myBioHolder.theGreyWaterStores.get(0));
		refresh();
		JFreeChart myChart = ChartFactory.createBarChart3D(
		                  "Water Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Water Level (L)",                 // range axis label
		                  myDataset, PlotOrientation.VERTICAL,                // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myPotableWaterStore.getCapacity());
		CategoryItemRenderer renderer = myPlot.getRenderer();
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
			float greyDirtyMax = Math.max(myGreyWaterStore.getCapacity(), myDirtyWaterStore.getCapacity());
			float capacity = Math.max(greyDirtyMax, myPotableWaterStore.getCapacity());
			if ((rangeAxis.getRange().getUpperBound() != capacity) && (capacity > 0)){
				rangeAxis.setRange(0.0, capacity);
				myChartPanel.repaint();
			}
			myDataset.setValue(new Float(myPotableWaterStore.getLevel()), "Potable Water", "");
			myDataset.setValue(new Float(myGreyWaterStore.getLevel()), "Grey Water", "");
			myDataset.setValue(new Float(myDirtyWaterStore.getLevel()), "Dirty Water", "");
		}
	}
}
