/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Dec 1, 2003
 */
package com.traclabs.biosim.server.editor.presentation;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Vector;

import org.tigris.gef.presentation.Fig;

/**
 * BriGridLayout is like Java's GridLayout except that it will layout figs
 * within a FigContainer.
 * 
 * @author kkusy
 */
public class GEFGridLayout implements GEFLayoutManager {
    private int _rows = 1;

    private int _cols = 0;

    private int _hgap = 0;

    private int _vgap = 0;

    public GEFGridLayout() {
        this(1, 0, 0, 0);
    }

    public GEFGridLayout(int rows, int cols) {
        this(rows, cols, 0, 0);
    }

    public GEFGridLayout(int rows, int cols, int hgap, int vgap) {
        _rows = rows;
        _cols = cols;
        _hgap = hgap;
        _vgap = vgap;
    }

    public void layoutContainer(FigPanel parent) {
        Insets insets = parent.getInsets();
        Vector children = parent.getContents();
        int nfigs = children.size();
        int nrows = _rows;
        int ncols = _cols;

        if (nfigs == 0) {
            return;
        }
        if (nrows > 0) {
            ncols = (nfigs + nrows - 1) / nrows;
        } else {
            nrows = (nfigs + ncols - 1) / ncols;
        }

        int w = parent.getWidth() - (insets.left + insets.right);
        int h = parent.getHeight() - (insets.top + insets.bottom);
        w = (w - (ncols - 1) * _hgap) / ncols;
        h = (h - (nrows - 1) * _vgap) / nrows;

        int x = parent.getX() + insets.left;
        for (int c = 0; c < ncols; c++) {
            int y = parent.getY() + insets.top;
            for (int r = 0; r < nrows; r++) {
                int i = r * ncols + c;
                if (i < nfigs) {
                    Fig fig = (Fig) children.get(i);
                    fig.setBounds(x, y, w, h);
                }
                y += h + _vgap;
            }
            x += w + _hgap;
        }
    }

    public void addLayoutFig(Fig fig, Object constraints) {
    }

    public Dimension minimumLayoutSize(FigPanel parent) {
        Insets insets = parent.getInsets();
        Vector children = parent.getContents();
        int nfigs = children.size();
        int nrows = _rows;
        int ncols = _cols;

        if (nrows > 0) {
            ncols = (nfigs + nrows - 1) / nrows;
        } else {
            nrows = (nfigs + ncols - 1) / ncols;
        }
        int w = 0;
        int h = 0;
        for (int i = 0; i < nfigs; i++) {
            Fig fig = (Fig) children.get(i);
            Dimension d = fig.getMinimumSize();
            if (w < d.width) {
                w = d.width;
            }
            if (h < d.height) {
                h = d.height;
            }
        }
        return new Dimension(insets.left + insets.right + ncols * w
                + (ncols - 1) * _hgap, insets.top + insets.bottom + nrows * h
                + (nrows - 1) * _vgap);
    }

    public Dimension preferredLayoutSize(FigPanel parent) {
        Insets insets = parent.getInsets();
        Vector children = parent.getContents();
        int nfigs = children.size();
        int nrows = _rows;
        int ncols = _cols;

        if (nrows > 0) {
            ncols = (nfigs + nrows - 1) / nrows;
        } else {
            nrows = (nfigs + ncols - 1) / ncols;
        }
        int w = 0;
        int h = 0;

        for (int i = 0; i < nfigs; i++) {
            Fig fig = (Fig) children.get(i);
            Dimension d = fig.getPreferredSize();
            if (w < d.width) {
                w = d.width;
            }
            if (h < d.height) {
                h = d.height;
            }
        }
        return new Dimension(insets.left + insets.right + ncols * w
                + (ncols - 1) * _hgap, insets.top + insets.bottom + nrows * h
                + (nrows - 1) * _vgap);
    }

    public void removeLayoutFig(Fig fig) {
    }

    public int getColumns() {
        return _cols;
    }

    public int getRows() {
        return _rows;
    }

    public int getHGap() {
        return _hgap;
    }

    public int getVGap() {
        return _vgap;
    }
}