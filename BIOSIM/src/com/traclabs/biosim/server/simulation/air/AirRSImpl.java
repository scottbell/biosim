package com.traclabs.biosim.server.simulation.air;

import java.util.Arrays;
import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.AirRSOperationMode;
import com.traclabs.biosim.idl.simulation.air.AirRSOperations;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class AirRSImpl extends SimBioModuleImpl implements AirRSOperations,
        PowerConsumerOperations, PotableWaterConsumerOperations,
        PotableWaterProducerOperations, AirConsumerOperations,
        O2ProducerOperations, AirProducerOperations, CO2ProducerOperations,
        CO2ConsumerOperations, H2ProducerOperations, H2ConsumerOperations {
    //Consumers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;
    
    private VCCR myVCCR;

    private CRS myCRS;

    private CH4Tank myCH4Tank;

    private OGS myOGS;

    private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 3;

    private float myProductionRate = 1f;
    
    private AirRSSubSystem[] mySubsystems;

    private AirRSOperationMode myMode;

    public AirRSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myVCCR = new VCCR(this);
        myCRS = new CRS(this);
        myCH4Tank = new CH4Tank(this);
        myOGS = new OGS(this);

        mySubsystems = new AirRSSubSystem[4];
        mySubsystems[0] = myVCCR;
        mySubsystems[1] = myCRS;
        mySubsystems[2] = myOGS;
        mySubsystems[3] = myCH4Tank;
    }
    
    public PowerConsumerDefinition getPowerConsumerDefinition(){
        return (PowerConsumerDefinition)(OrbUtils.poaToCorbaObj(myPowerConsumerDefinitionImpl));
    }

    public boolean VCCRHasPower() {
        return myVCCR.hasPower();
    }

    public boolean CRSHasPower() {
        return myCRS.hasPower();
    }

    public boolean OGSHasPower() {
        return myOGS.hasPower();
    }

    VCCR getVCCR() {
        return myVCCR;
    }

    CRS getCRS() {
        return myCRS;
    }

    CH4Tank getCH4Tank() {
        return myCH4Tank;
    }

    OGS getOGS() {
        return myOGS;
    }

    /**
     * Returns the power consumption (in watts) of the AirRS at the current
     * tick.
     * 
     * @return the power consumed (in watts) at the current tick
     */
    public float getPowerConsumed() {
        return myVCCR.getPowerConsumed() + myOGS.getPowerConsumed()
                + myCRS.getPowerConsumed();
    }

    /**
     * Returns the CO2 consumption (in moles) of the AirRS at the current tick.
     * 
     * @return the CO2 consumed at the current tick
     */
    public float getCO2Consumed() {
        return myVCCR.getCO2Consumed();
    }

    /**
     * Returns the O2 produced (in moles) of the AirRS at the current tick.
     * 
     * @return the O2 produced (in moles) at the current tick
     */
    public float getO2Produced() {
        return myOGS.getO2Produced();
    }

    /**
     * Returns the CO2 produced (in moles) of the AirRS at the current tick.
     * 
     * @return the CO2 produced (in moles) at the current tick
     */
    public float getCO2Produced() {
        return myVCCR.getCO2Produced();
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        Arrays.fill(powerActualFlowRates, 0f);
        enableSubsystemsBasedOnPower();
        for (int i = 0; i < mySubsystems.length; i++)
            mySubsystems[i].tick();
    }
    
    /**
     * @param sumOfDesiredFlowRates
     * @param powerNeeded
     */
    private void enableSubsystemsBasedOnPower() {
        float sumOfDesiredFlowRates = 0f;
        for (int i = 0; i < powerDesiredFlowRates.length; i++)
            sumOfDesiredFlowRates += powerDesiredFlowRates[i];
        
        float totalPowerNeeded = 0;
        for (int i = 0; i < mySubsystems.length; i++)
            totalPowerNeeded += mySubsystems[i].getBasePowerNeeded();
        
        if (sumOfDesiredFlowRates >= totalPowerNeeded)
            setOperationMode(AirRSOperationMode.FULL);
        else if (sumOfDesiredFlowRates >= (totalPowerNeeded - myOGS.getBasePowerNeeded()))
                setOperationMode(AirRSOperationMode.MOST);
        else if (sumOfDesiredFlowRates >= (totalPowerNeeded - myOGS.getBasePowerNeeded() - myCRS.getBasePowerNeeded()))
            setOperationMode(AirRSOperationMode.LESS);
        else
            setOperationMode(AirRSOperationMode.OFF);
    }

    public void setProductionRate(float percentage) {
        myVCCR.setProductionRate(percentage);
        myOGS.setProductionRate(percentage);
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        StringBuffer returnBuffer = new StringBuffer();
        if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
            returnBuffer.append("Severe ");
        else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
            returnBuffer.append("Medium ");
        else if (pIntensity == MalfunctionIntensity.LOW_MALF)
            returnBuffer.append("Low ");
        if (pLength == MalfunctionLength.TEMPORARY_MALF)
            returnBuffer.append("Production Rate Decrease (Temporary)");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Production Rate Decrease (Permanent)");
        return returnBuffer.toString();
    }

    protected void performMalfunctions() {
        float productionRate = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            }
        }
        setProductionRate(productionRate);
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        for (int i = 0; i < mySubsystems.length; i++)
            mySubsystems[i].reset();
    }

    int getSubsystemsConsumingPower() {
        return NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER;
    }


    /**
     * Sets the current AirRS operation 
     * modes:
     * FULL - AirRS operates at full capacity (and power) 
     * MOST - turns off OGS
     * LESS - turns off OGS, CRS
     * OFF  - turns everything off
     */
    public void setOperationMode(AirRSOperationMode pMode) {
        myMode = pMode;
        if (myMode == AirRSOperationMode.FULL) {
            myVCCR.setEnabled(true);
            myCRS.setEnabled(true);
            myOGS.setEnabled(true);
        } else if (myMode == AirRSOperationMode.MOST) {
            myVCCR.setEnabled(true);
            myCRS.setEnabled(true);
            myOGS.setEnabled(false);
        } else if (myMode == AirRSOperationMode.LESS) {
            myVCCR.setEnabled(true);
            myCRS.setEnabled(false);
            myOGS.setEnabled(false);
        } else if (myMode == AirRSOperationMode.OFF) {
            myVCCR.setEnabled(false);
            myCRS.setEnabled(false);
            myOGS.setEnabled(false);
        } else {
            myLogger.warn("unknown state for AirRS: " + myMode);
        }
    }

    /**
     * gets the current AirRS Operation mode
     */
    public AirRSOperationMode getOpertationMode() {
        return myMode;
    }
}