package com.traclabs.biosim.editor.graph.air;

import java.awt.Color;

import com.traclabs.biosim.editor.graph.FigStoreNode;

public class FigNitrogenStoreNode extends FigStoreNode {

    public FigNitrogenStoreNode() {
        super();
        setFillColor(Color.RED);
        setLineColor(Color.BLACK);
    }

    public String getTag() {
        return "NitrogenStore";
    }

    

}