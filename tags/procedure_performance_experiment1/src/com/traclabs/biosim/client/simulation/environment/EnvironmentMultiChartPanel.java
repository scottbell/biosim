package com.traclabs.biosim.client.simulation.environment;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;

import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

public class EnvironmentMultiChartPanel extends UpdatablePanel {
	Collection<UpdatablePanel> myUpdatablePanels = new ArrayList<UpdatablePanel>();

    public EnvironmentMultiChartPanel() {
    	int numberOfEnvironments = BioHolderInitializer.getBioHolder().theSimEnvironments.size();
        if (numberOfEnvironments == 1)
			setLayout(new GridLayout(1, 1));
        else if ((numberOfEnvironments % 2) != 0)
			setLayout(new GridLayout(numberOfEnvironments / 2 + 1,numberOfEnvironments / 2));
		else
			setLayout(new GridLayout(numberOfEnvironments / 2,numberOfEnvironments / 2));
        
        for (SimEnvironment environment : BioHolderInitializer.getBioHolder().theSimEnvironments) {
        	EnvironmentHybridChartPanel newPieChartPanel = new EnvironmentHybridChartPanel(environment);
        	myUpdatablePanels.add(newPieChartPanel);
            add(newPieChartPanel);
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