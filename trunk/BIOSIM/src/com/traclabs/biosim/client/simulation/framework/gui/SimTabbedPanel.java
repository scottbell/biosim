package biosim.client.framework.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the a bio module
 *
 * @author    Scott Bell
 */

public abstract class BioTabbedPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	protected JPanel myTextPanel;
	protected JPanel myChartPanel;
	protected JPanel mySchematicPanel;
	protected BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public BioTabbedPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		createPanels();
		buildGui();
	}
	
	protected abstract void createPanels();
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myTabbedPane.addTab("Text", myTextPanel);
		myTabbedPane.addTab("Chart", myChartPanel);
		myTabbedPane.addTab("Schematic", mySchematicPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
