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

	private Hashtable myPanels;

	//GUI properties
	private int openFrameCount = 0;
	private int xOffset = 30, yOffset = 30;
	private boolean isPaused = false;
	private boolean isStarted = true;

	public SimDesktop(){
		myBiosim = new BioSimulator();
		myPanels = new Hashtable();
		buildGUI();
	}

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

		setTitle("Advanced Life Support Simulation  Copyright "+ new Character( '\u00A9' ) + " 2002, TRACLabs");
		getContentPane().add(myDesktop, BorderLayout.CENTER);
	}

	private void loadIcons(){
		try{
			waterIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/water.jpg"));
			foodIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/food/gui/food.jpg"));
			powerIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/power/gui/power.jpg"));
			crewIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/crew/gui/crew.jpg"));
			environmentIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/environment/gui/environment.jpg"));
			airIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/air/gui/air.jpg"));
			allIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/all.jpg"));
			startIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/power.gif"));
			playIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/play.gif"));
			stopIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/stop.gif"));
			pauseIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/pause.gif"));
			forwardIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/forward.gif"));
		}
		catch (Exception e){
			System.out.println("Couldn't find icons ("+e+"), skipping");
			waterIcon = new ImageIcon();
			foodIcon = new ImageIcon();
			powerIcon = new ImageIcon();
			crewIcon = new ImageIcon();
			environmentIcon = new ImageIcon();
			airIcon = new ImageIcon();
		}
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
			newFrame.setFrameIcon(findIcon(title));
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
			isStarted = !isStarted;
			if (isStarted){
				myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				myStartSimButton.setToolTipText("Starts the simulation");
				myStartSimButton.setIcon(startIcon);
				myStartSimItem.setText("Start");
				myPauseSimButton.setEnabled(false);
				myPauseSimItem.setEnabled(false);
				myAdvanceSimButton.setEnabled(false);
				myAdvanceSimItem.setEnabled(false);
				myBiosim.endSimulation();
				myDesktop.setCursor(Cursor.getDefaultCursor());
			}
			else{
				myDesktop.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				myStartSimButton.setToolTipText("Ends the simulation");
				myStartSimButton.setIcon(stopIcon);
				myStartSimItem.setText("End");
				myPauseSimButton.setEnabled(true);
				myPauseSimItem.setEnabled(true);
				myAdvanceSimButton.setEnabled(false);
				myAdvanceSimItem.setEnabled(false);
				myBiosim.spawnSimulation();
				myDesktop.setCursor(Cursor.getDefaultCursor());
			}
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
				myPauseSimButton.setIcon(playIcon);
				myPauseSimItem.setText("Resume");
				myAdvanceSimButton.setEnabled(true);
				myAdvanceSimItem.setEnabled(true);
				myBiosim.pauseSimulation();
			}
			else{
				myPauseSimButton.setToolTipText("Pause the simulation");
				myPauseSimButton.setIcon(pauseIcon);
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

