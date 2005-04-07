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

    public CrewToolBar() {
        super("Crew");
        myCrewButton = new JButton("Crew");

        add(myCrewButton);
    }
}