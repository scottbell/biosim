package com.traclabs.biosim.client.simulation.air.cdrs;

import com.traclabs.biosim.idl.simulation.power.RPCM;
import com.traclabs.biosim.idl.simulation.power.RPCMArmedStatus;
import com.traclabs.biosim.idl.simulation.power.RPCMSwitchState;

public abstract class RpcStatusLabel extends StatusLabel{
	private RPCM myRpcm;
	public RpcStatusLabel(RPCM rpcm){
		this.myRpcm = rpcm;
	}
	
	public RPCM getRPCM(){
		return myRpcm;
	}
	
	public static String getArmedStatus(RPCMArmedStatus armedStatus) {
		if (armedStatus == RPCMArmedStatus.enabled)
			return "enabled";
		else if (armedStatus == RPCMArmedStatus.inhibited)
			return "inhibited";
		return "?";
	}

	public static String getSwitchState(RPCMSwitchState switchState) {
		if (switchState == RPCMSwitchState.open)
			return "open";
		else if (switchState == RPCMSwitchState.closed)
			return "closed";
		return "?";
	}
}
