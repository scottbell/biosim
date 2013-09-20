package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus;

public class LssmArRtStatus extends GridButtonPanel {
	public LssmArRtStatus(){
		setName("LSSM AR RT Status");
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		JLabel blowerLabel = new JLabel("CDRS Blower LSSMB1");
		add(blowerLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		StatusLabel blowerStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(getCDRSCommandStatus(LssmViewer.getCDRSModule().getBlowerArmedStatus()));
			}
		};
		add(blowerStatusLabel, constraints);
		addUpdateable(blowerStatusLabel);

		constraints.gridx = 2;
		constraints.gridy = 0;
		JButton enableBlowerButton = new JButton("Enable");
		enableBlowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setBlowerArmedStatus(CDRSCommandStatus.enabled);
				LssmViewer.sendCommand("blowerEnableCommand");
			}
		});
		add(enableBlowerButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		JButton inhibitBlowerButton = new JButton("Inhibit");
		inhibitBlowerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setBlowerArmedStatus(CDRSCommandStatus.inibited);
				LssmViewer.sendCommand("blowerInhibitCommand");
			}
		});
		add(inhibitBlowerButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		JLabel pumpLabel = new JLabel("CDRS Pump LSSMP1");
		add(pumpLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		StatusLabel pumpStatusLabel = new StatusLabel() {
			public void refresh() {
				setText(getCDRSCommandStatus(LssmViewer.getCDRSModule().getWaterPumpArmedStatus()));
			}
		};
		add(pumpStatusLabel, constraints);
		addUpdateable(pumpStatusLabel);

		constraints.gridx = 2;
		constraints.gridy = 1;
		JButton enablePumpButton = new JButton("Enable");
		enablePumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setWaterPumpArmedStatus(CDRSCommandStatus.enabled);
				LssmViewer.sendCommand("waterPumpEnableCommand");
			}
		});
		add(enablePumpButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 1;
		JButton inhibitPumpButton = new JButton("Inhibit");
		inhibitPumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setWaterPumpArmedStatus(CDRSCommandStatus.inibited);
				LssmViewer.sendCommand("waterPumpInhibitCommand");
			}
		});
		add(inhibitPumpButton, constraints);
	}

}
