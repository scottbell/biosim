package com.traclabs.biosim.server.simulation.food;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.PlantPOA;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.server.util.MathUtils;
import com.traclabs.biosim.util.MersenneTwister;

/**
 * Plant
 * 
 * @author Scott Bell
 */

public abstract class PlantImpl extends PlantPOA {
    protected Logger myLogger = Logger.getLogger(PlantImpl.class);

    private static Random myRandomGen = new MersenneTwister();

    protected int myAge = 0;

    private boolean hasDied = false;

    private boolean canopyClosed = false;

    protected ShelfImpl myShelfImpl;

    private float myAveragePPF = 0f;

    private float myTotalPPF = 0f;

    private int myNumberOfPPFReadings = 0;

    private float myAverageWaterNeeded = 0f;

    private float myTotalWaterNeeded = 0f;

    private float myAverageCO2Concentration = 0f;

    private float myTotalCO2Concentration = 0f;

    private int myNumberOfCO2ConcentrationReadings = 0;

    private float myCurrentWaterInsideInedibleBiomass = 0f;

    private float myCurrentWaterInsideEdibleBiomass = 0f;

    private float myCurrentTotalWetBiomass = 0f;

    private float myCurrentEdibleWetBiomass = 0f;

    private float myCurrentEdibleDryBiomass = 0f;

    private float myCurrentDryBiomass = 0f;

    private float myLastTotalWetBiomass = 0f;

    private float myLastEdibleWetBiomass = 0f;

    private float myWaterNeeded = 0f;

    private float myWaterLevel = 0f;

    private float CQY = 0f;

    private float carbonUseEfficiency24 = 0f;

    private float totalO2GramsProduced = 0f;

    private float totalCO2GramsConsumed = 0f;

    private float totalCO2MolesConsumed = 0f;

    private float totalWaterLitersTranspired = 0f;

    private float myTimeTillCanopyClosure = 0f;

    protected float[] canopyClosureConstants = new float[25];

    protected float[] canopyQYConstants = new float[25];
    
    private float myPPFFractionAbsorbed = 0f;

    private List<Float> myCanopyClosurePPFValues;

    private List<Float>  myCanopyClosureCO2Values;

    private static final float WATER_TILL_DEAD = 200f;

    private static final float WATER_RECOVERY_RATE = 0.005f;

    private static final float CO2_LOW_TILL_DEAD = 24f;

    private static final float CO2_LOW_RECOVERY_RATE = 0.005f;

    private static final float CO2_RATIO_LOW = 500f;

    private static final float CO2_HIGH_TILL_DEAD = 24f;

    private static final float CO2_HIGH_RECOVERY_RATE = 0.05f;

    private static final float CO2_RATIO_HIGH = 20000f;

    private static final float HEAT_TILL_DEAD = 48f;

    private static final float HEAT_RECOVERY_RATE = 0.05f;

    private static final float DANGEROUS_HEAT_LEVEL = 300000f;

    private static final float LIGHT_TILL_DEAD = 150f;

    private static final float LIGHT_RECOVERY_RATE = 0.005f;

    private float myProductionRate = 1f;
    
    private float myMolesOfCO2Inhaled = 0f;
    
    public PlantImpl(ShelfImpl pShelfImpl) {
        myShelfImpl = pShelfImpl;
        Arrays.fill(canopyClosureConstants, 0f);
        Arrays.fill(canopyQYConstants, 0f);
        myAveragePPF = getInitialPPFValue();
        myAverageCO2Concentration = getInitialCO2Value();
        myCanopyClosurePPFValues = new Vector<Float>(getTAInitialValue());
        myCanopyClosureCO2Values = new Vector<Float>(getTAInitialValue());
    }

    protected abstract float getBCF();

    protected abstract float getCarbonUseEfficiency24();

    protected abstract float getPhotoperiod();

    protected abstract float getNominalPhotoperiod();

    protected abstract float getN();

    protected abstract float getTimeAtCanopySenescence();

    protected abstract float getCQYMin();

    protected abstract float getTimeAtCropMaturity();

    protected abstract float getOPF();

    protected abstract float calculateCanopyStomatalConductance();

    protected abstract float calculateAtmosphericAeroDynamicConductance();

    protected abstract float getProtectedFractionOfEdibleBiomass();

    protected abstract float getProtectedEdibleFreshBasisWaterContent();

    protected abstract float getProtectedInedibleFreshBasisWaterContent();

    protected abstract float getTimeAtOrganFormation();

    protected abstract float getConstantPPF();

    public abstract PlantType getPlantType();

    public abstract String getPlantTypeString();

    protected abstract float getInitialPPFValue();

    protected abstract float getInitialCO2Value();

    protected abstract int getTAInitialValue();

    public float getPPFNeeded() {
        return getConstantPPF();
    }

