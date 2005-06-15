package com.traclabs.biosim.client.simulation.power.gui;

import java.awt.Color;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TextTitle;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.CategoryItemRenderer;
import org.jfree.data.DefaultCategoryDataset;

import com.traclabs.biosim.client.framework.gui.GraphPanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.power.PowerStore;

/**
 * This is the JPanel that displays a chart about the Power Store
 * 
 * @author Scott Bell
 */
public class PowerStorePanel extends GraphPanel {
    private PowerStore myPowerStore;

    private DefaultCategoryDataset myDataset;

    private ValueAxis rangeAxis;

    private JFreeChart myChart;

    protected void createGraph() {
        // create the chart...
        myPowerStore = (PowerStore) (BioHolderInitializer.getBioHolder().thePowerStores
                .get(0));
        refresh();
        myChart = ChartFactory.createBarChart3D("Power Store Level", // chart
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
        rangeAxis.setRange(0.0, myPowerStore.getCurrentCapacity());
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE);
        TextTitle myTextTitle = (TextTitle) (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(200);
        myChartPanel.setMinimumDrawWidth(230);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }

    public void refresh() {
        if (myDataset == null) {
            double[][] data = { {} };
            myDataset = new DefaultCategoryDataset();
            myDataset.addValue(myPowerStore.getCurrentLevel(), "Power Store",
                    "");
        } else {
            float capacity = myPowerStore.getCurrentCapacity();
            if ((rangeAxis.getRange().getUpperBound() != capacity)
                    && (capacity > 0)) {
                rangeAxis.setRange(0.0, capacity);
                myChartPanel.repaint();
            }
            myDataset.setValue(new Float(myPowerStore.getCurrentLevel()),
                    "Power Store", "");
        }
    }
}