package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.idl.simulation.food.PlantType;

/**
 * Legume
 * 
 * @author Scott Bell
 */

public abstract class Legume extends Planophile {
    protected abstract float getBCF();

    protected abstract float getPhotoperiod();

    protected abstract float getNominalPhotoperiod();

    protected abstract float getN();

    protected abstract float getTimeAtCanopySenescence();

    protected abstract float getCQYMin();

    protected abstract float getTimeAtCropMaturity();

    protected abstract float getOPF();

    protected abstract float getProtectedFractionOfEdibleBiomass();

    protected abstract float getProtectedEdibleFreshBasisWaterContent();

    protected abstract float getProtectedInedibleFreshBasisWaterContent();

    protected abstract float getCUEMax();

    protected abstract float getCUEMin();

    protected abstract float getConstantPPF();

    public abstract PlantType getPlantType();

    public abstract String getPlantTypeString();

    protected abstract float getInitialPPFValue();

    protected abstract float getInitialCO2Value();

    protected abstract int getTAInitialValue();

    public Legume(ShelfImpl pShelfImpl) {
        super(pShelfImpl);
    }

    protected float getCarbonUseEfficiency24() {
        float CUEMax = getCUEMax();
        float timeTillCanopySenescence = getTimeAtCanopySenescence();
        myLogger.debug("Legume: CUEMax: " + CUEMax);
        myLogger.debug("Legume: timeTillCanopySenescence: "
                + timeTillCanopySenescence);
        if (getDaysOfGrowth() < getTimeAtCanopySenescence()) {
            return CUEMax;
        }
		float CUEMin = getCUEMin();
		float daysOfGrowth = getDaysOfGrowth();
		float timeTillCropMaturity = getTimeAtCropMaturity();
		float calculatedCUE24 = CUEMax
		        - ((CUEMax - CUEMin)
		                * ((daysOfGrowth - timeTillCanopySenescence)) / (timeTillCropMaturity - timeTillCanopySenescence));
		myLogger.debug("Legume: CUEMin: " + CUEMin);
		myLogger.debug("Legume: daysOfGrowth: " + daysOfGrowth);
		myLogger.debug("Legume: timeTillCropMaturity: "
		        + timeTillCropMaturity);
		myLogger.debug("Legume: calculatedCUE24: " + calculatedCUE24);
		if (calculatedCUE24 < 0f)
		    return 0f;
		return calculatedCUE24;
    }

}