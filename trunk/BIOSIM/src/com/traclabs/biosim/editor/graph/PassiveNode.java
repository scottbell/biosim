package com.traclabs.biosim.editor.graph;



public abstract class PassiveNode extends ModuleNode {
    /**
     * @param destNode
     * @return
     */
    public boolean allowsProductionFromActiveNode(ModuleNode destNode) {
        if (destNode instanceof ActiveNode){
            ActiveNode activeDestNode = (ActiveNode)destNode;
            Class[] producersAllowed = getProducersAllowed();
            for (int i = 0; i < producersAllowed.length; i++){
                if (producersAllowed[i].isInstance(activeDestNode.getSimBioModuleImpl()))
                    return true;
            }
        }
        return false;
    }
    
    /**
     * @param sourceNode
     * @return
     */
    public boolean allowsConsumptionFromActiveNode(ModuleNode sourceNode) {
        if (sourceNode instanceof ActiveNode){
            ActiveNode activeSourceNode = (ActiveNode)sourceNode;
            Class[] consumersAllowed = getConsumersAllowed();
            for (int i = 0; i < consumersAllowed.length; i++){
                if (consumersAllowed[i].isInstance(activeSourceNode.getSimBioModuleImpl()))
                    return true;
            }
        }
        return false;
    }

    /**
     * @return
     */
    public abstract Class[] getProducersAllowed();
    
    /**
     * @return
     */
    public abstract Class[] getConsumersAllowed();

    
}
