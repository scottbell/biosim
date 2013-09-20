package com.traclabs.biosim.client.simulation.air;

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
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.MethaneStore;
import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.air.O2Store;

/**
 * This is the JPanel that displays information about the AirRS, the O2 store,
 * and the CO2 Store. Each tick it polls each air related server for new
 * information regarding these systems.
 * 
 * @author Scott Bell
 */

public class AirTextPanel extends TimedPanel {
	// Various GUI componenets
	private JPanel airRSPanel;

	private JPanel O2StorePanel;

	private JLabel O2StoreLevelLabel;

	private JPanel CO2StorePanel;

	private JLabel CO2StoreLevelLabel;

	private JPanel H2StorePanel;

	private JLabel H2StoreLevelLabel;

	private JPanel nitrogenStorePanel;

	private JLabel nitrogenStoreLevelLabel;

	private JPanel methaneStorePanel;

	private JLabel methaneStoreLevelLabel;

	// Servers required for data polling
	private O2Store myO2Store;

	private CO2Store myCO2Store;

	private H2Store myH2Store;

	private NitrogenStore myNitrogenStore;

	private MethaneStore myMethaneStore;

	// For formatting floats
	private DecimalFormat numFormat;

	/**
	 * Creates and registers this panel.
	 */
	public AirTextPanel() {
		try {
			BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
			myO2Store = (myBioHolder.theO2Stores.get(0));
			myCO2Store = (myBioHolder.theCO2Stores.get(0));
			myH2Store = (myBioHolder.theH2Stores.get(0));
			myNitrogenStore = (myBioHolder.theNitrogenStores.get(0));
			myMethaneStore = (myBioHolder.theMethaneStores.get(0));
			buildGui();
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
	}

	/**
	 * Contructs GUI components, adds them to the panel.
	 */
	private void buildGui() {
		numFormat = new DecimalFormat("#,##0.00;(#)");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		airRSPanel = new JPanel();
		airRSPanel.setLayout(new GridLayout(7, 1));
		airRSPanel.setBorder(BorderFactory
				.createTitledBorder("Air Recovery System"));

		O2StorePanel = new JPanel();
		O2StorePanel.setLayout(new GridLayout(1, 1));
		O2StorePanel.setBorder(BorderFactory.createTitledBorder("O2 Store"));
		O2StoreLevelLabel = new JLabel("O2 level:    "
				+ numFormat.format(myO2Store.getCurrentLevel()) + " moles");
		O2StorePanel.add(O2StoreLevelLabel);

		CO2StorePanel = new JPanel();
		CO2StorePanel.setLayout(new GridLayout(1, 1));
		CO2StorePanel.setBorder(BorderFactory.createTitledBorder("CO2 Store"));
		CO2StoreLevelLabel = new JLabel("CO2 level:  "
				+ numFormat.format(myCO2Store.getCurrentLevel()) + " moles");
		CO2StorePanel.add(CO2StoreLevelLabel);

		H2StorePanel = new JPanel();
		H2StorePanel.setLayout(new GridLayout(1, 1));
		H2StorePanel.setBorder(BorderFactory.createTitledBorder("H2 Store"));
		H2StoreLevelLabel = new JLabel("H2 level:  "
				+ numFormat.format(myH2Store.getCurrentLevel()) + " moles");
		H2StorePanel.add(H2StoreLevelLabel);

		nitrogenStorePanel = new JPanel();
		nitrogenStorePanel.setLayout(new GridLayout(1, 1));
		nitrogenStorePanel.setBorder(BorderFactory
				.createTitledBorder("Nitrogen Store"));
		nitrogenStoreLevelLabel = new JLabel("Nitrogen level:  "
				+ numFormat.format(myNitrogenStore.getCurrentLevel())
				+ " moles");
		nitrogenStorePanel.add(nitrogenStoreLevelLabel);

		methaneStorePanel = new JPanel();
		methaneStorePanel.setLayout(new GridLayout(1, 1));
		methaneStorePanel.setBorder(BorderFactory
				.createTitledBorder("Methane Store"));
		methaneStoreLevelLabel = new JLabel("Methane level:  "
				+ numFormat.format(myMethaneStore.getCurrentLevel()) + " moles");
		methaneStorePanel.add(methaneStoreLevelLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(airRSPanel, c);
		add(airRSPanel);
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(O2StorePanel, c);
		add(O2StorePanel);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(CO2StorePanel, c);
		add(CO2StorePanel);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(H2StorePanel, c);
		add(H2StorePanel);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(nitrogenStorePanel, c);
		add(nitrogenStorePanel);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(methaneStorePanel, c);
		add(methaneStorePanel);
	}

	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void refresh() {
		try {
			CO2StoreLevelLabel
					.setText("CO2 level:      "
							+ numFormat.format(myCO2Store.getCurrentLevel())
							+ " moles");
			H2StoreLevelLabel.setText("H2 level:      "
					+ numFormat.format(myH2Store.getCurrentLevel()) + " moles");
			nitrogenStoreLevelLabel.setText("Nitrogen level:      "
					+ numFormat.format(myNitrogenStore.getCurrentLevel())
					+ " moles");
			O2StoreLevelLabel.setText("O2 level:       "
					+ numFormat.format(myO2Store.getCurrentLevel()) + " moles");
			methaneStoreLevelLabel.setText("Methane level:       "
					+ numFormat.format(myMethaneStore.getCurrentLevel())
					+ " moles");
		} catch (NullPointerException e) {
			// do nothing.
		}
	}
}