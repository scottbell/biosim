package biosim.client.food.gui;

import java.awt.*;
import biosim.idl.simulation.food.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.chart.axis.*;
import com.jrefinery.chart.plot.*;
import com.jrefinery.chart.renderer.*;

/**
 * This is the JPanel that displays a chart about the Food/Biomass Stores
 *
 * @author    Scott Bell
 */
public class FoodStorePanel extends GraphPanel
{
	private FoodStore myFoodStore;
	private BiomassStore myBiomassStore;
	private DefaultCategoryDataset myDataset;
	private ValueAxis rangeAxis;

	protected void createGraph(){
		// create the chart...
		myFoodStore = (FoodStore)(BioHolder.getBioModule(BioHolder.foodStoreName));
		myBiomassStore = (BiomassStore)(BioHolder.getBioModule(BioHolder.biomassStoreName));
		refresh();
		JFreeChart myChart = ChartFactory.createVerticalBarChart3D(
		                  "Food Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Level (kg)",                 // range axis label
		                  myDataset,                 // data
		                  true,                     // include legend
				  true,
				  false
		          );
		// add the chart to a panel...
		CategoryPlot myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myFoodStore.getCapacity());
		Renderer renderer = myPlot.getRenderer();
		renderer.setSeriesPaint(0, new Color(51,153,51));
		renderer.setSeriesPaint(1, new Color(204,204,0));
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle());
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(300);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			myDataset = new DefaultCategoryDataset();
			String series1 = "Biomass";
			String series2 = "Food";
			String category = "";
			myDataset.addValue(myBiomassStore.getLevel(),series1, category);
			myDataset.addValue(myFoodStore.getLevel(), series2, category);
		}
		else{
			float capacity = Math.max(myBiomassStore.getCapacity(), myFoodStore.getCapacity());
			if ((rangeAxis.getRange().getUpperBound() != capacity) && (capacity > 0)){
				rangeAxis.setRange(0.0, capacity);
				myChartPanel.repaint();
			}
			myDataset.setValue(new Float(myBiomassStore.getLevel()), "Biomass", "");
			myDataset.setValue(new Float(myFoodStore.getLevel()), "Food", "");
		}
	}
}
