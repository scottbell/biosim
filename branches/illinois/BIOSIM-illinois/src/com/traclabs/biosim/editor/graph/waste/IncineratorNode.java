package com.traclabs.biosim.editor.graph.waste;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.waste.IncineratorImpl;


public class IncineratorNode extends ActiveNode{
    private static int nameID = 0;
    
    private IncineratorImpl myIncineratorImpl;
    public IncineratorNode() {
        myIncineratorImpl = new IncineratorImpl(0, "Incinerator" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigIncineratorNode node = new FigIncineratorNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myIncineratorImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "Incinerator";
    }
}
