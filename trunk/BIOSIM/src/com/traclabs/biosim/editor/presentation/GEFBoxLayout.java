/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Dec 3, 2003
 *
 */
package com.traclabs.biosim.editor.presentation;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.Vector;

import org.tigris.gef.presentation.Fig;

/**
 * GefBoxLayout is a simplified version of Java's BoxLayout used to layout figs
 * within a FigContainer.
 * 
 * @author kkusy
 */
public class GEFBoxLayout implements GEFLayoutManager {
    public static final int X_AXIS = 0;

    public static final int Y_AXIS = 1;

    public static final int LEFT = 0;

    public static final int CENTER = 1;

    public static final int RIGHT = 2;

    public static final int TOP = LEFT;

    public static final int BOTTOM = RIGHT;

    private int _axis = Y_AXIS;

    private int _gap = 0;

    private int _alignment = LEFT;

    /**
     * Constructor that specifies which axis should be used to arrange the
     * components, how much spacing should be between the components, and how
     * they should be aligned inside the container.
     */
    public GEFBoxLayout(int axis, int gap, int alignment) {
        _axis = axis;
        _gap = gap;
        _alignment = alignment;
    }

    /**
     * Constructor that specifies which axis should be used to arrange the
     * components.
     * 
     * @param axis
     */
    public GEFBoxLayout(int axis) {
        this(axis, 0, axis == Y_AXIS ? LEFT : TOP);
    }

    /**
     * Default constructor that arranges figs along the y-axis and aligns them
     * on the left side of the container.
     */
    public GEFBoxLayout() {
        this(Y_AXIS, 0, LEFT);
    }

    /** Sets the gap between figs in the layout. */
    public void setGap(int gap) {
        if (gap < 0) {
            gap = 0;
        }
        _gap = gap;
    }

    /** Gets the gap between figs in the layout. */
    public int getGap() {
        return _gap;
    }

    /**
     * Sets the alignment of the figs in the container. If the figs are arranged
     * along the x-axis, alignment should be TOP, CENTER, or BOTTOM. If the figs
     * are arranged along the y-axis, alignment should be LEFT, CENTER, or
     * RIGHT.
     */
    public void setAlignment(int alignment) {
        if (_alignment < LEFT && _alignment > RIGHT) {
            throw new IllegalArgumentException("Bad aligment argument");
        }

        _alignment = alignment;
    }

    /**
     * Returns the alignment of the figs in the container.
     * 
     * @return
     */
    public int getAlignment() {
        return _alignment;
    }

    public void layoutContainer(FigPanel parent) {
        Insets insets = parent.getInsets();
        Vector children = parent.getContents();
        int left = parent.getX() + insets.left;
        int top = parent.getY() + insets.top;
        int bottom = parent.getY() + parent.getHeight() - insets.bottom;
        int right = parent.getX() + parent.getWidth() - insets.right;

        if (_axis == X_AXIS) {
            int cx = left;
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                Fig fig = (Fig) e.nextElement();
                Dimension d = fig.getPreferredSize();

                switch (_alignment) {
                case TOP:
                    fig.setBounds(cx, top, d.width, d.height);
                    break;
                case CENTER:
                    fig.setBounds(cx, top + (bottom - top - d.height) / 2,
                            d.width, d.height);
                    break;
                case BOTTOM:
                    fig.setBounds(cx, bottom - d.height, d.width, d.height);
                    break;
                }

                cx += d.width + _gap;
            }
        } else {
            int cy = top;
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                Fig fig = (Fig) e.nextElement();
                Dimension d = fig.getPreferredSize();

                switch (_alignment) {
                case LEFT:
                    fig.setBounds(left, cy, d.width, d.height);
                    break;
                case CENTER:
                    fig.setBounds(left + (right - left - d.width) / 2, cy,
                            d.width, d.height);
                    break;
                case BOTTOM:
                    fig.setBounds(right - d.width, cy, d.width, d.height);
                    break;
                }

                cy += d.height + _gap;
            }
        }
    }

    public void addLayoutFig(Fig fig, Object constraints) {
    }

    public Dimension minimumLayoutSize(FigPanel parent) {
        Insets insets = parent.getInsets();
        Dimension dim = new Dimension(0, 0);
        Vector children = parent.getContents();
        int nfigs = children.size();

        if (_axis == X_AXIS) {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                Fig fig = (Fig) e.nextElement();
                Dimension d = fig.getMinimumSize();
                dim.height = Math.max(dim.height, d.height);
                dim.width += d.width;
            }
            dim.height += insets.top + insets.bottom;
            dim.width += insets.left + insets.right + (nfigs - 1) * _gap;
        } else {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                Fig fig = (Fig) e.nextElement();
                Dimension d = fig.getMinimumSize();
                dim.height += d.height;
                dim.width = Math.max(dim.width, d.width);
            }
            dim.height += insets.top + insets.bottom + (nfigs - 1) * _gap;
            dim.width += insets.left + insets.right;
        }
        return dim;
    }

    public Dimension preferredLayoutSize(FigPanel parent) {
        Insets insets = parent.getInsets();
        Dimension dim = new Dimension(0, 0);
        Vector children = parent.getContents();
        int nfigs = children.size();

        if (_axis == X_AXIS) {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                Fig fig = (Fig) e.nextElement();
                Dimension d = fig.getPreferredSize();
                dim.height = Math.max(dim.height, d.height);
                dim.width += d.width;
            }
            dim.height += insets.top + insets.bottom;
            dim.width += insets.left + insets.right + (nfigs - 1) * _gap;
        } else {
            Enumeration e = children.elements();
            while (e.hasMoreElements()) {
                Fig fig = (Fig) e.nextElement();
                Dimension d = fig.getPreferredSize();
                dim.height += d.height;
                dim.width = Math.max(dim.width, d.width);
            }
            dim.height += insets.top + insets.bottom + (nfigs - 1) * _gap;
            dim.width += insets.left + insets.right;
        }
        return dim;
    }

    public void removeLayoutFig(Fig fig) {
    }

}