package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.simulation.food.PlantType;

/**
 * Planophile
 * 
 * @author Scott Bell
 */

public abstract class Planophile extends Plant {
    protected abstract float getBCF();

    protected abstract float getCarbonUseEfficiency24();

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

    protected abstract float getConstantPPF();

    public abstract PlantType getPlantType();

    public abstract String getPlantTypeString();

    protected abstract float getInitialPPFValue();

    protected abstract float getInitialCO2Value();

    protected abstract int getTAInitialValue();

    public Planophile(Shelf pShelf) {
        super(pShelf);
    }

    protected float calculateCanopyStomatalConductance() {
        float temperature = myShelf.getBiomassPS()
                .getAirProducerDefinition().getEnvironments()[0]
                .getTemperature();
        float vaporPressureDeficit = calculateVaporPressureDeficit();
        float netCanopyPhotosynthesis = calculateNetCanopyPhotosynthesis();
        myLogger.debug("Planophile: temperature: " + temperature);
        myLogger.debug("Planophile: vaporPressureDeficit:"
                + vaporPressureDeficit);
        myLogger.debug("Planophile: CO2Concentration:"
                + getAverageCO2Concentration());
        myLogger.debug("Planophile: netCanopyPhotosynthesis:"
                + netCanopyPhotosynthesis);
        float canopyStomatalConductance = (1.717f * temperature - 19.96f - 10.54f * vaporPressureDeficit)
                * (netCanopyPhotosynthesis / getAverageCO2Concentration());
        if (canopyStomatalConductance <= 0)
            return Float.MIN_VALUE;
		return canopyStomatalConductance;
    }

    protected float calculateAtmosphericAeroDynamicConductance() {
        return 2.5f;
    }
}