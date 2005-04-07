/*
 * Created on Jan 26, 2005
 *
 * TODO
 */
package com.traclabs.biosim.editor.ui;

import javax.swing.JButton;

/**
 * @author scott
 * 
 * TODO
 */
public class WasteToolBar extends EditorToolBar {
    private JButton myIncineratorButton;

    private JButton myDryWasteStoreButton;

    public WasteToolBar() {
        super("Waste");
        myIncineratorButton = new JButton("Incinerator");
        myDryWasteStoreButton = new JButton("Dry Waste Store");

        add(myIncineratorButton);
        addSeparator();
        add(myDryWasteStoreButton);
    }

}