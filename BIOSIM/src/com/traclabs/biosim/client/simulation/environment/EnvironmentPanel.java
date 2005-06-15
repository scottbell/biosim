package com.traclabs.biosim.client.simulation.environment;

import com.traclabs.biosim.client.simulation.framework.SimTabbedPanel;

/**
 * This is the JPanel that displays information about the Environment
 * 
 * @author Scott Bell
 */

public class EnvironmentPanel extends SimTabbedPanel {
    protected void createPanels() {
        myTextPanel = new EnvironmentDualTextPanel();
        myChartPanel = new EnvironmentChartPanel();
        mySchematicPanel = new EnvironmentSchematicPanel();
    }
}