package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.PlantType;

/**
 * WhitePotato
 * 
 * @author Scott Bell
 */

public class WhitePotato extends Planophile {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 648.15f;

    private static final float initialCO2Value = 1200f;

    public WhitePotato(ShelfImpl pShelfImpl) {
        super(pShelfImpl);
        canopyClosureConstants[0] = 657730f;
        canopyClosureConstants[1] = 8562.6f;
        canopyClosureConstants[11] = 0.042749f;
        canopyClosureConstants[12] = 0.00000088437f;
        canopyClosureConstants[16] = -0.000017905f;

        canopyQYConstants[6] = 0.046929f;
        canopyQYConstants[7] = 0.000050910f;
        canopyQYConstants[8] = -0.000000021878f;
        canopyQYConstants[14] = 0.0000000000000043976f;
        canopyQYConstants[17] = -0.000000000015272f;
        canopyQYConstants[21] = -0.000000000019602f;
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
        return PlantType.WHITE_POTATO;
    }

    public String getPlantTypeString() {
        return "White Potato";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getCarbonUseEfficiency24() {
        return 0.625f;
    }

    protected float getBCF() {
        return 0.41f;
    }

    protected float getCUEMax() {
        return 0.625f;
    }

    protected float getTimeAtOrganFormation() {
        return 45f;
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
        return 2.0f;
    }

    protected float getCQYMin() {
        return 0.02f;
    }

    protected float getTimeAtCanopySenescence() {
        return 75f;
    }

    protected float getTimeAtCropMaturity() {
        //return 138f;
        return 132f;
    }

    protected float getOPF() {
        return 1.02f;
    }

    public static float getFractionOfEdibleBiomass() {
        return 0.3f;
    }

    protected float getProtectedFractionOfEdibleBiomass() {
        return getFractionOfEdibleBiomass();
    }

    /**
     * Returns calories per kilogram
     */
    public static float getCaloriesPerKilogram() {
        return 760f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.80f;
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