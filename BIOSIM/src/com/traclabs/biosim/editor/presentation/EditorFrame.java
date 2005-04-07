package com.traclabs.biosim.editor.presentation;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.Action;
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
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;
import org.tigris.gef.base.CmdAdjustGrid;
import org.tigris.gef.base.CmdAdjustGuide;
import org.tigris.gef.base.CmdAdjustPageBreaks;
import org.tigris.gef.base.CmdAlign;
import org.tigris.gef.base.CmdDeleteFromModel;
import org.tigris.gef.base.CmdDistribute;
import org.tigris.gef.base.CmdGroup;
import org.tigris.gef.base.CmdNudge;
import org.tigris.gef.base.CmdOpenWindow;
import org.tigris.gef.base.CmdPrint;
import org.tigris.gef.base.CmdReorder;
import org.tigris.gef.base.CmdSelectAll;
import org.tigris.gef.base.CmdSelectInvert;
import org.tigris.gef.base.CmdSelectNext;
import org.tigris.gef.base.CmdShowProperties;
import org.tigris.gef.base.CmdUngroup;
import org.tigris.gef.base.CmdUseReshape;
import org.tigris.gef.base.CmdUseResize;
import org.tigris.gef.base.CmdUseRotate;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;

import com.traclabs.biosim.client.framework.gui.BioFrame;
import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.base.CmdNewEditor;
import com.traclabs.biosim.editor.base.CmdOpenEditor;
import com.traclabs.biosim.editor.base.CmdSaveAsEditor;
import com.traclabs.biosim.editor.base.CmdSaveEditor;
import com.traclabs.biosim.editor.base.CmdTreeLayout;
import com.traclabs.biosim.editor.base.CmdZoomTo;
import com.traclabs.biosim.editor.base.EditorCmdCopy;
import com.traclabs.biosim.editor.base.EditorCmdCut;
import com.traclabs.biosim.editor.base.EditorCmdPaste;
import com.traclabs.biosim.editor.base.EditorCmdSpawn;
import com.traclabs.biosim.editor.base.EditorCmdZoom;
import com.traclabs.biosim.editor.base.EditorDocument;
import com.traclabs.biosim.editor.ui.AirToolBar;
import com.traclabs.biosim.editor.ui.CrewToolBar;
import com.traclabs.biosim.editor.ui.EnvironmentToolBar;
import com.traclabs.biosim.editor.ui.FrameworkToolBar;
import com.traclabs.biosim.editor.ui.PowerToolBar;
import com.traclabs.biosim.editor.ui.WasteToolBar;
import com.traclabs.biosim.editor.ui.WaterToolBar;

public class EditorFrame extends BioFrame {
    private Editor myEditor;
    
    private JMenuBar myMenuBar = new JMenuBar();
    
    /** The graph pane (shown in middle of window). */
    private JGraph myGraph;

    /** A statusbar (shown at bottom ow window). */
    private JLabel myStatusbar = new JLabel(" ");
    
    private JPanel myGraphPanel;

    private JTabbedPane myTabbedPane;

    private Logger myLogger;

    private JMenu myFileMenu;

    private JMenu myNewMenu;

    private JMenuItem myLoggingItem;

    private JMenuItem myQuitItem;

    private JMenu myHelpMenu;

    private JMenuItem myAboutItem;

    private AboutAction myAboutAction = new AboutAction("About");;

    private QuitAction myQuitAction = new QuitAction("Quit");

    private JComponent myAirToolBar;

    private JComponent myCrewToolBar;

    private JComponent myEnvironmentToolBar;

    private JComponent myFrameworkToolBar;

    private JComponent myPowerToolBar;

    private JComponent myWasteToolBar;

    private JComponent myWaterToolBar;

    private JSplitPane mySplitPane;

    public EditorFrame() {
        this("BiosimEditor", new BiosimEditor());
    }

    public EditorFrame(String title) {
        this(title, new BiosimEditor());
    }

    public EditorFrame(String title, BiosimEditor pEditor) {
        super(title);
        myEditor = pEditor;  
        loadResources();
        buildGui();
        Globals.curEditor(myEditor);
        // make the delete key remove elements from the underlying GraphModel
        myGraph.bindKey(new CmdDeleteFromModel(), KeyEvent.VK_DELETE, 0);
    }
    
    public Editor getEditor(){
        return myEditor;
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
        mySplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, myTabbedPane, myGraphPanel);
        getContentPane().add(mySplitPane, BorderLayout.CENTER);
        getContentPane().add(myStatusbar, BorderLayout.SOUTH);

        //do menu bar
        createMenuBar();
        
