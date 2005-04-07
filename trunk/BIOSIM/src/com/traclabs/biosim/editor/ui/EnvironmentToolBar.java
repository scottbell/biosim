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

    public EnvironmentToolBar() {
        super("Environment");
        myEnvironmentButton = new JButton("Environment");
        myDehumidifierButton = new JButton("Dehumidifier");

        add(myDehumidifierButton);
        addSeparator();
        add(myEnvironmentButton);
    }
}