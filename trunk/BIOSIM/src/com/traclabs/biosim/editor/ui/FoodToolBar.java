/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.food.BiomassRSNode;
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
        add(new CmdCreateNode(BiomassRSNode.class, "EditorBase", "BiomassRS"));
        add(new CmdCreateNode(FoodProcessorNode.class, "EditorBase", "FoodProcessor"));
        addSeparator();
        add(new CmdCreateNode(BiomassStoreNode.class, "EditorBase", "BiomassStore"));
        add(new CmdCreateNode(FoodStoreNode.class, "EditorBase", "FoodStore"));
        
    }
}