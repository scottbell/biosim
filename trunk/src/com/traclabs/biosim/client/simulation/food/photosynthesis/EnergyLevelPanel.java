package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.traclabs.biosim.server.simulation.food.photosynthesis.ATPSynthase;
import com.traclabs.biosim.server.simulation.food.photosynthesis.CytochromeB6F;
import com.traclabs.biosim.server.simulation.food.photosynthesis.FNR;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Membrane;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Photosystem1;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Photosystem2;

public class EnergyLevelPanel extends JPanel {
    private int ticks = 0;

    private ChartPanel myChartPanel;

    private JFreeChart myChart;

    private XYSeries myPS2Series = new XYSeries("PS2 Energy Level");

    private XYSeries myCyB6FSeries = new XYSeries("CyB6F Energy Level");

    private XYSeries myPS1Series = new XYSeries("PS1 Energy Level");
    
    private XYSeries myFNRSeries = new XYSeries("FNR Energy Level");
    
    private XYSeries myATPSynthaseSeries = new XYSeries("ATP Synthase Energy Level");
    
    private XYSeries[] mySeries = {myPS2Series, myCyB6FSeries, myPS1Series, myFNRSeries, myATPSynthaseSeries};

    private Color[] myColors = {Color.CYAN, Color.RED, Color.GREEN, Color.BLUE, Color.MAGENTA};
    
    private XYSeriesCollection myPS2Data = new XYSeriesCollection(myPS2Series);
    
    private XYSeriesCollection myCyB6FData = new XYSeriesCollection(myCyB6FSeries);
    
    private XYSeriesCollection myPS1Data = new XYSeriesCollection(myPS1Series);
    
    private XYSeriesCollection myFNRData = new XYSeriesCollection(myFNRSeries);
    
    private XYSeriesCollection myATPSynthaseData = new XYSeriesCollection(myATPSynthaseSeries);
    
    private XYSeriesCollection[] myData = {myPS2Data, myCyB6FData, myPS1Data, myFNRData, myATPSynthaseData};
    
    private XYPlot myPS2Plot;

    private XYPlot myCyB6FPlot;

    private XYPlot myPS1Plot;
    
    private XYPlot myFNRPlot;
    
    private XYPlot myATPPlot;
    
    private XYPlot[] myPlots = {myPS2Plot, myCyB6FPlot, myPS1Plot, myFNRPlot, myATPPlot};


    private Membrane myMembrane;
    
    private Photosystem2 myPhotosystem2;
    private CytochromeB6F myCytochromeB6F;
    private Photosystem1 myPhotosystem1;
    private FNR myFNR;
    private ATPSynthase myATPSynthase;

    public EnergyLevelPanel(Membrane pMembrane) {
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
        NumberAxis xAxis = new NumberAxis("Ticks");
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis("Energy");
        setLayout(new BorderLayout());
        
        CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(xAxis);
        combinedPlot.getDomainAxis().setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
       
        
        for (int i = 0; i < mySeries.length; i++){
        	XYPlot currentPlot = myPlots[i];
            XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
            renderer.setSeriesPaint(0, myColors[i]);
        	currentPlot = new XYPlot(myData[i], xAxis, yAxis, renderer);
        	currentPlot.setOrientation(PlotOrientation.VERTICAL);
        	currentPlot.getRangeAxis().setStandardTickUnits(
                    NumberAxis.createIntegerTickUnits());
        	currentPlot.getRangeAxis().setAutoRange(false);
        	currentPlot.getRangeAxis().setRange(0, 1.1);
        	combinedPlot.add(currentPlot);
		}
        myChart = new JFreeChart("Enzyme Energy Level", JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, true);
        myChartPanel = new ChartPanel(myChart);
        myChart.setBackgroundPaint(myChartPanel.getBackground());
        setLayout(new BorderLayout());
        add(myChartPanel, BorderLayout.CENTER);
    }

    public void refresh() {
    	//if (myPhotosystem2.isEnergized())
    	//	myPS2Series.add(ticks, 1);
    	
    		myPS2Series.add(ticks, 0);
    	if (myCytochromeB6F.getNumberOfElectrons() > 0)
    		myCyB6FSeries.add(ticks, 1);
    	else
    		myCyB6FSeries.add(ticks, 0);
    	
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
    	
        ticks++;
    }

	public void reset() {
		for (XYSeries series : mySeries)
			series.clear();
	}
}