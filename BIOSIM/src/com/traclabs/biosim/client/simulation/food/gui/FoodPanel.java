package biosim.client.food.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Food
 *
 * @author    Scott Bell
 */

public class FoodPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	private JPanel myFoodTextPanel;
	private BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public FoodPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myFoodTextPanel = new FoodTextPanel(myBioSimulator);
		myTabbedPane.addTab("Text", myFoodTextPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
