package biosim.client.simulation.power.gui;

import biosim.client.simulation.framework.gui.*;
/** 
 * This is the JPanel that displays information about the Power
 *
 * @author    Scott Bell
 */

public class PowerPanel extends SimTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new PowerTextPanel();
		myChartPanel = new PowerChartPanel();
		mySchematicPanel = new PowerSchematicPanel();
	}
}
