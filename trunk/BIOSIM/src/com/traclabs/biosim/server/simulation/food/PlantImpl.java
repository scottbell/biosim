package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.idl.environment.*;
import biosim.server.util.*;
/**
 * Plant
 * @author    Scott Bell
 */

public abstract class Plant {
	protected boolean hasCollectedReferences = false;
	protected boolean hasEnoughCO2 = false;
	protected boolean hasEnoughWater = false;
	protected int myAge = 0;
	protected Breath airRetrieved;
	protected float areaPerCrop = 11.2f;
	protected float waterNeeded = 2.0f;
	protected int numberOfCrops = 1;
	protected float currentWaterLevel = 0f;
	protected float biomassProduced = 0f;
	private SimEnvironment mySimEnvironment;
	private BiomassStore myBiomassStore;
	
	public Plant(){
	}
	
	public Plant(float pAreaPerCrop, int pNumberOfCrops){
		areaPerCrop = pAreaPerCrop;
		numberOfCrops = pNumberOfCrops;
	}
	
	public float getCO2Consumed(){
		return airRetrieved.CO2;
	}
	
	private double pow(double a, double b){
		return Math.pow(a,b);
	}
	
	protected abstract float calculateCO2Needed();
	
	/**
	* The "inhalation" and "exhalation" of the plants for one tick.
	* The plants inhale a needed amount of CO2 (along with O2 and other gasses).
	* and exhale fraction of the CO2 inhaled, a multiple of the O2 inhaled, and the same amount of other gasses inhaled.
	*/
	private void consumeAir(){
		float CO2Needed = calculateCO2Needed();
		airRetrieved = mySimEnvironment.takeCO2Breath(CO2Needed);
		if (airRetrieved.CO2 < CO2Needed)
			hasEnoughCO2 = false;
		else
			hasEnoughCO2 = true;
		mySimEnvironment.addO2(new Double(airRetrieved.O2 + airRetrieved.CO2 * .90).floatValue());
		mySimEnvironment.addOther(airRetrieved.other);
		mySimEnvironment.addCO2(new Double(airRetrieved.CO2 * .10).floatValue());
	}
	
	public void addWater(float pWaterToAdd){
		currentWaterLevel = pWaterToAdd;
		if (currentWaterLevel < waterNeeded)
			hasEnoughWater = false;
		else
			hasEnoughWater = true;
	}
	
	public void reset(){
		airRetrieved = new Breath(0f, 0f, 0f);
		myAge = 0;
	}
	
	protected abstract void calculateProducedBiomass();
	
	private void produceBiomass(){
		calculateProducedBiomass();
		if (biomassProduced > 0)
			myBiomassStore.add(biomassProduced);
	}
	
	public void tick(){
		collectReferences();
		myAge++;
		consumeAir();
		produceBiomass();
		afflict();
		deathCheck();
	}
	
	protected void afflict(){
	}
	
	protected void deathCheck(){
	}
	
	public float getWaterNeeded(){
		return waterNeeded;
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("BiomassStore"));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
	}
}
