/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.graph;

import java.awt.Color;
import java.util.StringTokenizer;

import org.tigris.gef.graph.GraphEvent;
import org.tigris.gef.graph.GraphListener;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigRect;

public class FigRequiredNode extends FigLabelNode {

    public FigRequiredNode() {
        super();
        setFillColor(Color.CYAN);
        setLineColor(Color.BLUE);
        setShowShadow(false);
        setShadowOffset(5);
    }

    public String getTag() {
        return "Required";
    }

    protected Fig createShadowFig() {
        Fig fig = new FigRect(2, 2, 1, 1, Color.BLUE, Color.WHITE);
        return fig;
    }

    // Unsure if the following works.
    // public void setWidth(int width) { obj1.setWidth(width); }
    // public void setHeight(int height) { obj1.setHeight(height); }

    public String getPrivateData() {
        return "text=\"" + _label.getText() + "\"";
    }

    public void setOwner(Object own) {
        super.setOwner(own);
        if (!(own instanceof RequiredNode))
            return;
        RequiredNode node = (RequiredNode) own;
        // _label.setText(""+ node.getName());

        node.getNestedModel().addGraphEventListener(new GraphListener() {
            public void nodeAdded(GraphEvent e) {
                graphChanged(e);
            }

            public void nodeRemoved(GraphEvent e) {
                graphChanged(e);
            }

            public void graphChanged(GraphEvent e) {
                GraphModel model = (GraphModel) e.getSource();
                FigRequiredNode.this.setShowShadow(model.getNodes().size() > 0);
            }

            public void edgeRemoved(GraphEvent e) {
            }

            public void edgeAdded(GraphEvent e) {
            }
        });
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
} /* end class FigRequiredNode */
