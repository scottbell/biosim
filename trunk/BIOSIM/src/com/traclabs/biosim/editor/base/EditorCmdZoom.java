/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Jun 10, 2004
 */
package com.traclabs.biosim.editor.base;

import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;

/**
 * Zoom in and zoom out to predefined scale factors.
 * 
 * @author kkusy
 */
public class EditorCmdZoom extends Cmd {
    public static class ZoomMode {
        private ZoomMode() {
        }
    }

    public static final ZoomMode ZOOM_IN = new ZoomMode();

    public static final ZoomMode ZOOM_OUT = new ZoomMode();

    public static final ZoomMode ZOOM_RESET = new ZoomMode();

    private ZoomMode _mode = ZOOM_RESET;

    private static final double _scales[] = { 0.12, 0.25, 0.50, 0.75, 1.0,
            1.25, 1.5, 2.0, 4.0, 8.0, 16.0 };

    public EditorCmdZoom(ZoomMode mode) {
        super(wordFor(mode));
        _mode = mode;
    }

    private static String wordFor(ZoomMode mode) {
        if (mode == ZOOM_IN) {
            return "ZoomIn";
        }
        if (mode == ZOOM_OUT) {
            return "ZoomOut";
        }
        return "ZoomReset";
    }

    public void doIt() {
        Editor ed = (Editor) Globals.curEditor();
        if (ed == null)
            return;

        if (_mode == ZOOM_IN) {
            zoomIn(ed);
        } else if (_mode == ZOOM_OUT) {
            zoomOut(ed);
        } else if (_mode == ZOOM_RESET) {
            ed.setScale(1.0);
        }
    }

    private void zoomIn(Editor ed) {
        for (int i = 0; i < _scales.length; i++) {
            if (ed.getScale() < _scales[i]) {
                ed.setScale(_scales[i]);
                return;
            }
        }
    }

    private void zoomOut(Editor ed) {
        for (int i = _scales.length - 1; i >= 0; i--) {
            if (ed.getScale() > _scales[i]) {
                ed.setScale(_scales[i]);
                return;
            }
        }
    }

    public void undoIt() {
    }
}