package com.traclabs.biosim.editor.graph;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;

public abstract class FigStoreNode extends ModuleFigLabelNode {

    JFrame myStoreEditFrame;

    public FigStoreNode() {
        super();
        setShowShadow(false);
        myStoreEditFrame = createStoreEditFrame();
    }

    /**
     * 
     */
    private JFrame createStoreEditFrame() {
        JFrame newEditFrame = new JFrame();
        newEditFrame.setLayout(new GridLayout(3,3));
        newEditFrame.add(new JLabel("Name"));
        newEditFrame.add(new JTextField(getText()));
        newEditFrame.add(new JLabel("Capacity"));
        newEditFrame.add(new JTextField("0"));
        newEditFrame.add(new JLabel("Level"));
        newEditFrame.add(new JTextField("0"));
        newEditFrame.setTitle(getText() + " Properties");
        newEditFrame.pack();
        return newEditFrame;
    }

    protected Fig createBgFig() {
        return new FigCircle(0, 0, 75, 50);
    }

    public String getPrivateData() {
        return null; //"text=\"" + _label.getText() + "\"";
    }


    public void setPrivateData(String data) {
        StringTokenizer tokenizer = new StringTokenizer(data, "=\"' ");

        while (tokenizer.hasMoreTokens()) {
            String tok = tokenizer.nextToken();
            if (tok.equals("text")) {
                String s = tokenizer.nextToken();
                _label.setText(s);
            } else {
                /* Unknown value */
            }
        }
    }

    public Point connectionPoint(Point anotherPt) {
        return connectionPoint((FigCircle) _bgFig, anotherPt);
    }

    public Dimension getMinimumSize() {
        Dimension dim = _label.getSize();

        double x = dim.getWidth() / 2.0;
        double y = dim.getHeight() / 2.0;
        double c = x;
        double a = (Math.sqrt((x + c) * (x + c) + y * y) + Math.sqrt((x - c)
                * (x - c) + y * y)) / 2.0;
        double b = Math.sqrt(a * a - c * c);
        int w = Math.max((int) (2 * a) + 6, 75);
        int h = Math.max((int) (2 * b) + 6, 50);
        return new Dimension(w, h);
    }
    
    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleFigNode#getPropertyEditor()
     */
    protected JFrame getPropertyEditor() {
        return myStoreEditFrame;
    }
} /* end class FigGoToNode */
