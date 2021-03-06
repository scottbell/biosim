package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState;

public class CDRSDetailPanel extends GridButtonPanel {
	private int currentRow = 0;
	
	public CDRSDetailPanel(){
		setName("CDRS Details");
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		JLabel statusName = new JLabel("Status: ");
		add(statusName, constraints);
		
		StatusLabel statusLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getArmedStatus(LssmViewer.getCDRSModule().getArmedStatus()));
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 0;
		statusLabel.setBorder(BorderFactory.createEtchedBorder());
		add(statusLabel, constraints);
		addUpdateable(statusLabel);
		
		ActionListener armListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setArmedStatus(CDRSArmedStatus.armed);
				LssmViewer.sendCommand("lssmCdrsArmCommand");
			}
		};
		JButton armedButton = new JButton("Arm");
		armedButton.addActionListener(armListener);
		constraints.gridx = 2;
		constraints.gridy = 0;
		add(armedButton, constraints);

		JLabel stateName = new JLabel("State: ");
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(stateName, constraints);
		
		StatusLabel stateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getCDRSState(LssmViewer.getCDRSModule().getState()));
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 1;
		stateLabel.setBorder(BorderFactory.createEtchedBorder());
		add(stateLabel, constraints);
		addUpdateable(stateLabel);
		
		ActionListener startupListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setState(CDRSState.init);
				LssmViewer.sendCommand("cdrsInitCommand");
			}
		};
		JButton startupButton = new JButton("Init");
		startupButton.addActionListener(startupListener);
		constraints.gridx = 2;
		constraints.gridy = 1;
		add(startupButton, constraints);
		
		ActionListener standbyListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setState(CDRSState.standby);
				LssmViewer.sendCommand("cdrsStandbyCommand");
			}
		};
		JButton standbyButton = new JButton("Standby");
		standbyButton.addActionListener(standbyListener);
		constraints.gridx = 3;
		constraints.gridy = 1;
		add(standbyButton, constraints);
		
		ActionListener dualBedListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setState(CDRSState.dual_bed);
				LssmViewer.sendCommand("cdrsDoubleBedCommand");
			}
		};
		JButton dualBedButton = new JButton("Dual Bed Ops");
		dualBedButton.addActionListener(dualBedListener);
		constraints.gridx = 4;
		constraints.gridy = 1;
		add(dualBedButton, constraints);
		
		ActionListener singleBedListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setState(CDRSState.single_bed);
				LssmViewer.sendCommand("cdrsSingleBedCommand");
			}
		};
		JButton singleBedButton = new JButton("Single Bed Ops");
		singleBedButton.addActionListener(singleBedListener);
		constraints.gridx = 5;
		constraints.gridy = 1;
		add(singleBedButton, constraints);
		
		ActionListener stopListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setState(CDRSState.inactive);
				LssmViewer.sendCommand("cdrsInactiveCommand");
			}
		};
		JButton stopButton = new JButton("Inactive");
		stopButton.addActionListener(stopListener);
		constraints.gridx = 6;
		constraints.gridy = 1;
		add(stopButton, constraints);
		

		
		JLabel dayNightLabel = new JLabel("Day/Night Indicator: ");
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(dayNightLabel, constraints);
		
		StatusLabel dayNightStateLabel = new StatusLabel() {
			public void refresh() {
				setText(LssmViewer.getDayNight(LssmViewer.getCDRSModule().getDayNightState()));
			}
		};
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(dayNightStateLabel, constraints);
		addUpdateable(dayNightStateLabel);
		
		ActionListener dayListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setDayNightState(CDRSDayNightState.day);
			}
		};
		JButton dayButton = new JButton("Day");
		dayButton.addActionListener(dayListener);
		constraints.gridx = 2;
		constraints.gridy = 2;
		add(dayButton, constraints);
		
		ActionListener nightListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LssmViewer.getCDRSModule().setDayNightState(CDRSDayNightState.night);
			}
		};
		JButton nightButton = new JButton("Night");
		nightButton.addActionListener(nightListener);
		constraints.gridx = 3;
		constraints.gridy = 2;
		add(nightButton, constraints);
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
