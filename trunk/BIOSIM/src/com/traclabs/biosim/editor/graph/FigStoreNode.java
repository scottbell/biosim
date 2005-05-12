package com.traclabs.biosim.editor.graph;

import javax.swing.JFrame;

import com.traclabs.biosim.editor.ui.StorePropertiesFrame;

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
     * @param b
     */
    public void setIsSensed(boolean pStoreSensed) {
        if (myStoreSensed == pStoreSensed)
            return;
        myStoreSensed = pStoreSensed;
        StoreNode theStoreNode = (StoreNode)getOwner();
        if (myStoreSensed){
            theStoreNode.addSensor();
            setDescriptionText("sensed");
            
        }
        else{
            theStoreNode.removeSensor();
            setDescriptionText("");
        }
        
    }
} /* end class FigGoToNode */
