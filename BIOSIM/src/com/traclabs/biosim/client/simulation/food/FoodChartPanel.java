package com.traclabs.biosim.client.simulation.food;

import java.awt.BorderLayout;

import com.traclabs.biosim.client.framework.UpdatablePanel;

/**
 * This is the JPanel that displays a chart about the Food/Biomass
 * 
 * @author Scott Bell
 */
public class FoodChartPanel extends UpdatablePanel {
    private UpdatablePanel myFoodStorePanel;

    public FoodChartPanel() {
        setLayout(new BorderLayout());
        myFoodStorePanel = new FoodStorePanel();
        add(myFoodStorePanel, BorderLayout.CENTER);
    }

    public void refresh() {
        myFoodStorePanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
        myFoodStorePanel.visibilityChange(nowVisible);
    }
}