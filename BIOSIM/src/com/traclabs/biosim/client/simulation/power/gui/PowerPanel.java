package biosim.client.power.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Power
 *
 * @author    Scott Bell
 */

public class PowerPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	private JPanel myPowerTextPanel;
	private BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public PowerPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myPowerTextPanel = new PowerTextPanel(myBioSimulator);
		myTabbedPane.addTab("Text", myPowerTextPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
