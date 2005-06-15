package com.traclabs.biosim.client.simulation.power;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.traclabs.biosim.client.framework.gui.TimedPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.power.PowerPS;
import com.traclabs.biosim.idl.simulation.power.PowerStore;

/**
 * This is the JPanel that displays information about the Power Production
 * System and it's store. Each tick it polls each power related server for new
 * information regarding these systems.
 * 
 * @author Scott Bell
 */

public class PowerTextPanel extends TimedPanel {
    //Various GUI componenets
    private JPanel powerPSPanel;

    private JLabel powerPSPowerProducedLabel;

    private JPanel powerStorePanel;

    private JLabel powerStoreLevelLabel;

    //Servers required for data polling
    private PowerPS myPowerPS;

    private PowerStore myPowerStore;

    //For formatting floats
    private DecimalFormat numFormat;

    /**
     * Creates and registers this panel.
     */
    public PowerTextPanel() {
        BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
        myPowerPS = (PowerPS) (myBioHolder.thePowerPSModules.get(0));
        myPowerStore = (PowerStore) (myBioHolder.thePowerStores.get(0));
        buildGui();
    }

    /**
     * Contructs GUI components, adds them to the panel.
     */
    private void buildGui() {
        numFormat = new DecimalFormat("#,##0.00;(#)");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);

        powerPSPanel = new JPanel();
        powerPSPanel.setLayout(new GridLayout(1, 1));
        powerPSPanel.setBorder(BorderFactory
                .createTitledBorder("Power Production System"));
        powerPSPowerProducedLabel = new JLabel("power produced:         "
                + numFormat.format(myPowerPS.getPowerProduced()) + " W");
        powerPSPanel.add(powerPSPowerProducedLabel);

        powerStorePanel = new JPanel();
        powerStorePanel.setLayout(new GridLayout(1, 1));
        powerStorePanel.setBorder(BorderFactory
                .createTitledBorder("Power Store"));
        powerStoreLevelLabel = new JLabel("power level:    "
                + numFormat.format(myPowerStore.getCurrentLevel()) + " W");
        powerStorePanel.add(powerStoreLevelLabel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(powerPSPanel, c);
        add(powerPSPanel);

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty = 1.0;
        gridbag.setConstraints(powerStorePanel, c);
        add(powerStorePanel);
    }

    /**
     * Updates every label on the panel with new data pulled from the servers.
     */
    public void refresh() {
        powerPSPowerProducedLabel.setText("power produced:         "
                + numFormat.format(myPowerPS.getPowerProduced()) + " W");
        powerStoreLevelLabel.setText("power level:    "
                + numFormat.format(myPowerStore.getCurrentLevel()) + " W");
    }
}