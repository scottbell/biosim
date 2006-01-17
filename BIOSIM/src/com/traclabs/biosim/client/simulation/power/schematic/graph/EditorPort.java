package com.traclabs.biosim.client.simulation.power.schematic.graph;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.graph.presentation.NetPort;

public class EditorPort extends NetPort implements Serializable {

    public EditorPort(NetNode parent) {
        super(parent);
    }

    protected Class defaultEdgeClass(NetPort otherPort) {
        return ModuleEdge.class;
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
        List edgeClone = getEdges();
        int edgeCount = edgeClone.size();
        for (int edgeIndex = 0; edgeIndex < edgeCount; ++edgeIndex) {
            NetEdge e = (NetEdge)(edgeClone.get(edgeIndex));
            e.deleteFromModel();
        }

        firePropertyChange("disposed", false, true);
    }
} /* end class EditorPort */
