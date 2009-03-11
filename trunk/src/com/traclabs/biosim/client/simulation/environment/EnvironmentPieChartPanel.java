package com.traclabs.biosim.client.simulation.environment;

import java.awt.Color;
import java.awt.Dimension;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.traclabs.biosim.client.framework.GraphPanel;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;

/**
 * This is the JPanel that displays a chart about the Environment
 * 
 * @author Scott Bell
 */
public class EnvironmentPieChartPanel extends GraphPanel {
    private SimEnvironment mySimEnvironment;

    private PiePlot3D myPlot;

    private JFreeChart myChart;

    private DefaultPieDataset myDataset;

    private static final String O2Category = "O2";

    private static final String CO2Category = "CO2";

    private static final String nitrogenCategory = "N";

    private static final String waterCategory = "H20";

    private static final String otherCategory = "Other";

    private static final String vacuumCategory = "Vacuum";

    private boolean isVacuum = false;

    private Logger myLogger = Logger.getLogger(EnvironmentPieChartPanel.class);

    public EnvironmentPieChartPanel(SimEnvironment pEnvironment) {
    	super(pEnvironment);
    }
    
    protected void initializeDataSources(BioModule module) {
    	mySimEnvironment = SimEnvironmentHelper.narrow(module);
    }

    protected void createGraph() {
        // create the chart...
        refresh();
        String titleText = mySimEnvironment.getModuleName();
        myChart = ChartFactory.createPieChart3D(titleText, myDataset, true,
                true, false);
        myPlot = (PiePlot3D) (myChart.getPlot());
        myPlot.setDepthFactor(0.1d);
        myPlot.setLabelLinksVisible(false);
        myPlot.setForegroundAlpha(0.3f);
        myPlot.setLabelGenerator(null);
        initDataset();
        TextTitle myTextTitle = (myChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myChart);
        myChartPanel.setMinimumDrawHeight(300);
        myChartPanel.setMinimumDrawWidth(300);
        myChartPanel.setPreferredSize(new Dimension(300, 300));
    }

    public void refresh() {
        if (myDataset == null) {
            myDataset = new DefaultPieDataset();
        } else if ((mySimEnvironment.getO2Store().getCurrentLevel() <= 0)
                && (mySimEnvironment.getCO2Store().getCurrentLevel() <= 0)
                && (mySimEnvironment.getNitrogenStore().getCurrentLevel() <= 0)
                && (mySimEnvironment.getOtherStore().getCurrentLevel() <= 0)
                && (mySimEnvironment.getVaporStore().getCurrentLevel() <= 0)) {
            //It isn't in a vacuum, set it up
            if (!isVacuum) {
                myDataset = new DefaultPieDataset();
                myPlot.setDataset(myDataset);
                myDataset.setValue(vacuumCategory, new Float(1f));
                myPlot.setSectionPaint(vacuumCategory, Color.DARK_GRAY);
                isVacuum = true;
            }
        } else {
            //In a vacuum, set it up for normal use
            if (isVacuum) {
                myDataset = new DefaultPieDataset();
                myPlot.setDataset(myDataset);
                myPlot.setSectionPaint(O2Category, Color.BLUE);
                myPlot.setSectionPaint(CO2Category, Color.GREEN);
                myPlot.setSectionPaint(waterCategory, Color.CYAN);
                myPlot.setSectionPaint(otherCategory, Color.YELLOW);
                myPlot.setSectionPaint(nitrogenCategory, Color.RED);
                isVacuum = false;
            }
            myDataset.setValue(O2Category, new Float(mySimEnvironment.getO2Store().getCurrentLevel()));
            myDataset.setValue(CO2Category, new Float(mySimEnvironment.getCO2Store().getCurrentLevel()));
            myDataset.setValue(waterCategory, new Float(mySimEnvironment.getVaporStore().getCurrentLevel()));
            myDataset.setValue(otherCategory, new Float(mySimEnvironment.getOtherStore().getCurrentLevel()));
            myDataset.setValue(nitrogenCategory, new Float(mySimEnvironment.getNitrogenStore().getCurrentLevel()));
        }
    }

    private void initDataset() {
        if (mySimEnvironment == null)
            myLogger
                    .error("EnvironmentPieChartPanel: mySimEnvironment is null!");
        if ((mySimEnvironment.getO2Store().getCurrentLevel() <= 0)
                && (mySimEnvironment.getCO2Store().getCurrentLevel() <= 0)
                && (mySimEnvironment.getNitrogenStore().getCurrentLevel() <= 0)
                && (mySimEnvironment.getOtherStore().getCurrentLevel() <= 0)
                && (mySimEnvironment.getVaporStore().getCurrentLevel() <= 0)) {
            myDataset.setValue(vacuumCategory, new Float(1f));
            myPlot.setSectionPaint(vacuumCategory, Color.DARK_GRAY);
            isVacuum = true;
        } else {
            myPlot.setSectionPaint(O2Category, Color.BLUE);
            myPlot.setSectionPaint(CO2Category, Color.GREEN);
            myPlot.setSectionPaint(waterCategory, Color.CYAN);
            myPlot.setSectionPaint(otherCategory, Color.YELLOW);
            myPlot.setSectionPaint(nitrogenCategory, Color.RED);
            isVacuum = false;
        }
    }
}