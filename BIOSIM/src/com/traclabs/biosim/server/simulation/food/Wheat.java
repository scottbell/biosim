package biosim.server.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends Plant{
	private static final int HARVEST_TIME=1992;  //hours to harvest
	private static final float EDIBLE_KILOGRAMS = .0025f;  //edible kilograms per meter squared per hour

	public Wheat(){
	}

	public Wheat(float pTotalArea){
		super(pTotalArea);
	}

	protected void calculateCO2Needed(){
		double time = (myAge / 24.0);
		if (time < 1)
			time = 1;
		double a = -1.4950;
		double b = 0.31944 * time;
		double c =  (9.9587 * 0.001) * (time * time);
		double d = (1.1802 *  0.0001) * (time * time * time);
		double e =  (5.0269  * 0.0000001) * (time * time * time * time);
		double f = 11.2;
		double CO2FlowRate = (a + b - c + d - e) / f;
		//convert kg -> L at STP
		CO2FlowRate *= .001964;
		if (CO2FlowRate < 0)
			CO2Needed = 0f;
		else{
			Double CO2FlowRateObj = new Double((CO2FlowRate * totalArea) / (24.0f));
			CO2Needed = CO2FlowRateObj.floatValue();
		}
	}
	
	protected void calculateWaterNeeded(){
		float biomassProducedForWater = EDIBLE_KILOGRAMS * totalArea;
		waterNeeded =  new Double(0.10274 * biomassProducedForWater).floatValue();
	}
	
	public String getPlantType(){
		return "wheat";
	}
	
	protected void calculatePowerNeeded(){
		powerNeeded  = new Double(0.5 * totalArea).floatValue();
	}

	protected void calculateProducedBiomass(){
		if (myAge >= HARVEST_TIME){
			//Harvest time
			biomassProduced = EDIBLE_KILOGRAMS * myAge * totalArea;
			myAge =0;
		}
		else
			biomassProduced =  0f;
	}

}
