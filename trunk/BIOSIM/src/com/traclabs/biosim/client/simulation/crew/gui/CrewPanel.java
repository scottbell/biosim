package biosim.client.crew.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Crew
 *
 * @author    Scott Bell
 */

public class CrewPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	private JPanel myCrewTextPanel;
	private BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public CrewPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myCrewTextPanel = new CrewTextPanel(myBioSimulator);
		myTabbedPane.addTab("Text", myCrewTextPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
