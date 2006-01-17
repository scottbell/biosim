/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.client.simulation.power.schematic.ui;

import com.traclabs.biosim.client.simulation.power.schematic.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.food.BiomassPSNode;
import com.traclabs.biosim.editor.graph.food.BiomassStoreNode;
import com.traclabs.biosim.editor.graph.food.FoodProcessorNode;
import com.traclabs.biosim.editor.graph.food.FoodStoreNode;

/**
 * @author scott
 * 
 */
public class FoodToolBar extends EditorToolBar {
    public FoodToolBar() {
        super("Food");
        add(new CmdCreateModuleNode(BiomassPSNode.class, "EditorBase", "BiomassPS"));
        add(new CmdCreateModuleNode(FoodProcessorNode.class, "EditorBase", "FoodProcessor"));
        addSeparator();
        add(new CmdCreateModuleNode(BiomassStoreNode.class, "EditorBase", "BiomassStore"));
        add(new CmdCreateModuleNode(FoodStoreNode.class, "EditorBase", "FoodStore"));
        
    }
}