    public void reset() {
        myAge = 0;
        hasDied = false;
        canopyClosed = false;
        myTotalPPF = 0f;
        myNumberOfPPFReadings = 0;
        myAverageWaterNeeded = 0f;
        myTotalWaterNeeded = 0f;
        myTotalCO2Concentration = 0f;
        myNumberOfCO2ConcentrationReadings = 0;
        myCurrentWaterInsideInedibleBiomass = 0f;
        myCurrentWaterInsideEdibleBiomass = 0f;
        myCurrentTotalWetBiomass = 0f;
        myCurrentEdibleWetBiomass = 0f;
        myCurrentEdibleDryBiomass = 0f;
        myCurrentDryBiomass = 0f;
        myLastTotalWetBiomass = 0f;
        myLastEdibleWetBiomass = 0f;
        myWaterNeeded = 0f;
        myWaterLevel = 0f;
        CQY = 0f;
        carbonUseEfficiency24 = 0f;
        totalO2GramsProduced = 0f;
        totalCO2GramsConsumed = 0f;
        totalCO2MolesConsumed = 0f;
        totalWaterLitersTranspired = 0f;
        myTimeTillCanopyClosure = 0f;
        myPPFFractionAbsorbed = 0f;
        myProductionRate = 1f;
        myMolesOfCO2Inhaled = 0f;
        myCanopyClosurePPFValues = new Vector<Float>(getTAInitialValue());
        myCanopyClosureCO2Values = new Vector<Float>(getTAInitialValue());
        myAveragePPF = getInitialPPFValue();
        myAverageCO2Concentration = getInitialCO2Value();
    }

    public void tick() {
        myAge++;
        if (!hasDied) {
            growBiomass();
            myTotalWaterNeeded += myWaterNeeded;
            myAverageWaterNeeded = myTotalWaterNeeded / myAge;
            healthCheck();
        }
    }
    
    private void healthCheck(){
        if (myWaterNeeded < myWaterLevel){
            myLogger.warn("Not enough water, asked for "+myWaterNeeded + " and received " + myWaterLevel);
         }
        if (myAveragePPF > DANGEROUS_HEAT_LEVEL){
           myLogger.warn("Average PPF too high at "+myAveragePPF + " (should be " + DANGEROUS_HEAT_LEVEL +")");
        }
        if (myAverageCO2Concentration < CO2_RATIO_LOW){
            myLogger.warn("Average CO2 too low at "+myAverageCO2Concentration + " (should be " + CO2_RATIO_LOW +")");
        }
        if (myAverageCO2Concentration > CO2_RATIO_HIGH){
            myLogger.warn("Average CO2 too high at "+myAverageCO2Concentration + " (should be " + CO2_RATIO_HIGH +")");
        }
        if (myAverageCO2Concentration > CO2_RATIO_HIGH){
            myLogger.warn("Average CO2 too high at "+myAverageCO2Concentration + " (should be " + CO2_RATIO_HIGH +")");
        }
    }

    public boolean isDead() {
        return hasDied;
    }

    public boolean readyForHarvest() {
        //myLogger.debug("daysOfGrowth: " + getDaysOfGrowth() + "and timeAtCropMaturity: " + getTimeAtCropMaturity());
        return (getDaysOfGrowth() >= getTimeAtCropMaturity());
    }

    public BioMatter harvest() {
        float inedibleFraction = 1f - (myCurrentEdibleWetBiomass / myCurrentTotalWetBiomass);
        BioMatter newBioMatter = new BioMatter(myCurrentTotalWetBiomass,
                inedibleFraction, myCurrentWaterInsideEdibleBiomass,
                myCurrentWaterInsideInedibleBiomass, getPlantType());
        myLogger.info("harvested: " + newBioMatter.mass + " kg of " + getPlantTypeString() + " after "+ getDaysOfGrowth() +" days of growth, of which " + newBioMatter.inedibleFraction * 100 + "% is inedible");
        reset();
        return newBioMatter;
    }

    public void shine(float pPPF) {
        myTotalPPF += pPPF;
        myNumberOfPPFReadings++;
        myAveragePPF = myTotalPPF / myNumberOfPPFReadings;
        if (!canopyClosed)
        	myCanopyClosurePPFValues.add(pPPF);
        //myLogger.debug("pPPF: " + pPPF);
    }

    public void kill(){
        reset();
        hasDied = true;
    }

