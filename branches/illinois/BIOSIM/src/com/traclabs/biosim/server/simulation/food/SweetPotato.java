package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.PlantType;

/**
 * SweetPotato
 * 
 * @author Scott Bell
 */

public class SweetPotato extends Planophile {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 432.1f;

    private static final float initialCO2Value = 1200f;

    public SweetPotato(ShelfImpl pShelfImpl) {
        super(pShelfImpl);
        canopyClosureConstants[0] = 1207000f;
        canopyClosureConstants[1] = 4948.4f;
        canopyClosureConstants[6] = 4.2978f;
        canopyClosureConstants[20] = .00000040109f;
        canopyClosureConstants[22] = .0000000000020193f;

        canopyQYConstants[6] = 0.039317f;
        canopyQYConstants[7] = 0.000056741f;
        canopyQYConstants[8] = -0.000000021797f;
        canopyQYConstants[11] = -0.000013836f;
        canopyQYConstants[12] = -0.0000000063397f;
        canopyQYConstants[17] = -0.000000000013464f;
        canopyQYConstants[18] = 0.0000000000000077362f;
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
        return PlantType.SWEET_POTATO;
    }

    public String getPlantTypeString() {
        return "Sweet Potato";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getCarbonUseEfficiency24() {
        return 0.625f;
    }

    protected float getBCF() {
        //return 0.44f;
        return 0.41f;
    }

    protected float getTimeAtOrganFormation() {
        return 33f;
    }

    protected float getCUEMax() {
        return 0.625f;
    }

    protected float getCUEMin() {
        return 0f;
    }

    protected float getPhotoperiod() {
        //return 18f;
        return 12f;
    }

    protected float getNominalPhotoperiod() {
        return 18f;
    }

    protected float getN() {
        return 1.5f;
    }

    protected float getCQYMin() {
        return 0f;
    }

    protected float getTimeAtCanopySenescence() {
        return 121f;
    }

    protected float getTimeAtCropMaturity() {
        //return 120f;
        return 85f;
    }

    protected float getOPF() {
        return 1.02f;
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
        return 1140f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.71f;
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