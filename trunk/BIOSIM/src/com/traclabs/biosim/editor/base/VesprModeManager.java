/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.base;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeCreateEdge;
import org.tigris.gef.base.ModeManager;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.server.editor.graph.VesprFigNode;

public class VesprModeManager extends ModeManager {

    public VesprModeManager(Editor par) {
        super(par);
    }

    public void checkModeTransitions(InputEvent ie) {

        if (!top().canExit() && ie.getID() == MouseEvent.MOUSE_PRESSED) {
            MouseEvent me = (MouseEvent) ie;
            if ((me.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                int x = me.getX(), y = me.getY();
                Fig underMouse = editor.hit(x, y);
                if (underMouse instanceof VesprFigNode) {
                    Object startPort = ((VesprFigNode) underMouse).getPort();

                    if (startPort != null) {
                        ModeCreateEdge createArc = new ModeCreateEdge(editor);

                        push(createArc);
                        createArc.mousePressed(me);
                        return;
                    }
                }
            }
        }
    }
}