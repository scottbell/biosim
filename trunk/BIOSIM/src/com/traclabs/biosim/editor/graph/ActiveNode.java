package com.traclabs.biosim.editor.graph;

import java.util.Iterator;
import java.util.List;




public abstract class ActiveNode extends ModuleNode {

    /**
     * @param destNode
     * @return
     */
    public boolean isConsumingForPassiveNode(ModuleNode destNode) {
        if (destNode instanceof PassiveNode){
            PassiveNode passiveDestNode = (PassiveNode)destNode;
            List currentNodesConnected = getDestNodes();
            for (Iterator iter = currentNodesConnected.iterator(); iter.hasNext();){
                ModuleNode currentNode = (ModuleNode) iter.next();
                if (currentNode.equals(destNode))
                    return true;
            }
        }
        return false;
    }

    /**
     * @param sourceNode
     * @return
     */
    public boolean isProducingForPassiveNode(ModuleNode sourceNode) {
        if (sourceNode instanceof PassiveNode){
            PassiveNode passiveDestNode = (PassiveNode)sourceNode;
            List currentNodesConnected = getSourceNodes();
            for (Iterator iter = currentNodesConnected.iterator(); iter.hasNext();){
                ModuleNode currentNode = (ModuleNode) iter.next();
                if (currentNode.equals(sourceNode))
                    return true;
            }
        }
        return false;
    }
    
}
