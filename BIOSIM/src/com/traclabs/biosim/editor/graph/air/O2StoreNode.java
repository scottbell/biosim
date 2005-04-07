package com.traclabs.biosim.editor.graph.air;

import java.io.Serializable;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.EditorGraphModel;
import com.traclabs.biosim.editor.graph.EditorNode;


public class O2StoreNode extends EditorNode implements Serializable {
    public O2StoreNode() {
        setText("O2Store");
    }

    public FigNode makePresentation(Layer lay) {
        FigO2StoreNode node = new FigO2StoreNode();
        node.setOwner(this);
        return node;
    }

    protected MutableGraphModel createNestedGraphModel() {
        return new EditorGraphModel();
    }

}
