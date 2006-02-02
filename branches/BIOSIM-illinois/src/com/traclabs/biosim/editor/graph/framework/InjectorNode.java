package com.traclabs.biosim.editor.graph.framework;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.ActiveNode;
import com.traclabs.biosim.server.simulation.framework.InjectorImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;


public class InjectorNode extends ActiveNode{
    private static int nameID = 0;
    
    private InjectorImpl myInjectorImpl;
    public InjectorNode() {
    	myInjectorImpl = new InjectorImpl(0, "Injector" + nameID++);
    }

    public FigNode makePresentation(Layer lay) {
        FigInjectorNode node = new FigInjectorNode();
        node.setOwner(this);
        return node;
    }
    
    public SimBioModuleImpl getSimBioModuleImpl(){
        return myInjectorImpl;
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleNode#getModuleType()
     */
    public String getModuleType() {
        return "Injector";
    }
}
