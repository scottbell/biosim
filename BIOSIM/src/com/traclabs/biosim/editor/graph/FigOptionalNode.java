/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.graph;

//package org.tigris.gef.demo;

import java.awt.Color;
import java.util.StringTokenizer;

import org.tigris.gef.graph.GraphEvent;
import org.tigris.gef.graph.GraphListener;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigRect;

public class FigOptionalNode extends FigLabelNode {

    public FigOptionalNode() {
        super();
        setLineColor(Color.BLUE);
        setFillColor(new Color(87, 171, 255));
        setShowShadow(false);
        setShadowOffset(5);
    }

    protected Fig createShadowFig() {
        Fig fig = new FigRect(2, 2, 1, 1, Color.BLUE, Color.WHITE);
        return fig;
    }

    public String getTag() {
        return "Optional";
    }

    public String getPrivateData() {
        return null; //"text=\"" + obj7.getText() + "\"";
    }

    public void setOwner(Object own) {
        if (!(own instanceof OptionalNode))
            return;

        super.setOwner(own);
        OptionalNode node = (OptionalNode) own;
        // obj7.setText(""+ node.getName());

        node.getNestedModel().addGraphEventListener(new GraphListener() {
            public void nodeAdded(GraphEvent e) {
                graphChanged(e);
            }

            public void nodeRemoved(GraphEvent e) {
                graphChanged(e);
            }

            public void graphChanged(GraphEvent e) {
                GraphModel model = (GraphModel) e.getSource();
                FigOptionalNode.this.setShowShadow(model.getNodes().size() > 0);
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
} /* end class FigOptionalNode */
