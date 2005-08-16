package com.traclabs.biosim.client.simulation.environment;

import java.awt.BorderLayout;

import com.traclabs.biosim.client.framework.gui.UpdatablePanel;

/**
 * This is the JPanel that displays a chart about the Water
 * 
 * @author Scott Bell
 */
public class EnvironmentSingleChartPanel extends UpdatablePanel {
    private UpdatablePanel myCrewEnvironmentPieChartPanel;

    public EnvironmentSingleChartPanel() {
        setLayout(new BorderLayout());
        myCrewEnvironmentPieChartPanel = new EnvironmentPieChartPanel("Crew");

        add(myCrewEnvironmentPieChartPanel, BorderLayout.CENTER);
    }

    public void refresh() {
        myCrewEnvironmentPieChartPanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
        myCrewEnvironmentPieChartPanel.visibilityChange(nowVisible);
    }
}