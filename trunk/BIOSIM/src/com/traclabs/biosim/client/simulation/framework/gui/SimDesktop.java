package biosim.client.framework.gui;

import biosim.client.framework.*;
import biosim.client.gui.*;
import biosim.client.environment.gui.*;
import biosim.client.air.gui.*;
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
	private JMenuBar myMenuBar;
	private JMenu myFileMenu;
	private JMenu myNewMenu;
	private JMenuItem myShowAllDisplayItem;
	private JMenuItem myQuitItem;
	private JMenu myHelpMenu;
	private JMenuItem myAboutItem;
	private JMenu myControlMenu;
	private JMenuItem myEndSimItem;
	private JMenuItem myStartSimItem;
	private JMenuItem myPauseSimItem;
	private JMenuItem myAdvanceSimItem;

	private Action myEndAction;
	private Action myStartAction;
	private Action myPauseAction;
	private Action myAdvanceAction;
	private Action myAboutAction;
	private Action myShowAllDisplayAction;
	private Action myQuitAction;

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

		myEndAction = new EndSimulationAction("End");
		myStartAction = new StartSimulationAction("Start");
		myPauseAction = new PauseSimulationAction("Pause");
		myAdvanceAction = new AdvanceTickSimulationAction("Advance Tick");
		myAboutAction = new AboutAction("About");
		myShowAllDisplayAction = new ShowAllDisplaysAction("Show All Displays");
		myQuitAction = new QuitAction("Quit");

		myMenuBar = new JMenuBar();
		myFileMenu = new JMenu("File");
		myFileMenu.setMnemonic(KeyEvent.VK_F);
		myNewMenu = new JMenu("New");
		myNewMenu.setMnemonic(KeyEvent.VK_N);
		myShowAllDisplayItem = myNewMenu.add(myShowAllDisplayAction);
		myShowAllDisplayItem.setMnemonic(KeyEvent.VK_D);
		myFileMenu.add(myNewMenu);
		myQuitItem = myFileMenu.add(myQuitAction);
		myQuitItem.setMnemonic(KeyEvent.VK_Q);
		myControlMenu = new JMenu("Control");
		myControlMenu.setMnemonic(KeyEvent.VK_C);
		myStartSimItem = myControlMenu.add(myStartAction);
		myStartSimItem.setMnemonic(KeyEvent.VK_S);
		myAdvanceSimItem = myControlMenu.add(myAdvanceAction);
		myAdvanceSimItem.setMnemonic(KeyEvent.VK_A);
		myAdvanceSimItem.setEnabled(false);
		myPauseSimItem = myControlMenu.add(myPauseAction);
		myPauseSimItem.setMnemonic(KeyEvent.VK_P);
		myPauseSimItem.setEnabled(false);
		myEndSimItem = myControlMenu.add(myEndAction);
		myEndSimItem.setMnemonic(KeyEvent.VK_E);
		myEndSimItem.setEnabled(false);
		myHelpMenu = new JMenu("Help");
		myAboutItem = myHelpMenu.add(myAboutAction);
		myMenuBar.add(myFileMenu);
		myMenuBar.add(myControlMenu);
		myMenuBar.add(myHelpMenu);
		setJMenuBar(myMenuBar);

		myToolBar = new JToolBar();
		myToolBar.setFloatable(false);
		myStartSimButton = myToolBar.add(myStartAction);
		myStartSimButton.setToolTipText("Starts the simulation");
		myAdvanceSimButton = myToolBar.add(myAdvanceAction);
		myAdvanceSimButton.setToolTipText("Advances the simulation one timestep");
		myAdvanceSimButton.setEnabled(false);
		myPauseSimButton = myToolBar.add(myPauseAction);
		myPauseSimButton.setToolTipText("Pauses the simulation");
		myPauseSimButton.setEnabled(false);
		myEndSimButton = myToolBar.add(myEndAction);
		myEndSimButton.setToolTipText("Ends the simulation");
		myEndSimButton.setEnabled(false);
		getContentPane().add(myToolBar, BorderLayout.NORTH);
		
		setTitle("Advanced Life Support Simulation  Copyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs");
		getContentPane().add(myDesktop, BorderLayout.CENTER);
	}

	private void addInternalFrame(JInternalFrame newFrame){
		myDesktop.add(newFrame);
		openFrameCount = myDesktop.getAllFrames().length;
		newFrame.setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		newFrame.moveToFront();
		newFrame.setVisible(true);
	}

	protected void frameExiting(){
		if (myBiosim.simulationHasStarted())
			myBiosim.endSimulation();
	}

	private class StartSimulationAction extends AbstractAction{
		public StartSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myStartSimButton.setEnabled(false);
			myStartSimItem.setEnabled(false);
			myPauseSimButton.setEnabled(true);
			myPauseSimItem.setEnabled(true);
			myEndSimButton.setEnabled(true);
			myEndSimItem.setEnabled(true);
			myAdvanceSimButton.setEnabled(false);
			myAdvanceSimItem.setEnabled(false);
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
			myStartSimItem.setEnabled(true);
			myPauseSimButton.setEnabled(false);
			myPauseSimItem.setEnabled(false);
			myEndSimButton.setEnabled(false);
			myEndSimItem.setEnabled(false);
			myAdvanceSimButton.setEnabled(false);
			myAdvanceSimItem.setEnabled(false);
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
				myPauseSimItem.setText("Resume");
				myAdvanceSimButton.setEnabled(true);
				myAdvanceSimItem.setEnabled(true);
				myBiosim.pauseSimulation();
			}
			else{
				myPauseSimButton.setToolTipText("Pause the simulation");
				myPauseSimButton.setText("Pause");
				myPauseSimItem.setText("Pause");
				myAdvanceSimButton.setEnabled(false);
				myAdvanceSimItem.setEnabled(false);
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

	private class AboutAction extends AbstractAction{
		public AboutAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			JOptionPane.showMessageDialog(null,"Advanced Life Support System Simulation");
		}
	}
	
	private void displayEnvironment(){
		JInternalFrame environmentFrame = new JInternalFrame("Environment", true, true, true, true);
		environmentFrame.getContentPane().add(new EnvironmentPanel(myBiosim), BorderLayout.CENTER);
		environmentFrame.pack();
		addInternalFrame(environmentFrame);
	}
	
	private void displayAir(){
		JInternalFrame airFrame = new JInternalFrame("Air", true, true, true, true);
		airFrame.getContentPane().add(new AirPanel(myBiosim), BorderLayout.CENTER);
		airFrame.pack();
		addInternalFrame(airFrame);
	}
	
	private class ShowAllDisplaysAction extends AbstractAction{
		public ShowAllDisplaysAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayEnvironment();
			displayAir();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class QuitAction extends AbstractAction{
		public QuitAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			frameClosing();
		}
	}
}

