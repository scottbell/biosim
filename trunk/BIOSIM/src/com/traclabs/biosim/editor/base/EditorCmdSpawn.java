/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.base;

import java.awt.Dimension;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;

import com.traclabs.biosim.editor.presentation.EditorFrame;

/**
 * Cmd to open a new editor on the same document as in the current editor. Works
 * by making a new VesprFrame with a clone of the current editor. The argument
 * "dimension" may be set to th desired size of the new window.
 * 
 * @author Kevin Kusy
 */

public class EditorCmdSpawn extends Cmd {

    public EditorCmdSpawn() {
        super("SpawnEditor");
    }

    // doIt must create a VesprFrame instead of a JGraphFrame.
    public void doIt() {
        VesprEditor ce = (VesprEditor) Globals.curEditor();
        EditorFrame frame = (EditorFrame) ce.findFrame();

        if (frame != null) {
            EditorFrame copy = (EditorFrame) frame.clone();

            Object d = getArg("dimension", new Dimension(640, 480));
            if (d instanceof Dimension)
                copy.setSize((Dimension) d);

            copy.setLocation(frame.getX() + 20, frame.getY() + 20);
            copy.setVisible(true);
        }
    }

    public void undoIt() {
        System.out.println("Cannot undo CmdSpawn");
    }
} /* end class CmdSpawn */
