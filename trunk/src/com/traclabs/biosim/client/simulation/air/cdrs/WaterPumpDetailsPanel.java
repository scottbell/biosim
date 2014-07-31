package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.traclabs.biosim.client.util.BioHolderInitializer;

public class WaterPumpDetailsPanel extends GridButtonPanel {
	public WaterPumpDetailsPanel(){
		setName("Water Pump Details");

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		
		JLabel flowRateName = new JLabel("Flow Rate: ");
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(flowRateName, constraints); 
		StatusLabel flowRateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getCDRSModule().getGreyWaterConsumerDefinition().getDesiredFlowRate(0) + "");
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 0;
		flowRateLabel.setBorder(BorderFactory.createEtchedBorder());
		add(flowRateLabel, constraints);
		addUpdateable(flowRateLabel);
		
		JLabel pumpStateName = new JLabel("Pump State: ");
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(pumpStateName, constraints); 
		StatusLabel pumpStateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getPowerState(BioHolderInitializer.getBioHolder().theCDRSModules.get(0).getWaterPumpState()));
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 1;
		pumpStateLabel.setBorder(BorderFactory.createEtchedBorder());
		add(pumpStateLabel, constraints);
		addUpdateable(pumpStateLabel);
	}

}
