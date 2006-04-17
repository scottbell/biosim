package com.traclabs.biosim.client.simulation.power.schematic.graph;

import javax.swing.JFrame;

import com.traclabs.biosim.client.simulation.power.schematic.ui.ActivePropertiesFrame;


public abstract class FigActiveNode extends FigModuleLabelNode {
    private JFrame myEditFrame;
    
    protected JFrame getPropertiesEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new ActivePropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }
}