    private float calculateDailyCanopyTranspirationRate() {
        float airPressure = myShelfImpl.getBiomassPSImpl()
                .getAirProducerDefinition().getEnvironments()[0]
                .getTotalPressure();
        float canopySurfaceConductance = calculateCanopySurfaceConductance();
        float vaporPressureDeficit = calculateVaporPressureDeficit();
        float photoperiod = getPhotoperiod();
        //myLogger.debug("airPressure: " + airPressure);
        //myLogger.debug("canopySurfaceConductance:" + canopySurfaceConductance);
        //myLogger.debug("vaporPressureDeficit:" + vaporPressureDeficit);
        //myLogger.debug("getPhotoperiod(): " + getPhotoperiod());
        return 3600f * photoperiod * (18.015f / 998.23f)
                * canopySurfaceConductance
                * (vaporPressureDeficit / airPressure);
    }

    protected float calculateVaporPressureDeficit() {
        float saturatedMoistureVaporPressure = calculateSaturatedMoistureVaporPressure();
        float actualMoistureVaporPressure = calculateActualMoistureVaporPressure();
        //myLogger.debug("saturatedMoistureVaporPressure:" + saturatedMoistureVaporPressure);
        //myLogger.debug("actualMoistureVaporPressure:" + actualMoistureVaporPressure);
        float vaporPressureDeficit = saturatedMoistureVaporPressure
                - actualMoistureVaporPressure;
        //myLogger.debug("vaporPressureDeficit:" + vaporPressureDeficit);
        if (vaporPressureDeficit < 0)
            return 0f;
		return vaporPressureDeficit;
    }

    private float calculateSaturatedMoistureVaporPressure() {
        float temperatureLight = myShelfImpl.getBiomassPSImpl()
                .getAirProducerDefinition().getEnvironments()[0]
                .getTemperature();
        float exponent = (17.4f * temperatureLight) / (temperatureLight + 239f);
        //myLogger.debug("temperatureLight: " + temperatureLight);
        //myLogger.debug("exponent: " + exponent);
        return 0.611f * MathUtils.exp(exponent);
    }

    private float calculateActualMoistureVaporPressure() {
        return myShelfImpl.getBiomassPSImpl().getAirProducerDefinition()
                .getEnvironments()[0].getVaporStore().getPressure();
    }

    protected float calculateNetCanopyPhotosynthesis() {
        float plantGrowthDiurnalCycle = 24f;
        float grossCanopyPhotosynthesis = calculateGrossCanopyPhotosynthesis();
        float photoperiod = getPhotoperiod();
        //myLogger.debug("plantGrowthDiurnalCycle:" + plantGrowthDiurnalCycle);
        //myLogger.debug("carbonUseEfficiency24:" + carbonUseEfficiency24);
        myLogger
                .debug("grossCanopyPhotosynthesis:" + grossCanopyPhotosynthesis);
        //myLogger.debug("photoperiod: " + photoperiod);
        return (((plantGrowthDiurnalCycle - photoperiod) / plantGrowthDiurnalCycle) + ((photoperiod * carbonUseEfficiency24) / plantGrowthDiurnalCycle))
                * grossCanopyPhotosynthesis;
    }

    private float calculateGrossCanopyPhotosynthesis() {
        float PPF = getAveragePPF();
        //myLogger.debug("CQY: " + CQY);
        //myLogger.debug("PPF: " + PPF);
        //myLogger.debug("myPPFFractionAbsorbed:" + myPPFFractionAbsorbed);
        return myPPFFractionAbsorbed * CQY * PPF;
    }

    private float calculateCanopySurfaceConductance() {
        float canopyStomatalConductance = calculateCanopyStomatalConductance();
        float atmosphericAeroDynamicConductance = calculateAtmosphericAeroDynamicConductance();
        myLogger
                .debug("canopyStomatalConductance:" + canopyStomatalConductance);
        //myLogger.debug("atmosphericAeroDynamicConductance:" + atmosphericAeroDynamicConductance);
        return (atmosphericAeroDynamicConductance * canopyStomatalConductance)
                / (canopyStomatalConductance + atmosphericAeroDynamicConductance);
    }

