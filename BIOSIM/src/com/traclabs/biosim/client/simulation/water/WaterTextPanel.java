package com.traclabs.biosim.client.simulation.water;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;

/**
 * This is the JPanel that displays information about the Water RS and the water
 * stores. Each tick it polls each water related server for new information
 * regarding these systems.
 * 
 * @author Scott Bell
 */

public class WaterTextPanel extends TimedPanel {
    //Various GUI componenets
    private JPanel waterRSPanel;

    private JPanel waterRSLevelPanel;

    private JPanel waterRSStatusPanel;

    private JPanel potableWaterStorePanel;

    private JLabel potableWaterStoreLevelLabel;

    private JPanel greyWaterStorePanel;

    private JLabel greyWaterStoreLevelLabel;

    private JPanel dirtyWaterStorePanel;

    private JLabel dirtyWaterStoreLevelLabel;

    private PotableWaterStore myPotableWaterStore;

    private GreyWaterStore myGreyWaterStore;

    private DirtyWaterStore myDirtyWaterStore;

    //For formatting floats
    private DecimalFormat numFormat;

    /**
     * Creates and registers this panel.
     */
    public WaterTextPanel() {
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        myPotableWaterStore = (myBioHolder.thePotableWaterStores
                .get(0));
        myDirtyWaterStore = (myBioHolder.theDirtyWaterStores
                .get(0));
        myGreyWaterStore = (myBioHolder.theGreyWaterStores
                .get(0));
        buildGui();
    }

    /**
     * Contructs main GUI components, adds them to the panel.
     */
    private void buildGui() {
        numFormat = new DecimalFormat("#,##0.00;(#)");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);

        createWaterRSPanel();
        createDirtyWaterStorePanel();
        createGreyWaterStorePanel();
        createPotableWaterStorePanel();

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(waterRSPanel, c);
        add(waterRSPanel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(potableWaterStorePanel, c);
        add(potableWaterStorePanel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(greyWaterStorePanel, c);
        add(greyWaterStorePanel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(dirtyWaterStorePanel, c);
        add(dirtyWaterStorePanel);
    }

    /**
     * Contructs WaterRS GUI components, adds them to the WaterRS panel.
     */
    private void createWaterRSPanel() {
        waterRSPanel = new JPanel();
        waterRSPanel.setBorder(BorderFactory
                .createTitledBorder("Water Recovery System"));
        createWaterRSLevelPanel();
        createWaterRSStatusPanel();

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        waterRSPanel.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(waterRSStatusPanel, c);
        waterRSPanel.add(waterRSStatusPanel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(waterRSLevelPanel, c);
        waterRSPanel.add(waterRSLevelPanel);
    }

    /**
     * Contructs WaterRS water levels GUI components, adds them to the WaterRS
     * level panel.
     */
    private void createWaterRSLevelPanel() {
        waterRSLevelPanel = new JPanel();
    }

    /**
     * Contructs WaterRS status GUI components, adds them to the WaterRS status
     * panel.
     */
    private void createWaterRSStatusPanel() {
        waterRSStatusPanel = new JPanel();
    }

    /**
     * Contructs dirty water store GUI components, adds them to the dirty water
     * store panel.
     */
    private void createDirtyWaterStorePanel() {
        dirtyWaterStorePanel = new JPanel();
        dirtyWaterStorePanel.setLayout(new GridLayout(1, 1));
        dirtyWaterStorePanel.setBorder(BorderFactory
                .createTitledBorder("Dirty Water Store"));
        dirtyWaterStoreLevelLabel = new JLabel("water level:    "
                + numFormat.format(myDirtyWaterStore.getCurrentLevel()) + " L");
        dirtyWaterStorePanel.add(dirtyWaterStoreLevelLabel);
    }

    /**
     * Contructs grey water store GUI components, adds them to the grey water
     * store panel.
     */
    private void createGreyWaterStorePanel() {
        greyWaterStorePanel = new JPanel();
        greyWaterStorePanel.setLayout(new GridLayout(1, 1));
        greyWaterStorePanel.setBorder(BorderFactory
                .createTitledBorder("Grey Water Store"));
        greyWaterStoreLevelLabel = new JLabel("water level:    "
                + numFormat.format(myGreyWaterStore.getCurrentLevel()) + " L");
        greyWaterStorePanel.add(greyWaterStoreLevelLabel);
    }

    /**
     * Contructs potable water store GUI components, adds them to the potable
     * water store panel.
     */
    private void createPotableWaterStorePanel() {
        potableWaterStorePanel = new JPanel();
        potableWaterStorePanel.setLayout(new GridLayout(1, 1));
        potableWaterStorePanel.setBorder(BorderFactory
                .createTitledBorder("Potable Water Store"));
        potableWaterStoreLevelLabel = new JLabel("water level:    "
                + numFormat.format(myPotableWaterStore.getCurrentLevel())
                + " L");
        potableWaterStorePanel.add(potableWaterStoreLevelLabel);
    }

    /**
     * Updates every label on the panel with new data pulled from the servers.
     */
    public void refresh() {
        potableWaterStoreLevelLabel.setText("water level:    "
                + numFormat.format(myPotableWaterStore.getCurrentLevel())
                + " L");
        greyWaterStoreLevelLabel.setText("water level:    "
                + numFormat.format(myGreyWaterStore.getCurrentLevel()) + " L");
        dirtyWaterStoreLevelLabel.setText("water level:    "
                + numFormat.format(myDirtyWaterStore.getCurrentLevel()) + " L");
    }
}