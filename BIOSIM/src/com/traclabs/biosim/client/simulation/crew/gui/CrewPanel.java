package biosim.client.crew.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Crew
 *
 * @author    Scott Bell
 */

public class CrewPanel extends BioTabbedPanel
{
	public CrewPanel(BioSimulator pBioSimulator){
		super(pBioSimulator);
	}
	
	protected void createPanels(){
		myTextPanel = new CrewTextPanel(myBioSimulator);
		myChartPanel = new JPanel();
		mySchematicPanel = new JPanel();
	}
}
