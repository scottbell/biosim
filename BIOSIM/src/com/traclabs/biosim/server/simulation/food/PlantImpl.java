package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.idl.util.log.*;
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
	protected float CO2Consumed = 0f;
	protected float O2Produced = 0f;
	protected int myAge = 0;
	protected Breath airRetrieved;
	protected float totalArea = 11.2f;
	protected float waterNeeded = .01f;
	protected float powerNeeded = 2.0f; 
	protected float CO2Needed = 1.0f;
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
	private int myID = 0;

	public Plant(int pID){
		myID = pID;
	}

	public Plant(int pID, float pTotalArea){
		myID = pID;
		totalArea = pTotalArea;
	}

	protected abstract void calculateCO2Needed();
	protected abstract void calculatePowerNeeded();
	protected abstract void calculateWaterNeeded();
	protected abstract void calculateProducedBiomass();
	public abstract String getPlantType();
	
	public boolean hasWater(){
		return hasEnoughWater;
	}
	
	public boolean hasLight(){
		return hasEnoughLight;
	}
	
	public boolean isDead(){
		return plantsDead;
	}
	
	public boolean hasCO2(){
		return hasEnoughCO2;
	}
	
	public float getCO2Consumed(){
		return CO2Consumed;
	}
	
	public float getO2Produced(){
		return O2Produced;
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
		CO2Consumed = airRetrieved.CO2;
		O2Produced = new Double(airRetrieved.O2 + airRetrieved.CO2 * .90).floatValue();
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
		CO2Consumed = 0f;
		O2Produced = 0f;
		plantsDead = false;
	}
	
	private void produceBiomass(){
		calculateProducedBiomass();
		if (biomassProduced > 0){
			myBiomassStore.add(biomassProduced);
			myAge = 0;
		}
	}
	
	public float getBiomassProduced(){
		return biomassProduced;
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
			reset();
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
				mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"+myID));
				myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("BiomassStore"+myID));
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
			LogNode typeHead = myLogHead.addChild("plant_type");
			myLogIndex.typeIndex = typeHead.addChild(""+getPlantType());
			LogNode hasEnoughCO2Head = myLogHead.addChild("has_enough_CO2");
			myLogIndex.hasEnoughCO2Index = hasEnoughCO2Head.addChild(""+hasEnoughCO2);
			LogNode hasEnoughWaterHead = myLogHead.addChild("has_enough_water");
			myLogIndex.hasEnoughWaterIndex = hasEnoughWaterHead.addChild(""+hasEnoughWater);
			LogNode hasEnoughLightHead = myLogHead.addChild("has_enough_light");
			myLogIndex.hasEnoughLightIndex = hasEnoughLightHead.addChild(""+hasEnoughLight);
			LogNode myAgeHead = myLogHead.addChild("age");
			myLogIndex.myAgeIndex = myAgeHead.addChild(""+myAge);
			LogNode O2RetrievedHead = myLogHead.addChild("O2_retrieved");
			myLogIndex.O2RetrievedIndex = O2RetrievedHead.addChild(""+airRetrieved.O2);
			LogNode CO2RetrievedHead = myLogHead.addChild("CO2_retrieved");
			myLogIndex.CO2RetrievedIndex = CO2RetrievedHead.addChild(""+airRetrieved.CO2);
			LogNode otherRetrievedHead = myLogHead.addChild("other_retrieved");
			myLogIndex.otherRetrievedIndex = otherRetrievedHead.addChild(""+airRetrieved.other);
			LogNode totalAreaHead = myLogHead.addChild("total_area");
			myLogIndex.totalAreaIndex = totalAreaHead.addChild(""+totalArea);
			LogNode waterNeededHead = myLogHead.addChild("water_needed");
			myLogIndex.waterNeededIndex = waterNeededHead.addChild(""+waterNeeded);
			LogNode powerNeededHead = myLogHead.addChild("power_needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode CO2NeededHead = myLogHead.addChild("CO2_needed");
			myLogIndex.CO2NeededIndex = CO2NeededHead.addChild(""+CO2Needed);
			LogNode currentWaterLevelHead = myLogHead.addChild("water_level");
			myLogIndex.currentWaterLevelIndex = currentWaterLevelHead.addChild(""+currentWaterLevel);
			LogNode currentPowerLevelHead = myLogHead.addChild("power_level");
			myLogIndex.currentPowerLevelIndex = currentPowerLevelHead.addChild(""+currentPowerLevel);
			LogNode biomassProducedlHead = myLogHead.addChild("biomass_produced");
			myLogIndex.biomassProducedIndex = biomassProducedlHead.addChild(""+biomassProduced);
			LogNode surviveNoWaterHead = myLogHead.addChild("duration_can_survive_without_water");
			myLogIndex.surviveNoWaterIndex = surviveNoWaterHead.addChild(""+surviveNoWater);
			LogNode surviveNoCO2Head = myLogHead.addChild("duration_can_survive_without_CO2");
			myLogIndex.surviveNoCO2Index = surviveNoCO2Head.addChild(""+surviveNoCO2);
			LogNode surviveNoLightHead = myLogHead.addChild("duration_can_survive_without_light");
			myLogIndex.surviveNoLightIndex = surviveNoLightHead.addChild(""+surviveNoLight);
			LogNode noWaterTimeHead = myLogHead.addChild("duration_without_water");
			myLogIndex.noWaterTimeIndex = noWaterTimeHead.addChild(""+noWaterTime);
			LogNode noCO2TimeHead = myLogHead.addChild("duration_without_CO2");
			myLogIndex.noCO2TimeIndex = noCO2TimeHead.addChild(""+noCO2Time);
			LogNode noLightTimeHead = myLogHead.addChild("duration_without_light");
			myLogIndex.noLightTimeIndex = noLightTimeHead.addChild(""+noLightTime);
			LogNode plantsDeadHead = myLogHead.addChild("plants_dead");
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
			myLogIndex.totalAreaIndex.setValue(""+totalArea);
			myLogIndex.waterNeededIndex.setValue(""+waterNeeded);
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
			myLogIndex.CO2NeededIndex.setValue(""+CO2Needed);
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
		public LogNode totalAreaIndex;
		public LogNode waterNeededIndex;
		public LogNode powerNeededIndex;
		public LogNode CO2NeededIndex;
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
