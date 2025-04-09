package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.food.BiomassConsumerDefinition;
import com.traclabs.biosim.server.simulation.food.BiomassProducerDefinition;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.server.simulation.food.FoodProducerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.server.simulation.water.*;

/**
 * An effluent valve.  Takes from n stores and dumps to a store (chosen by the index).
 *
 * @author Scott Bell
 */

public class EffluentValve extends SimBioModule {

    // Consumers, Producers
    private final PowerConsumerDefinition myPowerConsumerDefinition;

    private final PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    private final GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;

    private final DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;

    private final O2ConsumerDefinition myO2ConsumerDefinition;

    private final CO2ConsumerDefinition myCO2ConsumerDefinition;

    private final H2ConsumerDefinition myH2ConsumerDefinition;

    private final NitrogenConsumerDefinition myNitrogenConsumerDefinition;

    private final AirConsumerDefinition myAirConsumerDefinition;

    private final BiomassConsumerDefinition myBiomassConsumerDefinition;

    private final FoodConsumerDefinition myFoodConsumerDefinition;

    private final DryWasteConsumerDefinition myDryWasteConsumerDefinition;

    private final WaterConsumerDefinition myWaterConsumerDefinition;

    private final PowerProducerDefinition myPowerProducerDefinition;

    private final PotableWaterProducerDefinition myPotableWaterProducerDefinition;

    private final GreyWaterProducerDefinition myGreyWaterProducerDefinition;

    private final DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    private final O2ProducerDefinition myO2ProducerDefinition;

    private final CO2ProducerDefinition myCO2ProducerDefinition;

    private final H2ProducerDefinition myH2ProducerDefinition;

    private final NitrogenProducerDefinition myNitrogenProducerDefinition;

    private final AirProducerDefinition myAirProducerDefinition;

    private final BiomassProducerDefinition myBiomassProducerDefinition;

    private final FoodProducerDefinition myFoodProducerDefinition;

    private final DryWasteProducerDefinition myDryWasteProducerDefinition;

    private final WaterProducerDefinition myWaterProducerDefinition;

    // Flowing from first or second store?
    private int myIndexOfEffluentStore = 0;

    public EffluentValve(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myPotableWaterConsumerDefinition = new PotableWaterConsumerDefinition(this);
        myGreyWaterConsumerDefinition = new GreyWaterConsumerDefinition(this);
        myDirtyWaterConsumerDefinition = new DirtyWaterConsumerDefinition(this);
        myO2ConsumerDefinition = new O2ConsumerDefinition(this);
        myCO2ConsumerDefinition = new CO2ConsumerDefinition(this);
        myH2ConsumerDefinition = new H2ConsumerDefinition(this);
        myNitrogenConsumerDefinition = new NitrogenConsumerDefinition(this);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myBiomassConsumerDefinition = new BiomassConsumerDefinition(this);
        myFoodConsumerDefinition = new FoodConsumerDefinition(this);
        myDryWasteConsumerDefinition = new DryWasteConsumerDefinition(this);
        myWaterConsumerDefinition = new WaterConsumerDefinition(this);

        myPowerProducerDefinition = new PowerProducerDefinition(this);
        myPotableWaterProducerDefinition = new PotableWaterProducerDefinition(this);
        myGreyWaterProducerDefinition = new GreyWaterProducerDefinition(this);
        myDirtyWaterProducerDefinition = new DirtyWaterProducerDefinition(this);
        myO2ProducerDefinition = new O2ProducerDefinition(this);
        myCO2ProducerDefinition = new CO2ProducerDefinition(this);
        myH2ProducerDefinition = new H2ProducerDefinition(this);
        myNitrogenProducerDefinition = new NitrogenProducerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
        myBiomassProducerDefinition = new BiomassProducerDefinition(this);
        myFoodProducerDefinition = new FoodProducerDefinition(this);
        myDryWasteProducerDefinition = new DryWasteProducerDefinition(this);
        myWaterProducerDefinition = new WaterProducerDefinition(this);
    }

    private static float waterLitersToMoles(float pLiters) {
        return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
    }

    private static float waterMolesToLiters(float pMoles) {
        return (pMoles * 18.01524f) / 1000f; // 1000g/liter, 18.01524g/mole
    }

    public void tick() {
        super.tick();
        getAndPushResources();
    }

    public int getIndexOfEffluentStore() {
        return myIndexOfEffluentStore;
    }

