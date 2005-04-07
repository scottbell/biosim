package com.traclabs.biosim.editor.graph.air;

import java.awt.Color;

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

}
