/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.framework.AccumulatorNode;
import com.traclabs.biosim.editor.graph.framework.InjectorNode;

/**
 * @author scott
 * 
 */
public class FrameworkToolBar extends EditorToolBar {
    private JButton myAccumulatorButton;
    private JButton myInjectorButton;

    public FrameworkToolBar() {
        super("Framework");
        add(new CmdCreateNode(AccumulatorNode.class, "EditorBase", "Accumulator"));
        add(new CmdCreateNode(InjectorNode.class, "EditorBase", "Injector"));
    }
}