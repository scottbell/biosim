package com.traclabs.biosim.editor.graph.air;

import java.io.Serializable;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.presentation.FigNode;

import com.traclabs.biosim.editor.graph.EditorGraphModel;
import com.traclabs.biosim.editor.graph.EditorNode;

public class NitrogenStoreNode extends EditorNode implements Serializable {
    public NitrogenStoreNode() {
        setText("NitrogenStore");
    }

    public FigNode makePresentation(Layer lay) {
        FigNitrogenStoreNode node = new FigNitrogenStoreNode();
        node.setOwner(this);
        return node;
    }

    protected MutableGraphModel createNestedGraphModel() {
        return new EditorGraphModel();
    }

}