    private void growBiomass() {
        float waterFraction = 0f;
        float litersOfWaterProduced = 0f;
        myLastTotalWetBiomass = myCurrentTotalWetBiomass;
        myLastEdibleWetBiomass = myCurrentEdibleWetBiomass;
        calculateAverageCO2Concentration();
        if (!canopyClosed) {
            //myLogger.debug("getDaysOfGrowth() = " + getDaysOfGrowth() + " and myTimeTillCanopyClosure = " + myTimeTillCanopyClosure);
            if ((getDaysOfGrowth() >= myTimeTillCanopyClosure) && (myAge > 1)) {
                canopyClosed = true;
                //myLogger.debug("canopy closed");
            } else
                myTimeTillCanopyClosure = calculateTimeTillCanopyClosure();
        }
        calculatePPFFractionAbsorbed();

        //Biomass Grown this tick
        float molecularWeightOfCarbon = 12.011f; //g/mol
        CQY = calculateCQY();
        carbonUseEfficiency24 = getCarbonUseEfficiency24();
        float dailyCarbonGain = calculateDailyCarbonGain();
        if (myAge % 24 == 0)
            //myLogger.debug(getDaysOfGrowth() + "\t" + dailyCarbonGain);
        totalCO2MolesConsumed += (dailyCarbonGain / 24f);
        //myLogger.debug("dailyCarbonGain: " + dailyCarbonGain);
        float cropGrowthRate = molecularWeightOfCarbon
                * (dailyCarbonGain / getBCF());
        //myLogger.debug("cropGrowthRate: " + cropGrowthRate);
        myCurrentDryBiomass += (cropGrowthRate / 1000 / 24f
                * myShelfImpl.getCropAreaUsed() * myProductionRate); //in
                                                                     // kilograms
                                                                     // per hour
        //myLogger.debug("myCurrentDryBiomass:" + myCurrentDryBiomass);

        if (getDaysOfGrowth() > getTimeAtOrganFormation())
            myCurrentEdibleDryBiomass += (cropGrowthRate / 1000 / 24f
                    * myShelfImpl.getCropAreaUsed() * getProtectedFractionOfEdibleBiomass());
        myCurrentWaterInsideEdibleBiomass = myCurrentEdibleDryBiomass
                * getProtectedEdibleFreshBasisWaterContent()
                / (1f - getProtectedEdibleFreshBasisWaterContent());

        myCurrentEdibleWetBiomass = myCurrentWaterInsideEdibleBiomass
                + myCurrentEdibleDryBiomass;
        float myCurrentInedbileDryBiomass = myCurrentDryBiomass
                - myCurrentEdibleDryBiomass;
        myCurrentWaterInsideInedibleBiomass = myCurrentInedbileDryBiomass
                * getProtectedInedibleFreshBasisWaterContent()
                / (1f - getProtectedInedibleFreshBasisWaterContent());
        float myCurrentInedibleWetBiomass = myCurrentWaterInsideInedibleBiomass
                + myCurrentInedbileDryBiomass;
        myCurrentTotalWetBiomass = myCurrentInedibleWetBiomass
                + myCurrentEdibleWetBiomass;
        //myLogger.debug("myCurrentEdibleDryBiomass: " + myCurrentEdibleDryBiomass);

        //Water In
        myWaterNeeded = calculateWaterUptake();
        myWaterLevel = myShelfImpl.takeWater(myWaterNeeded);
        //myLogger.debug("myWaterNeeded: " + myWaterNeeded);
        //myLogger.debug("myWaterLevel: " + myWaterLevel);
        if (myWaterNeeded == 0)
            waterFraction = 1f;
        else
            waterFraction = myWaterLevel / myWaterNeeded;

        if (waterFraction < 1f) {
            myCurrentDryBiomass -= (1f - waterFraction)
                    * (cropGrowthRate / 1000 / 24f * myShelfImpl
                            .getCropAreaUsed());
            if (getDaysOfGrowth() > getTimeAtOrganFormation())
                myCurrentEdibleDryBiomass -= (1f - waterFraction)
                        * (cropGrowthRate / 1000 / 24f
                                * myShelfImpl.getCropAreaUsed() * getProtectedFractionOfEdibleBiomass());
            //myLogger.debug("myCurrentDryBiomass:" + myCurrentDryBiomass);
            //myLogger.debug("myCurrentEdibleDryBiomass:" + myCurrentEdibleDryBiomass);

            myCurrentWaterInsideEdibleBiomass = myCurrentEdibleDryBiomass
                    * getProtectedEdibleFreshBasisWaterContent()
                    / (1f - getProtectedEdibleFreshBasisWaterContent());
            myCurrentEdibleWetBiomass = myCurrentWaterInsideEdibleBiomass
                    + myCurrentEdibleDryBiomass;

            myCurrentWaterInsideInedibleBiomass = myCurrentInedbileDryBiomass
                    * getProtectedInedibleFreshBasisWaterContent()
                    / (1f - getProtectedInedibleFreshBasisWaterContent());
            myCurrentInedibleWetBiomass = myCurrentWaterInsideInedibleBiomass
                    + myCurrentInedbileDryBiomass;
            myCurrentTotalWetBiomass = myCurrentInedibleWetBiomass
                    + myCurrentEdibleWetBiomass;
        }
        //Breathe Air
        float molesOfCO2ToInhale = 0f;
        if (waterFraction < 1f)
            molesOfCO2ToInhale = waterFraction * dailyCarbonGain
                    * myShelfImpl.getCropAreaUsed() / 24f;
        else
            molesOfCO2ToInhale = dailyCarbonGain
                    * myShelfImpl.getCropAreaUsed() / 24f;
        myMolesOfCO2Inhaled = myShelfImpl.getBiomassPSImpl()
                .getAirConsumerDefinition().getEnvironments()[0]
                .getCO2Store().take(molesOfCO2ToInhale);
        totalCO2GramsConsumed += myMolesOfCO2Inhaled * 44f;
        //myLogger.debug("totalCO2GramsConsumed:" + totalCO2GramsConsumed);
        myShelfImpl.getBiomassPSImpl().addAirInputActualFlowRates(0,
        		myMolesOfCO2Inhaled);
        //myLogger.debug("molesOfCO2ToInhale:" + molesOfCO2ToInhale);

        //Exhale Air
        float dailyO2MolesProduced = 0f;
        if (waterFraction < 1f)
            dailyO2MolesProduced = waterFraction * getOPF()
                    * dailyCarbonGain * myShelfImpl.getCropAreaUsed();
        else
            dailyO2MolesProduced = getOPF() * dailyCarbonGain
                    * myShelfImpl.getCropAreaUsed();
        float dailyO2GramsProduced = dailyO2MolesProduced * 32f;
        //myLogger.debug("dailyO2MolesProduced:" + dailyO2MolesProduced);
        //myLogger.debug("dailyO2GramsProduced:" + dailyO2GramsProduced);
        totalO2GramsProduced += (dailyO2GramsProduced / 24f);
        //myLogger.debug("totalO2GramsProduced:" + totalO2GramsProduced);
        float O2Produced = dailyO2MolesProduced / 24f; //in mol of oxygen per
        // hour
        float O2Exhaled = myShelfImpl.getBiomassPSImpl()
                .getAirProducerDefinition().getEnvironments()[0]
                .getO2Store().add(O2Produced);
        myShelfImpl.getBiomassPSImpl()
                .addAirOutputActualFlowRates(0, O2Exhaled);
        //myLogger.debug("O2Produced: " + O2Produced);

        //Water Vapor Produced
        if (waterFraction < 1f)
            litersOfWaterProduced = waterFraction
                    * calculateDailyCanopyTranspirationRate() / 24f
                    * myShelfImpl.getCropAreaUsed();
        else
            litersOfWaterProduced = calculateDailyCanopyTranspirationRate()
                    / 24f * myShelfImpl.getCropAreaUsed();
        totalWaterLitersTranspired += litersOfWaterProduced;
        //myLogger.debug("totalWaterLitersTranspired:" + totalWaterLitersTranspired);
        //consumedWaterBuffer.take(litersOfWaterProduced);
        //1/1000 liters per milliter, 1 gram per millilters, 8.016 grams per
        // mole
        float molesOfWaterProduced = waterLitersToMoles(litersOfWaterProduced);
        float molesOfWaterAdded = myShelfImpl.getBiomassPSImpl()
                .getAirProducerDefinition().getEnvironments()[0]
                .getVaporStore().add(molesOfWaterProduced);
        myShelfImpl.getBiomassPSImpl().addAirOutputActualFlowRates(0,
                molesOfWaterAdded);
        //myLogger.debug("litersOfWaterProduced:" + litersOfWaterProduced);
        //myLogger.debug("molesOfWaterProduced:" + molesOfWaterProduced);
        //myLogger.debug("molesOfWaterAdded:" + molesOfWaterAdded);
        //myLogger.debug("consumedWaterBuffer level:" + consumedWaterBuffer.getLevel());

    }

