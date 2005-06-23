/**
 * EditorSelectionManager marks the EDITOR document dirty whenever figs are moved
 * or resized.
 * 
 * @author Kevin Kusy
 */
package com.traclabs.biosim.editor.base;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JComponent;

import org.tigris.gef.base.Selection;
import org.tigris.gef.base.SelectionManager;
import org.tigris.gef.presentation.Handle;

public class EditorSelectionManager extends SelectionManager {

    public EditorSelectionManager(BiosimEditor editor) {
        super(editor);
    }
    
    public void drag(int dx, int dy) {
        // Adjust dx and dy to keep the figs within bounds.
        JComponent drawingPanel = _editor.getJComponent();
        Dimension dim = new Dimension(drawingPanel.getSize());
        dim.width /= _editor.getScale();
        dim.height /= _editor.getScale();

        int w = (int) dim.getWidth();
        int h = (int) dim.getHeight();

        Rectangle rect = getContentBounds();
        int left = (int) rect.getX();
        int right = (int) (rect.getX() + rect.getWidth());
        int top = (int) rect.getY();
        int bottom = (int) (rect.getY() + rect.getHeight());

        if (left + dx <= 0) {
            dx = -left;
        } else if (right + dx >= w) {
            dx = w - right;
        }

        if (top + dy <= 0) {
            dy = -top;
        } else if (bottom + dy >= h) {
            dy = h - bottom;
        }

        super.drag(dx, dy);
        modifyDocument();
    }

    public void dragHandle(int mx, int my, int an_x, int an_y, Handle h) {
        JComponent drawingPanel = _editor.getJComponent();
        Dimension dim = new Dimension(drawingPanel.getSize());
        dim.width /= _editor.getScale();
        dim.height /= _editor.getScale();
        mx = Math.min(dim.width, Math.max(0, mx));
        my = Math.min(dim.height, Math.max(0, my));

        super.dragHandle(mx, my, an_x, an_y, h);
        modifyDocument();
    }

    private void modifyDocument() {
        Object doc = _editor.document();
        if (doc instanceof EditorDocument) {
            ((EditorDocument) doc).setModified(true);
        }
    }

    /** When a multiple selection are deleted, each selection is deleted */
    public void dispose() {
        Enumeration ss = ((Vector) _selections.clone()).elements();
        while (ss.hasMoreElements()) {
            Selection s = (Selection) ss.nextElement();
            s.dispose();
        }
    }
}