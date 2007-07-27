package com.traclabs.biosim.client.simulation.crew;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import com.traclabs.biosim.client.framework.GraphPanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;

/**
 * This is the JPanel that displays a chart about the Crew and food
 * 
 * @author Scott Bell
 */
public class CrewFoodChartPanel extends GraphPanel {
    private Collection<CrewGroup> myCrewGroups;

    private DefaultCategoryDataset myDataset;

    protected void createGraph() {
        // create the chart...
    	myCrewGroups = new ArrayList<CrewGroup>();
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			myCrewGroups.add(crewGroup);
        refresh();
        JFreeChart myChart = ChartFactory.createBarChart3D("Food Consumption", // chart
                // title
                "", // domain axis label
                "Calories", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        ValueAxis rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, 1000.0);
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(10, 204, 102));
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(250);
        myChartPanel.setMinimumDrawWidth(250);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }
    
    private float getTotalFoodConsumed(){
    	float totalFood = 0f;
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			totalFood += crewGroup.getFoodConsumed();
		return totalFood;
    }

    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultCategoryDataset();
            String series1 = "Food";
            String category = "";
            myDataset
                    .addValue(getTotalFoodConsumed(), series1, category);
        } else {
            myDataset.setValue(getTotalFoodConsumed(), "Food", "");
        }
    }
}