package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperationMode;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.util.Engine;

//import java.lang.*;

/**
 * The Water Recovery System takes grey/dirty water and refines it to potable
 * water for the crew members and grey water for the crops.. Class modeled after
 * the paper:. "Intelligent Control of a Water Recovery System: Three Years in
 * the Trenches" by Bonasso, Kortenkamp, and Thronesbery
 * 
 * @author Scott Bell
 */

public class WaterRSLinearImpl extends SimBioModuleImpl implements
        WaterRSOperations, PowerConsumerOperations,
        DirtyWaterConsumerOperations, GreyWaterConsumerOperations,
        PotableWaterProducerOperations {
    //The various subsystems of Water RS that clean the water
    private LogIndex myLogIndex;
    
    private float currentPowerConsumed = 0f;
    
    private float currentWaterConsumed = 0f;

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

    //MatLab specific
    private Engine myEngine;

    private WaterRSMatlabTechInfoImpl myTechSpecificInfoImpl;

    //multiply times power to determine how much water we're consuming
    private static final float LINEAR_MULTIPLICATIVE_FACTOR = 4;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRSLinearImpl(int pID, String pName) {
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
    }

    /**
     * Resets production/consumption levels and resets all the subsystems
     */
    public void reset() {
        super.reset();
    }
    
    private void gatherPower() {
        currentPowerConsumed = getMostResourceFromStore(myPowerInputs, powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates);
    }
    
    private void gatherWater() {
        //1540 Watts -> 4.26 liters of water
        float waterNeeded = (currentPowerConsumed / 1540f) * 4.26f;
        float currentDirtyWaterConsumed = SimBioModuleImpl.getResourceFromStore(getDirtyWaterInputs(), getDirtyWaterInputMaxFlowRates(), getDirtyWaterInputDesiredFlowRates(), getDirtyWaterInputActualFlowRates(), waterNeeded);
        float currentGreyWaterConsumed = SimBioModuleImpl.getResourceFromStore(getGreyWaterInputs(), getGreyWaterInputMaxFlowRates(), getGreyWaterInputDesiredFlowRates(), getGreyWaterInputActualFlowRates(), waterNeeded - currentDirtyWaterConsumed);
        currentWaterConsumed = currentDirtyWaterConsumed
                + currentGreyWaterConsumed;
    }
    
    /**
     * Flushes the water from this subsystem (via the WaterRS) to the Potable
     * Water Store
     */
    private void pushWater() {
        float distributedWaterLeft = pushResourceToStore(getPotableWaterOutputs(), getPotableWaterOutputMaxFlowRates(), getPotableWaterOutputDesiredFlowRates(), getPotableWaterOutputActualFlowRates(), currentWaterConsumed);
    }

    /**
     * When ticked, the Water RS: 1) gets as much water as it can in relation to power
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherWater();
        pushWater();
    }

    protected void performMalfunctions() {
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "NoName";
    }

    /**
     * For fast reference to the log tree
     */
    private class LogIndex {
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

}