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
import org.tigris.gef.base.CmdDeleteFromModel;

public class EditorCmdCut extends Cmd {
    EditorCmdCopy _copyCmd;

    CmdDeleteFromModel _deleteCmd;

    public EditorCmdCut() {
        super("Cut");
        _copyCmd = new EditorCmdCopy();
        _deleteCmd = new CmdDeleteFromModel();
        putValue(Action.SHORT_DESCRIPTION,
                "Cuts the selection and puts it on the clipboard");
    }

    public void doIt() {
        _copyCmd.doIt();
        _deleteCmd.doIt();
    }

    public void undoIt() {
    }
}