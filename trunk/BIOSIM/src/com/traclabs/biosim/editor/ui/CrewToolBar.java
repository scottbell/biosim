/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

/**
 * @author scott
 * 
 */
public class CrewToolBar extends EditorToolBar {
    private JButton myCrewButton;

    private JButton myConduitButton;

    public CrewToolBar() {
        super("Crew");
        myCrewButton = new JButton("Crew");
        myConduitButton = new JButton("Conduit");

        add(myCrewButton);
        addSeparator();
        add(myConduitButton);
    }
}