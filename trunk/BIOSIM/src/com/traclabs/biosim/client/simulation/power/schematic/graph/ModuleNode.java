package com.traclabs.biosim.client.simulation.power.schematic.graph;

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
    protected PowerPort myPort;

    protected MutableGraphModel myNestedModel;

    public ModuleNode() {
        myNestedModel = createNestedGraphModel();
        //myPort = new PowerPort(this);
    }
    
    public abstract SimBioModuleImpl getSimBioModuleImpl();
    
    public abstract String getModuleType();

    /**
     * Initialize a new node from the given default node and application
     * specific model.
     * <p>
     */
    public void initialize(Hashtable args) {
        addPort(myPort = new PowerPort(this));
        //center.printEdges();

    }

    public Object getPort() {
        return myPort;
    }

    public MutableGraphModel getNestedModel() {
        return myNestedModel;
    }

    /** Returns all source nodes for this node. */
    public List getSourceNodes() {
        List<NetNode> nodes = new ArrayList<NetNode>();
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
        List<NetNode> nodes = new ArrayList<NetNode>();

        Iterator i = edges.iterator();
        while (i.hasNext()) {
            NetEdge edge = (NetEdge) i.next();
            nodes.add(edge.getDestPort().getParentNode());
        }

        return nodes;
    }

    /** Returns all in bound edges for this node. */
    public List getInBoundEdges() {
        ArrayList<NetEdge> edges = new ArrayList<NetEdge>();
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
        ArrayList<NetEdge> edges = new ArrayList<NetEdge>();
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
    
    public String getId(){
        return getSimBioModuleImpl().getModuleName();
    }

    /**
     * @param destNode
     * @return
     */
    public int countEdges(ModuleNode destNode) {
        int edges = 0;
        NetPort sourcePort = (NetPort)getPort();
        NetPort destPort = (NetPort)destNode.getPort();
        for (Iterator iter = sourcePort.getEdges().iterator(); iter.hasNext();){
            NetEdge currentEdge = (NetEdge) iter.next();
            if (currentEdge.getDestPort().equals(destPort))
                edges++;
        }
        return edges;
    }
    
    
} /* end class EditorNode */
