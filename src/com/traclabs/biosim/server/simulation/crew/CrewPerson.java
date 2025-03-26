package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.server.simulation.food.FoodMatter;
import com.traclabs.biosim.server.simulation.food.FoodStore;
import com.traclabs.biosim.server.simulation.framework.SimpleBuffer;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.server.util.MathUtils;
import com.traclabs.biosim.util.MersenneTwister;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * The Crew Person ementation. Eats/drinks/excercises away resources
 * according to a set schedule.
 * 
 * @author Scott Bell
 */

public class CrewPerson extends BaseCrewPerson {
	// How much O2 this crew member consumed in the current tick
	private float O2Consumed = 0f;

	// How much CO2 this crew member produced in the current tick
	private float CO2Produced = 0f;

	// How much food this crew member consumed in the current tick
	private float caloriesConsumed = 0f;

	// How much food this crew member consumed in the current tick (in kg)
	private float foodMassConsumed = 0f;

	// How much potable water this crew member consumed in the current tick
	private float potableWaterConsumed = 0f;

	// How much dirty water this crew member produced in the current tick
	private float dirtyWaterProduced = 0f;

	// How much dry waste this crew member produced in the current tick
	private float dryWasteProduced = 0f;

	// How much grey water this crew member produced in the current tick
	private float greyWaterProduced = 0f;

	// How much O2 this crew member needs to consume in one tick
	private float O2Needed = 0f;

	private float O2Ratio = 0f;

	private float CO2Ratio = 0f;

	// How much potable water this crew member needs to consume in one tick
	private float potableWaterNeeded = 0f;

	// How much food this crew member needs to consume in one tick
	private float caloriesNeeded = 0f;

	// How much water (in moles) is produced in one tick
	private float vaporProduced = 0f;

	private boolean thirsty = false;

	private boolean starving = false;

	private boolean suffocating = false;

	private boolean poisoned = false;

	private boolean fireRisked = false;

	// A mission productivity measure, used when "mission" is specified in the
	// schedule. Not implemented correctly yet.
	private float myMissionProductivity = 0;

	// Used to format floats
	private DecimalFormat numFormat;

	private Random myRandomGen = new MersenneTwister();

	private SimpleBuffer consumedWaterBuffer;

	private SimpleBuffer consumedLowOxygenBuffer;

	private SimpleBuffer highOxygenBuffer;

	private SimpleBuffer consumedCaloriesBuffer;

	private SimpleBuffer consumedCO2Buffer;

	private SimpleBuffer sleepBuffer;

	private SimpleBuffer leisureBuffer;

	private static final float WATER_TILL_DEAD = 5.3f;

	private static final float WATER_RECOVERY_RATE = 0.01f;

	private static final float CALORIE_TILL_DEAD = 180000f;

	private static final float CALORIE_RECOVERY_RATE = 0.0001f;

	private static final float CO2_HIGH_RATIO = 0.06f;

	private static final float CO2_HIGH_TILL_DEAD = 4f;

	private static final float CO2_HIGH_RECOVERY_RATE = 0.005f;

	private static final float O2_HIGH_TILL_DEAD = 24f;

	private static final float O2_HIGH_RECOVERY_RATE = 0.01f;

	private static final float O2_LOW_RATIO = 0.1f;

	private static final float O2_LOW_TILL_DEAD = 2f;

	private static final float O2_LOW_RECOVERY_RATE = 0.01f;

	private static final float LEISURE_TILL_BURNOUT = 168f;

	private static final float LEISURE_RECOVERY_RATE = 90f;

	private static final float AWAKE_TILL_EXHAUSTION = 120;

	private static final float SLEEP_RECOVERY_RATE = 120f;

	public CrewPerson(String pName, float pAge, float pWeight, Sex pSex,
			int pArrivalTick, int pDepartureTick, CrewGroup pBaseCrewGroup,
			CrewGroup pCurrentCrewGroup) {
		this(pName, pAge, pWeight, pSex, pArrivalTick, pDepartureTick,
				pBaseCrewGroup, new Schedule(pBaseCrewGroup));
	}

