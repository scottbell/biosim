/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.presentation;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.tigris.gef.base.Cmd;
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
import org.tigris.gef.graph.presentation.JGraphFrame;

import com.traclabs.biosim.editor.base.CmdNewVespr;
import com.traclabs.biosim.editor.base.CmdOpenVespr;
import com.traclabs.biosim.editor.base.CmdSaveAsVespr;
import com.traclabs.biosim.editor.base.CmdSaveVespr;
import com.traclabs.biosim.editor.base.CmdTreeLayout;
import com.traclabs.biosim.editor.base.CmdZoomTo;
import com.traclabs.biosim.editor.base.EditorCmdCopy;
import com.traclabs.biosim.editor.base.EditorCmdCut;
import com.traclabs.biosim.editor.base.EditorCmdExit;
import com.traclabs.biosim.editor.base.EditorCmdPaste;
import com.traclabs.biosim.editor.base.EditorCmdSpawn;
import com.traclabs.biosim.editor.base.EditorCmdZoom;
import com.traclabs.biosim.editor.base.EditorDocument;
import com.traclabs.biosim.editor.base.BiosimEditor;
import com.traclabs.biosim.editor.ui.EditorPalette;

public class EditorFrame extends JGraphFrame {
    protected Cmd _exitCmd;

    private static int _refCount = 0;

    private static EditorFrame _active;

    public EditorFrame() {
        this("VESPR - ", new BiosimEditor());
    }

    public EditorFrame(String title) {
        this(title, new BiosimEditor());
    }

    public EditorFrame(EditorDocument doc) {
        this(doc.getAppName(), new BiosimEditor(doc));
    }

    public EditorFrame(String title, BiosimEditor ed) {
        super(title, ed);

        _refCount++;
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                //dispose();
                _exitCmd.doIt();
            }

            public void windowClosed(WindowEvent event) {
                //System.exit(0);
            }

            public void windowActivated(WindowEvent e) {
                // Switch the current editor to the active frame editor.
                // This is required so that menu commands will be executed
                // in the active frame window.
                _active = EditorFrame.this;
                Globals.curEditor(_graph.getEditor());
            }

            public void windowDeactivated(WindowEvent e) {
                _active = null;
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (_active == EditorFrame.this) {
                    Globals.curEditor(_graph.getEditor());
                }
            }
        });

        setToolBar(new EditorPalette()); //needs-more-work

        // make the delete key remove elements from the underlying GraphModel
        getGraph().bindKey(new CmdDeleteFromModel(), KeyEvent.VK_DELETE, 0);
    }

    public static EditorFrame curFrame() {
        return _active;
    }

    //override the setUpMenus() in superclass
    protected void setUpMenus() {
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
        _menubar.add(file);

        // Create the New menu item.
        JMenuItem newItem = file.add(new CmdNewVespr());
        newItem.setMnemonic('N');
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N,
                KeyEvent.CTRL_MASK);
        newItem.setAccelerator(ctrlN);
        file.add(newItem); //added for VESPR

        // Create the Open menu item.
        JMenuItem openItem = file.add(new CmdOpenVespr());
        openItem.setMnemonic('O');
        KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O,
                KeyEvent.CTRL_MASK);
        openItem.setAccelerator(ctrlO);

        // Create the Save menu item.
        JMenuItem saveItem = file.add(new CmdSaveVespr());
        saveItem.setMnemonic('S');
        KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.CTRL_MASK);
        saveItem.setAccelerator(ctrlS);

        // Create the Save As menu item.
        JMenuItem saveAsItem = file.add(new CmdSaveAsVespr());
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
        JMenuItem exitItem = file.add(_exitCmd = new EditorCmdExit(this));
        KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,
                KeyEvent.ALT_MASK);
        exitItem.setAccelerator(altF4);
    }

    private void createEditMenu() {
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic('E');
        _menubar.add(edit);

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

        //added for VESPR
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
        _menubar.add(view);
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

        //added for VESPR
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
        //added for VESPR
        JMenu zoom = new JMenu("Zoom");
        _menubar.add(zoom);
        zoom.setMnemonic('Z');

        JMenuItem zoomItem = zoom.add(new EditorCmdZoom(EditorCmdZoom.ZOOM_IN));
        KeyStroke ctrlPlus = KeyStroke.getKeyStroke(KeyEvent.VK_ADD,
                KeyEvent.CTRL_MASK);
        zoomItem.setAccelerator(ctrlPlus);

        zoomItem = zoom.add(new EditorCmdZoom(EditorCmdZoom.ZOOM_OUT));
        KeyStroke ctrlMinus = KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT,
                KeyEvent.CTRL_MASK);
        zoomItem.setAccelerator(ctrlMinus);

        //		zoom.add(new CmdZoom(0.0));
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
        //added for VESPR
        JMenu report = new JMenu("Report");
        _menubar.add(report);
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
        _menubar.add(arrange);

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
        _menubar.add(help);

        JMenuItem CheckNetwork = new JMenuItem("Check Network");
        CheckNetwork.setMnemonic('C');
        CheckNetwork.setEnabled(false);
        help.add(CheckNetwork);

        JMenuItem About = new JMenuItem("About VESPR");
        About.setMnemonic('A');
        About.setEnabled(false);
        help.add(About);
    }

    /**
     * Close this vespr frame.
     */
    public void exit() {
        // Remove this editor from the document.
        Editor ed = (BiosimEditor) getGraph().getEditor();

        // Remove the editor from the layer.
        Layer layer = ed.getLayerManager().getActiveLayer();
        layer.removeEditor(ed);

        // Remove the editor from the document.
        ed.document(null);

        // Destroy the window.
        dispose();

        // Quit the application when no windows are left.
        _refCount--;

        //		if (_refCount == 0) {
        //			Main.getInstance().exit(0);
        //		}
    }

    ////////////////////////////////////////////////////////////////
    // Cloneable implementation
    public Object clone() {
        return new EditorFrame(getTitle(), (BiosimEditor) getGraph().getEditor()
                .clone());
    }

    public EditorDocument getDocument() {
        return (EditorDocument) this.getGraph().getEditor().document();
    }
}