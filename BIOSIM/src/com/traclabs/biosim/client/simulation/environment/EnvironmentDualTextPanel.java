package com.traclabs.biosim.client.simulation.environment;

import java.awt.GridLayout;

import com.traclabs.biosim.client.framework.gui.UpdatablePanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;

/**
 * 
 * @author Scott Bell
 */

public class EnvironmentDualTextPanel extends UpdatablePanel {
    private UpdatablePanel myCrewEnvironmentTextPanel;

    private UpdatablePanel myPlantEnvironmentTextPanel;

    public EnvironmentDualTextPanel() {
        setLayout(new GridLayout(1, 2));
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        SimEnvironment myCrewEnvironment = (myBioHolder.theSimEnvironments
                .get(0));
        SimEnvironment myPlantEnvironment = (myBioHolder.theSimEnvironments
                .get(1));
        myCrewEnvironmentTextPanel = new EnvironmentTextPanel(myCrewEnvironment);
        myPlantEnvironmentTextPanel = new EnvironmentTextPanel(
                myPlantEnvironment);

        add(myCrewEnvironmentTextPanel);
        add(myPlantEnvironmentTextPanel);
    }

    public void refresh() {
        myCrewEnvironmentTextPanel.refresh();
        myPlantEnvironmentTextPanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
        myCrewEnvironmentTextPanel.visibilityChange(nowVisible);
        myPlantEnvironmentTextPanel.visibilityChange(nowVisible);
    }
}