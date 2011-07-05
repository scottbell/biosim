package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState;

public class CO2IsoValvePanel extends GridButtonPanel {
	public CO2IsoValvePanel(){
		setName("CO2 Isolation Valve");

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
				setText(LssmViewer.getCDRSValveState(LssmViewer.getCDRSModule().getCO2IsolationValveState()));
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
				LssmViewer.getCDRSModule().setCO2IsolationValveState(CDRSValveState.open);
			}
		};
		JButton openValveButton = new JButton("Open");
		openValveButton.addActionListener(openValveListener);
		add(openValveButton, constraints);

		constraints.gridx = 3;
		constraints.gridy = 0;
		ActionListener closeValveListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2IsolationValveState(CDRSValveState.closed);
			}
		};
		JButton closeValveButton = new JButton("Close");
		closeValveButton.addActionListener(closeValveListener);
		add(closeValveButton, constraints);
	}
}
