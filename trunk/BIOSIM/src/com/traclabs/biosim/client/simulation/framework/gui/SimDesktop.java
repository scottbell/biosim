package biosim.client.framework.gui;

import biosim.client.framework.*;
import biosim.client.gui.*;
import biosim.client.environment.gui.*;
import biosim.client.air.gui.*;
import biosim.client.crew.gui.*;
import biosim.client.food.gui.*;
import biosim.client.power.gui.*;
import biosim.client.water.gui.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

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
	private JButton myDisplayAllButton;
	private JButton myDisplayAirButton;
	private JButton myDisplayCrewButton;
	private JButton myDisplayWaterButton;
	private JButton myDisplayPowerButton;
	private JButton myDisplayFoodButton;
	private JButton myDisplayEnvironmentButton;
	private JToolBar.Separator myToolbarSeparator;
	private JMenuBar myMenuBar;
	private JMenu myFileMenu;
	private JMenu myNewMenu;
	private JMenuItem myQuitItem;
	private JMenu myHelpMenu;
	private JMenuItem myAboutItem;
	private JMenu myControlMenu;
	private JMenuItem myEndSimItem;
	private JMenuItem myStartSimItem;
	private JMenuItem myPauseSimItem;
	private JMenuItem myAdvanceSimItem;
	private JMenuItem myShowAllDisplayItem;
	private JMenuItem myShowAirDisplayItem;
	private JMenuItem myShowFoodDisplayItem;
	private JMenuItem myShowEnvironmentDisplayItem;
	private JMenuItem myShowWaterDisplayItem;
	private JMenuItem myShowPowerDisplayItem;
	private JMenuItem myShowCrewDisplayItem;

	private Action myEndAction;
	private Action myStartAction;
	private Action myPauseAction;
	private Action myAdvanceAction;
	private Action myAboutAction;
	private Action myShowAllDisplayAction;
	private Action myShowAirDisplayAction;
	private Action myShowWaterDisplayAction;
	private Action myShowCrewDisplayAction;
	private Action myShowEnvironmentDisplayAction;
	private Action myShowFoodDisplayAction;
	private Action myShowPowerDisplayAction;
	private Action myQuitAction;

	private Hashtable myPanels;

	//GUI properties
	private int openFrameCount = 0;
	private int xOffset = 30, yOffset = 30;
	private boolean isPaused = false;

	public SimDesktop(){
		myBiosim = new BioSimulator();
		myPanels = new Hashtable();
		buildGUI();
	}

	private void buildGUI(){
		myDesktop = new JDesktopPane();

		myEndAction = new EndSimulationAction("End");
		myStartAction = new StartSimulationAction("Start");
		myPauseAction = new PauseSimulationAction("Pause");
		myAdvanceAction = new AdvanceTickSimulationAction("Advance Tick");
		myAboutAction = new AboutAction("About");
		myQuitAction = new QuitAction("Quit");
		myShowAllDisplayAction = new ShowAllDisplaysAction("Show All Displays");
		myShowAirDisplayAction = new ShowAirDisplayAction("Show Air");
		myShowWaterDisplayAction = new ShowWaterDisplayAction("Show Water");
		myShowCrewDisplayAction = new ShowCrewDisplayAction("Show Crew");
		myShowEnvironmentDisplayAction = new ShowEnvironmentDisplayAction("Show Environment");
		myShowFoodDisplayAction = new ShowFoodDisplayAction("Show Food");
		myShowPowerDisplayAction = new ShowPowerDisplayAction("Show Power");

		myMenuBar = new JMenuBar();
		myFileMenu = new JMenu("File");
		myFileMenu.setMnemonic(KeyEvent.VK_F);
		myNewMenu = new JMenu("New");
		myNewMenu.setMnemonic(KeyEvent.VK_N);
		myShowAllDisplayItem = myNewMenu.add(myShowAllDisplayAction);
		myShowAllDisplayItem.setMnemonic(KeyEvent.VK_D);
		myShowAirDisplayItem = myNewMenu.add(myShowAirDisplayAction);
		myShowAirDisplayItem.setMnemonic(KeyEvent.VK_A);
		myShowCrewDisplayItem = myNewMenu.add(myShowCrewDisplayAction);
		myShowCrewDisplayItem.setMnemonic(KeyEvent.VK_R);
		myShowWaterDisplayItem = myNewMenu.add(myShowWaterDisplayAction);
		myShowWaterDisplayItem.setMnemonic(KeyEvent.VK_W);
		myShowFoodDisplayItem = myNewMenu.add(myShowFoodDisplayAction);
		myShowFoodDisplayItem.setMnemonic(KeyEvent.VK_F);
		myShowPowerDisplayItem = myNewMenu.add(myShowPowerDisplayAction);
		myShowPowerDisplayItem.setMnemonic(KeyEvent.VK_P);
		myShowEnvironmentDisplayItem = myNewMenu.add(myShowEnvironmentDisplayAction);
		myShowEnvironmentDisplayItem.setMnemonic(KeyEvent.VK_E);
		myFileMenu.add(myNewMenu);
		myQuitItem = myFileMenu.add(myQuitAction);
		myQuitItem.setMnemonic(KeyEvent.VK_Q);
		myControlMenu = new JMenu("Control");
		myStartSimItem = myControlMenu.add(myStartAction);
		myStartSimItem.setMnemonic(KeyEvent.VK_S);
		myAdvanceSimItem = myControlMenu.add(myAdvanceAction);
		myAdvanceSimItem.setMnemonic(KeyEvent.VK_O);
		myAdvanceSimItem.setEnabled(false);
		myPauseSimItem = myControlMenu.add(myPauseAction);
		myPauseSimItem.setMnemonic(KeyEvent.VK_U);
		myPauseSimItem.setEnabled(false);
		myEndSimItem = myControlMenu.add(myEndAction);
		myEndSimItem.setMnemonic(KeyEvent.VK_T);
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
		myToolbarSeparator = new JToolBar.Separator();
		myToolBar.add(myToolbarSeparator);
		myDisplayAllButton = myToolBar.add(myShowAllDisplayAction);
		myDisplayAllButton.setToolTipText("Displays All the Views");
		myDisplayWaterButton = myToolBar.add(myShowWaterDisplayAction);
		myDisplayWaterButton.setToolTipText("Displays Water View");
		myDisplayCrewButton = myToolBar.add(myShowCrewDisplayAction);
		myDisplayCrewButton.setToolTipText("Displays Crew View");
		myDisplayAirButton = myToolBar.add(myShowAirDisplayAction);
		myDisplayAirButton.setToolTipText("Displays Air View");
		myDisplayEnvironmentButton = myToolBar.add(myShowEnvironmentDisplayAction);
		myDisplayEnvironmentButton.setToolTipText("Displays Environment View");
		myDisplayPowerButton = myToolBar.add(myShowPowerDisplayAction);
		myDisplayPowerButton.setToolTipText("Displays Power View");
		myDisplayFoodButton = myToolBar.add(myShowFoodDisplayAction);
		myDisplayFoodButton.setToolTipText("Displays Food View");
		getContentPane().add(myToolBar, BorderLayout.NORTH);

		setTitle("Advanced Life Support Simulation  Copyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs");
		getContentPane().add(myDesktop, BorderLayout.CENTER);
	}

	private SimDesktopFrame getSimFrame(JPanel thePanel){
		Container theContainer = thePanel.getParent();
		while (theContainer != null){
			if (theContainer instanceof SimDesktopFrame)
				return ((SimDesktopFrame)(theContainer));
			else
				theContainer = theContainer.getParent();
		}
		return null;
	}

	private void addInternalFrame(String title, JPanel newPanel){
		JPanel existingPanel = (JPanel)(myPanels.get(title));
		if (existingPanel != null){
			SimDesktopFrame existingFrame = getSimFrame(existingPanel);
			if (existingFrame != null){
				existingFrame.moveToFront();
				existingFrame.setVisible(true);
			}
		}
		else {
			SimDesktopFrame newFrame = new SimDesktopFrame(title, this);
			newFrame.getContentPane().add(newPanel, BorderLayout.CENTER);
			newFrame.pack();
			myDesktop.add(newFrame);
			openFrameCount = myDesktop.getAllFrames().length;
			newFrame.setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
			newFrame.moveToFront();
			newFrame.setVisible(true);
			myPanels.put(title, newPanel);
		}
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
			JOptionPane.showMessageDialog(null,"Advanced Life Support Simulation\nCopyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs\nby Scott Bell and David Kortenkamp");
		}
	}

	private void displayAir(){
		addInternalFrame("Air",new AirPanel(myBiosim));
	}

	private void displayEnvironment(){
		addInternalFrame("Environment",new EnvironmentPanel(myBiosim));
	}

	private void displayCrew(){
		addInternalFrame("Crew",new CrewPanel(myBiosim));
	}

	private void displayFood(){
		addInternalFrame("Food",new FoodPanel(myBiosim));
	}

	private void displayPower(){
		addInternalFrame("Power",new PowerPanel(myBiosim));
	}

	private void displayWater(){
		addInternalFrame("Water",new WaterPanel(myBiosim));
	}

	private class ShowAllDisplaysAction extends AbstractAction{
		public ShowAllDisplaysAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayAir();
			displayEnvironment();
			displayCrew();
			displayFood();
			displayPower();
			displayWater();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ShowEnvironmentDisplayAction extends AbstractAction{
		public ShowEnvironmentDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayEnvironment();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ShowAirDisplayAction extends AbstractAction{
		public ShowAirDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayAir();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ShowCrewDisplayAction extends AbstractAction{
		public ShowCrewDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayCrew();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ShowFoodDisplayAction extends AbstractAction{
		public ShowFoodDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayFood();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ShowPowerDisplayAction extends AbstractAction{
		public ShowPowerDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayPower();
			myDesktop.setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ShowWaterDisplayAction extends AbstractAction{
		public ShowWaterDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayWater();
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

