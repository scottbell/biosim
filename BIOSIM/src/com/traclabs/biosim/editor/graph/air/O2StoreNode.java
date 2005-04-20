package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ModuleNode;
import com.traclabs.biosim.server.simulation.air.O2StoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class O2StoreNode extends ModuleNode{
    private O2StoreImpl myO2StoreImpl;
    
    public O2StoreNode() {
        myO2StoreImpl = new O2StoreImpl(0, "Unamed");
        setText("O2Store");
    }

    public FigNode makePresentation(Layer lay) {
        FigO2StoreNode node = new FigO2StoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myO2StoreImpl;
    }
}
