/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.editor.graph;

import java.io.Serializable;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

/*
 * A Terminator Node represents the end of a chain of nodes.
 */

public class TerminatorNode extends EditorNode implements Serializable {
    public TerminatorNode() {
        setText("Terminator");
    }

    public FigNode makePresentation(Layer lay) {
        FigTerminatorNode ftn = new FigTerminatorNode();
        ftn.setOwner(this);
        return ftn;
    }

} /* end class TerminatorNode */