    private static float waterLitersToMoles(float pLiters) {
        return (pLiters * 998.23f) / 18.01524f; // 998.23g/liter, 18.01524g/mole
    }

    //in g/meters^2*day
    private float calculateDailyCarbonGain() {
        float photoperiod = getPhotoperiod();
        float PPF = getAveragePPF();
        //myLogger.debug("photoperiod: " + photoperiod);
        //myLogger.debug("carbonUseEfficiency24:" + carbonUseEfficiency24);
        //myLogger.debug("myPPFFractionAbsorbed:" + myPPFFractionAbsorbed);
        //myLogger.debug("CQY: " + CQY);
        //myLogger.debug("PPF: " + PPF);
        return (0.0036f * photoperiod * carbonUseEfficiency24
                * myPPFFractionAbsorbed * CQY * PPF);
    }

    //in liters/day
    private float calculateWaterUptake() {
        float dailyCanopyTranspirationRate = calculateDailyCanopyTranspirationRate()
                * myShelfImpl.getCropAreaUsed();
        float wetIncoporatedWaterUptake = calculateWetIncoporatedWaterUptake();
        float dryIncoporatedWaterUptake = calculateDryIncoporatedWaterUptake(
                dailyCanopyTranspirationRate, wetIncoporatedWaterUptake);
        //myLogger.debug("dailyCanopyTranspirationRate:" + dailyCanopyTranspirationRate);
        //myLogger
        //        .debug("wetIncoporatedWaterUptake:" + wetIncoporatedWaterUptake);
        //myLogger
        //        .debug("dryIncoporatedWaterUptake:" + dryIncoporatedWaterUptake);
        float waterUptake = ((dailyCanopyTranspirationRate / 24f)
                + wetIncoporatedWaterUptake + dryIncoporatedWaterUptake);
        return waterUptake;
    }

