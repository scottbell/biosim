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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.graph.presentation.NetPort;

/**
 * An abstract subclass of NetNode for use in the VESPR application. All nodes
 * in the VESPR application will be subclasses of VesprNode
 */
public abstract class EditorNode extends NetNode implements Serializable {
    protected EditorPort _port;

    protected int _number;

    protected String _text = new String();

    protected int _id;

    protected MutableGraphModel _nestedModel;

    public EditorNode() {
        _nestedModel = createNestedGraphModel();
    }

    /**
     * Initialize a new node from the given default node and application
     * specific model.
     * <p>
     */
    public void initialize(Hashtable args) {
        addPort(_port = new EditorPort(this));
        //center.printEdges();
        _number = _NextNumber++;

    }

    protected MutableGraphModel createNestedGraphModel() {
        return null;
    }

    static int _NextNumber = 1;

    public void setText(String text) {
        String oldText = _text;
        _text = text;
        this.firePropertyChange("text", oldText, _text);
    }

    public String getText() {
        return _text;
    }

    public Object getPort() {
        return _port;
    }

    public String getId() {
        return "" + _number;
    }

    public int getNumber() {
        return _number;
    }

    public MutableGraphModel getNestedModel() {
        return _nestedModel;
    }

    /** Returns all source nodes for this node. */
    public List getSourceNodes() {
        List nodes = new ArrayList();
        List edges = getInBoundEdges();

        Iterator i = edges.iterator();
        while (i.hasNext()) {
            NetEdge edge = (NetEdge) i.next();
            nodes.add(edge.getSourcePort().getParentNode());
        }

        return nodes;
    }

    /** Returns all destination nodes for this node. */
    public List getDestNodes() {
        List edges = getOutBoundEdges();
        List nodes = new ArrayList();

        Iterator i = edges.iterator();
        while (i.hasNext()) {
            NetEdge edge = (NetEdge) i.next();
            nodes.add(edge.getDestPort().getParentNode());
        }

        return nodes;
    }

    /** Returns all in bound edges for this node. */
    public List getInBoundEdges() {
        ArrayList edges = new ArrayList();
        NetPort destPort = (NetPort) getPort();
        Enumeration theElements = destPort.getEdges().elements();
        while (theElements.hasMoreElements()) {
            NetEdge edge = (NetEdge) theElements.nextElement();
            if (edge.getDestPort() == destPort) {
                edges.add(edge);
            }
        }
        return edges;
    }

    /** Returns out bound edges for this node. */
    public List getOutBoundEdges() {
        ArrayList edges = new ArrayList();
        NetPort srcPort = (NetPort) getPort();
        Enumeration theElements = srcPort.getEdges().elements();
        while (theElements.hasMoreElements()) {
            NetEdge edge = (NetEdge) theElements.nextElement();
            if (edge.getSourcePort() == srcPort) {
                edges.add(edge);
            }
        }
        return edges;
    }

    /** Returns the number of inputs into the VESPR node. */
    public int getSourceCount() {
        return getSourceNodes().size();
    }

    /** Returns the number of outputs from the VESPR node. */
    public int getDestCount() {
        return getDestNodes().size();
    }
} /* end class VesprNode */
