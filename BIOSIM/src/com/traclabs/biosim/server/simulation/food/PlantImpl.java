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
	protected boolean hasEnoughLight = false;
	protected int myAge = 0;
	protected Breath airRetrieved;
	protected float areaPerCrop = 11.2f;
	protected float waterNeeded = .01f; //for one crop
	protected float powerNeeded = 2.0f; //for one crop
	protected float CO2Needed = 1.0f;
	protected int numberOfCrops = 1;
	protected float currentWaterLevel = 0f;
	protected float currentPowerLevel = 0f;
	protected float biomassProduced = 0f;
	protected int surviveNoWater = 168;
	protected int surviveNoCO2 = 800;
	protected int surviveNoLight = 168;
	private int noWaterTime = 0;
	private int noCO2Time = 0;
	private int noLightTime = 0;
	private boolean plantsDead = true;
	private SimEnvironment mySimEnvironment;
	private BiomassStore myBiomassStore;

	public Plant(){
	}

	public Plant(float pAreaPerCrop, int pNumberOfCrops){
		areaPerCrop = pAreaPerCrop;
		numberOfCrops = pNumberOfCrops;
	}
	
	protected abstract void calculateCO2Needed();
	protected abstract void calculatePowerNeeded();
	protected abstract void calculateWaterNeeded();
	protected abstract void calculateProducedBiomass();

	public float getCO2Consumed(){
		return airRetrieved.CO2;
	}

	private double pow(double a, double b){
		return Math.pow(a,b);
	}

	/**
	* The "inhalation" and "exhalation" of the plants for one tick.
	* The plants inhale a needed amount of CO2 (along with O2 and other gasses).
	* and exhale fraction of the CO2 inhaled, a multiple of the O2 inhaled, and the same amount of other gasses inhaled.
	*/
	private void consumeAir(){
		calculateCO2Needed();
		airRetrieved = mySimEnvironment.takeCO2Breath(CO2Needed);
		if (airRetrieved.CO2 < (.10 * CO2Needed))
			hasEnoughCO2 = false;
		else
			hasEnoughCO2 = true;
		mySimEnvironment.addO2(new Double(airRetrieved.O2 + airRetrieved.CO2 * .90).floatValue());
		mySimEnvironment.addOther(airRetrieved.other);
		mySimEnvironment.addCO2(new Double(airRetrieved.CO2 * .10).floatValue());
	}

	public void addWater(float pWaterToAdd){
		calculateWaterNeeded();
		currentWaterLevel = pWaterToAdd;
		if (currentWaterLevel < waterNeeded)
			hasEnoughWater = false;
		else
			hasEnoughWater = true;
	}

	public void lightPlants(float pWatts){
		calculatePowerNeeded();
		currentPowerLevel = pWatts;
		if (currentPowerLevel < powerNeeded)
			hasEnoughLight = false;
		else
			hasEnoughLight = true;
	}

	public void reset(){
		airRetrieved = new Breath(0f, 0f, 0f);
		myAge = 0;
		noWaterTime = 0;
		noCO2Time = 0;
		noLightTime = 0;
		currentWaterLevel = 0f;
		currentPowerLevel = 0f;
		biomassProduced = 0f;
		plantsDead = false;
	}
	
	private void produceBiomass(){
		calculateProducedBiomass();
		if (biomassProduced > 0){
			myBiomassStore.add(biomassProduced);
		}
	}

	public void tick(){
		if (!plantsDead){
			collectReferences();
			myAge++;
			consumeAir();
			produceBiomass();
			afflict();
			deathCheck();
		}
	}

	protected void afflict(){
		if (!hasEnoughCO2){
			noCO2Time++;
		}
		else{
			noCO2Time = 0;
		}
		
		if (!hasEnoughWater){
			noWaterTime++;
		}
		else{
			noWaterTime = 0;
		}
		
		if (!hasEnoughLight){
			noLightTime++;
		}
		else{
			noLightTime = 0;
		}
	}

	public boolean arePlantsDead(){
		return plantsDead;
	}

	protected void deathCheck(){
		if (	(noCO2Time   > surviveNoCO2) ||
		        (noWaterTime > surviveNoWater) ||
		        (noLightTime  > surviveNoLight))
		{
			if (noWaterTime > surviveNoWater)
				System.out.println("Plants out of water");
			reset();
			System.out.println("Plants dead");
			plantsDead = true;
		}
	}

	public float getWaterNeeded(){
		return waterNeeded;
	}

	public float getPowerNeeded(){
		return powerNeeded;
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
