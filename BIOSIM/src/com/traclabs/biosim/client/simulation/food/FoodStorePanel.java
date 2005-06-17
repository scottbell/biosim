package com.traclabs.biosim.client.simulation.food;

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

import com.traclabs.biosim.client.framework.gui.GraphPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.FoodStore;

/**
 * This is the JPanel that displays a chart about the Food/Biomass Stores
 * 
 * @author Scott Bell
 */
public class FoodStorePanel extends GraphPanel {
    private FoodStore myFoodStore;

    private BiomassStore myBiomassStore;

    private DefaultCategoryDataset myDataset;

    private ValueAxis rangeAxis;

    protected void createGraph() {
        // create the chart...
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        myBiomassStore = (BiomassStore) (myBioHolder.theBiomassStores.get(0));
        myFoodStore = (FoodStore) (myBioHolder.theFoodStores.get(0));
        refresh();
        JFreeChart myChart = ChartFactory.createBarChart3D("Food Store Levels", // chart
                // title
                "Stores", // domain axis label
                "Level (kg)", // range axis label
                myDataset, PlotOrientation.VERTICAL, // data
                true, // include legend
                true, false);
        // add the chart to a panel...
        CategoryPlot myPlot = myChart.getCategoryPlot();
        rangeAxis = myPlot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0.0, myFoodStore.getCurrentCapacity());
        CategoryItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, new Color(51, 153, 51));
        renderer.setSeriesPaint(1, new Color(204, 204, 0));
        TextTitle myTextTitle = (TextTitle) (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(300);
        myChartPanel.setMinimumDrawWidth(250);
        myChartPanel.setPreferredSize(new Dimension(200, 200));
    }

    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultCategoryDataset();
            String series1 = "Biomass";
            String series2 = "Food";
            String category = "";
            myDataset.addValue(myBiomassStore.getCurrentLevel(), series1,
                    category);
            myDataset
                    .addValue(myFoodStore.getCurrentLevel(), series2, category);
        } else {
            float capacity = Math.max(myBiomassStore.getCurrentCapacity(),
                    myFoodStore.getCurrentCapacity());
            if ((rangeAxis.getRange().getUpperBound() != capacity)
                    && (capacity > 0)) {
                rangeAxis.setRange(0.0, capacity);
                myChartPanel.repaint();
            }
            myDataset.setValue(new Float(myBiomassStore.getCurrentLevel()),
                    "Biomass", "");
            myDataset.setValue(new Float(myFoodStore.getCurrentLevel()),
                    "Food", "");
        }
    }
}