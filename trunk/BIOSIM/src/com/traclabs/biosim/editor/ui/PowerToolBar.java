/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.power.NuclearPowerPSNode;
import com.traclabs.biosim.editor.graph.power.PowerStoreNode;
import com.traclabs.biosim.editor.graph.power.SolarPowerPSNode;

/**
 * @author scott
 * 
 */
public class PowerToolBar extends EditorToolBar {
    private JButton myPowerPSButton;

    private JButton myPowerStoreButton;

    public PowerToolBar() {
        super("Power");
        add(new CmdCreateNode(SolarPowerPSNode.class, "EditorBase", "SolarPowerPS"));
        add(new CmdCreateNode(NuclearPowerPSNode.class, "EditorBase", "NuclearPowerPS"));
        addSeparator();
        add(new CmdCreateNode(PowerStoreNode.class, "EditorBase", "PowerStore"));
    }
}