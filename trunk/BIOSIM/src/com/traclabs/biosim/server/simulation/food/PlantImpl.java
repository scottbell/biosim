package com.traclabs.biosim.server.simulation.food;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.PlantPOA;
import com.traclabs.biosim.idl.simulation.food.PlantType;
import com.traclabs.biosim.server.simulation.framework.SimpleBuffer;

/**
 * Plant
 * 
 * @author Scott Bell
 */

public abstract class PlantImpl extends PlantPOA {
    private DecimalFormat numFormat;

    private Random myRandomGen;

    protected int myAge = 0;

    private boolean logInitialized = false;

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

    //in dry weight
    private float myCurrentTotalWaterInsideBiomass = 0f;

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

    private SimpleBuffer consumedWaterBuffer;

    private SimpleBuffer consumedCO2LowBuffer;

    private SimpleBuffer consumedCO2HighBuffer;

    private SimpleBuffer consumedHeatBuffer;

    private SimpleBuffer consumedLightBuffer;

    protected float[] canopyClosureConstants;

    protected float[] canopyQYConstants;

    private float myPPFFractionAbsorbed = 0f;

    private List myCanopyClosurePPFValues;

    private List myCanopyClosureCO2Values;

    private static final float WATER_TILL_DEAD = 200f;

    private static final float WATER_RECOVERY_RATE = 0.005f;

    private static final float CO2_LOW_TILL_DEAD = 24f;

    private static final float CO2_LOW_RECOVERY_RATE = 0.05f;

    private static final float CO2_RATIO_LOW = 500f;

    private static final float CO2_HIGH_TILL_DEAD = 24f;

    private static final float CO2_HIGH_RECOVERY_RATE = 0.05f;

    private static final float CO2_RATIO_HIGH = 20000f;

    private static final float HEAT_TILL_DEAD = 48f;

    private static final float HEAT_RECOVERY_RATE = 0.05f;

    private static final float DANGEROUS_HEAT_LEVEL = 300000f;

    private static final float LIGHT_TILL_DEAD = 150f;

    private static final float LIGHT_RECOVERY_RATE = 0.005f;

    public PlantImpl(ShelfImpl pShelfImpl) {
        myAge = 0;
        myShelfImpl = pShelfImpl;
        canopyClosureConstants = new float[25];
        canopyQYConstants = new float[25];
        Arrays.fill(canopyClosureConstants, 0f);
        Arrays.fill(canopyQYConstants, 0f);
        myAveragePPF = getInitialPPFValue();
        myAverageCO2Concentration = getInitialCO2Value();
        consumedWaterBuffer = new SimpleBuffer(WATER_TILL_DEAD
                * myShelfImpl.getCropAreaUsed(), WATER_TILL_DEAD
                * myShelfImpl.getCropAreaUsed());
        consumedLightBuffer = new SimpleBuffer(LIGHT_TILL_DEAD
                * myShelfImpl.getCropAreaUsed(), LIGHT_TILL_DEAD
                * myShelfImpl.getCropAreaUsed());
        consumedCO2LowBuffer = new SimpleBuffer(CO2_LOW_TILL_DEAD
                * CO2_RATIO_LOW, CO2_LOW_TILL_DEAD * CO2_RATIO_LOW);
        consumedCO2HighBuffer = new SimpleBuffer(CO2_HIGH_TILL_DEAD
                * CO2_RATIO_HIGH, CO2_HIGH_TILL_DEAD * CO2_RATIO_HIGH);
        consumedHeatBuffer = new SimpleBuffer(HEAT_TILL_DEAD
                * DANGEROUS_HEAT_LEVEL, HEAT_TILL_DEAD * DANGEROUS_HEAT_LEVEL);
        myRandomGen = new Random();
        numFormat = new DecimalFormat("#,##0.0;(#)");

        myCanopyClosurePPFValues = new Vector(getTAInitialValue());
        myCanopyClosureCO2Values = new Vector(getTAInitialValue());
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
        myCurrentTotalWetBiomass = 0f;
        myCurrentEdibleWetBiomass = 0f;
        myCurrentDryBiomass = 0f;
        myLastTotalWetBiomass = 0f;
        myLastEdibleWetBiomass = 0f;
        myCurrentEdibleDryBiomass = 0f;
        myCurrentTotalWaterInsideBiomass = 0f;
        myCurrentWaterInsideInedibleBiomass = 0f;
        myCurrentWaterInsideEdibleBiomass = 0f;
        myAverageWaterNeeded = 0f;
        myTotalWaterNeeded = 0f;
        myWaterNeeded = 0f;
        myWaterLevel = 0f;
        CQY = 0f;
        carbonUseEfficiency24 = 0f;
        myAveragePPF = getInitialPPFValue();
        myTotalPPF = 0f;
        myNumberOfPPFReadings = 0;
        myAverageCO2Concentration = getInitialCO2Value();
        myTotalCO2Concentration = 0f;
        myNumberOfCO2ConcentrationReadings = 0;
        myAge = 0;
        totalO2GramsProduced = 0f;
        totalCO2GramsConsumed = 0f;
        totalCO2MolesConsumed = 0f;
        totalWaterLitersTranspired = 0f;
        myPPFFractionAbsorbed = 0f;
        myTimeTillCanopyClosure = 0f;
        hasDied = false;
        canopyClosed = false;
        myCanopyClosurePPFValues.clear();
        myCanopyClosureCO2Values.clear();
        consumedWaterBuffer.reset();
        consumedCO2LowBuffer.reset();
        consumedCO2HighBuffer.reset();
        consumedHeatBuffer.reset();
        consumedLightBuffer.reset();
    }

