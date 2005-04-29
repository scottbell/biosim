package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.air.VCCRImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class VCCRNode extends ActiveNode{
    private static int nameID = 0;
    
    private VCCRImpl myVCCRImpl;
    public VCCRNode() {
        myVCCRImpl = new VCCRImpl(0, "VCCR" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigVCCRNode node = new FigVCCRNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myVCCRImpl;
    }
}
