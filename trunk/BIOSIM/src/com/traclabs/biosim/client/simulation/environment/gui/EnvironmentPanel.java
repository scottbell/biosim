package biosim.client.environment.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Environment
 *
 * @author    Scott Bell
 */

public class EnvironmentPanel extends BioTabbedPanel
{
	protected void createPanels(){
		myTextPanel = new EnvironmentTextPanel();
		myChartPanel = new BioTabPanel();
		mySchematicPanel = new BioTabPanel();
	}
}
