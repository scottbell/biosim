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
 * This is the JPanel that displays a chart about the crew and water
 * 
 * @author Scott Bell
 */
public class CrewWaterChartPanel extends GraphPanel {
    private Collection<CrewGroup> myCrewGroups;

    private DefaultCategoryDataset myDataset;

    protected void createGraph() {
    	myCrewGroups = new ArrayList<CrewGroup>();
        // create the chart...
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			myCrewGroups.add(crewGroup);
        refresh();
        JFreeChart myChart = ChartFactory.createBarChart3D(
                "Water Consumed/Produced", // chart title
                "", // domain axis label
                "Liters", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        ValueAxis rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, 2.0);
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.GRAY);
        renderer.setSeriesPaint(2, Color.YELLOW);
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(250);
        myChartPanel.setMinimumDrawWidth(250);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }
    
    private float getTotalGreyWaterProduced(){
    	float water = 0f;
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			water += crewGroup.getGreyWaterProduced();
		return water;
    }
    
    private float getTotalDirtyWaterProduced(){
    	float water = 0f;
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			water += crewGroup.getDirtyWaterProduced();
		return water;
    }
    
    private float getTotalPotableWaterConsumed(){
    	float water = 0f;
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			water += crewGroup.getPotableWaterConsumed();
		return water;
    }

    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultCategoryDataset();
            float myPotableWaterConsumed = getTotalPotableWaterConsumed();
            float myGreyWaterProduced = getTotalGreyWaterProduced();
            float myDirtyWaterProduced = getTotalDirtyWaterProduced();
            String series1 = "Potable Water Consumed";
            String series2 = "Grey Water Produced";
            String series3 = "Dirty Water Produced";
            String category = "";
            myDataset.addValue(myPotableWaterConsumed, series1, category);
            myDataset.addValue(myGreyWaterProduced, series2, category);
            myDataset.addValue(myDirtyWaterProduced, series3, category);
        } else {
            myDataset.setValue(
                    new Float(getTotalPotableWaterConsumed()),
                    "Potable Water Consumed", "");
            myDataset.setValue(new Float(getTotalGreyWaterProduced()),
                    "Grey Water Produced", "");
            myDataset.setValue(new Float(getTotalDirtyWaterProduced()),
                    "Dirty Water Produced", "");
        }
    }
}