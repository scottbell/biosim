package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.server.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.WaterProducerOperations;

import java.util.Iterator;

/**
 * The Food Processor takes biomass (plants matter) and refines it to food for
 * the crew members.
 * 
 * @author Scott Bell
 */

public class FoodProcessor extends SimBioModule implements
        FoodProcessorOperations, PowerConsumerOperations,
        BiomassConsumerOperations, FoodProducerOperations,
        DryWasteProducerOperations, WaterProducerOperations {
    //Consumers, Producers
    private BiomassConsumerDefinition myBiomassConsumerDefinition;

    private PowerConsumerDefinition myPowerConsumerDefinition;

    private FoodProducerDefinition myFoodProducerDefinition;

    private WaterProducerDefinition myWaterProducerDefinition;

    private DryWasteProducerDefinition myDryWasteProducerDefinition;

    //During any given tick, this much power is needed for the food processor
    // to run at all
    private float powerNeeded = 100;

    //During any given tick, this much biomass is needed for the food processor
    // to run optimally
    private float biomassNeeded = 200f;

    //Flag to determine if the Food Processor has enough power to function
    private boolean hasEnoughPower = false;

    //Flag to determine if the Food Processor has enough biomass to function
    // nominally
    private boolean hasEnoughBiomass = false;

    //The biomass consumed (in kilograms) by the Food Processor at the current
    // tick
    private float massConsumed = 0f;

    //The power consumed (in watts) by the Food Processor at the current tick
    private float currentPowerConsumed = 0f;

    //The food produced (in kilograms) by the Food Processor at the current
    // tick
    private float currentFoodProduced = 0f;

    //References to the servers the Food Processor takes/puts resources (like
    // power, biomass, etc)
    private float myProductionRate = 1f;

    private BioMatter[] biomatterConsumed;

    public FoodProcessor(int pID, String pName) {
        super(pID, pName);
        biomatterConsumed = new BioMatter[0];
        myBiomassConsumerDefinition = new BiomassConsumerDefinition(this);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myWaterProducerDefinition = new WaterProducerDefinition(this);
        myFoodProducerDefinition = new FoodProducerDefinition(this);
        myDryWasteProducerDefinition = new DryWasteProducerDefinition(this);
    }

    public BiomassConsumerDefinition getBiomassConsumerDefinition() {
        return myBiomassConsumerDefinition.getCorbaObject();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition.getCorbaObject();
    }

    public WaterProducerDefinition getWaterProducerDefinition() {
        return myWaterProducerDefinition.getCorbaObject();
    }

    public FoodProducerDefinition getFoodProducerDefinition() {
        return myFoodProducerDefinition.getCorbaObject();
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinition.getCorbaObject();
    }

    /**
     * Resets production/consumption levels
     */
    public void reset() {
        super.reset();
        massConsumed = 0f;
        biomatterConsumed = null;
        currentPowerConsumed = 0f;
        currentFoodProduced = 0f;
        myBiomassConsumerDefinition.reset();
        myPowerConsumerDefinition.reset();
        myWaterProducerDefinition.reset();
        myFoodProducerDefinition.reset();
        myDryWasteProducerDefinition.reset();
    }

    /**
     * Returns the biomass consumed (in kilograms) by the Food Processor during
     * the current tick
     * 
     * @return the biomass consumed (in kilograms) by the Food Processor during
     *         the current tick
     */
    public float getBiomassConsumed() {
        return massConsumed;
    }

    /**
     * Returns the power consumed (in watts) by the Food Processor during the
     * current tick
     * 
     * @return the power consumed (in watts) by the Food Processor during the
     *         current tick
     */
    public float getPowerConsumed() {
        return currentPowerConsumed;
    }

    /**
     * Returns the food produced (in kilograms) by the Food Processor during the
     * current tick
     * 
     * @return the food produced (in kilograms) by the Food Processor during the
     *         current tick
     */
    public float getFoodProduced() {
        return currentFoodProduced;
    }

    /**
     * Checks whether Food Processor has enough power or not
     * 
     * @return <code>true</code> if the Food Processor has enough power,
     *         <code>false</code> if not.
     */
    public boolean hasPower() {
        return hasEnoughPower;
    }

    /**
     * Checks whether Food Processor has enough biomass to run optimally or not
     * 
     * @return <code>true</code> if the Food Processor has enough biomass,
     *         <code>false</code> if not.
     */
    public boolean hasBiomass() {
        return hasEnoughBiomass;
    }

    /**
     * Attempts to collect enough power from the Power PS to run the Food
     * Processor for one tick.
     */
    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinition
                .getResourceFromStores(powerNeeded);
        if (currentPowerConsumed < powerNeeded) {
            hasEnoughPower = false;
        } else {
            hasEnoughPower = true;
        }
    }

    /**
     * Attempts to collect enough biomass from the Biomass Store to run the Food
     * Processor optimally for one tick.
     */
    private void gatherBiomass() {
        biomatterConsumed = myBiomassConsumerDefinition
                .getBioMassFromStore(biomassNeeded);
        massConsumed = calculateSizeOfBioMatter(biomatterConsumed);
        if (massConsumed > 0)
            myLogger
                    .debug(getModuleName() + ": massConsumed = " + massConsumed);
        if (massConsumed < biomassNeeded) {
            hasEnoughBiomass = false;
        } else {
            hasEnoughBiomass = true;
        }
    }

    private static float calculateSizeOfBioMatter(BioMatter[] arrayOfMatter) {
        float totalSize = 0f;
        for (int i = 0; i < arrayOfMatter.length; i++)
            totalSize += arrayOfMatter[i].mass;
        return totalSize;
    }

    private static float calculateSizeOfFoodMatter(FoodMatter[] arrayOfMatter) {
        float totalSize = 0f;
        for (int i = 0; i < arrayOfMatter.length; i++)
            totalSize += arrayOfMatter[i].mass;
        return totalSize;
    }

    private float calculateInedibleWaterContent(BioMatter inMatter) {
        float totalWaterWithinInedibleBioMatter = inMatter.inedibleWaterContent;
        return totalWaterWithinInedibleBioMatter
                * myProductionRate;
    }

    private FoodMatter transformBioMatter(BioMatter inMatter) {
        float foodMass = 0f;
        if (inMatter.inedibleFraction > 0)
            foodMass = inMatter.mass * (1f - inMatter.inedibleFraction);
        else
            foodMass = inMatter.mass;
        myLogger.debug(getModuleName() + ": Creating " + foodMass
                + " kg of food");
        FoodMatter newFoodMatter = new FoodMatter(foodMass,
                inMatter.edibleWaterContent, inMatter.type);
        newFoodMatter.mass = newFoodMatter.mass
                * myProductionRate;
        return newFoodMatter;
    }

    /**
     * If the Food Processor has any biomass and enough power, it provides some
     * food to put into the store.
     */
    private void createFood() {
        if (hasEnoughPower) {
            if (biomatterConsumed == null) {
                currentFoodProduced = 0f;
                return;
            }
            FoodMatter[] foodMatterArray = new FoodMatter[biomatterConsumed.length];
            float currentWaterProduced = 0f;
            for (int i = 0; i < foodMatterArray.length; i++) {
                foodMatterArray[i] = transformBioMatter(biomatterConsumed[i]);
                currentWaterProduced += calculateInedibleWaterContent(biomatterConsumed[i]);
            }
            currentFoodProduced = calculateSizeOfFoodMatter(foodMatterArray);
            myFoodProducerDefinition
                    .pushFoodToStore(foodMatterArray);
            float currentDryWasteProduced = currentFoodProduced
                    - calculateSizeOfBioMatter(biomatterConsumed);
            myDryWasteProducerDefinition
                    .pushResourceToStores(currentDryWasteProduced);
            myWaterProducerDefinition
                    .pushResourceToStores(currentWaterProduced);
        }
    }

    /**
     * Attempts to consume resource (power and biomass) for Food Processor
     */
    private void consumeResources() {
        gatherPower();
        if (hasEnoughPower)
            gatherBiomass();
    }

    private void setProductionRate(float pProductionRate) {
        myProductionRate = pProductionRate;
    }

    protected void performMalfunctions() {
        float productionRate = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.90; // 10% reduction
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.90; // 10% reduction
            }
        }
        setProductionRate(productionRate);
    }

    /**
     * When ticked, the Food Processor does the following: 1) attempts to
     * collect references to various server (if not already done). 2) consumes
     * power and biomass. 3) creates food (if possible)
     */
    public void tick() {
        super.tick();
        massConsumed = 0f;
        currentFoodProduced = 0f;
        currentPowerConsumed = 0f;
        consumeResources();
        createFood();
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
            returnBuffer.append("Production Rate Decrease (Temporary)");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Production Rate Decrease (Permanent)");
        return returnBuffer.toString();
    }

    public void log() {
        /*
         * LogNode powerNeededHead = myLog.addChild("power_needed");
         * myLogIndex.powerNeededIndex =
         * powerNeededHead.addChild(""+powerNeeded); LogNode hasEnoughPowerHead =
         * myLog.addChild("has_enough_power"); myLogIndex.hasEnoughPowerIndex =
         * hasEnoughPowerHead.addChild(""+hasEnoughPower); LogNode
         * biomassNeededHead = myLog.addChild("biomass_needed");
         * myLogIndex.biomassNeededIndex =
         * biomassNeededHead.addChild(""+biomassNeeded); LogNode
         * massConsumedHead = myLog.addChild("current_biomass_consumed");
         * myLogIndex.massConsumedIndex =
         * massConsumedHead.addChild(""+massConsumed); LogNode
         * currentPowerConsumedHead = myLog.addChild("current_power_consumed");
         * myLogIndex.currentPowerConsumedIndex =
         * currentPowerConsumedHead.addChild(""+currentPowerConsumed); LogNode
         * currentFoodProducedHead = myLog.addChild("current_food_produced");
         * myLogIndex.currentFoodProducedIndex =
         * currentFoodProducedHead.addChild(""+currentFoodProduced);
         */
    }
}
