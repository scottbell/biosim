package com.traclabs.biosim.server.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.ui.IStatusBar;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.client.framework.gui.BioFrame;

/**
 * A window that displays a toolbar, a connected graph editing pane, and a
 * status bar.  Creates an xml file to initialize BioSim.
 */

public class BiosimEditor extends BioFrame implements IStatusBar {
    /** The graph pane (shown in middle of window). */
    private JGraph myGraph;

    /** A statusbar (shown at bottom ow window). */
    private JLabel myStatusbar = new JLabel(" ");

    private JMenuBar myMenubar = new JMenuBar();
    
    private JPanel myGraphPanel;

    private JTabbedPane myTabbedPane;

    private Logger myLogger;

    private JMenuBar myMenuBar;

    private JMenu myFileMenu;

    private JMenu myNewMenu;

    private JMenuItem myLoggingItem;

    private JMenuItem myQuitItem;

    private JMenu myHelpMenu;

    private JMenuItem myAboutItem;

    private AboutAction myAboutAction;

    private QuitAction myQuitAction;

    private JComponent myAirToolBar;

    private JComponent myCrewToolBar;

    private JComponent myEnvironmentToolBar;

    private JComponent myFrameworkToolBar;

    private JComponent myPowerToolBar;

    private JComponent myWasteToolBar;

    private JComponent myWaterToolBar;

    private EditorFig myCurrentFig;

    private JSplitPane mySplitPane;

    /**
     * Contruct a new JGraphFrame with the given title and a new
     * DefaultGraphModel.
     */
    public BiosimEditor() {
        super("BioSim Editor", false);
        loadResources();
        buildGui();
    }

    /**
     *  
     */
    private void loadResources() {
        Localizer.addResource("GefBase",
                "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres",
                "org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("png");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
    }

    /**
     *  
     */
    private void buildGui() {
        //init graph
        createGraphPanel();

        //init tabbed pane tool bar
        createTabbedPane();

        //do splitpane
        mySplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, myTabbedPane,
                myGraphPanel);
        getContentPane().add(mySplitPane, BorderLayout.CENTER);
        getContentPane().add(myStatusbar, BorderLayout.SOUTH);

        //do menu bar
        createMenuBar();

        pack();
        setSize(680, 600);
        setVisible(true);
    }
    
    /**
     *  
     */
    private void createMenuBar() {
        myMenuBar = new JMenuBar();
        myFileMenu = new JMenu("File");
        myFileMenu.setMnemonic(KeyEvent.VK_F);
        myQuitAction = new QuitAction("Quit");
        myQuitItem = myFileMenu.add(myQuitAction);
        myQuitItem.setMnemonic(KeyEvent.VK_Q);
        myHelpMenu = new JMenu("Help");
        myAboutAction = new AboutAction("About");
        myAboutItem = myHelpMenu.add(myAboutAction);
        myMenuBar.add(myFileMenu);
        myMenuBar.add(myHelpMenu);
        setJMenuBar(myMenuBar);
    }
    
    /**
     *  
     */
    private void createGraphPanel() {
        myGraph = new JGraph();
        myGraph.setDrawingSize(0, 0);
        myGraphPanel = new JPanel();
        myGraphPanel.setLayout(new GridLayout(1, 1));
        myGraphPanel.add(myGraph);
        myGraph.getEditor().add(new FigCircle(0, 0, 50, 50));
        myGraph.addModeChangeListener(new BiosimModeChangeListener());
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private static ImageIcon createImageIcon(String path) {
        URL imgURL = ClassLoader.getSystemClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            Logger.getLogger(BiosimEditor.class.toString()).error(
                    "Couldn't find file for icon: " + path);
            return null;
        }
    }
    
    /**
     *  
     */
    private void createTabbedPane() {
        myTabbedPane = new JTabbedPane();

        myAirToolBar = new AirToolBar(this);
        myTabbedPane.addTab("Air",
                createImageIcon("com/traclabs/biosim/client/air/gui/air.png"),
                myAirToolBar);

        myCrewToolBar = new CrewToolBar(this);
        myTabbedPane
                .addTab(
                        "Crew",
                        createImageIcon("com/traclabs/biosim/client/crew/gui/crew.png"),
                        myCrewToolBar);

        myEnvironmentToolBar = new EnvironmentToolBar(this);
        myTabbedPane
                .addTab(
                        "Environment",
                        createImageIcon("com/traclabs/biosim/client/environment/gui/environment.png"),
                        myEnvironmentToolBar);

        myFrameworkToolBar = new FrameworkToolBar(this);
        myTabbedPane
                .addTab(
                        "Framework",
                        createImageIcon("com/traclabs/biosim/client/framework/gui/all.png"),
                        myFrameworkToolBar);

        myPowerToolBar = new PowerToolBar(this);
        myTabbedPane
                .addTab(
                        "Power",
                        createImageIcon("com/traclabs/biosim/client/power/gui/power.png"),
                        myPowerToolBar);

        myWasteToolBar = new WasteToolBar(this);
        myTabbedPane
                .addTab(
                        "Waste",
                        createImageIcon("com/traclabs/biosim/client/framework/gui/gear.png"),
                        myWasteToolBar);

        myWaterToolBar = new WaterToolBar(this);
        myTabbedPane
                .addTab(
                        "Water",
                        createImageIcon("com/traclabs/biosim/client/water/gui/water.png"),
                        myWaterToolBar);
    }

    /**
     *  
     */
    private static void initializeLogger() {
        Properties logProps = new Properties();
        logProps.setProperty("log4j.rootLogger", "INFO, rootAppender");
        logProps.setProperty("log4j.appender.rootAppender",
                "org.apache.log4j.ConsoleAppender");
        logProps.setProperty("log4j.appender.rootAppender.layout",
                "org.apache.log4j.PatternLayout");
        logProps.setProperty(
                "log4j.appender.rootAppender.layout.ConversionPattern",
                "%5p [%c] - %m%n");
        PropertyConfigurator.configure(logProps);

    }

    public static void main(String args[]) {
        BiosimEditor.initializeLogger();
        BiosimEditor editor = new BiosimEditor();
    }

    /** Show a message in the statusbar. */
    public void showStatus(String msg) {
        if (myStatusbar != null)
            myStatusbar.setText(msg);
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
            setCursor(Cursor
                    .getPredefinedCursor(Cursor.WAIT_CURSOR));
            frameClosing();
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
            JOptionPane.showMessageDialog(null, "BioSim Editor\nCopyright "
                    + new Character('\u00A9')
                    + " 2005, TRACLabs\nby Scott Bell and David Kortenkamp");
        }
    }
    
    private class BiosimModeChangeListener implements ModeChangeListener{
        public void modeChange(ModeChangeEvent mce) {
            //System.out.println("TabDiagram got mode change event");
            //if (!Globals.getSticky() && Globals.mode() instanceof ModeSelect)
                //myToolbar.unpressAllButtons();
        }
    }
}