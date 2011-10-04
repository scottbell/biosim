package com.traclabs.biosim.client.simulation.framework;

/**
 * SimDesktop is the main client GUI. It holds references to all the rest of the
 * various modules' GUIs.
 * 
 * @author Scott Bell
 */

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.actuator.ActuatorViewer;
import com.traclabs.biosim.client.framework.BioFrame;
import com.traclabs.biosim.client.framework.MalfunctionPanel;
import com.traclabs.biosim.client.framework.StochasticPanel;
import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.client.sensor.framework.SensorViewer;
import com.traclabs.biosim.client.simulation.air.AirPanel;
import com.traclabs.biosim.client.simulation.air.cdrs.LssmViewer;
import com.traclabs.biosim.client.simulation.crew.CrewPanel;
import com.traclabs.biosim.client.simulation.environment.EnvironmentGraph;
import com.traclabs.biosim.client.simulation.environment.EnvironmentPanel;
import com.traclabs.biosim.client.simulation.food.FoodPanel;
import com.traclabs.biosim.client.simulation.power.PowerPanel;
import com.traclabs.biosim.client.simulation.water.WaterPanel;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.client.util.Fnorder;
import com.traclabs.biosim.idl.framework.BioDriver;

public class SimDesktop extends BioFrame {
    //The driver
    private BioDriver myDriver;

    //GUI Componenets
    private JDesktopPane myDesktop;

    private SimDesktopManager mySimDesktopManager;

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

    private JButton myDisplayMalfunctionButton;

    private JButton myDisplayStochasticButton;

    private JToolBar.Separator myToolbarSeparator;

    private JMenuBar myMenuBar;

    private JMenu myFileMenu;

    private JMenu myNewMenu;

    private JMenuItem myLoggingItem;

    private JMenuItem myQuitItem;

    private JMenu myHelpMenu;

    private JMenu myControlMenu;

    private JMenu myWindowMenu;

    private JMenuItem myStartSimItem;

    private JMenuItem myPauseSimItem;

    private JMenuItem myAdvanceSimItem;

    private JMenuItem myLoopSimItem;

    private JMenuItem myShowAllDisplayItem;

    private JMenuItem myShowAirDisplayItem;

    private JMenuItem myShowFoodDisplayItem;

    private JMenuItem myShowEnvironmentDisplayItem;

    private JMenuItem myShowWaterDisplayItem;

    private JMenuItem myShowPowerDisplayItem;

    private JMenuItem myShowCrewDisplayItem;

    private JMenuItem myShowMalfunctionDisplayItem;

    private JMenuItem myShowStochasticDisplayItem;

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

    private Action myShowMalfunctionDisplayAction;

    private Action myShowStochasticDisplayAction;

    private Action myShowSensorViewerAction;

    private Action myShowActuatorViewerAction;

    private Action myShowEnvironmentGraphAction;

    private Action myLoggingAction;

    private Action myQuitAction;

    private Action myRefreshGuiAction;

    private Action myTileAction;

    private Action myStackAction;

    private Action myLoopAction;
    
    private Action myShowCdrsDisplayAction;

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

    private ImageIcon biosimIcon;

    private ImageIcon malfunctionIcon;

    private ImageIcon stochasticIcon;

    private javax.swing.Timer myRefreshGuiTimer;

    private final static int TIMER_DELAY = 500;

    private Map<String,SimDesktopFrame> myFrames;

    //Count of how many frames are opened. Used to stagger windows
    private int openFrameCount = 0;

    //Integers dictating how much the windows should be staggered.
    private int xOffset = 30, yOffset = 30;

    private Logger myLogger;

    /**
     * Creates a BioSimulator, a panel hashtable, and creates the GUI
     */
    public SimDesktop() {
        myLogger = Logger.getLogger(this.getClass());
        myDriver = BioHolderInitializer.getBioHolder().theBioDriver;
        myDriver.setPauseSimulation(false);
        myFrames = new Hashtable<String,SimDesktopFrame>();
        buildGUI();
    }

