/**
 * SimDesktop is the main client GUI.  It holds references to all the rest of the various modules' GUIs.
 *
 * @author    Scott Bell
 */

package biosim.client.framework.gui;

import biosim.client.framework.*;
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

public class SimDesktop extends BioFrame
{
	//The simulator
	private BioSimulator myBiosim;

	//GUI Componenets
	private JDesktopPane myDesktop;
	private JToolBar myToolBar;
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
	private JMenuItem myLoggingItem;
	private JMenuItem myQuitItem;
	private JMenu myHelpMenu;
	private JMenuItem myAboutItem;
	private JMenu myControlMenu;
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
	
	//Various actions attributed to Buttons/MenuItems
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
	private Action myLoggingAction;
	private Action myQuitAction;
	
	//Various icons used to display buttons
	private ImageIcon waterIcon;
	private ImageIcon foodIcon;
	private ImageIcon powerIcon;
	private ImageIcon crewIcon;
	private ImageIcon environmentIcon;
	private ImageIcon airIcon;
	private ImageIcon allIcon;
	private ImageIcon startIcon;
	private ImageIcon playIcon;
	private ImageIcon stopIcon;
	private ImageIcon pauseIcon;
	private ImageIcon forwardIcon;
	
	//A hashtable containing each client panel (water, food, air, etc)
	private Hashtable myPanels;
	//Count of how many frames are opened.  Used to stagger windows
	private int openFrameCount = 0;
	//Integers dictating how much the windows should be staggered.
	private int xOffset = 30, yOffset = 30;
	
	/**
	* Creates a BioSimulator, a panel hashtable, and creates the GUI
	*/
	public SimDesktop(){
		myBiosim = new BioSimulator();
		myPanels = new Hashtable();
		buildGUI();
	}
	
	/**
	* Creates the toolbar, creates the menubar, sets the title and displays the desktop.
	*/
	private void buildGUI(){
		myDesktop = new JDesktopPane();
		loadIcons();
		myStartAction = new StartSimulationAction("Start");
		myPauseAction = new PauseSimulationAction("Pause");
		myAdvanceAction = new AdvanceTickSimulationAction("Advance");
		myAboutAction = new AboutAction("About");
		myQuitAction = new QuitAction("Quit");
		myShowAllDisplayAction = new ShowAllDisplaysAction("Show All Displays");
		myShowAirDisplayAction = new ShowAirDisplayAction("Show Air");
		myShowWaterDisplayAction = new ShowWaterDisplayAction("Show Water");
		myShowCrewDisplayAction = new ShowCrewDisplayAction("Show Crew");
		myShowEnvironmentDisplayAction = new ShowEnvironmentDisplayAction("Show Environment");
		myShowFoodDisplayAction = new ShowFoodDisplayAction("Show Food");
		myShowPowerDisplayAction = new ShowPowerDisplayAction("Show Power");
		myLoggingAction = new LoggingAction("Enable Logging");
		
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
		myLoggingItem = myFileMenu.add(myLoggingAction);
		myLoggingItem.setMnemonic(KeyEvent.VK_L);
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
		myStartSimButton.setText("");
		myStartSimButton.setIcon(startIcon);
		myPauseSimButton = myToolBar.add(myPauseAction);
		myPauseSimButton.setToolTipText("Pauses the simulation");
		myPauseSimButton.setEnabled(false);
		myPauseSimButton.setText("");
		myPauseSimButton.setIcon(pauseIcon);
		myAdvanceSimButton = myToolBar.add(myAdvanceAction);
		myAdvanceSimButton.setToolTipText("Advances the simulation one timestep");
		myAdvanceSimButton.setEnabled(false);
		myAdvanceSimButton.setText("");
		myAdvanceSimButton.setIcon(forwardIcon);
		myToolbarSeparator = new JToolBar.Separator();
		myToolBar.add(myToolbarSeparator);
		myDisplayAllButton = myToolBar.add(myShowAllDisplayAction);
		myDisplayAllButton.setToolTipText("Displays All the Views");
		myDisplayAllButton.setIcon(allIcon);
		myDisplayAllButton.setText("");
		myDisplayWaterButton = myToolBar.add(myShowWaterDisplayAction);
		myDisplayWaterButton.setToolTipText("Displays Water View");
		myDisplayWaterButton.setIcon(waterIcon);
		myDisplayWaterButton.setText("");
		myDisplayCrewButton = myToolBar.add(myShowCrewDisplayAction);
		myDisplayCrewButton.setToolTipText("Displays Crew View");
		myDisplayCrewButton.setIcon(crewIcon);
		myDisplayCrewButton.setText("");
		myDisplayAirButton = myToolBar.add(myShowAirDisplayAction);
		myDisplayAirButton.setToolTipText("Displays Air View");
		myDisplayAirButton.setIcon(airIcon);
		myDisplayAirButton.setText("");
		myDisplayEnvironmentButton = myToolBar.add(myShowEnvironmentDisplayAction);
		myDisplayEnvironmentButton.setToolTipText("Displays Environment View");
		myDisplayEnvironmentButton.setIcon(environmentIcon);
		myDisplayEnvironmentButton.setText("");
		myDisplayPowerButton = myToolBar.add(myShowPowerDisplayAction);
		myDisplayPowerButton.setToolTipText("Displays Power View");
		myDisplayPowerButton.setIcon(powerIcon);
		myDisplayPowerButton.setText("");
		myDisplayFoodButton = myToolBar.add(myShowFoodDisplayAction);
		myDisplayFoodButton.setToolTipText("Displays Food View");
		myDisplayFoodButton.setIcon(foodIcon);
		myDisplayFoodButton.setText("");
		getContentPane().add(myToolBar, BorderLayout.NORTH);

		setTitle("BIOSIM Advanced Life Support Simulation  Copyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs");
		myDesktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
		getContentPane().add(myDesktop, BorderLayout.CENTER);
	}
	
