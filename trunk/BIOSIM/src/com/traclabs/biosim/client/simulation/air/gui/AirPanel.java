package biosim.client.simulation.air.gui;

import biosim.client.simulation.framework.gui.*;
/** 
 * This is the JPanel that displays information about the Air
 *
 * @author    Scott Bell
 */

public class AirPanel extends BioTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new AirTextPanel();
		myChartPanel = new AirChartPanel();
		mySchematicPanel = new AirSchematicPanel();
	}
}
