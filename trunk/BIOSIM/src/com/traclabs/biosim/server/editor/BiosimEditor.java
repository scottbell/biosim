package com.traclabs.biosim.server.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import org.tigris.gef.base.CmdExit;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.demo.SamplePalette;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.ui.IStatusBar;
import org.tigris.gef.ui.ToolBar;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

/**
 * A window that displays a toolbar, a connected graph editing pane, and a
 * status bar.
 */

public class BiosimEditor extends JFrame implements IStatusBar, Cloneable,
        ModeChangeListener {

    /** The toolbar (shown at top of window). */
    protected ToolBar myToolbar;

    /** The graph pane (shown in middle of window). */
    protected JGraph myGraph;

    /** A statusbar (shown at bottom ow window). */
    protected JLabel myStatusbar = new JLabel(" ");

    protected JPanel myMainPanel = new JPanel(new BorderLayout());

    protected JPanel myGraphPanel = new JPanel(new BorderLayout());

    protected JMenuBar myMenubar = new JMenuBar();

    /**
     * Contruct a new JGraphFrame with the given title and a new
     * DefaultGraphModel.
     */
    public BiosimEditor(String title) {
        myGraph = new JGraph();
        Localizer.addResource("GefBase",
                "org.tigris.gef.base.BaseResourceBundle");
        Localizer.addResource("GefPres",
                "org.tigris.gef.presentation.PresentationResourceBundle");
        Localizer.addLocale(Locale.getDefault());
        Localizer.switchCurrentLocale(Locale.getDefault());
        ResourceLoader.addResourceExtension("gif");
        ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                dispose();
            }

            public void windowClosed(WindowEvent event) {
                System.exit(0);
            }
        });
        myToolbar = new SamplePalette();
        Container content = getContentPane();
        setUpMenus();
        content.setLayout(new BorderLayout());
        content.add(myMenubar, BorderLayout.NORTH);
        myGraphPanel.add(myGraph, BorderLayout.CENTER);
        myGraphPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

        myMainPanel.add(myToolbar, BorderLayout.NORTH);
        myMainPanel.add(myGraphPanel, BorderLayout.CENTER);
        content.add(myMainPanel, BorderLayout.CENTER);
        content.add(myStatusbar, BorderLayout.SOUTH);
        setSize(300, 250);
        myGraph.addModeChangeListener(this);

        setBounds(10, 10, 300, 200);
        setVisible(true);
    }

    public static void main(String args[]) {
        BiosimEditor editor = new BiosimEditor("BioSim Editor");
    }
    
    // ModeChangeListener implementation
    public void modeChange(ModeChangeEvent mce) {
        //System.out.println("TabDiagram got mode change event");
        if (!Globals.getSticky() && Globals.mode() instanceof ModeSelect)
            myToolbar.unpressAllButtons();
    }

    /**
     * Set up the menus and keystrokes for menu items. Subclasses can override
     * this, or you can use setMenuBar().
     */
    protected void setUpMenus() {
        JMenuItem exitItem;

        JMenu file = new JMenu(Localizer.localize("GefBase", "File"));
        file.setMnemonic('F');
        myMenubar.add(file);
        exitItem = file.add(new CmdExit());
        KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,
                KeyEvent.ALT_MASK);
        exitItem.setAccelerator(altF4);
    }

    /** Show a message in the statusbar. */
    public void showStatus(String msg) {
        if (myStatusbar != null)
            myStatusbar.setText(msg);
    }
}