/*
 * Copyright © 2004 S&K Technologies, Inc, 56 Old Hwy 93, St Ignatius, MT 98865
 * All rights reserved.
 * U.S. Government Rights - Commercial software.  Government users are subject
 * to S&K Technologies, Inc, standard license agreement and applicable 
 * provisions of the FAR and its supplements.
 * Use is subject to license terms.
 */
/*
 * Created on Oct 23, 2003
 *
 */
package com.traclabs.biosim.server.editor.graph;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetList;
import org.tigris.gef.graph.presentation.NetNode;

/**
 * VesprGraphModel listens for when nodes and edges are disposed of and
 * automatically removes them from the graph model.
 * 
 * @author kkusy
 */
public class VesprGraphModel extends DefaultGraphModel {
    public VesprGraphModel(NetList nl) {
        super(nl);
    }

    public VesprGraphModel() {
        super();
    }

    public boolean canAddEdge(Object obj) {
        return (obj instanceof VesprEdge);
    }

    public boolean canAddNode(Object obj) {
        return (obj instanceof VesprNode);
    }

    /** Add the given edge to the graph, if valid. */
    public void addEdge(Object edge) {
        NetEdge e = (NetEdge) edge;
        e.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String pName = evt.getPropertyName();
                NetEdge src = (NetEdge) evt.getSource();
                if (pName.equals("disposed")) {
                    src.removePropertyChangeListener(this);
                    removeEdge(src);
                }
            }
        });
        super.addEdge(edge);
    }

    /** Add the given node to the graph, if valid. */
    public void addNode(Object node) {
        NetNode n = (NetNode) node;
        n.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                String pName = evt.getPropertyName();
                NetNode src = (NetNode) evt.getSource();
                if (pName.equals("disposed")) {
                    src.removePropertyChangeListener(this);
                    removeNode(src);
                }
            }
        });
        super.addNode(node);
    }

    /**
     * Returns all nodes that do not have input connections.
     */
    public ArrayList findInputs() {
        ArrayList roots = new ArrayList();
        Collection nodes = getNodes(null);
        Iterator i = nodes.iterator();
        while (i.hasNext()) {
            VesprNode node = (VesprNode) i.next();
            if (node.getSourceCount() == 0) {
                roots.add(node);
            }
        }

        return roots;
    }
}