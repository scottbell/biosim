package biosim.server.simulation.air;

import biosim.idl.util.log.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.power.*;
import biosim.server.util.*;
/**
 * VCCR Subsystem
 *
 * @author    Scott Bell
 */

public class VCCR extends AirRSSubSystem{
	private Breath myBreath;
	private float molesAirNeeded = 4.0f;
	private boolean enoughAir = false;
	private float myProductionRate = 1f;

	public VCCR(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
		myBreath = new Breath(0f, 0f, 0f, 0f);
	}

	public boolean hasEnoughAir(){
		return enoughAir;
	}

	public float getAirProduced(){
		return (myBreath.CO2 + myBreath.O2 + myBreath.other);
	}

	public float getCO2Consumed(){
		return myBreath.CO2;
	}

	public void setProductionRate(float percentage){
		myProductionRate = percentage;
	}

	public float getProductionRate(){
		return myProductionRate;
	}

	private void gatherAir(){
		float airNeededFiltered = myAirRS.randomFilter(molesAirNeeded * myProductionRate);
		float gatheredAir = 0f;
		float gatheredO2 = 0f;
		float gatheredCO2 = 0f;
		float gatheredOther = 0f;
		for (int i = 0; (i < myAirRS.getAirInputs().length) && (gatheredAir < airNeededFiltered); i++){
			float resourceToGatherFirst = Math.min(airNeededFiltered, myAirRS.getAirInputMaxFlowRate(i));
			float resourceToGatherFinal = Math.min(resourceToGatherFirst, myAirRS.getAirInputDesiredFlowRate(i));
			Breath currentBreath = myAirRS.getAirInputs()[i].takeAirMoles(resourceToGatherFinal);
			gatheredAir += currentBreath.O2 + currentBreath.CO2 + currentBreath.other;
			myAirRS.setAirInputActualFlowRate(gatheredAir, i);
			gatheredO2 += currentBreath.CO2;
			gatheredCO2 += currentBreath.O2;
			gatheredOther += currentBreath.other;
		}
		myBreath.O2 =  gatheredO2;
		myBreath.CO2 = gatheredCO2;
		myBreath.other = gatheredOther;
		if ((gatheredAir +.1) < airNeededFiltered)
			enoughAir = false;
		else
			enoughAir = true;
	}

	private void pushAir(){
		float distributedO2Left = myBreath.O2;
		float distributedOtherLeft = myBreath.other;
		for (int i = 0; (i < myAirRS.getAirOutputs().length) && ((distributedO2Left > 0) || (distributedOtherLeft > 0)); i++){
			float molesToAdd = distributedO2Left + distributedOtherLeft;
			float resourceToDistributeFirst = Math.min(molesToAdd, myAirRS.getAirOutputMaxFlowRate(i));
			float resourceToDistributeFinal = Math.min(resourceToDistributeFirst, myAirRS.getAirOutputDesiredFlowRate(i));
			//Recalculate percentages based on smaller volume
			float reducedO2ToPass = resourceToDistributeFinal * (distributedO2Left / (distributedO2Left + distributedOtherLeft));
			float reducedOtherToPass = resourceToDistributeFinal * (distributedOtherLeft / (distributedO2Left + distributedOtherLeft));
			distributedO2Left -= myAirRS.getAirOutputs()[i].addO2Moles(reducedO2ToPass);
			distributedOtherLeft -= myAirRS.getAirOutputs()[i].addOtherMoles(reducedOtherToPass);
			myAirRS.setAirOutputActualFlowRate(reducedO2ToPass + reducedOtherToPass, i);
		}
		float CO2ToDistribute = myBreath.CO2 * myProductionRate;
		float distributedCO2Left = myAirRS.pushResourceToStore(myAirRS.getCO2Outputs(), myAirRS.getCO2OutputMaxFlowRates(), myAirRS.getCO2OutputDesiredFlowRates(), myAirRS.getCO2OutputActualFlowRates(), CO2ToDistribute);
	}

	public void reset(){
		myBreath = new Breath(0f, 0f, 0f, 0f);
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		enoughAir = false;
	}

	public void tick(){
		gatherPower();
		if (hasEnoughPower){
			gatherAir();
			pushAir();
		}
	}

}
