package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.idl.util.*;
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
	private LogIndex myLogIndex;
	private boolean logInitialized = false;

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
		waterNeeded = 0f;
		CO2Needed = 0f;
		currentPowerLevel = 0f;
		biomassProduced = 0f;
		plantsDead = false;
	}
	
	public abstract String getPlantType();
	
	private void produceBiomass(){
		calculateProducedBiomass();
		if (biomassProduced > 0){
			myBiomassStore.add(biomassProduced);
			myAge = 0;
			System.out.println("Harvested!");
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

	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode typeHead = myLogHead.addChild("Plant Type");
			myLogIndex.typeIndex = typeHead.addChild(""+getPlantType());
			LogNode hasEnoughCO2Head = myLogHead.addChild("Has Enough CO2");
			myLogIndex.hasEnoughCO2Index = hasEnoughCO2Head.addChild(""+hasEnoughCO2);
			LogNode hasEnoughWaterHead = myLogHead.addChild("Has Enough Water");
			myLogIndex.hasEnoughWaterIndex = hasEnoughWaterHead.addChild(""+hasEnoughWater);
			LogNode hasEnoughLightHead = myLogHead.addChild("Has Enough Light");
			myLogIndex.hasEnoughLightIndex = hasEnoughLightHead.addChild(""+hasEnoughLight);
			LogNode myAgeHead = myLogHead.addChild("Age");
			myLogIndex.myAgeIndex = myAgeHead.addChild(""+myAge);
			LogNode O2RetrievedHead = myLogHead.addChild("O2 Retrieved");
			myLogIndex.O2RetrievedIndex = O2RetrievedHead.addChild(""+airRetrieved.O2);
			LogNode CO2RetrievedHead = myLogHead.addChild("CO2 Retrieved");
			myLogIndex.CO2RetrievedIndex = CO2RetrievedHead.addChild(""+airRetrieved.CO2);
			LogNode otherRetrievedHead = myLogHead.addChild("Other Retrieved");
			myLogIndex.otherRetrievedIndex = otherRetrievedHead.addChild(""+airRetrieved.other);
			LogNode areaPerCropHead = myLogHead.addChild("Area Per Crop");
			myLogIndex.areaPerCropIndex = areaPerCropHead.addChild(""+areaPerCrop);
			LogNode waterNeededHead = myLogHead.addChild("Water Needed");
			myLogIndex.waterNeededIndex = waterNeededHead.addChild(""+waterNeeded);
			LogNode powerNeededHead = myLogHead.addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode CO2NeededHead = myLogHead.addChild("CO2 Needed");
			myLogIndex.CO2NeededIndex = CO2NeededHead.addChild(""+CO2Needed);
			LogNode numberOfCropsHead = myLogHead.addChild("Number of Crops");
			myLogIndex.numberOfCropsIndex = numberOfCropsHead.addChild(""+numberOfCrops);
			LogNode currentWaterLevelHead = myLogHead.addChild("Water Level");
			myLogIndex.currentWaterLevelIndex = currentWaterLevelHead.addChild(""+currentWaterLevel);
			LogNode currentPowerLevelHead = myLogHead.addChild("Power Level");
			myLogIndex.currentPowerLevelIndex = currentPowerLevelHead.addChild(""+currentPowerLevel);
			LogNode biomassProducedlHead = myLogHead.addChild("Biomass Produced");
			myLogIndex.biomassProducedIndex = biomassProducedlHead.addChild(""+biomassProduced);
			LogNode surviveNoWaterHead = myLogHead.addChild("Duration Plants Can Survive Without Water");
			myLogIndex.surviveNoWaterIndex = surviveNoWaterHead.addChild(""+surviveNoWater);
			LogNode surviveNoCO2Head = myLogHead.addChild("Duration Plants Can Survive Without CO2");
			myLogIndex.surviveNoCO2Index = surviveNoCO2Head.addChild(""+surviveNoCO2);
			LogNode surviveNoLightHead = myLogHead.addChild("Duration Plants Can Survive Without Light");
			myLogIndex.surviveNoLightIndex = surviveNoLightHead.addChild(""+surviveNoLight);
			LogNode noWaterTimeHead = myLogHead.addChild("Duration Without Water");
			myLogIndex.noWaterTimeIndex = noWaterTimeHead.addChild(""+noWaterTime);
			LogNode noCO2TimeHead = myLogHead.addChild("Duration Without CO2");
			myLogIndex.noCO2TimeIndex = noCO2TimeHead.addChild(""+noCO2Time);
			LogNode noLightTimeHead = myLogHead.addChild("Duration Without Light");
			myLogIndex.noLightTimeIndex = noLightTimeHead.addChild(""+noLightTime);
			LogNode plantsDeadHead = myLogHead.addChild("Plants Dead");
			myLogIndex.plantsDeadIndex = plantsDeadHead.addChild(""+plantsDead);
			logInitialized = true;
		}
		else{
			myLogIndex.typeIndex.setValue(""+getPlantType());
			myLogIndex.hasEnoughCO2Index.setValue(""+hasEnoughCO2);
			myLogIndex.hasEnoughWaterIndex.setValue(""+hasEnoughWater);
			myLogIndex.hasEnoughLightIndex.setValue(""+hasEnoughLight);
			myLogIndex.myAgeIndex.setValue(""+myAge);
			myLogIndex.O2RetrievedIndex.setValue(""+airRetrieved.O2);
			myLogIndex.CO2RetrievedIndex.setValue(""+airRetrieved.CO2);
			myLogIndex.otherRetrievedIndex.setValue(""+airRetrieved.other);
			myLogIndex.areaPerCropIndex.setValue(""+areaPerCrop);
			myLogIndex.waterNeededIndex.setValue(""+waterNeeded);
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
			myLogIndex.CO2NeededIndex.setValue(""+CO2Needed);
			myLogIndex.numberOfCropsIndex.setValue(""+numberOfCrops);
			myLogIndex.currentWaterLevelIndex.setValue(""+currentWaterLevel);
			myLogIndex.currentPowerLevelIndex.setValue(""+currentPowerLevel);
			myLogIndex.biomassProducedIndex.setValue(""+biomassProduced);
			myLogIndex.surviveNoWaterIndex.setValue(""+surviveNoWater);
			myLogIndex.surviveNoCO2Index.setValue(""+surviveNoCO2);
			myLogIndex.surviveNoLightIndex.setValue(""+surviveNoLight);
			myLogIndex.noWaterTimeIndex.setValue(""+noWaterTime);
			myLogIndex.noCO2TimeIndex.setValue(""+noCO2Time);
			myLogIndex.noLightTimeIndex.setValue(""+noLightTime);
			myLogIndex.plantsDeadIndex.setValue(""+plantsDead);
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode typeIndex;
		public LogNode hasEnoughCO2Index;
		public LogNode hasEnoughWaterIndex;
		public LogNode hasEnoughLightIndex;
		public LogNode myAgeIndex;
		public LogNode O2RetrievedIndex;
		public LogNode CO2RetrievedIndex;
		public LogNode otherRetrievedIndex;
		public LogNode areaPerCropIndex;
		public LogNode waterNeededIndex;
		public LogNode powerNeededIndex;
		public LogNode CO2NeededIndex;
		public LogNode numberOfCropsIndex;
		public LogNode currentWaterLevelIndex;
		public LogNode currentPowerLevelIndex;
		public LogNode biomassProducedIndex;
		public LogNode surviveNoWaterIndex;
		public LogNode surviveNoCO2Index;
		public LogNode surviveNoLightIndex;
		public LogNode noWaterTimeIndex;
		public LogNode noCO2TimeIndex;
		public LogNode noLightTimeIndex;
		public LogNode plantsDeadIndex;
	}
}
