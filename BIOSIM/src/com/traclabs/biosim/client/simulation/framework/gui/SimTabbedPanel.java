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
	private int oldSelectedIndex;
	private UpdateActionListener myUpdateActionListener;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public BioTabbedPanel(){
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
	
	public void visibilityChange(boolean nowVisible){
		if (nowVisible)
			SimTimer.addListener(myUpdateActionListener);
		else
			SimTimer.removeListener(myUpdateActionListener);
		myTextPanel.visibilityChange(nowVisible);
		myChartPanel.visibilityChange(nowVisible);
		mySchematicPanel.visibilityChange(nowVisible);
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
	
	private void updateSelectedTab(){
	}

	private class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e){
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
				myTextPanel.processUpdate();
				myTextPanel.visibilityChange(true);
			}
			else if (myTabbedPane.getSelectedIndex() == 1){
				myChartPanel.processUpdate();
				myChartPanel.visibilityChange(true);
			}
			else if (myTabbedPane.getSelectedIndex() == 2){
				mySchematicPanel.processUpdate();
				mySchematicPanel.visibilityChange(true);
			}
			oldSelectedIndex = myTabbedPane.getSelectedIndex();
		}
	}
}
