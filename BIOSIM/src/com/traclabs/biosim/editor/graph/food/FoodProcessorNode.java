package com.traclabs.biosim.editor.graph.food;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.food.FoodProcessorImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class FoodProcessorNode extends ActiveNode{
    private static int nameID = 0;
    
    private FoodProcessorImpl myFoodProcessorImpl;
    public FoodProcessorNode() {
        myFoodProcessorImpl = new FoodProcessorImpl(0, "FoodProcessor" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigFoodProcessorNode node = new FigFoodProcessorNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myFoodProcessorImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "FoodProcessor";
    }
}
