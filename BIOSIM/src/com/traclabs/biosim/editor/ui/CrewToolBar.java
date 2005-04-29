/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

import org.tigris.gef.base.CmdCreateNode;

import com.traclabs.biosim.editor.graph.crew.CrewGroupNode;

/**
 * @author scott
 * 
 */
public class CrewToolBar extends EditorToolBar {
    private JButton myCrewButton;

    public CrewToolBar() {
        super("Crew");
        add(new CmdCreateNode(CrewGroupNode.class, "EditorBase", "CrewGroup"));
    }
}