package biosim.server.air;

import biosim.idl.util.log.*;
import biosim.idl.air.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.server.util.*;
/**
 * VCCR Subsystem
 *
 * @author    Scott Bell
 */

public class VCCR extends AirRSSubSystem{
	private Breath myBreath;
	private float litersAirNeeded = 4.0f;
	private boolean enoughAir = false;
	private float myProductionRate = 1f;

	public VCCR(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
		myBreath = new Breath(0,0,0);
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
		float airNeededFiltered = myAirRS.randomFilter(litersAirNeeded);
		float gatheredAir = 0f;
		float gatheredO2 = 0f;
		float gatheredCO2 = 0f;
		float gatheredOther = 0f;
		for (int i = 0; (i < myAirRS.getAirInputs().length) && (gatheredAir < airNeededFiltered); i++){
			airNeededFiltered = Math.min(airNeededFiltered, myAirRS.getAirInputFlowrate(i));
			Breath currentBreath = myAirRS.getAirInputs()[i].takeVolume(airNeededFiltered);
			gatheredAir += currentBreath.O2 + currentBreath.CO2 + currentBreath.other;
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
		float distributedO2Left = myBreath.O2 * myProductionRate;
		float distributedOtherLeft = myBreath.other * myProductionRate;
		for (int i = 0; (i < myAirRS.getAirOutputs().length) && ((distributedO2Left > 0) || (distributedOtherLeft > 0)); i++){
			float litersToAdd = distributedO2Left + distributedOtherLeft;
			if (litersToAdd <= myAirRS.getAirOutputFlowrate(i)){
				distributedO2Left -= myAirRS.getAirOutputs()[i].addO2(distributedO2Left);
				distributedOtherLeft -= myAirRS.getAirOutputs()[i].addOther(distributedOtherLeft);
			}
			else{
				//Recalculate percentages based on smaller volume
				float reducedO2ToPass = myAirRS.getAirOutputFlowrate(i) * (distributedO2Left / (distributedO2Left + distributedOtherLeft));
				float reducedOtherToPass = myAirRS.getAirOutputFlowrate(i) * (distributedOtherLeft / (distributedO2Left + distributedOtherLeft));
				distributedO2Left -= myAirRS.getAirOutputs()[i].addO2(reducedO2ToPass);
				distributedOtherLeft -= myAirRS.getAirOutputs()[i].addOther(reducedOtherToPass);
			}

		}
		float CO2ToDistribute = myBreath.CO2 * myProductionRate;
		float distributedCO2Left = myAirRS.pushResourceToStore(myAirRS.getCO2Outputs(), myAirRS.getCO2OutputFlowrates(), CO2ToDistribute);
	}

	public void reset(){
		myBreath = new Breath(0,0,0);
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
