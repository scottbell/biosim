package com.traclabs.biosim.editor.graph;

import java.util.StringTokenizer;

public abstract class FigActiveModuleNode extends FigLabelNode {

    public FigActiveModuleNode() {
        super();
    }

    public abstract String getTag();

    public String getPrivateData() {
        return "text=\"" + _label.getText() + "\"";
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