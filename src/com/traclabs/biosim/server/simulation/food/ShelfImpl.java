package com.traclabs.biosim.server.simulation.food;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.BiomassPS;
import com.traclabs.biosim.idl.simulation.food.BiomassPSHelper;
import com.traclabs.biosim.idl.simulation.food.Plant;
import com.traclabs.biosim.idl.simulation.food.PlantHelper;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.idl.simulation.food.ShelfPOA;
import com.traclabs.biosim.util.OrbUtils;

/**
 * Tray contains Plants
 * 
 * @author Scott Bell
 */

public class ShelfImpl extends ShelfPOA {
    private Logger myLogger;

    private PlantImpl myCrop;

    private float cropAreaTotal = 0f;

    private float cropAreaUsed = 0f;

    private BiomassPSImpl myBiomassPSImpl;

    private float waterLevel = 0f;

    /* grab up to 50 liters per meters squared of crops per hour (WAG) */
    private static final float waterNeededPerMeterSquared = 50f;

    private float waterNeeded = 0f;

    private float powerLevel = 0f;

    /* grab 1520 watts per square meter (from table 4.2.2 in BVAD) */
    private static final float POWER_PER_SQUARE_METER = 1520f;

    private int myStartTick;

    public ShelfImpl(PlantType pType, float pCropAreaTotal,
            BiomassPSImpl pBiomassImpl) {
        this(pType, pCropAreaTotal, pBiomassImpl, 0);
    }

    public ShelfImpl(PlantType pType, float pCropAreaTotal,
            BiomassPSImpl pBiomassImpl, int pStartTick) {
        myLogger = Logger.getLogger(this.getClass());
        myStartTick = pStartTick;
        cropAreaTotal = pCropAreaTotal;
        myBiomassPSImpl = pBiomassImpl;
        replant(pType, cropAreaTotal);
        waterNeeded = cropAreaUsed * waterNeededPerMeterSquared;
    }

    public Plant getPlant() {
        return PlantHelper.narrow(OrbUtils.poaToCorbaObj(myCrop));
    }

    public PlantImpl getPlantImpl() {
        return myCrop;
    }

    public void reset() {
        waterLevel = 0f;
        powerLevel = 0f;
        myCrop.reset();
    }
    
    public float getTimeTillCanopyClosure(){
    	return myCrop.getTimeTillCanopyClosure();
    }

    public PlantType getCropType() {
        return myCrop.getPlantType();
    }

    public String getCropTypeString() {
        return myCrop.getPlantTypeString();
    }

    public BiomassPS getBiomassPS() {
        return BiomassPSHelper.narrow(OrbUtils.poaToCorbaObj(myBiomassPSImpl));
    }

    public BiomassPSImpl getBiomassPSImpl() {
        return myBiomassPSImpl;
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
        float gatheredGreyWater = myBiomassPSImpl
                .getGreyWaterConsumerDefinitionImpl()
                .getFractionalResourceFromStores(extraWaterNeeded,
                        1f / myBiomassPSImpl.getNumberOfShelves());
        float gatheredPotableWater = myBiomassPSImpl
                .getPotableWaterConsumerDefinitionImpl()
                .getFractionalResourceFromStores(
                        extraWaterNeeded - gatheredGreyWater,
                        1f / myBiomassPSImpl.getNumberOfShelves());
        waterLevel += gatheredGreyWater + gatheredPotableWater;
    }

    private void gatherPower() {
        float powerNeeded = POWER_PER_SQUARE_METER * getCropAreaUsed();
        powerLevel = myBiomassPSImpl.getPowerConsumerDefinitionImpl()
                .getFractionalResourceFromStores(powerNeeded,
                        1f / myBiomassPSImpl.getNumberOfShelves());
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
        //myLogger.debug("ShelfImpl: powerLevel: " + powerLevel);
        //myLogger.debug("ShelfImpl: getLampEfficiency:" + getLampEfficiency());
        //myLogger.debug("ShelfImpl: getPSEfficiency: " + getPSEfficiency());
        float powerToDeliver = Math
                .min(powerLevel, myCrop.getPPFNeeded() * getCropAreaUsed()
                        / (getLampEfficiency() * getPSEfficiency()));
        if (powerToDeliver <= 0)
            powerToDeliver = Float.MIN_VALUE;
        float thePPF = powerToDeliver * getLampEfficiency() * getPSEfficiency()
                / getCropAreaUsed();
        //myLogger.debug("ShelfImpl: thePPF: " + thePPF);
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
        myBiomassPSImpl.getBiomassProducerDefinitionImpl()
                .pushFractionalResourceToBiomassStore(biomassProduced,
                        1f / myBiomassPSImpl.getNumberOfShelves());
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
        if (myBiomassPSImpl.autoHarvestAndReplantEnabled()) {
            if (myCrop.readyForHarvest() || myCrop.isDead()) {
            	float age = myCrop.getDaysOfGrowth();
                BioMatter biomassProduced = myCrop.harvest();
                myLogger.info("ShelfImpl: Harvested " + biomassProduced.mass
                        + "kg of " + myCrop.getPlantTypeString() + " ("+ biomassProduced.inedibleFraction +" inedible) after " +age+ " days of growth on tick " + myBiomassPSImpl.getMyTicks());
                myBiomassPSImpl
                        .getBiomassProducerDefinitionImpl()
                        .pushFractionalResourceToBiomassStore(biomassProduced,
                                1f / myBiomassPSImpl.getNumberOfShelves());
                myCrop.reset();
            }
        }
    }

    public void tick() {
        if (cropAreaUsed > 0 && (myBiomassPSImpl.getMyTicks() >= myStartTick)) {
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
    
    public void kill(){
    	myCrop.kill();
    }
    
}