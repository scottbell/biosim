package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.simulation.food.PlantType;

/**
 * Peanut
 * 
 * @author Scott Bell
 */

public class Peanut extends Legume {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 625f;

    private static final float initialCO2Value = 1200f;

    public Peanut(Shelf pShelf) {
        super(pShelf);
        canopyClosureConstants[0] = 3748700f;
        canopyClosureConstants[1] = 2920f;
        canopyClosureConstants[4] = .000000094008f;
        canopyClosureConstants[5] = -18840f;
        canopyClosureConstants[6] = 23.912f;
        canopyClosureConstants[10] = 51.256f;
        canopyClosureConstants[15] = -0.05963f;
        canopyClosureConstants[16] = .0000055180f;
        canopyClosureConstants[20] = .000025969f;

        canopyQYConstants[6] = 0.041513f;
        canopyQYConstants[7] = 0.000051157f;
        canopyQYConstants[8] = -0.000000020992f;
        canopyQYConstants[12] = 0.000000040864f;
        canopyQYConstants[16] = -0.000000021582f;
        canopyQYConstants[17] = -0.00000000010468f;
        canopyQYConstants[22] = 0.000000000000048541f;
        canopyQYConstants[24] = 0.0000000000000000000039259f;
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
        return PlantType.PEANUT;
    }

    public String getPlantTypeString() {
        return "Peanut";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getBCF() {
        //return 0.50f;
        return 0.60f;
    }

    protected float getTimeAtOrganFormation() {
        return 49f;
    }

    protected float getCUEMax() {
        return 0.65f;
    }

    protected float getCUEMin() {
        return 0.30f;
    }

    protected float getPhotoperiod() {
        return 12f;
    }

    protected float getNominalPhotoperiod() {
        return 12f;
    }

    protected float getN() {
        return 2.0f;
    }

    protected float getCQYMin() {
        return 0.02f;
    }

    protected float getTimeAtCanopySenescence() {
        return 65f;
    }

    protected float getTimeAtCropMaturity() {
        //return 110;
        return 104;
    }

    protected float getOPF() {
        return 1.19f;
    }

    protected float getFreshBasisWaterContent() {
        return 7.7614f;
    }

    public static float getFractionOfEdibleBiomass() {
        return 0.25f;
    }

    protected float getProtectedFractionOfEdibleBiomass() {
        return getFractionOfEdibleBiomass();
    }

    /**
     * Returns calories per kilogram
     */
    public static float getCaloriesPerKilogram() {
        return 5680f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.056f;
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