/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/**
 * Paste the figs that have been saved to the clipboard. Paste recreates figs
 * that have been saved to a temporary file. The clipboard contains the name of
 * this file.
 */
package com.traclabs.biosim.editor.base;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JViewport;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.CmdSelectAll;
import org.tigris.gef.base.CmdSelectInvert;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.SelectionManager;

public class EditorCmdPaste extends Cmd {
    private CmdSelectAll _selectAllCmd = new CmdSelectAll();

    private CmdSelectInvert _selectInvertCmd = new CmdSelectInvert();

    public EditorCmdPaste() {
        super("Paste");
        putValue(Action.SHORT_DESCRIPTION, "Inserts clipboard contents");
    }

    public void doIt() {

        try {
            BiosimEditor ed = (BiosimEditor) Globals.curEditor();
            Clipboard clipboard = ed.getJComponent().getToolkit()
                    .getSystemClipboard();
            DataFlavor flavor = new DataFlavor(Class.forName("java.io.File"),
                    "File");
            Transferable clipboardContent = clipboard.getContents(this);
            if ((clipboardContent != null)
                    && (clipboardContent.isDataFlavorSupported(flavor))) {
                _selectAllCmd.doIt();
                File tempFile = (File) clipboardContent.getTransferData(flavor);
                EditorDocument doc = (EditorDocument) ed.document();
                doc.pasteSelections(tempFile, ed);
                _selectInvertCmd.doIt();
                translate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Globals.curEditor().findFrame(),
                    "Paste failed.\n" + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Translates the pasted figs to the upper left corner of the graph.
     */
    void translate() {
        Editor ed = Globals.curEditor();
        SelectionManager sm = ed.getSelectionManager();
        Rectangle rect = sm.getContentBounds();

        Component c = ed.getJComponent();
        if (c != null && c.getParent() instanceof JViewport) {
            JViewport view = (JViewport) c.getParent();
            Point viewPosition = view.getViewPosition();
            sm.translate((int) (viewPosition.getX() + 10 - rect.getX()),
                    (int) (viewPosition.getY() + 10 - rect.getY()));
        }
    }

    public void undoIt() {
        System.out.println("VesprCmdPaste.undoIt");
    }
}