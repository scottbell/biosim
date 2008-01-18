package com.traclabs.biosim.client.framework.apollo13;
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

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;

public class ApolloGraphPanel extends JPanel {
    private ChartPanel myChartPanel;

    private JFreeChart myChart;

    private XYSeries myO2OutFlowSeries = new XYSeries("O2 Usage");

    private XYSeries myO2ValveCommandSeries = new XYSeries("O2 Valve Command");

    private XYSeries myO2ValveStateSeries = new XYSeries("O2 Valve State");
    
    private XYSeries myO2GasConcetrationSeries = new XYSeries("O2 Gas Concetration");
    
    private XYSeries myCO2GasConcetrationSeries = new XYSeries("CO2 Gas Concetration");
    
    private XYSeries myO2StoreLevelSeries = new XYSeries("O2 Flow");
    
    private XYSeries myO2UsageSeries = new XYSeries("O2 Flowrate");
    
    private XYSeries[] mySeries = {myO2OutFlowSeries, myO2ValveCommandSeries, myO2ValveStateSeries, myO2GasConcetrationSeries, myCO2GasConcetrationSeries, myO2StoreLevelSeries, myO2UsageSeries};

    private Color[] myColors = {Color.CYAN, Color.RED, Color.PINK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE};
    
    private XYSeriesCollection myO2OutFlowData = new XYSeriesCollection(myO2OutFlowSeries);
    
    private XYSeriesCollection myO2ValveCommandData = new XYSeriesCollection(myO2ValveCommandSeries);
    
    private XYSeriesCollection myO2ValveStateData = new XYSeriesCollection(myO2ValveStateSeries);
    
    private XYSeriesCollection myO2GasConcetrationData = new XYSeriesCollection(myO2GasConcetrationSeries);
   
    private XYSeriesCollection myCO2GasConcetrationData = new XYSeriesCollection(myCO2GasConcetrationSeries);
    
    private XYSeriesCollection myO2StoreLevelData = new XYSeriesCollection(myO2StoreLevelSeries);

    private XYSeriesCollection myO2UsageData = new XYSeriesCollection(myO2UsageSeries);
    
    private XYSeriesCollection[] myData = {myO2OutFlowData, myO2ValveCommandData, myO2ValveStateData, myO2GasConcetrationData, myCO2GasConcetrationData, myO2StoreLevelData, myO2UsageData};
    
    private XYPlot myO2OutFlowPlot;

    private XYPlot myO2ValveCommandPlot;

    private XYPlot myO2ValveStatePlot;
    
    private XYPlot myO2GasConcetrationPlot;
    
    private XYPlot myCO2GasConcetrationPlot;
    
    private XYPlot myO2StoreLevelPlot;
    
    private XYPlot myO2UsagePlot;
    
    private XYPlot[] myPlots = {myO2OutFlowPlot, myO2ValveCommandPlot, myO2ValveStatePlot, myO2GasConcetrationPlot, myCO2GasConcetrationPlot, myO2StoreLevelPlot, myO2UsagePlot};
    
    private NumberAxis myO2OutFlowYAxis = new NumberAxis("Lbs");
    
    private NumberAxis myO2ValveCommandYAxis = new NumberAxis("Level");
    
    private NumberAxis myO2ValveStateYAxis = new NumberAxis("Level");
    
    private NumberAxis myO2GasConcetrationAxis = new NumberAxis("%");
    
    private NumberAxis myCO2GasConcetrationAxis = new NumberAxis("%");
    
    private NumberAxis myO2StoreLevelAxis = new NumberAxis("Lbs");
    
    private NumberAxis myO2UsageAxis = new NumberAxis("Lbs/Hr");
    
    private NumberAxis[] myYAxes = {myO2OutFlowYAxis, myO2ValveCommandYAxis, myO2ValveStateYAxis, myO2GasConcetrationAxis, myCO2GasConcetrationAxis, myO2StoreLevelAxis, myO2UsageAxis};
    
    
    
    private BioHolder myBioHolder;

    public ApolloGraphPanel() {
    	myBioHolder = BioHolderInitializer.getBioHolder();
        buildFullGUI();
    }

    private void buildFullGUI() {
        //Chart Panel
        NumberAxis xAxis = new NumberAxis("Ticks");
        xAxis.setAutoRangeIncludesZero(false);
        setLayout(new BorderLayout());
        
        CombinedDomainXYPlot combinedPlot = new CombinedDomainXYPlot(xAxis);
        combinedPlot.getDomainAxis().setStandardTickUnits(
                NumberAxis.createIntegerTickUnits());
        for (int i = 0; i < mySeries.length; i++){
        	XYPlot currentPlot = myPlots[i];
            XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
            renderer.setSeriesPaint(0, myColors[i]);
        	currentPlot = new XYPlot(myData[i], xAxis, myYAxes[i], renderer);
        	currentPlot.setOrientation(PlotOrientation.VERTICAL);
        	combinedPlot.add(currentPlot);
		}
        myChart = new JFreeChart("Apollo 13 Conditions", JFreeChart.DEFAULT_TITLE_FONT, combinedPlot, true);
        myChartPanel = new ChartPanel(myChart);
        myChart.setBackgroundPaint(myChartPanel.getBackground());
        setLayout(new BorderLayout());
        add(myChartPanel, BorderLayout.CENTER);
    }

	public void reset() {
		for (XYSeries series : mySeries)
			series.clear();
	}

	public void refresh(int pTicks, float pO2FlowRate, float pValveCommand, float pValveState, float pO2Concentration, float pCO2Concentration, float pO2StoreLevel, float pUsage) {
		myO2OutFlowSeries.add(pTicks, pO2FlowRate);
		myO2ValveCommandSeries.add(pTicks, pValveCommand);
		myO2ValveStateSeries.add(pTicks, pValveState);
		myO2GasConcetrationSeries.add(pTicks, pO2Concentration);
		myCO2GasConcetrationSeries.add(pTicks, pCO2Concentration);
		myO2StoreLevelSeries.add(pTicks, pO2StoreLevel);
		myO2UsageSeries.add(pTicks, pUsage);
	}
}