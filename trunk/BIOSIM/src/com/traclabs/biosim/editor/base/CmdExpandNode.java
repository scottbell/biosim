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

import javax.swing.ImageIcon;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;
import org.tigris.gef.base.SelectionManager;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.editor.graph.EditorFigNode;

/**
 * Displays the contents of the node that is current selected in the editor.
 * 
 * @author Kevin Kusy
 */
public class CmdExpandNode extends Cmd {
    public CmdExpandNode() {
        super("VesprBase", "ExpandNode");
    }

    public CmdExpandNode(ImageIcon icon) {
        super(null, "VesprBase", "ExpandNode", icon);
    }

    public void doIt() {
        // Find out which items are selected.
        VesprEditor editor = (VesprEditor) Globals.curEditor();
        SelectionManager sm = editor.getSelectionManager();
        Vector selections = sm.getFigs();

        // If only one item is selected and it is a VesprFigNode then
        //    Expand the node.
        if (selections.size() == 1) {
            Fig fig = (Fig) selections.get(0);

            if (fig instanceof EditorFigNode) {
                // Switch out the layer that is being displayed.
                editor.expandNode((EditorFigNode) fig);
            }
        }
    }

    public void undoIt() {
    }
}