	public CrewPerson(String pName, float pAge, float pWeight, Sex pSex,
			int pArrivalTick, int pDepartureTick, CrewGroup pBaseCrewGroup,
			Schedule pSchedule) {
		super(pName, pAge, pWeight, pSex, pArrivalTick, pDepartureTick,
				pBaseCrewGroup, pSchedule);
		consumedWaterBuffer = new SimpleBuffer(WATER_TILL_DEAD, WATER_TILL_DEAD);
		consumedCaloriesBuffer = new SimpleBuffer(CALORIE_TILL_DEAD,
				CALORIE_TILL_DEAD);
		consumedCO2Buffer = new SimpleBuffer(CO2_HIGH_TILL_DEAD
				* CO2_HIGH_RATIO, CO2_HIGH_TILL_DEAD * CO2_HIGH_RATIO);
		consumedLowOxygenBuffer = new SimpleBuffer(O2_LOW_TILL_DEAD
				* O2_LOW_RATIO, O2_LOW_TILL_DEAD * O2_LOW_RATIO);
		highOxygenBuffer = new SimpleBuffer(O2_HIGH_TILL_DEAD,
				O2_HIGH_TILL_DEAD);
		sleepBuffer = new SimpleBuffer(AWAKE_TILL_EXHAUSTION,
				AWAKE_TILL_EXHAUSTION);
		leisureBuffer = new SimpleBuffer(LEISURE_TILL_BURNOUT,
				LEISURE_TILL_BURNOUT);
		numFormat = new DecimalFormat("#,##0.0;(#)");
	}

	/**
	 * Resets the state of this crew member
	 */
	public void reset() {
		super.reset();
		consumedWaterBuffer.reset();
		consumedLowOxygenBuffer.reset();
		highOxygenBuffer.reset();
		consumedCaloriesBuffer.reset();
		consumedCO2Buffer.reset();
		sleepBuffer.reset();
		leisureBuffer.reset();
		myMissionProductivity = 0f;
		thirsty = false;
		starving = false;
		suffocating = false;
		poisoned = false;
		fireRisked = false;
		
		O2Consumed = 0f;
		CO2Produced = 0f;
		O2Ratio = 0f;
		CO2Ratio = 0f;
		caloriesConsumed = 0f;
		foodMassConsumed = 0f;
		potableWaterConsumed = 0f;
		dirtyWaterProduced = 0f;
		dryWasteProduced = 0f;
		greyWaterProduced = 0f;
		O2Needed = 0f;
		potableWaterNeeded = 0f;
		caloriesNeeded = 0f;
		vaporProduced = 0f;
	}

	/**
	 * Returns the grey water produced (in liters) by this crew member during
	 * the current tick
	 * 
	 * @return the grey water produced (in liters) by this crew member during
	 *         the current tick
	 */
	public float getGreyWaterProduced() {
		return greyWaterProduced;
	}

	/**
	 * Returns the dirty water produced (in liters) by this crew member during
	 * the current tick
	 * 
	 * @return the dirty water produced (in liters) by this crew member during
	 *         the current tick
	 */
	public float getDirtyWaterProduced() {
		return dirtyWaterProduced;
	}

	/**
	 * Returns the dry waste produced (in liters) by this crew member during the
	 * current tick
	 * 
	 * @return the dry waste produced (in liters) by this crew member during the
	 *         current tick
	 */
	public float getDryWasteProduced() {
		return dirtyWaterProduced;
	}

	/**
	 * Returns the potable water consumed (in liters) by this crew member during
	 * the current tick
	 * 
	 * @return the potable water consumed (in liters) by this crew member during
	 *         the current tick
	 */
	public float getPotableWaterConsumed() {
		return potableWaterConsumed;
	}

	/**
	 * Returns the food consumed (in liters) by this crew member during the
	 * current tick
	 * 
	 * @return the food consumed (in liters) by this crew member during the
	 *         current tick
	 */
	public float getFoodConsumed() {
		return caloriesConsumed;
	}

	/**
	 * Returns the CO2 produced (in moles) by this crew member during the
	 * current tick
	 * 
	 * @return the CO2 produced (in moles) by this crew member during the
	 *         current tick
	 */
	public float getCO2Produced() {
		return CO2Produced;
	}

