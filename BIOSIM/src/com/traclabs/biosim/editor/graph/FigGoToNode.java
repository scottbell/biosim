/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.StringTokenizer;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;

public class FigGoToNode extends FigLabelNode {

    public FigGoToNode() {
        super();

        setFillColor(Color.CYAN);
        setLineColor(Color.BLUE);
        setShowShadow(false);
    }

    protected Fig createBgFig() {
        return new FigCircle(0, 0, 75, 50, Color.blue, Color.cyan);
    }

    public String getTag() {
        return "Goto";
    }

    public String getPrivateData() {
        return null; //"text=\"" + _label.getText() + "\"";
    }

    public void setOwner(Object own) {
        super.setOwner(own);
        if (!(own instanceof GoToNode))
            return;
        GoToNode node = (GoToNode) own;
        // _label.setText(""+ node.getName());
    }

    public void setPrivateData(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "=\"' ");

        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken();
            if (tok.equals("text")) {
                String s = tokenizer.nextToken();
                _label.setText(s);
            } else {
                /* Unknown value */
            }
        }
    }

    public Point connectionPoint(Point anotherPt) {
        return connectionPoint((FigCircle) _bgFig, anotherPt);
    }

    public Dimension getMinimumSize() {
        Dimension dim = _label.getSize();

        double x = dim.getWidth() / 2.0;
        double y = dim.getHeight() / 2.0;
        double c = x;
        double a = (Math.sqrt((x + c) * (x + c) + y * y) + Math.sqrt((x - c)
                * (x - c) + y * y)) / 2.0;
        double b = Math.sqrt(a * a - c * c);
        int w = Math.max((int) (2 * a) + 6, 75);
        int h = Math.max((int) (2 * b) + 6, 50);
        return new Dimension(w, h);
    }
} /* end class FigGoToNode */
