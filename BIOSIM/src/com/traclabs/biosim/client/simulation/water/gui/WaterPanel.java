package biosim.client.water.gui;

import biosim.client.framework.*;
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
	public WaterPanel(BioSimulator pBioSimulator){
		super(pBioSimulator);
	}
	
	protected void createPanels(){
		myTextPanel = new WaterTextPanel(myBioSimulator);
		myChartPanel = new WaterChartPanel(myBioSimulator);
		mySchematicPanel = new JPanel();
	}
}
