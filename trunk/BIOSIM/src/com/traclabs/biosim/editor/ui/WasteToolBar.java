/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.waste.DryWasteStoreNode;
import com.traclabs.biosim.editor.graph.waste.IncineratorNode;

/**
 * @author scott
 * 
 */
public class WasteToolBar extends EditorToolBar {
    private JButton myIncineratorButton;

    private JButton myDryWasteStoreButton;

    public WasteToolBar() {
        super("Waste");
        add(new CmdCreateNode(IncineratorNode.class, "EditorBase", "Incinerator"));
        addSeparator();
        add(new CmdCreateNode(DryWasteStoreNode.class, "EditorBase", "DryWasteStore"));
    }

}