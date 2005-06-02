package com.traclabs.biosim.editor.graph.food;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.StoreNode;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProducerOperations;
import com.traclabs.biosim.server.simulation.food.FoodStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class FoodStoreNode extends StoreNode{
    private FoodStoreImpl myFoodStoreImpl;
    private static int nameID = 0;
    
    private final static Class[] myProducersAllowed = {FoodProducerOperations.class};
    private final static Class[] myConsumersAllowed = {FoodConsumerOperations.class};
    
    public FoodStoreNode() {
        myFoodStoreImpl = new FoodStoreImpl(0, "FoodStore"+nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigFoodStoreNode node = new FigFoodStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myFoodStoreImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.PassiveNode#getProducersAllowed()
     */
    public Class[] getProducersAllowed() {
        return myProducersAllowed;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.PassiveNode#getConsumersAllowed()
     */
    public Class[] getConsumersAllowed() {
        return myConsumersAllowed;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "FoodStore";
    }
}