	/**
	* Attempts to load the icons from the resource directory.
	*/
	private void loadIcons(){
		try{
			waterIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/water.jpg"));
			foodIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/food/gui/food.jpg"));
			powerIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/power/gui/power.jpg"));
			crewIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/crew/gui/crew.jpg"));
			environmentIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/environment/gui/environment.jpg"));
			airIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/air/gui/air.jpg"));
			allIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/all.jpg"));
			startIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/stop.gif"));
			playIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/play.gif"));
			stopIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/power.gif"));
			pauseIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/pause.gif"));
			forwardIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/forward.gif"));
		}
		catch (Exception e){
			System.out.println("Couldn't find icon ("+e+"), skipping");
			e.printStackTrace();
			waterIcon = new ImageIcon();
			foodIcon = new ImageIcon();
			powerIcon = new ImageIcon();
			crewIcon = new ImageIcon();
			environmentIcon = new ImageIcon();
			airIcon = new ImageIcon();
		}
	}
	
	/**
	* Returns a selected SimFrame (an internal frame) that contains the panel passed into it
	* @return the parent frame requested, null if not found
	* @param thePanel the panel who's parent frame is needed
	*/
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
	
	/**
	* Returns a n icon based on the string desciptor (air, water crew, etc)
	* @return the icon specified by the title
	* @param title the descriptor of the icon wanted
	*/
	private ImageIcon findIcon(String title){
		if (title.equals("Air"))
			return airIcon;
		else if (title.equals("Food"))
			return foodIcon;
		else if (title.equals("Power"))
			return powerIcon;
		else if (title.equals("Crew"))
			return crewIcon;
		else if (title.equals("Environment"))
			return environmentIcon;
		else if (title.equals("Water"))
			return waterIcon;
		else
			return new ImageIcon();
	}
	
