package com.traclabs.biosim.server.actuator.environment;

import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.environment.AirProducer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImpl;

public class AirOutFlowRateActuatorImpl extends GenericActuatorImpl implements
        AirOutFlowRateActuatorOperations {
    private AirProducer myProducer;

    private int myIndex;
    
    public AirOutFlowRateActuatorImpl(){
    	super(0, "Unnamed AirOutFlowRateActuator");
    }
    
    public AirOutFlowRateActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getAirProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }



    public void setOutput(AirProducer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getAirProducerDefinition().getDesiredFlowRate(myIndex);
    }

    public BioModule getOutputModule() {
        return myProducer;
    }

    public float getMax() {
        return myProducer.getAirProducerDefinition().getMaxFlowRate(myIndex);
    }

    public AirProducer getOutput() {
        return myProducer;
    }

    public int getIndex() {
        return myIndex;
    }
}