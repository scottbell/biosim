package biosim.server.simulation.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends Plant{
	private static final int HARVEST_TIME=1992;  //hours to harvest
	private static final float EDIBLE_KILOGRAMS = .0025f;  //edible kilograms per meter squared per hour

	public Wheat(int pID, BiomassRSImpl pBiomassImpl){
		super(pID, pBiomassImpl);
	}

	public Wheat(int pID, float pTotalArea, BiomassRSImpl pBiomassImpl){
		super(pID, pTotalArea, pBiomassImpl);
	}

	float calculateCO2Needed(){
		float theCO2Needed = 0f;;
		double time = (myAge / 24.0);
		if (time < 1)
			time = 1;
		double a = -1.4950;
		double b = 0.31944 * time;
		double c =  (9.9587 * 0.001) * (time * time);
		double d = (1.1802 *  0.0001) * (time * time * time);
		double e =  (5.0269  * 0.0000001) * (time * time * time * time);
		double f = 11.2;
		double CO2MaxFlowRate = ((a + b - c + d - e) / f) * 10000;
		//convert kg -> L at STP
		CO2MaxFlowRate *= .001964f;
		if (CO2MaxFlowRate < 0)
			CO2Needed = 0f;
		else{
			Double CO2MaxFlowRateObj = new Double((CO2MaxFlowRate * totalArea) / (24.0f));
			theCO2Needed = myBiomassImpl.randomFilter(CO2MaxFlowRateObj.floatValue());
		}
		return theCO2Needed;
	}
	
	float calculateWaterNeeded(){
		float theWaterNeeded = 0f;
		float biomassProducedForWater = EDIBLE_KILOGRAMS * totalArea / 2;
		theWaterNeeded =  myBiomassImpl.randomFilter(new Double(0.10274 * biomassProducedForWater).floatValue());
		return theWaterNeeded;
	}
	
	public String getPlantType(){
		return "wheat";
	}
	
	float calculatePowerNeeded(){
		float thePowerNeeded  = myBiomassImpl.randomFilter(new Double(0.5 * totalArea).floatValue());
		return thePowerNeeded;
	}

	float calculateProducedBiomass(){
		float theBiomassProduced = 0f;
		if (myAge >= HARVEST_TIME){
			//Harvest time
			theBiomassProduced = myBiomassImpl.randomFilter(EDIBLE_KILOGRAMS * myAge * totalArea);
			myAge =0;
		}
		else
			theBiomassProduced =  myBiomassImpl.randomFilter(0f);
		return theBiomassProduced;
	}

}