    /**
     * Creates the toolbar, creates the menubar, sets the title and displays the
     * desktop.
     */
    private void buildGUI() {
        myDesktop = new JDesktopPane();
        mySimDesktopManager = new SimDesktopManager(this);
        myDesktop.setDesktopManager(mySimDesktopManager);
        loadIcons();
        myStartAction = new StartSimulationAction("Start");
        myPauseAction = new PauseSimulationAction("Pause");
        myAdvanceAction = new AdvanceTickSimulationAction("Advance");
        myLoopAction = new LoopSimulationAction("Loop");
        myAboutAction = new AboutAction("About");
        myQuitAction = new QuitAction("Quit");
        myShowAllDisplayAction = new ShowAllDisplaysAction("Show All Displays");
        myShowAirDisplayAction = new ShowAirDisplayAction("Show Air");
        myShowWaterDisplayAction = new ShowWaterDisplayAction("Show Water");
        myShowCrewDisplayAction = new ShowCrewDisplayAction("Show Crew");
        myShowEnvironmentDisplayAction = new ShowEnvironmentDisplayAction(
                "Show Environment");
        myShowFoodDisplayAction = new ShowFoodDisplayAction("Show Food");
        myShowPowerDisplayAction = new ShowPowerDisplayAction("Show Power");
        myShowMalfunctionDisplayAction = new ShowMalfunctionDisplayAction(
                "Show Malfunction Controller");
        myShowStochasticDisplayAction = new ShowStochasticDisplayAction(
                "Show Stochastic Controller");
        myShowCdrsDisplayAction = new ShowCdrsDisplayAction("Show CDRS Display");
        myShowSensorViewerAction = new ShowSensorViewerAction(
                "Show Sensor Viewer");
        myShowActuatorViewerAction = new ShowActuatorViewerAction("Show Actuator Viewer");
        myShowEnvironmentGraphAction = new ShowEnvironmentGraphAction("Show Environment Graph");
        myLoggingAction = new LoggingAction("Enable Logging");
        myTileAction = new TileAction("Tile");
        myStackAction = new StackAction("Stack");

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
        myShowEnvironmentDisplayItem = myNewMenu
                .add(myShowEnvironmentDisplayAction);
        myShowEnvironmentDisplayItem.setMnemonic(KeyEvent.VK_E);
        myShowMalfunctionDisplayItem = myNewMenu
                .add(myShowMalfunctionDisplayAction);
        myShowMalfunctionDisplayItem.setMnemonic(KeyEvent.VK_M);
        myShowStochasticDisplayItem = myNewMenu
                .add(myShowStochasticDisplayAction);
        myShowStochasticDisplayItem.setMnemonic(KeyEvent.VK_I);
        myNewMenu.add(myShowCdrsDisplayAction);
        myNewMenu.add(myShowSensorViewerAction);
        myNewMenu.add(myShowActuatorViewerAction);
        myNewMenu.add(myShowEnvironmentGraphAction);
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
        myPauseSimItem = myControlMenu.add(myPauseAction);
        myPauseSimItem.setMnemonic(KeyEvent.VK_U);
        myLoopSimItem = myControlMenu.add(myLoopAction);
        myWindowMenu = new JMenu("Window");
        myWindowMenu.add(myStackAction);
        myWindowMenu.add(myTileAction);
        myHelpMenu = new JMenu("Help");
        myHelpMenu.add(myAboutAction);
        myMenuBar.add(myFileMenu);
        myMenuBar.add(myControlMenu);
        myMenuBar.add(myWindowMenu);
        myMenuBar.add(myHelpMenu);
        setJMenuBar(myMenuBar);

        myToolBar = new JToolBar();
        myToolBar.setFloatable(false);
        myStartSimButton = myToolBar.add(myStartAction);
        myStartSimButton.setToolTipText("Starts the simulation");
        myStartSimButton.setText("");
        myStartSimButton.setIcon(startIcon);
        myStartSimButton.setEnabled(false);
        myPauseSimButton = myToolBar.add(myPauseAction);
        myPauseSimButton.setToolTipText("Pauses the simulation");
        myPauseSimButton.setIcon(pauseIcon);
        myPauseSimButton.setText("");
        myPauseSimButton.setEnabled(true);
        myAdvanceSimButton = myToolBar.add(myAdvanceAction);
        myAdvanceSimButton
                .setToolTipText("Advances the simulation one timestep");
        myAdvanceSimButton.setText("");
        myAdvanceSimButton.setIcon(forwardIcon);
        myAdvanceSimButton.setEnabled(false);
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
        myDisplayEnvironmentButton = myToolBar
                .add(myShowEnvironmentDisplayAction);
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
        myDisplayMalfunctionButton = myToolBar
                .add(myShowMalfunctionDisplayAction);
        myDisplayMalfunctionButton
                .setToolTipText("Displays Malfunction Controller");
        myDisplayMalfunctionButton.setIcon(malfunctionIcon);
        myDisplayMalfunctionButton.setText("");
        myDisplayStochasticButton = myToolBar
                .add(myShowStochasticDisplayAction);
        myDisplayStochasticButton
                .setToolTipText("Displays Stochastic Controller");
        myDisplayStochasticButton.setIcon(stochasticIcon);
        myDisplayStochasticButton.setText("");
        getContentPane().add(myToolBar, BorderLayout.NORTH);
        setTitle("BioSim: Advanced Life Support Simulation");
        myDesktop.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
        setIconImage(biosimIcon.getImage());
        getContentPane().add(myDesktop, BorderLayout.CENTER);
        myRefreshGuiAction = new RefreshGuiAction();
        myRefreshGuiTimer = new javax.swing.Timer(TIMER_DELAY,
                myRefreshGuiAction);
        myRefreshGuiTimer.start();
    }

