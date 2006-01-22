package com.traclabs.biosim.client.simulation.power.schematic.graph;

import java.awt.Dimension;
import java.awt.Point;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;

public abstract class FigPassiveNode extends FigModuleLabelNode {
   public FigPassiveNode() {
        super();
        setShowShadow(false);
    }

    protected Fig createBgFig() {
        return new FigCircle(0, 0, 40, 30);
    }

    public Point connectionPoint(Point anotherPt) {
        return connectionPoint((FigCircle) _bgFig, anotherPt);
    }

    public Dimension getMinimumSize() {
        Dimension dim = myNameLabel.getSize();

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
