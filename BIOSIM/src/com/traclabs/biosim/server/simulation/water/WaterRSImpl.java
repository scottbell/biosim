package com.traclabs.biosim.server.simulation.water;

import java.util.Arrays;
import java.util.Iterator;

import com.traclabs.biosim.idl.framework.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperationMode;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Water Recovery System takes grey/dirty water and refines it to potable
 * water for the crew members and grey water for the crops.. Class modeled after
 * the paper:. "Intelligent Control of a Water Recovery System: Three Years in
 * the Trenches" by Bonasso, Kortenkamp, and Thronesbery
 * 
 * @author Scott Bell
 */

public class WaterRSImpl extends SimBioModuleImpl implements WaterRSOperations,
        PowerConsumerOperations, DirtyWaterConsumerOperations,
        GreyWaterConsumerOperations, PotableWaterProducerOperations {
    //The various subsystems of Water RS that clean the water
    private BWP myBWP;

    private RO myRO;

    private AES myAES;

    private PPS myPPS;

    private PowerStore[] myPowerInputs;

    private DirtyWaterStore[] myDirtyWaterInputs;

    private GreyWaterStore[] myGreyWaterInputs;

    private PotableWaterStore[] myPotableWaterOutputs;

    private float[] powerMaxFlowRates;

    private float[] dirtyWaterMaxFlowRates;

    private float[] greyWaterMaxFlowRates;

    private float[] potableWaterMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] dirtyWaterActualFlowRates;

    private float[] greyWaterActualFlowRates;

    private float[] potableWaterActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float[] dirtyWaterDesiredFlowRates;

    private float[] greyWaterDesiredFlowRates;

    private float[] potableWaterDesiredFlowRates;
    
    private WaterRSOperationMode myMode;
    
    private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 4;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerInputs = new PowerStore[0];
        myDirtyWaterInputs = new DirtyWaterStore[0];
        myGreyWaterInputs = new GreyWaterStore[0];
        myPotableWaterOutputs = new PotableWaterStore[0];
        powerMaxFlowRates = new float[0];
        dirtyWaterMaxFlowRates = new float[0];
        greyWaterMaxFlowRates = new float[0];
        potableWaterMaxFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        dirtyWaterActualFlowRates = new float[0];
        greyWaterActualFlowRates = new float[0];
        potableWaterActualFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
        dirtyWaterDesiredFlowRates = new float[0];
        greyWaterDesiredFlowRates = new float[0];
        potableWaterDesiredFlowRates = new float[0];
        myBWP = new BWP(this);
        myRO = new RO(this);
        myAES = new AES(this);
        myPPS = new PPS(this);
    }

    /**
     * Resets production/consumption levels and resets all the subsystems
     */
    public void reset() {
        super.reset();
        myBWP.reset();
        myRO.reset();
        myAES.reset();
        myPPS.reset();
    }

    /**
     * Returns the RO subsystem
     * 
     * @return the RO subsystem
     */
    RO getRO() {
        return myRO;
    }

    /**
     * Returns the AES subsystem
     * 
     * @return the AES subsystem
     */
    AES getAES() {
        return myAES;
    }

    /**
     * Returns the PPS subsystem
     * 
     * @return the PPS subsystem
     */
    PPS getPPS() {
        return myPPS;
    }

    /**
     * Returns the BWP subsystem
     * 
     * @return the BWP subsystem
     */
    BWP getBWP() {
        return myBWP;
    }

    /**
     * Returns the potable water produced (in liters) by the Water RS during the
     * current tick
     * 
     * @return the potable water produced (in liters) by the Water RS during the
     *         current tick
     */
    public float getPotableWaterProduced() {
        return myPPS.getPotableWaterProduced();
    }

    /**
     * Returns the grey water produced (in liters) by the Water RS during the
     * current tick
     * 
     * @return the grey water produced (in liters) by the Water RS during the
     *         current tick
     */
    public float getGreyWaterProduced() {
        return 0f;
    }

    /**
     * Returns the grey water consumed (in liters) by the Water RS during the
     * current tick
     * 
     * @return the grey water consumed (in liters) by the Water RS during the
     *         current tick
     */
    public float getGreyWaterConsumed() {
        return myBWP.getGreyWaterConsumed();
    }

    /**
     * Returns the power consumed (in watts) by the Water RS during the current
     * tick
     * 
     * @return the power consumed (in watts) by the Water RS during the current
     *         tick
     */
    public float getPowerConsumed() {
        float powerConsumed = myAES.getPowerConsumed()
                + myPPS.getPowerConsumed() + myBWP.getPowerConsumed()
                + myRO.getPowerConsumed();
        return powerConsumed;
    }

    /**
     * Returns the dirty water consumed (in liters) by the Water RS during the
     * current tick
     * 
     * @return the dirty water consumed (in liters) by the Water RS during the
     *         current tick
     */
    public float getDirtyWaterConsumed() {
        return myBWP.getDirtyWaterConsumed();
    }

    /**
     * When ticked, the Water RS: 1) ticks each subsystem.
     */
    public void tick() {
        super.tick();
        Arrays.fill(powerActualFlowRates, 0f);
        //tick each system
        myBWP.tick();
        myRO.tick();
        myAES.tick();
        myPPS.tick();
        myAES.setMalfunctioning(false);
        myRO.setMalfunctioning(false);
    }

    protected void performMalfunctions() {
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
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
        myLogger.debug("potable_water_produced="+getPotableWaterProduced());
        myLogger.debug("grey_water_produced="+getGreyWaterProduced());
        myLogger.debug("power_consumed="+getPowerConsumed());
        myLogger.debug("dirty_water_consumed="+getDirtyWaterConsumed());
        myLogger.debug("grey_water_consumed="+getGreyWaterConsumed());
    }
    
    int getSubsystemsConsumingPower() {
        return NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER;
    }

    //Power Input
    public void setPowerInputMaxFlowRate(float watts, int index) {
        powerMaxFlowRates[index] = watts;
    }

    public float getPowerInputMaxFlowRate(int index) {
        return powerMaxFlowRates[index];
    }

    public float[] getPowerInputMaxFlowRates() {
        return powerMaxFlowRates;
    }

    public void setPowerInputDesiredFlowRate(float watts, int index) {
        powerDesiredFlowRates[index] = watts;
    }

    public float getPowerInputDesiredFlowRate(int index) {
        return powerDesiredFlowRates[index];
    }

    public float[] getPowerInputDesiredFlowRates() {
        return powerDesiredFlowRates;
    }

    public float getPowerInputActualFlowRate(int index) {
        return powerActualFlowRates[index];
    }

    public float[] getPowerInputActualFlowRates() {
        return powerActualFlowRates;
    }

    public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myPowerInputs = sources;
        powerMaxFlowRates = maxFlowRates;
        powerDesiredFlowRates = desiredFlowRates;
        powerActualFlowRates = new float[powerDesiredFlowRates.length];
    }

    public PowerStore[] getPowerInputs() {
        return myPowerInputs;
    }

    void addPowerInputActualFlowRate(float watts, int index) {
        powerActualFlowRates[index] += watts;
    }

    //Dirty Water Input
    public void setDirtyWaterInputMaxFlowRate(float watts, int index) {
        dirtyWaterMaxFlowRates[index] = watts;
    }

    public float getDirtyWaterInputMaxFlowRate(int index) {
        return dirtyWaterMaxFlowRates[index];
    }

    public float[] getDirtyWaterInputMaxFlowRates() {
        return dirtyWaterMaxFlowRates;
    }

    public void setDirtyWaterInputDesiredFlowRate(float watts, int index) {
        dirtyWaterDesiredFlowRates[index] = watts;
    }

    public float getDirtyWaterInputDesiredFlowRate(int index) {
        return dirtyWaterDesiredFlowRates[index];
    }

    public float[] getDirtyWaterInputDesiredFlowRates() {
        return dirtyWaterDesiredFlowRates;
    }

    public float getDirtyWaterInputActualFlowRate(int index) {
        return dirtyWaterActualFlowRates[index];
    }

    public float[] getDirtyWaterInputActualFlowRates() {
        return dirtyWaterActualFlowRates;
    }

    public void setDirtyWaterInputs(DirtyWaterStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myDirtyWaterInputs = sources;
        dirtyWaterMaxFlowRates = maxFlowRates;
        dirtyWaterDesiredFlowRates = desiredFlowRates;
        dirtyWaterActualFlowRates = new float[dirtyWaterDesiredFlowRates.length];
    }

    public DirtyWaterStore[] getDirtyWaterInputs() {
        return myDirtyWaterInputs;
    }

    //Grey Water Input
    public void setGreyWaterInputMaxFlowRate(float watts, int index) {
        greyWaterMaxFlowRates[index] = watts;
    }

    public float getGreyWaterInputMaxFlowRate(int index) {
        return greyWaterMaxFlowRates[index];
    }

    public float[] getGreyWaterInputMaxFlowRates() {
        return greyWaterMaxFlowRates;
    }

    public void setGreyWaterInputDesiredFlowRate(float watts, int index) {
        greyWaterDesiredFlowRates[index] = watts;
    }

    public float getGreyWaterInputDesiredFlowRate(int index) {
        return greyWaterDesiredFlowRates[index];
    }

    public float[] getGreyWaterInputDesiredFlowRates() {
        return greyWaterDesiredFlowRates;
    }

    public float getGreyWaterInputActualFlowRate(int index) {
        return greyWaterActualFlowRates[index];
    }

    public float[] getGreyWaterInputActualFlowRates() {
        return greyWaterActualFlowRates;
    }

    public void setGreyWaterInputs(GreyWaterStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myGreyWaterInputs = sources;
        greyWaterMaxFlowRates = maxFlowRates;
        greyWaterDesiredFlowRates = desiredFlowRates;
        greyWaterActualFlowRates = new float[greyWaterDesiredFlowRates.length];
    }

    public GreyWaterStore[] getGreyWaterInputs() {
        return myGreyWaterInputs;
    }

    //Potable Water Output
    public void setPotableWaterOutputMaxFlowRate(float watts, int index) {
        potableWaterMaxFlowRates[index] = watts;
    }

    public float getPotableWaterOutputMaxFlowRate(int index) {
        return potableWaterMaxFlowRates[index];
    }

    public float[] getPotableWaterOutputMaxFlowRates() {
        return potableWaterMaxFlowRates;
    }

    public void setPotableWaterOutputDesiredFlowRate(float watts, int index) {
        potableWaterDesiredFlowRates[index] = watts;
    }

    public float getPotableWaterOutputDesiredFlowRate(int index) {
        return potableWaterDesiredFlowRates[index];
    }

    public float[] getPotableWaterOutputDesiredFlowRates() {
        return potableWaterDesiredFlowRates;
    }

    public float getPotableWaterOutputActualFlowRate(int index) {
        return potableWaterActualFlowRates[index];
    }

    public float[] getPotableWaterOutputActualFlowRates() {
        return potableWaterActualFlowRates;
    }

    public void setPotableWaterOutputs(PotableWaterStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myPotableWaterOutputs = sources;
        potableWaterMaxFlowRates = maxFlowRates;
        potableWaterDesiredFlowRates = desiredFlowRates;
        potableWaterActualFlowRates = new float[potableWaterDesiredFlowRates.length];
    }

    public PotableWaterStore[] getPotableWaterOutputs() {
        return myPotableWaterOutputs;
    }

    /**
     * Sets the current WaterRS operation mode
     * FULL - WaterRS operates at full capacity (and power)
     * GREY WATER ONLY - produces only grey water (saves power)
     * PARTIAL - produces 85% potable
     * OFF - turns off WaterRS
     */
    public void setOperationMode(WaterRSOperationMode pMode) {
        myMode = pMode;
        if (myMode == WaterRSOperationMode.FULL){
            myBWP.setEnabled(true);
            myPPS.setEnabled(true);
            myRO.setEnabled(true);
            myAES.setEnabled(true);
        }
        else if (myMode == WaterRSOperationMode.GREY_WATER_ONLY){
            myBWP.setEnabled(true);
            myPPS.setEnabled(false);
            myRO.setEnabled(true);
            myAES.setEnabled(true);
        }
        else if (myMode == WaterRSOperationMode.PARTIAL){
            myBWP.setEnabled(true);
            myPPS.setEnabled(true);
            myRO.setEnabled(true);
            myAES.setEnabled(false);
        }
        else if (myMode == WaterRSOperationMode.OFF){
            myBWP.setEnabled(false);
            myPPS.setEnabled(false);
            myRO.setEnabled(false);
            myAES.setEnabled(false);
        }
        else{
            myLogger.warn("unknown state for WaterRS: "+myMode);
        }
    }

    /**
     * gets the current WaterRS Operation mode
     */
    public WaterRSOperationMode getOpertationMode() {
        return myMode;
    }
}