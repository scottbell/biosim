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
public class PowerToolBar extends EditorToolBar {
    private JButton myPowerPSButton;

    private JButton myPowerStoreButton;

    private JButton myConduitButton;

    public PowerToolBar(BiosimEditor pEditor) {
        super("Power");
        myPowerPSButton = new JButton("Power PS");
        myPowerStoreButton = new JButton("Power Store");

        myConduitButton = new JButton("Conduit");

        add(myPowerPSButton);
        addSeparator();
        add(myPowerStoreButton);
        addSeparator();
        add(myConduitButton);
    }
}