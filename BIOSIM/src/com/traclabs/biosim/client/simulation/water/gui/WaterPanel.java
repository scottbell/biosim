package biosim.client.simulation.water.gui;

import biosim.client.simulation.framework.gui.*;
/** 
 * This is the JPanel that displays information about the Water
 *
 * @author    Scott Bell
 */

public class WaterPanel extends SimTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new WaterTextPanel();
		myChartPanel = new WaterChartPanel();
		mySchematicPanel = new WaterSchematicPanel();
	}
}