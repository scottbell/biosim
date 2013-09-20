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
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
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

	private float myO2FlowPerHour;

	private ApolloGraphPanel myGraphPanel;

	private StringBuilder myLogString;

	private float myO2UsageOverTimestep;

	private float myValveCommand;

	private float myValveState;

	private int myTicks;

	private float myTimeInSeconds;

	private float myO2Concentration;

	private float myCO2Concentration;

	private float myO2StoreLevel;

	private boolean loggingOnly = false;

	private JMenuBar myMenuBar;

	private JMenu myFileMenu;

	private JMenu myOptionsMenu;

	private JMenu myNewMenu;

	private JMenuItem mySaveItem;

	private JMenuItem myQuitItem;

	private JCheckBoxMenuItem myLogOnlyItem;

	private JMenu myHelpMenu;

	private JMenuItem myAboutItem;

	private JFileChooser myFileChooser;

	private int myMalfunctionState;
	
    private JButton myLogOnlyButton;

	private ImageIcon myGraphingIcon;

	private ImageIcon myNotGraphingIcon;
	
    private final static String myGraphingToolTipText = "Stop graphing the simulation (logging only)";
    private final static String myNotGraphingToolTipText = "Start graphing the simulation";
    

	public Apollo13Viewer() {
		myLogger = Logger.getLogger(this.getClass());
		myBioHolder = BioHolderInitializer.getBioHolder();
		myGraphPanel = new ApolloGraphPanel();
		myGraphPanel.setBorder(BorderFactory.createTitledBorder("Data"));
		add(myGraphPanel, BorderLayout.CENTER);
		myLogString = new StringBuilder();
		createHeader();

		myMenuBar = new JMenuBar();
		myFileMenu = new JMenu("File");
		myOptionsMenu = new JMenu("Options");
		myHelpMenu = new JMenu("Help");
		myFileMenu.setMnemonic(KeyEvent.VK_F);
		myOptionsMenu.setMnemonic(KeyEvent.VK_E);
		mySaveItem = myFileMenu.add(new SaveAction());
		mySaveItem.setText("Save");
		myQuitItem = myFileMenu.add(new QuitAction());
		myQuitItem.setText("Quit");
		myAboutItem = myHelpMenu.add(new AboutAction());
		myAboutItem.setText("About");
		myLogOnlyItem = new JCheckBoxMenuItem(new LogOnlyAction());
		myLogOnlyItem.setText("Log Only");
		myLogOnlyItem = new JCheckBoxMenuItem(new LogOnlyAction());
		myLogOnlyItem.setText("Log Only");
		myOptionsMenu.add(myLogOnlyItem);
		myQuitItem.setMnemonic(KeyEvent.VK_Q);
		myMenuBar.add(myFileMenu);
		myMenuBar.add(myOptionsMenu);
		myMenuBar.add(myHelpMenu);

		loadIcons();
		myLogOnlyButton = new JButton(new LogOnlyAction());
		myLogOnlyButton.setToolTipText(myGraphingToolTipText);
		myLogOnlyButton.setIcon(myGraphingIcon);
		getButtonBar().add(myLogOnlyButton);

		myFileChooser = new JFileChooser();
		myFileChooser.setApproveButtonText("Save");
		reset();
	}
	
	private void loadIcons(){
    	myGraphingIcon = new ImageIcon(
    			SimulationPanel.class.getClassLoader()
            .getResource(
                    "com/traclabs/biosim/client/framework/zoom-out.png"));
    	myNotGraphingIcon = new ImageIcon(
    			SimulationPanel.class.getClassLoader()
            .getResource(
                    "com/traclabs/biosim/client/framework/zoom-in.png"));
    }

	private void createHeader() {
		myLogString.append("TIME,CF1001F,SF1003Q,CF0035R,SF1004Q,FAULT1,FAULT2,FAULT3\n");
		myLogString
				.append("Description,Isolation Valve Command ECS O2,Atmosphere concentration ECS O2,Flowrate ECS O2,Atmosphere concentration ECS CO2,Fault Insertion Variable,Fault Insertion Variable,Fault Insertion Variable\n");
		myLogString.append("SYS_CMMD/L1DA,SYS_CMMD,L1DA,L1DA,L1DA,USER,USER,USER\n");
		myLogString.append("Units,BINARY,CONCEN,FLOW_LB,CONCEN,,,\n");
		myLogString.append("Vehicle,Apollo,Apollo,Apollo,Apollo,NULL,NULL,NULL\n");
		myLogString.append("System,CM,CM,CM,CM,NULL,NULL,NULL\n");
		myLogString.append("Subsystem,ECLS,ECLS,ECLS,ECLS,NULL,NULL,NULL\n");
		myLogString
				.append("Component,oxygen,oxygen,oxygen,carbon dioxide,NULL,NULL,NULL\n");
	}

	protected synchronized void refresh() {
		if (!myBioHolder.theBioDriver.isDone()) {
			collectData();
			log();
			myBioHolder.theBioDriver.advanceOneTick();
			if (!loggingOnly)
				myGraphPanel.refresh(myTicks, myO2UsageOverTimestep,
						myValveCommand, myValveState, myO2Concentration,
						myCO2Concentration, myO2StoreLevel, myO2FlowPerHour);
		} else {
			stopRefresh();
			try {
				File tempFile = File.createTempFile("apollo13-", ".csv");
				saveLog(tempFile);
				JOptionPane.showMessageDialog(Apollo13Viewer.this,
						"Simulation has completed. Results temporarily saved to:\n"
								+ tempFile.getCanonicalPath());
			} catch (IOException e) {
				JOptionPane
						.showMessageDialog(Apollo13Viewer.this,
								"Simulation has completed. Save the results if you like.");
			}
		}

	}

	private void collectData() {
		myTicks = myBioHolder.theBioDriver.getTicks();
		myTimeInSeconds = (myTicks * myBioHolder.theBioDriver.getTickLength() * 3600f);
		myO2UsageOverTimestep = convertO2MolesToPounds(myBioHolder.theO2OutFlowRateSensors
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
		myO2FlowPerHour = convertO2MolesToPounds(myBioHolder.theO2OutFlowRateSensors
				.get(0).getValue())
				/ myBioHolder.theBioDriver.getTickLength();
		myMalfunctionState = processMalfunctionState(myBioHolder.theInfluentValves.get(0).isMalfunctioning());
	}

	protected void reset() {
		myBioHolder.theBioDriver.setPauseSimulation(true);
		myBioHolder.theBioDriver.startSimulation();
		myGraphPanel.reset();
	}

	private void log() {
		myLogString.append(myTimeInSeconds);
		myLogString.append(",");
		myLogString.append(myValveCommand);
		myLogString.append(",");
		myLogString.append(myO2Concentration);
		myLogString.append(",");
		myLogString.append(myO2FlowPerHour);
		myLogString.append(",");
		myLogString.append(myCO2Concentration);
		myLogString.append(",");
		myLogString.append(myMalfunctionState);
		myLogString.append(",");
		myLogString.append(0);
		myLogString.append(",");
		myLogString.append(0);
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
	

	private int processMalfunctionState(boolean isMalfunctioning) {
		if (isMalfunctioning)
			return 1;
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

	private void saveLog(File pFile) {
		try {
			FileWriter theFileWriter = new FileWriter(pFile);
			theFileWriter.write(myLogString.toString());
			theFileWriter.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't save file",
					"IO Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
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
				saveLog(file);
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
			JOptionPane.showMessageDialog(Apollo13Viewer.this,
					"Apollo 13 Simulation");
		}
	}

	/**
	 * Doesn't update graphs. When done, displays dialog box.
	 */
	private class LogOnlyAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			loggingOnly = !loggingOnly;
			if (loggingOnly){
				myLogOnlyItem.setSelected(true);
				myLogOnlyButton.setToolTipText(myNotGraphingToolTipText);
				myLogOnlyButton.setIcon(myNotGraphingIcon);
			}
			else{
				myLogOnlyItem.setSelected(false);
				myLogOnlyButton.setToolTipText(myGraphingToolTipText);
				myLogOnlyButton.setIcon(myGraphingIcon);
			}
		}
	}
}
