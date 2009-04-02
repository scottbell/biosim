package com.traclabs.biosim.client.simulation.food;

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
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.food.BiomassPS;
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
	private JPanel biomassPSPanel;

	private List<JLabel> biomassPSlLabels = new Vector<JLabel>();

	private JPanel biomassStorePanel;

	private List<JLabel> biomassStoreLabels = new Vector<JLabel>();

	private JPanel foodProcessorPanel;

	private List<JLabel> foodProcessorLabels = new Vector<JLabel>();

	private JPanel foodStorePanel;

	private List<JLabel> foodStoreLabels = new Vector<JLabel>();

	// For formatting floats
	private DecimalFormat numFormat;

	/**
	 * Creates and registers this panel.
	 */
	public FoodTextPanel() {
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
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

		biomassPSPanel = new JPanel();
		biomassPSPanel.setBorder(BorderFactory
				.createTitledBorder("Biomass Production System"));
		List<BiomassPS> biomassPSs = BioHolderInitializer.getBioHolder().theBiomassPSModules;
		biomassPSPanel.setLayout(new GridLayout(biomassPSs.size(), 1));
		for (BiomassPS biomassPS : biomassPSs) {
			JLabel biomassSystemLabel = new JLabel();
			biomassSystemLabel.setText(biomassPS.getModuleName() + " produced: "
					+ numFormat.format(biomassPS.getBiomassProducerDefinition().getActualFlowRate(0)) + " kg");
			biomassPSPanel.add(biomassSystemLabel);
			biomassPSlLabels.add(biomassSystemLabel);
		}
		
		biomassStorePanel = new JPanel();
		biomassStorePanel.setBorder(BorderFactory
				.createTitledBorder("biomass Stores"));
		List<BiomassStore> biomassStores = BioHolderInitializer.getBioHolder().theBiomassStores;
		biomassStorePanel.setLayout(new GridLayout(biomassStores.size(), 1));
		for (BiomassStore biomassStore : biomassStores) {
			JLabel biomassStoreLabel = new JLabel();
			biomassStoreLabel.setText(biomassStore.getModuleName() + " level: "
					+ numFormat.format(biomassStore.getCurrentLevel()) + " kg");
			biomassStorePanel.add(biomassStoreLabel);
			biomassStoreLabels.add(biomassStoreLabel);
		}
		
		foodProcessorPanel = new JPanel();
		foodProcessorPanel.setBorder(BorderFactory
				.createTitledBorder("Biomass Production System"));
		List<FoodProcessor> foodProcessors = BioHolderInitializer.getBioHolder().theFoodProcessors;
		foodProcessorPanel.setLayout(new GridLayout(foodProcessors.size(), 1));
		for (FoodProcessor foodProcessor : foodProcessors) {
			JLabel biomassSystemLabel = new JLabel();
			biomassSystemLabel.setText(foodProcessor.getModuleName() + " produced: "
					+ numFormat.format(foodProcessor.getFoodProduced()) + " kg");
			foodProcessorPanel.add(biomassSystemLabel);
			foodProcessorLabels.add(biomassSystemLabel);
		}
		
		
		foodStorePanel = new JPanel();
		foodStorePanel.setBorder(BorderFactory
				.createTitledBorder("food Stores"));
		List<FoodStore> foodStores = BioHolderInitializer.getBioHolder().theFoodStores;
		foodStorePanel.setLayout(new GridLayout(foodStores.size(), 1));
		for (FoodStore foodStore : foodStores) {
			JLabel foodStoreLabel = new JLabel();
			foodStoreLabel.setText(foodStore.getModuleName() + " level: "
					+ numFormat.format(foodStore.getCurrentLevel()) + " kg");
			foodStorePanel.add(foodStoreLabel);
			foodStoreLabels.add(foodStoreLabel);
		}

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(biomassPSPanel, c);

		add(biomassPSPanel);

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
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void refresh() {
		for (int i = 0; i < biomassPSlLabels.size(); i++){
			JLabel biomassPSLabel = biomassPSlLabels.get(i);
			BiomassPS biomassSystem = BioHolderInitializer.getBioHolder().theBiomassPSModules.get(i);
			biomassPSLabel.setText(biomassSystem.getModuleName() + " produced: "
					+ numFormat.format(biomassSystem.getBiomassProducerDefinition().getActualFlowRate(0)) + " kg");
		}
		for (int i = 0; i < biomassStoreLabels.size(); i++){
			JLabel biomassStoreLabel = biomassStoreLabels.get(i);
			BiomassStore biomassStore = BioHolderInitializer.getBioHolder().theBiomassStores.get(i);
			biomassStoreLabel.setText(biomassStore.getModuleName() + " level: "
					+ numFormat.format(biomassStore.getCurrentLevel()) + " kg");
		}
		for (int i = 0; i < foodProcessorLabels.size(); i++){
			JLabel foodProcessorLabel = foodProcessorLabels.get(i);
			FoodProcessor foodSystem = BioHolderInitializer.getBioHolder().theFoodProcessors.get(i);
			foodProcessorLabel.setText(foodSystem.getModuleName() + " produced: "
					+ numFormat.format(foodSystem.getFoodProducerDefinition().getActualFlowRate(0)) + " kg");
		}
		for (int i = 0; i < foodStoreLabels.size(); i++){
			JLabel foodStoreLabel = foodStoreLabels.get(i);
			FoodStore foodStore = BioHolderInitializer.getBioHolder().theFoodStores.get(i);
			foodStoreLabel.setText(foodStore.getModuleName() + " level: "
					+ numFormat.format(foodStore.getCurrentLevel()) + " kg");
		}
	}
}