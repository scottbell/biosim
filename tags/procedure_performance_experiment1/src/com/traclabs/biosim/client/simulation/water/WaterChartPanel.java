package com.traclabs.biosim.client.simulation.water;

import java.awt.BorderLayout;

import com.traclabs.biosim.client.framework.UpdatablePanel;

/**
 * This is the JPanel that displays a chart about the Water
 * 
 * @author Scott Bell
 */
public class WaterChartPanel extends UpdatablePanel {
    private WaterStorePanel myWaterStorePanel;

    public WaterChartPanel() {
        setLayout(new BorderLayout());
        myWaterStorePanel = new WaterStorePanel();
        add(myWaterStorePanel, BorderLayout.CENTER);
    }

    public void visibilityChange(boolean nowVisible) {
        myWaterStorePanel.visibilityChange(nowVisible);
    }

    public void refresh() {
        myWaterStorePanel.refresh();
    }
}