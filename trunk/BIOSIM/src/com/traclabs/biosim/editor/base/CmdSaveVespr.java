/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.base;

import java.io.File;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;

public class CmdSaveVespr extends Cmd {

    public CmdSaveVespr() {
        super("Save");
        putValue(Action.SHORT_DESCRIPTION,
                "Saves the active document with a new name");
    }

    public void doIt() {
        VesprEditor ce = (VesprEditor) Globals.curEditor();
        if (!save((VesprDocument) ce.document())) {
            JOptionPane.showMessageDialog(ce.findFrame(),
                    "Failed to save document.");
        }
    }

    /**
     * Saves a vespr document to a file. If the document does not have a
     * filename the user will be prompted for one.
     * 
     * @return Returns false if the save failed.
     */
    public boolean save(VesprDocument doc) {
        File file = doc.getFile();

        if (file == null) {
            return saveAs(doc); // no filename
        }

        Globals.showStatus("Saving " + file.getPath());
        try {
            doc.saveDocument(file);
            return true; // Save successful.
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Globals.curEditor().findFrame(),
                    "Copy failed.\n" + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false; // Save failed.
    }

    /**
     * Prompts the user for a filename and saves the vespr document to that
     * file.
     * 
     * @return Returns false if the save failed or if it was cancelled by the
     *         user.
     */
    public boolean saveAs(VesprDocument doc) {
        File file = saveDialog(doc);

        if (file != null) {
            Globals.showStatus("Saving " + file.getPath());
            try {
                doc.saveDocument(file);
                return true; // Save successful.
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(Globals.curEditor().findFrame(),
                        "Save failed.\n" + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        // Save As cancelled or failed?
        return false;
    }

    protected File saveDialog(VesprDocument doc) {
        File result = null;
        JFileChooser fc = new JFileChooser();

        SimpleFileFilter filter = doc.getFileFilter();
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(Globals.getLastDirectory()));
        int returnVal = fc.showSaveDialog(Globals.curEditor().findFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Globals.setLastDirectory(fc.getCurrentDirectory().getPath());
            result = fc.getSelectedFile();

            if (result.getName().lastIndexOf('.') == -1) {
                // No extension specified.
                String[] exts = filter.getExtensions();
                String ext = exts.length > 0 ? ("." + exts[0]) : "";
                result = new File(result.getPath() + ext);
            }
        }

        return result;
    }

    /**
     * Prompts the user to save the document before a new document is loaded.
     * 
     * @return Returns false if the user cancels or if the save fails.
     */
    boolean promptSave(VesprEditor e) {
        VesprDocument doc = (VesprDocument) e.document();
        // Prompt to save changes.
        Vector editors = doc.getEditors();
        if (editors.size() == 1 && doc.getModified() == true) {
            String msg;

            if (doc.getFile() == null) {
                msg = "Save changes to Untitled?";
            } else {
                File file = doc.getFile();
                msg = "Save changes to " + file.toString() + "?";
            }

            int result = JOptionPane.showOptionDialog(e.findFrame(), msg,
                    "New", JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);

            switch (result) {
            case JOptionPane.YES_OPTION:
                return save(doc); // save and continue.
            case JOptionPane.NO_OPTION:
                return true; // no save but continue.
            case JOptionPane.CANCEL_OPTION:
            case JOptionPane.CLOSED_OPTION:
                return false; // cancel New operation
            }
        }
        return true; // continue because other editor are open on the document.
    }

    public void undoIt() {
        // Cannot undo.
    }
}