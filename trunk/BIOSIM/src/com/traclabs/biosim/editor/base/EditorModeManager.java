package com.traclabs.biosim.editor.base;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeManager;
import org.tigris.gef.presentation.Fig;

import com.traclabs.biosim.editor.graph.ModuleFigNode;

public class EditorModeManager extends ModeManager {

    Logger myLogger = Logger.getLogger(EditorModeManager.class);

    public EditorModeManager(Editor par) {
        super(par);
    }

    public void checkModeTransitions(InputEvent ie) {
        if (!top().canExit() && ie.getID() == MouseEvent.MOUSE_PRESSED) {
            MouseEvent me = (MouseEvent) ie;
            if ((me.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                int x = me.getX(), y = me.getY();
                Fig underMouse = editor.hit(x, y);
                if (underMouse instanceof ModuleFigNode) {
                    Object startPort = ((ModuleFigNode) underMouse).getPort();
                    if (startPort != null) {
                        EditorModeCreateEdge createArc = new EditorModeCreateEdge(
                                editor);

                        push(createArc);
                        createArc.mousePressed(me);
                        return;
                    }
                }
            }
        }
    }
}