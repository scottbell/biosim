package com.traclabs.biosim.editor.graph.air;

import org.tigris.gef.base.Layer;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.EditorNode;


public class OGSNode extends EditorNode{
    public OGSNode() {
        setText("OGS");
    }

    public FigNode makePresentation(Layer lay) {
        FigOGSNode node = new FigOGSNode();
        node.setOwner(this);
        return node;
    }
}
