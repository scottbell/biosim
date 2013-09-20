package com.traclabs.biosim.client.simulation.crew;

import com.traclabs.biosim.client.simulation.framework.SimTabbedPanel;

/**
 * This is the JPanel that displays information about the Crew
 * 
 * @author Scott Bell
 */

public class CrewPanel extends SimTabbedPanel {
    protected void createPanels() {
        myTextPanel = new CrewTextPanel();
        myChartPanel = new CrewChartPanel();
        mySchematicPanel = new CrewSchematicPanel();
    }
}