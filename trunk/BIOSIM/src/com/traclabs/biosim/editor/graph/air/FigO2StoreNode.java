package com.traclabs.biosim.editor.graph.air;

import java.awt.Color;
import java.util.StringTokenizer;

import org.tigris.gef.graph.GraphEvent;
import org.tigris.gef.graph.GraphListener;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigRect;

import com.traclabs.biosim.editor.graph.FigLabelNode;

public class FigO2StoreNode extends FigLabelNode {

    public FigO2StoreNode() {
        super();
        setFillColor(Color.BLUE);
        setLineColor(Color.WHITE);
        setShowShadow(false);
        setShadowOffset(5);
    }

    public String getTag() {
        return "O2Store";
    }

    protected Fig createShadowFig() {
        Fig fig = new FigRect(2, 2, 1, 1, Color.BLUE, Color.WHITE);
        return fig;
    }
    
    public String getPrivateData() {
        return "text=\"" + _label.getText() + "\"";
    }

    public void setOwner(Object own) {
        super.setOwner(own);
        if (!(own instanceof O2StoreNode))
            return;
        O2StoreNode node = (O2StoreNode) own;

        node.getNestedModel().addGraphEventListener(new GraphListener() {
            public void nodeAdded(GraphEvent e) {
                graphChanged(e);
            }

            public void nodeRemoved(GraphEvent e) {
                graphChanged(e);
            }

            public void graphChanged(GraphEvent e) {
                GraphModel model = (GraphModel) e.getSource();
                FigO2StoreNode.this.setShowShadow(model.getNodes().size() > 0);
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
                //Unknown Value
            }
        }
    }
}
