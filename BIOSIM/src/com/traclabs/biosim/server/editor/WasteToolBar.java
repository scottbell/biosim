/*
 * Created on Jan 26, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.editor;

import javax.swing.JButton;

/**
 * @author scott
 * 
 * TODO
 */
public class WasteToolBar extends EditorToolBar {
    private JButton myIncineratorButton;

    private JButton myDryWasteStoreButton;

    private JButton myConduitButton;

    public WasteToolBar() {
        super("Waste");
        myIncineratorButton = new JButton("Incinerator");
        myDryWasteStoreButton = new JButton("Dry Waste Store");
        myConduitButton = new JButton("Conduit");

        add(myIncineratorButton);
        addSeparator();
        add(myDryWasteStoreButton);
        addSeparator();
        add(myConduitButton);
    }

}