package biosim.client.air.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Air
 *
 * @author    Scott Bell
 */

public class AirPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	private JPanel myAirTextPanel;
	private BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public AirPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myAirTextPanel = new AirTextPanel(myBioSimulator);
		myTabbedPane.addTab("Text", myAirTextPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
