/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.base;

import org.tigris.gef.base.Cmd;

import com.traclabs.biosim.editor.presentation.EditorFrame;

public class EditorCmdExit extends Cmd {

    EditorFrame _frame;

    private CmdSaveVespr _cmd = new CmdSaveVespr();

    public EditorCmdExit(EditorFrame frame) {
        super("VesprBase", "Exit");
        _frame = frame;
    }

    public void doIt() {
        if (_frame != null) {
            VesprEditor ed = (VesprEditor) _frame.getGraph().getEditor();
            EditorDocument doc = (EditorDocument) ed.document();
            if (_cmd.promptSave(ed)) {
                _frame.exit();
            }
        }
    }

    public void undoIt() {
        System.out.println("Undo does not make sense for CmdExit");
    }
} /* end class CmdExit */
