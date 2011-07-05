package com.traclabs.biosim.client.simulation.air.cdrs;

import com.traclabs.biosim.idl.simulation.power.RPCM;

public class RpcPanel extends GridButtonPanel {
	private RPCM myRpcm;
	public RpcPanel(RPCM rpcm){
		this.myRpcm = rpcm;
	}
	
	public RPCM getRPCM(){
		return myRpcm;
	}

}
