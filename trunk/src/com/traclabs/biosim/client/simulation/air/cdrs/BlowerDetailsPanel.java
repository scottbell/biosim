package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.traclabs.biosim.client.util.BioHolderInitializer;

public class BlowerDetailsPanel extends GridButtonPanel {

	public BlowerDetailsPanel() {
		setName("Blower LSSMB1 Details");

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;

		JLabel flowRateName = new JLabel("Blower Speed: ");
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(flowRateName, constraints);
		StatusLabel flowRateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getCDRSModule().getAirConsumerDefinition().getDesiredFlowRate(0) + "");
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 0;
		flowRateLabel.setBorder(BorderFactory.createEtchedBorder());
		add(flowRateLabel, constraints);
		addUpdateable(flowRateLabel);
		
		JButton speedButton = new JButton("Set Speed");
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(speedButton, constraints);
		JTextField flowRateTextField = new JTextField();
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(flowRateTextField, constraints);
		speedButton.addActionListener(new SpeedActionListener(flowRateTextField));

		JLabel blowerStateName = new JLabel("Blower State: ");
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(blowerStateName, constraints);
		StatusLabel blowerStateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getPowerState(BioHolderInitializer
						.getBioHolder().theCDRSModules.get(0).getBlowerState()));
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 2;
		blowerStateLabel.setBorder(BorderFactory.createEtchedBorder());
		add(blowerStateLabel, constraints);
		addUpdateable(blowerStateLabel);
	}
	
	private class SpeedActionListener implements ActionListener {
		private JTextField myFlowRateTextField;
		
		public SpeedActionListener(JTextField flowRateTextField) {
			this.myFlowRateTextField = flowRateTextField;
		}

		public void actionPerformed(ActionEvent e) {
			String flowRateText = myFlowRateTextField.getSelectedText();
			float flowRate = Float.parseFloat(flowRateText);
			LssmViewer.getCDRSModule().getAirConsumerDefinition().setDesiredFlowRate(flowRate, 0);
		}

	}
}
