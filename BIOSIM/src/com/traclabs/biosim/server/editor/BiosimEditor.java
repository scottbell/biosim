package com.traclabs.biosim.server.editor;

import org.tigris.gef.graph.presentation.JGraphFrame;

/**
 * @author scott
 */
public class BiosimEditor {
    JGraphFrame myFrame;
    
    public BiosimEditor(){
        myFrame = new JGraphFrame();
        myFrame.setVisible(true);
    }
    
    public static void main(String args[]){
        BiosimEditor editor = new BiosimEditor();
    }
}
