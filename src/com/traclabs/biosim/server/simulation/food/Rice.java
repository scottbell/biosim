package com.traclabs.biosim.server.simulation.food;

/**
 * Rice
 *
 * @author Scott Bell
 */

public class Rice extends Erectophile {
    private static final int taInitialValue = 1200;

    private static final float initialPPFValue = 763.89f;

    private static final float initialCO2Value = 1200f;

    public Rice(Shelf pShelf) {
        super(pShelf);
        canopyClosureConstants[0] = 6591400f;
        canopyClosureConstants[1] = 25776f;
        canopyClosureConstants[3] = .0064532f;
        canopyClosureConstants[5] = -3748f;
        canopyClosureConstants[7] = -0.043378f;
        canopyClosureConstants[12] = .00004562f;
        canopyClosureConstants[16] = .0000045207f;
        canopyClosureConstants[17] = -.000000014936f;

        canopyQYConstants[6] = 0.036186f;
        canopyQYConstants[7] = 0.000061457f;
        canopyQYConstants[8] = -0.000000024322f;
        canopyQYConstants[12] = -0.0000000091477f;
        canopyQYConstants[13] = 0.000000000003889f;
        canopyQYConstants[16] = -0.0000000026712f;
    }

    public static float getFractionOfEdibleBiomass() {
        return 0.3f;
    }

    /**
     * Returns calories per kilogram
     */
    public static float getCaloriesPerKilogram() {
        return 3630f;
    }

    public static float getEdibleFreshBasisWaterContent() {
        return 0.12f;
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
        return PlantType.WHEAT;
    }

    public String getPlantTypeString() {
        return "Rice";
    }

    protected float getConstantPPF() {
        return initialPPFValue;
    }

    protected float getCarbonUseEfficiency24() {
        return 0.64f;
    }

    protected float getBCF() {
        //return 0.44f;
        return 0.45f;
    }

    protected float getPhotoperiod() {
        return 12f;
    }

    protected float getNominalPhotoperiod() {
        return 12f;
    }

    protected float getTimeAtOrganFormation() {
        return 57f;
    }

    protected float getN() {
        return 1.5f;
    }

    protected float getCQYMin() {
        return 0.01f;
    }

    protected float getTimeAtCanopySenescence() {
        return 61f;
    }

    protected float getTimeAtCropMaturity() {
        //return 88f;
        return 85f;
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