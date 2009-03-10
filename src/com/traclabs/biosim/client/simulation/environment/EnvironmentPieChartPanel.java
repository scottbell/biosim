package com.traclabs.biosim.client.simulation.environment;

import java.awt.Color;
import java.awt.Dimension;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
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

    private PiePlot3D myPiePlot;

    private JFreeChart myPieChart;

    private DefaultPieDataset myPieDataset;
    
    private DefaultCategoryDataset myTotalPressureDataset;
    
    private ValueAxis myTotalPressureAxis;

    private JFreeChart myTotalPressureChart;

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
        myPieChart = ChartFactory.createPieChart3D(titleText, myPieDataset, true,
                true, false);
        myPiePlot = (PiePlot3D) (myPieChart.getPlot());
        myPiePlot.setDepthFactor(0.1d);
        myPiePlot.setLabelLinksVisible(false);
        myPiePlot.setForegroundAlpha(0.3f);
        myPiePlot.setLabelGenerator(null);
        initDataset();
        TextTitle myTextTitle = (myPieChart.getTitle());
        myTextTitle.setFont(myTextTitle.getFont().deriveFont(13.0f));
        myChartPanel = new ChartPanel(myPieChart);
        myChartPanel.setMinimumDrawHeight(250);
        myChartPanel.setMinimumDrawWidth(250);
        myChartPanel.setPreferredSize(new Dimension(250, 250));
    }

    public void refresh() {
        if (myPieDataset == null) {
            myPieDataset = new DefaultPieDataset();
        } else if ((mySimEnvironment.getO2Store().getCurrentLevel() <= 0)
                && (mySimEnvironment.getCO2Store().getCurrentLevel() <= 0)
                && (mySimEnvironment.getNitrogenStore().getCurrentLevel() <= 0)
                && (mySimEnvironment.getOtherStore().getCurrentLevel() <= 0)
                && (mySimEnvironment.getVaporStore().getCurrentLevel() <= 0)) {
            //It isn't in a vacuum, set it up
            if (!isVacuum) {
                myPieDataset = new DefaultPieDataset();
                myPiePlot.setDataset(myPieDataset);
                myPieDataset.setValue(vacuumCategory, new Float(1f));
                myPiePlot.setSectionPaint(vacuumCategory, Color.DARK_GRAY);
                isVacuum = true;
            }
        } else {
            //In a vacuum, set it up for normal use
            if (isVacuum) {
                myPieDataset = new DefaultPieDataset();
                myPiePlot.setDataset(myPieDataset);
                myPiePlot.setSectionPaint(O2Category, Color.BLUE);
                myPiePlot.setSectionPaint(CO2Category, Color.GREEN);
                myPiePlot.setSectionPaint(waterCategory, Color.CYAN);
                myPiePlot.setSectionPaint(otherCategory, Color.YELLOW);
                myPiePlot.setSectionPaint(nitrogenCategory, Color.RED);
                isVacuum = false;
            }
            myPieDataset.setValue(O2Category, new Float(mySimEnvironment.getO2Store().getCurrentLevel()));
            myPieDataset.setValue(CO2Category, new Float(mySimEnvironment.getCO2Store().getCurrentLevel()));
            myPieDataset.setValue(waterCategory, new Float(mySimEnvironment.getVaporStore().getCurrentLevel()));
            myPieDataset.setValue(otherCategory, new Float(mySimEnvironment.getOtherStore().getCurrentLevel()));
            myPieDataset.setValue(nitrogenCategory, new Float(mySimEnvironment.getNitrogenStore().getCurrentLevel()));
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
            myPieDataset.setValue(vacuumCategory, new Float(1f));
            myPiePlot.setSectionPaint(vacuumCategory, Color.DARK_GRAY);
            isVacuum = true;
        } else {
            myPiePlot.setSectionPaint(O2Category, Color.BLUE);
            myPiePlot.setSectionPaint(CO2Category, Color.GREEN);
            myPiePlot.setSectionPaint(waterCategory, Color.CYAN);
            myPiePlot.setSectionPaint(otherCategory, Color.YELLOW);
            myPiePlot.setSectionPaint(nitrogenCategory, Color.RED);
            isVacuum = false;
        }
    }
}