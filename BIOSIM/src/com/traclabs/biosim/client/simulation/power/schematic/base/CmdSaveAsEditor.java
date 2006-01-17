/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.client.simulation.power.schematic.base;

import javax.swing.Action;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;

public class CmdSaveAsEditor extends Cmd {
    protected CmdSaveEditor _cmd = new CmdSaveEditor();

    public CmdSaveAsEditor() {
        super("EditorBase", "SaveAs");
        putValue(Action.SHORT_DESCRIPTION,
                "Saves the active document with a new name");
    }

    public void doIt() {
        BiosimEditor ce = (BiosimEditor) Globals.curEditor();
        _cmd.saveAs((EditorDocument) ce.document());
    }

    public void undoIt() {
    }
}