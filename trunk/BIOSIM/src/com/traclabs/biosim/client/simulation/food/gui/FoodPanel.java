package com.traclabs.biosim.client.simulation.food.gui;

import com.traclabs.biosim.client.simulation.framework.gui.SimTabbedPanel;

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