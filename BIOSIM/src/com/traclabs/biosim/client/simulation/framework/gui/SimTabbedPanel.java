package biosim.client.framework.gui;

import biosim.client.framework.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 * This is the JPanel that displays information about the a bio module
 *
 * @author    Scott Bell
 */

public abstract class BioTabbedPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	protected BioTabPanel myTextPanel;
	protected BioTabPanel myChartPanel;
	protected BioTabPanel mySchematicPanel;
	protected BioSimulator myBioSimulator;
	private int oldSelectedIndex;
	private UpdateActionListener myUpdateActionListener;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public BioTabbedPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myUpdateActionListener = new UpdateActionListener();
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
		oldSelectedIndex = myTabbedPane.getSelectedIndex();
		add(myTabbedPane, BorderLayout.CENTER);
		myTabbedPane.addChangeListener(new TabChangeListener());
	}

	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void actionPerformed(ActionEvent e){
		System.out.println("Something happened");
		if (myTabbedPane.getSelectedIndex() == 0){
			myTextPanel.processUpdate();
		}
		else if (myTabbedPane.getSelectedIndex() == 1){
			myChartPanel.processUpdate();
		}
		else if (myTabbedPane.getSelectedIndex() == 2){
		}
	}
	
	public void visibilityChange(boolean isVisible){
		if (isVisible)
			myBioSimulator.addDisplayListener(myUpdateActionListener);
		else
			myBioSimulator.removeDisplayListener(myUpdateActionListener);
		myTextPanel.visibilityChange(isVisible);
		myChartPanel.visibilityChange(isVisible);
		mySchematicPanel.visibilityChange(isVisible);
	}
	
	private class UpdateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			if (myTabbedPane.getSelectedIndex() == 0){
				myTextPanel.processUpdate();
			}
			else if (myTabbedPane.getSelectedIndex() == 1){
				myChartPanel.processUpdate();
			}
			else if (myTabbedPane.getSelectedIndex() == 2){
				mySchematicPanel.processUpdate();
			}
		}
	}

	private class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
			//Notify panel that we lost focus
			if (oldSelectedIndex == 0){
				myTextPanel.lostFocus();
			}
			else if (oldSelectedIndex == 1){
				myChartPanel.lostFocus();
			}
			else if (oldSelectedIndex== 2){
				mySchematicPanel.lostFocus();
			}
			//Notify panel that we gained focus
			if (myTabbedPane.getSelectedIndex() == 0){
				myTextPanel.processUpdate();
				myTextPanel.gotFocus();
			}
			else if (myTabbedPane.getSelectedIndex() == 1){
				myChartPanel.processUpdate();
				myChartPanel.gotFocus();
			}
			else if (myTabbedPane.getSelectedIndex() == 2){
				mySchematicPanel.processUpdate();
				mySchematicPanel.gotFocus();
			}
			oldSelectedIndex = myTabbedPane.getSelectedIndex();
		}
	}
}
