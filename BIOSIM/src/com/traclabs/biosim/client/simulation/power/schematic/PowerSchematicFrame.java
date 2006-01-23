package com.traclabs.biosim.client.simulation.power.schematic;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.tigris.gef.base.CmdOpenWindow;
import org.tigris.gef.base.CmdPrint;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.client.framework.BioFrame;
import com.traclabs.biosim.client.simulation.framework.SimDesktop;
import com.traclabs.biosim.client.simulation.framework.SimulationToolBar;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicDocument;
import com.traclabs.biosim.client.simulation.power.schematic.base.PowerSchematicEditor;
import com.traclabs.biosim.util.OrbUtils;

public class PowerSchematicFrame extends BioFrame {
	private PowerSchematicEditor myEditor;
	
	private SimulationToolBar myToolBar;
	
    private JMenuBar myMenuBar = new JMenuBar();

    /** A statusbar (shown at bottom ow window). */
    private JLabel myStatusbar = new JLabel(" ");
    
    private PowerSchematicPanel myPowerSchematicPanel;

    private QuitAction myQuitAction = new QuitAction("Quit");

    public PowerSchematicFrame(String title) {
        super(title);
        loadResources();
        buildGui();
        myEditor = myPowerSchematicPanel.getEditor();
        myEditor.setFrame(this);
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
    
    public void setVisible(boolean visible){
    	super.setVisible(visible);
    	myPowerSchematicPanel.visibilityChange(visible);
    }
    
    /**
     *  
     */
    private void buildGui() {
    	myPowerSchematicPanel = new PowerSchematicPanel();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(myPowerSchematicPanel, BorderLayout.CENTER);
        //do menu bar
        createMenuBar();
        myToolBar = new SimulationToolBar();
        getContentPane().add(myToolBar, BorderLayout.NORTH);
        setJMenuBar(myMenuBar);
        pack();
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    private static ImageIcon createImageIcon(String path) {
        URL imgURL = PowerSchematicFrame.class.getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        }
		Logger.getLogger(PowerSchematicEditor.class.toString()).error(
		        "Couldn't find file for icon: " + path);
		return null;
    }
    
    //override the setUpMenus() in superclass
    private void createMenuBar() {
        createFileMenu();
        createHelpMenu();
    }

    private void createFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        myMenuBar.add(file);

        file.addSeparator();

        // Create the Print menu item.
        JMenuItem printItem = file.add(new CmdPrint());
        printItem.setMnemonic('P');
        KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                KeyEvent.CTRL_MASK);
        printItem.setAccelerator(ctrlP);

        // Create the Preferences menu item.
        file.add(new CmdOpenWindow(
                "org.tigris.gef.base.PrefsEditor", "Preferences..."));

        file.addSeparator();

        // Create the Exit menu item.
        JMenuItem exitItem = file.add(myQuitAction);
        KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,
                KeyEvent.ALT_MASK);
        exitItem.setAccelerator(altF4);
    }

    private void createHelpMenu() {
        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        myMenuBar.add(help);
        
        JMenuItem about = new JMenuItem(new AboutAction());
        about.setMnemonic('A');
        about.setEnabled(true);
        help.add(about);
    }
    
    public PowerSchematicDocument getDocument() {
        return (PowerSchematicDocument) this.myEditor.document();
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
    
    public static void main(String[] args){
    	OrbUtils.initializeLog();
    	PowerSchematicFrame newPowerSchematicFrame = new PowerSchematicFrame("CEV Power Schematic");
    	ImageIcon powerIcon = new ImageIcon(SimDesktop.class.getClassLoader()
                .getResource(
                        "com/traclabs/biosim/client/power/power.png"));
    	newPowerSchematicFrame.setIconImage(powerIcon.getImage());
    	newPowerSchematicFrame.setSize(1000, 500);
    	newPowerSchematicFrame.setLocationRelativeTo(null); 
    	newPowerSchematicFrame.setVisible(true);
    }
    
	/**
	 * Action that brings up a dialog box about authors, company, etc.
	 */
	private class AboutAction extends AbstractAction {
		public AboutAction(){
			super("CEV Power Schematic");
		}
		public void actionPerformed(ActionEvent ae) {
			JOptionPane.showMessageDialog(PowerSchematicFrame.this,
					"CEV Power Schematic\nCopyright "
							+ new Character('\u00A9') + " 2006, TRACLabs\n");
		}
	}
}