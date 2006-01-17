package com.traclabs.biosim.client.simulation.power.schematic.graph;

import java.util.Iterator;
import java.util.List;

public abstract class ActiveNode extends ModuleNode {

    /**
     * @param sourceNode
     * @return
     */
    public boolean isConsumingFromNode(PassiveNode sourceNode) {
        List currentNodesConnected = getSourceNodes();
        for (Iterator iter = currentNodesConnected.iterator(); iter.hasNext();) {
            ModuleNode currentNode = (ModuleNode) iter.next();
            if (currentNode.getClass().equals(sourceNode.getClass()))
                return true;
        }
        return false;
    }

    /**
     * @param sourceNode
     * @return
     */
    public boolean isProducingForNode(PassiveNode destNode) {
        List currentNodesConnected = getDestNodes();
        for (Iterator iter = currentNodesConnected.iterator(); iter.hasNext();) {
            ModuleNode currentNode = (ModuleNode) iter.next();
            if (currentNode.getClass().equals(destNode.getClass()))
                return true;
        }
        return false;
    }

}
