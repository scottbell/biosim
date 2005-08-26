package com.traclabs.biosim.client.framework.apollo13;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.framework.BioFrame;
import com.traclabs.biosim.client.simulation.framework.SimulationPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.util.OrbUtils;

public class Apollo13Viewer extends SimulationPanel {
	private Logger myLogger;

	private BioHolder myBioHolder;

	private float myO2Usage = 0f;

	private ApolloGraphPanel myGraphPanel;

	private StringBuilder myLogString;

	private float myO2FlowRate;

	private float myValveCommand;

	private float myValveState;

	private int myTicks;

	private float myO2Concentration;

	private float myCO2Concentration;

	private float myO2StoreLevel;

	private JMenuBar myMenuBar;

	private JMenu myFileMenu;

	private JMenu myNewMenu;

	private JMenuItem mySaveItem;

	private JMenuItem myQuitItem;

	private JMenu myHelpMenu;

	private JMenuItem myAboutItem;

	private JFileChooser myFileChooser;

	public Apollo13Viewer() {
		myLogger = Logger.getLogger(this.getClass());
		myBioHolder = BioHolderInitializer.getBioHolder();
		myBioHolder.theBioDriver.reset();
		myBioHolder.theBioDriver.setPauseSimulation(true);
		myBioHolder.theBioDriver.startSimulation();
		myGraphPanel = new ApolloGraphPanel();
		myGraphPanel.setBorder(BorderFactory.createTitledBorder("Data"));
		add(myGraphPanel, BorderLayout.CENTER);
		myLogString = new StringBuilder();
		myLogString
				.append("timetag,O2 flow rate,Valve command,Valve state,O2 concentration in cabin,CO2 concentration in cabin,O2 tank pressure,O2 usage over timestep");

		myMenuBar = new JMenuBar();
		myFileMenu = new JMenu("File");
		myHelpMenu = new JMenu("Help");
		myFileMenu.setMnemonic(KeyEvent.VK_F);
		mySaveItem = myFileMenu.add(new SaveAction());
		mySaveItem.setText("Save");
		myQuitItem = myFileMenu.add(new QuitAction());
		myQuitItem.setText("Quit");
		myFileMenu.add(mySaveItem);
		myFileMenu.add(myQuitItem);
		myAboutItem = myHelpMenu.add(new AboutAction());
		myAboutItem.setText("About");
		myQuitItem.setMnemonic(KeyEvent.VK_Q);
		myMenuBar.add(myFileMenu);
		myMenuBar.add(myHelpMenu);

		myFileChooser = new JFileChooser();
		myFileChooser.setApproveButtonText("Save");
	}

	protected synchronized void refresh() {
		if (!myBioHolder.theBioDriver.isDone()) {
			collectData();
			log();
			myBioHolder.theBioDriver.advanceOneTick();
			myGraphPanel.refresh(myTicks, myO2FlowRate, myValveCommand,
					myValveState, myO2Concentration, myCO2Concentration,
					myO2StoreLevel, myO2Usage);
		} else
			stopRefresh();

	}

	private void collectData() {
		myTicks = myBioHolder.theBioDriver.getTicks();
		myO2FlowRate = convertO2MolesToPounds(myBioHolder.theO2OutFlowRateSensors
				.get(0).getValue());
		myValveCommand = processValveCommand(myBioHolder.theInfluentValveActuators
				.get(0).getValue());
		myValveState = processValveState(myBioHolder.theInfluentValveStateSensors
				.get(0).getValue());
		myO2Concentration = myBioHolder.theGasConcentrationSensors.get(0)
				.getValue();
		myCO2Concentration = myBioHolder.theGasConcentrationSensors.get(1)
				.getValue();
		myO2StoreLevel = convertO2MolesToPounds(myBioHolder.theStoreLevelSensors
				.get(0).getValue());
		myO2Usage += myO2FlowRate;
	}

	protected void reset() {
		myBioHolder.theBioDriver.reset();
		myGraphPanel.reset();
	}

	private void log() {
		myLogString.append(myTicks);
		myLogString.append(",");
		myLogString.append(myO2FlowRate);
		myLogString.append(",");
		myLogString.append(myValveCommand);
		myLogString.append(",");
		myLogString.append(myValveState);
		myLogString.append(",");
		myLogString.append(myO2Concentration);
		myLogString.append(",");
		myLogString.append(myCO2Concentration);
		myLogString.append(",");
		myLogString.append(myO2StoreLevel);
		myLogString.append(",");
		myLogString.append(myO2Usage);
		myLogString.append("\n");
	}

	private float processValveState(float valveSensorValue) {
		if (valveSensorValue == 0)
			return 1;
		if (valveSensorValue == 1)
			return 2;
		else
			return 0;
	}

	private float processValveCommand(float valveActuatorValue) {
		if (valveActuatorValue == 0)
			return 1;
		if (valveActuatorValue == 1)
			return 2;
		else
			return 0;
	}

	public static void main(String[] strings) {
		OrbUtils.initializeLog();
		Apollo13Viewer newViewer = new Apollo13Viewer();
		BioFrame myFrame = new BioFrame("Apollo 13 Viewer", false);
		myFrame.getContentPane().add(newViewer);
		myFrame.pack();
		myFrame.setSize(800, 600);
		ImageIcon biosimIcon = new ImageIcon(Apollo13Viewer.class
				.getClassLoader().getResource(
						"com/traclabs/biosim/client/framework/gear.png"));
		myFrame.setIconImage(biosimIcon.getImage());
		myFrame.setJMenuBar(newViewer.getMenuBar());
		myFrame.setVisible(true);
	}

	private JMenuBar getMenuBar() {
		return myMenuBar;
	}

	private float convertO2MolesToPounds(float o2InMoles) {
		float molecularWeightOfO2 = 31.998f;
		float o2InGrams = o2InMoles * molecularWeightOfO2;
		float o2InPounds = 0.00220462262f * o2InGrams;
		return o2InPounds;
	}

	/**
	 * Action that saves data to csv file
	 */
	private class SaveAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			int returnVal = myFileChooser.showSaveDialog(Apollo13Viewer.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = myFileChooser.getSelectedFile();
				try {
					FileWriter theFileWriter = new FileWriter(file);
					theFileWriter.write(myLogString.toString());
					theFileWriter.close();
				} catch (IOException e) {
				    JOptionPane.showMessageDialog(null, "Couldn't save file", "IO Error", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	 * Quits the program
	 */
	private class QuitAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			System.exit(0);
		}
	}

	/**
	 * Action that brings up a dialog box about authors, company, etc.
	 */
	private class AboutAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			JOptionPane
					.showMessageDialog(
							Apollo13Viewer.this,
							"Apollo 13 Simulation: \nCopyright "
									+ new Character('\u00A9')
									+ " 2005, TRACLabs\nby Scott Bell and David Kortenkamp");
		}
	}
}
