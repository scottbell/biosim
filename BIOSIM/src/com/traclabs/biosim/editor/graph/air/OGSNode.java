package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.air.OGSImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class OGSNode extends ActiveNode{
    private OGSImpl myOGSImpl;
    public OGSNode() {
        myOGSImpl = new OGSImpl(0, "Unamed");
        setText("OGS");
    }

    public FigNode makePresentation(Layer lay) {
        FigOGSNode node = new FigOGSNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myOGSImpl;
    }
}
