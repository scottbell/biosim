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
public class FrameworkToolBar extends EditorToolBar {
    private JButton myAccumulatorButton;
    private JButton myInjectorButton;

    public FrameworkToolBar() {
        super("Framework");
        myAccumulatorButton = new JButton("Accumulator");
        myInjectorButton = new JButton("Injector");

        add(myAccumulatorButton);
        add(myInjectorButton);
    }
}