	/**
	 * Returns the O2 consumed (in moles) by this crew member during the current
	 * tick
	 * 
	 * @return the O2 consumed (in moles) by this crew member during the current
	 *         tick
	 */
	public float getO2Consumed() {
		return O2Consumed;
	}

	/**
	 * Checks whether the crew member is starving or not
	 * 
	 * @return <code>true</code> if the crew memeber is starving,
	 *         <code>false</code> if not.
	 */
	public boolean isStarving() {
		return starving;
	}

	/**
	 * Checks whether the crew member is CO2 poisoned or not
	 * 
	 * @return <code>true</code> if the crew memeber is CO2 poisoned,
	 *         <code>false</code> if not.
	 */
	public boolean isPoisoned() {
		return poisoned;
	}

	/**
	 * Checks whether the crew member is thirsty or not
	 * 
	 * @return <code>true</code> if the crew memeber is thirsty,
	 *         <code>false</code> if not.
	 */
	public boolean isThirsty() {
		return thirsty;
	}

	/**
	 * Checks whether the crew member is suffocating from lack of O2 or not
	 * 
	 * @return <code>true</code> if the crew memeber is suffocating from lack
	 *         of O2, <code>false</code> if not.
	 */
	public boolean isSuffocating() {
		return suffocating;
	}

	public boolean hasFireRisk() {
		return fireRisked;
	}

	protected void checkForMeaningfulActivity() {
		super.checkForMeaningfulActivity();
		String activityName = getCurrentActivity().getName();
		if (activityName.equals("mission")) {
			addProductivity();
		} else if (activityName.startsWith("sleep")
				|| activityName.startsWith("sick")) {
			sleepBuffer.add(SLEEP_RECOVERY_RATE
					* getCurrentCrewGroup().getTickLength());
		} else if (activityName.equals("leisure")) {
			leisureBuffer.add(LEISURE_RECOVERY_RATE
					* getCurrentCrewGroup().getTickLength());
		}
	}

	/**
	 * productivity measure, used for metrics. the longer the crew does this,
	 * the better evaluation (not implemented yet) invoked when crew person
	 * performs "mission" activity
	 */
	private void addProductivity() {
		float caloriePercentFull = MathUtils.calculateSCurve(
				consumedCaloriesBuffer.getAmountMissing(),
				consumedCaloriesBuffer.getCapacity());
		float waterPercentFull = MathUtils.calculateSCurve(consumedWaterBuffer
				.getAmountMissing(), consumedWaterBuffer.getCapacity());
		float oxygenPercentFull = MathUtils.calculateSCurve(
				consumedLowOxygenBuffer.getAmountMissing(),
				consumedLowOxygenBuffer.getCapacity());
		float CO2PercentFull = MathUtils.calculateSCurve(consumedCO2Buffer
				.getAmountMissing(), consumedCO2Buffer.getCapacity());
		float sleepPercentFull = sleepBuffer.getLevel()
				/ sleepBuffer.getCapacity();
		float leisurePercentFull = leisureBuffer.getLevel()
				/ leisureBuffer.getCapacity();

		float averagePercentFull = (caloriePercentFull + waterPercentFull
				+ oxygenPercentFull + CO2PercentFull + sleepPercentFull + leisurePercentFull) / 6f;
		myMissionProductivity += averagePercentFull
				* getCurrentCrewGroup().getTickLength();
	}

	/**
	 * Returns mission productivity.
	 */
	public float getProductivity() {
		return myMissionProductivity;
	}

	/**
	 * Calculate the current O2 needed by the crew memeber given the intensity
	 * of the activity for the current tick. Algorithm derived from "Top Level
	 * Modeling of Crew Component of ALSS" by Goudrazi and Ting
	 * 
	 * @param currentActivityIntensity
	 *            the intensity of the current activity
	 * @return O2 needed in liters
	 */
	private float calculateO2Needed(int currentActivityIntensity) {
		if (currentActivityIntensity < 0)
			return 0f;
		float heartRate = (currentActivityIntensity * 30f) + 15f;
		float a = 0.223804f;
		float b = 5.64f * MathUtils.pow(10f, -7f);
		float resultInLiters = (a + (b * MathUtils.pow(heartRate, 3f) * 60f)); // liters
																				// per
																				// hour,
																				// 5
		myLogger.debug("resultInLiters " + resultInLiters);
		// equation assumes STP
		float moleOfAirPerLiterAtSTP = 22.4f;
		float resultInMoles = resultInLiters / moleOfAirPerLiterAtSTP; // moles
																		// per
																		// hour
		float adjustForTickLength = resultInMoles
				* getCurrentCrewGroup().getTickLength();
		myLogger.debug("resultInMoles " + resultInMoles);
		myLogger.debug("adjustForTickLength " + adjustForTickLength);
		myLogger.debug("adjustForTickLength " + adjustForTickLength);
		return adjustForTickLength;
	}

