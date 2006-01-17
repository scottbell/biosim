/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import com.traclabs.biosim.client.simulation.power.schematic.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.water.DirtyWaterStoreNode;
import com.traclabs.biosim.editor.graph.water.GreyWaterStoreNode;
import com.traclabs.biosim.editor.graph.water.PotableWaterStoreNode;
import com.traclabs.biosim.editor.graph.water.WaterRSNode;

/**
 * @author scott
 * 
 */
public class WaterToolBar extends EditorToolBar {
    public WaterToolBar() {
        super("Water");
        add(new CmdCreateModuleNode(WaterRSNode.class, "EditorBase", "WaterRS"));
        addSeparator();
        add(new CmdCreateModuleNode(PotableWaterStoreNode.class, "EditorBase", "PotableWaterStore"));
        add(new CmdCreateModuleNode(GreyWaterStoreNode.class, "EditorBase", "GreyWater"));
        add(new CmdCreateModuleNode(DirtyWaterStoreNode.class, "EditorBase", "DirtyWater"));
    }

}