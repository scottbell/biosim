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
	public PowerPanel(BioSimulator pBioSimulator){
		super(pBioSimulator);
	}
	
	protected void createPanels(){
		myTextPanel = new PowerTextPanel(myBioSimulator);
		myChartPanel = new BioTabPanel();
		mySchematicPanel = new BioTabPanel();
	}
}
