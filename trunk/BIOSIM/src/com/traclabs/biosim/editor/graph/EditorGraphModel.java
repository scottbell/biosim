package com.traclabs.biosim.editor.graph;

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
 * EditorGraphModel listens for when nodes and edges are disposed of and
 * automatically removes them from the graph model.
 * 
 * @author kkusy
 */
public class EditorGraphModel extends DefaultGraphModel {
    public EditorGraphModel(NetList nl) {
        super(nl);
    }

    public EditorGraphModel() {
        super();
    }

    public boolean canAddEdge(Object obj) {
        return (obj instanceof ModuleEdge);
    }

    public boolean canAddNode(Object obj) {
        return (obj instanceof ModuleNode);
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
            ModuleNode node = (ModuleNode) i.next();
            if (node.getSourceCount() == 0) {
                roots.add(node);
            }
        }

        return roots;
    }
}