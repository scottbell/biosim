package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.idl.air.*;
import biosim.idl.util.*;
import biosim.server.framework.*;
/**
 * The Biomass RS is essentially responsible for growing plants.  The plant matter (biomass) is fed into the food processor to create food
 * for the crew.  The plants can also (along with the AirRS) take CO2 out of the air and add O2.
 *
 * @author    Scott Bell
 */

public class BiomassRSImpl extends BioModuleImpl implements BiomassRSOperations {
	//During any given tick, this much water is needed for the plants
	private float waterNeeded = 0.10f;
	//During any given tick, this much power is needed for the Biomass RS to provide light to the plants
	private float powerNeeded = 100;
	//The power consumed (in watts) by the Biomass RS at the current tick
	private float currentPowerConsumed = 0f;
	//The grey water consumed (in liters) by the Biomass RS at the current tick
	private float currentGreyWaterConsumed = 0f;
	//The biomass produced (in kilograms) by the Biomass RS at the current tick
	private float currentBiomassProduced = 0f;
	//The CO2 consumed (in liters) by the Biomass RS at the current tick
	private float currentCO2Consumed= 0f;
	//The O2 produced (in liters) by the Biomass RS at the current tick
	private float currentO2Produced = 0f;
	//How long the Biomass RS has been without power (in ticks)
	private int noPowerTime = 0;
	//How long the Biomass RS has been without water (in ticks)
	private int noWaterTime = 0;
	//How long the Biomass RS has been without CO2 (in ticks)
	private int noCO2Time = 0;
	//How long the Biomass RS has been with too much O2 (in ticks)
	private int tooMuchO2Time = 0;
	//Flag switched when the Biomass RS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//Flag to determine if the Biomass RS has enough power
	private boolean systemHasEnoughPower = false;
	//Flag to determine if the Biomass RS has enough water
	private boolean plantsHaveEnoughWater = false;
	//Flag to determine if the Biomass RS has enough CO2
	private boolean plantsHaveEnoughCO2 = false;
	//Flag to determine if the Biomass RS has too much O2
	private boolean O2Poisoned = false;
	//Flag to determine if the plants in the Biomass RS are dead
	private boolean plantsDead = false;
	private Breath airRetrieved;
	private float CO2Needed;
	//References to the servers the Biomass RS takes/puts resources (like air, water, etc)
	private SimEnvironment myEnvironment;
	private GreyWaterStore myGreyWaterStore;
	private PowerStore myPowerStore;
	private BiomassStore myBiomassStore;
	private LogIndex myLogIndex;

	/**
	* Resets production/consumption levels and death/affliction flags
	*/
	public void reset(){
		currentPowerConsumed = 0f;
		currentGreyWaterConsumed = 0f;
		currentBiomassProduced = 0f;
		currentCO2Consumed= 0f;
		currentO2Produced = 0f;
		noPowerTime = 0;
		noWaterTime = 0;
		noCO2Time = 0;
		tooMuchO2Time = 0;
		systemHasEnoughPower = false;
		plantsHaveEnoughWater = false;
		plantsHaveEnoughCO2 = false;
		O2Poisoned = false;
		plantsDead = false;
	}

	/**
	* Returns the power consumed (in watts) by the Biomass RS during the current tick
	* @return the power consumed (in watts) by the Biomass RS during the current tick
	*/
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	/**
	* Returns the CO2 consumed (in liters) by the plants member during the current tick
	* @return the CO2 consumed (in liters) by the plants member during the current tick
	*/
	public float getCO2Consumed(){
		return currentCO2Consumed;
	}

	/**
	* Returns the O2 produced (in liters) by the plants member during the current tick
	* @return the O2 produced (in liters) by the plants member during the current tick
	*/
	public float getO2Produced(){
		return currentO2Produced;
	}

	/**
	* Returns the grey water consumed (in liters) by the plants member during the current tick
	* @return the grey water consumed (in liters) by the plants member during the current tick
	*/
	public float getGreyWaterConsumed(){
		return currentGreyWaterConsumed;
	}

