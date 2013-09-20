package com.traclabs.biosim.client.simulation.power;

import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.client.simulation.framework.SimTabbedPanel;

/**
 * This is the JPanel that displays information about the Power
 * 
 * @author Scott Bell
 */

public class PowerPanel extends SimTabbedPanel {
    protected void createPanels() {
        myTextPanel = new PowerTextPanel();
        myChartPanel = new PowerChartPanel();
        mySchematicPanel = new UpdatablePanel();
    }
    
    protected void alterVisibility() {
    	super.alterVisibility();
	}
}