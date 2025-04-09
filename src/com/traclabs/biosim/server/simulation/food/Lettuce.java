package com.traclabs.biosim.server.simulation.food;

/**
 * Lettuce
 *
 * @author Scott Bell
 */

public class Lettuce extends Planophile {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 295.14f;

    private static final float initialCO2Value = 1200f;

    public Lettuce(Shelf pShelf) {
        super(pShelf);
        canopyClosureConstants[1] = 10289f;
        canopyClosureConstants[2] = -3.7018f;
        canopyClosureConstants[4] = .00000036648f;
        canopyClosureConstants[6] = 1.7571f;
        canopyClosureConstants[8] = .0000023127f;
        canopyClosureConstants[10] = 1.876f;

        canopyQYConstants[6] = 0.044763f;
        canopyQYConstants[7] = 0.00005163f;
        canopyQYConstants[8] = -0.00000002075f;
        canopyQYConstants[11] = -0.000011701f;
        canopyQYConstants[17] = -0.000000000019731f;
        canopyQYConstants[18] = 0.0000000000000089265f;
    }

    public static float getFractionOfEdibleBiomass() {
        return 0.9f;
    }

    /**
     * Returns calories per kilogram
     */
    public static float getCaloriesPerKilogram() {
        return 180f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.95f;
    }

    public static float getInedibleFreshBasisWaterContent() {
        return 0.90f;
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
        return PlantType.LETTUCE;
    }

    public String getPlantTypeString() {
        return "Lettuce";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getCarbonUseEfficiency24() {
        return 0.625f;
    }

    protected float getBCF() {
        return 0.40f;
    }

    protected float getTimeAtOrganFormation() {
        return 1f;
    }

    protected float getCUEMax() {
        return 0.625f;
    }

    protected float getPhotoperiod() {
        return 16f;
    }

    protected float getNominalPhotoperiod() {
        return 16f;
    }

    protected float getN() {
        return 2.5f;
    }

    protected float getCQYMin() {
        return 0f;
    }

    protected float getTimeAtCanopySenescence() {
        return 31f;
    }

    protected float getTimeAtCropMaturity() {
        //return 30f;
        return 28f;
    }

    protected float getOPF() {
        return 1.08f;
    }

    protected float getProtectedFractionOfEdibleBiomass() {
        return getFractionOfEdibleBiomass();
    }

    protected float getProtectedEdibleFreshBasisWaterContent() {
        return getEdibleFreshBasisWaterContent();
    }

    protected float getProtectedInedibleFreshBasisWaterContent() {
        return getInedibleFreshBasisWaterContent();
    }
}