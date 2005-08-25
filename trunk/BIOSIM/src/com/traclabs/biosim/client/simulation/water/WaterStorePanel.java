package com.traclabs.biosim.client.simulation.water;

import java.awt.Color;
import java.awt.Dimension;

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
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

/**
 * This is the JPanel that displays a chart about the WaterStores
 * 
 * @author Scott Bell
 */
public class WaterStorePanel extends GraphPanel {
    private PotableWaterStore myPotableWaterStore;

    private GreyWaterStore myGreyWaterStore;

    private DirtyWaterStore myDirtyWaterStore;

    private DefaultCategoryDataset myDataset;

    private ValueAxis rangeAxis;

    protected void createGraph() {
        // create the chart...
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        myPotableWaterStore = (myBioHolder.thePotableWaterStores
                .get(0));
        myDirtyWaterStore = (myBioHolder.theDirtyWaterStores
                .get(0));
        myGreyWaterStore = (myBioHolder.theGreyWaterStores
                .get(0));
        refresh();
        JFreeChart myChart = ChartFactory.createBarChart3D(
                "Water Store Levels", // chart title
                "Stores", // domain axis label
                "Water Level (L)", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, myPotableWaterStore.getCurrentCapacity());
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesPaint(1, Color.GRAY);
        renderer.setSeriesPaint(2, Color.YELLOW);
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(300);
        myChartPanel.setMinimumDrawWidth(300);
        myChartPanel.setPreferredSize(new Dimension(200, 300));
    }

    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultCategoryDataset();
            float myPotableWaterStoreLevel = myPotableWaterStore
                    .getCurrentLevel();
            float myGreyWaterStoreLevel = myGreyWaterStore.getCurrentLevel();
            float myDirtyWaterStoreLevel = myDirtyWaterStore.getCurrentLevel();
            String series1 = "Potable Water";
            String series2 = "Grey Water";
            String series3 = "Dirty Water";
            String category = "";
            myDataset.addValue(myPotableWaterStoreLevel, series1, category);
            myDataset.addValue(myGreyWaterStoreLevel, series2, category);
            myDataset.addValue(myDirtyWaterStoreLevel, series3, category);
        } else {
            float greyDirtyMax = Math.max(
                    myGreyWaterStore.getCurrentCapacity(), myDirtyWaterStore
                            .getCurrentCapacity());
            float capacity = Math.max(greyDirtyMax, myPotableWaterStore
                    .getCurrentCapacity());
            if ((rangeAxis.getRange().getUpperBound() != capacity)
                    && (capacity > 0)) {
                rangeAxis.setRange(0.0, capacity);
                myChartPanel.repaint();
            }
            myDataset.setValue(
                    new Float(myPotableWaterStore.getCurrentLevel()),
                    "Potable Water", "");
            myDataset.setValue(new Float(myGreyWaterStore.getCurrentLevel()),
                    "Grey Water", "");
            myDataset.setValue(new Float(myDirtyWaterStore.getCurrentLevel()),
                    "Dirty Water", "");
        }
    }
}