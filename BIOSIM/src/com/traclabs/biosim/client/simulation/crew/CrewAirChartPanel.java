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
 * This is the JPanel that displays a chart about the Crew and air
 * 
 * @author Scott Bell
 */
public class CrewAirChartPanel extends GraphPanel {
    private Collection<CrewGroup> myCrewGroups;

    private DefaultCategoryDataset myDataset;

    protected void createGraph() {
    	myCrewGroups = new ArrayList<CrewGroup>();
        // create the chart...
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
				myCrewGroups.add(crewGroup);
        refresh();
        JFreeChart myChart = ChartFactory.createBarChart3D(
                "O2/CO2 Consumed/Produced", // chart title
                "", // domain axis label
                "moles", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        ValueAxis rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, 4);
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.GREEN);
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(250);
        myChartPanel.setMinimumDrawWidth(250);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }
    
    private float getTotalCO2Produced(){
    	float totalCO2 = 0f;
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			totalCO2 += crewGroup.getCO2Produced();
		return totalCO2;
    }
    
    private float getTotalO2Consumed(){
    	float totalO2 = 0f;
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			totalO2 += crewGroup.getO2Consumed();
		return totalO2;
    }
    
    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultCategoryDataset();
            String series1 = "O2 Consumed";
            String series2 = "CO2 Produced";
            String category = "";
            myDataset.addValue(getTotalO2Consumed(), series1, category);
            myDataset.addValue(getTotalCO2Produced(), series2, category);
        } else {
            myDataset.addValue(getTotalO2Consumed(), "O2 Consumed", "");
            myDataset
                    .addValue(getTotalCO2Produced(), "CO2 Produced", "");
        }
    }
}