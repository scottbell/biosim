package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.water.aws.*;

import java.util.Arrays;
import java.util.Iterator;

/**
 * The Water Recovery System takes grey/dirty water and refines it to potable
 * water for the crew members and grey water for the crops.. Class modeled after
 * the paper:. "Intelligent Control of a Water Recovery System: Three Years in
 * the Trenches" by Bonasso, Kortenkamp, and Thronesbery
 *
 * @author Scott Bell
 */

public class WaterRS extends SimBioModule {
    private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 4;
    //Consumers, Producers
    private final PowerConsumerDefinition myPowerConsumerDefinition;
    private final GreyWaterConsumerDefinition myGreyWaterConsumerDefinition;
    private final DirtyWaterConsumerDefinition myDirtyWaterConsumerDefinition;
    private final PotableWaterProducerDefinition myPotableWaterProducerDefinition;
    //The various subsystems of Water RS that clean the water
    private final BWP myBWP;
    private final RO myRO;
    private final AES myAES;
    private final PPS myPPS;
    private final WaterRSSubSystem[] mySubsystems;
    private WaterRSOperationMode myMode;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRS(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myGreyWaterConsumerDefinition = new GreyWaterConsumerDefinition(this);
        myDirtyWaterConsumerDefinition = new DirtyWaterConsumerDefinition(this);
        myPotableWaterProducerDefinition = new PotableWaterProducerDefinition(this);

        myBWP = new BWP(this);
        myRO = new RO(this);
        myAES = new AES(this);
        myPPS = new PPS(this);
        mySubsystems = new WaterRSSubSystem[4];
        mySubsystems[0] = myBWP;
        mySubsystems[1] = myRO;
        mySubsystems[2] = myAES;
        mySubsystems[3] = myPPS;
    }

    /**
     * Resets production/consumption levels and resets all the subsystems
     */
    public void reset() {
        super.reset();
        for (int i = 0; i < mySubsystems.length; i++)
            mySubsystems[i].reset();
        myPowerConsumerDefinition.reset();
        myGreyWaterConsumerDefinition.reset();
        myDirtyWaterConsumerDefinition.reset();
        myPotableWaterProducerDefinition.reset();
    }

    /**
     * Returns the RO subsystem
     *
     * @return the RO subsystem
     */
    public RO getRO() {
        return myRO;
    }

    /**
     * Returns the AES subsystem
     *
     * @return the AES subsystem
     */
    public AES getAES() {
        return myAES;
    }

    /**
     * Returns the PPS subsystem
     *
     * @return the PPS subsystem
     */
    public PPS getPPS() {
        return myPPS;
    }

    /**
     * Returns the BWP subsystem
     *
     * @return the BWP subsystem
     */
    public BWP getBWP() {
        return myBWP;
    }

    /**
     * Returns the potable water produced (in liters) by the Water RS during the
     * current tick
     *
     * @return the potable water produced (in liters) by the Water RS during the
     * current tick
     */
    public float getPotableWaterProduced() {
        return myPPS.getPotableWaterProduced();
    }

    /**
     * Returns the grey water produced (in liters) by the Water RS during the
     * current tick
     *
     * @return the grey water produced (in liters) by the Water RS during the
     * current tick
     */
    public float getGreyWaterProduced() {
        return 0f;
    }

    /**
     * Returns the grey water consumed (in liters) by the Water RS during the
     * current tick
     *
     * @return the grey water consumed (in liters) by the Water RS during the
     * current tick
     */
    public float getGreyWaterConsumed() {
        return myBWP.getGreyWaterConsumed();
    }

    /**
     * Returns the power consumed (in watts) by the Water RS during the current
     * tick
     *
     * @return the power consumed (in watts) by the Water RS during the current
     * tick
     */
    public float getPowerConsumed() {
        float powerConsumed = 0f;
        for (int i = 0; i < mySubsystems.length; i++)
            powerConsumed += mySubsystems[i].getPowerConsumed();
        return powerConsumed;
    }

    /**
     * Returns the dirty water consumed (in liters) by the Water RS during the
     * current tick
     *
     * @return the dirty water consumed (in liters) by the Water RS during the
     * current tick
     */
    public float getDirtyWaterConsumed() {
        return myBWP.getDirtyWaterConsumed();
    }

    /**
     * When ticked, the Water RS: 1) ticks each subsystem.
     */
    public void tick() {
        super.tick();
        Arrays.fill(getPowerConsumerDefinition().getActualFlowRates(), 0f);
        enableSubsystemsBasedOnPower();
        //tick each system
        for (int i = 0; i < mySubsystems.length; i++)
            mySubsystems[i].tick();
        myAES.setMalfunctioning(false);
        myRO.setMalfunctioning(false);
    }

