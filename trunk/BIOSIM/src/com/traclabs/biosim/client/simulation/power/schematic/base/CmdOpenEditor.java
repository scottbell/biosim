package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.io.File;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.CmdSave;
import org.tigris.gef.base.Globals;

/**
 * Cmd to Load a previously saved document document. The loaded editor is
 * displayed in a new JGraphFrame.
 * 
 * @see CmdSave
 */

public class CmdOpenEditor extends Cmd {
    protected CmdSaveEditor _cmdSave = new CmdSaveEditor();

    public CmdOpenEditor() {
        super("Open");
        this.putValue(Action.SHORT_DESCRIPTION, "Opens an existing document");
    }

    public void doIt() {
        BiosimEditor ce = (BiosimEditor) Globals.curEditor();
        try {
            Class clazz = ce.document().getClass();
            EditorDocument doc = (EditorDocument) clazz.newInstance();
            File file = openDialog(doc);

            if (file != null) {
                // Prompt to save changes if this is the only editor
                // open on the document and the document has been modified.

                if (_cmdSave.promptSave(ce)) {
                    doc.openDocument(file);
                    ce.document(doc);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ce.findFrame(),
                    "Could not open document!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    protected File openDialog(EditorDocument doc) {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(doc.getFileFilter());
        fc.setCurrentDirectory(new File(Globals.getLastDirectory()));
        int returnVal = fc.showOpenDialog(Globals.curEditor().findFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Globals.setLastDirectory(fc.getCurrentDirectory().getPath());
            return fc.getSelectedFile();
        }

        return null;
    }

    public void undoIt() {
        System.out.println("Undo does not make sense for CmdOpen");
    }
} /* end class CmdOpenEditor */
