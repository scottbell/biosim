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
	protected void createPanels(){
		myTextPanel = new FoodTextPanel();
		myChartPanel = new BioTabPanel();
		mySchematicPanel = new BioTabPanel();
	}
}
