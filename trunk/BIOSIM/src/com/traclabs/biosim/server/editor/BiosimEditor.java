package com.traclabs.biosim.server.editor;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
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
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;

import com.traclabs.biosim.client.framework.gui.BioFrame;

/**
 * @author scott
 */
public class BiosimEditor {
    BioFrame myMainFrame;

    JGraph myGraph;
    
    JPanel myGraphPanel;

    JTabbedPane myTabbedPane;

    Logger myLogger;

    private JMenuBar myMenuBar;

    private JMenu myFileMenu;

    private JMenu myNewMenu;

    private JMenuItem myLoggingItem;

    private JMenuItem myQuitItem;

    private JMenu myHelpMenu;

    private JMenuItem myAboutItem;

    private AboutAction myAboutAction;

    private QuitAction myQuitAction;

    private JComponent myAirPanel;

    private JComponent myCrewPanel;

    private JComponent myEnvironmentPanel;

    private JComponent myFrameworkPanel;

    private JComponent myPowerPanel;

    private JComponent myWastePanel;

    private JComponent myWaterPanel;
    
    private Fig myCurrentFigure;

    private JSplitPane mySplitPane;

    public BiosimEditor(boolean fromMainMethod) {
        if (fromMainMethod)
            initLogger();
        buildGui();
    }

    /**
     *  
     */
    private void buildGui() {
        //init graph
        createGraphPanel();

        //init tabbed pane tool bar
        createTabbedPane();
        
        //create main frame
        myMainFrame = new BioFrame("BioSim Editor", false);
        
        //do splitpane
        mySplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,myTabbedPane, myGraphPanel);
        myMainFrame.getContentPane().setLayout(new GridLayout(1, 1));
        myMainFrame.getContentPane().add(mySplitPane);
        
        //do menu bar
        createMenuBar();
        
        myMainFrame.pack();
        myMainFrame.setSize(650, 600);
        myMainFrame.setVisible(true);
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
        myMainFrame.setJMenuBar(myMenuBar);
    }

    /**
     * 
     */
    private void createTabbedPane() {
        myTabbedPane = new JTabbedPane();
        
        myAirPanel = new AirPanel();
        myTabbedPane
                .addTab(
                        "Air",
                        createImageIcon("com/traclabs/biosim/client/air/gui/air.jpg"),
                        myAirPanel);
        
        myCrewPanel = new CrewPanel();
        myTabbedPane
                .addTab(
                        "Crew",
                        createImageIcon("com/traclabs/biosim/client/crew/gui/crew.jpg"),
                        myCrewPanel);
        
        myEnvironmentPanel = new EnvironmentPanel();
        myTabbedPane
                .addTab(
                        "Environment",
                        createImageIcon("com/traclabs/biosim/client/environment/gui/environment.jpg"),
                        myEnvironmentPanel);
        
        myFrameworkPanel = new FrameworkPanel();
        myTabbedPane
                .addTab(
                        "Framework",
                        createImageIcon("com/traclabs/biosim/client/framework/gui/all.jpg"),
                        myFrameworkPanel);
        
        myPowerPanel = new PowerPanel();
        myTabbedPane
                .addTab(
                        "Power",
                        createImageIcon("com/traclabs/biosim/client/power/gui/power.jpg"),
                        myPowerPanel);
        
        myWastePanel = new WastePanel();
        myTabbedPane
                .addTab(
                        "Waste",
                        createImageIcon("com/traclabs/biosim/client/framework/gui/gear.gif"),
                        myWastePanel);
        
        myWaterPanel = new WaterPanel();
        myTabbedPane
                .addTab(
                        "Water",
                        createImageIcon("com/traclabs/biosim/client/water/gui/water.jpg"),
                        myWaterPanel);
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
        myGraph.getEditor().add(new FigCircle(0,0,50,50));
    }

    private JComponent createModulePanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
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
    private void initLogger() {
        myLogger = Logger.getLogger( BiosimEditor.class.toString());
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
        BiosimEditor editor = new BiosimEditor(true);
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
            myMainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            myMainFrame.frameClosing();
            myMainFrame.setCursor(Cursor.getDefaultCursor());
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
                            "BioSim Editor\nCopyright "
                                    + new Character('\u00A9')
                                    + " 2005, TRACLabs\nby Scott Bell and David Kortenkamp");
        }
    }

    /**
     * @param figure
     */
    public void setCurrentFigure(Fig pFigure) {
        myCurrentFigure = pFigure;
        
    }
    
}