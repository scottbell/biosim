package com.traclabs.biosim.editor.graph;

import java.util.Collection;
import java.util.LinkedList;

public abstract class PassiveNode extends ModuleNode {
    /**
     * @param destNode
     * @return
     */
    public Class[] getClassesMatchingProductionFromActiveNode(
            ActiveNode destNode) {
        Collection<Class> producersMatching = new LinkedList<Class>();
        Class[] producersAllowed = getProducersAllowed();
        for (int i = 0; i < producersAllowed.length; i++) {
            if (producersAllowed[i].isInstance(destNode
                    .getSimBioModuleImpl())) {
                producersMatching.add(producersAllowed[i]);
            }
        }
        Class[] classArray = (producersMatching.toArray(new Class[0]));
        return classArray;
    }

    /**
     * @param sourceNode
     * @return
     */
    public Class[] getClassesMatchingConsumptionFromActiveNode(
            ActiveNode sourceNode) {
        Collection<Class> consumersMatching = new LinkedList<Class>();
        Class[] consumersAllowed = getConsumersAllowed();
        for (int i = 0; i < consumersAllowed.length; i++) {
            if (consumersAllowed[i].isInstance(sourceNode
                    .getSimBioModuleImpl())) {
                consumersMatching.add(consumersAllowed[i]);
            }
        }
        Class[] classArray = (consumersMatching.toArray(new Class[0]));
        return classArray;
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