	/**
	 * Calculate the current CO2 produced by the crew memeber given the O2
	 * consumed for the current tick. Algorithm derived from "Top Level Modeling
	 * of Crew Component of ALSS" by Goudrazi and Ting
	 * 
	 * @param O2Consumed
	 *            the O2 consumed (in moles) during this tick
	 * @return CO2 produced in moles
	 */
	private float calculateCO2Produced(float pO2Consumed) {
		float CO2Produced = pO2Consumed * 0.86f;
		myLogger.debug("O2Consumed = " + O2Consumed);
		myLogger.debug("CO2Produced = " + CO2Produced);
		return CO2Produced;
	}

	/**
	 * Calculate the current food needed (in calories) by the crew memeber given
	 * the intensity of the activity for the current tick. Algorithm derived
	 * from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	 * 
	 * @param currentActivityIntensity
	 *            the activity intensity for the current tick
	 * @return food needed in kilograms
	 */
	private float calculateFoodNeeded(int currentActivityIntensity) {
		if (currentActivityIntensity < 0f)
			return 0f;
		float activityCoefficient = (0.5f * (currentActivityIntensity - 1f)) + 1f;
		float joulesNeeded = 0f;
		if (getSex() == Sex.male)
			if (getAge() < 30f)
				joulesNeeded = (106f * getWeight())
						+ (5040f * activityCoefficient);
			else
				joulesNeeded = (86f * getWeight())
						+ (5990f * activityCoefficient);
		else if (getAge() < 30f)
			joulesNeeded = (106f * getWeight()) + (3200f * activityCoefficient);
		else
			joulesNeeded = (106f * getWeight()) + (6067f * activityCoefficient);
		// make it for one hour
		joulesNeeded /= 24f;
		// make it for the tick interval
		joulesNeeded *= getCurrentCrewGroup().getTickLength();
		caloriesNeeded = joulesNeeded / 4.2f;
		return caloriesNeeded;
	}

	/**
	 * Calculate the dirty water produced given the potable water consumed for
	 * the current tick.
	 * 
	 * @param potableWaterConsumed
	 *            the potable water consumed (in liters) during this tick
	 * @return dirty water produced in liters
	 */
	private float calculateDirtyWaterProduced(float pPotableWaterConsumed) {
		return pPotableWaterConsumed * 0.3625f;
	}

	/**
	 * Calculate the dry waste produced given the potable water consumed for the
	 * current tick.
	 * 
	 * @param foodConsumed
	 *            the food consumed (in kg) during this tick
	 * @return dry waste produced in kg
	 */
	private float calculateDryWasteProduced(float foodConsumed) {
		return foodConsumed * 0.022f;
	}

	/**
	 * Calculate the grey water produced given the potable water consumed for
	 * the current tick.
	 * 
	 * @param potableWaterConsumed
	 *            the potable water consumed (in liters) during this tick
	 * @return grey water produced in liters
	 */
	private float calculateGreyWaterProduced(float pPotableWaterConsumed) {
		return pPotableWaterConsumed * 0.5375f;
	}

	/**
	 * Calculate the current vapor produced by the crew memeber given the O2
	 * consumed for the current tick.
	 * 
	 * @param potableWaterConsumed
	 *            the potable water consumed (in liters) during this tick
	 * @return vapor produced in moles
	 */
	private float calculateVaporProduced(float pPotableWaterConsumed) {
		return waterLitersToMoles(pPotableWaterConsumed * 0.175f);
	}

