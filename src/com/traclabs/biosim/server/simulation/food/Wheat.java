package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.simulation.food.PlantType;

/**
 * Wheat
 * 
 * @author Scott Bell
 */

public class Wheat extends Erectophile {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 1597.22f;

    //private static final float initialPPFValue = 1402.778f;
    private static final float initialCO2Value = 1200f;

    public Wheat(Shelf pShelf) {
        super(pShelf);
        canopyClosureConstants[0] = 95488f;
        canopyClosureConstants[1] = 1068.6f;
        canopyClosureConstants[6] = 15.977f;
        canopyClosureConstants[10] = 0.3419f;
        canopyClosureConstants[11] = 0.00019733f;
        canopyClosureConstants[15] = -0.00019076f;

        canopyQYConstants[6] = 0.044793f;
        canopyQYConstants[7] = 0.000051583f;
        canopyQYConstants[8] = -0.000000020724f;
        canopyQYConstants[11] = -0.0000051946f;
        canopyQYConstants[17] = -0.0000000000049303f;
        canopyQYConstants[18] = 0.0000000000000022255f;
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
        return PlantType.WHEAT;
    }

    public String getPlantTypeString() {
        return "Wheat";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getCarbonUseEfficiency24() {
        return 0.64f;
    }

    protected float getBCF() {
        //return 0.44f;
        return 0.42f;
    }

    protected float getPhotoperiod() {
        return 20f;
        //return 22f;
    }

    protected float getNominalPhotoperiod() {
        return 20f;
    }

    protected float getTimeAtOrganFormation() {
        return 34f;
    }

    protected float getN() {
        return 1.0f;
    }

    protected float getCQYMin() {
        return 0.01f;
    }

    protected float getTimeAtCanopySenescence() {
        return 33f;
    }

    protected float getTimeAtCropMaturity() {
        return 62f;
        //return 79f;
    }

    protected float getOPF() {
        return 1.07f;
    }

    public static float getFractionOfEdibleBiomass() {
        return 0.4f;
    }

    protected float getProtectedFractionOfEdibleBiomass() {
        return getFractionOfEdibleBiomass();
    }

    /**
     * Returns calories per kilogram
     */
    public static float getCaloriesPerKilogram() {
        return 3300f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.12f;
    }

    public static float getInedibleFreshBasisWaterContent() {
        return 0.9f;
    }

    protected float getProtectedEdibleFreshBasisWaterContent() {
        return getEdibleFreshBasisWaterContent();
    }

    protected float getProtectedInedibleFreshBasisWaterContent() {
        return getInedibleFreshBasisWaterContent();
    }
}