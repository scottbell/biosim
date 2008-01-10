package com.traclabs.biosim.client.simulation.environment;

import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.client.simulation.framework.SimTabbedPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;

/**
 * This is the JPanel that displays information about the Environment
 * 
 * @author Scott Bell
 */

public class EnvironmentPanel extends SimTabbedPanel {
    protected void createPanels() {
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
    	if (myBioHolder.theSimEnvironments.size() > 0){
    		myTextPanel = new EnvironmentMultiTextPanel();
            myChartPanel = new EnvironmentMultiChartPanel();
    	}
    	else{
    		myTextPanel = new UpdatablePanel();
    		myChartPanel = new UpdatablePanel();
    	}
        mySchematicPanel = new EnvironmentSchematicPanel();
    }
}