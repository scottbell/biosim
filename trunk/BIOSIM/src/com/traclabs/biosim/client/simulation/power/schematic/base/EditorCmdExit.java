/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.client.simulation.power.schematic.base;

import org.tigris.gef.base.Cmd;

import com.traclabs.biosim.client.simulation.power.schematic.presentation.EditorFrame;

public class EditorCmdExit extends Cmd {

    EditorFrame _frame;

    private CmdSaveEditor _cmd = new CmdSaveEditor();

    public EditorCmdExit(EditorFrame frame) {
        super("EditorBase", "Exit");
        _frame = frame;
    }

    public void doIt() {
        if (_frame != null) {
            BiosimEditor ed = (BiosimEditor) _frame.getEditor();
            if (_cmd.promptSave(ed)) {
                _frame.exit();
            }
        }
    }

    public void undoIt() {
        System.out.println("Undo does not make sense for CmdExit");
    }
} /* end class CmdExit */