	/**
	 * Calculate the clean water needed given the activity intensity for the
	 * current tick.
	 * 
	 * @param currentActivityIntensity
	 *            the activity intensity for the current tick
	 * @return potable water needed in liters
	 */
	private float calculateCleanWaterNeeded(int currentActivityIntensity) {
		if (currentActivityIntensity >= 0)
			return 0.1667f * getCurrentCrewGroup().getTickLength();
		return 0f;
	}

	/**
	 * Calculate the CO2 ratio in the breath of air inhaled by the crew member.
	 * Used to see if crew member has inhaled lethal amount of CO2
	 * 
	 * @param aBreath
	 *            the breath inhaled by the crew memeber this tick
	 * @return percentage of CO2 in air
	 */
	private float getCO2Ratio() {
		SimEnvironment[] myAirInputs = getCurrentCrewGroup()
				.getAirConsumerDefinition().getEnvironments();
		if (myAirInputs.length < 1) {
			CO2Ratio = 0f;
			return CO2Ratio;
		}
		if (myAirInputs[0].getTotalMoles() <= 0) {
			CO2Ratio = 0f;
			return CO2Ratio;
		}
		CO2Ratio = myAirInputs[0].getCO2Store().getCurrentLevel()
				/ myAirInputs[0].getTotalMoles();
		return (CO2Ratio);
	}

	/**
	 * Calculate the O2 ratio in the breath of air inhaled by the crew member.
	 * Used to see if crew member has inhaled lethal amount of O2
	 * 
	 * @param aBreath
	 *            the breath inhaled by the crew memeber this tick
	 * @return percentage of O2 in air
	 */
	private float getO2Ratio() {
		SimEnvironment[] myAirInputs = getCurrentCrewGroup()
				.getAirConsumerDefinition().getEnvironments();
		if (myAirInputs.length < 1) {
			O2Ratio = 0f;
			return O2Ratio;
		}
		if (myAirInputs[0].getTotalMoles() <= 0) {
			O2Ratio = 0f;
			return O2Ratio;
		}
		O2Ratio = myAirInputs[0].getO2Store().getCurrentLevel()
				/ myAirInputs[0].getTotalMoles();
		return O2Ratio;
	}

	private void recoverCrew() {
		float tickInterval = getCurrentCrewGroup().getTickLength();
		consumedCaloriesBuffer.add(CALORIE_RECOVERY_RATE
				* consumedCaloriesBuffer.getCapacity() * tickInterval);
		consumedWaterBuffer.add(WATER_RECOVERY_RATE
				* consumedWaterBuffer.getCapacity() * tickInterval);
		consumedLowOxygenBuffer.add(O2_LOW_RECOVERY_RATE
				* consumedLowOxygenBuffer.getCapacity() * tickInterval);
		consumedCO2Buffer.add(CO2_HIGH_RECOVERY_RATE
				* consumedCO2Buffer.getCapacity() * tickInterval);
	}

	protected void afflictAndRecover() {
		afflictCrew();
		healthCheck();
		recoverCrew();
	}

	/**
	 * If not all the resources required were consumed, we damage the crew
	 * member.
	 */
	private void afflictCrew() {
		float tickInterval = getCurrentCrewGroup().getTickLength();
		sleepBuffer.take(1 * tickInterval);
		leisureBuffer.take(1 * tickInterval);
		consumedCaloriesBuffer.take(caloriesNeeded - caloriesConsumed);
		if (caloriesNeeded - caloriesConsumed > 100) {
			myLogger.debug("starving: caloriesNeeded=" + caloriesNeeded
					+ " caloriesConsumed=" + caloriesConsumed + " deficit="
					+ (caloriesNeeded - caloriesConsumed));
			starving = true;
		} else
			starving = false;
		consumedWaterBuffer.take(potableWaterNeeded - potableWaterConsumed);
		if (potableWaterNeeded - potableWaterConsumed > 0)
			thirsty = true;
		else
			thirsty = false;
		myLogger.debug("getO2Ratio() = " + getO2Ratio());
		if (getO2Ratio() < O2_LOW_RATIO) {
			consumedLowOxygenBuffer.take((O2_LOW_RATIO - getO2Ratio())
					* tickInterval);
			suffocating = true;
		} else
			suffocating = false;

		float dangerousOxygenThreshold = getCurrentCrewGroup()
				.getAirConsumerDefinition().getEnvironments()[0]
				.getDangerousOxygenThreshold();
		if (getO2Ratio() > dangerousOxygenThreshold) {
			highOxygenBuffer.take((getO2Ratio() - dangerousOxygenThreshold)
					* tickInterval);
			fireRisked = true;
		} else if (getCO2Ratio() > CO2_HIGH_RATIO) {
			consumedCO2Buffer.take((getCO2Ratio() - CO2_HIGH_RATIO)
					* tickInterval);
			poisoned = true;
		} else
			poisoned = false;
	}

