package com.traclabs.biosim.client.simulation.power.schematic.graph;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.presentation.NetEdge;
import org.tigris.gef.presentation.FigEdge;

/**
 */

public class ModuleEdge extends NetEdge {
    private String myName = "Unnamed";
    
    private int myIndex;
    private Logger myLogger;
    
    public ModuleEdge() {
        myLogger = Logger.getLogger(ModuleEdge.class);
        myName = "Unnamed";
    } 

    public String getId() {
        return toString();
    }
    
    public String getName() {
        return myName;
    }
    
    public void setIndex(int pIndex){
        myIndex = pIndex;
    }
    
    public int getIndex(){
        return myIndex;
    }

    public FigEdge makePresentation(Layer lay) {
        return new FigModuleEdge();
    }
    
}
