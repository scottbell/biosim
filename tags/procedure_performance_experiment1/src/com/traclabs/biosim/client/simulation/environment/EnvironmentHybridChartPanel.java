package com.traclabs.biosim.client.simulation.environment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

public class EnvironmentHybridChartPanel extends UpdatablePanel {
    private SimEnvironment mySimEnvironment;
    private EnvironmentPieChartPanel myPieChartPanel;
    private EnvironmentPressureChartPanel myBarChartPanel;

    
    public EnvironmentHybridChartPanel(SimEnvironment pEnvironment) {
    	mySimEnvironment = pEnvironment;
    	myPieChartPanel = new EnvironmentPieChartPanel(mySimEnvironment);
    	myBarChartPanel = new EnvironmentPressureChartPanel(mySimEnvironment);
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.weighty = 1.0;
        gridbag.setConstraints(myPieChartPanel, c);
        add(myPieChartPanel);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0.4;
        gridbag.setConstraints(myBarChartPanel, c);
        add(myBarChartPanel);
    }
    
    public void refresh() {
    	myPieChartPanel.refresh();
    	myBarChartPanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
    	myPieChartPanel.visibilityChange(nowVisible);
    	myBarChartPanel.visibilityChange(nowVisible);
    }
    
}