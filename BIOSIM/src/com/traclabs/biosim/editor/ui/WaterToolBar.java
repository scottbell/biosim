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
public class WaterToolBar extends EditorToolBar {
    private JButton myWaterRSButton;

    private JButton myPotableWaterStoreButton;

    private JButton myGreyWaterStoreButton;

    private JButton myDirtyWaterStoreButton;

    public WaterToolBar() {
        super("Water");
        myWaterRSButton = new JButton("Water RS");
        myPotableWaterStoreButton = new JButton("Potable Water Store");
        myGreyWaterStoreButton = new JButton("Grey Water Store");
        myDirtyWaterStoreButton = new JButton("Dirty Water Store");

        add(myWaterRSButton);
        addSeparator();
        add(myPotableWaterStoreButton);
        add(myGreyWaterStoreButton);
        add(myDirtyWaterStoreButton);
    }

}