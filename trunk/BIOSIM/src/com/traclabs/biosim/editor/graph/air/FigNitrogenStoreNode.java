package com.traclabs.biosim.editor.graph.air;

import java.awt.Color;
import java.util.StringTokenizer;

import org.tigris.gef.graph.GraphEvent;
import org.tigris.gef.graph.GraphListener;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigRect;

import com.traclabs.biosim.editor.graph.FigLabelNode;

public class FigNitrogenStoreNode extends FigLabelNode {

    public FigNitrogenStoreNode() {
        super();
        setFillColor(Color.RED);
        setLineColor(Color.BLACK);
        setShowShadow(false);
        setShadowOffset(5);
    }

    public String getTag() {
        return "NitrogenStore";
    }

    protected Fig createShadowFig() {
        Fig fig = new FigRect(2, 2, 1, 1, Color.RED, Color.BLACK);
        return fig;
    }

    public String getPrivateData() {
        return "text=\"" + _label.getText() + "\"";
    }

    public void setOwner(Object own) {
        super.setOwner(own);
        if (!(own instanceof NitrogenStoreNode))
            return;
        NitrogenStoreNode node = (NitrogenStoreNode) own;

        node.getNestedModel().addGraphEventListener(new GraphListener() {
            public void nodeAdded(GraphEvent e) {
                graphChanged(e);
            }

            public void nodeRemoved(GraphEvent e) {
                graphChanged(e);
            }

            public void graphChanged(GraphEvent e) {
                GraphModel model = (GraphModel) e.getSource();
                FigNitrogenStoreNode.this.setShowShadow(model.getNodes().size() > 0);
            }

            public void edgeRemoved(GraphEvent e) {
            }

            public void edgeAdded(GraphEvent e) {
            }
        });
    }

    public void setPrivateData(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "=\"' ");

        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken();
            if (tok.equals("text")) {
                String s = tokenizer.nextToken();
                _label.setText(s);
            } else {
                //Unknown value
            }
        }
    }
}
