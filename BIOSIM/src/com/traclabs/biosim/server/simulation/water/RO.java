package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The RO is the second stage of water purification.  It takes water from the BWP, filters it some, and
 * sends the water to the AES
 *
 * @author    Scott Bell
 */

public class RO extends WaterRSSubSystem{
	private float currentAESWaterProduced = 0f;
	private float currentPPSWaterProduced = 0f;

	/**
	* Constructor that creates the RO
	* @param pWaterRSImpl The Water RS system the RO is contained in
	*/
	public RO(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}

	public float getAESWaterProduced(){
		return currentAESWaterProduced;
	}

	public float getPPSWaterProduced(){
		return currentPPSWaterProduced;
	}

	/**
	* Flushes the water from this subsystem to the AES
	*/
	private void pushWater(){
		if (myWaterRS.getAES().isEnabled()){
			currentAESWaterProduced = (new Double(waterLevel * 0.15f)).floatValue();
			myWaterRS.getAES().addWater(currentAESWaterProduced);
		}
		else{
			currentAESWaterProduced = 0f;
		}
		currentPPSWaterProduced = (new Double(waterLevel * 0.85f)).floatValue();
		myWaterRS.getPPS().addWater(currentPPSWaterProduced);
		waterLevel = 0;
	}

	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the AES.
	*/
	public void tick(){
		super.tick();
		if (enabled){
			if (hasEnoughPower){
				pushWater();
			}
			else{
				//try to put back into dirtyWater Store.
				waterLevel = SimBioModuleImpl.pushResourceToStore(myWaterRS.getDirtyWaterInputs(), myWaterRS.getDirtyWaterInputMaxFlowRates(), myWaterRS.getDirtyWaterInputDesiredFlowRates(), myWaterRS.getDirtyWaterInputActualFlowRates(), waterLevel);
				//dump extra water
				waterLevel = 0f;
				currentAESWaterProduced = 0f;
				currentPPSWaterProduced = 0f;
			}
		}
		else{
			//try to put back into dirtyWater Store.
			waterLevel = SimBioModuleImpl.pushResourceToStore(myWaterRS.getDirtyWaterInputs(), myWaterRS.getDirtyWaterInputMaxFlowRates(), myWaterRS.getDirtyWaterInputDesiredFlowRates(), myWaterRS.getDirtyWaterInputActualFlowRates(), waterLevel);
			//dump extra water
			waterLevel = 0f;
			currentAESWaterProduced = 0f;
			currentPPSWaterProduced = 0f;
		}
	}

	public void reset(){
		super.reset();
		currentAESWaterProduced = 0f;
		currentPPSWaterProduced = 0f;
	}
}
