/*
 * Created on Jan 26, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.editor;

import javax.swing.JButton;

/**
 * @author scott
 *
 * TODO
 */
public class EnvironmentToolBar extends EditorToolBar {
    private JButton myEnvironmentButton;
    private JButton myDehumidifierButton;
    
    private JButton myConduitButton;
    
    public EnvironmentToolBar() {
        super("Environment");
        myEnvironmentButton = new JButton("Environment");
        myDehumidifierButton = new JButton("Dehumidifier");
        myConduitButton = new JButton("Conduit");
        
        add(myEnvironmentButton);
        add(myDehumidifierButton);
        addSeparator();
        add(myConduitButton);
    }
}
