/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.base;

import javax.swing.Action;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;

public class CmdNewVespr extends Cmd {
    protected CmdSaveVespr _cmd = new CmdSaveVespr();

    public CmdNewVespr() {
        super("New");
        this.putValue(Action.SHORT_DESCRIPTION, "Creates a new document");
    }

    public void doIt() {
        // TODO: Prompt to save changes if this is the only editor
        // open on this document and the document has been modified.

        VesprEditor ce = (VesprEditor) Globals.curEditor();

        if (_cmd.promptSave(ce)) {
            // Create the new document.
            try {
                Class clazz = ce.document().getClass();
                VesprDocument doc = (VesprDocument) clazz.newInstance();
                ce.document(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void undoIt() {
    }
}