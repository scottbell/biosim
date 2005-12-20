/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import com.traclabs.biosim.editor.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.framework.AccumulatorNode;
import com.traclabs.biosim.editor.graph.framework.InjectorNode;

/**
 * @author scott
 * 
 */
public class FrameworkToolBar extends EditorToolBar {
    public FrameworkToolBar() {
        super("Framework");
        add(new CmdCreateModuleNode(AccumulatorNode.class, "EditorBase", "Accumulator"));
        add(new CmdCreateModuleNode(InjectorNode.class, "EditorBase", "Injector"));
    }
}