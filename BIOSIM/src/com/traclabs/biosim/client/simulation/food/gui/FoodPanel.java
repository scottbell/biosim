package biosim.client.food.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Food
 *
 * @author    Scott Bell
 */

public class FoodPanel extends BioTabbedPanel
{
	public FoodPanel(BioSimulator pBioSimulator){
		super(pBioSimulator);
	}
	
	protected void createPanels(){
		myTextPanel = new FoodTextPanel(myBioSimulator);
		myChartPanel = new JPanel();
		mySchematicPanel = new JPanel();
	}
}
