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

public class CmdSaveAsVespr extends Cmd {
    protected CmdSaveVespr _cmd = new CmdSaveVespr();

    public CmdSaveAsVespr() {
        super("VesprBase", "SaveAs");
        putValue(Action.SHORT_DESCRIPTION,
                "Saves the active document with a new name");
    }

    public void doIt() {
        VesprEditor ce = (VesprEditor) Globals.curEditor();
        _cmd.saveAs((EditorDocument) ce.document());
    }

    public void undoIt() {
    }
}