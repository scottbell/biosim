package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class AirInFlowRateActuatorImpl extends GenericActuatorImpl implements
        AirInFlowRateActuatorOperations {
    private AirConsumer myConsumer;

    private int myIndex;
    
    public AirInFlowRateActuatorImpl(){
    	super(0, "Unnamed AirInFlowRateActuator");
    }

    public AirInFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getAirConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(AirConsumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getAirConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    public AirConsumer getOutput() {
        return myConsumer;
    }

    public BioModule getOutputModule() {
        return myConsumer;
    }

    public int getIndex() {
        return myIndex;
    }

    public float getMax() {
        return myConsumer.getAirConsumerDefinition().getMaxFlowRate(myIndex);
    }
}