        pack();
        setSize(700, 600);
        setVisible(true);
    }
    
    /**
     *  
     */
    private void createGraphPanel() {
        myGraph = new JGraph(myEditor);
        myGraph.setDrawingSize(0, 0);
        myGraphPanel = new JPanel();
        myGraphPanel.setLayout(new GridLayout(1, 1));
        myGraphPanel.add(myGraph);
        myGraph.getEditor().add(new FigCircle(0, 0, 50, 50));
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

        myAirToolBar = new AirToolBar();
        myTabbedPane.addTab("Air",
                createImageIcon("com/traclabs/biosim/client/air/gui/air.png"),
                myAirToolBar);
        myCrewToolBar = new CrewToolBar();
        myTabbedPane
                .addTab(
                        "Crew",
                        createImageIcon("com/traclabs/biosim/client/crew/gui/crew.png"),
                        myCrewToolBar);

        myEnvironmentToolBar = new EnvironmentToolBar();
        myTabbedPane
                .addTab(
                        "Environment",
                        createImageIcon("com/traclabs/biosim/client/environment/gui/environment.png"),
                        myEnvironmentToolBar);

        myFrameworkToolBar = new FrameworkToolBar();
        myTabbedPane
                .addTab(
                        "Framework",
                        createImageIcon("com/traclabs/biosim/client/framework/gui/all.png"),
                        myFrameworkToolBar);

        myPowerToolBar = new PowerToolBar();
        myTabbedPane
                .addTab(
                        "Power",
                        createImageIcon("com/traclabs/biosim/client/power/gui/power.png"),
                        myPowerToolBar);

        myWasteToolBar = new WasteToolBar();
        myTabbedPane
                .addTab(
                        "Waste",
                        createImageIcon("com/traclabs/biosim/client/framework/gui/gear.png"),
                        myWasteToolBar);

        myWaterToolBar = new WaterToolBar();
        myTabbedPane
                .addTab(
                        "Water",
                        createImageIcon("com/traclabs/biosim/client/water/gui/water.png"),
                        myWaterToolBar);
                        
    }

    //override the setUpMenus() in superclass
    private void createMenuBar() {
        createFileMenu();
        createEditMenu();
        createViewMenu();
        createZoomMenu();
        createReportMenu();
        createArrangeMenu();
        createHelpMenu();
    }

    private void createFileMenu() {
        JMenu file = new JMenu("File");
        file.setMnemonic('F');
        myMenuBar.add(file);

        // Create the New menu item.
        JMenuItem newItem = file.add(new CmdNewEditor());
        newItem.setMnemonic('N');
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                KeyEvent.CTRL_MASK);
        newItem.setAccelerator(ctrlN);
        file.add(newItem);

        // Create the Open menu item.
        JMenuItem openItem = file.add(new CmdOpenEditor());
        openItem.setMnemonic('O');
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                KeyEvent.CTRL_MASK);
        openItem.setAccelerator(ctrlO);

        // Create the Save menu item.
        JMenuItem saveItem = file.add(new CmdSaveEditor());
        saveItem.setMnemonic('S');
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.CTRL_MASK);
        saveItem.setAccelerator(ctrlS);

        // Create the Save As menu item.
        JMenuItem saveAsItem = file.add(new CmdSaveAsEditor());
        saveAsItem.setMnemonic('A');

        file.addSeparator();

        // Create the Print menu item.
        JMenuItem printItem = file.add(new CmdPrint());
        printItem.setMnemonic('P');
        KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P,
                KeyEvent.CTRL_MASK);
        printItem.setAccelerator(ctrlP);

        // Create the Preferences menu item.
        JMenuItem prefsItem = file.add(new CmdOpenWindow(
                "org.tigris.gef.base.PrefsEditor", "Preferences..."));

        file.addSeparator();

        // Create the Exit menu item.
        JMenuItem exitItem = file.add(myQuitAction);
        KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,
                KeyEvent.ALT_MASK);
        exitItem.setAccelerator(altF4);
    }

    private void createEditMenu() {
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('E');
        myMenuBar.add(edit);

        JMenu select = new JMenu("Select");
        edit.add(select);
        select.add(new CmdSelectAll());
        select.add(new CmdSelectNext(false));
        select.add(new CmdSelectNext(true));
        select.add(new CmdSelectInvert());

        edit.addSeparator();

        // Create the Copy menu item
        JMenuItem copyItem = edit.add(new EditorCmdCopy());
        copyItem.setMnemonic('C');
        KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                KeyEvent.CTRL_MASK);
        copyItem.setAccelerator(ctrlC);

        // Create the Paste menu item
        JMenuItem pasteItem = edit.add(new EditorCmdPaste());
        pasteItem.setMnemonic('P');
        KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                KeyEvent.CTRL_MASK);
        pasteItem.setAccelerator(ctrlV);

        // Create the Cut menu item.
        JMenuItem cutItem = edit.add(new EditorCmdCut());
        cutItem.setMnemonic('T');
        KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                KeyEvent.CTRL_MASK);
        cutItem.setAccelerator(ctrlX);

        // Create the Delete menu item.
        Action cmdDelete = new CmdDeleteFromModel();
        cmdDelete.putValue(Action.NAME, "Delete");
        JMenuItem deleteItem = edit.add(cmdDelete);
        KeyStroke delKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
        deleteItem.setAccelerator(delKey);

        edit.addSeparator();

        edit.add(new CmdUseReshape());
        edit.add(new CmdUseResize());
        edit.add(new CmdUseRotate());

        edit.addSeparator();

        JMenuItem EditDomainName = new JMenuItem("Edit Domain Name");
        EditDomainName.setMnemonic('N');
        EditDomainName.setEnabled(false);
        edit.add(EditDomainName);

        JMenuItem FindTask = new JMenuItem("Find Task...");
        FindTask.setEnabled(false);
        FindTask.setMnemonic('F');
        edit.add(FindTask);

        JMenuItem FindNext = new JMenuItem("Find Next");
        FindNext.setMnemonic('N');
        FindNext.setEnabled(false);
        edit.add(FindNext);
    }

    private void createViewMenu() {
        JMenu view = new JMenu("View");
        myMenuBar.add(view);
        view.setMnemonic('V');

        view.add(new EditorCmdSpawn());

        JMenuItem propsItem = view.add(new CmdShowProperties());
        propsItem.setMnemonic('P');
        KeyStroke altEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
                KeyEvent.ALT_MASK);
        propsItem.setAccelerator(altEnter);

        view.add(new CmdAdjustGrid());
        view.add(new CmdAdjustGuide());
        view.add(new CmdAdjustPageBreaks());

        JMenuItem Redraw = new JMenuItem("Redraw");
        Redraw.setMnemonic('R');
        view.add(Redraw);
        JMenuItem JumpToStart = new JMenuItem("Jump to Start");
        JumpToStart.setMnemonic('S');
        view.add(JumpToStart);
        JMenuItem JumpToEnd = new JMenuItem("Jump to End");
        JumpToEnd.setMnemonic('E');
        view.add(JumpToEnd);

        Redraw.setEnabled(false);
        JumpToStart.setEnabled(false);
        JumpToEnd.setEnabled(false);
    }

    private void createZoomMenu() {
        JMenu zoom = new JMenu("Zoom");
        myMenuBar.add(zoom);
        zoom.setMnemonic('Z');

        JMenuItem zoomItem = zoom.add(new EditorCmdZoom(EditorCmdZoom.ZOOM_IN));
        KeyStroke ctrlPlus = KeyStroke.getKeyStroke(KeyEvent.VK_ADD,
                KeyEvent.CTRL_MASK);
        zoomItem.setAccelerator(ctrlPlus);

        zoomItem = zoom.add(new EditorCmdZoom(EditorCmdZoom.ZOOM_OUT));
        KeyStroke ctrlMinus = KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,
                KeyEvent.CTRL_MASK);
        zoomItem.setAccelerator(ctrlMinus);

        zoom.add(new CmdZoomTo());

        zoom.addSeparator();

        JMenuItem View50 = new JMenuItem("View at 50%");
        View50.setMnemonic('V');
        zoom.add(View50);
        JMenuItem View25 = new JMenuItem("View at 25%");
        View25.setMnemonic('V');
        zoom.add(View25);
        JMenuItem View12 = new JMenuItem("View at 12%");
        View12.setMnemonic('V');
        zoom.add(View12);

        View50.setEnabled(false);
        View25.setEnabled(false);
        View12.setEnabled(false);
    }

    private void createReportMenu() {
        JMenu report = new JMenu("Report");
        myMenuBar.add(report);
        report.setMnemonic('R');
        JMenuItem Printer = new JMenuItem("Print");
        Printer.setMnemonic('P');
        report.add(Printer);
        JMenuItem Screen = new JMenuItem("Screen");
        Screen.setMnemonic('S');
        report.add(Screen);
        JMenuItem Disk = new JMenuItem("Disk");
        Disk.setMnemonic('D');
        report.add(Disk);

        Printer.setEnabled(false);
        Screen.setEnabled(false);
        Disk.setEnabled(false);
    }

    private void createArrangeMenu() {
        JMenu arrange = new JMenu("Arrange");
        arrange.setMnemonic('A');
        myMenuBar.add(arrange);

        JMenuItem groupItem = arrange.add(new CmdGroup());
        groupItem.setMnemonic('G');
        KeyStroke ctrlG = KeyStroke.getKeyStroke(KeyEvent.VK_G,
                KeyEvent.CTRL_MASK);
        groupItem.setAccelerator(ctrlG);

        JMenuItem ungroupItem = arrange.add(new CmdUngroup());
        ungroupItem.setMnemonic('U');
        KeyStroke ctrlU = KeyStroke.getKeyStroke(KeyEvent.VK_U,
                KeyEvent.CTRL_MASK);
        ungroupItem.setAccelerator(ctrlU);

        JMenuItem treeItem = arrange.add(new CmdTreeLayout());
        treeItem.setMnemonic('T');
        KeyStroke ctrlT = KeyStroke.getKeyStroke(KeyEvent.VK_T,
                KeyEvent.CTRL_MASK);
        treeItem.setAccelerator(ctrlT);

        JMenu align = new JMenu("Align");
        arrange.add(align);
        align.add(new CmdAlign(CmdAlign.ALIGN_TOPS));
        align.add(new CmdAlign(CmdAlign.ALIGN_BOTTOMS));
        align.add(new CmdAlign(CmdAlign.ALIGN_LEFTS));
        align.add(new CmdAlign(CmdAlign.ALIGN_RIGHTS));
        align.add(new CmdAlign(CmdAlign.ALIGN_H_CENTERS));
        align.add(new CmdAlign(CmdAlign.ALIGN_V_CENTERS));
        align.add(new CmdAlign(CmdAlign.ALIGN_TO_GRID));

        JMenu distribute = new JMenu("Distribute");
        arrange.add(distribute);
        distribute.add(new CmdDistribute(CmdDistribute.H_SPACING));
        distribute.add(new CmdDistribute(CmdDistribute.H_CENTERS));
        distribute.add(new CmdDistribute(CmdDistribute.V_SPACING));
        distribute.add(new CmdDistribute(CmdDistribute.V_CENTERS));

        JMenu reorder = new JMenu("Reorder");
        arrange.add(reorder);

        JMenuItem toBackItem = reorder.add(new CmdReorder(
                CmdReorder.SEND_TO_BACK));
        KeyStroke sCtrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B,
                KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
        toBackItem.setAccelerator(sCtrlB);

        JMenuItem toFrontItem = reorder.add(new CmdReorder(
                CmdReorder.BRING_TO_FRONT));
        KeyStroke sCtrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F,
                KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
        toFrontItem.setAccelerator(sCtrlF);

        JMenuItem backwardItem = reorder.add(new CmdReorder(
                CmdReorder.SEND_BACKWARD));
        KeyStroke ctrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B,
                KeyEvent.CTRL_MASK);
        backwardItem.setAccelerator(ctrlB);

        JMenuItem forwardItem = reorder.add(new CmdReorder(
                CmdReorder.BRING_FORWARD));
        KeyStroke ctrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F,
                KeyEvent.CTRL_MASK);
        forwardItem.setAccelerator(ctrlF);

        JMenu nudge = new JMenu("Nudge");
        arrange.add(nudge);

        JMenuItem nudgeLeftItem = nudge.add(new CmdNudge(CmdNudge.LEFT));
        KeyStroke sLeftArrow = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,
                KeyEvent.SHIFT_MASK);
        nudgeLeftItem.setAccelerator(sLeftArrow);

        JMenuItem nudgeRightItem = nudge.add(new CmdNudge(CmdNudge.RIGHT));
        KeyStroke sRightArrow = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,
                KeyEvent.SHIFT_MASK);
        nudgeRightItem.setAccelerator(sRightArrow);

        JMenuItem nudgeUpItem = nudge.add(new CmdNudge(CmdNudge.UP));
        KeyStroke sUpArrow = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                KeyEvent.SHIFT_MASK);
        nudgeUpItem.setAccelerator(sUpArrow);

        JMenuItem nudgeDownItem = nudge.add(new CmdNudge(CmdNudge.DOWN));
        KeyStroke sDownArrow = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                KeyEvent.SHIFT_MASK);
        nudgeDownItem.setAccelerator(sDownArrow);
    }

    private void createHelpMenu() {
        JMenu help = new JMenu("Help");
        help.setMnemonic('H');
        myMenuBar.add(help);

        JMenuItem CheckNetwork = new JMenuItem("Check Network");
        CheckNetwork.setMnemonic('C');
        CheckNetwork.setEnabled(false);
        help.add(CheckNetwork);

        JMenuItem About = new JMenuItem("About Biosim Editor");
        About.setMnemonic('A');
        About.setEnabled(false);
        help.add(About);
    }

    /**
     * Close this editor frame.
     */
    public void exit() {
        // Remove the editor from the layer.
        Layer layer = myEditor.getLayerManager().getActiveLayer();
        layer.removeEditor(myEditor);

        // Remove the editor from the document.
        myEditor.document(null);

        // Destroy the window.
        dispose();
    }
    
    public Object clone() {
        return new EditorFrame(getTitle(), (BiosimEditor)myEditor.clone());
    }

    public EditorDocument getDocument() {
        return (EditorDocument) this.myEditor.document();
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
}