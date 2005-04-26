package com.traclabs.biosim.editor.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.graph.presentation.NetPort;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * An abstract subclass of NetNode for use in the Editor application. All nodes
 * in the Editor application will be subclasses of ModuleNode
 */
public abstract class ModuleNode extends NetNode implements Serializable {
    protected EditorPort _port;

    protected int _number;

    protected String _text = new String();

    protected int _id;

    protected MutableGraphModel _nestedModel;
    

    public ModuleNode() {
        _nestedModel = createNestedGraphModel();
    }
    
    public abstract SimBioModuleImpl getSimBioModuleImpl();
   

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

    /** Returns the number of inputs into the EDITOR node. */
    public int getSourceCount() {
        return getSourceNodes().size();
    }

    /** Returns the number of outputs from the EDITOR node. */
    public int getDestCount() {
        return getDestNodes().size();
    }
    

    public abstract FigNode makePresentation(Layer lay);

    protected MutableGraphModel createNestedGraphModel() {
        return new EditorGraphModel();
    }
    
    /**
     * @param figNode
     * @param destFigNode
     * @return
     */
    public boolean edgeExists(ModuleNode destNode) {
        NetPort sourcePort = (NetPort)getPort();
        NetPort destPort = (NetPort)destNode.getPort();
        for (Iterator iter = sourcePort.getEdges().iterator(); iter.hasNext();){
            NetEdge currentEdge = (NetEdge) iter.next();
            if (currentEdge.getDestPort().equals(destPort))
                return true;
        }
        return false;
    }

    /**
     * @param sourceNode
     * @param destNode
     * @return
     */
    public static boolean meetsConstraintsForEdge(ModuleNode sourceNode, ModuleNode destNode) {
        //only allow 2 edges (one each way) for each module
        if (sourceNode.edgeExists(destNode))
            return false;
        //check to see if consumption/production is allowed
        if (sourceNode instanceof PassiveNode){
            PassiveNode sourcePassiveNode = (PassiveNode)sourceNode;
            if (!sourcePassiveNode.allowsConsumptionFromActiveNode(destNode))
                return false;
        }
        else if (destNode instanceof PassiveNode){
            PassiveNode destActiveNode = (PassiveNode)destNode;
            if (!destActiveNode.allowsProductionFromActiveNode(sourceNode))
                return false;
        }
        //Connecting two active nodes?
        else
            return false;
        return true;
    }
} /* end class EditorNode */
