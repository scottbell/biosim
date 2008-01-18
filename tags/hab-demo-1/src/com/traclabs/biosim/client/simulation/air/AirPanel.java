package com.traclabs.biosim.client.simulation.air;

import com.traclabs.biosim.client.simulation.framework.SimTabbedPanel;

/**
 * This is the JPanel that displays information about the Air
 * 
 * @author Scott Bell
 */

public class AirPanel extends SimTabbedPanel {
    protected void createPanels() {
        myTextPanel = new AirTextPanel();
        myChartPanel = new AirChartPanel();
        mySchematicPanel = new AirSchematicPanel();
    }
}