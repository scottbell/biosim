package biosim.server.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends Plant{
	private static final int HARVEST_TIME=83;
	private static final float EDIBLE_KILOGRAMS = .0025f;  //edible kilograms per meter squared per hour

	public Wheat(){
	}

	public Wheat(float pAreaPerCrop, int pNumberOfCrops){
		super(pAreaPerCrop, pNumberOfCrops);
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
		if (CO2FlowRate < 0)
			CO2Needed = 0f;
		else{
			Double CO2FlowRateObj = new Double((CO2FlowRate * (numberOfCrops * areaPerCrop)) / (24.0f));
			CO2Needed = CO2FlowRateObj.floatValue();
		}
	}
	
	protected void calculateWaterNeeded(){
		waterNeeded =  new Double((866.299 / HARVEST_TIME) * numberOfCrops).floatValue();;
	}
	
	public String getPlantType(){
		return "Wheat";
	}
	
	protected void calculatePowerNeeded(){
		powerNeeded  = new Double(2.0 * numberOfCrops).floatValue();
	}

	protected void calculateProducedBiomass(){
		if (((myAge / 24)) >= HARVEST_TIME){
			//Harvest time
			biomassProduced = EDIBLE_KILOGRAMS * myAge * areaPerCrop;
			biomassProduced *= numberOfCrops;
			myAge =0;
		}
		else
			biomassProduced =  0f;
	}

}
