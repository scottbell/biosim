/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.server.editor.graph;

import java.awt.Color;
import java.awt.Rectangle;

import org.tigris.gef.presentation.FigText;

/**
 * FigLabel represents a label for VesprFigNode objects. The label is centered
 * both horizontally and vertically within its parent node. The label is also
 * editable.
 * 
 * @author Kevin Kusy
 */
public class FigLabel extends VesprFigText {

    /**
     * Constructs a new FigLabel with the given position, size, color, string,
     * font, and font size. Text string is initially empty and centered.
     */
    public FigLabel(int x, int y, int w, int h, Color textColor,
            String familyName, int fontSize) {
        super(x, y, w, h, textColor, familyName, fontSize);

        setExpandOnly(false);
        setFilled(false);
        setTextFilled(false);
        setLineWidth(0);
        setJustification(FigText.JUSTIFY_CENTER);
        setEditable(true);
    }

    /**
     * Constructs a new FigText with the given position, size, and attributes.
     */
    public FigLabel(int x, int y, int w, int h) {
        this(x, y, w, h, Color.blue, "TimesRoman", 12);
    }

    /**
     * Computes the overall width and height of the FigLabel object and then
     * adjusts the label position as to center it in the node.
     */
    public void calcBounds() {
        super.calcBounds();

        // calculate x, y to center in the parent node.
        if (_group != null && _group instanceof FigLabelNode) {
            VesprFigNode node = (VesprFigNode) _group;
            Rectangle rect = node.getHandleBox();
            int x = (int) rect.getX();
            int y = (int) rect.getY();
            int w = (int) rect.getWidth();
            int h = (int) rect.getHeight();
            _x = x + w / 2 - _w / 2;
            _y = y + h / 2 - _h / 2;
        }
    }

    public void setText(String t) {
        super.setText(t);
    }
}