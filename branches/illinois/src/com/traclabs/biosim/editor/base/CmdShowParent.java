/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.base;

import javax.swing.ImageIcon;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Globals;

/**
 * Displays the graph that is one level up in the tree.
 */
public class CmdShowParent extends Cmd {
    public CmdShowParent() {
        super("EditorBase", "ShowParent");
    }

    public CmdShowParent(ImageIcon icon) {
        super(null, "EditorBase", "ShowParent", icon);
    }

    public void doIt() {
        BiosimEditor editor = (BiosimEditor) Globals.curEditor();
        editor.showParent();
    }

    public void undoIt() {
    }
}