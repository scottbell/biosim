package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.simulation.food.PlantType;

/**
 * DryBean
 * 
 * @author Scott Bell
 */

public class DryBean extends Legume {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 555.55f;

    private static final float initialCO2Value = 1200f;

    public DryBean(Shelf pShelf) {
        super(pShelf);
        canopyClosureConstants[0] = 290410f;
        canopyClosureConstants[1] = 1559.4f;
        canopyClosureConstants[6] = 15.840f;
        canopyClosureConstants[11] = .0061120f;
        canopyClosureConstants[17] = -0.0000000037409f;
        canopyClosureConstants[24] = .00000000000000000096484f;

        canopyQYConstants[6] = 0.04191f;
        canopyQYConstants[7] = 0.000053852f;
        canopyQYConstants[8] = -0.000000021275f;
        canopyQYConstants[11] = -0.00001238f;
        canopyQYConstants[17] = -0.00000000001544f;
        canopyQYConstants[18] = 0.000000000000006469f;
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
        return PlantType.DRY_BEAN;
    }

    public String getPlantTypeString() {
        return "Dry Bean";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getBCF() {
        //return 0.45f;
        return 0.46f;
    }

    protected float getCUEMax() {
        return 0.65f;
    }

    protected float getCUEMin() {
        return 0.50f;
    }

    protected float getPhotoperiod() {
        //return 12f;
        return 18f;
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
        return 42f;
    }

    protected float getTimeAtOrganFormation() {
        return 40f;
    }

    protected float getTimeAtCropMaturity() {
        //return 63f;
        return 85f;
    }

    protected float getOPF() {
        return 1.1f;
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
        return 3490f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.10f;
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