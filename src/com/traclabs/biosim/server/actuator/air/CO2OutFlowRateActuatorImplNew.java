package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.api.BioModule;
import com.traclabs.biosim.api.simulation.air.CO2Producer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImplNew;

/**
 * CO2 outflow rate actuator implementation.
 */
public class CO2OutFlowRateActuatorImplNew extends GenericActuatorImplNew {
    private CO2Producer myProducer;
    private int myIndex;

    /**
     * Constructor
     * 
     * @param pID The ID of the actuator
     * @param pName The name of the actuator
     */
    public CO2OutFlowRateActuatorImplNew(long pID, String pName) {
        super(pID, pName);
    }

    /**
     * Process the data (apply the actuator value to the target)
     */
    @Override
    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getCO2ProducerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    /**
     * Set the output module and index
     * 
     * @param pProducer The CO2 producer
     * @param pIndex The index
     */
    public void setOutput(CO2Producer pProducer, int pIndex) {
        myProducer = pProducer;
        myIndex = pIndex;
        myValue = getOutput().getCO2ProducerDefinition().getDesiredFlowRate(myIndex);
    }

    /**
     * Get the output module
     * 
     * @return The output module
     */
    @Override
    public BioModule getOutputModule() {
        return myProducer;
    }

    /**
     * Get the CO2 producer
     * 
     * @return The CO2 producer
     */
    public CO2Producer getOutput() {
        return myProducer;
    }

    /**
     * Get the index
     * 
     * @return The index
     */
    public int getIndex() {
        return myIndex;
    }

    /**
     * Get the maximum value of the actuator
     * 
     * @return The maximum value
     */
    @Override
    public float getMax() {
        return myProducer.getCO2ProducerDefinition().getMaxFlowRate(myIndex);
    }
}
