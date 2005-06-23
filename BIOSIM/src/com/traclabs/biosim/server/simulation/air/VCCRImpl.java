package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.VCCROperations;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class VCCRImpl extends SimBioModuleImpl implements VCCROperations,
        PowerConsumerOperations, AirConsumerOperations, AirProducerOperations,
        CO2ProducerOperations {

    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

    private Breath myBreath;

    private float currentCO2Produced = 0f;

    private float currentPowerConsumed = 0;

    public VCCRImpl(int pID, String pName) {
        super(pID, pName);
        myBreath = new Breath(0f, 0f, 0f, 0f, 0f);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl();
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl();
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl();
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl();
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinitionImpl.getCorbaObject();
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherAir();
        pushAir();
    }

    /**
     * Adds power for this tick
     */
    private void gatherPower() {
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getMostResourceFromStore();
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myBreath = new Breath(0f, 0f, 0f, 0f, 0f);
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
    }

    private void gatherAir() {
        //25.625 watts -> 1.2125 moles of Air
        float molesAirNeeded = (currentPowerConsumed / 25.625f) * 1.2125f;
        myBreath = myAirConsumerDefinitionImpl
                .getAirFromEnvironment(molesAirNeeded);
    }

    private void pushAir() {
        Breath breathToDistribute = new Breath(myBreath.O2, 0f, myBreath.water,
                myBreath.other, myBreath.nitrogen);
        myAirProducerDefinitionImpl
                .pushAirToEnvironments(breathToDistribute);
        currentCO2Produced = myBreath.CO2;
        myCO2ProducerDefinitionImpl
                .pushResourceToStore(currentCO2Produced);
        myLogger.debug("currentCO2Produced = " + currentCO2Produced);
    }
}