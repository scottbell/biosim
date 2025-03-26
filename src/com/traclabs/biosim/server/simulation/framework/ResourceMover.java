package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.environment.Air;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.food.BiomassConsumerDefinition;
import com.traclabs.biosim.server.simulation.food.BiomassProducerDefinition;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.server.simulation.food.FoodProducerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerOperations;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.server.simulation.water.*;

/**
 * The basic Resource Mover implementation. Can be configured to take any
 * modules as input, and any modules as output. It takes as much as it can (max
 * taken set by maxFlowRates) from one module and pushes it into another module.
 * Functionally equivalent to an Resource Mover at this point.
 * 
 * @author Scott Bell
 */

public class ResourceMover extends SimBioModule implements
		PowerConsumerOperations, PotableWaterConsumerOperations,
		GreyWaterConsumerOperations, WaterConsumerOperations,
		DirtyWaterConsumerOperations, O2ConsumerOperations,
		CO2ConsumerOperations, AirConsumerOperations,
		BiomassConsumerOperations, FoodConsumerOperations,
		DryWasteConsumerOperations, H2ConsumerOperations,
		NitrogenConsumerOperations, PowerProducerOperations,
		PotableWaterProducerOperations, GreyWaterProducerOperations,
		WaterProducerOperations, DirtyWaterProducerOperations,
		O2ProducerOperations, CO2ProducerOperations, AirProducerOperations,
		BiomassProducerOperations, FoodProducerOperations,
		DryWasteProducerOperations, H2ProducerOperations,
		NitrogenProducerOperations {

	// Consumers, Producers
	private PowerConsumerDefinition myPowerConsumerDefinition;

	private PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

	private GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

	private DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;

	private O2ConsumerDefinition myO2ConsumerDefinition;

	private CO2ConsumerDefinition myCO2ConsumerDefinition;

	private H2ConsumerDefinition myH2ConsumerDefinition;

	private NitrogenConsumerDefinition myNitrogenConsumerDefinition;

	private AirConsumerDefinition myAirConsumerDefinition;

	private BiomassConsumerDefinition myBiomassConsumerDefinition;

	private FoodConsumerDefinition myFoodConsumerDefinition;

	private DryWasteConsumerDefinition myDryWasteConsumerDefinition;

	private WaterConsumerDefinition myWaterConsumerDefinition;

	private PowerProducerDefinition myPowerProducerDefinition;

	private PotableWaterProducerDefinition myPotableWaterProducerDefinition;

	private GreyWaterProducerDefinition myGreyWaterProducerDefinition;

	private DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

	private O2ProducerDefinition myO2ProducerDefinition;

	private CO2ProducerDefinition myCO2ProducerDefinition;

	private H2ProducerDefinition myH2ProducerDefinition;

	private NitrogenProducerDefinition myNitrogenProducerDefinition;

	private AirProducerDefinition myAirProducerDefinition;

	private BiomassProducerDefinition myBiomassProducerDefinition;

	private FoodProducerDefinition myFoodProducerDefinition;

	private DryWasteProducerDefinition myDryWasteProducerDefinition;

	private WaterProducerDefinition myWaterProducerDefinition;

	public ResourceMover(int pID, String pName) {
		super(pID, pName);
		myPowerConsumerDefinition = new PowerConsumerDefinition(this);
		myPotableWaterConsumerDefinition = new PotableWaterConsumerDefinition(
				this);
		myGreyWaterConsumerDefinition = new GreyWaterConsumerDefinition(
				this);
		myDirtyWaterConsumerDefinition = new DirtyWaterConsumerDefinition(
				this);
		myO2ConsumerDefinition = new O2ConsumerDefinition(this);
		myCO2ConsumerDefinition = new CO2ConsumerDefinition(this);
		myH2ConsumerDefinition = new H2ConsumerDefinition(this);
		myNitrogenConsumerDefinition = new NitrogenConsumerDefinition(
				this);
		myAirConsumerDefinition = new AirConsumerDefinition(this);
		myBiomassConsumerDefinition = new BiomassConsumerDefinition(
				this);
		myFoodConsumerDefinition = new FoodConsumerDefinition(this);
		myDryWasteConsumerDefinition = new DryWasteConsumerDefinition(
				this);
		myWaterConsumerDefinition = new WaterConsumerDefinition(this);

		myPowerProducerDefinition = new PowerProducerDefinition(this);
		myPotableWaterProducerDefinition = new PotableWaterProducerDefinition(
				this);
		myGreyWaterProducerDefinition = new GreyWaterProducerDefinition(
				this);
		myDirtyWaterProducerDefinition = new DirtyWaterProducerDefinition(
				this);
		myO2ProducerDefinition = new O2ProducerDefinition(this);
		myCO2ProducerDefinition = new CO2ProducerDefinition(this);
		myH2ProducerDefinition = new H2ProducerDefinition(this);
		myNitrogenProducerDefinition = new NitrogenProducerDefinition(
				this);
		myAirProducerDefinition = new AirProducerDefinition(this);
		myBiomassProducerDefinition = new BiomassProducerDefinition(
				this);
		myFoodProducerDefinition = new FoodProducerDefinition(this);
		myDryWasteProducerDefinition = new DryWasteProducerDefinition(
				this);
		myWaterProducerDefinition = new WaterProducerDefinition(this);
	}

	public void tick() {
		super.tick();
		getAndPushResources();
	}

	protected void performMalfunctions() {
		for (Malfunction malfunction : myMalfunctions.values()) {
			malfunction.setPerformed(true);
		}
		if (myMalfunctions.values().size() > 0) {
			myPowerConsumerDefinition.malfunction();
			myPotableWaterConsumerDefinition.malfunction();
			myGreyWaterConsumerDefinition.malfunction();
			myDirtyWaterConsumerDefinition.malfunction();
			myO2ConsumerDefinition.malfunction();
			myCO2ConsumerDefinition.malfunction();
			myH2ConsumerDefinition.malfunction();
			myNitrogenConsumerDefinition.malfunction();
			myAirConsumerDefinition.malfunction();
			myBiomassConsumerDefinition.malfunction();
			myFoodConsumerDefinition.malfunction();
			myDryWasteConsumerDefinition.malfunction();
			myWaterConsumerDefinition.malfunction();

			myPowerProducerDefinition.malfunction();
			myPotableWaterProducerDefinition.malfunction();
			myGreyWaterProducerDefinition.malfunction();
			myDirtyWaterProducerDefinition.malfunction();
			myO2ProducerDefinition.malfunction();
			myCO2ProducerDefinition.malfunction();
			myH2ProducerDefinition.malfunction();
			myNitrogenProducerDefinition.malfunction();
			myAirProducerDefinition.malfunction();
			myBiomassProducerDefinition.malfunction();
			myFoodProducerDefinition.malfunction();
			myDryWasteProducerDefinition.malfunction();
			myWaterProducerDefinition.malfunction();
		}
	}

	private void getAndPushResources() {
		float powerGathered = myPowerConsumerDefinition
				.getMostResourceFromStores();
		myPowerProducerDefinition.pushResourceToStores(powerGathered);

		float potableWaterGathered = myPotableWaterConsumerDefinition
				.getMostResourceFromStores();
		myPotableWaterProducerDefinition
				.pushResourceToStores(potableWaterGathered);

		float greyWaterGathered = myGreyWaterConsumerDefinition
				.getMostResourceFromStores();
		myGreyWaterProducerDefinition
				.pushResourceToStores(greyWaterGathered);

		float dirtyWaterGathered = myDirtyWaterConsumerDefinition
				.getMostResourceFromStores();
		myDirtyWaterProducerDefinition
				.pushResourceToStores(dirtyWaterGathered);

		float biomassGathered = myBiomassConsumerDefinition
				.getMostResourceFromStores();
		myBiomassProducerDefinition.pushResourceToStores(biomassGathered);

		float foodGathered = myFoodConsumerDefinition
				.getMostResourceFromStores();
		myFoodProducerDefinition.pushResourceToStores(foodGathered);

		float dryWasteGathered = myDryWasteConsumerDefinition
				.getMostResourceFromStores();
		myDryWasteProducerDefinition.pushResourceToStores(dryWasteGathered);

		float O2Gathered = myO2ConsumerDefinition
				.getMostResourceFromStores();
		myO2ProducerDefinition.pushResourceToStores(O2Gathered);

		float CO2Gathered = myCO2ConsumerDefinition
				.getMostResourceFromStores();
		myCO2ProducerDefinition.pushResourceToStores(CO2Gathered);

		float nitrogenGathered = myNitrogenConsumerDefinition
				.getMostResourceFromStores();
		myNitrogenProducerDefinition.pushResourceToStores(nitrogenGathered);

		float H2Gathered = myH2ConsumerDefinition
				.getMostResourceFromStores();
		myH2ProducerDefinition.pushResourceToStores(H2Gathered);

		Air airGathered = myAirConsumerDefinition
				.getMostAirFromEnvironments();
		// TODO currently only pushes to one environment
		if (myAirProducerDefinition.getEnvironments().length > 0)
			myAirProducerDefinition.pushAirToEnvironment(airGathered, 0);
		airGathered = null;
	}

	private static float waterLitersToMoles(float pLiters) {
		return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
	}

	private static float waterMolesToLiters(float pMoles) {
		return (pMoles * 18.01524f) / 1000f; // 1000g/liter, 18.01524g/mole
	}

	protected String getMalfunctionName(MalfunctionIntensity pIntensity,
			MalfunctionLength pLength) {
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Temporary Production Reduction");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Permanent Production Reduction");
		return returnBuffer.toString();
	}

	public void reset() {
		super.reset();
		myPowerConsumerDefinition.reset();
		myPotableWaterConsumerDefinition.reset();
		myGreyWaterConsumerDefinition.reset();
		myDirtyWaterConsumerDefinition.reset();
		myO2ConsumerDefinition.reset();
		myCO2ConsumerDefinition.reset();
		myH2ConsumerDefinition.reset();
		myNitrogenConsumerDefinition.reset();
		myAirConsumerDefinition.reset();
		myBiomassConsumerDefinition.reset();
		myFoodConsumerDefinition.reset();
		myDryWasteConsumerDefinition.reset();
		myWaterConsumerDefinition.reset();

		myPowerProducerDefinition.reset();
		myPotableWaterProducerDefinition.reset();
		myGreyWaterProducerDefinition.reset();
		myDirtyWaterProducerDefinition.reset();
		myO2ProducerDefinition.reset();
		myCO2ProducerDefinition.reset();
		myH2ProducerDefinition.reset();
		myNitrogenProducerDefinition.reset();
		myAirProducerDefinition.reset();
		myBiomassProducerDefinition.reset();
		myFoodProducerDefinition.reset();
		myDryWasteProducerDefinition.reset();
		myWaterProducerDefinition.reset();
	}

	public void log() {
	}

	// Consumers
	public PowerConsumerDefinition getPowerConsumerDefinition() {
		return myPowerConsumerDefinition.getCorbaObject();
	}

	public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
		return myPotableWaterConsumerDefinition.getCorbaObject();
	}

	public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
		return myGreyWaterConsumerDefinition.getCorbaObject();
	}

	public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
		return myDirtyWaterConsumerDefinition.getCorbaObject();
	}

	public O2ConsumerDefinition getO2ConsumerDefinition() {
		return myO2ConsumerDefinition.getCorbaObject();
	}

	public CO2ConsumerDefinition getCO2ConsumerDefinition() {
		return myCO2ConsumerDefinition.getCorbaObject();
	}

	public NitrogenConsumerDefinition getNitrogenConsumerDefinition() {
		return myNitrogenConsumerDefinition.getCorbaObject();
	}

	public AirConsumerDefinition getAirConsumerDefinition() {
		return myAirConsumerDefinition.getCorbaObject();
	}

	public BiomassConsumerDefinition getBiomassConsumerDefinition() {
		return myBiomassConsumerDefinition.getCorbaObject();
	}

	public FoodConsumerDefinition getFoodConsumerDefinition() {
		return myFoodConsumerDefinition.getCorbaObject();
	}

	public DryWasteConsumerDefinition getDryWasteConsumerDefinition() {
		return myDryWasteConsumerDefinition.getCorbaObject();
	}

	public H2ConsumerDefinition getH2ConsumerDefinition() {
		return myH2ConsumerDefinition.getCorbaObject();
	}

	public WaterConsumerDefinition getWaterConsumerDefinition() {
		return myWaterConsumerDefinition.getCorbaObject();
	}

	// Producers
	public PowerProducerDefinition getPowerProducerDefinition() {
		return myPowerProducerDefinition.getCorbaObject();
	}

	public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
		return myPotableWaterProducerDefinition.getCorbaObject();
	}

	public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
		return myGreyWaterProducerDefinition.getCorbaObject();
	}

	public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
		return myDirtyWaterProducerDefinition.getCorbaObject();
	}

	public O2ProducerDefinition getO2ProducerDefinition() {
		return myO2ProducerDefinition.getCorbaObject();
	}

	public CO2ProducerDefinition getCO2ProducerDefinition() {
		return myCO2ProducerDefinition.getCorbaObject();
	}

	public NitrogenProducerDefinition getNitrogenProducerDefinition() {
		return myNitrogenProducerDefinition.getCorbaObject();
	}

	public AirProducerDefinition getAirProducerDefinition() {
		return myAirProducerDefinition.getCorbaObject();
	}

	public BiomassProducerDefinition getBiomassProducerDefinition() {
		return myBiomassProducerDefinition.getCorbaObject();
	}

	public FoodProducerDefinition getFoodProducerDefinition() {
		return myFoodProducerDefinition.getCorbaObject();
	}

	public DryWasteProducerDefinition getDryWasteProducerDefinition() {
		return myDryWasteProducerDefinition.getCorbaObject();
	}

	public H2ProducerDefinition getH2ProducerDefinition() {
		return myH2ProducerDefinition.getCorbaObject();
	}

	public WaterProducerDefinition getWaterProducerDefinition() {
		return myWaterProducerDefinition.getCorbaObject();
	}
}