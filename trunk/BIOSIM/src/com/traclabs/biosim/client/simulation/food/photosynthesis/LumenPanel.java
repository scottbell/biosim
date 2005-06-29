package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Iterator;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.traclabs.biosim.server.simulation.food.photosynthesis.Lumen;

public class LumenPanel extends JPanel {
    private int ticks = 0;

    private ChartPanel myChartPanel;

    private JFreeChart myChart;

    private XYSeries myProtonsSeries = new XYSeries("Protons");

    private XYSeries myWaterSeries = new XYSeries("H20");

    private XYSeries myOxygenSeries = new XYSeries("O2");

    private XYSeriesCollection myData;

    private Lumen myLumen;

    public LumenPanel(Lumen pLumen) {
        myLumen = pLumen;
        buildFullGUI();
    }

    private void buildFullGUI() {
        //Chart Panel
        setLayout(new BorderLayout());
        myData = new XYSeriesCollection(myProtonsSeries);
        myData.addSeries(myWaterSeries);
        myData.addSeries(myOxygenSeries);

        myChart = ChartFactory.createXYLineChart("Lumen Conditions",
                "Ticks", "Quantity", myData, PlotOrientation.VERTICAL, true, true, false);

        XYPlot myPlot = myChart.getXYPlot();
        XYItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.CYAN);
        renderer.setSeriesPaint(2, Color.DARK_GRAY);
        myPlot.getDomainAxis().setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        myPlot.getRangeAxis().setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        myChartPanel = new ChartPanel(myChart);
        myChart.setBackgroundPaint(myChartPanel.getBackground());
        setLayout(new BorderLayout());
        add(myChartPanel, BorderLayout.CENTER);
    }

    public void refresh() {
        myProtonsSeries.add(ticks, myLumen.getProtons().getQuantity());
        myWaterSeries.add(ticks, myLumen.getWaterMolecules().getQuantity());
        myOxygenSeries.add(ticks, myLumen.getOxygen().getQuantity());
        myChart.getXYPlot().setDataset(myData);
        ticks++;
    }

	public void reset() {
		for (Iterator iter = myData.getSeries().iterator(); iter.hasNext();){
			XYSeries series = (XYSeries)(iter.next());
			series.clear();
		}
	}
}