package com.traclabs.biosim.client.simulation.power;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.traclabs.biosim.client.framework.TimedPanel;
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
	// Various GUI componenets
	private JPanel powerPSPanel;

	private List<JLabel> powerSystemLabels = new Vector<JLabel>();

	private JPanel powerStorePanel;

	private List<JLabel> powerStoreLabels = new Vector<JLabel>();

	// For formatting floats
	private DecimalFormat numFormat;

	/**
	 * Creates and registers this panel.
	 */
	public PowerTextPanel() {
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
		powerPSPanel.setBorder(BorderFactory
				.createTitledBorder("Power Production Systems"));
		List<PowerPS> powerSystems = BioHolderInitializer.getBioHolder().thePowerPSModules;
		powerPSPanel.setLayout(new GridLayout(powerSystems.size(), 1));
		for (PowerPS powerPS : powerSystems) {
			JLabel powerSystemLabel = new JLabel();
			powerSystemLabel.setText(powerPS.getModuleName() + " produced: "
					+ numFormat.format(powerPS.getPowerProduced()) + " W");
			powerPSPanel.add(powerSystemLabel);
			powerSystemLabels.add(powerSystemLabel);
		}

		powerStorePanel = new JPanel();
		powerStorePanel.setBorder(BorderFactory
				.createTitledBorder("Power Stores"));
		List<PowerStore> powerStores = BioHolderInitializer.getBioHolder().thePowerStores;
		powerStorePanel.setLayout(new GridLayout(powerStores.size(), 1));
		for (PowerStore powerStore : powerStores) {
			JLabel powerStoreLabel = new JLabel();
			powerStoreLabel.setText(powerStore.getModuleName() + " level: "
					+ numFormat.format(powerStore.getCurrentLevel()) + " W");
			powerStorePanel.add(powerStoreLabel);
			powerStoreLabels.add(powerStoreLabel);
		}

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
		for (int i = 0; i < powerSystemLabels.size(); i++){
			JLabel powerSystemLabel = powerSystemLabels.get(i);
			PowerPS powerSystem = BioHolderInitializer.getBioHolder().thePowerPSModules.get(i);
			powerSystemLabel.setText(powerSystem.getModuleName() + " produced: "
					+ numFormat.format(powerSystem.getPowerProduced()) + " W");
		}
		for (int i = 0; i < powerStoreLabels.size(); i++){
			JLabel powerStoreLabel = powerStoreLabels.get(i);
			PowerStore powerStore = BioHolderInitializer.getBioHolder().thePowerStores.get(i);
			powerStoreLabel.setText(powerStore.getModuleName() + " level: "
					+ numFormat.format(powerStore.getCurrentLevel()) + " W");
		}
	}
}