    private float calculateWetIncoporatedWaterUptake() {
        float myCurrentInedibleWetBiomass = myCurrentTotalWetBiomass
                - myCurrentEdibleWetBiomass;
        float myLastInedibleWetBiomass = myLastTotalWetBiomass
                - myLastEdibleWetBiomass;
        if (myCurrentInedibleWetBiomass < 0)
            myCurrentInedibleWetBiomass = 0f;
        if (myLastInedibleWetBiomass < 0)
            myLastInedibleWetBiomass = 0f;
        //myLogger.debug("myCurrentTotalWetBiomass:" + myCurrentTotalWetBiomass);
        //myLogger.debug("myCurrentEdibleWetBiomass: " + myCurrentEdibleWetBiomass);
        //myLogger.debug("myLastTotalWetBiomass:" + myLastTotalWetBiomass);
        //myLogger.debug("myLastEdibleWetBiomass:" + myLastEdibleWetBiomass);
        //myLogger.debug("myCurrentInedibleWetBiomass:" + myCurrentInedibleWetBiomass);
        //myLogger.debug("myLastInedibleWetBiomass:" + myLastInedibleWetBiomass);
        return (((myCurrentEdibleWetBiomass - myLastEdibleWetBiomass) * getProtectedEdibleFreshBasisWaterContent()) + ((myCurrentInedibleWetBiomass - myLastInedibleWetBiomass) * getProtectedInedibleFreshBasisWaterContent()));
    }

    private float calculateDryIncoporatedWaterUptake(
            float pDailyCanopyTranspirationRate,
            float pWetIncoporatedWaterUptake) {
        return (pDailyCanopyTranspirationRate + pWetIncoporatedWaterUptake) / 500;
    }

    protected float getAverageCO2Concentration() {
        return myAverageCO2Concentration;
    }

    //Convert current CO2 levels to moles of CO2 / moles of air
    private void calculateAverageCO2Concentration() {
        //Convert current CO2 levels to micromoles of CO2 / moles of air
        SimEnvironment myEnvironment = myShelfImpl.getBiomassPSImpl()
                .getAirConsumerDefinition().getEnvironments()[0];
        float CO2Moles = myEnvironment.getCO2Store().getCurrentLevel() * MathUtils.pow(10, 6); //in micro
        // moles
        float airMoles = myEnvironment.getTotalMoles(); //in moles
        if (CO2Moles <= 0)
            CO2Moles = MathUtils.pow(1f, -30f);
        else if (airMoles <= 0)
            airMoles = MathUtils.pow(1f, -30f);
        //myLogger.debug("CO2Moles: " + CO2Moles);
        //myLogger.debug("airMoles: " + airMoles);
        float currentCO2Concentration = (CO2Moles / airMoles);
        if (!canopyClosed)
        	myCanopyClosureCO2Values.add(currentCO2Concentration);
        //myLogger.debug("CO2_Concentration: " + currentCO2Concentration);
        myTotalCO2Concentration += currentCO2Concentration;
        myNumberOfCO2ConcentrationReadings++;
        myAverageCO2Concentration = myTotalCO2Concentration
                / myNumberOfCO2ConcentrationReadings;
        //myLogger.debug("myAverageCO2Concentration: " + myAverageCO2Concentration);
    }
    
    public float getTimeTillCanopyClosure(){
    	return myTimeTillCanopyClosure;
    }

    //returns the age in days
    protected float getDaysOfGrowth() {
        float daysOfGrowth = myAge / 24f;
        //myLogger.debug("daysOfGrowth: " + daysOfGrowth);
        //myLogger.debug("myAge: " + myAge);
        return daysOfGrowth;
    }

    private float getAverageForList(List pList, float fillerValue) {
        //myLogger.debug("*********************************************");
        if (pList.size() < 2)
            return fillerValue;
        float totalReal = 0f;
        for (Iterator iter = pList.iterator(); iter.hasNext();) {
            Float currentFloat = (Float) (iter.next());
            totalReal += currentFloat.floatValue();
        }
        //myLogger.debug("]");
        float totalFiller = fillerValue
                * ((myTimeTillCanopyClosure * 24f) - pList.size());

        //myLogger.debug("fillerValue = " + fillerValue);
        //myLogger.debug("totalFiller = " + totalFiller);
        //myLogger.debug("totalReal = " + totalReal);
        //myLogger.debug("pList.size() = " + pList.size());
        //myLogger.debug("myTimeTillCanopyClosure * 24f = "+ myTimeTillCanopyClosure * 24f);
        //myLogger.debug("myTimeTillCanopyClosure = " + myTimeTillCanopyClosure);
        //myLogger.debug("getAverageForList returning " + ((totalFiller + totalReal) / (myTimeTillCanopyClosure * 24f)));

        return (totalFiller + totalReal) / (myTimeTillCanopyClosure * 24f);
    }

