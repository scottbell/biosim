/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

/**
 * @author scott
 * 
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