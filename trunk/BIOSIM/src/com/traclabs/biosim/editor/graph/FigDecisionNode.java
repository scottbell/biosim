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
import java.awt.Polygon;
import java.util.StringTokenizer;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;

public class FigDecisionNode extends FigLabelNode {

    public FigDecisionNode() {
        super();
        setFillColor(Color.CYAN);
        setLineColor(Color.BLUE);
        setShowShadow(false);
    }

    protected Fig createBgFig() {
        return new FigBackground();
    }

    public String getTag() {
        return "Decision";
    }

    public String getPrivateData() {
        return null; //"text=\"" + _label.getText() + "\"";
    }

    public void setOwner(Object own) {
        super.setOwner(own);
        if (!(own instanceof DecisionNode))
            return;
        DecisionNode node = (DecisionNode) own;
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

    public Dimension getMinimumSize() {
        Dimension dim = _label.getSize();
        int w = Math.max((int) (3.0 * (dim.getWidth() + 4) / 2.0), 75);
        int h = Math.max((int) dim.getHeight() + 10, 50);
        return new Dimension(w, h);
    }

    public Point connectionPoint(Point anotherPt) {
        return connectionPoint((FigPoly) _bgFig, anotherPt);
    }

    private static class FigBackground extends FigPoly {
        public FigBackground() {
            super(Color.blue, Color.cyan);
            addPoint(0, 25);
            addPoint(12, 0);
            addPoint(62, 0);
            addPoint(75, 25);
            addPoint(62, 50);
            addPoint(12, 50);
            addPoint(0, 25);
        }

        public void setBounds(int x, int y, int w, int h) {
            Point center = new Point(x + w / 2, y + h / 2);

            // Resize the polygon.
            int[] xpoints = new int[7];
            xpoints[0] = xpoints[6] = x;
            xpoints[1] = xpoints[5] = (int) (x + 0.16 * w);
            xpoints[2] = xpoints[4] = (int) (x + 0.84 * w);
            xpoints[3] = x + w;

            int[] ypoints = new int[7];
            ypoints[0] = ypoints[3] = ypoints[6] = center.y;
            ypoints[1] = ypoints[2] = y;
            ypoints[4] = ypoints[5] = y + h;

            this.setPolygon(new Polygon(xpoints, ypoints, 7));
        }
    }

} /* end class FigDecisionNode */
