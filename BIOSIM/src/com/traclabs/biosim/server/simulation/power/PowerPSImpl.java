package biosim.server.power;

import biosim.idl.power.*;

public class PowerPSImpl extends PowerPSPOA {
	private float currentPowerProduced = 0f;

	public void tick(){
		System.out.println("PowerPS has been ticked!");
	}
	
	public  float getPowerProduced(){
		return currentPowerProduced;
	}
	
	public String getModuleName(){
		return "PowerPS";
	}
}