	/**
	 * Checks to see if crew memeber has been lethally damaged (i.e., hasn't
	 * received a resource for too many ticks)
	 */
	private void healthCheck() {
		// check for death
		float randomNumber = myRandomGen.nextFloat();
		myLogger.debug("random number this tick is " + randomNumber);
		float calorieRiskReturn = MathUtils.calculateSCurve(
				consumedCaloriesBuffer.getAmountMissing(),
				consumedCaloriesBuffer.getCapacity());
		float waterRiskReturn = MathUtils.calculateSCurve(consumedWaterBuffer
				.getAmountMissing(), consumedWaterBuffer.getCapacity());
		float oxygenLowRiskReturn = MathUtils.calculateSCurve(
				consumedLowOxygenBuffer.getAmountMissing(),
				consumedLowOxygenBuffer.getCapacity());
		float oxygenHighRiskReturn = MathUtils.calculateSCurve(highOxygenBuffer
				.getAmountMissing(), highOxygenBuffer.getCapacity());
		float CO2RiskReturn = MathUtils.calculateSCurve(consumedCO2Buffer
				.getAmountMissing(), consumedCO2Buffer.getCapacity());
		float sleepRiskReturn = MathUtils.calculateSCurve(sleepBuffer
				.getAmountMissing(), sleepBuffer.getCapacity());
		myLogger.debug(getName());
		myLogger.debug("\tcalorie taken=" + (caloriesNeeded - caloriesConsumed)
				+ ", recovered " + CALORIE_RECOVERY_RATE
				* consumedCaloriesBuffer.getCapacity() + " calorie risk level="
				+ calorieRiskReturn + " (level="
				+ consumedCaloriesBuffer.getLevel() + ", capacity="
				+ consumedCaloriesBuffer.getCapacity() + ")");
		myLogger.debug("\twater taken="
				+ (potableWaterNeeded - potableWaterConsumed) + ", recovered "
				+ WATER_RECOVERY_RATE * consumedWaterBuffer.getCapacity()
				+ " thirst risk level=" + waterRiskReturn + " (level="
				+ consumedWaterBuffer.getLevel() + ", capacity="
				+ consumedWaterBuffer.getCapacity() + ")");
		myLogger.debug("\tlow oxygen taken=" + (O2Needed - O2Consumed)
				+ ", low recovered " + O2_LOW_RECOVERY_RATE
				* consumedLowOxygenBuffer.getCapacity() + " low O2 risk level="
				+ oxygenLowRiskReturn + " (level="
				+ consumedLowOxygenBuffer.getLevel() + ", capacity="
				+ consumedLowOxygenBuffer.getCapacity() + ")");
		float dangerousOxygenThreshold = getCurrentCrewGroup()
				.getAirConsumerDefinition().getEnvironments()[0]
				.getDangerousOxygenThreshold();
		myLogger.debug("\thigh oxygen taken=" + (O2Needed - O2Consumed)
				+ ", high recovered " + O2_HIGH_RECOVERY_RATE
				* highOxygenBuffer.getCapacity() + " high O2 risk level="
				+ oxygenHighRiskReturn + " (level="
				+ highOxygenBuffer.getLevel() + ", capacity="
				+ highOxygenBuffer.getCapacity() + ")");

		myLogger.debug("\tCO2 taken=" + (getCO2Ratio() - CO2_HIGH_RATIO)
				+ ", recovered " + CO2_HIGH_RECOVERY_RATE
				* consumedCO2Buffer.getCapacity() + " CO2 risk level="
				+ CO2RiskReturn + " (level=" + consumedCO2Buffer.getLevel()
				+ ", capacity=" + consumedCO2Buffer.getCapacity() + ")");
		myLogger.debug("\tsleep (level=" + sleepBuffer.getLevel()
				+ ", capacity=" + sleepBuffer.getCapacity() + ")");
		myLogger.debug("\tCO2 ration =" + getCO2Ratio()
				+ ", DANGEROUS_CO2_RATION=" + CO2_HIGH_RATIO);
		myLogger.debug("\tsleep (level=" + sleepBuffer.getLevel()
				+ ", capacity=" + sleepBuffer.getCapacity() + ")");
		myLogger.debug("\tCO2 ration =" + getCO2Ratio()
				+ ", DANGEROUS_CO2_RATION=" + CO2_HIGH_RATIO);
		/*
		if (sleepRiskReturn > (randomNumber + 0.05f)) {
			sicken();
			myLogger.info(getName()
					+ " has fallen ill from exhaustion (risk was "
					+ numFormat.format(sleepRiskReturn * 100) + "%) @ tick "
					+ getCurrentCrewGroup().getMyTicks());
		}
		*/
		if (calorieRiskReturn > randomNumber) {
			kill();
			myLogger.info(getName() + " has died from starvation on tick "
					+ getCurrentCrewGroup().getMyTicks() + " (risk was "
					+ calorieRiskReturn * 100 + "%)");
		} else if (waterRiskReturn > randomNumber) {
			kill();
			myLogger.info(getName() + " has died from thirst on tick "
					+ getCurrentCrewGroup().getMyTicks() + " (risk was "
					+ waterRiskReturn * 100 + "%)");
		} else if (oxygenLowRiskReturn > randomNumber) {
			kill();
			SimEnvironment[] myAirInputs = getCurrentCrewGroup()
					.getAirConsumerDefinition().getEnvironments();
			myLogger.info(getName() + " has died from lack of oxygen on tick "
					+ getCurrentCrewGroup().getMyTicks() + " (risk was "
					+ oxygenLowRiskReturn * 100 + "%)");
			logEnvironmentConditions();
		} else if (oxygenHighRiskReturn > randomNumber) {
			kill();
			SimEnvironment[] myAirInputs = getCurrentCrewGroup()
					.getAirConsumerDefinition().getEnvironments();
			myLogger.info(getName()
					+ " has died from oxygen flammability on tick "
					+ getCurrentCrewGroup().getMyTicks() + " (risk was "
					+ oxygenHighRiskReturn * 100 + "%)");
			logEnvironmentConditions();
		} else if (CO2RiskReturn > randomNumber) {
			kill();
			SimEnvironment[] myAirInputs = getCurrentCrewGroup()
					.getAirConsumerDefinition().getEnvironments();
			myLogger.info(getName() + " has died from CO2 poisoning on tick "
					+ getCurrentCrewGroup().getMyTicks() + " (risk was "
					+ CO2RiskReturn * 100 + "%)");

		}
	}

