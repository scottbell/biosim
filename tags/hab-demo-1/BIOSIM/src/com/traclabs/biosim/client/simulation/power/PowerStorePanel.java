package com.traclabs.biosim.client.simulation.power;

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
import com.traclabs.biosim.idl.simulation.power.PowerStore;

/**
 * This is the JPanel that displays a chart about the Power Store
 * 
 * @author Scott Bell
 */
public class PowerStorePanel extends GraphPanel {
    private DefaultCategoryDataset myDataset;

    private ValueAxis rangeAxis;

    private JFreeChart myChart;

    protected void createGraph() {
        // create the chart...
        myDataset = new DefaultCategoryDataset();
    	for (PowerStore powerStore : BioHolderInitializer.getBioHolder().thePowerStores) {
    		 myDataset.addValue(powerStore.getCurrentLevel(), powerStore.getModuleName(), "");
    	}
            
        myChart = ChartFactory.createBarChart3D("Power Store Levels", // chart
                // title
                "", // domain axis label
                "Power Level (W)", // range axis label
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
    	for (PowerStore powerStore : BioHolderInitializer.getBioHolder().thePowerStores) {
    		greatestCapacity = Math.max(greatestCapacity, powerStore.getCurrentCapacity());
		}
    	return greatestCapacity;
    }

    public void refresh() {
    	for (PowerStore powerStore : BioHolderInitializer.getBioHolder().thePowerStores) {
   		 	myDataset.setValue(powerStore.getCurrentLevel(), powerStore.getModuleName(), "");
    	}
    }
}