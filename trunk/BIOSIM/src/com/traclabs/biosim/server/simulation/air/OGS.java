package biosim.server.simulation.air;

import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.power.*;
import biosim.server.util.*;
/**
 * OGS Subsystem
 *
 * @author    Scott Bell
 */

public class OGS extends AirRSSubSystem{
	private final static float waterNeeded = 1.0f;
	private float currentH2OConsumed = 0;
	private float currentO2Produced = 0;
	private float currentH2Produced = 0;
	private float myProductionRate = 1f;

	public OGS(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}


	public float getO2Produced(){
		return currentO2Produced;
	}
	
	private void gatherWater(){
		currentH2OConsumed = myAirRS.getResourceFromStore(myAirRS.getPotableWaterInputs(), myAirRS.getPotableWaterInputMaxFlowRates(), myAirRS.getPotableWaterInputDesiredFlowRates(), myAirRS.getPotableWaterInputActualFlowRates(), waterNeeded);
	}

	private void pushGasses(){
		currentO2Produced = myAirRS.randomFilter(currentH2OConsumed * 0.66666f) * myProductionRate;
		currentH2Produced = myAirRS.randomFilter(currentH2OConsumed * 0.33333f) * myProductionRate;
		float distributedO2 = myAirRS.randomFilter(currentO2Produced);
		float distributedO2Left = myAirRS.pushResourceToStore(myAirRS.getO2Outputs(), myAirRS.getO2OutputMaxFlowRates(), myAirRS.getO2OutputDesiredFlowRates(), myAirRS.getO2OutputActualFlowRates(), distributedO2);
	}

	public void setProductionRate(float percentage){
		myProductionRate = percentage;
	}

	public float getProductionRate(){
		return myProductionRate;
	}

	public float takeH2(float H2toTake){
		if (H2toTake <= currentH2Produced)
			return H2toTake;
		else
			return currentH2Produced;
	}

	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		currentH2OConsumed = 0;
		currentO2Produced = 0;
	}

	public void tick(){
		gatherPower();
		if (hasEnoughPower){
			gatherWater();
			pushGasses();
		}
	}

}
