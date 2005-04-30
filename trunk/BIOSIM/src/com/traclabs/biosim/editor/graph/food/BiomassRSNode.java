package com.traclabs.biosim.editor.graph.food;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.food.BiomassRSImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class BiomassRSNode extends ActiveNode{
    private static int nameID = 0;
    
    private BiomassRSImpl myBiomassRSImpl;
    public BiomassRSNode() {
        myBiomassRSImpl = new BiomassRSImpl(0, "BiomassRS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigBiomassRSNode node = new FigBiomassRSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myBiomassRSImpl;
    }
}
