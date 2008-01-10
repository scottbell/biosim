package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.PlantType;

/**
 * Tomato
 * 
 * @author Scott Bell
 */

public class Tomato extends Planophile {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 625f;

    private static final float initialCO2Value = 1200f;

    public Tomato(ShelfImpl pShelfImpl) {
        super(pShelfImpl);
        canopyClosureConstants[0] = 627740f;
        canopyClosureConstants[1] = 3172.4f;
        canopyClosureConstants[6] = 24.281f;
        canopyClosureConstants[10] = 0.44686f;
        canopyClosureConstants[11] = 0.0056276f;
        canopyClosureConstants[16] = -0.0000030690f;

        canopyQYConstants[6] = 0.040061f;
        canopyQYConstants[7] = 0.00005688f;
        canopyQYConstants[8] = -0.000000022598f;
        canopyQYConstants[12] = -0.00000001182f;
        canopyQYConstants[13] = 0.00000000000550264f;
        canopyQYConstants[16] = -0.0000000071241f;
    }

    protected float getInitialPPFValue() {
        return initialPPFValue;
    }

    protected float getInitialCO2Value() {
        return initialCO2Value;
    }

    protected int getTAInitialValue() {
        return taInitialValue;
    }

    public PlantType getPlantType() {
        return PlantType.TOMATO;
    }

    public String getPlantTypeString() {
        return "Tomato";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getCarbonUseEfficiency24() {
        return 0.65f;
    }

    protected float getBCF() {
        //return 0.42f;
        return 0.43f;
    }

    protected float getCUEMax() {
        return 0.65f;
    }

    protected float getTimeAtOrganFormation() {
        return 41f;
    }

    protected float getCUEMin() {
        return 0f;
    }

    protected float getPhotoperiod() {
        return 12f;
    }

    protected float getNominalPhotoperiod() {
        return 12f;
    }

    protected float getN() {
        return 2.5f;
    }

    protected float getCQYMin() {
        return 0.01f;
    }

    protected float getTimeAtCanopySenescence() {
        return 56f;
    }

    protected float getTimeAtCropMaturity() {
        //return 80f;
        return 85f;
    }

    protected float getOPF() {
        return 1.09f;
    }

    public static float getFractionOfEdibleBiomass() {
        return 0.45f;
    }

    protected float getProtectedFractionOfEdibleBiomass() {
        return getFractionOfEdibleBiomass();
    }

    /**
     * Returns calories per kilogram
     */
    public static float getCaloriesPerKilogram() {
        return 220f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.94f;
    }

    public static float getInedibleFreshBasisWaterContent() {
        return 0.90f;
    }

    protected float getProtectedEdibleFreshBasisWaterContent() {
        return getEdibleFreshBasisWaterContent();
    }

    protected float getProtectedInedibleFreshBasisWaterContent() {
        return getInedibleFreshBasisWaterContent();
    }
}