    private float calculateTimeTillCanopyClosure() {
        float thePPF = getAverageForList(myCanopyClosurePPFValues, myAveragePPF)
                * getPhotoperiod() / getNominalPhotoperiod();
        float oneOverPPf = 1f / thePPF;
        float thePPFsquared = MathUtils.pow(thePPF, 2f);
        float thePPFcubed = MathUtils.pow(thePPF, 3f);

        float theCO2 = getAverageForList(myCanopyClosureCO2Values,
                myAverageCO2Concentration);
        float oneOverCO2 = 1f / theCO2;
        float theCO2squared = MathUtils.pow(theCO2, 2f);
        float theCO2cubed = MathUtils.pow(theCO2, 3f);

        float tA = canopyClosureConstants[0] * oneOverPPf * oneOverCO2
                + canopyClosureConstants[1] * oneOverPPf
                + canopyClosureConstants[2] * oneOverPPf * theCO2
                + canopyClosureConstants[3] * oneOverPPf * theCO2squared
                + canopyClosureConstants[4] * oneOverPPf * theCO2cubed
                + canopyClosureConstants[5] * oneOverCO2
                + canopyClosureConstants[6] + canopyClosureConstants[7]
                * theCO2 + canopyClosureConstants[8] * theCO2squared
                + canopyClosureConstants[9] * theCO2cubed
                + canopyClosureConstants[10] * thePPF * oneOverCO2
                + canopyClosureConstants[11] * thePPF
                + canopyClosureConstants[12] * thePPF * theCO2
                + canopyClosureConstants[13] * thePPF * theCO2squared
                + canopyClosureConstants[14] * thePPF * theCO2cubed
                + canopyClosureConstants[15] * thePPFsquared * oneOverCO2
                + canopyClosureConstants[16] * thePPFsquared
                + canopyClosureConstants[17] * thePPFsquared * theCO2
                + canopyClosureConstants[18] * thePPFsquared * theCO2squared
                + canopyClosureConstants[19] * thePPFsquared * theCO2cubed
                + canopyClosureConstants[20] * thePPFcubed * oneOverCO2
                + canopyClosureConstants[21] * thePPFcubed
                + canopyClosureConstants[22] * thePPFcubed * theCO2
                + canopyClosureConstants[23] * thePPFcubed * theCO2squared
                + canopyClosureConstants[24] * thePPFcubed * theCO2cubed;
        if ((tA < 0) || (Float.isNaN(tA))) {
            tA = 0;
            //myLogger.debug("Time till canopy closure is negative or NAN!");
        }
        //round the number according to Jim
        long tALong = Math.round(tA);
        tA = tALong;
        //myLogger.debug("tA: " + tA);
        return tA;
    }

    private void calculatePPFFractionAbsorbed() {
        float PPFFractionAbsorbedMax = 0.93f;
        //myLogger.debug("PPFFractionAbsorbedMax:" + PPFFractionAbsorbedMax);
        //myLogger.debug("myTimeTillCanopyClosure:" + myTimeTillCanopyClosure);
        //myLogger.debug("getDaysOfGrowth():" + getDaysOfGrowth());
        //myLogger.debug("getN(): " + getN());
        if (canopyClosed)
            myPPFFractionAbsorbed = PPFFractionAbsorbedMax;
        else
            myPPFFractionAbsorbed += (calculateDaDt() / 24f);
    }

    private float calculateDaDt() {
        float PPFFractionAbsorbedMax = 0.93f;
        if (myTimeTillCanopyClosure <= 0)
            return 0f;
        float daDt = PPFFractionAbsorbedMax
                * getN()
                * MathUtils.pow((getDaysOfGrowth() / myTimeTillCanopyClosure),
                        getN() - 1f) * (1f / myTimeTillCanopyClosure);
        //myLogger.debug("daDt: " + daDt);
        return daDt;
    }

