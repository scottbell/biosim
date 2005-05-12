package com.traclabs.biosim.editor.graph;

import javax.swing.JFrame;

import com.traclabs.biosim.editor.ui.StorePropertiesFrame;

public abstract class FigStoreNode extends FigPassiveNode {
    private JFrame myEditFrame;

    protected JFrame getPropertiesEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new StorePropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }
} /* end class FigGoToNode */