	/**
	* Creates a new SimDesktopFrame (an internal frame), adds it to the desktop, and adds it to it's hashtable.
	* If the panel already has a SimDesktopFrame, it repacks the frame and brings it to the front.
	* @param title the name of the new internal frame
	* @param newPanel the panel to be added to the new internal frame
	*/
	private void addInternalFrame(String title, JPanel newPanel){
		JPanel existingPanel = (JPanel)(myPanels.get(title));
		if (existingPanel != null){
			SimDesktopFrame existingFrame = getSimFrame(existingPanel);
			if (existingFrame != null){
				existingFrame.pack();
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
			newFrame.setFrameIcon(findIcon(title));
			newFrame.setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
			newFrame.moveToFront();
			newFrame.setVisible(true);
			myPanels.put(title, newPanel);
		}
	}
	
	/**
	* Implemented to satisfy BaseJFrame's interface.  Called when SimDesktop is exiting.
	* Stops the simulation if necessary.
	*/
	protected void frameExiting(){
		if (myBiosim.simulationHasStarted())
			myBiosim.endSimulation();
	}
	
	/**
	* Action describing a simulation start/stop (depending on the button state).  Enables/disables various buttons on the SimDesktop
	* and invokes BioSimulator's stop or start method.
	*/
	private class StartSimulationAction extends AbstractAction{
		public StartSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			//We want to stop the simulation
			if (myBiosim.isStarted()){
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				myStartSimButton.setToolTipText("Starts the simulation");
				myStartSimButton.setIcon(startIcon);
				myStartSimItem.setText("Start");
				myPauseSimButton.setEnabled(false);
				myPauseSimItem.setEnabled(false);
				myAdvanceSimButton.setEnabled(false);
				myAdvanceSimItem.setEnabled(false);
				myBiosim.endSimulation();
				setCursor(Cursor.getDefaultCursor());
			}
			//We want to start the simulation
			else{
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				myStartSimButton.setToolTipText("Ends the simulation");
				myStartSimButton.setIcon(stopIcon);
				myStartSimItem.setText("End");
				myPauseSimButton.setEnabled(true);
				myPauseSimItem.setEnabled(true);
				myAdvanceSimButton.setEnabled(true);
				myAdvanceSimItem.setEnabled(true);
				//If we're paused, stay paused.
				if (myBiosim.isPaused()){
					myPauseSimButton.setToolTipText("Resume the simulation");
					myPauseSimButton.setIcon(playIcon);
					myPauseSimItem.setText("Resume");
					myAdvanceSimButton.setEnabled(true);
					myAdvanceSimItem.setEnabled(true);
				}
				//If we're not paused, start ticking immediatly
				else{
					myPauseSimButton.setToolTipText("Pause the simulation");
					myPauseSimButton.setIcon(pauseIcon);
					myPauseSimItem.setText("Pause");
					myAdvanceSimButton.setEnabled(false);
					myAdvanceSimItem.setEnabled(false);
				}
				//Tell the biosimulator to enter loop
				myBiosim.spawnSimulation(true);
				setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	/**
	* Action describing a simulation pause/resume (depending on the button state).  Enables/disables various buttons on the SimDesktop
	* and invokes BioSimulator's pause or resume method.
	*/
	private class PauseSimulationAction extends AbstractAction{
		public PauseSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			//We want to resume the simulation
			if (myBiosim.isPaused()){
				myPauseSimButton.setToolTipText("Pause the simulation");
				myPauseSimButton.setIcon(pauseIcon);
				myPauseSimItem.setText("Pause");
				myAdvanceSimButton.setEnabled(false);
				myAdvanceSimItem.setEnabled(false);
				myBiosim.resumeSimulation();
			}
			//We want to pause the simulation
			else{myPauseSimButton.setToolTipText("Resume the simulation");
				myPauseSimButton.setIcon(playIcon);
				myPauseSimItem.setText("Resume");
				myAdvanceSimButton.setEnabled(true);
				myAdvanceSimItem.setEnabled(true);
				myBiosim.pauseSimulation();
			}
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action describing a simulation advance of one tick.  Enables/disables various buttons on the SimDesktop
	* and invokes BioSimulator's tick method.  Used only when BioSimulator is paused.
	*/
	private class AdvanceTickSimulationAction extends AbstractAction{
		public AdvanceTickSimulationAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myBiosim.advanceOneTick();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	*  Action that brings up a dialog box about authors, company, etc.
	*/
	private class AboutAction extends AbstractAction{
		public AboutAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			JOptionPane.showMessageDialog(null,"Advanced Life Support Simulation\nCopyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs\nby Scott Bell and David Kortenkamp");
		}
	}
	
	/**
	*  Displays the Air panel with an internal frame inside this desktop.
	*/
	private void displayAir(){
		addInternalFrame("Air",new AirPanel(myBiosim));
	}
	
	/**
	*  Displays the Environment panel with an internal frame inside this desktop.
	*/
	private void displayEnvironment(){
		addInternalFrame("Environment",new EnvironmentPanel(myBiosim));
	}
	
	/**
	*  Displays the Crew panel with an internal frame inside this desktop.
	*/
	private void displayCrew(){
		addInternalFrame("Crew",new CrewPanel(myBiosim));
	}
	
	/**
	*  Displays the Food panel with an internal frame inside this desktop.
	*/
	private void displayFood(){
		addInternalFrame("Food",new FoodPanel(myBiosim));
	}
	
	/**
	*  Displays the Power panel with an internal frame inside this desktop.
	*/
	private void displayPower(){
		addInternalFrame("Power",new PowerPanel(myBiosim));
	}
	
	/**
	*  Displays the Water panel with an internal frame inside this desktop.
	*/
	private void displayWater(){
		addInternalFrame("Water",new WaterPanel(myBiosim));
	}
	
	/**
	*  Action that displays all the panels with internal frames inside this desktop.
	*/
	private class ShowAllDisplaysAction extends AbstractAction{
		public ShowAllDisplaysAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayAir();
			displayEnvironment();
			displayCrew();
			displayFood();
			displayPower();
			displayWater();
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	* Action that displays the environment panel in an internal frame on the desktop.
	*/
	private class ShowEnvironmentDisplayAction extends AbstractAction{
		public ShowEnvironmentDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayEnvironment();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that displays the air panel in an internal frame on the desktop.
	*/
	private class ShowAirDisplayAction extends AbstractAction{
		public ShowAirDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayAir();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that displays the crew panel in an internal frame on the desktop.
	*/
	private class ShowCrewDisplayAction extends AbstractAction{
		public ShowCrewDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayCrew();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that displays the food panel in an internal frame on the desktop.
	*/
	private class ShowFoodDisplayAction extends AbstractAction{
		public ShowFoodDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayFood();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that displays the power panel in an internal frame on the desktop.
	*/
	private class ShowPowerDisplayAction extends AbstractAction{
		public ShowPowerDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayPower();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that displays the water panel in an internal frame on the desktop.
	*/
	private class ShowWaterDisplayAction extends AbstractAction{
		public ShowWaterDisplayAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			displayWater();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that stops the simulation and exits (by way of the frameClosing method)
	*/
	private class QuitAction extends AbstractAction{
		public QuitAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			frameClosing();
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	/**
	* Action that enables/disables logging
	*/
	private class LoggingAction extends AbstractAction{
		public LoggingAction(String name){
			super(name);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myBiosim.setLogging(!myBiosim.isLogging());
			if (myBiosim.isLogging()){
				myLoggingItem.setText("Disable Logging");
			}
			else{
				myLoggingItem.setText("Enable Logging");
			}
			setCursor(Cursor.getDefaultCursor());
		}
	}
}

