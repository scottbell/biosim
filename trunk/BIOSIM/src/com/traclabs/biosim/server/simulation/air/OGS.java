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
	private final static float waterNeeded = 1.5f;
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
		//2H20 --> 2H2 + O2
		float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter, 18.01524g/mole
		currentO2Produced = myAirRS.randomFilter(molesOfWater * 0.333f) * myProductionRate;
		currentH2Produced = myAirRS.randomFilter(molesOfWater * 0.666f) * myProductionRate;
		float O2ToDistrubute = myAirRS.randomFilter(currentO2Produced);
		float H2ToDistrubute = myAirRS.randomFilter(currentH2Produced);
		float distributedO2 = myAirRS.pushResourceToStore(myAirRS.getO2Outputs(), myAirRS.getO2OutputMaxFlowRates(), myAirRS.getO2OutputDesiredFlowRates(), myAirRS.getO2OutputActualFlowRates(), O2ToDistrubute);
		float distributedH2 = myAirRS.pushResourceToStore(myAirRS.getH2Outputs(), myAirRS.getH2OutputMaxFlowRates(), myAirRS.getH2OutputDesiredFlowRates(), myAirRS.getH2OutputActualFlowRates(), H2ToDistrubute);
	}

	public void setProductionRate(float percentage){
		myProductionRate = percentage;
	}

	public float getProductionRate(){
		return myProductionRate;
	}

	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		currentH2OConsumed = 0;
		currentO2Produced = 0;
		currentH2Produced = 0;
	}

	public void tick(){
		super.tick();
		if (hasEnoughPower){
			gatherWater();
			pushGasses();
		}
	}

}
