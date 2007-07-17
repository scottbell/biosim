package com.traclabs.biosim.client.simulation.environment;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;

import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

/**
 * 
 * @author Scott Bell
 */

public class EnvironmentMultiTextPanel extends UpdatablePanel {
	Collection<UpdatablePanel> myUpdatablePanels = new ArrayList<UpdatablePanel>();

    public EnvironmentMultiTextPanel() {
    	int numberOfEnvironments = BioHolderInitializer.getBioHolder().theSimEnvironments.size();
        if (numberOfEnvironments == 1)
			setLayout(new GridLayout(1, 1));
        else if ((numberOfEnvironments % 2) != 0)
			setLayout(new GridLayout(numberOfEnvironments / 2 + 1,numberOfEnvironments / 2));
		else
			setLayout(new GridLayout(numberOfEnvironments / 2,numberOfEnvironments / 2));
        
        for (SimEnvironment environment : BioHolderInitializer.getBioHolder().theSimEnvironments) {
        	EnvironmentTextPanel newTextPanel = new EnvironmentTextPanel(environment);
        	myUpdatablePanels.add(newTextPanel);
            add(newTextPanel);
        }
    }

    public void refresh() {
    	for (UpdatablePanel updatablePanel : myUpdatablePanels)
    		updatablePanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
    	for (UpdatablePanel updatablePanel : myUpdatablePanels)
    		updatablePanel.visibilityChange(nowVisible);
    }
}