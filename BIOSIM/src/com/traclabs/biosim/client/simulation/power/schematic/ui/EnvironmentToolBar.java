/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import com.traclabs.biosim.client.simulation.power.schematic.base.CmdCreateModuleNode;
import com.traclabs.biosim.client.simulation.power.schematic.graph.environment.SimEnvironmentNode;

/**
 * @author scott
 * 
 */
public class EnvironmentToolBar extends EditorToolBar {
    public EnvironmentToolBar() {
        super("Environment");
        add(new CmdCreateModuleNode(SimEnvironmentNode.class, "EditorBase", "SimEnvironment"));
    }
}