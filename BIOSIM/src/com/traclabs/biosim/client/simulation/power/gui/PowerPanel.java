package biosim.client.power.gui;

import biosim.client.framework.gui.*;
/** 
 * This is the JPanel that displays information about the Power
 *
 * @author    Scott Bell
 */

public class PowerPanel extends BioTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new PowerTextPanel();
		myChartPanel = new PowerChartPanel();
		mySchematicPanel = new PowerSchematicPanel();
	}
}
