package biosim.client.power.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Power
 *
 * @author    Scott Bell
 */

public class PowerPanel extends BioTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new PowerTextPanel();
		myChartPanel = new BioTabPanel();
		mySchematicPanel = new BioTabPanel();
	}
}
