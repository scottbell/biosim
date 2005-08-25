package com.traclabs.biosim.client.simulation.environment;

import java.awt.BorderLayout;

import com.traclabs.biosim.client.framework.UpdatablePanel;

/**
 * This is the JPanel that displays a chart about the Water
 * 
 * @author Scott Bell
 */
public class EnvironmentDualChartPanel extends UpdatablePanel {
    private UpdatablePanel myCrewEnvironmentPieChartPanel;

    private UpdatablePanel myPlantEnvironmentPieChartPanel;

    public EnvironmentDualChartPanel() {
        setLayout(new BorderLayout());
        myCrewEnvironmentPieChartPanel = new EnvironmentPieChartPanel("Crew");
        myPlantEnvironmentPieChartPanel = new EnvironmentPieChartPanel("Plant");

        add(myCrewEnvironmentPieChartPanel, BorderLayout.WEST);
        add(myPlantEnvironmentPieChartPanel, BorderLayout.EAST);
    }

    public void refresh() {
        myCrewEnvironmentPieChartPanel.refresh();
        myPlantEnvironmentPieChartPanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
        myCrewEnvironmentPieChartPanel.visibilityChange(nowVisible);
        myPlantEnvironmentPieChartPanel.visibilityChange(nowVisible);
    }
}