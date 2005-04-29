package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.air.CRSImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class CRSNode extends ActiveNode{
    private static int nameID = 0;
    
    private CRSImpl myCRSImpl;
    public CRSNode() {
        myCRSImpl = new CRSImpl(0, "CRS" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigCRSNode node = new FigCRSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myCRSImpl;
    }
}
