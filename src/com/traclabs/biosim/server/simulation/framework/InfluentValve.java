package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.environment.*;
import com.traclabs.biosim.server.simulation.food.*;
import com.traclabs.biosim.server.simulation.power.*;
import com.traclabs.biosim.server.simulation.waste.*;
import com.traclabs.biosim.server.simulation.water.*;

/**
 * An influent valve.  Takes from 1 store (chosen by the index) and dumps to n stores.
 *
 * @author Scott Bell
 */

public class InfluentValve extends SimBioModule implements PowerConsumer, PotableWaterConsumer, GreyWaterConsumer, DirtyWaterConsumer, WaterConsumer, O2Consumer, CO2Consumer, NitrogenConsumer, AirConsumer, BiomassConsumer, FoodConsumer, DryWasteConsumer, PowerProducer, PotableWaterProducer, GreyWaterProducer, DirtyWaterProducer, O2Producer, CO2Producer, NitrogenProducer, AirProducer, BiomassProducer, FoodProducer, DryWasteProducer {

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

    //Flowing from first or second store?
    private int myIndexOfInfluentStore = 0;

    //For apollo13 malfunction
    private float myO2MalfunctionDecrement = 0f;

    public InfluentValve(int pID, String pName) {
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

    public int getIndexOfInfluentStore() {
        return myIndexOfInfluentStore;
    }

    public void setIndexOfInfluentStore(int pIndexOfInfluentStore) {
        myIndexOfInfluentStore = pIndexOfInfluentStore;
    }

    private void getAndPushResources() {
        float powerGathered = myPowerConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myPowerProducerDefinition.pushResourceToStores(powerGathered);

        float potableWaterGathered = myPotableWaterConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myPotableWaterProducerDefinition
                .pushResourceToStores(potableWaterGathered);

        float greyWaterGathered = myGreyWaterConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myGreyWaterProducerDefinition
                .pushResourceToStores(greyWaterGathered);

        float dirtyWaterGathered = myDirtyWaterConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myDirtyWaterProducerDefinition
                .pushResourceToStores(dirtyWaterGathered);

        float biomassGathered = myBiomassConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myBiomassProducerDefinition.pushResourceToStores(biomassGathered);

        float foodGathered = myFoodConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myFoodProducerDefinition.pushResourceToStores(foodGathered);

        float dryWasteGathered = myDryWasteConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myDryWasteProducerDefinition.pushResourceToStores(dryWasteGathered);

        float O2Gathered = myO2ConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myLogger.debug("O2Gathered = " + O2Gathered);
        myO2ProducerDefinition.pushResourceToStores(O2Gathered);

        float CO2Gathered = myCO2ConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myCO2ProducerDefinition.pushResourceToStores(CO2Gathered);

        float nitrogenGathered = myNitrogenConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myNitrogenProducerDefinition.pushResourceToStores(nitrogenGathered);

        float H2Gathered = myH2ConsumerDefinition
                .getMostResourceFromStore(myIndexOfInfluentStore);
        myH2ProducerDefinition.pushResourceToStores(H2Gathered);
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

    /**
     * Actually performs the malfunctions. Reduces levels/currentCapacity
     */
    protected void performMalfunctions() {
        for (Malfunction currentMalfunction : myMalfunctions.values()) {
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                //decrease O2 flowrate to 0 over 7649 ticks
                if (!currentMalfunction.hasPerformed()) {
                    myO2MalfunctionDecrement = myO2ConsumerDefinition.getMaxFlowRate(0) / 7649f;
                    currentMalfunction.setPerformed(true);
                }

                if (myO2ConsumerDefinition.getMaxFlowRate(0) > 0) {
                    float newAmount = myO2ConsumerDefinition.getMaxFlowRate(0) - myO2MalfunctionDecrement;
                    if (newAmount < 0)
                        newAmount = 0;
                    myO2ConsumerDefinition.setMaxFlowRate(newAmount, 0);
                }
            } else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF)
                    && (!currentMalfunction.hasPerformed())) {
                currentMalfunction.setPerformed(true);
            }
        }
    }
}