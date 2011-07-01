package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus;

public class CDRSValveRTPanel extends GridButtonPanel {
	
	public CDRSValveRTPanel(){
		setName("CDRS Valve RT Status");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		ActionListener ventEnable = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2VentValveArmedStatus(CDRSCommandStatus.enabled);
			}
		};
		ActionListener ventInhibit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setCO2VentValveArmedStatus(CDRSCommandStatus.enabled);
			}
		};
		StatusLabel ventStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getCDRSModule().getCO2VentValveArmedStatus().toString());
			}
		};
		
		
		
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		JLabel co2VentLabel = new JLabel("CO2 Vent Valve");
		add(co2VentLabel, constraints);
		
		JButton co2VentEnableButton = new JButton("Enable");
		
	}
	
}
