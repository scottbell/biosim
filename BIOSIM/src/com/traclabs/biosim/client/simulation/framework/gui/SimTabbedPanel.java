package biosim.client.framework.gui;

import biosim.client.framework.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
/**
 * This is the JPanel that displays information about the a bio module
 *
 * @author    Scott Bell
 */

public abstract class BioTabbedPanel extends JPanel implements BioSimulatorListener
{
	private JTabbedPane myTabbedPane;
	protected BioTabPanel myTextPanel;
	protected BioTabPanel myChartPanel;
	protected BioTabPanel mySchematicPanel;
	protected BioSimulator myBioSimulator;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public BioTabbedPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		createPanels();
		buildGui();
		myBioSimulator.registerListener(this);
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
		myTabbedPane.addChangeListener(new BioChangeListener());
	}

	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void processTick(){
		if (myTabbedPane.getSelectedIndex() == 0){
			myTextPanel.processTick();
		}
		else if (myTabbedPane.getSelectedIndex() == 1){
			myChartPanel.processTick();
		}
		else if (myTabbedPane.getSelectedIndex() == 2){
		}
	}

	private class BioChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
			if (myTabbedPane.getSelectedIndex() == 0){
				myTextPanel.processTick();
			}
			else if (myTabbedPane.getSelectedIndex() == 1){
				myChartPanel.processTick();
			}
			else if (myTabbedPane.getSelectedIndex() == 2){
			}
		}
	}
}
