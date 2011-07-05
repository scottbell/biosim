package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.event.ActionListener;

import com.traclabs.biosim.idl.simulation.power.RPCM;

public abstract class RpcActionListener implements ActionListener {

	private RPCM myRPCM;
	
	public RpcActionListener(RPCM rpcm) {
		this.myRPCM = rpcm;
	}
	
	public RPCM getRPCM(){
		return myRPCM;
	}

}
