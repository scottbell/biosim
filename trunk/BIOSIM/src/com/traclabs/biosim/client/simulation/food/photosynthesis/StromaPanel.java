package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.JLabel;
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

import com.traclabs.biosim.server.simulation.food.photosynthesis.Stroma;

public class StromaPanel extends JPanel {
    private int ticks = 0;

    private JPanel myDataPanel;

    private JLabel[] myDataLabels;

    private ChartPanel myChartPanel;

    private JFreeChart myChart;

    private DecimalFormat numFormat;

    private XYSeries myProtonsSeries = new XYSeries("Protons");

    private XYSeries myNADPSeries = new XYSeries("NAPDs");

    private XYSeries myADPSeries = new XYSeries("ADPs");

    private XYSeries myPhosphateSeries = new XYSeries("Phosphates");

    private XYSeries myATPSeries = new XYSeries("ATPs");

    private XYSeries myNADPHSeries = new XYSeries("NADPHs");

    private XYSeriesCollection myData;

    private Stroma myStroma;

    public StromaPanel(Stroma pStroma) {
        myStroma = pStroma;
        numFormat = new DecimalFormat("#,##0.00;(#)");
        buildFullGUI();
    }

    private void buildFullGUI() {
        //Chart Panel
        setLayout(new BorderLayout());
        XYSeries series = new XYSeries("Stroma Conditions");
        myData = new XYSeriesCollection(myProtonsSeries);
        myData.addSeries(myNADPSeries);
        myData.addSeries(myADPSeries);
        myData.addSeries(myPhosphateSeries);
        myData.addSeries(myATPSeries);
        myData.addSeries(myNADPHSeries);

        myChart = ChartFactory.createXYLineChart("Stroma Conditions",
                "Simulation Iterations", "Quantity", myData, PlotOrientation.VERTICAL, true, true, false);

        XYPlot myPlot = myChart.getXYPlot();
        XYItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.ORANGE);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.PINK);
        renderer.setSeriesPaint(4, Color.MAGENTA);
        renderer.setSeriesPaint(5, Color.BLUE);
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
        myProtonsSeries.add(ticks, myStroma.getProtons().getQuantity());
        myNADPSeries.add(ticks, myStroma.getNADPs().getQuantity());
        myADPSeries.add(ticks, myStroma.getADPs().getQuantity());
        myPhosphateSeries.add(ticks, myStroma.getPhosphates().getQuantity());
        myATPSeries.add(ticks, myStroma.getATPs().getQuantity());
        myNADPHSeries.add(ticks, myStroma.getNADPHs().getQuantity());
        myChart.getXYPlot().setDataset(myData);
        ticks++;
    }
}