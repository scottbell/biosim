/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import com.traclabs.biosim.editor.base.CmdCreateModuleNode;
import com.traclabs.biosim.editor.graph.environment.SimEnvironmentNode;

/**
 * @author scott
 * 
 */
public class EnvironmentToolBar extends EditorToolBar {
    public EnvironmentToolBar() {
        super("Environment");
        add(new CmdCreateModuleNode(SimEnvironmentNode.class, "EditorBase", "SimEnvironment"));
    }
}