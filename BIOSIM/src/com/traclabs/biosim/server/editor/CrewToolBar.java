/*
 * Created on Jan 26, 2005
 *
 */
package com.traclabs.biosim.server.editor;

import javax.swing.JButton;

/**
 * @author scott
 * 
 */
public class CrewToolBar extends EditorToolBar {
    private JButton myCrewButton;

    private JButton myConduitButton;

    public CrewToolBar(BiosimEditor pEditor) {
        super("Crew");
        myCrewButton = new JButton("Crew");
        myConduitButton = new JButton("Conduit");

        add(myCrewButton);
        addSeparator();
        add(myConduitButton);
    }
}