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
	private final static float CO2Needed = 10f;
	private final static float H2Needed = CO2Needed * 4f;
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
		float filteredH2Needed = myAirRS.randomFilter(H2Needed);
		currentCO2Consumed = myAirRS.getResourceFromStore(myAirRS.getCO2Inputs(), myAirRS.getCO2InputMaxFlowRates(), myAirRS.getCO2InputDesiredFlowRates(), myAirRS.getCO2InputActualFlowRates(), filteredCO2Needed);
		currentH2Consumed = myAirRS.getResourceFromStore(myAirRS.getH2Inputs(), myAirRS.getH2InputMaxFlowRates(), myAirRS.getH2InputDesiredFlowRates(), myAirRS.getH2InputActualFlowRates(), filteredH2Needed);
	}

	private void pushGasses(){
		if ((currentH2Consumed <= 0) || (currentCO2Consumed <=0)){
			currentH2OProduced = myAirRS.randomFilter(0f);
			currentCH4Produced = myAirRS.randomFilter(0f);
		}
		else{
			// CO2 + 4H2 --> CH4 + 2H20
			float waterMolesProduced = (0.5f * currentH2Consumed) + (.727f * currentCO2Consumed);
			float waterLitersProduced = (waterMolesProduced * 18.01524f) / 1000f; //1000g/liter, 18.01524g/mole
			float methaneMolesProduced = (0.5f * currentH2Consumed) + (.273f * currentCO2Consumed);
			currentH2OProduced = myAirRS.randomFilter(waterLitersProduced);
			currentCH4Produced = myAirRS.randomFilter(methaneMolesProduced);
		}
		float distributedWaterLeft = myAirRS.pushResourceToStore(myAirRS.getPotableWaterOutputs(), myAirRS.getPotableWaterOutputMaxFlowRates(), myAirRS.getPotableWaterOutputDesiredFlowRates(), myAirRS.getPotableWaterOutputActualFlowRates(), currentH2OProduced);
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
		super.tick();
		if (hasEnoughPower){
			gatherGasses();
			pushGasses();
		}
	}

}
