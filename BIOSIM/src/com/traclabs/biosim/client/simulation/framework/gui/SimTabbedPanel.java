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
	protected UpdatablePanel myTextPanel;
	protected UpdatablePanel myChartPanel;
	protected UpdatablePanel mySchematicPanel;
	private int oldSelectedIndex = -1;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public BioTabbedPanel(){
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
		myTabbedPane.addChangeListener(new TabChangeListener());
		alterVisibility();
	}
	
	public void visibilityChange(boolean nowVisible){
		myTextPanel.visibilityChange(nowVisible);
		myChartPanel.visibilityChange(nowVisible);
		mySchematicPanel.visibilityChange(nowVisible);
	}
	
	private void alterVisibility(){
		//Notify panel that we lost focus
			if (oldSelectedIndex == 0){
				myTextPanel.visibilityChange(false);
			}
			else if (oldSelectedIndex == 1){
				myChartPanel.visibilityChange(false);
			}
			else if (oldSelectedIndex== 2){
				mySchematicPanel.visibilityChange(false);
			}
			//Notify panel that we gained focus
			if (myTabbedPane.getSelectedIndex() == 0){
				myTextPanel.visibilityChange(true);
			}
			else if (myTabbedPane.getSelectedIndex() == 1){
				myChartPanel.visibilityChange(true);
			}
			else if (myTabbedPane.getSelectedIndex() == 2){
				mySchematicPanel.visibilityChange(true);
			}
			oldSelectedIndex = myTabbedPane.getSelectedIndex();
	}

	private class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
			alterVisibility();
		}
	}
}
