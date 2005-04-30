/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

import org.tigris.gef.base.CmdCreateNode;

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
        add(new CmdCreateNode(WaterRSNode.class, "EditorBase", "WaterRS"));
        addSeparator();
        add(new CmdCreateNode(PotableWaterStoreNode.class, "EditorBase", "PotableWaterStore"));
        add(new CmdCreateNode(GreyWaterStoreNode.class, "EditorBase", "GreyWater"));
        add(new CmdCreateNode(DirtyWaterStoreNode.class, "EditorBase", "DirtyWater"));
    }

}