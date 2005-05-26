/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

import com.traclabs.biosim.editor.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.water.DirtyWaterStoreNode;
import com.traclabs.biosim.editor.graph.water.GreyWaterStoreNode;
import com.traclabs.biosim.editor.graph.water.PotableWaterStoreNode;
import com.traclabs.biosim.editor.graph.water.WaterRSNode;

/**
 * @author scott
 * 
 */
public class WaterToolBar extends EditorToolBar {
    private JButton myWaterRSButton;

    private JButton myPotableWaterStoreButton;

    private JButton myGreyWaterStoreButton;

    private JButton myDirtyWaterStoreButton;

    public WaterToolBar() {
        super("Water");
        add(new CmdCreateModuleNode(WaterRSNode.class, "EditorBase", "WaterRS"));
        addSeparator();
        add(new CmdCreateModuleNode(PotableWaterStoreNode.class, "EditorBase", "PotableWaterStore"));
        add(new CmdCreateModuleNode(GreyWaterStoreNode.class, "EditorBase", "GreyWater"));
        add(new CmdCreateModuleNode(DirtyWaterStoreNode.class, "EditorBase", "DirtyWater"));
    }

}