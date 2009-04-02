package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.simulation.air.H2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.OGSOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionImpl;

public class OGSImpl extends SimBioModuleImpl implements OGSOperations,
        PowerConsumerOperations, PotableWaterConsumerOperations,
        O2ProducerOperations, H2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private O2ProducerDefinitionImpl myO2ProducerDefinitionImpl;

    private H2ProducerDefinitionImpl myH2ProducerDefinitionImpl;

    private float currentH2OConsumed = 0;

    private float currentO2Produced = 0;

    private float currentH2Produced = 0;

    private float myProductionRate = 1f;

    private float currentPowerConsumed = 0f;

    public OGSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl(this);
        myO2ProducerDefinitionImpl = new O2ProducerDefinitionImpl(this);
        myH2ProducerDefinitionImpl = new H2ProducerDefinitionImpl(this);
    }
    
    @Override
    protected void performMalfunctions() {
		for (Malfunction malfunction : myMalfunctions.values()) {
			malfunction.setPerformed(true);
		}
		if (myMalfunctions.values().size() > 0) {
	        myPowerConsumerDefinitionImpl.malfunction();
	        myPotableWaterConsumerDefinitionImpl.malfunction();
	        myO2ProducerDefinitionImpl.malfunction();
	        myH2ProducerDefinitionImpl.malfunction();
		}
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public O2ProducerDefinition getO2ProducerDefinition() {
        return myO2ProducerDefinitionImpl.getCorbaObject();
    }

    public H2ProducerDefinition getH2ProducerDefinition() {
        return myH2ProducerDefinitionImpl.getCorbaObject();
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentH2OConsumed = 0;
        currentO2Produced = 0;
        currentH2Produced = 0;
        currentPowerConsumed = 0;
        myPowerConsumerDefinitionImpl.reset();
        myPotableWaterConsumerDefinitionImpl.reset();
        myO2ProducerDefinitionImpl.reset();
        myH2ProducerDefinitionImpl.reset();
    }

    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStores();
    }

    public void tick() {
        super.tick();
        gatherPower();
        gatherWater();
        pushGasses();
    }
    
    public void log() {
        myLogger.debug("power_consumed=" + currentPowerConsumed);
    }

    private void gatherWater() {
        float waterToConsume = (currentPowerConsumed / 75f) * 0.04167f * getTickLength();
        currentH2OConsumed = myPotableWaterConsumerDefinitionImpl
                .getResourceFromStores(waterToConsume);
    }

    private void pushGasses() {
        //2H20 --> 2H2 + O2
        float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter,
        // 18.01524g/mole
        float molesOfReactant = molesOfWater / 2f;
        currentO2Produced = molesOfReactant * myProductionRate;
        currentH2Produced = molesOfReactant * 2f
                * myProductionRate;
        float O2ToDistrubute = currentO2Produced;
        float H2ToDistrubute = currentH2Produced;
        myO2ProducerDefinitionImpl
                .pushResourceToStores(O2ToDistrubute);
        myH2ProducerDefinitionImpl
                .pushResourceToStores(H2ToDistrubute);
    }
}