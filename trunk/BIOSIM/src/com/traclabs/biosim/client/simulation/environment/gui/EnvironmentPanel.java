package biosim.client.environment.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the environment (gas levels, current time, etc.)
 *
 * @author    Scott Bell
 */

public class EnvironmentPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	private JPanel myEnvironmentTextPanel;
	private BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public EnvironmentPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myEnvironmentTextPanel = new EnvironmentTextPanel(myBioSimulator);
		myTabbedPane.addTab("Text", myEnvironmentTextPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
