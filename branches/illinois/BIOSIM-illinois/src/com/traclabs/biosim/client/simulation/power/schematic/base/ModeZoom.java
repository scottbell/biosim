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
package com.traclabs.biosim.client.simulation.power.schematic.base;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.FigModifyingModeImpl;
import org.tigris.gef.base.Globals;
import org.tigris.gef.util.ResourceLoader;

/**
 * @author kkusy
 */
public class ModeZoom extends FigModifyingModeImpl {
    ////////////////////////////////////////////////////////////////
    // event handlers
    EditorCmdZoom _zoomInCmd;

    EditorCmdZoom _zoomOutCmd;

    public ModeZoom() {
        _zoomInCmd = new EditorCmdZoom(EditorCmdZoom.ZOOM_IN);
        _zoomOutCmd = new EditorCmdZoom(EditorCmdZoom.ZOOM_OUT);
    }

    public void mouseMoved(MouseEvent me) {
        me.consume();
    }

    public void mouseDragged(MouseEvent me) {
        me.consume();
    }

    public void mouseClicked(MouseEvent me) {
        Editor currentEditor = Globals.curEditor();

        if (me.isMetaDown()) {
            // Right mouse button.
            _zoomOutCmd.doIt();
        } else if (!me.isAltDown()) {
            // Left mouse button.
            _zoomInCmd.doIt();
        }

        int x = (int) (me.getX() * currentEditor.getScale());
        int y = (int) (me.getY() * currentEditor.getScale());
        final Rectangle r = new Rectangle(x, y, 1, 1);
        r
                .grow((int) (100 * currentEditor.getScale()), (int) (100 * currentEditor
                        .getScale()));

        currentEditor.getJComponent().scrollRectToVisible(new Rectangle(0, 0, 1, 1));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Editor theEditor = Globals.curEditor();
                JComponent c = theEditor.getJComponent();
                c.scrollRectToVisible(r);
            }
        });

        me.consume();
    }

    public void mousePressed(MouseEvent me) {
        me.consume();
    }

    public void mouseReleased(MouseEvent me) {
        me.consume();
    }

    public void mouseExited(MouseEvent me) {
        me.consume();
        leave();
    }

    public void mouseEntered(MouseEvent me) {
        me.consume();
    }

    /** Returns the cursor that should be shown when this Mode starts. */
    public Cursor getInitialCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        ImageIcon icon = ResourceLoader.lookupIconResource("ZoomCursor");
        if (icon == null) {
            return Cursor.getDefaultCursor();
        }

        return toolkit.createCustomCursor(icon.getImage(), new Point(7, 7),
                "zoom");
    }

}