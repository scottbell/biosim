/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved. U.S. Government Rights - Commercial software. Government
 * users are subject to S&K Technologies, Inc, standard license agreement and
 * applicable provisions of the FAR and its supplements. Use is subject to
 * license terms.
 */
package com.traclabs.biosim.server.editor.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.graph.presentation.NetPort;

public class VesprPort extends NetPort implements Serializable {

    /** Construct a new DecisionNodePort as a port of the given NetNode. */
    public VesprPort(NetNode parent) {
        super(parent);
    }

    protected Class defaultEdgeClass(NetPort otherPort) {
        return VesprEdge.class;
    }

    public void printEdges() {
        Vector v = getEdges();
        System.out.println("The edges for decision note: " + v.size());
    }

    /**
     * Remove this port from the underlying connected graph model and dispose
     * all arcs connected to it.
     */
    public void dispose() {
        ArrayList edgeClone = new ArrayList(_edges);
        int edgeCount = edgeClone.size();
        for (int edgeIndex = 0; edgeIndex < edgeCount; ++edgeIndex) {
            NetEdge e = (NetEdge) edgeClone.get(edgeIndex);
            e.dispose();
        }

        firePropertyChange("disposed", false, true);
    }
} /* end class VesprPort */
