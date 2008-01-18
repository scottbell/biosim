package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.InfluentValveOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterProducerOperations;
import com.traclabs.biosim.server.simulation.air.CO2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.H2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.H2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.NitrogenProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.O2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.O2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.BiomassConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.BiomassProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.FoodProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.power.PowerProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.WaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.WaterProducerDefinitionImpl;

/**
 * An influent valve.  Takes from 1 store (chosen by the index) and dumps to n stores.
 * 
 * @author Scott Bell
 */

public class InfluentValveImpl extends SimBioModuleImpl implements
		InfluentValveOperations, PowerConsumerOperations,
		PotableWaterConsumerOperations, GreyWaterConsumerOperations,
		WaterConsumerOperations, DirtyWaterConsumerOperations,
		O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations,
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
	private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

	private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

	private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

	private DirtyWaterConsumerDefinitionImpl myDirtyWaterConsumerDefinitionImpl;

	private O2ConsumerDefinitionImpl myO2ConsumerDefinitionImpl;

	private CO2ConsumerDefinitionImpl myCO2ConsumerDefinitionImpl;

	private H2ConsumerDefinitionImpl myH2ConsumerDefinitionImpl;

	private NitrogenConsumerDefinitionImpl myNitrogenConsumerDefinitionImpl;

	private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

	private BiomassConsumerDefinitionImpl myBiomassConsumerDefinitionImpl;

	private FoodConsumerDefinitionImpl myFoodConsumerDefinitionImpl;
	
	private DryWasteConsumerDefinitionImpl myDryWasteConsumerDefinitionImpl;

	private WaterConsumerDefinitionImpl myWaterConsumerDefinitionImpl;

	private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;

	private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;

	private GreyWaterProducerDefinitionImpl myGreyWaterProducerDefinitionImpl;

	private DirtyWaterProducerDefinitionImpl myDirtyWaterProducerDefinitionImpl;

	private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;

	private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

	private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;

	private NitrogenProducerDefinitionImpl myNitrogenProducerDefinitionImpl;

	private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

	private BiomassProducerDefinitionImpl myBiomassProducerDefinitionImpl;

	private FoodProducerDefinitionImpl myFoodProducerDefinitionImpl;
	
	private DryWasteProducerDefinitionImpl myDryWasteProducerDefinitionImpl;

	private WaterProducerDefinitionImpl myWaterProducerDefinitionImpl;
	
	//Flowing from first or second store?
	private int myIndexOfInfluentStore = 0;
	
	//For apollo13 malfunction
	private float myO2MalfunctionDecrement = 0f;

	public InfluentValveImpl(int pID, String pName) {
		super(pID, pName);
		myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
		myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl(this);
		myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl(this);
		myDirtyWaterConsumerDefinitionImpl = new DirtyWaterConsumerDefinitionImpl(this);
		myO2ConsumerDefinitionImpl = new O2ConsumerDefinitionImpl(this);
		myCO2ConsumerDefinitionImpl = new CO2ConsumerDefinitionImpl(this);
		myH2ConsumerDefinitionImpl = new H2ConsumerDefinitionImpl(this);
		myNitrogenConsumerDefinitionImpl = new NitrogenConsumerDefinitionImpl(this);
		myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
		myBiomassConsumerDefinitionImpl = new BiomassConsumerDefinitionImpl(this);
		myFoodConsumerDefinitionImpl = new FoodConsumerDefinitionImpl(this);
		myDryWasteConsumerDefinitionImpl = new DryWasteConsumerDefinitionImpl(this);
		myWaterConsumerDefinitionImpl = new WaterConsumerDefinitionImpl(this);

		myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl(this);
		myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl(this);
		myGreyWaterProducerDefinitionImpl = new GreyWaterProducerDefinitionImpl(this);
		myDirtyWaterProducerDefinitionImpl = new DirtyWaterProducerDefinitionImpl(this);
		myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl(this);
		myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl(this);
		myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl(this);
		myNitrogenProducerDefinitionImpl = new NitrogenProducerDefinitionImpl(this);
		myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
		myBiomassProducerDefinitionImpl = new BiomassProducerDefinitionImpl(this);
		myFoodProducerDefinitionImpl = new FoodProducerDefinitionImpl(this);
		myDryWasteProducerDefinitionImpl = new DryWasteProducerDefinitionImpl(this);
		myWaterProducerDefinitionImpl = new WaterProducerDefinitionImpl(this);
	}

	public void tick() {
		super.tick();
		getAndPushResources();
	}
	
	public void setIndexOfInfluentStore(int pIndexOfInfluentStore){
		myIndexOfInfluentStore = pIndexOfInfluentStore;
	}
	
	public int getIndexOfInfluentStore(){
		return myIndexOfInfluentStore;
	}

	private void getAndPushResources() {
		float powerGathered = myPowerConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myPowerProducerDefinitionImpl.pushResourceToStores(powerGathered);

		float potableWaterGathered = myPotableWaterConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myPotableWaterProducerDefinitionImpl
				.pushResourceToStores(potableWaterGathered);

		float greyWaterGathered = myGreyWaterConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myGreyWaterProducerDefinitionImpl
				.pushResourceToStores(greyWaterGathered);

		float dirtyWaterGathered = myDirtyWaterConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myDirtyWaterProducerDefinitionImpl
				.pushResourceToStores(dirtyWaterGathered);

		float biomassGathered = myBiomassConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myBiomassProducerDefinitionImpl.pushResourceToStores(biomassGathered);

		float foodGathered = myFoodConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myFoodProducerDefinitionImpl.pushResourceToStores(foodGathered);

		float dryWasteGathered = myDryWasteConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myDryWasteProducerDefinitionImpl.pushResourceToStores(dryWasteGathered);

		float O2Gathered = myO2ConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myLogger.debug("O2Gathered = "+O2Gathered);
		myO2ProducerDefinitionImpl.pushResourceToStores(O2Gathered);

		float CO2Gathered = myCO2ConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myCO2ProducerDefinitionImpl.pushResourceToStores(CO2Gathered);

		float nitrogenGathered = myNitrogenConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myNitrogenProducerDefinitionImpl.pushResourceToStores(nitrogenGathered);

		float H2Gathered = myH2ConsumerDefinitionImpl
				.getMostResourceFromStore(myIndexOfInfluentStore);
		myH2ProducerDefinitionImpl.pushResourceToStores(H2Gathered);
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
		myO2MalfunctionDecrement = 0;
		myIndexOfInfluentStore = 0;
		
		myPowerConsumerDefinitionImpl.reset();
		myPotableWaterConsumerDefinitionImpl.reset();
		myGreyWaterConsumerDefinitionImpl.reset();
		myDirtyWaterConsumerDefinitionImpl.reset();
		myO2ConsumerDefinitionImpl.reset();
		myCO2ConsumerDefinitionImpl.reset();
		myH2ConsumerDefinitionImpl.reset();
		myNitrogenConsumerDefinitionImpl.reset();
		myAirConsumerDefinitionImpl.reset();
		myBiomassConsumerDefinitionImpl.reset();
		myFoodConsumerDefinitionImpl.reset();
		myDryWasteConsumerDefinitionImpl.reset();
		myWaterConsumerDefinitionImpl.reset();

		myPowerProducerDefinitionImpl.reset();
		myPotableWaterProducerDefinitionImpl.reset();
		myGreyWaterProducerDefinitionImpl.reset();
		myDirtyWaterProducerDefinitionImpl.reset();
		myO2ProducerDefinitionImpl.reset();
		myCO2ProducerDefinitionImpl.reset();
		myH2ProducerDefinitionImpl.reset();
		myNitrogenProducerDefinitionImpl.reset();
		myAirProducerDefinitionImpl.reset();
		myBiomassProducerDefinitionImpl.reset();
		myFoodProducerDefinitionImpl.reset();
		myDryWasteProducerDefinitionImpl.reset();
		myWaterProducerDefinitionImpl.reset();
	}

	public void log() {
	}

	// Consumers
	public PowerConsumerDefinition getPowerConsumerDefinition() {
		return myPowerConsumerDefinitionImpl.getCorbaObject();
	}

	public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
		return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
	}

	public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
		return myGreyWaterConsumerDefinitionImpl.getCorbaObject();
	}

	public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
		return myDirtyWaterConsumerDefinitionImpl.getCorbaObject();
	}

	public O2ConsumerDefinition getO2ConsumerDefinition() {
		return myO2ConsumerDefinitionImpl.getCorbaObject();
	}

	public CO2ConsumerDefinition getCO2ConsumerDefinition() {
		return myCO2ConsumerDefinitionImpl.getCorbaObject();
	}

	public NitrogenConsumerDefinition getNitrogenConsumerDefinition() {
		return myNitrogenConsumerDefinitionImpl.getCorbaObject();
	}

	public AirConsumerDefinition getAirConsumerDefinition() {
		return myAirConsumerDefinitionImpl.getCorbaObject();
	}

	public BiomassConsumerDefinition getBiomassConsumerDefinition() {
		return myBiomassConsumerDefinitionImpl.getCorbaObject();
	}

	public FoodConsumerDefinition getFoodConsumerDefinition() {
		return myFoodConsumerDefinitionImpl.getCorbaObject();
	}

	public DryWasteConsumerDefinition getDryWasteConsumerDefinition() {
		return myDryWasteConsumerDefinitionImpl.getCorbaObject();
	}

	public H2ConsumerDefinition getH2ConsumerDefinition() {
		return myH2ConsumerDefinitionImpl.getCorbaObject();
	}

	public WaterConsumerDefinition getWaterConsumerDefinition() {
		return myWaterConsumerDefinitionImpl.getCorbaObject();
	}

	// Producers
	public PowerProducerDefinition getPowerProducerDefinition() {
		return myPowerProducerDefinitionImpl.getCorbaObject();
	}

	public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
		return myPotableWaterProducerDefinitionImpl.getCorbaObject();
	}

	public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
		return myGreyWaterProducerDefinitionImpl.getCorbaObject();
	}

	public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
		return myDirtyWaterProducerDefinitionImpl.getCorbaObject();
	}

	public O2ProducerDefinition getO2ProducerDefinition() {
		return myO2ProducerDefinitionImpl.getCorbaObject();
	}

	public CO2ProducerDefinition getCO2ProducerDefinition() {
		return myCO2ProducerDefinitionImpl.getCorbaObject();
	}

	public NitrogenProducerDefinition getNitrogenProducerDefinition() {
		return myNitrogenProducerDefinitionImpl.getCorbaObject();
	}

	public AirProducerDefinition getAirProducerDefinition() {
		return myAirProducerDefinitionImpl.getCorbaObject();
	}

	public BiomassProducerDefinition getBiomassProducerDefinition() {
		return myBiomassProducerDefinitionImpl.getCorbaObject();
	}

	public FoodProducerDefinition getFoodProducerDefinition() {
		return myFoodProducerDefinitionImpl.getCorbaObject();
	}

	public DryWasteProducerDefinition getDryWasteProducerDefinition() {
		return myDryWasteProducerDefinitionImpl.getCorbaObject();
	}

	public H2ProducerDefinition getH2ProducerDefinition() {
		return myH2ProducerDefinitionImpl.getCorbaObject();
	}

	public WaterProducerDefinition getWaterProducerDefinition() {
		return myWaterProducerDefinitionImpl.getCorbaObject();
	}
	
    /**
     * Actually performs the malfunctions. Reduces levels/currentCapacity
     */
    protected void performMalfunctions() {
    	for (Malfunction currentMalfunction : myMalfunctions.values()) {
    		if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                //decrease O2 flowrate to 0 over 7649 ticks
    			if (!currentMalfunction.hasPerformed()){
    					myO2MalfunctionDecrement = myO2ConsumerDefinitionImpl.getMaxFlowRate(0) / 7649f;
    					currentMalfunction.setPerformed(true);
    			}

				if (myO2ConsumerDefinitionImpl.getMaxFlowRate(0) > 0){
					float newAmount = myO2ConsumerDefinitionImpl.getMaxFlowRate(0) - myO2MalfunctionDecrement;
					if (newAmount < 0)
						newAmount = 0;
					myO2ConsumerDefinitionImpl.setMaxFlowRate(newAmount, 0);
				}
            } else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF)
                    && (!currentMalfunction.hasPerformed())) {
    			currentMalfunction.setPerformed(true);
            }
        }
    }
}