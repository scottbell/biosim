/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.graph;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.StringTokenizer;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigGroup;
import org.tigris.gef.presentation.FigRect;

public class FigTerminatorNode extends FigLabelNode {

    public FigTerminatorNode() {
        super();
        setFillColor(Color.CYAN);
        setLineColor(Color.BLUE);
        this.setShowShadow(false);
    }

    protected Fig createBgFig() {
        return new FigBackground();
    }

    public String getTag() {
        return "Terminator";
    }

    public String getPrivateData() {
        return null; //"text=\"" + _label.getText() + "\"";
    }

    public void setOwner(Object own) {
        super.setOwner(own);
        if (!(own instanceof TerminatorNode))
            return;

        TerminatorNode node = (TerminatorNode) own;
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

    private static class FigBackground extends FigGroup {
        private FigRect obj1;

        private FigRect obj2;

        public FigBackground() {
            obj1 = new FigRect(0, 0, 75, 50, Color.blue, Color.cyan);
            obj2 = new FigRect(1, 1, 73, 48, Color.blue, Color.cyan);

            addFig(obj1);
            addFig(obj2);
        }

        public void setBounds(int x, int y, int w, int h) {
            Rectangle oldBounds = getBounds();
            obj1.setBounds(x, y, w, h);
            obj2.setBounds(x + 1, y + 1, w - 2, h - 2);

            _x = x;
            _y = y;
            _w = w;
            _h = h;
            firePropChange("bounds", oldBounds, getBounds());
        }
    }
} /* end class FigTerminatorNode */