    public void tick() {
        myAge++;
        if (!hasDied) {
            growBiomass();
            if (myAge > 1) {
                afflictPlants();
                healthCheck();
                recoverPlants();
            }
            myTotalWaterNeeded += myWaterNeeded;
            myAverageWaterNeeded = myTotalWaterNeeded / myAge;
        }
    }

    public boolean isDead() {
        return hasDied;
    }

    public boolean readyForHarvest() {
        //System.out.println("PlantImpl: daysOfGrowth: "+getDaysOfGrowth()+"
        // and timeAtCropMaturity: "+getTimeAtCropMaturity());
        return (getDaysOfGrowth() >= getTimeAtCropMaturity());
    }

    public BioMatter harvest() {
        float inedibleFraction = 1f - (myCurrentEdibleWetBiomass / myCurrentTotalWetBiomass);
        BioMatter newBioMatter = new BioMatter(myCurrentTotalWetBiomass,
                inedibleFraction, myCurrentWaterInsideEdibleBiomass,
                myCurrentWaterInsideInedibleBiomass, getPlantType());
        reset();
        return newBioMatter;
    }

    public void shine(float pPPF) {
        myTotalPPF += pPPF;
        myNumberOfPPFReadings++;
        myAveragePPF = myTotalPPF / myNumberOfPPFReadings;
        if (!canopyClosed)
            addToCanopyClosureList(pPPF, myCanopyClosurePPFValues);
        //System.out.println("PlantImpl: pPPF: "+pPPF);
    }

    /**
     * adds a float to the list(for PPF and CO2 values used in the tA
     * calculation),
     */
    private void addToCanopyClosureList(float pValueToInsert, List pList) {
        pList.add(new Float(pValueToInsert));
    }

    private void recoverPlants() {
        consumedWaterBuffer.add(WATER_RECOVERY_RATE
                * consumedWaterBuffer.getCapacity());
        consumedCO2LowBuffer.add(CO2_LOW_RECOVERY_RATE
                * consumedCO2LowBuffer.getCapacity());
        consumedCO2HighBuffer.add(CO2_HIGH_RECOVERY_RATE
                * consumedCO2HighBuffer.getCapacity());
        consumedHeatBuffer.add(HEAT_RECOVERY_RATE
                * consumedHeatBuffer.getCapacity());
        consumedLightBuffer.add(LIGHT_RECOVERY_RATE
                * consumedLightBuffer.getCapacity());
    }

