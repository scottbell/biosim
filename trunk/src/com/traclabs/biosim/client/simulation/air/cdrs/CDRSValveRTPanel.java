package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus;

public class CDRSValveRTPanel extends GridButtonPanel {
	
	private int currentRow = 0;
	
	public CDRSValveRTPanel(){
		setName("CDRS Valve RT Status");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		ActionListener ventEnable = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2VentValveArmedStatus(CDRSCommandStatus.enabled);
			}
		};
		ActionListener ventInhibit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2VentValveArmedStatus(CDRSCommandStatus.inibited);
			}
		};
		StatusLabel ventStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(getCDRSCommandStatus(LssmViewer.getCDRSModule().getCO2VentValveArmedStatus()));
			}
		};
		addLine("CO2 Vent Valve", ventEnable, ventInhibit, ventStatusLabel);
		
		ActionListener isoEnable = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2IsolationValveArmedStatus(CDRSCommandStatus.enabled);
			}
		};
		ActionListener isoInhibit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2IsolationValveArmedStatus(CDRSCommandStatus.inibited);
			}
		};
		StatusLabel isoStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(getCDRSCommandStatus(LssmViewer.getCDRSModule().getCO2IsolationValveArmedStatus()));
			}
		};
		addLine("CO2 Isolation Valve", isoEnable, isoInhibit, isoStatusLabel);
		
		ActionListener airInletEnable = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setAirInletValveArmedStatus(CDRSCommandStatus.enabled);
			}
		};
		ActionListener airInletInhibit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setAirInletValveArmedStatus(CDRSCommandStatus.inibited);
			}
		};
		StatusLabel airInletStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(getCDRSCommandStatus(LssmViewer.getCDRSModule().getAirInletValveArmedStatus()));
			}
		};
		addLine("Air Inlet Valve", airInletEnable, airInletInhibit, airInletStatusLabel);
		
		ActionListener airReturnEnable = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setAirReturnValveArmedStatus(CDRSCommandStatus.enabled);
			}
		};
		ActionListener airReturnInhibit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setAirReturnValveArmedStatus(CDRSCommandStatus.inibited);
			}
		};
		StatusLabel airReturnStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(getCDRSCommandStatus(LssmViewer.getCDRSModule().getAirReturnValveArmedStatus()));
			}
		};
		addLine("Air Return Valve", airReturnEnable, airReturnInhibit, airReturnStatusLabel);
	}

	private void addLine(String lineName, ActionListener enableListener, ActionListener inhibitListener, StatusLabel statusLabel) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = currentRow;
		JLabel label = new JLabel(lineName);
		add(label, constraints);
		
		JButton enableButton = new JButton("Enable");
		enableButton.addActionListener(enableListener);
		constraints.gridx = 1;
		constraints.gridy = currentRow;
		add(enableButton, constraints);
		
		JButton inhibitButton = new JButton("Inhibit");
		inhibitButton.addActionListener(inhibitListener);
		constraints.gridx = 2;
		constraints.gridy = currentRow;
		add(inhibitButton, constraints);
		
		addUpdateable(statusLabel);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridx = 3;
		constraints.gridy = currentRow;
		add(statusLabel, constraints);
		
		currentRow++;
	}
	
}
