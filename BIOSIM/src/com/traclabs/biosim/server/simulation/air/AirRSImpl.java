package com.traclabs.biosim.server.simulation.air;

import java.util.Arrays;
import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.AirRSOperationMode;
import com.traclabs.biosim.idl.simulation.air.AirRSOperations;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.CO2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.H2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.H2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.O2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PotableWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

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
    private VCCRSubsystem myVCCR;

    private CRSSubsystem myCRS;

    private CH4Tank myCH4Tank;

    private OGSSubsystem myOGS;

    private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 3;

    private float myProductionRate = 1f;
    
    private AirRSSubSystem[] mySubsystems;
    

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;
    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;
    private PotableWaterProducerDefinitionImpl myPotableWaterProducerDefinitionImpl;
    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;
    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;
    private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;
    private CO2ConsumerDefinitionImpl myCO2ConsumerDefinitionImpl;
    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;
    private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;
    private H2ConsumerDefinitionImpl myH2ConsumerDefinitionImpl;

    private AirRSOperationMode myMode;

    public AirRSImpl(int pID, String pName) {
        super(pID, pName);
        myVCCR = new VCCRSubsystem(this);
        myCRS = new CRSSubsystem(this);
        myCH4Tank = new CH4Tank(this);
        myOGS = new OGSSubsystem(this);

        mySubsystems = new AirRSSubSystem[4];
        mySubsystems[0] = myVCCR;
        mySubsystems[1] = myCRS;
        mySubsystems[2] = myOGS;
        mySubsystems[3] = myCH4Tank;
        
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl();
        myPotableWaterProducerDefinitionImpl = new PotableWaterProducerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl();
        myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl();
        myCO2ConsumerDefinitionImpl = new CO2ConsumerDefinitionImpl();
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl();
        myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl();
        myH2ConsumerDefinitionImpl = new H2ConsumerDefinitionImpl();
    }
    
    public PowerConsumerDefinition getPowerConsumerDefinition(){
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }
    
    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition(){
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }
    
    public PotableWaterProducerDefinition getPotableWaterProducerDefinition(){
        return myPotableWaterProducerDefinitionImpl.getCorbaObject();
    }
    
    public AirConsumerDefinition getAirConsumerDefinition(){
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }
    
    public AirProducerDefinition getAirProducerDefinition(){
        return myAirProducerDefinitionImpl.getCorbaObject();
    }
    
    public CO2ProducerDefinition getCO2ProducerDefinition(){
        return myCO2ProducerDefinitionImpl.getCorbaObject();
    }
    
    public CO2ConsumerDefinition getCO2ConsumerDefinition(){
        return myCO2ConsumerDefinitionImpl.getCorbaObject();
    }
    
    public O2ProducerDefinition getO2ProducerDefinition(){
        return myO2ProducerDefinitionImpl.getCorbaObject();
    }
    
    public H2ProducerDefinition getH2ProducerDefinition(){
        return myH2ProducerDefinitionImpl.getCorbaObject();
    }
    
    public H2ConsumerDefinition getH2ConsumerDefinition(){
        return myH2ConsumerDefinitionImpl.getCorbaObject();
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

    VCCRSubsystem getVCCR() {
        return myVCCR;
    }

    CRSSubsystem getCRS() {
        return myCRS;
    }

    CH4Tank getCH4Tank() {
        return myCH4Tank;
    }

    OGSSubsystem getOGS() {
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
        Arrays.fill(getPowerConsumerDefinition().getActualFlowRates(), 0f);
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
        for (int i = 0; i < getPowerConsumerDefinition().getDesiredFlowRates().length; i++)
            sumOfDesiredFlowRates += getPowerConsumerDefinition().getDesiredFlowRate(i);
        
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
    /**
     * @return Returns the myAirConsumerDefinitionImpl.
     */
    protected AirConsumerDefinitionImpl getAirConsumerDefinitionImpl() {
        return myAirConsumerDefinitionImpl;
    }
    /**
     * @return Returns the myCO2ConsumerDefinitionImpl.
     */
    protected CO2ConsumerDefinitionImpl getCO2ConsumerDefinitionImpl() {
        return myCO2ConsumerDefinitionImpl;
    }
    /**
     * @return Returns the myCO2ProducerDefinitionImpl.
     */
    protected CO2ProducerDefinitionImpl getCO2ProducerDefinitionImpl() {
        return myCO2ProducerDefinitionImpl;
    }
    /**
     * @return Returns the myH2ConsumerDefinitionImpl.
     */
    protected H2ConsumerDefinitionImpl getH2ConsumerDefinitionImpl() {
        return myH2ConsumerDefinitionImpl;
    }
    /**
     * @return Returns the myH2ProducerDefinitionImpl.
     */
    protected H2ProducerDefinitionImpl getH2ProducerDefinitionImpl() {
        return myH2ProducerDefinitionImpl;
    }
    /**
     * @return Returns the myO2ProducerDefinitionImpl.
     */
    protected O2ProducerDefinitionImpl getO2ProducerDefinitionImpl() {
        return myO2ProducerDefinitionImpl;
    }
    /**
     * @return Returns the myPotableWaterConsumerDefinitionImpl.
     */
    protected PotableWaterConsumerDefinitionImpl getPotableWaterConsumerDefinitionImpl() {
        return myPotableWaterConsumerDefinitionImpl;
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
    /**
     * @return Returns the myAirProducerDefinitionImpl.
     */
    protected AirProducerDefinitionImpl getAirProducerDefinitionImpl() {
        return myAirProducerDefinitionImpl;
    }
    /**
     * @param myAirProducerDefinitionImpl The myAirProducerDefinitionImpl to set.
     */
    protected void setMyAirProducerDefinitionImpl(
            AirProducerDefinitionImpl myAirProducerDefinitionImpl) {
        this.myAirProducerDefinitionImpl = myAirProducerDefinitionImpl;
    }
    /**
     * @return Returns the myAirConsumerDefinitionImpl.
     */
    protected AirConsumerDefinitionImpl getMyAirConsumerDefinitionImpl() {
        return myAirConsumerDefinitionImpl;
    }
}