	/**
	* Returns the biomass produced (in kilograms) by the plants member during the current tick
	* @return the biomass produced (in kilograms) by the plants member during the current tick
	*/
	public float getBiomassProduced(){
		return currentBiomassProduced;
	}

	/**
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("GreyWaterStore"));
				myEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("BiomassStore"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}

	/**
	* Attempts to collect enough power from the Power PS to run the Biomass RS for one tick.
	*/
	private void gatherPower(){
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			systemHasEnoughPower = false;
			noPowerTime++;
		}
		else{
			systemHasEnoughPower = true;
			noPowerTime = 0;
		}
	}

	/**
	* Attempts to collect enough water from the Grey Water Store for the plants for one tick.
	*/
	private void gatherWater(){
		currentGreyWaterConsumed = myGreyWaterStore.take(waterNeeded);
		if (currentGreyWaterConsumed < waterNeeded){
			plantsHaveEnoughWater = false;
			noWaterTime++;
		}
		else
			plantsHaveEnoughWater = true;
		noWaterTime = 0;
	}

	/**
	* Calculate the O2 ratio in the breath of air inhaled by the plants.
	* Used to see if plants has inhaled lethal amount of O2
	* @param aBreath the breath inhaled by the plants this tick
	* @return percentage of O2 in air
	*/
	private float getO2Ratio(Breath aBreath){
		Double ratio = new Double(aBreath.O2 / (aBreath.O2 + aBreath.CO2 + aBreath.other));
		return ratio.floatValue();
	}

	/**
	* Calculate the O2 needed for the current tick.
	* @return O2 needed in ticks
	*/
	private float calculateCO2Needed(){
		Double result = new Double(40.5);
		return result.floatValue();
	}

	/**
	* Calculate the O2 produced given the CO2 consumed for the current tick.
	* @param CO2Consumed the CO2 consumed (in liters) during this tick
	* @return O2 produced in liters
	*/
	private float calculateO2Produced(float CO2Consumed){
		Double result = new Double(CO2Consumed * 0.86);
		return result.floatValue();
	}

	/**
	* The "inhalation" and "exhalation" of the plants for one tick.
	* The plants inhale a needed amount of CO2 (along with O2 and other gasses).
	* and exhale fraction of the CO2 inhaled, a multiple of the O2 inhaled, and the same amount of other gasses inhaled.
	*/
	private void gatherAir(){
		CO2Needed = calculateCO2Needed();
		airRetrieved = myEnvironment.takeCO2Breath(CO2Needed);
		currentCO2Consumed = airRetrieved.CO2;
		if (currentCO2Consumed < CO2Needed){
			plantsHaveEnoughCO2 = false;
			noCO2Time++;
		}
		else{
			plantsHaveEnoughCO2 = true;
			noCO2Time = 0;
		}
		if (getO2Ratio(airRetrieved) < .80){
			O2Poisoned = false;
			tooMuchO2Time++;
		}
		else{
			O2Poisoned = true;
			tooMuchO2Time = 0;
		}
		currentO2Produced = calculateO2Produced(currentCO2Consumed);
		myEnvironment.addO2(currentO2Produced);
		myEnvironment.addOther(airRetrieved.other);
	}

	/**
	* Checks whether plants are dead or not
	* @return <code>true</code> if the plants are dead, <code>false</code> if not.
	*/
	public boolean isDead(){
		return plantsDead;
	}

	/**
	* Checks whether plants have enough CO2 or not
	* @return <code>true</code> if the plants have enough CO2, <code>false</code> if not.
	*/
	public boolean hasCO2(){
		return plantsHaveEnoughCO2;
	}

	/**
	* Checks whether plants have enough water or not
	* @return <code>true</code> if the plants have enough water, <code>false</code> if not.
	*/
	public boolean hasWater(){
		return plantsHaveEnoughWater;
	}

	/**
	* Checks whether Biomass RS has enough power or not
	* @return <code>true</code> if the Biomass RS has enough power, <code>false</code> if not.
	*/
	public boolean hasPower(){
		return systemHasEnoughPower;
	}

	/**
	* Checks whether the plants are O2 poisoned or not
	* @return <code>true</code> if the plants are O2 poisoned, <code>false</code> if not.
	*/
	public boolean isO2Poisoned(){
		return O2Poisoned;
	}

	/**
	* If the plants have received light (from power), water, little O2, and enough CO2, they provide some biomass to
	* put into the store.
	*/
	private void createBiomass(){
		if (plantsHaveEnoughCO2 && plantsHaveEnoughWater && systemHasEnoughPower && (!O2Poisoned)){
			currentBiomassProduced = 0.2f;
			myBiomassStore.add(currentBiomassProduced);
		}
	}

	/**
	* Attempts to consume resource for the Biomass RS.
	* inhales/drinks, then exhales
	*/
	private void consumeResources(){
		//gather power for each system
		gatherPower();
		//gather water for system
		gatherWater();
		//gather air for system
		gatherAir();
	}

	/**
	* Checks to see if plants have been lethally damaged (i.e., haven't received a resource for too many ticks)
	*/
	private void deathCheck(){
		if (noPowerTime > 220)
			plantsDead = true;
		if (noWaterTime > 180)
			plantsDead = true;
		if (noCO2Time > 4)
			plantsDead = true;
		if (tooMuchO2Time > 8)
			plantsDead = true;
		if (plantsDead){
			currentPowerConsumed = 0f;
			currentGreyWaterConsumed = 0f;
			currentBiomassProduced = 0f;
			currentCO2Consumed= 0f;
			currentO2Produced = 0f;
		}
	}

	/**
	* When ticked, the Biomass RS does the following
	* on the condition that the plants aren't dead it.
	* 1) attempts to collect references to various server (if not already done).
	* 4) consumes air/light/water, exhales.
	* 5) creates biomass (if possible).
	* 6) checks whether afflictions (if any) are fatal
	*/
	public void tick(){
		if (plantsDead)
			return;
		else{
			collectReferences();
			consumeResources();
			createBiomass();
			deathCheck();
		}
		if (moduleLogging)
			log();
	}

	/**
	* Returns the name of this module (BiomassRS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassRS";
	}

	public void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode waterNeededHead = myLog.getHead().addChild("Water Needed");
			myLogIndex.waterNeededIndex = waterNeededHead.addChild(""+waterNeeded);
			LogNode powerNeededHead = myLog.getHead().addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode CO2NeededHead = myLog.getHead().addChild("CO2 Needed");
			myLogIndex.CO2NeededIndex = CO2NeededHead.addChild(""+CO2Needed);
			LogNode currentGreyWaterConsumedHead = myLog.getHead().addChild("Grey Water Consumed");
			myLogIndex.currentGreyWaterConsumedIndex = currentGreyWaterConsumedHead.addChild(""+currentGreyWaterConsumed);
			LogNode currentBiomassProducedHead = myLog.getHead().addChild("Biomass Produced");
			myLogIndex.currentBiomassProducedIndex = currentBiomassProducedHead.addChild(""+currentBiomassProduced);
			LogNode currentCO2ConsumedHead = myLog.getHead().addChild("CO2 Consumed");
			myLogIndex.currentCO2ConsumedIndex = currentCO2ConsumedHead.addChild(""+currentCO2Consumed);
			LogNode currentO2ProducedHead = myLog.getHead().addChild("O2 Produced");
			myLogIndex.currentO2ProducedIndex = currentO2ProducedHead.addChild(""+currentO2Produced);
			LogNode noPowerTimeHead = myLog.getHead().addChild("No Power Time");
			myLogIndex.noPowerTimeIndex = noPowerTimeHead.addChild(""+noPowerTime);
			LogNode noWaterTimeHead = myLog.getHead().addChild("No Water Time");
			myLogIndex.noWaterTimeIndex = noWaterTimeHead.addChild(""+noWaterTime);
			LogNode noCO2TimeHead = myLog.getHead().addChild("No CO2 Time");
			myLogIndex.noCO2TimeIndex = noCO2TimeHead.addChild(""+noCO2Time);
			LogNode tooMuchO2TimeHead = myLog.getHead().addChild("Too much O2 Time");
			myLogIndex.tooMuchO2TimeIndex = tooMuchO2TimeHead.addChild(""+tooMuchO2Time);
			LogNode systemHasEnoughPowerHead = myLog.getHead().addChild("System has enough power");
			myLogIndex.systemHasEnoughPowerIndex = systemHasEnoughPowerHead.addChild(""+systemHasEnoughPower);
			LogNode plantsHaveEnoughWaterHead = myLog.getHead().addChild("Plants have enough water");
			myLogIndex.plantsHaveEnoughWaterIndex = plantsHaveEnoughWaterHead.addChild(""+plantsHaveEnoughWater);
			LogNode plantsHaveEnoughCO2Head = myLog.getHead().addChild("Plants have enough CO2");
			myLogIndex.plantsHaveEnoughCO2Index = plantsHaveEnoughCO2Head.addChild(""+plantsHaveEnoughCO2);
			LogNode O2PoisonedHead = myLog.getHead().addChild("O2 Poisoned");
			myLogIndex.O2PoisonedIndex = O2PoisonedHead.addChild(""+O2Poisoned);
			LogNode plantsDeadHead = myLog.getHead().addChild("Plants Dead");
			myLogIndex.plantsDeadIndex = plantsDeadHead.addChild(""+plantsDead);
			LogNode airRetrievedHead = myLog.getHead().addChild("Air Retrieved (O2 # CO2 # other)");
			myLogIndex.airRetrievedIndex = airRetrievedHead.addChild(airRetrieved.O2 + " # " +airRetrieved.CO2 +" # " +airRetrieved.other);
			
			logInitialized = true;
		}
		else{
			myLogIndex.waterNeededIndex.setValue(""+waterNeeded);
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
			myLogIndex.CO2NeededIndex.setValue(""+CO2Needed);
			myLogIndex.currentGreyWaterConsumedIndex.setValue(""+currentGreyWaterConsumed);
			myLogIndex.currentBiomassProducedIndex.setValue(""+currentBiomassProduced);
			myLogIndex.currentCO2ConsumedIndex.setValue(""+currentCO2Consumed);
			myLogIndex.currentO2ProducedIndex.setValue(""+currentO2Produced);
			myLogIndex.noPowerTimeIndex.setValue(""+noPowerTime);
			myLogIndex.noWaterTimeIndex.setValue(""+noWaterTime);
			myLogIndex.noCO2TimeIndex.setValue(""+noCO2Time);
			myLogIndex.tooMuchO2TimeIndex.setValue(""+tooMuchO2Time);
			myLogIndex.systemHasEnoughPowerIndex.setValue(""+systemHasEnoughPower);
			myLogIndex.plantsHaveEnoughWaterIndex.setValue(""+plantsHaveEnoughWater);
			myLogIndex.plantsHaveEnoughCO2Index.setValue(""+plantsHaveEnoughCO2);
			myLogIndex.O2PoisonedIndex.setValue(""+O2Poisoned);
			myLogIndex.plantsDeadIndex.setValue(""+plantsDead);
			myLogIndex.airRetrievedIndex.setValue(airRetrieved.O2 + " # " +airRetrieved.CO2 +" # " +airRetrieved.other);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode waterNeededIndex;
		public LogNode airRetrievedIndex;
		public LogNode powerNeededIndex;
		public LogNode CO2NeededIndex;
		public LogNode currentPowerConsumedIndex;
		public LogNode currentGreyWaterConsumedIndex;
		public LogNode currentBiomassProducedIndex;
		public LogNode currentCO2ConsumedIndex;
		public LogNode currentO2ProducedIndex;
		public LogNode noPowerTimeIndex;
		public LogNode noWaterTimeIndex;
		public LogNode noCO2TimeIndex;
		public LogNode tooMuchO2TimeIndex;
		public LogNode systemHasEnoughPowerIndex;
		public LogNode plantsHaveEnoughWaterIndex;
		public LogNode plantsHaveEnoughCO2Index;
		public LogNode O2PoisonedIndex;
		public LogNode plantsDeadIndex;
	}
}
