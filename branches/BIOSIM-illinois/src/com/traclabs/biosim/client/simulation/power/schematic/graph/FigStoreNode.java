package com.traclabs.biosim.client.simulation.power.schematic.graph;

import javax.swing.JFrame;

import com.traclabs.biosim.client.simulation.power.schematic.ui.StorePropertiesFrame;

public abstract class FigStoreNode extends FigPassiveNode {
    private JFrame myEditFrame;
    private boolean myStoreSensed = false;

    protected JFrame getPropertiesEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new StorePropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }

    /**
     * @param pStoreSensed
     */
    public void setIsSensed(boolean pStoreSensed) {
        if (myStoreSensed == pStoreSensed)
            return;
        myStoreSensed = pStoreSensed;
        StoreNode theStoreNode = (StoreNode)getOwner();
        
    }
} /* end class FigGoToNode */