    /**
     * Attempts to load the icons from the resource directory.
     */
    private void loadIcons() {
        try {
            waterIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                    .getResource(
                            "com/traclabs/biosim/client/water/water.png"));
            foodIcon = new ImageIcon(
                    SimDesktop.class.getClassLoader().getResource(
                            "com/traclabs/biosim/client/food/food.png"));
            powerIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                    .getResource(
                            "com/traclabs/biosim/client/power/power.png"));
            crewIcon = new ImageIcon(
                    SimDesktop.class.getClassLoader().getResource(
                            "com/traclabs/biosim/client/crew/crew.png"));
            environmentIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/environment/environment.png"));
            airIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                    .getResource("com/traclabs/biosim/client/air/air.png"));
            allIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                    .getResource(
                            "com/traclabs/biosim/client/framework/all.png"));
            startIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/start.png"));
            playIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/play.png"));
            stopIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/stop.png"));
            pauseIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/pause.png"));
            forwardIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/forward.png"));
            biosimIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/biosim.png"));
            malfunctionIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/gear.png"));
            stochasticIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                            .getResource(
                                    "com/traclabs/biosim/client/framework/dice.png"));
        } catch (Exception e) {
            myLogger.error("Couldn't find icon (" + e + "), skipping");
            e.printStackTrace();
        }
    }

    /**
     * Returns a n icon based on the string desciptor (air, water crew, etc)
     * 
     * @return the icon specified by the title
     * @param title
     *            the descriptor of the icon wanted
     */
    private ImageIcon findIcon(String title) {
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
        else if (title.equals("Malfunction Controller"))
            return malfunctionIcon;
        else if (title.equals("Stochastic Controller"))
            return stochasticIcon;
        else
            return new ImageIcon();
    }

    private boolean tryExisitingInternalFrame(String title) {
        SimDesktopFrame existingFrame = (myFrames.get(title));
        if (existingFrame != null) {
            existingFrame.pack();
            existingFrame.moveToFront();
            existingFrame.setVisible(true);
            return true;
        }
        return false;
    }

    /**
     * Creates a new SimDesktopFrame (an internal frame), adds it to the
     * desktop, and adds it to it's hashtable.
     * 
     * @param title
     *            the name of the new internal frame
     * @param newPanel
     *            the panel to be added to the new internal frame
     */
    private SimDesktopFrame addInternalFrame(String title, JPanel newPanel) {
        SimDesktopFrame newFrame = new SimDesktopFrame(title, this);
        if (newPanel instanceof UpdatablePanel)
            newFrame.add((UpdatablePanel) (newPanel));
        else
            newFrame.getContentPane().add(newPanel, BorderLayout.CENTER);
        newFrame.pack();
        myDesktop.add(newFrame);
        openFrameCount = myDesktop.getAllFrames().length;
        newFrame.setFrameIcon(findIcon(title));
        newFrame
                .setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
        newFrame.moveToFront();
        newFrame.setVisible(true);
        myFrames.put(title, newFrame);
        return newFrame;
    }

    /**
     * Implemented to satisfy BaseJFrame's interface. Called when SimDesktop is
     * exiting.
     */
    protected void frameExiting() {
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
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                myStartSimButton.setToolTipText("Starts the simulation");
                myStartSimButton.setIcon(startIcon);
                myStartSimItem.setText("Start");
                myAdvanceSimButton.setEnabled(false);
                myAdvanceSimItem.setEnabled(false);
                myDriver.endSimulation();
                setCursor(Cursor.getDefaultCursor());
            }
            //We want to start the simulation
            else {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                myStartSimButton.setToolTipText("Ends the simulation");
                myStartSimButton.setIcon(stopIcon);
                myStartSimItem.setText("End");
                myAdvanceSimButton.setEnabled(true);
                myAdvanceSimItem.setEnabled(true);
                //If we're paused, stay paused.
                if (myDriver.isPaused()) {
                    myPauseSimButton.setToolTipText("Resume the simulation");
                    myPauseSimButton.setIcon(playIcon);
                    myPauseSimItem.setText("Resume");
                    myAdvanceSimButton.setEnabled(true);
                    myAdvanceSimItem.setEnabled(true);
                }
                //If we're not paused, start ticking immediatly
                else {
                    myPauseSimButton.setToolTipText("Pause the simulation");
                    myPauseSimButton.setIcon(pauseIcon);
                    myPauseSimItem.setText("Pause");
                    myAdvanceSimButton.setEnabled(false);
                    myAdvanceSimItem.setEnabled(false);
                }
                //Tell the biosimulator to enter loop
                myDriver.startSimulation();
                //myDriver.spawnSimulation();
                setCursor(Cursor.getDefaultCursor());
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
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //We want to resume the simulation
            if (myDriver.isPaused()) {
                myPauseSimButton.setToolTipText("Pause the simulation");
                myPauseSimButton.setIcon(pauseIcon);
                myPauseSimItem.setText("Pause");
                myAdvanceSimButton.setEnabled(false);
                myAdvanceSimItem.setEnabled(false);
                myDriver.setPauseSimulation(false);
            }
            //We want to pause the simulation
            else {
                myPauseSimButton.setToolTipText("Resume the simulation");
                myPauseSimButton.setIcon(playIcon);
                myPauseSimItem.setText("Resume");
                if (myDriver.isStarted()) {
                    myAdvanceSimButton.setEnabled(true);
                    myAdvanceSimItem.setEnabled(true);
                }
                myDriver.setPauseSimulation(true);
            }
            setCursor(Cursor.getDefaultCursor());
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
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myDriver.advanceOneTick();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Enables looping of simulation
     */
    private class LoopSimulationAction extends AbstractAction {
        public LoopSimulationAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myDriver.setLooping(!myDriver.isLooping());
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that brings up a dialog box about authors, company, etc.
     */
    private class AboutAction extends AbstractAction {
        public AboutAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            JOptionPane
                    .showMessageDialog(
                            null,
                            "BioSim: Advanced Life Support Simulation\nCopyright "
                                    + new Character('\u00A9')
                                    + " 2007, TRACLabs\nby Scott Bell and David Kortenkamp");
        }
    }

    /**
     * Displays the Air panel with an internal frame inside this desktop.
     */
    private void displayAir() {
        if (!tryExisitingInternalFrame("Air"))
            addInternalFrame("Air", new AirPanel());
    }

    /**
     * Displays the Environment panel with an internal frame inside this
     * desktop.
     */
    private void displayEnvironment() {
        if (!tryExisitingInternalFrame("Environment"))
            addInternalFrame("Environment", new EnvironmentPanel());
    }

    /**
     * Displays the Crew panel with an internal frame inside this desktop.
     */
    private void displayCrew() {
        if (!tryExisitingInternalFrame("Crew"))
            addInternalFrame("Crew", new CrewPanel());
    }

    /**
     * Displays the Food panel with an internal frame inside this desktop.
     */
    private void displayFood() {
        if (!tryExisitingInternalFrame("Food"))
            addInternalFrame("Food", new FoodPanel());
    }

    /**
     * Displays the Power panel with an internal frame inside this desktop.
     */
    private void displayPower() {
        if (!tryExisitingInternalFrame("Power"))
            addInternalFrame("Power", new PowerPanel());
    }

    /**
     * Displays the Water panel with an internal frame inside this desktop.
     */
    private void displayWater() {
        if (!tryExisitingInternalFrame("Water"))
            addInternalFrame("Water", new WaterPanel());
    }

    /**
     * Displays the Malfunction panel with an internal frame inside this
     * desktop.
     */
    private void displayMalfunction() {
        if (!tryExisitingInternalFrame("Malfunction Controller")) {
            SimDesktopFrame newFrame = addInternalFrame(
                    "Malfunction Controller", new MalfunctionPanel());
            newFrame.setSize(575, 350);
        }
    }

    /**
     * Displays the Stochastic panel with an internal frame inside this desktop.
     */
    private void displayStochastic() {
        if (!tryExisitingInternalFrame("Stochastic Controller")) {
            SimDesktopFrame newFrame = addInternalFrame(
                    "Stochastic Controller", new StochasticPanel());
            newFrame.setSize(500, 350);
        }
    }
    
    private void displayCdrs() {
        if (!tryExisitingInternalFrame("CDRS Display")) {
            SimDesktopFrame newFrame = addInternalFrame("CDRS Display", new LssmViewer());
            newFrame.setSize(500, 350);
        }
    }

    /**
     * Displays the Sensor Viewer panel with an internal frame inside this
     * desktop.
     */
    private void displaySensorViewer() {
        if (!tryExisitingInternalFrame("Sensor Viewer")) {
            SimDesktopFrame newFrame = addInternalFrame("Sensor Viewer", new SensorViewer());
            newFrame.pack();
        }
    }

    /**
     * Displays the Actuator Viewer panel with an internal frame inside this
     * desktop.
     */
    private void displayActuatorViewer() {
        if (!tryExisitingInternalFrame("Actuator Viewer")) {
            SimDesktopFrame newFrame = addInternalFrame("Actuator Viewer", new ActuatorViewer());
            newFrame.pack();
        }
    }

    /**
     * Displays the Enviornment volume graph with an internal frame inside this
     * desktop.
     */
    private void displayEnvironmentGraph() {
        if (!tryExisitingInternalFrame("Environment Graph")) {
            SimDesktopFrame newFrame = addInternalFrame("Environment Graph", new EnvironmentGraph());
            newFrame.pack();
        }
    }

    protected Map getInternalFrames() {
        return myFrames;
    }

    protected JDesktopPane getDesktopPane() {
        return myDesktop;
    }

    /**
     * Action that displays all the panels with internal frames inside this
     * desktop.
     */
    private class ShowAllDisplaysAction extends AbstractAction {
        public ShowAllDisplaysAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (ae.getModifiers() == (ActionEvent.CTRL_MASK + 16)) {
                com.traclabs.biosim.client.util.Fnorder myFnord = new Fnorder();
                String message = myFnord.getFnord();
                ImageIcon fnordIcon = new ImageIcon(SimDesktop.class.getClassLoader().getResource("com/traclabs/biosim/client/framework/pyramid.png"));
                JOptionPane fnordPane = new JOptionPane(message,
                        JOptionPane.INFORMATION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION, fnordIcon);
                JDialog dialog = fnordPane.createDialog(null,
                        "Your message from the Illuminati");
                dialog.setVisible(true);
                setCursor(Cursor.getDefaultCursor());
                return;
            }
            displayAir();
            displayEnvironment();
            displayCrew();
            displayFood();
            displayPower();
            displayWater();
            displayMalfunction();
            displayStochastic();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the environment panel in an internal frame on the
     * desktop.
     */
    private class ShowEnvironmentDisplayAction extends AbstractAction {
        public ShowEnvironmentDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayEnvironment();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the air panel in an internal frame on the desktop.
     */
    private class ShowAirDisplayAction extends AbstractAction {
        public ShowAirDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayAir();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the crew panel in an internal frame on the desktop.
     */
    private class ShowCrewDisplayAction extends AbstractAction {
        public ShowCrewDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayCrew();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the food panel in an internal frame on the desktop.
     */
    private class ShowFoodDisplayAction extends AbstractAction {
        public ShowFoodDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayFood();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the power panel in an internal frame on the desktop.
     */
    private class ShowPowerDisplayAction extends AbstractAction {
        public ShowPowerDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayPower();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the water panel in an internal frame on the desktop.
     */
    private class ShowWaterDisplayAction extends AbstractAction {
        public ShowWaterDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayWater();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the malfunction panel in an internal frame on the
     * desktop.
     */
    private class ShowMalfunctionDisplayAction extends AbstractAction {
        public ShowMalfunctionDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayMalfunction();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that displays the malfunction panel in an internal frame on the
     * desktop.
     */
    private class ShowStochasticDisplayAction extends AbstractAction {
        public ShowStochasticDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayStochastic();
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private class ShowCdrsDisplayAction extends AbstractAction {
        public ShowCdrsDisplayAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayCdrs();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private class ShowSensorViewerAction extends AbstractAction {
        public ShowSensorViewerAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displaySensorViewer();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private class ShowActuatorViewerAction extends AbstractAction {
        public ShowActuatorViewerAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayActuatorViewer();
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private class ShowEnvironmentGraphAction extends AbstractAction {
        public ShowEnvironmentGraphAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            displayEnvironmentGraph();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that stops the simulation and exits (by way of the frameClosing
     * method)
     */
    private class QuitAction extends AbstractAction {
        public QuitAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            frameClosing();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that auto-arranges internal menus
     */
    private class TileAction extends AbstractAction {
        public TileAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            mySimDesktopManager.tileWindows();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * Action that auto-arranges internal menus
     */
    private class StackAction extends AbstractAction {
        public StackAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            mySimDesktopManager.stackWindows();
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private class RefreshGuiAction extends AbstractAction {
        public void actionPerformed(ActionEvent ae) {
            //Initialize buttons by looking at server
            myLoggingItem.setText("Enable Logging");
            //Simulation has stopped
            if (!myDriver.isStarted()) {
                myStartSimButton.setToolTipText("Starts the simulation");
                myStartSimButton.setIcon(startIcon);
                myStartSimButton.setEnabled(true);
                myStartSimItem.setText("Start");
                myAdvanceSimButton.setEnabled(false);
                myAdvanceSimItem.setEnabled(false);
            }
            //Simulation has started
            else {
                myStartSimButton.setToolTipText("Ends the simulation");
                myStartSimButton.setIcon(stopIcon);
                myStartSimItem.setText("End");
                myStartSimButton.setEnabled(true);
                myAdvanceSimButton.setEnabled(true);
                myAdvanceSimItem.setEnabled(true);
            }
            //Simulation has paused
            if (myDriver.isPaused()) {
                myPauseSimButton.setToolTipText("Resume the simulation");
                myPauseSimButton.setIcon(playIcon);
                myPauseSimItem.setText("Resume");
                if (myDriver.isStarted()) {
                    myAdvanceSimButton.setEnabled(true);
                    myAdvanceSimItem.setEnabled(true);
                }
            }
            //Simulation has resumed
            else {
                myPauseSimButton.setToolTipText("Pause the simulation");
                myPauseSimButton.setIcon(pauseIcon);
                myPauseSimItem.setText("Pause");
                myAdvanceSimButton.setEnabled(false);
                myAdvanceSimItem.setEnabled(false);
            }
            if (myDriver.isLooping())
                myLoopSimItem.setText("Stop Looping");
            else
                myLoopSimItem.setText("Start Looping");
        }
    }

    /**
     * Action that enables/disables logging
     */
    private class LoggingAction extends AbstractAction {
        public LoggingAction(String name) {
            super(name);
        }

        public void actionPerformed(ActionEvent ae) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myLoggingItem.setText("Enable Logging");
            setCursor(Cursor.getDefaultCursor());
        }
    }
}