    /**
     * If not all the resources required were consumed, we damage the plant
     */
    private void afflictPlants() {
        //System.out.println("PlantImpl: before: water buffer at
        // "+consumedWaterBuffer.getLevel()+" of
        // "+consumedWaterBuffer.getCapacity());
        //System.out.println("PlantImpl: before: light buffer at
        // "+consumedLightBuffer.getLevel()+" of
        // "+consumedLightBuffer.getCapacity());

        consumedWaterBuffer.setCapacity(myAverageWaterNeeded * WATER_TILL_DEAD);
        consumedLightBuffer.setCapacity(getPPFNeeded() * LIGHT_TILL_DEAD);

        //System.out.println("PlantImpl: after asked for "+myWaterNeeded+" and
        // got "+myWaterLevel+", water buffer at
        // "+consumedWaterBuffer.getLevel()+" of
        // "+consumedWaterBuffer.getCapacity()+" and going to take
        // "+(myWaterNeeded - myWaterLevel) +" from death buffer");
        //System.out.println("PlantImpl: after asked for "+getPPFNeeded()+" and
        // got "+myAveragePPF+", light buffer at
        // "+consumedLightBuffer.getLevel()+" of
        // "+consumedLightBuffer.getCapacity()+" and going to take
        // "+(getPPFNeeded() - myAveragePPF));

        consumedWaterBuffer.take(myWaterNeeded - myWaterLevel);
        consumedLightBuffer.take(getPPFNeeded() - myAveragePPF);
        if (myAveragePPF > DANGEROUS_HEAT_LEVEL)
            consumedHeatBuffer.take(myAveragePPF - DANGEROUS_HEAT_LEVEL);
        if (myAverageCO2Concentration < CO2_RATIO_LOW)
            consumedCO2LowBuffer
                    .take(CO2_RATIO_LOW - myAverageCO2Concentration);
        if (myAverageCO2Concentration > CO2_RATIO_HIGH)
            consumedCO2HighBuffer.take(myAverageCO2Concentration
                    - CO2_RATIO_HIGH);
    }

    /**
     * Checks to see if crew memeber has been lethally damaged (i.e., hasn't
     * received a resource for too many ticks)
     */
    private void healthCheck() {
        //check for death
        float randomNumber = myRandomGen.nextFloat();
        float CO2RiskLowReturn = sigmoidLikeProbability((consumedCO2LowBuffer
                .getCapacity() - consumedCO2LowBuffer.getLevel())
                / consumedCO2LowBuffer.getCapacity());
        float CO2RiskHighReturn = sigmoidLikeProbability((consumedCO2HighBuffer
                .getCapacity() - consumedCO2HighBuffer.getLevel())
                / consumedCO2HighBuffer.getCapacity());
        float waterRiskReturn = sigmoidLikeProbability((consumedWaterBuffer
                .getCapacity() - consumedWaterBuffer.getLevel())
                / consumedWaterBuffer.getCapacity());
        float heatRiskReturn = sigmoidLikeProbability((consumedHeatBuffer
                .getCapacity() - consumedHeatBuffer.getLevel())
                / consumedHeatBuffer.getCapacity());
        float lightRiskReturn = sigmoidLikeProbability((consumedLightBuffer
                .getCapacity() - consumedLightBuffer.getLevel())
                / consumedLightBuffer.getCapacity());

        if (CO2RiskLowReturn > randomNumber) {
            hasDied = true;
            System.out.println("PlantImpl: " + getPlantTypeString()
                    + " crops have died from low CO2 at " + getDaysOfGrowth()
                    + " days (risk was " + (CO2RiskLowReturn * 100) + "%)");
        } else if (CO2RiskHighReturn > randomNumber) {
            hasDied = true;
            System.out.println("PlantImpl: " + getPlantTypeString()
                    + " crops have died from high CO2 at " + getDaysOfGrowth()
                    + " days (risk was " + (CO2RiskHighReturn * 100) + "%)");
        } else if (waterRiskReturn > randomNumber) {
            hasDied = true;
            System.out.println("PlantImpl: " + getPlantTypeString()
                    + " crops have died from lack of water at "
                    + getDaysOfGrowth() + " days (risk was "
                    + (waterRiskReturn * 100) + "%)");
        } else if (heatRiskReturn > randomNumber) {
            hasDied = true;
            System.out.println("PlantImpl: " + getPlantTypeString()
                    + " crops have died from lack of heat at "
                    + getDaysOfGrowth() + " days (risk was "
                    + (heatRiskReturn * 100) + "%)");
        } else if (lightRiskReturn > randomNumber) {
            hasDied = true;
            System.out.println("PlantImpl: " + getPlantTypeString()
                    + " crops have died from lack of light at "
                    + getDaysOfGrowth() + " days (risk was "
                    + (lightRiskReturn * 100) + "%)");
        }
        //if died, kill
        if (hasDied) {
            reset();
            hasDied = true;
        }
    }

