/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.graph;

import java.io.Serializable;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.presentation.FigNode;

/**
 * An example subclass of NetNode for use in the BasicApplication application.
 */
public class OptionalNode extends EditorNode implements Serializable {
    public OptionalNode() {
        setText("Optional");
    }

    public FigNode makePresentation(Layer lay) {
        FigOptionalNode fon = new FigOptionalNode();
        fon.setOwner(this);
        return fon;
    }

    protected MutableGraphModel createNestedGraphModel() {
        return new EditorGraphModel();
    }
} /* end class OptionalNode */
