/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import com.traclabs.biosim.client.simulation.power.schematic.base.CmdCreateModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.framework.AccumulatorNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.framework.InjectorNode;

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