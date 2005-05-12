package com.traclabs.biosim.editor.graph.environment;

import java.awt.Color;

import javax.swing.JFrame;

import com.traclabs.biosim.editor.graph.FigPassiveNode;
import com.traclabs.biosim.editor.ui.SimEnvironmentPropertiesFrame;

public class FigSimEnvironmentNode extends FigPassiveNode {
    private JFrame myEditFrame;

    public FigSimEnvironmentNode() {
        super();
        setFillColor(Color.WHITE);
        setLineColor(Color.MAGENTA);
    }

    protected JFrame getPropertiesEditor() {
        if (myEditFrame != null)
            return myEditFrame;
        myEditFrame = new SimEnvironmentPropertiesFrame(this);
        myEditFrame.pack();
        return myEditFrame;
    }

}
