package com.traclabs.biosim.client.simulation.food;

import com.traclabs.biosim.client.simulation.framework.SimTabbedPanel;

/**
 * This is the JPanel that displays information about the Food
 * 
 * @author Scott Bell
 */

public class FoodPanel extends SimTabbedPanel {
    protected void createPanels() {
        myTextPanel = new FoodTextPanel();
        myChartPanel = new FoodChartPanel();
        mySchematicPanel = new FoodSchematicPanel();
    }
}