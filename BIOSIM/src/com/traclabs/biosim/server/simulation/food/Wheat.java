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
	
	public double getCO2Consumption(){
		return currentCO2flowRate;
	}
	
	private double pow(double a, double b){
		return Math.pow(a,b);
	}
	
	public Wheat(){
	}
	
	/**
	* The "inhalation" and "exhalation" of the plants for one tick.
	* The plants inhale a needed amount of CO2 (along with O2 and other gasses).
	* and exhale fraction of the CO2 inhaled, a multiple of the O2 inhaled, and the same amount of other gasses inhaled.
	*/
	private void gatherAir(){
	}
	
	
	private double calculateCO2flowRate(){
		/*
		float time = 1.0;
		float plantArea = 11.2;
		float CO2FlowRate = (-1.4950 + 0.1944 * time - 9.9587 * Math.exp(-3) * (time * time) + 1.1802 * Math.exp(-4) * (time * time * time) -
		 5.0269 * Math.exp(-7) * (time * time * time* time)) / plantArea;
		 return CO2FlowRate;
		 */
		 return 0.0d;
	}
	
	
	public void tick(){
		myAge++;
	}
}
