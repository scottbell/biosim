package com.traclabs.biosim.client.simulation.framework;

import java.awt.Cursor;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.client.util.Fnorder;
import com.traclabs.biosim.idl.framework.BioDriver;

public class SimulationToolBar extends JToolBar {
	private JButton myResetButton;

	private final static String myResetToolTipText = "Reset the simulation.";

	private JButton myTickButton;

	private final static String myTickToolTipText = "Advance the simulation one tick";

	private JButton myPlayPauseButton;

	private final static String myPlayToolTipText = "Run the simulation";

	private final static String myPauseToolTipText = "Pause the simulation";

	private BioDriver myBioDriver;

	// Icons
	private ImageIcon myResetIcon;

	private ImageIcon myPlayIcon;

	private ImageIcon myPauseIcon;

	private ImageIcon myTickIcon;

	private Action myPlayPauseButtonAction;

	public SimulationToolBar() {
		myBioDriver = BioHolderInitializer.getBioHolder().theBioDriver;
		buildGUI();
	}

	private void buildGUI() {
		loadIcons();
		myResetButton = new JButton(new ResetButtonAction());
		myResetButton.setIcon(myResetIcon);
		myResetButton.setToolTipText(myPlayToolTipText);
		myTickButton = new JButton(new TickButtonAction());
		myTickButton.setIcon(myTickIcon);
		myTickButton.setToolTipText(myTickToolTipText);
		myPlayPauseButtonAction = new PlayPauseButtonAction();
		myPlayPauseButton = new JButton(myPlayPauseButtonAction);
		myPlayPauseButton.setIcon(myPlayIcon);
		myPlayPauseButton.setToolTipText(myPlayToolTipText);
		add(myResetButton);
		add(myPlayPauseButton);
		add(myTickButton);
		refresh();
	}

	private void loadIcons() {
		myResetIcon = new ImageIcon(SimulationPanel.class.getClassLoader()
				.getResource("com/traclabs/biosim/client/framework/start.png"));

		myTickIcon = new ImageIcon(
				SimulationPanel.class.getClassLoader().getResource(
						"com/traclabs/biosim/client/framework/forward.png"));
		myPlayIcon = new ImageIcon(SimulationPanel.class.getClassLoader()
				.getResource("com/traclabs/biosim/client/framework/play.png"));

		myPauseIcon = new ImageIcon(SimulationPanel.class.getClassLoader()
				.getResource("com/traclabs/biosim/client/framework/pause.png"));
	}

	private class PlayPauseButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			getTopLevelAncestor().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myBioDriver.setPauseSimulation(!myBioDriver.isPaused());
			getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
			refresh();
		}
	}

	private class TickButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			getTopLevelAncestor().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myBioDriver.advanceOneTick();
			if (ae.getModifiers() == (ActionEvent.CTRL_MASK + 16)) {
				Fnorder myFnord = new Fnorder();
				String message = myFnord.getFnord();
				ImageIcon fnordIcon = new ImageIcon(
						SimulationPanel.class
								.getClassLoader()
								.getResource(
										"com/traclabs/biosim/client/framework/pyramid.png"));
				JOptionPane fnordPane = new JOptionPane(message,
						JOptionPane.INFORMATION_MESSAGE,
						JOptionPane.DEFAULT_OPTION, fnordIcon);
				JDialog dialog = fnordPane.createDialog(null,
						"Your message from the Illuminati");
				dialog.setVisible(true);
			}
			getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ResetButtonAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			getTopLevelAncestor().setCursor(
					Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if(!myBioDriver.isStarted())
						myBioDriver.startSimulation();
			else
				myBioDriver.reset();
			getTopLevelAncestor().setCursor(Cursor.getDefaultCursor());
		}
	}

	public void refresh() {
		// Simulation has paused
		if (myBioDriver.isPaused()) {
			myPlayPauseButton.setIcon(myPlayIcon);
			myPlayPauseButton.setToolTipText(myPlayToolTipText);
		}
		// Simulation has resumed
		else {
			myPlayPauseButton.setIcon(myPauseIcon);
			myPlayPauseButton.setToolTipText(myPauseToolTipText);
		}
	}
}
