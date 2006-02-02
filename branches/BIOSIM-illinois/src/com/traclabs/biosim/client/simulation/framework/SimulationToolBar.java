package com.traclabs.biosim.client.simulation.framework;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioDriver;

public class SimulationToolBar extends JToolBar {

	private BioDriver myDriver;

	private JButton myStartSimButton;

	private JButton myPauseSimButton;

	private JButton myAdvanceSimButton;

	// Icons
	private ImageIcon startIcon;

	private ImageIcon playIcon;

	private ImageIcon stopIcon;

	private ImageIcon pauseIcon;

	private ImageIcon forwardIcon;

	private Action myStartAction;

	private Action myPauseAction;

	private Action myAdvanceAction;

	public SimulationToolBar() {
		myDriver = BioHolderInitializer.getBioHolder().theBioDriver;
		buildGUI();
	}

	private void buildGUI() {
		loadIcons();
		myStartAction = new StartSimulationAction("Start");
		myPauseAction = new PauseSimulationAction("Pause");
		myAdvanceAction = new AdvanceTickSimulationAction("Advance");

		myStartSimButton = add(myStartAction);
		myStartSimButton.setToolTipText("Starts the simulation");
		myStartSimButton.setText("");
		myStartSimButton.setIcon(startIcon);
		myStartSimButton.setEnabled(true);
		myPauseSimButton = add(myPauseAction);
		myPauseSimButton.setToolTipText("Pauses the simulation");
		myPauseSimButton.setIcon(pauseIcon);
		myPauseSimButton.setText("");
		myPauseSimButton.setEnabled(true);
		myAdvanceSimButton = add(myAdvanceAction);
		myAdvanceSimButton
				.setToolTipText("Advances the simulation one timestep");
		myAdvanceSimButton.setText("");
		myAdvanceSimButton.setIcon(forwardIcon);
		myAdvanceSimButton.setEnabled(false);
		refresh();
	}

	private void loadIcons() {
		startIcon = new ImageIcon(SimDesktop.class.getClassLoader()
				.getResource("com/traclabs/biosim/client/framework/start.png"));
		playIcon = new ImageIcon(SimDesktop.class.getClassLoader().getResource(
				"com/traclabs/biosim/client/framework/play.png"));
		stopIcon = new ImageIcon(SimDesktop.class.getClassLoader().getResource(
				"com/traclabs/biosim/client/framework/stop.png"));
		pauseIcon = new ImageIcon(SimDesktop.class.getClassLoader()
				.getResource("com/traclabs/biosim/client/framework/pause.png"));
		forwardIcon = new ImageIcon(
				SimDesktop.class.getClassLoader().getResource(
						"com/traclabs/biosim/client/framework/forward.png"));
	}

	public void refresh() {
		// Simulation has started
		if (myDriver.isStarted()) {
			myStartSimButton.setToolTipText("Ends the simulation");
			myStartSimButton.setIcon(stopIcon);
			myStartSimButton.setEnabled(true);
			myAdvanceSimButton.setEnabled(true);
		}
		// Simulation has stopped
		else {
			myStartSimButton.setToolTipText("Starts the simulation");
			myStartSimButton.setIcon(startIcon);
			myStartSimButton.setEnabled(true);
			myAdvanceSimButton.setEnabled(false);
		}
		// Simulation has paused
		if (myDriver.isPaused()) {
			myPauseSimButton.setToolTipText("Resume the simulation");
			myPauseSimButton.setIcon(playIcon);
			if (myDriver.isStarted()) {
				myAdvanceSimButton.setEnabled(true);
			}
		}
		// Simulation has resumed
		else {
			myPauseSimButton.setToolTipText("Pause the simulation");
			myPauseSimButton.setIcon(pauseIcon);
			myAdvanceSimButton.setEnabled(false);
		}
	}
	
    /**
     * Action describing a simulation start/stop (depending on the button
     * state). Enables/disables various buttons on the SimDesktop and invokes
     * BioSimulator's stop or start method.
     */
    private class StartSimulationAction extends AbstractAction {
        public StartSimulationAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            //We want to stop the simulation
            if (myDriver.isStarted()) {
    			getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                myStartSimButton.setToolTipText("Starts the simulation");
                myStartSimButton.setIcon(startIcon);
                myAdvanceSimButton.setEnabled(false);
                myDriver.endSimulation();
    			getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
            }
            //We want to start the simulation
            else {
            	getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                myStartSimButton.setToolTipText("Ends the simulation");
                myStartSimButton.setIcon(stopIcon);
                myAdvanceSimButton.setEnabled(true);
                //If we're paused, stay paused.
                if (myDriver.isPaused()) {
                    myPauseSimButton.setToolTipText("Resume the simulation");
                    myPauseSimButton.setIcon(playIcon);
                    myAdvanceSimButton.setEnabled(true);
                }
                //If we're not paused, start ticking immediatly
                else {
                    myPauseSimButton.setToolTipText("Pause the simulation");
                    myPauseSimButton.setIcon(pauseIcon);
                    myAdvanceSimButton.setEnabled(false);
                }
                //Tell the biosimulator to enter loop
                myDriver.startSimulation();
                getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
                
            }
        }
    }

    /**
     * Action describing a simulation pause/resume (depending on the button
     * state). Enables/disables various buttons on the SimDesktop and invokes
     * BioSimulator's pause or resume method.
     */
    private class PauseSimulationAction extends AbstractAction {
        public PauseSimulationAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
        	getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //We want to resume the simulation
            if (myDriver.isPaused()) {
                myPauseSimButton.setToolTipText("Pause the simulation");
                myPauseSimButton.setIcon(pauseIcon);
                myAdvanceSimButton.setEnabled(false);
                myDriver.setPauseSimulation(false);
            }
            //We want to pause the simulation
            else {
                myPauseSimButton.setToolTipText("Resume the simulation");
                myPauseSimButton.setIcon(playIcon);
                if (myDriver.isStarted()) {
                    myAdvanceSimButton.setEnabled(true);
                }
                myDriver.setPauseSimulation(true);
            }
            getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action describing a simulation advance of one tick. Enables/disables
     * various buttons on the SimDesktop and invokes BioSimulator's tick method.
     * Used only when BioSimulator is paused.
     */
    private class AdvanceTickSimulationAction extends AbstractAction {
        public AdvanceTickSimulationAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
        	getTopLevelAncestor().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myDriver.advanceOneTick();
            getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
        }
    }
}
