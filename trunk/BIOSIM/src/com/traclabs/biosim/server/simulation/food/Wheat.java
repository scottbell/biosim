package biosim.server.food;

/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends Plant{
	
	public Wheat(){
	}
	
	public Wheat(float pAreaPerCrop, int pNumberOfCrops){
		super(pAreaPerCrop, pNumberOfCrops);
	}
	
	protected float calculateCO2Needed(){
		float time = 1.0f;
		Double CO2FlowRate = new Double((-1.4950 + 0.1944 * time - 9.9587 * Math.exp(-3) * (time * time) + 1.1802 * Math.exp(-4) * (time * time * time) -
		 5.0269 * Math.exp(-7) * (time * time * time* time)) / areaPerCrop);
		 return CO2FlowRate.floatValue();
	}
}
