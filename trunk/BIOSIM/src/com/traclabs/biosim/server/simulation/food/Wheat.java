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
		float c = 0.68f;
		float ta = 12;
		float tq = 33;
		float tm = 78;
		float ageDays = myAge / 24;
		float co2_ppm = 100; //need to change;
		float area = 14;
		float ppf = 2080;
		float h = 24;
		float Amax = 0.93f;
		float Qmin = 0.01f;
		double Qmax = 0.066 * co2_ppm / (pow(( pow(210, 1.4) + pow(co2_ppm, 1.4)), (1/1.4)));
		double A = pow((Amax * ageDays / (pow(ta,5) + pow(ageDays,5))), (1/5));
		double Q = Qmin + (Qmax-Qmin) * (tm - ageDays) / ((tm-ageDays) / pow( (pow((tm - tq), 15) + pow( (tm - ageDays), 15) ), 1/15) );
		double co2_rxn = 0.0036 * area * ( (c*h) - ((24 - h) * (1 - c))) * ppf * Q * A;
		return co2_rxn;
	}
	
	
	public void tick(){
		myAge++;
	}
}
