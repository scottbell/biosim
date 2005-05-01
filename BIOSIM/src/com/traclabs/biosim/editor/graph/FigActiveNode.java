package com.traclabs.biosim.editor.graph;

import javax.swing.JFrame;


public abstract class FigActiveNode extends FigModuleLabelNode {
    private JFrame myEditFrame;
    
    /*
     * (non-Javadoc)
     * 
     * @see com.traclabs.biosim.editor.graph.ModuleFigNode#getPropertyEditor()
     */
    protected JFrame getPropertiesEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new ActivePropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }
}