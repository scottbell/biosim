package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState;

public class CO2VentValvePanel extends GridButtonPanel {
	public CO2VentValvePanel(){
		setName("CO2 Vent Valve");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		
		JLabel valveStateName = new JLabel("Valve Position: ");
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(valveStateName, constraints); 
		StatusLabel valveStateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getCDRSValveState(LssmViewer.getCDRSModule().getCO2VentValveState()));
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 0;
		valveStateLabel.setBorder(BorderFactory.createEtchedBorder());
		add(valveStateLabel, constraints);
		addUpdateable(valveStateLabel);

		constraints.gridx = 2;
		constraints.gridy = 0;
		ActionListener openValveListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2VentValveState(CDRSValveState.open);
				LssmViewer.sendCommand("co2VentOpenCommand");
			}
		};
		JButton openValveButton = new JButton("Open");
		openValveButton.addActionListener(openValveListener);
		add(openValveButton, constraints);

		constraints.gridx = 3;
		constraints.gridy = 0;
		ActionListener closeValveListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2VentValveState(CDRSValveState.closed);
				LssmViewer.sendCommand("co2VentCloseCommand");
			}
		};
		JButton closeValveButton = new JButton("Close");
		closeValveButton.addActionListener(closeValveListener);
		add(closeValveButton, constraints);
	}
}
