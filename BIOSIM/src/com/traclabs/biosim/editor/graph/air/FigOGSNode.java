package com.traclabs.biosim.editor.graph.air;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.traclabs.biosim.editor.graph.FigActiveModuleNode;

public class FigOGSNode extends FigActiveModuleNode {
    private JFrame myEditFrame;
    
    public FigOGSNode() {
        super();
        setFillColor(Color.BLUE);
        setLineColor(Color.CYAN);
    }

    /* (non-Javadoc)
     * @see com.traclabs.biosim.editor.graph.ModuleFigNode#getPropertyEditor()
     */
    protected JFrame getPropertyEditor() {
        createStoreEditFrame();
        return myEditFrame;
    }
    
    /**
     * 
     */
    private void createStoreEditFrame() {
        if (myEditFrame != null)
            return;
        myEditFrame = new JFrame();
        myEditFrame.setLayout(new GridLayout(1,1));
        myEditFrame.add(new JLabel("Name"));
        myEditFrame.add(new JTextField(getText()));
        myEditFrame.setTitle(getText() + " Properties");
        myEditFrame.pack();
    }

}
