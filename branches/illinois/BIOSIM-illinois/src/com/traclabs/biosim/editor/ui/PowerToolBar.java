/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import com.traclabs.biosim.editor.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.power.NuclearPowerPSNode;
import com.traclabs.biosim.editor.graph.power.PowerStoreNode;
import com.traclabs.biosim.editor.graph.power.SolarPowerPSNode;

/**
 * @author scott
 * 
 */
public class PowerToolBar extends EditorToolBar {
    public PowerToolBar() {
        super("Power");
        add(new CmdCreateModuleNode(SolarPowerPSNode.class, "EditorBase", "SolarPowerPS"));
        add(new CmdCreateModuleNode(NuclearPowerPSNode.class, "EditorBase", "NuclearPowerPS"));
        addSeparator();
        add(new CmdCreateModuleNode(PowerStoreNode.class, "EditorBase", "PowerStore"));
    }
}