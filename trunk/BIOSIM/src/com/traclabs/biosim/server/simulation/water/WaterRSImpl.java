package com.traclabs.biosim.server.simulation.water;

import java.util.Arrays;
import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperationMode;
import com.traclabs.biosim.idl.simulation.water.WaterRSOperations;
import com.traclabs.biosim.server.simulation.framework.DirtyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.GreyWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.util.OrbUtils;

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
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private GreyWaterConsumerDefinitionImpl myGreyWaterConsumerDefinitionImpl;

    private DirtyWaterConsumerDefinitionImpl myDirtyWaterConsumerDefinitionImpl;
    
    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;
    
    //The various subsystems of Water RS that clean the water
    private BWP myBWP;

    private RO myRO;

    private AES myAES;

    private PPS myPPS;
    
    private WaterRSSubSystem[] mySubsystems;

    private WaterRSOperationMode myMode;

    private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 4;

    /**
     * Creates the Water RS and it's subsystems
     */
    public WaterRSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myGreyWaterConsumerDefinitionImpl = new GreyWaterConsumerDefinitionImpl();
        myDirtyWaterConsumerDefinitionImpl = new DirtyWaterConsumerDefinitionImpl();
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl();
        
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
    
    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return (PowerConsumerDefinition) (OrbUtils
                .poaToCorbaObj(myPowerConsumerDefinitionImpl));
    }

    public GreyWaterConsumerDefinition getGreyWaterConsumerDefinition() {
        return (GreyWaterConsumerDefinition) (OrbUtils
                .poaToCorbaObj(myGreyWaterConsumerDefinitionImpl));
    }
    
    public DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition() {
        return (DirtyWaterConsumerDefinition) (OrbUtils
                .poaToCorbaObj(myDirtyWaterConsumerDefinitionImpl));
    }

    public PotableWaterProducerDefinition getPotableWaterProducerDefinition() {
        return (PotableWaterProducerDefinition) (OrbUtils
                .poaToCorbaObj(myPotableWaterProducerDefinitionImpl));
    }

    /**
     * Resets production/consumption levels and resets all the subsystems
     */
    public void reset() {
        super.reset();
        for (int i = 0; i < mySubsystems.length; i++)
            mySubsystems[i].reset();
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
            sumOfDesiredFlowRates += getPowerConsumerDefinition().getDesiredFlowRate(i);
        
        float totalPowerNeeded = 0;
        for (int i = 0; i < mySubsystems.length; i++)
            totalPowerNeeded += mySubsystems[i].getBasePowerNeeded();
        
        if (sumOfDesiredFlowRates >= totalPowerNeeded)
            setOperationMode(WaterRSOperationMode.FULL);
        else if (sumOfDesiredFlowRates >= (totalPowerNeeded - myAES.getBasePowerNeeded()))
                setOperationMode(WaterRSOperationMode.PARTIAL);
        else if (sumOfDesiredFlowRates >= (totalPowerNeeded - myAES.getBasePowerNeeded() - myPPS.getBasePowerNeeded()))
            setOperationMode(WaterRSOperationMode.GREY_WATER_ONLY);
        else
            setOperationMode(WaterRSOperationMode.OFF);
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
        myLogger.debug("potable_water_produced=" + getPotableWaterProduced());
        myLogger.debug("grey_water_produced=" + getGreyWaterProduced());
        myLogger.debug("power_consumed=" + getPowerConsumed());
        myLogger.debug("dirty_water_consumed=" + getDirtyWaterConsumed());
        myLogger.debug("grey_water_consumed=" + getGreyWaterConsumed());
    }

    int getSubsystemsConsumingPower() {
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
     * @return Returns the myDirtyWaterConsumerDefinitionImpl.
     */
    protected DirtyWaterConsumerDefinitionImpl getDirtyWaterConsumerDefinitionImpl() {
        return myDirtyWaterConsumerDefinitionImpl;
    }
    /**
     * @return Returns the myGreyWaterConsumerDefinitionImpl.
     */
    protected GreyWaterConsumerDefinitionImpl getGreyWaterConsumerDefinitionImpl() {
        return myGreyWaterConsumerDefinitionImpl;
    }
    /**
     * @return Returns the myPotableWaterProducerDefinitionImpl.
     */
    protected PotableWaterProducerDefinitionImpl getPotableWaterProducerDefinitionImpl() {
        return myPotableWaterProducerDefinitionImpl;
    }
    /**
     * @return Returns the myPowerConsumerDefinitionImpl.
     */
    protected PowerConsumerDefinitionImpl getPowerConsumerDefinitionImpl() {
        return myPowerConsumerDefinitionImpl;
    }
}