package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.idl.simulation.power.RPCM;
import com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus;
import com.traclabs.biosim.idl.simulation.power.RPCMSwitchState;

public class RpcPanel extends GridButtonPanel {
	private RPCM myRpcm;
	public RpcPanel(RPCM rpcm){
		this.myRpcm = rpcm;
		
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.weightx = 0.5;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		JLabel positionLabel = new JLabel("RPC Position");
		add(positionLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 0;
		RpcStatusLabel positionStatusLabel = new RpcStatusLabel(myRpcm) {
			public void refresh() {
				setText(getSwitchState(getRPCM().getSwitchState()));
			}
		};
		add(positionStatusLabel, constraints);
		addUpdateable(positionStatusLabel);
		constraints.gridx = 2;
		constraints.gridy = 0;
		RpcActionListener openPositionListener = new RpcActionListener(myRpcm) {
			public void actionPerformed(ActionEvent e) {
				getRPCM().setSwitchState(RPCMSwitchState.open);
			}
		};
		JButton openPositionButton = new JButton("Open");
		openPositionButton.addActionListener(openPositionListener);
		add(openPositionButton, constraints);
		constraints.gridx = 3;
		constraints.gridy = 0;
		RpcActionListener closePositionListener = new RpcActionListener(myRpcm) {
			public void actionPerformed(ActionEvent e) {
				getRPCM().setSwitchState(RPCMSwitchState.closed);
			}
		};
		JButton closePositionButton = new JButton("Close");
		closePositionButton.addActionListener(closePositionListener);
		add(closePositionButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		JLabel trippedLabel = new JLabel("RPC Tripped");
		add(trippedLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		RpcStatusLabel trippedStatusLabel = new RpcStatusLabel(myRpcm) {
			public void refresh() {
				setText("false");
			}
		};
		add(trippedStatusLabel, constraints);
		addUpdateable(trippedStatusLabel);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		JLabel commandEnabledLabel = new JLabel("Command Enabled");
		add(commandEnabledLabel, constraints);
		constraints.gridx = 1;
		constraints.gridy = 2;
		RpcStatusLabel commandEnabledStatusLabel = new RpcStatusLabel(myRpcm) {
			public void refresh() {
				setText(getArmedStatus(getRPCM().getArmedStatus()));
			}
		};
		add(commandEnabledStatusLabel, constraints);
		addUpdateable(commandEnabledStatusLabel);
		constraints.gridx = 2;
		constraints.gridy = 2;
		RpcActionListener enableCommandListener = new RpcActionListener(myRpcm) {
			public void actionPerformed(ActionEvent e) {
				getRPCM().setArmedStatus(RPCMArmedStatus.enabled);
			}
		};
		JButton enableCommandButton = new JButton("Enable");
		enableCommandButton.addActionListener(enableCommandListener);
		add(enableCommandButton, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 2;
		RpcActionListener inhibitCommandListener = new RpcActionListener(myRpcm) {
			public void actionPerformed(ActionEvent e) {
				getRPCM().setArmedStatus(RPCMArmedStatus.inhibited);
			}
		};
		JButton inhibitCommandButton = new JButton("Inhibit");
		inhibitCommandButton.addActionListener(inhibitCommandListener);
		add(inhibitCommandButton, constraints);
	}
	
	public RPCM getRPCM(){
		return myRpcm;
	}

}
