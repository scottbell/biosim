/*
 * Created on Jan 26, 2005
 */
package com.traclabs.biosim.server.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;


/**
 * @author scott
 */
public class AirToolBar extends EditorToolBar {
    private ButtonPanel myStorePanel;
    private ButtonPanel myModulesPanel;
    private ButtonPanel myConduitsPanel;
    
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
        myStorePanel = new ButtonPanel();
        myModulesPanel = new ButtonPanel();
        myConduitsPanel = new ButtonPanel();
        myStorePanel.setBorder(BorderFactory.createTitledBorder("Stores"));
        myModulesPanel.setBorder(BorderFactory.createTitledBorder("Modules"));
        myConduitsPanel.setBorder(BorderFactory.createTitledBorder("Conduits"));
        
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
        
        myStorePanel.add(myH2StoreButton);
        myStorePanel.add(myNitrogenStoreButton);
        myStorePanel.add(myCH4StoreButton);
        myStorePanel.add(myO2StoreButton);
        myStorePanel.add(myCO2StoreButton);
        myModulesPanel.add(myCRSButton);
        myModulesPanel.add(myOGSButton);
        myModulesPanel.add(myVCCRButton);
        myModulesPanel.add(myAirRSButton);
        myConduitsPanel.add(myO2ConduitButton);
        
        //do gridbag
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(gridbag);
        
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.weightx  = 1;
        constraints.weighty  = 1;
        gridbag.setConstraints(myModulesPanel, constraints);
        add(myModulesPanel);
        
        //constraints.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(myStorePanel, constraints);
        add(myStorePanel);
        
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(myConduitsPanel, constraints);
        add(myConduitsPanel);
    }
}
