package biosim.client.water.gui;

import biosim.client.framework.gui.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Water
 *
 * @author    Scott Bell
 */

public class WaterPanel extends BioTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new WaterTextPanel();
		myChartPanel = new WaterChartPanel();
		mySchematicPanel = new BioTabPanel();
	}
}
