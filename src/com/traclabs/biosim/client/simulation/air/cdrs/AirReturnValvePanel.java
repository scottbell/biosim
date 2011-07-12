package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState;

public class AirReturnValvePanel extends GridButtonPanel {
	public AirReturnValvePanel(){
		setName("Air Return Valve");
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
				setText(LssmViewer.getCDRSValveState(LssmViewer.getCDRSModule().getAirReturnValveState()));
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
				LssmViewer.getCDRSModule().setAirReturnValveState(CDRSValveState.open);
			}
		};
		JButton openValveButton = new JButton("Open");
		openValveButton.addActionListener(openValveListener);
		add(openValveButton, constraints);

		constraints.gridx = 3;
		constraints.gridy = 0;
		ActionListener closeValveListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setAirReturnValveState(CDRSValveState.closed);
			}
		};
		JButton closeValveButton = new JButton("Close");
		closeValveButton.addActionListener(closeValveListener);
		add(closeValveButton, constraints);
	}
}