	public void kill() {
		super.kill();
		myLogger.info(getName() + " killed");
		O2Consumed = 0f;
		CO2Produced = 0f;
		caloriesConsumed = 0f;
		potableWaterConsumed = 0f;
		dirtyWaterProduced = 0f;
		greyWaterProduced = 0f;
		caloriesConsumed = 0f;
	}

	private void logEnvironmentConditions() {
		SimEnvironment[] myAirInputs = getCurrentCrewGroup()
				.getAirConsumerDefinition().getEnvironments();
		myLogger
				.info(getName() + " Environmental conditions were: 02="
						+ myAirInputs[0].getO2Store().getCurrentLevel()
						+ ", CO2="
						+ myAirInputs[0].getCO2Store().getCurrentLevel()
						+ ", N="
						+ myAirInputs[0].getNitrogenStore().getCurrentLevel()
						+ ", water="
						+ myAirInputs[0].getVaporStore().getCurrentLevel()
						+ ", other="
						+ myAirInputs[0].getOtherStore().getCurrentLevel());
	}

	private static float waterLitersToMoles(float pLiters) {
		return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
	}

	private void eatFood() {
		FoodMatter[] foodConsumed = FoodConsumerDefinition
				.getCaloriesFromStore(getCurrentCrewGroup()
						.getFoodConsumerDefinition(), caloriesNeeded);
		foodMassConsumed = calculateFoodMass(foodConsumed);
		if ((foodConsumed.length == 0)
				|| (getCurrentCrewGroup().getFoodConsumerDefinition()
						.getStores().length == 0))
			caloriesConsumed = 0f;
		else {
			caloriesConsumed = FoodStore.calculateCalories(foodConsumed);
			potableWaterNeeded -= FoodStore
					.calculateWaterContent(foodConsumed);
		}
	}