    private float calculateDailyCanopyTranspirationRate() {
        float airPressure = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0]
                .getTotalPressure();
        float canopySurfaceConductance = calculateCanopySurfaceConductance();
        float vaporPressureDeficit = calculateVaporPressureDeficit();
        float photoperiod = getPhotoperiod();
        //System.out.println("PlantImpl: airPressure: "+airPressure);
        //System.out.println("PlantImpl: canopySurfaceConductance:
        // "+canopySurfaceConductance);
        //System.out.println("PlantImpl: vaporPressureDeficit:
        // "+vaporPressureDeficit);
        //System.out.println("PlantImpl: getPhotoperiod(): "+getPhotoperiod());
        return 3600f * photoperiod * (18.015f / 998.23f)
                * canopySurfaceConductance
                * (vaporPressureDeficit / airPressure);
    }

    protected float calculateVaporPressureDeficit() {
        float saturatedMoistureVaporPressure = calculateSaturatedMoistureVaporPressure();
        float actualMoistureVaporPressure = calculateActualMoistureVaporPressure();
        //System.out.println("PlantImpl: saturatedMoistureVaporPressure:
        // "+saturatedMoistureVaporPressure);
        //System.out.println("PlantImpl: actualMoistureVaporPressure:
        // "+actualMoistureVaporPressure);
        float vaporPressureDeficit = saturatedMoistureVaporPressure
                - actualMoistureVaporPressure;
        //System.out.println("PlantImpl: vaporPressureDeficit:
        // "+vaporPressureDeficit);
        if (vaporPressureDeficit < 0)
            return 0f;
        else
            return vaporPressureDeficit;
    }

    private float calculateSaturatedMoistureVaporPressure() {
        float temperatureLight = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0]
                .getTemperature();
        float exponent = (17.4f * temperatureLight) / (temperatureLight + 239f);
        //System.out.println("PlantImpl: temperatureLight: "+temperatureLight);
        //System.out.println("PlantImpl: exponent: "+exponent);
        return 0.611f * exp(exponent);
    }

    private float calculateActualMoistureVaporPressure() {
        return myShelfImpl.getBiomassRSImpl().getAirInputs()[0]
                .getWaterPressure();
    }

    protected float calculateNetCanopyPhotosynthesis() {
        float plantGrowthDiurnalCycle = 24f;
        float grossCanopyPhotosynthesis = calculateGrossCanopyPhotosynthesis();
        float photoperiod = getPhotoperiod();
        //System.out.println("PlantImpl: plantGrowthDiurnalCycle:
        // "+plantGrowthDiurnalCycle);
        //System.out.println("PlantImpl: carbonUseEfficiency24:
        // "+carbonUseEfficiency24);
        //System.out.println("PlantImpl: grossCanopyPhotosynthesis:
        // "+grossCanopyPhotosynthesis);
        //System.out.println("PlantImpl: photoperiod: "+photoperiod);
        return (((plantGrowthDiurnalCycle - photoperiod) / plantGrowthDiurnalCycle) + ((photoperiod * carbonUseEfficiency24) / plantGrowthDiurnalCycle))
                * grossCanopyPhotosynthesis;
    }

    private float calculateGrossCanopyPhotosynthesis() {
        float PPF = getAveragePPF();
        //System.out.println("PlantImpl: CQY: "+CQY);
        //System.out.println("PlantImpl: PPF: "+PPF);
        //System.out.println("PlantImpl: myPPFFractionAbsorbed:
        // "+myPPFFractionAbsorbed);
        return myPPFFractionAbsorbed * CQY * PPF;
    }

    private float calculateCanopySurfaceConductance() {
        float canopyStomatalConductance = calculateCanopyStomatalConductance();
        float atmosphericAeroDynamicConductance = calculateAtmosphericAeroDynamicConductance();
        //System.out.println("PlantImpl: canopyStomatalConductance:
        // "+canopyStomatalConductance);
        //System.out.println("PlantImpl: atmosphericAeroDynamicConductance:
        // "+atmosphericAeroDynamicConductance);
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
            //System.out.println("getDaysOfGrowth() = "+getDaysOfGrowth()+" and
            // myTimeTillCanopyClosure = "+myTimeTillCanopyClosure);
            if ((getDaysOfGrowth() >= myTimeTillCanopyClosure) && (myAge > 1)) {
                canopyClosed = true;
                //System.out.println("canopy closed");
            } else
                myTimeTillCanopyClosure = calculateTimeTillCanopyClosure();
        }
        calculatePPFFractionAbsorbed();

        //Biomass Grown this tick
        float molecularWeightOfCarbon = 12.011f; //g/mol
        CQY = calculateCQY();
        carbonUseEfficiency24 = getCarbonUseEfficiency24();
        float dailyCarbonGain = calculateDailyCarbonGain();
        //if (myAge % 24 == 0)
        //	System.out.println(getDaysOfGrowth()+"\t"+dailyCarbonGain);
        totalCO2MolesConsumed += (dailyCarbonGain / 24f);
        //System.out.println("dailyCarbonGain: "+dailyCarbonGain);
        float cropGrowthRate = molecularWeightOfCarbon
                * (dailyCarbonGain / getBCF());
        //System.out.println("PlantImpl: cropGrowthRate: "+cropGrowthRate);
        myCurrentDryBiomass += (cropGrowthRate / 1000 / 24f * myShelfImpl
                .getCropAreaUsed()); //in kilograms per hour
        //System.out.println("PlantImpl: myCurrentDryBiomass:
        // "+myCurrentDryBiomass);

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
        //System.out.println("PlantImpl: myCurrentEdibleDryBiomass:
        // "+myCurrentEdibleDryBiomass);

        //Water In
        myWaterNeeded = calculateWaterUptake();
        myWaterLevel = myShelfImpl.takeWater(myWaterNeeded);
        consumedWaterBuffer.add(myWaterLevel);
        //System.out.println("PlantImpl: myWaterNeeded: "+myWaterNeeded);
        //System.out.println("PlantImpl: myWaterLevel: "+myWaterLevel);
        if (myWaterNeeded == 0)
            waterFraction = 1f;
        else
            waterFraction = myWaterLevel / myWaterNeeded;

        if (waterFraction < 1f) {
            myCurrentDryBiomass -= (float) (1f - waterFraction)
                    * (cropGrowthRate / 1000 / 24f * myShelfImpl
                            .getCropAreaUsed());
            if (getDaysOfGrowth() > getTimeAtOrganFormation())
                myCurrentEdibleDryBiomass -= (float) (1f - waterFraction)
                        * (cropGrowthRate / 1000 / 24f
                                * myShelfImpl.getCropAreaUsed() * getProtectedFractionOfEdibleBiomass());
            //System.out.println("PlantImpl: myCurrentDryBiomass:
            // "+myCurrentDryBiomass);
            //System.out.println("PlantImpl: myCurrentEdibleDryBiomass:
            // "+myCurrentEdibleDryBiomass);

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
        myCurrentTotalWaterInsideBiomass = myCurrentWaterInsideEdibleBiomass
                + myCurrentWaterInsideInedibleBiomass;

        //Breathe Air
        float molesOfCO2ToInhale = 0f;
        if (waterFraction < 1f)
            molesOfCO2ToInhale = (float) waterFraction * dailyCarbonGain
                    * myShelfImpl.getCropAreaUsed() / 24f;
        else
            molesOfCO2ToInhale = dailyCarbonGain
                    * myShelfImpl.getCropAreaUsed() / 24f;
        float molesOfCO2Inhaled = myShelfImpl.getBiomassRSImpl().getAirInputs()[0]
                .takeCO2Moles(molesOfCO2ToInhale);
        totalCO2GramsConsumed += molesOfCO2Inhaled * 44f;
        //System.out.println("PlantImpl: totalCO2GramsConsumed:
        // "+totalCO2GramsConsumed);
        myShelfImpl.getBiomassRSImpl().addAirInputActualFlowRates(0,
                molesOfCO2Inhaled);
        //System.out.println("PlantImpl: molesOfCO2ToInhale:
        // "+molesOfCO2ToInhale);

        //Exhale Air
        float dailyO2MolesProduced = 0f;
        if (waterFraction < 1f)
            dailyO2MolesProduced = (float) waterFraction * getOPF()
                    * dailyCarbonGain * myShelfImpl.getCropAreaUsed();
        else
            dailyO2MolesProduced = getOPF() * dailyCarbonGain
                    * myShelfImpl.getCropAreaUsed();
        float dailyO2GramsProduced = dailyO2MolesProduced * 32f;
        //System.out.println("PlantImpl: dailyO2MolesProduced:
        // "+dailyO2MolesProduced);
        //System.out.println("PlantImpl: dailyO2GramsProduced:
        // "+dailyO2GramsProduced);
        totalO2GramsProduced += (dailyO2GramsProduced / 24f);
        //System.out.println("PlantImpl: totalO2GramsProduced:
        // "+totalO2GramsProduced);
        float O2Produced = dailyO2MolesProduced / 24f; //in mol of oxygen per
        // hour
        float O2Exhaled = myShelfImpl.getBiomassRSImpl().getAirOutputs()[0]
                .addO2Moles(O2Produced);
        myShelfImpl.getBiomassRSImpl()
                .addAirOutputActualFlowRates(0, O2Exhaled);
        //System.out.println("PlantImpl: O2Produced: "+O2Produced);

        //Water Vapor Produced
        if (waterFraction < 1f)
            litersOfWaterProduced = (float) waterFraction
                    * calculateDailyCanopyTranspirationRate() / 24f
                    * myShelfImpl.getCropAreaUsed();
        else
            litersOfWaterProduced = calculateDailyCanopyTranspirationRate()
                    / 24f * myShelfImpl.getCropAreaUsed();
        totalWaterLitersTranspired += litersOfWaterProduced;
        //System.out.println("PlantImpl: totalWaterLitersTranspired:
        // "+totalWaterLitersTranspired);
        //consumedWaterBuffer.take(litersOfWaterProduced);
        //1/1000 liters per milliter, 1 gram per millilters, 8.016 grams per
        // mole
        float molesOfWaterProduced = waterLitersToMoles(litersOfWaterProduced);
        float molesOfWaterAdded = myShelfImpl.getBiomassRSImpl()
                .getAirOutputs()[0].addWaterMoles(molesOfWaterProduced);
        myShelfImpl.getBiomassRSImpl().addAirOutputActualFlowRates(0,
                molesOfWaterAdded);
        //System.out.println("PlantImpl: litersOfWaterProduced:
        // "+litersOfWaterProduced);
        //System.out.println("PlantImpl: molesOfWaterProduced:
        // "+molesOfWaterProduced);
        //System.out.println("PlantImpl: molesOfWaterAdded:
        // "+molesOfWaterAdded);
        //System.out.println("PlantImpl: consumedWaterBuffer level:
        // "+consumedWaterBuffer.getLevel());

    }

    private static float waterLitersToMoles(float pLiters) {
        return (pLiters * 998.23f) / 18.01524f; // 998.23g/liter, 18.01524g/mole
    }

    private static float waterMolesToLiters(float pMoles) {
        return (pMoles * 18.01524f) / 998.23f; // 998.23g/liter, 18.01524g/mole
    }

    //in g/meters^2*day
    private float calculateDailyCarbonGain() {
        float photoperiod = getPhotoperiod();
        float PPF = getAveragePPF();
        //System.out.println("PlantImpl: photoperiod: "+photoperiod);
        //System.out.println("PlantImpl: carbonUseEfficiency24:
        // "+carbonUseEfficiency24);
        //System.out.println("PlantImpl: myPPFFractionAbsorbed:
        // "+myPPFFractionAbsorbed);
        //System.out.println("PlantImpl: CQY: "+CQY);
        //System.out.println("PlantImpl: PPF: "+PPF);
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
        //System.out.println("PlantImpl: dailyCanopyTranspirationRate:
        // "+dailyCanopyTranspirationRate);
        //System.out.println("PlantImpl: wetIncoporatedWaterUptake:
        // "+wetIncoporatedWaterUptake);
        //System.out.println("PlantImpl: dryIncoporatedWaterUptake:
        // "+dryIncoporatedWaterUptake);
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
        //System.out.println("PlantImpl: myCurrentTotalWetBiomass:
        // "+myCurrentTotalWetBiomass);
        //System.out.println("PlantImpl: myCurrentEdibleWetBiomass:
        // "+myCurrentEdibleWetBiomass);
        //System.out.println("PlantImpl: myLastTotalWetBiomass:
        // "+myLastTotalWetBiomass);
        //System.out.println("PlantImpl: myLastEdibleWetBiomass:
        // "+myLastEdibleWetBiomass);
        //System.out.println("PlantImpl: myCurrentInedibleWetBiomass:
        // "+myCurrentInedibleWetBiomass);
        //System.out.println("PlantImpl: myLastInedibleWetBiomass:
        // "+myLastInedibleWetBiomass);
        //System.out.println("PlantImpl: densityOfWater: "+densityOfWater);
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
        SimEnvironment myEnvironment = myShelfImpl.getBiomassRSImpl()
                .getAirOutputs()[0];
        float CO2Moles = myEnvironment.getCO2Moles() * pow(10, 6); //in micro
        // moles
        float airMoles = myEnvironment.getTotalMoles(); //in moles
        if (CO2Moles <= 0)
            CO2Moles = pow(1f, -30f);
        else if (airMoles <= 0)
            airMoles = pow(1f, -30f);
        //System.out.println("PlantImpl: CO2Moles: "+CO2Moles);
        //System.out.println("PlantImpl: airMoles: "+airMoles);
        float currentCO2Concentration = (CO2Moles / airMoles);
        if (!canopyClosed)
            addToCanopyClosureList(currentCO2Concentration,
                    myCanopyClosureCO2Values);
        //System.out.println("PlantImpl: CO2_Concentration:
        // "+CO2_Concentration);
        myTotalCO2Concentration += currentCO2Concentration;
        myNumberOfCO2ConcentrationReadings++;
        myAverageCO2Concentration = myTotalCO2Concentration
                / myNumberOfCO2ConcentrationReadings;
        //System.out.println("PlantImpl: myAverageCO2Concentration:
        // "+myAverageCO2Concentration);
    }

    //returns the age in days
    protected float getDaysOfGrowth() {
        float daysOfGrowth = (float) myAge / 24f;
        //System.out.println("PlantImpl: daysOfGrowth: "+daysOfGrowth);
        //System.out.println("PlantImpl: myAge: "+myAge);
        return daysOfGrowth;
    }

    private float getAverageForList(List pList, float fillerValue) {
        //System.out.println("*********************************************");
        if (pList.size() < 2)
            return fillerValue;
        float totalReal = 0f;
        //System.out.print("list values = ["+fillerValue);
        for (Iterator iter = pList.iterator(); iter.hasNext();) {
            Float currentFloat = (Float) (iter.next());
            totalReal += currentFloat.floatValue();
            //System.out.print(currentFloat.floatValue()+", ");
        }
        //System.out.println("]");
        float totalFiller = fillerValue
                * ((myTimeTillCanopyClosure * 24f) - pList.size());

        //System.out.println("fillerValue = "+fillerValue);
        //System.out.println("totalFiller = "+totalFiller);
        //System.out.println("totalReal = "+totalReal);
        //System.out.println("pList.size() = "+pList.size());
        //System.out.println("myTimeTillCanopyClosure * 24f =
        // "+myTimeTillCanopyClosure * 24f);
        //System.out.println("myTimeTillCanopyClosure =
        // "+myTimeTillCanopyClosure);
        //System.out.println("getAverageForList returning "+((totalFiller +
        // totalReal) / (myTimeTillCanopyClosure * 24f)));

        return (totalFiller + totalReal) / (myTimeTillCanopyClosure * 24f);
    }

    private float calculateTimeTillCanopyClosure() {
        float thePPF = getAverageForList(myCanopyClosurePPFValues, myAveragePPF)
                * getPhotoperiod() / getNominalPhotoperiod();
        float oneOverPPf = 1f / thePPF;
        float thePPFsquared = pow(thePPF, 2f);
        float thePPFcubed = pow(thePPF, 3f);

        float theCO2 = getAverageForList(myCanopyClosureCO2Values,
                myAverageCO2Concentration);
        float oneOverCO2 = 1f / theCO2;
        float theCO2squared = pow(theCO2, 2f);
        float theCO2cubed = pow(theCO2, 3f);

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
            //System.out.println("PlantImpl: Time till canopy closure is
            // negative or NAN!");
        }
        //round the number according to Jim
        long tALong = Math.round(tA);
        tA = (float) tALong;
        //System.out.println("PlantImpl: tA: "+tA);
        return tA;
    }

    private void calculatePPFFractionAbsorbed() {
        float PPFFractionAbsorbedMax = 0.93f;
        //System.out.println("PlantImpl: PPFFractionAbsorbedMax:
        // "+PPFFractionAbsorbedMax);
        //System.out.println("PlantImpl: myTimeTillCanopyClosure:
        // "+myTimeTillCanopyClosure);
        //System.out.println("PlantImpl: getDaysOfGrowth():
        // "+getDaysOfGrowth());
        //System.out.println("PlantImpl: getN(): "+getN());
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
                * pow((getDaysOfGrowth() / myTimeTillCanopyClosure),
                        getN() - 1f) * (1f / myTimeTillCanopyClosure);
        //System.out.println("PlantImpl: daDt: "+daDt);
        return daDt;
    }

    private float calculateCQYMax() {
        float thePPF = getAveragePPF();
        float oneOverPPf = 1f / thePPF;
        float thePPFsquared = pow(thePPF, 2f);
        float thePPFcubed = pow(thePPF, 3f);
        //System.out.println("PlantImpl: thePPF: "+thePPF);
        //System.out.println("PlantImpl: oneOverPPf: "+oneOverPPf);
        //System.out.println("PlantImpl: thePPFsquared: "+thePPFsquared);
        //System.out.println("PlantImpl: thePPFcubed: "+thePPFcubed);

        float theCO2 = getAverageCO2Concentration();
        float oneOverCO2 = 1f / theCO2;
        float theCO2squared = pow(theCO2, 2f);
        float theCO2cubed = pow(theCO2, 3f);
        //System.out.println("PlantImpl: theCO2: "+theCO2);
        //System.out.println("PlantImpl: oneOverCO2: "+oneOverCO2);
        //System.out.println("PlantImpl: theCO2squared: "+theCO2squared);
        //System.out.println("PlantImpl: theCO2cubed: "+theCO2cubed);

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
            //System.out.println("PlantImpl: CQYMax is negative or NaN!");
        }
        //System.out.println("PlantImpl: theCQYMax: "+theCQYMax);
        return theCQYMax;
    }

    private float calculateCQY() {
        float CQYMax = calculateCQYMax();
        float timeTillCanopySenescence = getTimeAtCanopySenescence();
        //System.out.println("PlantImpl: CQYMax: "+CQYMax);
        //System.out.println("PlantImpl: timeTillCanopySenescence:
        // "+timeTillCanopySenescence);
        if (getDaysOfGrowth() < getTimeAtCanopySenescence()) {
            return CQYMax;
        } else {
            float CQYMin = getCQYMin();
            float daysOfGrowth = getDaysOfGrowth();
            float timeTillCropMaturity = getTimeAtCropMaturity();
            float calculatedCQY = CQYMax
                    - ((CQYMax - CQYMin)
                            * (daysOfGrowth - timeTillCanopySenescence) / (timeTillCropMaturity - timeTillCanopySenescence));
            //System.out.println("PlantImpl: CQYMin: "+CQYMin);
            //System.out.println("PlantImpl: daysOfGrowth: "+daysOfGrowth);
            //System.out.println("PlantImpl: timeTillCropMaturity:
            // "+timeTillCropMaturity);
            if (calculatedCQY < 0f)
                return 0f;
            else
                return calculatedCQY;
        }
    }

    private float getAveragePPF() {
        return myAveragePPF;
    }

    protected float pow(float a, float b) {
        return (new Double(Math.pow(a, b))).floatValue();
    }

    protected float exp(float a) {
        return (new Double(Math.exp(a))).floatValue();
    }

    private float abs(float a) {
        return (new Double(Math.abs(a))).floatValue();
    }

    private float sigmoidLikeProbability(float x) {
        if (x >= 1f)
            return 1f;
        else if ((x < 1f) && (x > 0f))
            return 0.3f * x * (1f - abs(x - 2f) / 2f);
        else
            return 0f;
    }

    public void log() {
    }

}

