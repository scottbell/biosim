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
 *
 */
package com.traclabs.biosim.server.editor.presentation;

import java.awt.Dimension;
import java.awt.Insets;

import org.tigris.gef.presentation.Fig;

/**
 * BorderLayout is like Java's BorderLayout except that it will layout figs
 * within a container rather than components.
 * 
 * @author kkusy
 */
public class GEFBorderLayout implements GEFLayoutManager {
    public static class Location {
        private Location() {
        }
    }

    public static final Location NORTH = new Location();

    public static final Location SOUTH = new Location();

    public static final Location EAST = new Location();

    public static final Location WEST = new Location();

    public static final Location CENTER = new Location();

    private Fig _north;

    private Fig _south;

    private Fig _east;

    private Fig _west;

    private Fig _center;

    private int _hgap = 0;

    private int _vgap = 0;

    public GEFBorderLayout() {
        this(0, 0);
    }

    public GEFBorderLayout(int hgap, int vgap) {
        _hgap = hgap;
        _vgap = vgap;
    }

    public void layoutContainer(FigPanel parent) {
        Insets insets = parent.getInsets();
        int top = parent.getY() + insets.top;
        int bottom = parent.getY() + parent.getHeight() - insets.bottom;
        int left = parent.getX() + insets.left;
        int right = parent.getX() + parent.getWidth() - insets.right;

        if (_north != null) {
            Dimension d = _north.getPreferredSize();
            _north.setBounds(left, top, right - left, d.height);
            top += d.height + _vgap;
        }
        if (_south != null) {
            Dimension d = _south.getPreferredSize();
            _south.setBounds(left, bottom - d.height, right - left, d.height);
            bottom -= d.height + _vgap;
        }
        if (_east != null) {
            Dimension d = _east.getPreferredSize();
            _east.setBounds(right - d.width, top, d.width, bottom - top);
            right -= d.width + _hgap;
        }
        if (_west != null) {
            Dimension d = _west.getPreferredSize();
            _west.setBounds(left, top, d.width, bottom - top);
            left += d.width + _hgap;
        }
        if (_center != null) {
            _center.setBounds(left, top, right - left, bottom - top);
        }
    }

    public int getHgap() {
        return _hgap;
    }

    public int getVgap() {
        return _vgap;
    }

    public void addLayoutFig(Fig fig, Object constraints) {
        if (constraints == null) {
            _center = fig;
            return;
        }

        if (!(constraints instanceof GEFBorderLayout.Location)) {
            throw new IllegalArgumentException();
        }
        Location loc = (Location) constraints;

        if (loc == CENTER) {
            _center = fig;
        } else if (loc == NORTH) {
            _north = fig;
        } else if (loc == WEST) {
            _west = fig;
        } else if (loc == SOUTH) {
            _south = fig;
        } else {
            _east = fig;
        }
    }

    public Dimension minimumLayoutSize(FigPanel parent) {
        int w = 0;
        int h = 0;

        if (_west != null) {
            Dimension d = _west.getMinimumSize();
            h = d.height;
            w = d.width + _hgap;
        }

        if (_center != null) {
            Dimension d = _center.getMinimumSize();
            h = Math.max(h, d.height);
            w += d.width;
        }

        if (_east != null) {
            Dimension d = _east.getMinimumSize();
            h = Math.max(h, d.height);
            w += d.width + _hgap;
        }

        if (_north != null) {
            Dimension d = _north.getMinimumSize();
            h += d.height + _vgap;
            w = Math.max(w, d.width);
        }

        if (_south != null) {
            Dimension d = _south.getMinimumSize();
            h += d.height + _vgap;
            w = Math.max(w, d.width);
        }

        Insets insets = parent.getInsets();
        w += insets.left + insets.right;
        h += insets.top + insets.bottom;

        return new Dimension(w, h);
    }

    public Dimension preferredLayoutSize(FigPanel parent) {
        int w = 0, h = 0;

        if (_west != null) {
            Dimension d = _west.getPreferredSize();
            h = d.height;
            w = d.width + _hgap;
        }

        if (_center != null) {
            Dimension d = _center.getPreferredSize();
            h = Math.max(h, d.height);
            w += d.width;
        }

        if (_east != null) {
            Dimension d = _east.getPreferredSize();
            h = Math.max(h, d.height);
            w += d.width + _hgap;
        }

        if (_north != null) {
            Dimension d = _north.getPreferredSize();
            h += d.height + _vgap;
            w = Math.max(w, d.width);
        }

        if (_south != null) {
            Dimension d = _south.getPreferredSize();
            h += d.height + _vgap;
            w = Math.max(w, d.width);
        }

        Insets insets = parent.getInsets();
        w += insets.left + insets.right;
        h += insets.top + insets.bottom;

        return new Dimension(w, h);
    }

    public void removeLayoutFig(Fig fig) {
        if (fig == _center) {
            _center = null;
        } else if (fig == _north) {
            _north = null;
        } else if (fig == _south) {
            _south = null;
        } else if (fig == _east) {
            _east = null;
        } else if (fig == _west) {
            _west = null;
        }
    }
}