package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ModuleNode;
import com.traclabs.biosim.server.simulation.air.NitrogenStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

public class NitrogenStoreNode extends ModuleNode{
    private NitrogenStoreImpl myNitrogenStoreImpl;
    
    public NitrogenStoreNode() {
        myNitrogenStoreImpl = new NitrogenStoreImpl(0, "Unamed");
        setText("NitrogenStore");
    }

    public FigNode makePresentation(Layer lay) {
        FigNitrogenStoreNode node = new FigNitrogenStoreNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myNitrogenStoreImpl;
    }
}
