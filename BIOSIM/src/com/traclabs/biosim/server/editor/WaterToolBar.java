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
public class WaterToolBar extends EditorToolBar {
    private JButton myWaterRSButton;

    private JButton myPotableWaterStoreButton;

    private JButton myGreyWaterStoreButton;

    private JButton myDirtyWaterStoreButton;

    private JButton myConduitButton;

    public WaterToolBar(BiosimEditor pEditor) {
        super("Water");
        myWaterRSButton = new JButton("Water RS");
        myPotableWaterStoreButton = new JButton("Potable Water Store");
        myGreyWaterStoreButton = new JButton("Grey Water Store");
        myDirtyWaterStoreButton = new JButton("Dirty Water Store");
        myConduitButton = new JButton("Conduit");

        add(myWaterRSButton);
        addSeparator();
        add(myPotableWaterStoreButton);
        add(myGreyWaterStoreButton);
        add(myDirtyWaterStoreButton);
        addSeparator();
        add(myConduitButton);
    }

}