package biosim.client.food.gui;

import java.awt.*;
import biosim.idl.food.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import com.jrefinery.chart.*;
import com.jrefinery.data.*;
import com.jrefinery.ui.*;

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
	private CategoryPlot myPlot;
	private JFreeChart myChart;

	protected void createGraph(){
		// create the chart...
		myFoodStore = (FoodStore)(BioHolder.getBioModule(BioHolder.foodStoreName));
		myBiomassStore = (BiomassStore)(BioHolder.getBioModule(BioHolder.biomassStoreName));
		refresh();
		myChart = ChartFactory.createVerticalBarChart3D(
		                  "Food Store Levels",  // chart title
		                  "Stores",              // domain axis label
		                  "Level (kg)",                 // range axis label
		                  myDataset,                 // data
		                  true                     // include legend
		          );
		// add the chart to a panel...
		myPlot = myChart.getCategoryPlot();
		rangeAxis = myPlot.getRangeAxis();
		rangeAxis.setAutoRange(false);
		rangeAxis.setRange(0.0, myFoodStore.getCapacity());
		myPlot.setSeriesPaint(new Paint[] { new Color(51,153,51), new Color(204,204,0)});
		TextTitle myTextTitle = (TextTitle)(myChart.getTitle(0));
		myTextTitle.setFont(myTextTitle.getFont().deriveFont(12.0f));
		myChartPanel = new ChartPanel(myChart);
		myChartPanel.setMinimumDrawHeight(300);
		myChartPanel.setMinimumDrawWidth(250);
		myChartPanel.setPreferredSize(new Dimension(200, 200));
	}

	public void refresh() {
		if (myDataset == null){
			double[][] data = { {myBiomassStore.getLevel()}, {myFoodStore.getLevel()} };
			myDataset = new DefaultCategoryDataset(data);
			String[] theSeries = {"Biomass", "Food"};
			String[] theCategory = {""};
			myDataset.setSeriesNames(theSeries);
			myDataset.setCategories(theCategory);
		}
		else{
			myDataset.setValue(0, "", new Float(myBiomassStore.getLevel()));
			myDataset.setValue(1, "", new Float(myFoodStore.getLevel()));
		}
	}
}
