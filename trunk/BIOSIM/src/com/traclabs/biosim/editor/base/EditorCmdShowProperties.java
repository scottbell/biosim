/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.base;

import java.util.Vector;

import javax.swing.Action;
import javax.swing.ImageIcon;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.editor.graph.EditorFigNode;

/**
 * Show the properties for the selected fig.
 */
public class EditorCmdShowProperties extends Cmd {

    public EditorCmdShowProperties() {
        super("VesprBase", "ShowProperties");
        putValue(Action.SHORT_DESCRIPTION,
                "Shows properties for the selected item");
    }

    public EditorCmdShowProperties(ImageIcon image) {
        super(null, "VesprBase", "ShowProperties", image);
    }

    public void doIt() {

        Editor editor = Globals.curEditor();

        Vector figs = editor.getSelectionManager().getFigs();
        if (figs.size() == 1) {
            Fig fig = (Fig) figs.firstElement();
            if (fig instanceof EditorFigNode) {
                ((EditorFigNode) fig).showProperties(editor.findFrame());
            }
        }
    }

    public void undoIt() {
    }
}