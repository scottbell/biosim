package com.traclabs.biosim.server.simulation.food;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodMatter;
import com.traclabs.biosim.idl.simulation.food.FoodProcessorOperations;
import com.traclabs.biosim.idl.simulation.food.FoodProducerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.WaterProducerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.WaterProducerDefinitionImpl;

/**
 * The Food Processor takes biomass (plants matter) and refines it to food for
 * the crew members.
 * 
 * @author Scott Bell
 */

public class FoodProcessorImpl extends SimBioModuleImpl implements
        FoodProcessorOperations, PowerConsumerOperations,
        BiomassConsumerOperations, FoodProducerOperations,
        DryWasteProducerOperations, WaterProducerOperations {
    //Consumers, Producers
    private BiomassConsumerDefinitionImpl myBiomassConsumerDefinitionImpl;

    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private FoodProducerDefinitionImpl myFoodProducerDefinitionImpl;

    private WaterProducerDefinitionImpl myWaterProducerDefinitionImpl;

    private DryWasteProducerDefinitionImpl myDryWasteProducerDefinitionImpl;

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

    public FoodProcessorImpl(int pID, String pName) {
        super(pID, pName);
        biomatterConsumed = new BioMatter[0];
        myBiomassConsumerDefinitionImpl = new BiomassConsumerDefinitionImpl(this);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myWaterProducerDefinitionImpl = new WaterProducerDefinitionImpl(this);
        myFoodProducerDefinitionImpl = new FoodProducerDefinitionImpl(this);
        myDryWasteProducerDefinitionImpl = new DryWasteProducerDefinitionImpl(this);
    }

    public BiomassConsumerDefinition getBiomassConsumerDefinition() {
        return myBiomassConsumerDefinitionImpl.getCorbaObject();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public WaterProducerDefinition getWaterProducerDefinition() {
        return myWaterProducerDefinitionImpl.getCorbaObject();
    }

    public FoodProducerDefinition getFoodProducerDefinition() {
        return myFoodProducerDefinitionImpl.getCorbaObject();
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinitionImpl.getCorbaObject();
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
        myBiomassConsumerDefinitionImpl.reset();
        myPowerConsumerDefinitionImpl.reset();
        myWaterProducerDefinitionImpl.reset();
        myFoodProducerDefinitionImpl.reset();
        myDryWasteProducerDefinitionImpl.reset();
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
        currentPowerConsumed = myPowerConsumerDefinitionImpl
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
        biomatterConsumed = myBiomassConsumerDefinitionImpl
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
            myFoodProducerDefinitionImpl
                    .pushFoodToStore(foodMatterArray);
            float currentDryWasteProduced = currentFoodProduced
                    - calculateSizeOfBioMatter(biomatterConsumed);
            myDryWasteProducerDefinitionImpl
                    .pushResourceToStores(currentDryWasteProduced);
            myWaterProducerDefinitionImpl
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
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
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