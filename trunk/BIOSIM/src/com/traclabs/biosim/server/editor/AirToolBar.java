/*
 * Created on Jan 26, 2005
 */
package com.traclabs.biosim.server.editor;

import javax.swing.JButton;


/**
 * @author scott
 */
public class AirToolBar extends EditorToolBar {
    private JButton myH2StoreButton;
    private JButton myNitrogenStoreButton;
    private JButton myCH4StoreButton;
    private JButton myO2StoreButton;
    private JButton myCO2StoreButton;
    private JButton myCRSButton;
    private JButton myOGSButton;
    private JButton myVCCRButton;
    private JButton myAirRSButton;
    
    private JButton myO2ConduitButton;
    
    public AirToolBar() {
        super("Air");
        myH2StoreButton = new JButton("H2 Store");
        myNitrogenStoreButton = new JButton("N2 Store");
        myCH4StoreButton = new JButton("CH4 Store");
        myO2StoreButton = new JButton("O2 Store");
        myCO2StoreButton = new JButton("CO2 Store");
        myCRSButton = new JButton("CRS");
        myOGSButton = new JButton("OGS");
        myVCCRButton = new JButton("VCCR");
        myAirRSButton = new JButton("AirRS");
        myO2ConduitButton = new JButton("O2 Conduit");
        
        add(myH2StoreButton);
        add(myNitrogenStoreButton);
        add(myCH4StoreButton);
        add(myO2StoreButton);
        add(myCO2StoreButton);
        addSeparator();
        add(myCRSButton);
        add(myOGSButton);
        add(myVCCRButton);
        add(myAirRSButton);
        addSeparator();
        add(myO2ConduitButton);
    }
}
