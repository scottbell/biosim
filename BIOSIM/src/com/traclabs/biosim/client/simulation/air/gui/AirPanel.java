package biosim.client.air.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Air
 *
 * @author    Scott Bell
 */

public class AirPanel extends BioTabbedPanel
{
	public AirPanel(BioSimulator pBioSimulator){
		super(pBioSimulator);
	}
	
	protected void createPanels(){
		myTextPanel = new AirTextPanel(myBioSimulator);
		myChartPanel = new JPanel();
		mySchematicPanel = new JPanel();
	}
}
