/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
package com.traclabs.biosim.editor.graph;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdge;

/**
 * A sample NetEdge subclass for use in the demos. This edge is drawn with an
 * arrowhead.
 */

public class EditorEdge extends NetEdge {
    /** Construct a new SampleEdge. */
    public EditorEdge() {
    } /* needs-more-work */

    public String getId() {
        return toString();
    }

    public FigEdge makePresentation(Layer lay) {
        return new EditorFigEdge();
    }

} /* end class VesprEdge */
