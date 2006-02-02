package com.traclabs.biosim.editor.graph.food;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.food.BiomassPSImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class BiomassPSNode extends ActiveNode{
    private static int nameID = 0;
    
    private BiomassPSImpl myBiomassPSImpl;
    public BiomassPSNode() {
        myBiomassPSImpl = new BiomassPSImpl(0, "BiomassPS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigBiomassPSNode node = new FigBiomassPSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myBiomassPSImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        // TODO Auto-generated method stub
        return "BiomassPS";
    }
}
