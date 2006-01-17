/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import com.traclabs.biosim.client.simulation.power.schematic.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.crew.CrewGroupNode;

/**
 * @author scott
 * 
 */
public class CrewToolBar extends EditorToolBar {
    public CrewToolBar() {
        super("Crew");
        add(new CmdCreateModuleNode(CrewGroupNode.class, "EditorBase", "CrewGroup"));
    }
}