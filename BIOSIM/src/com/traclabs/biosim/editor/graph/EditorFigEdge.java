package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.tigris.gef.base.PathConvPercent;
import org.tigris.gef.presentation.ArrowHeadTriangle;
import org.tigris.gef.presentation.FigEdgeLine;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigText;

public class EditorFigEdge extends FigEdgeLine implements MouseListener {

    FigText mid;

    public EditorFigEdge() {
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