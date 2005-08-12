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

public class EnvironmentSingleTextPanel extends UpdatablePanel {
    private UpdatablePanel myCrewEnvironmentTextPanel;

    public EnvironmentSingleTextPanel() {
        setLayout(new GridLayout(1, 1));
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        SimEnvironment myCrewEnvironment = (myBioHolder.theSimEnvironments
                .get(0));
        myCrewEnvironmentTextPanel = new EnvironmentTextPanel(myCrewEnvironment);

        add(myCrewEnvironmentTextPanel);
    }

    public void refresh() {
        myCrewEnvironmentTextPanel.refresh();
    }

    public void visibilityChange(boolean nowVisible) {
        myCrewEnvironmentTextPanel.visibilityChange(nowVisible);
    }
}