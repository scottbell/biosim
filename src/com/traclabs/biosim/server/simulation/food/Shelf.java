package com.traclabs.biosim.server.simulation.food;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tray contains Plants
 *
 * @author Scott Bell
 */

public class Shelf {
    /* grab up to 50 liters per meters squared of crops per hour (WAG) */
    private static final float waterNeededPerMeterSquared = 50f;
    /* grab 1520 watts per square meter (from table 4.2.2 in BVAD) */
    private static final float POWER_PER_SQUARE_METER = 1520f;
    private final Logger myLogger = LoggerFactory.getLogger(Shelf.class);
    private final BiomassPS myBiomassPS;
    private Plant myCrop;
    private float cropAreaTotal = 0f;
    private float cropAreaUsed = 0f;
    private float waterLevel = 0f;
    private float waterNeeded = 0f;
    private float powerLevel = 0f;
    private int myStartTick;

    public Shelf(PlantType pType, float pCropAreaTotal,
                 BiomassPS pBiomass) {
        this(pType, pCropAreaTotal, pBiomass, 0);
    }

    public Shelf(PlantType pType, float pCropAreaTotal,
                 BiomassPS pBiomass, int pStartTick) {
        myStartTick = pStartTick;
        cropAreaTotal = pCropAreaTotal;
        myBiomassPS = pBiomass;
        replant(pType, cropAreaTotal);
        waterNeeded = cropAreaUsed * waterNeededPerMeterSquared;
    }

    public Plant getPlant() {
        return myCrop;
    }

    public void reset() {
        waterLevel = 0f;
        powerLevel = 0f;
        myCrop.reset();
    }

    public float getTimeTillCanopyClosure() {
        return myCrop.getTimeTillCanopyClosure();
    }

    public PlantType getCropType() {
        return myCrop.getPlantType();
    }

    public String getCropTypeString() {
        return myCrop.getPlantTypeString();
    }

    public BiomassPS getBiomassPS() {
        return myBiomassPS;
    }

    public float getCropAreaTotal() {
        return cropAreaTotal;
    }

    public float getCropAreaUsed() {
        return cropAreaUsed;
    }

    public void setStartTick(int tick) {
        myStartTick = tick;
    }

    private void gatherWater() {
        float extraWaterNeeded = waterNeeded - waterLevel;
        if (extraWaterNeeded < 0) {
            extraWaterNeeded = 0;
        }
        float gatheredGreyWater = myBiomassPS
                .getGreyWaterConsumerDefinition()
                .getFractionalResourceFromStores(extraWaterNeeded,
                        1f / myBiomassPS.getNumberOfShelves());
        float gatheredPotableWater = myBiomassPS
                .getPotableWaterConsumerDefinition()
                .getFractionalResourceFromStores(
                        extraWaterNeeded - gatheredGreyWater,
                        1f / myBiomassPS.getNumberOfShelves());
        waterLevel += gatheredGreyWater + gatheredPotableWater;
    }

    private void gatherPower() {
        float powerNeeded = POWER_PER_SQUARE_METER * getCropAreaUsed();
        powerLevel = myBiomassPS.getPowerConsumerDefinition()
                .getFractionalResourceFromStores(powerNeeded,
                        1f / myBiomassPS.getNumberOfShelves());
    }

    public float takeWater(float pLiters) {
        if (waterLevel < pLiters) {
            float waterTaken = waterLevel;
            waterLevel = 0;
            return waterTaken;
        }
        waterLevel -= pLiters;
        return pLiters;
    }

    private void lightPlants() {
        //myLogger.debug("Shelf: powerLevel: " + powerLevel);
        //myLogger.debug("Shelf: getLampEfficiency:" + getLampEfficiency());
        //myLogger.debug("Shelf: getPSEfficiency: " + getPSEfficiency());
        float powerToDeliver = Math
                .min(powerLevel, myCrop.getPPFNeeded() * getCropAreaUsed()
                        / (getLampEfficiency() * getPSEfficiency()));
        if (powerToDeliver <= 0)
            powerToDeliver = Float.MIN_VALUE;
        float thePPF = powerToDeliver * getLampEfficiency() * getPSEfficiency()
                / getCropAreaUsed();
        myCrop.shine(thePPF);
    }

    private float getLampEfficiency() {
        return 261f; //for high pressure sodium bulbs
    }

    private float getPSEfficiency() {
        return 4.68f; //for high pressure sodium bulbs
    }

    public void harvest() {
        BioMatter biomassProduced = myCrop.harvest();
        myBiomassPS.getBiomassProducerDefinition()
                .pushFractionalResourceToBiomassStore(biomassProduced,
                        1f / myBiomassPS.getNumberOfShelves());
    }

    public boolean isReadyForHavest() {
        return myCrop.readyForHarvest();
    }

    public boolean isDead() {
        return myCrop.isDead();
    }

    public float getHarvestInterval() {
        return myCrop.getTimeAtCropMaturity();
    }

    private void tryHarvesting() {
        if (myBiomassPS.autoHarvestAndReplantEnabled()) {
            if (myCrop.readyForHarvest() || myCrop.isDead()) {
                float age = myCrop.getDaysOfGrowth();
                BioMatter biomassProduced = myCrop.harvest();
                float inedible = biomassProduced.inedibleFraction;
                if (Float.isNaN(inedible))
                    inedible = 0;
                myLogger.info("Shelf: Harvested " + biomassProduced.mass
                        + "kg of " + myCrop.getPlantTypeString() + " (" + inedible * 100 + "% inedible) after " + age + " days of growth on tick " + myBiomassPS.getMyTicks());
                myBiomassPS
                        .getBiomassProducerDefinition()
                        .pushFractionalResourceToBiomassStore(biomassProduced,
                                1f / myBiomassPS.getNumberOfShelves());
                myCrop.reset();
            }
        }
    }

    public void tick() {
        if (cropAreaUsed > 0 && (myBiomassPS.getMyTicks() >= myStartTick)) {
            tryHarvesting();
            gatherPower();
            gatherWater();
            lightPlants();
            myCrop.tick();
        }
    }

    public void replant(PlantType pType) {
        replant(pType, cropAreaTotal);
    }

    public void replant(PlantType pType, float pArea) {
        if (pArea > cropAreaTotal)
            cropAreaUsed = cropAreaTotal;
        else
            cropAreaUsed = pArea;

        if ((myCrop != null) && (pType == myCrop.getPlantType()))
            myCrop.reset();
        else if (pType == PlantType.DRY_BEAN)
            myCrop = new DryBean(this);
        else if (pType == PlantType.LETTUCE)
            myCrop = new Lettuce(this);
        else if (pType == PlantType.PEANUT)
            myCrop = new Peanut(this);
        else if (pType == PlantType.SOYBEAN)
            myCrop = new Soybean(this);
        else if (pType == PlantType.RICE)
            myCrop = new Rice(this);
        else if (pType == PlantType.SWEET_POTATO)
            myCrop = new SweetPotato(this);
        else if (pType == PlantType.TOMATO)
            myCrop = new Tomato(this);
        else if (pType == PlantType.WHEAT)
            myCrop = new Wheat(this);
        else if (pType == PlantType.WHITE_POTATO)
            myCrop = new WhitePotato(this);
        waterNeeded = cropAreaUsed * waterNeededPerMeterSquared;
    }

    public void kill() {
        myCrop.kill();
    }

}