/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.graph;

import java.awt.Color;

import org.tigris.gef.presentation.FigText;

/*
 * VesprFigText tries to correct a bug in the calcBounds() method of FigText.
 * FigText objects can be visually edited by double clicking them. The problem
 * was that the FigText was being shifted to the left when the text length was
 * decreased while editing. This occured when the backspace or delete keys were
 * pressed.
 * 
 * @author Kevin Kusy
 */
public class VesprFigText extends FigText {

    /**
     * Construct a new VesprFigText with the given position, size, color,
     * string, font, and font size. Text string is initially empty and centered.
     */
    public VesprFigText(int x, int y, int w, int h, Color textColor,
            String familyName, int fontSize) {
        super(x, y, w, h, textColor, familyName, fontSize);
        _expandOnly = false;
    }

    /**
     * Construct a new VesprFigText with the given position, size, and
     * attributes.
     */
    public VesprFigText(int x, int y, int w, int h) {
        super(x, y, w, h, Color.blue, "TimesRoman", 12);
        _expandOnly = false;
    }

}