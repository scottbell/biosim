/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Jun 9, 2004
 */
package com.traclabs.biosim.editor.base;

import java.awt.Frame;
import java.awt.Rectangle;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;

import com.traclabs.biosim.editor.ui.ZoomDialog;

/**
 * Displays the Zoom dialog which allows the user to change the magnification on
 * the current graph.
 * 
 * @author kkusy
 */
public class CmdZoomTo extends Cmd {
    public CmdZoomTo() {
        super("VesprBase", "ZoomTo");
    }

    public void doIt() {
        Editor ed = (Editor) Globals.curEditor();
        if (ed == null)
            return;

        Frame frame = ed.findFrame();

        ZoomDialog dlg = new ZoomDialog(frame);
        dlg.setMagnification((int) (ed.getScale() * 100));
        if (dlg.display() == ZoomDialog.OK) {
            int magnification = dlg.getMagnification();
            ed.setScale(((double) magnification) / 100);
        }
        ed.getJComponent().scrollRectToVisible(new Rectangle(0, 0, 1, 1));
    }

    public void undoIt() {
        // TODO Auto-generated method stub

    }

}