package com.traclabs.biosim.client.simulation.food;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import com.traclabs.biosim.client.framework.GraphPanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.FoodStore;

/**
 * This is the JPanel that displays a chart about the Food/Biomass Stores
 * 
 * @author Scott Bell
 */
public class FoodStorePanel extends GraphPanel {
	private DefaultCategoryDataset myDataset;

    private ValueAxis rangeAxis;

    private JFreeChart myChart;

    protected void createGraph() {
        // create the chart...
        myDataset = new DefaultCategoryDataset();
    	for (BiomassStore biomassStore : BioHolderInitializer.getBioHolder().theBiomassStores) {
      		 myDataset.addValue(biomassStore.getCurrentLevel(), biomassStore.getModuleName(), "");
   		}
    	for (FoodStore foodStore : BioHolderInitializer.getBioHolder().theFoodStores) {
    		 myDataset.addValue(foodStore.getCurrentLevel(), foodStore.getModuleName(), "");
    	}
            
        myChart = ChartFactory.createBarChart3D("Biomass & Food Store Levels", // chart
                // title
                "", // domain axis label
                "Store Level (kg)", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, getGreatestCapacity());
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(200);
        myChartPanel.setMinimumDrawWidth(230);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }

    private float getGreatestCapacity(){
    	float greatestCapacity = 0f;
    	for (FoodStore foodStore : BioHolderInitializer.getBioHolder().theFoodStores) {
    		greatestCapacity = Math.max(greatestCapacity, foodStore.getCurrentCapacity());
		}
    	for (BiomassStore foodStore : BioHolderInitializer.getBioHolder().theBiomassStores) {
    		greatestCapacity = Math.max(greatestCapacity, foodStore.getCurrentCapacity());
		}
    	return greatestCapacity;
    }

    public void refresh() {
    	for (BiomassStore biomassStore : BioHolderInitializer.getBioHolder().theBiomassStores) {
   		 	myDataset.setValue(biomassStore.getCurrentLevel(), biomassStore.getModuleName(), "");
    	}
    	for (FoodStore foodStore : BioHolderInitializer.getBioHolder().theFoodStores) {
   		 	myDataset.setValue(foodStore.getCurrentLevel(), foodStore.getModuleName(), "");
    	}
    }
}