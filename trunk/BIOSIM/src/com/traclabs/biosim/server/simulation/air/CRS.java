package biosim.server.simulation.air;

import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.power.*;
import biosim.server.util.*;
/**
 * CRS Subsystem
 *
 * @author    Scott Bell
 */

public class CRS extends AirRSSubSystem{
	private final static float CO2Needed = 4.0f;
	private final static float H2Needed = 1.0f;
	private float currentCO2Consumed = 0;
	private float currentH2Consumed = 0;
	private float currentH2OProduced = 0;
	private float currentCH4Produced = 0;

	public CRS(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}

	private void gatherGasses(){
		float gatheredCO2 = 0f;
		float filteredCO2Needed = myAirRS.randomFilter(CO2Needed);
		currentCO2Consumed = myAirRS.getResourceFromStore(myAirRS.getCO2Inputs(), myAirRS.getCO2InputMaxFlowRates(), myAirRS.getCO2InputDesiredFlowRates(), myAirRS.getCO2InputActualFlowRates(), filteredCO2Needed);
		currentH2Consumed = myAirRS.getOGS().takeH2(myAirRS.randomFilter(H2Needed));
	}

	private void pushGasses(){
		currentH2OProduced = myAirRS.randomFilter(new Double((currentH2Consumed + currentCO2Consumed) * .80).floatValue());
		float distributedWaterLeft = myAirRS.pushResourceToStore(myAirRS.getPotableWaterOutputs(), myAirRS.getPotableWaterOutputMaxFlowRates(), myAirRS.getPotableWaterOutputDesiredFlowRates(), myAirRS.getPotableWaterOutputActualFlowRates(), currentH2OProduced);
		currentCH4Produced = myAirRS.randomFilter(new Double((currentH2Consumed + currentCO2Consumed) * .20).floatValue());
		myAirRS.getCH4Tank().addCH4(currentCH4Produced);
	}

	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		currentCO2Consumed = 0;
		currentH2Consumed = 0;
		currentH2OProduced = 0;
		currentCH4Produced = 0;
	}

	public void tick(){
		gatherPower();
		if (hasEnoughPower){
			gatherGasses();
			pushGasses();
		}
	}

}
