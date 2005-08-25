package com.traclabs.biosim.client.simulation.food;

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
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.FoodProcessor;
import com.traclabs.biosim.idl.simulation.food.FoodStore;

/**
 * This is the JPanel that displays information about the Food Processor, the
 * Biomass RS, and their respective stores. Each tick it polls each related
 * server for new information regarding these systems.
 * 
 * @author Scott Bell
 */

public class FoodTextPanel extends TimedPanel {
	// Various GUI componenets
	private JPanel biomassRSPanel;

	private JPanel biomassStorePanel;

	private JLabel biomassStoreLevelLabel;

	private JPanel foodProcessorPanel;

	private JLabel foodProcessorStatusLabel;

	private JLabel foodProcessorPowerLabel;

	private JLabel foodProcessorBiomassConsumedLabel;

	private JLabel foodProcessorFoodProducedLabel;

	private JPanel foodStorePanel;

	private JLabel foodStoreLevelLabel;

	// Servers required for data polling
	private BiomassStore myBiomassStore;

	private FoodProcessor myFoodProcessor;

	private FoodStore myFoodStore;

	// For formatting floats
	private DecimalFormat numFormat;

	/**
	 * Creates and registers this panel.
	 */
	public FoodTextPanel() {
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		myBiomassStore = (myBioHolder.theBiomassStores.get(0));
		myFoodProcessor = (myBioHolder.theFoodProcessors.get(0));
		myFoodStore = (myBioHolder.theFoodStores.get(0));
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

		biomassRSPanel = new JPanel();
		biomassRSPanel.setBorder(BorderFactory
				.createTitledBorder("Biomass Recovery System"));

		biomassStorePanel = new JPanel();
		biomassStorePanel.setLayout(new GridLayout(1, 1));
		biomassStorePanel.setBorder(BorderFactory
				.createTitledBorder("Biomass Store"));
		biomassStoreLevelLabel = new JLabel("biomass level:    "
				+ numFormat.format(myBiomassStore.getCurrentLevel()) + " kg");
		biomassStorePanel.add(biomassStoreLevelLabel);

		foodProcessorPanel = new JPanel();
		foodProcessorPanel.setLayout(new GridLayout(4, 1));
		foodProcessorPanel.setBorder(BorderFactory
				.createTitledBorder("Food Processor"));
		foodProcessorStatusLabel = new JLabel("status:                       "
				+ coallateFoodProcessorStatus());
		foodProcessorFoodProducedLabel = new JLabel("food produced:          "
				+ numFormat.format(myFoodProcessor.getFoodProduced()) + " kg");
		foodProcessorBiomassConsumedLabel = new JLabel("biomass consumed:    "
				+ numFormat.format(myFoodProcessor.getBiomassConsumed())
				+ " kg");
		foodProcessorPowerLabel = new JLabel("power consumed:      "
				+ numFormat.format(myFoodProcessor.getPowerConsumed()) + " W");
		foodProcessorPanel.add(foodProcessorStatusLabel);
		foodProcessorPanel.add(foodProcessorFoodProducedLabel);
		foodProcessorPanel.add(foodProcessorBiomassConsumedLabel);
		foodProcessorPanel.add(foodProcessorPowerLabel);

		foodStorePanel = new JPanel();
		foodStorePanel.setLayout(new GridLayout(1, 1));
		foodStorePanel
				.setBorder(BorderFactory.createTitledBorder("Food Store"));
		foodStoreLevelLabel = new JLabel("food level:    "
				+ numFormat.format(myFoodStore.getCurrentLevel()) + " kg");
		foodStorePanel.add(foodStoreLevelLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(biomassRSPanel, c);

		add(biomassRSPanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(biomassStorePanel, c);
		add(biomassStorePanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(foodProcessorPanel, c);
		add(foodProcessorPanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(foodStorePanel, c);
		add(foodStorePanel);
	}

	/**
	 * Checks the status of Food Processor and constructs a string describing
	 * it.
	 * 
	 * @return A String representing the status of the Food Processor
	 */
	private String coallateFoodProcessorStatus() {
		StringBuffer statusBuffer = new StringBuffer();
		if (!myFoodProcessor.hasPower())
			statusBuffer.append("needs power, ");
		if (!myFoodProcessor.hasBiomass())
			statusBuffer.append("needs biomass, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		statusBuffer.delete(statusBuffer.length() - 2, statusBuffer.length());
		return statusBuffer.toString();
	}

	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void refresh() {
		foodProcessorFoodProducedLabel.setText("food produced:          "
				+ numFormat.format(myFoodProcessor.getFoodProduced()) + " kg");
		foodProcessorBiomassConsumedLabel.setText("biomass consumed:   "
				+ numFormat.format(myFoodProcessor.getBiomassConsumed())
				+ " kg");
		foodProcessorPowerLabel.setText("power consumed:      "
				+ numFormat.format(myFoodProcessor.getPowerConsumed()) + " W");
		foodStoreLevelLabel.setText("food level:    "
				+ numFormat.format(myFoodStore.getCurrentLevel()) + " kg");
		biomassStoreLevelLabel.setText("biomass level:    "
				+ numFormat.format(myBiomassStore.getCurrentLevel()) + " kg");
		foodProcessorStatusLabel.setText("status:                       "
				+ coallateFoodProcessorStatus());
	}
}