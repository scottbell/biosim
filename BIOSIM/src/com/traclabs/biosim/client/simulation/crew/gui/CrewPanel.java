package biosim.client.simulation.crew.gui;

import biosim.client.simulation.framework.gui.*;
/** 
 * This is the JPanel that displays information about the Crew
 *
 * @author    Scott Bell
 */

public class CrewPanel extends SimTabbedPanel
{	
	protected void createPanels(){
		myTextPanel = new CrewTextPanel();
		myChartPanel = new CrewChartPanel();
		mySchematicPanel = new CrewSchematicPanel();
	}
}
