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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigGroup;
import org.tigris.gef.presentation.FigRect;

/**
 * FigContainer is a group of figs whose layout is determined by a layout
 * manager.
 * 
 * @author kkusy
 */
public class FigPanel extends FigGroup {
    protected GEFLayoutManager _layoutManager = new GEFBorderLayout();

    protected Insets _insets = new Insets(0, 0, 0, 0);

    protected Vector _contents = new Vector();

    protected Fig _bgFig;

    /** Empty constructor */
    public FigPanel() {
        this(new GEFBorderLayout());
    }

    /**
     * Constructor that accepts the layout manager to be used to layout the
     * contained figs.
     */
    public FigPanel(GEFLayoutManager layoutManager) {
        _layoutManager = layoutManager;
        _bgFig = createBgFig();
        super.addFig(_bgFig);
    }

    protected Fig createBgFig() {
        return new FigRect(0, 0, 1, 1, Color.BLACK, Color.WHITE);
    }

    /** Sets the layout manager. */
    public void setLayout(GEFLayoutManager layoutManager) {
        removeAll();
        _layoutManager = layoutManager;

    }

    /** Layout the container. */
    public void doLayout() {
        Dimension dim = getPreferedSize();
        setBounds(_x, _y, dim.width, dim.height);
    }

    /**
     * Adds a fig to this container.
     */
    public void addFig(Fig fig) {
        this.addFig(fig, null, _contents.size());
    }

    /**
     * Adds a fig to this container with the specified layout constraints.
     */
    public void addFig(Fig fig, Object constraints) {
        this.addFig(fig, constraints, _contents.size());
    }

    /**
     * Adds a fig to this container at the specified index.
     */
    public void addFig(Fig fig, int index) {
        this.addFig(fig, null, index);
    }

    /**
     * Adds a fig to this container at index with the specified layout
     * constraints.
     */
    public void addFig(Fig fig, Object constraints, int index) {
        super.addFig(fig);
        _contents.add(index, fig);
        _layoutManager.addLayoutFig(fig, constraints);
    }

    /**
     * Removes the specified fig from this container.
     */
    public void removeFig(Fig fig) {
        if (_contents.contains(fig)) {
            super.removeFig(fig);
            _contents.remove(fig);
            _layoutManager.removeLayoutFig(fig);
        }
    }

    /**
     * Removes all figs from this container.
     */
    public void removeAll() {
        Enumeration e = _contents.elements();
        while (e.hasMoreElements()) {
            Fig fig = (Fig) e.nextElement();
            super.removeFig(fig);
            _layoutManager.removeLayoutFig(fig);
        }
        _contents.removeAllElements();
    }

    public Dimension getMinimumSize() {
        return _layoutManager.minimumLayoutSize(this);
    }

    public Dimension getPreferedSize() {
        return _layoutManager.preferredLayoutSize(this);
    }

    public void setBounds(int x, int y, int w, int h) {
        Rectangle oldBounds = getBounds();
        _x = x;
        _y = y;
        _w = w;
        _h = h;
        _bgFig.setBounds(x, y, w, h);
        _layoutManager.layoutContainer(this);
        firePropChange("bounds", oldBounds, getBounds());
    }

    public Insets getInsets() {
        return _insets;
    }

    public void setInsets(Insets insets) {
        _insets = insets;
    }

    /**
     * Returns a list of figs that should be managed by the layout manager.
     */
    public Vector getContents() {
        return _contents;
    }

    public void setLineColor(Color col) {
        _bgFig.setLineColor(col);
        /*
         * Enumeration enum = _contents.elements(); while
         * (enum.hasMoreElements()) { Fig fig = (Fig)enum.nextElement(); if (fig
         * instanceof FigText) { FigText text = (FigText)fig;
         * text.setTextColor(col); } }
         */
    }

    public void setFillColor(Color col) {
        _bgFig.setFillColor(col);
        /*
         * Enumeration enum = _contents.elements(); while
         * (enum.hasMoreElements()) { Fig fig = (Fig)enum.nextElement();
         * fig.setLineColor(col); fig.setFillColor(col); if (fig instanceof
         * FigText) { FigText text = (FigText)fig; text.setTextFillColor(col); } }
         */
    }

    public void setOpaque(boolean b) {
        _bgFig.setVisible(b);
    }

    public boolean getOpaque() {
        return _bgFig.isVisible();
    }
}