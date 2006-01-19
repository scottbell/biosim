package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.ModeManager;
import org.tigris.gef.presentation.Fig;

public class PowerModeManager extends ModeManager {

    Logger myLogger = Logger.getLogger(PowerModeManager.class);

    public PowerModeManager(Editor par) {
        super(par);
    }

    public void checkModeTransitions(InputEvent ie) {
        if (!top().canExit() && ie.getID() == MouseEvent.MOUSE_PRESSED) {
            MouseEvent me = (MouseEvent) ie;
            if ((me.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                int x = me.getX(), y = me.getY();
                Fig underMouse = editor.hit(x, y);
            }
        }
    }
}