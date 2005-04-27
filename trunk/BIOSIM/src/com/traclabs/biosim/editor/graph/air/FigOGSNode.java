package com.traclabs.biosim.editor.graph.air;

import java.awt.Color;

import javax.swing.JFrame;

import com.traclabs.biosim.editor.graph.FigActiveModuleNode;

public class FigOGSNode extends FigActiveModuleNode {

    public FigOGSNode() {
        super();
        setFillColor(Color.BLUE);
        setLineColor(Color.CYAN);
    }

    public String getTag() {
        return "OGS";
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleFigNode#getPropertyEditor()
     */
    protected JFrame getPropertyEditor() {
        // TODO Auto-generated method stub
        return null;
    }

}
