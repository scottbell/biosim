package com.traclabs.biosim.client.simulation.environment;

import java.awt.Color;
import java.awt.Dimension;

import org.apache.log4j.Logger;
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
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;

public class EnvironmentPressureChartPanel extends GraphPanel {
    private SimEnvironment mySimEnvironment;
    private JFreeChart myChart;
    private Logger myLogger = Logger.getLogger(EnvironmentPressureChartPanel.class);

    private DefaultCategoryDataset myDataset;

    private ValueAxis rangeAxis;

    public EnvironmentPressureChartPanel(SimEnvironment simEnvironment) {
    	super(simEnvironment);
	}

    protected void initializeDataSources(BioModule module) {
    	mySimEnvironment = SimEnvironmentHelper.narrow(module);
    }
    
	protected void createGraph() {
        // create the chart...
        refresh();
        JFreeChart myChart = ChartFactory.createBarChart3D(
                "", // chart title
                "", // domain axis label
                "Pressure (kPA)", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                false, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        float totalInitialPressure = mySimEnvironment.getInitialTotalPressure();
        rangeAxis.setRange(0.0, 120);
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.YELLOW);
        TextTitle myTextTitle = (myChart.getTitle());
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(300);
        myChartPanel.setMinimumDrawWidth(20);
        myChartPanel.setPreferredSize(new Dimension(20, 300));
    }

    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultCategoryDataset();
            String series1 = "Total Pressure";
            String category = "";
            myDataset.addValue(mySimEnvironment.getTotalPressure(), series1, category);
        } else {
            myDataset.setValue(
                    new Float(mySimEnvironment.getTotalPressure()),
                    "Total Pressure", "");
        }
    }
}