    /**
     * @param sumOfDesiredFlowRates
     * @param powerNeeded
     */
    private void enableSubsystemsBasedOnPower() {
        float sumOfDesiredFlowRates = 0f;
        for (int i = 0; i < getPowerConsumerDefinition().getDesiredFlowRates().length; i++)
            sumOfDesiredFlowRates += getPowerConsumerDefinition()
                    .getDesiredFlowRate(i);

        float totalPowerNeeded = 0;
        for (int i = 0; i < mySubsystems.length; i++)
            totalPowerNeeded += mySubsystems[i].getBasePowerNeeded();

        if (sumOfDesiredFlowRates >= totalPowerNeeded)
            setOperationMode(WaterRSOperationMode.FULL);
        else if (sumOfDesiredFlowRates >= (totalPowerNeeded - myAES
                .getBasePowerNeeded()))
            setOperationMode(WaterRSOperationMode.PARTIAL);
        else if (sumOfDesiredFlowRates >= (totalPowerNeeded
                - myAES.getBasePowerNeeded() - myPPS.getBasePowerNeeded()))
            setOperationMode(WaterRSOperationMode.GREY_WATER_ONLY);
        else
            setOperationMode(WaterRSOperationMode.OFF);
    }

    protected void performMalfunctions() {
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF) {
                    myAES.setMalfunctioning(true);
                    myRO.setMalfunctioning(true);
                } else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    myRO.setMalfunctioning(true);
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    myAES.setMalfunctioning(true);
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF) {
                    myAES.setMalfunctioning(true);
                    myRO.setMalfunctioning(true);
                } else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    myRO.setMalfunctioning(true);
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    myAES.setMalfunctioning(true);
            }
        }
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
                                        MalfunctionLength pLength) {
        StringBuffer returnBuffer = new StringBuffer();
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("AES and RO ");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("RO ");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("AES ");
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append("malfunctioning (repairable)");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("destroyed");
        return returnBuffer.toString();
    }

    public void log() {
        myLogger.debug("potable_water_produced=" + getPotableWaterProduced());
        myLogger.debug("grey_water_produced=" + getGreyWaterProduced());
        myLogger.debug("power_consumed=" + getPowerConsumed());
        myLogger.debug("dirty_water_consumed=" + getDirtyWaterConsumed());
        myLogger.debug("grey_water_consumed=" + getGreyWaterConsumed());
    }

    public int getSubsystemsConsumingPower() {
        return NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER;
    }

    /**
     * Sets the current WaterRS operation mode FULL - WaterRS operates at full
     * capacity (and power) GREY WATER ONLY - produces only grey water (saves
     * power) PARTIAL - produces 85% potable OFF - turns off WaterRS
     */
    public void setOperationMode(WaterRSOperationMode pMode) {
        myMode = pMode;
        if (myMode == WaterRSOperationMode.FULL) {
            myBWP.setEnabled(true);
            myPPS.setEnabled(true);
            myRO.setEnabled(true);
            myAES.setEnabled(true);
        } else if (myMode == WaterRSOperationMode.GREY_WATER_ONLY) {
            myBWP.setEnabled(true);
            myPPS.setEnabled(false);
            myRO.setEnabled(true);
            myAES.setEnabled(false);
        } else if (myMode == WaterRSOperationMode.PARTIAL) {
            myBWP.setEnabled(true);
            myPPS.setEnabled(true);
            myRO.setEnabled(true);
            myAES.setEnabled(false);
        } else if (myMode == WaterRSOperationMode.OFF) {
            myBWP.setEnabled(false);
            myPPS.setEnabled(false);
            myRO.setEnabled(false);
            myAES.setEnabled(false);
        } else {
            myLogger.warn("unknown state for WaterRS: " + myMode);
        }
    }

    /**
     * gets the current WaterRS Operation mode
     */
    public WaterRSOperationMode getOpertationMode() {
        return myMode;
    }

    /**
     * @return Returns the myDirtyWaterConsumerDefinition.
     */
    public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
        return myDirtyWaterConsumerDefinition;
    }

    /**
     * @return Returns the myGreyWaterConsumerDefinition.
     */
    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return myGreyWaterConsumerDefinition;
    }

    /**
     * @return Returns the myPotableWaterProducerDefinition.
     */
    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return myPotableWaterProducerDefinition;
    }

    /**
     * @return Returns the myPowerConsumerDefinition.
     */
    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }
}