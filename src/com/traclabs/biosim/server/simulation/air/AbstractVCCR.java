package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducer;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.air.CO2Producer;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinition;

/**
 * This abstract class contains the shared producer/consumer definitions used by both VCCR and VCCRLinear.
 */
public abstract class AbstractVCCR extends SimBioModule implements PowerConsumer, AirConsumer, AirProducer, CO2Producer {
    protected final PowerConsumerDefinition myPowerConsumerDefinition;
    protected final AirConsumerDefinition myAirConsumerDefinition;
    protected final AirProducerDefinition myAirProducerDefinition;
    protected final CO2ProducerDefinition myCO2ProducerDefinition;
    
    public AbstractVCCR(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinition = new PowerConsumerDefinition(this);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
        myCO2ProducerDefinition = new CO2ProducerDefinition(this);
    }
    
    @Override
    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinition;
    }
    
    @Override
    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition;
    }
    
    @Override
    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition;
    }
    
    @Override
    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinition;
    }


    @Override
    public void reset() {
        myPowerConsumerDefinition.reset();
        myAirConsumerDefinition.reset();
        myAirProducerDefinition.reset();
        myCO2ProducerDefinition.reset();
    }
}