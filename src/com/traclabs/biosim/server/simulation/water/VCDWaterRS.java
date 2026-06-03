package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;

/**
 * A Vapor Compression Distillation (VCD) water recovery system, modeled after the
 * International Space Station Urine Processor Assembly (UPA). Unlike {@link WaterRSLinear},
 * which recovers potable water in direct proportion to power with no losses, the VCD system
 * processes an energy-limited amount of waste water each tick (dirty/urine water first, then
 * grey water) and recovers only a configurable fraction of it as potable water. The
 * remainder is rejected as brine that leaves the recovery loop.
 *
 * <p>Selected via {@code <WaterRS implementation="VCD" .../>}. The recovery ratio and the
 * processing energy per liter are configurable; defaults reflect a realistic single-pass
 * urine recovery (~85%) and continuity with BioSim's existing linear water model.
 */
public class VCDWaterRS extends AbstractWaterRS {

    /** Default fraction of processed waste water recovered as potable water (ISS UPA ~85%). */
    public static final float DEFAULT_RECOVERY_RATIO = 0.85f;

    /**
     * Default power (in watts) required to process one liter of waste water per tick. Chosen
     * to match BioSim's existing linear water relationship (1540 W -> 4.26 L/tick), i.e.
     * 1540 / 4.26 ~= 361.5 W*tick per liter. Configurable per scenario.
     */
    public static final float DEFAULT_PROCESSING_ENERGY_PER_LITER = 361.5f;

    private float recoveryRatio = DEFAULT_RECOVERY_RATIO;
    private float processingEnergyPerLiter = DEFAULT_PROCESSING_ENERGY_PER_LITER;

    private float currentPowerConsumed = 0f;
    private float currentDirtyWaterConsumed = 0f;
    private float currentGreyWaterConsumed = 0f;
    private float currentWaterProcessed = 0f;
    private float currentPotableWaterProduced = 0f;
    private float currentBrineProduced = 0f;

    public VCDWaterRS(int pID, String pName) {
        super(pID, pName);
    }

    @Override
    public void reset() {
        super.reset();
        currentPowerConsumed = 0f;
        currentDirtyWaterConsumed = 0f;
        currentGreyWaterConsumed = 0f;
        currentWaterProcessed = 0f;
        currentPotableWaterProduced = 0f;
        currentBrineProduced = 0f;
    }

    @Override
    public void tick() {
        super.tick();
        gatherPower();
        processWasteWater();
        pushPotableWater();
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinition.getMostResourceFromStores();
    }

    private void processWasteWater() {
        float throughputRate = getMalfunctionThroughputRate();
        float maxProcessable = 0f;
        if (processingEnergyPerLiter > 0f && throughputRate > 0f) {
            maxProcessable = (currentPowerConsumed / processingEnergyPerLiter)
                    * getTickLength() * throughputRate;
        }

        currentDirtyWaterConsumed = myDirtyWaterConsumerDefinition
                .getResourceFromStores(maxProcessable);
        currentGreyWaterConsumed = myGreyWaterConsumerDefinition
                .getResourceFromStores(maxProcessable - currentDirtyWaterConsumed);
        currentWaterProcessed = currentDirtyWaterConsumed + currentGreyWaterConsumed;

        currentPotableWaterProduced = recoveryRatio * currentWaterProcessed;
        currentBrineProduced = currentWaterProcessed - currentPotableWaterProduced;
    }

    private void pushPotableWater() {
        myPotableWaterProducerDefinition.pushResourceToStores(currentPotableWaterProduced);
    }

    /**
     * Returns the throughput multiplier implied by any active malfunction. The most severe
     * active malfunction wins. Applied once, in {@link #tick()}, so
     * {@link #performMalfunctions()} only marks malfunctions performed.
     */
    protected float getMalfunctionThroughputRate() {
        if (!isMalfunctioning())
            return 1.0f;
        float rate = 1.0f;
        for (Malfunction malfunction : myMalfunctions.values()) {
            MalfunctionIntensity intensity = malfunction.getIntensity();
            if (intensity == MalfunctionIntensity.SEVERE_MALF)
                rate = Math.min(rate, 0.0f);
            else if (intensity == MalfunctionIntensity.MEDIUM_MALF)
                rate = Math.min(rate, 0.5f);
            else if (intensity == MalfunctionIntensity.LOW_MALF)
                rate = Math.min(rate, 0.75f);
        }
        return rate;
    }

    @Override
    protected void performMalfunctions() {
        for (Malfunction malfunction : myMalfunctions.values()) {
            malfunction.setPerformed(true);
        }
    }

    @Override
    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        StringBuilder returnBuffer = new StringBuilder("VCD water recovery ");
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("offline");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("at reduced (50%) throughput");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("at reduced (75%) throughput");
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append(" (repairable)");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append(" (permanent)");
        return returnBuffer.toString();
    }

    @Override
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
        myLogger.debug("dirty_water_consumed=" + currentDirtyWaterConsumed);
        myLogger.debug("grey_water_consumed=" + currentGreyWaterConsumed);
        myLogger.debug("water_processed=" + currentWaterProcessed);
        myLogger.debug("potable_water_produced=" + currentPotableWaterProduced);
        myLogger.debug("brine_produced=" + currentBrineProduced);
    }

    public float getRecoveryRatio() {
        return recoveryRatio;
    }

    /**
     * Sets the recovery ratio, constrained to {@code (0, 1]}. Non-positive values are
     * rejected (kept unchanged); values greater than 1 are clamped to 1 so potable output
     * can never exceed processed waste water.
     */
    public void setRecoveryRatio(float pRecoveryRatio) {
        if (pRecoveryRatio <= 0f) {
            myLogger.warn("Ignoring non-positive recoveryRatio " + pRecoveryRatio
                    + " for " + getModuleName());
            return;
        }
        recoveryRatio = Math.min(pRecoveryRatio, 1.0f);
    }

    public float getProcessingEnergyPerLiter() {
        return processingEnergyPerLiter;
    }

    public void setProcessingEnergyPerLiter(float pProcessingEnergyPerLiter) {
        if (pProcessingEnergyPerLiter <= 0f) {
            myLogger.warn("Ignoring non-positive processingEnergyPerLiter "
                    + pProcessingEnergyPerLiter + " for " + getModuleName());
            return;
        }
        processingEnergyPerLiter = pProcessingEnergyPerLiter;
    }

    public float getPowerConsumed() {
        return currentPowerConsumed;
    }

    public float getDirtyWaterConsumed() {
        return currentDirtyWaterConsumed;
    }

    public float getGreyWaterConsumed() {
        return currentGreyWaterConsumed;
    }

    public float getWaterProcessed() {
        return currentWaterProcessed;
    }

    public float getPotableWaterProduced() {
        return currentPotableWaterProduced;
    }

    public float getBrineProduced() {
        return currentBrineProduced;
    }
}
