package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.air.*;
/**
 * Wheat
 * @author    Scott Bell
 */

public class Wheat extends Plant{
	private float currentCO2flowRate = 0f;
	private int myAge = 0;
	private Breath airRetrieved;
	private float areaPerCrop = 11.2f;
	private float waterNeeded = 2.0f;
	private int numberOfCrops = 1;
	
	public Wheat(){
	}
	
	public Wheat(float pAreaPerCrop, int pNumberOfCrops){
		areaPerCrop = pAreaPerCrop;
		numberOfCrops = pNumberOfCrops;
	}
	
	public double getCO2Consumption(){
		return currentCO2flowRate;
	}
	
	private double pow(double a, double b){
		return Math.pow(a,b);
	}
	
	/**
	* The "inhalation" and "exhalation" of the plants for one tick.
	* The plants inhale a needed amount of CO2 (along with O2 and other gasses).
	* and exhale fraction of the CO2 inhaled, a multiple of the O2 inhaled, and the same amount of other gasses inhaled.
	*/
	private void gatherAir(){
	}
	
	public void addWater(float pWaterToAdd){
	}
	
	
	private float calculateCO2flowRate(){
		float time = 1.0f;
		Double CO2FlowRate = new Double((-1.4950 + 0.1944 * time - 9.9587 * Math.exp(-3) * (time * time) + 1.1802 * Math.exp(-4) * (time * time * time) -
		 5.0269 * Math.exp(-7) * (time * time * time* time)) / areaPerCrop);
		 return CO2FlowRate.floatValue();
	}
	
	public float getWaterNeeded(){
		return waterNeeded;
	}
	
	public void tick(){
		myAge++;
	}
	
	public void reset(){
		currentCO2flowRate = 0f;
		myAge = 0;
	}
}
