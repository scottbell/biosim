/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeLine;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigText;

public class VesprFigEdge extends FigEdgeLine implements MouseListener {

    FigText mid;

    public VesprFigEdge() {
        super();

        setDestArrowHead(new ArrowHeadTriangle());
        mid = new FigText(10, 30, 90, 20);
        mid.setText("");
        mid.setTextColor(Color.black);
        mid.setTextFilled(false);
        mid.setFilled(false);
        mid.setLineWidth(0);
        addPathItem(mid, new PathConvPercent(this, 50, 10));

        _useNearest = true;
    }

    public void setBetweenNearestPoints(boolean un) {
    }

    public String getText() {
        return mid.getText();
    }

    public void setText(String text) {
        mid.setText(text);
    }

    /** Compute the shape of the line that presents an Edge. */
    public void computeRoute() {
        Point srcPt = getSourcePortFig().center();
        Point dstPt = getDestPortFig().center();

        srcPt = _sourceFigNode.connectionPoint(dstPt);
        dstPt = _destFigNode.connectionPoint(srcPt);

        ((FigLine) _fig).setShape(srcPt, dstPt);
        calcBounds();
    }

    /////////////////////////////////////////////////////////////////
    // event handlers: MouseListener implementation
    public void mouseClicked(MouseEvent me) {
        mid.mouseClicked(me);
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
}