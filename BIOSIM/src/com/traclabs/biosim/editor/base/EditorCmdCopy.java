/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/**
 * Copies the selected figs to the clipboard. Copy is implemented by saving the
 * selected figs to a temporary file. The name of this file is placed on the
 * system clipboard. Paste retrieves this file name and parsed the file.
 * 
 * @author Kevin Kusy
 */

package com.traclabs.biosim.editor.base;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JOptionPane;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;

public class EditorCmdCopy extends Cmd implements ClipboardOwner {
    private FileSelection _selection;

    private File _tempFile;

    public EditorCmdCopy() {
        super("Copy");
        putValue(Action.SHORT_DESCRIPTION,
                "Copies the selection and puts it on the clipboard");
    }

    public void doIt() {
        BiosimEditor ed = (BiosimEditor) Globals.curEditor();
        try {
            Clipboard clipboard = ed.getJComponent().getToolkit()
                    .getSystemClipboard();
            Vector copies = ed.getSelectionManager().selections();
            if (copies.size() > 0) {

                if (_selection == null) {
                    _tempFile = File.createTempFile("biosimEditor", ".tmp");
                    _tempFile.deleteOnExit();
                    _selection = new FileSelection(_tempFile);
                }

                EditorDocument doc = (EditorDocument) ed.document();
                doc.copySelections(_tempFile, ed);
                clipboard.setContents(_selection, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(ed.findFrame(), "Copy failed.\n"
                    + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void undoIt() {
    }

    public void lostOwnership(Clipboard parClipboard,
            Transferable parTransferable) {
        System.out.println("Lost ownership");
    }

}