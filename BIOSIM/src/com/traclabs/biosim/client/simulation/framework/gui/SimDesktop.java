package biosim.client.control.gui;

import biosim.client.control.*;
import biosim.client.gui.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SimDesktop extends BaseJFrame
{
	//The simulator
	private BioSimulator myBiosim;
	
	//GUI Componenets
	private JDesktopPane myDesktop;
	private JToolBar myToolBar;
	private JButton myEndSimButton;
	private JButton myStartSimButton;
	private JButton myPauseSimButton;
	private JButton myAdvanceSimButton;
	
	private Action myEndAction;
	private Action myStartAction;
	private Action myPauseAction;
	private Action myAdvanceAction;
	
	//GUI properties
	private int openFrameCount = 0;
	private int xOffset = 30, yOffset = 30;
	private boolean isPaused = false;
	
	public SimDesktop(){
		myBiosim = new BioSimulator();
		buildGUI();
	}

	private void buildGUI(){
		myDesktop = new JDesktopPane();
		myDesktop.setLayout(new BorderLayout());
		
		myEndAction = new EndSimulationAction("End Simulation");
		myStartAction = new StartSimulationAction("Start Simulation");
		myPauseAction = new PauseSimulationAction("Pause Simulation");
		myAdvanceAction = new AdvanceTickSimulationAction("Advance Simulation");
		
		myToolBar = new JToolBar();
		myStartSimButton = myToolBar.add(myStartAction);
		myStartSimButton.setToolTipText("Starts the simulation");
		myStartSimButton.setText("Start");
		myAdvanceSimButton = myToolBar.add(myAdvanceAction);
		myAdvanceSimButton.setToolTipText("Advances the simulation one timestep");
		myAdvanceSimButton.setText("+");
		myAdvanceSimButton.setEnabled(false);
		myPauseSimButton = myToolBar.add(myPauseAction);
		myPauseSimButton.setToolTipText("Pauses the simulation");
		myPauseSimButton.setText("Pause");
		myPauseSimButton.setEnabled(false);
		myEndSimButton = myToolBar.add(myEndAction);
		myEndSimButton.setToolTipText("Ends the simulation");
		myEndSimButton.setText("End");
		myEndSimButton.setEnabled(false);
		myDesktop.add(myToolBar, BorderLayout.NORTH);
		
		setTitle("Advanced Life Support Simulation");
		getContentPane().add(myDesktop);
	}
	
	private void addInternalFrame(JInternalFrame newFrame){
		myDesktop.add(newFrame);
		openFrameCount = myDesktop.getAllFrames().length;
		newFrame.setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		newFrame.moveToFront();
		newFrame.setVisible(true);
	}
	
	private class StartSimulationAction extends AbstractAction{
		public StartSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myStartSimButton.setEnabled(false);
			myPauseSimButton.setEnabled(true);
			myEndSimButton.setEnabled(true);
			myAdvanceSimButton.setEnabled(false);
			myBiosim.spawnSimulation();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class EndSimulationAction extends AbstractAction{
		public EndSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myStartSimButton.setEnabled(true);
			myPauseSimButton.setEnabled(false);
			myEndSimButton.setEnabled(false);
			myAdvanceSimButton.setEnabled(false);
			myBiosim.endSimulation();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class PauseSimulationAction extends AbstractAction{
		public PauseSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			isPaused = !isPaused;
			if (isPaused){
				myPauseSimButton.setToolTipText("Resume the simulation");
				myPauseSimButton.setText("Resume");
				myAdvanceSimButton.setEnabled(true);
				myBiosim.pauseSimulation();
			}
			else{
				myPauseSimButton.setToolTipText("Pause the simulation");
				myPauseSimButton.setText("Pause");
				myAdvanceSimButton.setEnabled(false);
				myBiosim.resumeSimulation();
			}
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class AdvanceTickSimulationAction extends AbstractAction{
		public AdvanceTickSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myBiosim.advanceOneTick();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}
}

