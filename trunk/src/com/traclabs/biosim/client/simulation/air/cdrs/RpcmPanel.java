package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.power.RPCM;


public abstract class RpcmPanel extends GridButtonPanel {
	private String[] myNames = null;
	private int myNameIndex = 0;

	public RpcmPanel(String title, int lowIndex, int highIndex){
		this(title, lowIndex, highIndex, null);
	}
	public RpcmPanel(String title, int lowIndex, int highIndex, String[] names) {
		this.myNames = names;
		setName(title);
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		JLabel legendLabel = new JLabel();
		add(legendLabel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		JLabel positionLabel = new JLabel("Position");
		add(positionLabel, constraints);

		constraints.gridx = 2;
		constraints.gridy = 0;
		JLabel trippedLabel = new JLabel("Tripped");
		add(trippedLabel, constraints);

		constraints.gridx = 3;
		constraints.gridy = 0;
		JLabel openCommandLabel = new JLabel("Command Enabled");
		add(openCommandLabel, constraints);
		
		for (int i = lowIndex; i < highIndex; i++){
			RPCM currentRPCM = BioHolderInitializer.getBioHolder().theRPCMs.get(i);
			RpcPanel rpcPanel = new RpcPanel(currentRPCM);
			if (myNames != null){
				rpcPanel.setName(myNames[myNameIndex]);
				myNameIndex ++;
			}
			else
				rpcPanel.setName("RPC "+ (i + 1));
			addLine(i, rpcPanel, constraints);
		}
		myNameIndex = 0;
	}

	private void addLine(int index, RpcPanel rpcPanel, GridBagConstraints constraints) {
		RPCM currentRPCM = BioHolderInitializer.getBioHolder().theRPCMs.get(index);
		constraints.gridx = 0;
		constraints.gridy = index + 1;
		JButton rpcButton = new JButton();
		rpcButton.addActionListener(new LssmActionListener(rpcPanel));
		rpcButton.setText(rpcPanel.getName());
		add(rpcButton, constraints);
		addUpdateable(rpcPanel);

		constraints.gridx = 1;
		constraints.gridy = index + 1;
		RpcStatusLabel positionLabel = new RpcStatusLabel(rpcPanel.getRPCM()) {
			public void refresh() {
				setText(getSwitchState(getRPCM().getSwitchState()));
			}
		};
		add(positionLabel, constraints);
		addUpdateable(positionLabel);

		constraints.gridx = 2;
		constraints.gridy = index + 1;
		RpcStatusLabel trippedLabel = new RpcStatusLabel(rpcPanel.getRPCM()) {
			public void refresh() {
				setText("false");
			}
		};
		add(trippedLabel, constraints);
		addUpdateable(trippedLabel);

		constraints.gridx = 3;
		constraints.gridy = index + 1;
		RpcStatusLabel armedLabel = new RpcStatusLabel(rpcPanel.getRPCM()) {
			public void refresh() {
				setText(getArmedStatus(getRPCM().getArmedStatus()));
			}
		};
		add(armedLabel, constraints);
		addUpdateable(armedLabel);
	}

}
