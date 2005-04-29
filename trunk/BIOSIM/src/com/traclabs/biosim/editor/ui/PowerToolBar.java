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
public class PowerToolBar extends EditorToolBar {
    private JButton myPowerPSButton;

    private JButton myPowerStoreButton;

    public PowerToolBar() {
        super("Power");
        myPowerPSButton = new JButton("Power PS");
        myPowerStoreButton = new JButton("Power Store");

        add(myPowerPSButton);
        addSeparator();
        add(myPowerStoreButton);
    }
}