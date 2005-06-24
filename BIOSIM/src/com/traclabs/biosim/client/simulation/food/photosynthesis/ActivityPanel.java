package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.Color;

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

import com.traclabs.biosim.server.simulation.food.photosynthesis.ATPSynthase;
import com.traclabs.biosim.server.simulation.food.photosynthesis.CytochromeB6F;
import com.traclabs.biosim.server.simulation.food.photosynthesis.FNR;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Membrane;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Photosystem1;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Photosystem2;

public class ActivityPanel extends JPanel {
    private int ticks = 0;

    private ChartPanel myChartPanel;

    private JFreeChart myChart;

    private XYSeries myPS2Series = new XYSeries("PS2 Activity Level");

    private XYSeries myCyB6FSeries = new XYSeries("CyB6F Activity Level");

    private XYSeries myPS1Series = new XYSeries("PS1 Activity Level");
    
    private XYSeries myFNRSeries = new XYSeries("FNR Activity Level");
    
    private XYSeries myATPSynthaseSeries = new XYSeries("ATP Synthase Activity Level");

    private XYSeriesCollection myData;

    private Membrane myMembrane;
    
    private Photosystem2 myPhotosystem2;
    private CytochromeB6F myCytochromeB6F;
    private Photosystem1 myPhotosystem1;
    private FNR myFNR;
    private ATPSynthase myATPSynthase;

    public ActivityPanel(Membrane pMembrane) {
        myMembrane = pMembrane;
        myPhotosystem2 = myMembrane.getPhotosystem2();
        myCytochromeB6F = myMembrane.getCytochromeB6F();
        myPhotosystem1 = myMembrane.getPhotosystem1();
        myFNR = myMembrane.getFNR();
        myATPSynthase = myMembrane.getATPSynthase();
        buildFullGUI();
    }

    private void buildFullGUI() {
        //Chart Panel
        setLayout(new BorderLayout());
        myData = new XYSeriesCollection();
        myData.addSeries(myPS2Series);
        myData.addSeries(myCyB6FSeries);
        myData.addSeries(myPS1Series);
        myData.addSeries(myFNRSeries);
        myData.addSeries(myATPSynthaseSeries);

        myChart = ChartFactory.createXYLineChart("Enzyme Activity Level",
                "Simulation Iterations", "Activity Level", myData, PlotOrientation.VERTICAL, true, true, false);

        XYPlot myPlot = myChart.getXYPlot();
        XYItemRenderer renderer = myPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesPaint(2, Color.ORANGE);
        renderer.setSeriesPaint(2, Color.MAGENTA);
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
    	if (myPhotosystem2.isEnergized())
    		myPS2Series.add(ticks, 1);
    	else
    		myPS2Series.add(ticks, 0);
    	
    	myCyB6FSeries.add(ticks, myCytochromeB6F.getNumberOfElectrons());
    	
    	if (myPhotosystem1.isEnergized())
    		myPS1Series.add(ticks, 1);
    	else
    		myPS1Series.add(ticks, 0);
    	
    	if (myFNR.hasComplexHasFormed())
    		myFNRSeries.add(ticks, 1);
    	else
    		myFNRSeries.add(ticks, 0);
    	
    	if (myATPSynthase.isEnergized())
    		myATPSynthaseSeries.add(ticks, 1);
    	else
    		myATPSynthaseSeries.add(ticks, 0);
    	
        myChart.getXYPlot().setDataset(myData);
        ticks++;
    }
}