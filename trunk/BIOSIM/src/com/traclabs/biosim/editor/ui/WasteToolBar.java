/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import com.traclabs.biosim.editor.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.waste.DryWasteStoreNode;
import com.traclabs.biosim.editor.graph.waste.IncineratorNode;

/**
 * @author scott
 * 
 */
public class WasteToolBar extends EditorToolBar {
    public WasteToolBar() {
        super("Waste");
        add(new CmdCreateModuleNode(IncineratorNode.class, "EditorBase", "Incinerator"));
        addSeparator();
        add(new CmdCreateModuleNode(DryWasteStoreNode.class, "EditorBase", "DryWasteStore"));
    }

}