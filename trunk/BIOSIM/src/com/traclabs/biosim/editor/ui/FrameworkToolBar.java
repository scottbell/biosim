/*
 * Created on Jan 26, 2005
 *
 * TODO
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

/**
 * @author scott
 * 
 * TODO
 */
public class FrameworkToolBar extends EditorToolBar {
    private JButton myAccumulatorButton;

    private JButton myInjectorButton;

    private JButton myConduitButton;

    public FrameworkToolBar() {
        super("Framework");
        myAccumulatorButton = new JButton("Accumulator");
        myInjectorButton = new JButton("Injector");

        myConduitButton = new JButton("Conduit");

        add(myAccumulatorButton);
        add(myInjectorButton);
        addSeparator();
        add(myConduitButton);
    }
}