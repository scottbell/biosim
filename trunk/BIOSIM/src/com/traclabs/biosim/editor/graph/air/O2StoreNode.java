package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.EditorNode;


public class O2StoreNode extends EditorNode{
    public O2StoreNode() {
        setText("O2Store");
    }

    public FigNode makePresentation(Layer lay) {
        FigO2StoreNode node = new FigO2StoreNode();
        node.setOwner(this);
        return node;
    }
}