    public void setIndexOfEffluentStore(int pIndexOfEffluentStore) {
        myIndexOfEffluentStore = pIndexOfEffluentStore;
    }

    private void getAndPushResources() {
        float powerGathered = myPowerConsumerDefinition
                .getMostResourceFromStores();
        myPowerProducerDefinition.pushResourceToStore(powerGathered, myIndexOfEffluentStore);

        float potableWaterGathered = myPotableWaterConsumerDefinition
                .getMostResourceFromStores();
        myPotableWaterProducerDefinition
                .pushResourceToStore(potableWaterGathered, myIndexOfEffluentStore);

        float greyWaterGathered = myGreyWaterConsumerDefinition
                .getMostResourceFromStores();
        myGreyWaterProducerDefinition
                .pushResourceToStore(greyWaterGathered, myIndexOfEffluentStore);

        float dirtyWaterGathered = myDirtyWaterConsumerDefinition
                .getMostResourceFromStores();
        myDirtyWaterProducerDefinition
                .pushResourceToStore(dirtyWaterGathered, myIndexOfEffluentStore);

        float biomassGathered = myBiomassConsumerDefinition
                .getMostResourceFromStores();
        myBiomassProducerDefinition.pushResourceToStore(biomassGathered, myIndexOfEffluentStore);

        float foodGathered = myFoodConsumerDefinition
                .getMostResourceFromStores();
        myFoodProducerDefinition.pushResourceToStore(foodGathered, myIndexOfEffluentStore);

        float dryWasteGathered = myDryWasteConsumerDefinition
                .getMostResourceFromStores();
        myDryWasteProducerDefinition.pushResourceToStore(dryWasteGathered, myIndexOfEffluentStore);

        float O2Gathered = myO2ConsumerDefinition
                .getMostResourceFromStores();
        myO2ProducerDefinition.pushResourceToStore(O2Gathered, myIndexOfEffluentStore);

        float CO2Gathered = myCO2ConsumerDefinition
                .getMostResourceFromStores();
        myCO2ProducerDefinition.pushResourceToStore(CO2Gathered, myIndexOfEffluentStore);

        float nitrogenGathered = myNitrogenConsumerDefinition
                .getMostResourceFromStores();
        myNitrogenProducerDefinition.pushResourceToStore(nitrogenGathered, myIndexOfEffluentStore);

        float H2Gathered = myH2ConsumerDefinition
                .getMostResourceFromStores();
        myH2ProducerDefinition.pushResourceToStore(H2Gathered, myIndexOfEffluentStore);
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

    protected void performMalfunctions() {
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
        return myPowerConsumerDefinition;
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinition;
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinition;
    }

    public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
        return myDirtyWaterConsumerDefinition;
    }

    public O2ConsumerDefinition getO2ConsumerDefinition() {
        return myO2ConsumerDefinition;
    }

    public CO2ConsumerDefinition getCO2ConsumerDefinition() {
        return myCO2ConsumerDefinition;
    }

    public NitrogenConsumerDefinition getNitrogenConsumerDefinition() {
        return myNitrogenConsumerDefinition;
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition;
    }

    public BiomassConsumerDefinition getBiomassConsumerDefinition() {
        return myBiomassConsumerDefinition;
    }

    public FoodConsumerDefinition getFoodConsumerDefinition() {
        return myFoodConsumerDefinition;
    }

    public DryWasteConsumerDefinition getDryWasteConsumerDefinition() {
        return myDryWasteConsumerDefinition;
    }

    public H2ConsumerDefinition getH2ConsumerDefinition() {
        return myH2ConsumerDefinition;
    }

    public WaterConsumerDefinition getWaterConsumerDefinition() {
        return myWaterConsumerDefinition;
    }

    // Producers
    public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinition;
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinition;
    }

    public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
        return myGreyWaterProducerDefinition;
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinition;
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinition;
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinition;
    }

    public NitrogenProducerDefinition getNitrogenProducerDefinition() {
        return myNitrogenProducerDefinition;
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition;
    }

    public BiomassProducerDefinition getBiomassProducerDefinition() {
        return myBiomassProducerDefinition;
    }

    public FoodProducerDefinition getFoodProducerDefinition() {
        return myFoodProducerDefinition;
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinition;
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinition;
    }

    public WaterProducerDefinition getWaterProducerDefinition() {
        return myWaterProducerDefinition;
    }
}