    private float calculateCQYMax() {
        float thePPF = getAveragePPF();
        float oneOverPPf = 1f / thePPF;
        float thePPFsquared = MathUtils.pow(thePPF, 2f);
        float thePPFcubed = MathUtils.pow(thePPF, 3f);
        //myLogger.debug("thePPF: " + thePPF);
        //myLogger.debug("oneOverPPf: " + oneOverPPf);
        //myLogger.debug("thePPFsquared: " + thePPFsquared);
        //myLogger.debug("thePPFcubed: " + thePPFcubed);

        float theCO2 = getAverageCO2Concentration();
        float oneOverCO2 = 1f / theCO2;
        float theCO2squared = MathUtils.pow(theCO2, 2f);
        float theCO2cubed = MathUtils.pow(theCO2, 3f);
        //myLogger.debug("theCO2: " + theCO2);
        //myLogger.debug("oneOverCO2: " + oneOverCO2);
        //myLogger.debug("theCO2squared: " + theCO2squared);
        //myLogger.debug("theCO2cubed: " + theCO2cubed);

        float theCQYMax = canopyQYConstants[0] * oneOverPPf * oneOverCO2
                + canopyQYConstants[1] * oneOverPPf + canopyQYConstants[2]
                * oneOverPPf * theCO2 + canopyQYConstants[3] * oneOverPPf
                * theCO2squared + canopyQYConstants[4] * oneOverPPf
                * theCO2cubed + canopyQYConstants[5] * oneOverCO2
                + canopyQYConstants[6] + canopyQYConstants[7] * theCO2
                + canopyQYConstants[8] * theCO2squared + canopyQYConstants[9]
                * theCO2cubed + canopyQYConstants[10] * thePPF * oneOverCO2
                + canopyQYConstants[11] * thePPF + canopyQYConstants[12]
                * thePPF * theCO2 + canopyQYConstants[13] * thePPF
                * theCO2squared + canopyQYConstants[14] * thePPF * theCO2cubed
                + canopyQYConstants[15] * thePPFsquared * oneOverCO2
                + canopyQYConstants[16] * thePPFsquared + canopyQYConstants[17]
                * thePPFsquared * theCO2 + canopyQYConstants[18]
                * thePPFsquared * theCO2squared + canopyQYConstants[19]
                * thePPFsquared * theCO2cubed + canopyQYConstants[20]
                * thePPFcubed * oneOverCO2 + canopyQYConstants[21]
                * thePPFcubed + canopyQYConstants[22] * thePPFcubed * theCO2
                + canopyQYConstants[23] * thePPFcubed * theCO2squared
                + canopyQYConstants[24] * thePPFcubed * theCO2cubed;
        if ((theCQYMax < 0) || (Float.isNaN(theCQYMax))) {
            theCQYMax = 0;
            //myLogger.debug("CQYMax is negative or NaN!");
        }
        //myLogger.debug("theCQYMax: " + theCQYMax);
        return theCQYMax;
    }

    private float calculateCQY() {
        float CQYMax = calculateCQYMax();
        float timeTillCanopySenescence = getTimeAtCanopySenescence();
        //myLogger.debug("CQYMax: " + CQYMax);
        //myLogger.debug("timeTillCanopySenescence:" + timeTillCanopySenescence);
        if (getDaysOfGrowth() < getTimeAtCanopySenescence()) {
            return CQYMax;
        }
		float CQYMin = getCQYMin();
		float daysOfGrowth = getDaysOfGrowth();
		float timeTillCropMaturity = getTimeAtCropMaturity();
		float calculatedCQY = CQYMax
		        - ((CQYMax - CQYMin)
		                * (daysOfGrowth - timeTillCanopySenescence) / (timeTillCropMaturity - timeTillCanopySenescence));
		//myLogger.debug("CQYMin: " + CQYMin);
		//myLogger.debug("daysOfGrowth: " + daysOfGrowth);
		//myLogger.debug("timeTillCropMaturity: " + timeTillCropMaturity);
		if (calculatedCQY < 0f)
		    return 0f;
		return calculatedCQY;
    }

    private float getAveragePPF() {
        return myAveragePPF;
    }

    /**
     * @return Returns the myProductionRate.
     */
    protected float getProductionRate() {
        return myProductionRate;
    }

    /**
     * @param myProductionRate
     *            The myProductionRate to set.
     */
    protected void setProductionRate(float myProductionRate) {
        this.myProductionRate = myProductionRate;
    }

	public float getMolesOfCO2Inhaled() {
		return myMolesOfCO2Inhaled;
	}
	
	public static String getPlantTypeString(PlantType pType){
		if (pType == PlantType.DRY_BEAN)
            return "Dry Bean";
        else if (pType == PlantType.LETTUCE)
        	return "Lettuce";
        else if (pType == PlantType.PEANUT)
        	return "Peanut";
        else if (pType == PlantType.SOYBEAN)
        	return "Soybean";
        else if (pType == PlantType.RICE)
        	return "Rice";
        else if (pType == PlantType.SWEET_POTATO)
        	return "Sweet Potato";
        else if (pType == PlantType.TOMATO)
        	return "Tomato";
        else if (pType == PlantType.WHEAT)
        	return "Wheat";
        else if (pType == PlantType.WHITE_POTATO)
        	return "White Potato";
        else
        	return "Unknown";
	}
}