	private static float calculateFoodMass(FoodMatter[] pMatter) {
		float mass = 0f;
		if (pMatter == null)
			return 0f;
		for (int i = 0; i < pMatter.length; i++)
			mass += pMatter[i].mass;
		return mass;
	}

	/**
	 * Attempts to consume resource for this crew memeber. inhales/drinks/eats,
	 * then exhales/excretes
	 */
	protected void consumeAndProduceResources() {
		// calculate consumption
		int currentActivityIntensity = getCurrentActivity()
				.getActivityIntensity();
		O2Needed = calculateO2Needed(currentActivityIntensity);
		potableWaterNeeded = calculateCleanWaterNeeded(currentActivityIntensity);
		caloriesNeeded = calculateFoodNeeded(currentActivityIntensity);
		dirtyWaterProduced = calculateDirtyWaterProduced(potableWaterNeeded);
		dryWasteProduced = calculateDryWasteProduced(foodMassConsumed);
		greyWaterProduced = calculateGreyWaterProduced(potableWaterNeeded);
		CO2Produced = calculateCO2Produced(O2Needed);
		vaporProduced = calculateVaporProduced(potableWaterNeeded);

		// consume
		eatFood();
		float myFractionOfCrew = 1f / getCurrentCrewGroup().getCrewSize();
		potableWaterConsumed = StoreFlowRateControllable
				.getFractionalResourceFromStores(getCurrentCrewGroup(),
						getCurrentCrewGroup()
								.getPotableWaterConsumerDefinition(),
						potableWaterNeeded, myFractionOfCrew);
		SimEnvironment[] myAirInputs = getCurrentCrewGroup()
				.getAirConsumerDefinition().getEnvironments();
		SimEnvironment[] myAirOutputs = getCurrentCrewGroup()
				.getAirProducerDefinition().getEnvironments();
		if (myAirInputs.length < 1) {
			O2Consumed = 0f;
		} else {
			O2Consumed = myAirInputs[0].getO2Store().take(O2Needed);
		}

		// produce
		StoreFlowRateControllable.pushFractionalResourceToStores(
				getCurrentCrewGroup(), getCurrentCrewGroup()
						.getDirtyWaterProducerDefinition(), dirtyWaterProduced,
				myFractionOfCrew);
		StoreFlowRateControllable.pushFractionalResourceToStores(
				getCurrentCrewGroup(), getCurrentCrewGroup()
						.getGreyWaterProducerDefinition(), greyWaterProduced,
				myFractionOfCrew);
		StoreFlowRateControllable.pushFractionalResourceToStores(
				getCurrentCrewGroup(), getCurrentCrewGroup()
						.getDryWasteProducerDefinition(), dryWasteProduced,
				myFractionOfCrew);

		if (myAirOutputs.length > 0) {
			myAirOutputs[0].getCO2Store().add(CO2Produced);
			myAirOutputs[0].getVaporStore().add(vaporProduced);
		}
	}

	public void log() {
		super.log();
		myLogger.debug("O2_consumed=" + O2Consumed);
		myLogger.debug("CO2_produced=" + CO2Produced);
		myLogger.debug("calories_consumed=" + caloriesConsumed);
		myLogger.debug("potable_water_consumed=" + potableWaterConsumed);
		myLogger.debug("dirty_water_produced=" + dirtyWaterProduced);
		myLogger.debug("grey_water_produced=" + greyWaterProduced);
		myLogger.debug("O2_needed=" + O2Needed);
		myLogger.debug("potable_water_needed=" + potableWaterNeeded);
		myLogger.debug("calories_needed=" + caloriesNeeded);
		myLogger.debug("vapor_produced=" + vaporProduced);
		logEnvironmentConditions();
	}

}