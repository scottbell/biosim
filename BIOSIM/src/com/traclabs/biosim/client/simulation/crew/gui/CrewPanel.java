package biosim.client.crew.gui;

import biosim.client.framework.gui.*;
/** 
 * This is the JPanel that displays information about the Crew
 *
 * @author    Scott Bell
 */

public class CrewPanel extends BioTabbedPanel
{	
	protected void createPanels(){
		myTextPanel = new CrewTextPanel();
		myChartPanel = new CrewChartPanel();
		mySchematicPanel = new CrewSchematicPanel();
	}
}
