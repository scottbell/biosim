package com.traclabs.biosim.client.simulation.water.gui;

import com.traclabs.biosim.client.simulation.framework.gui.SimTabbedPanel;

/**
 * This is the JPanel that displays information about the Water
 * 
 * @author Scott Bell
 */

public class WaterPanel extends SimTabbedPanel {
    protected void createPanels() {
        myTextPanel = new WaterTextPanel();
        myChartPanel = new WaterChartPanel();
        mySchematicPanel = new WaterSchematicPanel();
    }
}