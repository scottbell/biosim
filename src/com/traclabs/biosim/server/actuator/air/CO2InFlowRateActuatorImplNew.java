package com.traclabs.biosim.server.actuator.air;

import com.traclabs.biosim.api.BioModule;
import com.traclabs.biosim.api.simulation.air.CO2Consumer;
import com.traclabs.biosim.server.actuator.framework.GenericActuatorImplNew;

/**
 * CO2 inflow rate actuator implementation.
 */
public class CO2InFlowRateActuatorImplNew extends GenericActuatorImplNew {
    private CO2Consumer myConsumer;
    private int myIndex;

    /**
     * Constructor
     * 
     * @param pID The ID of the actuator
     * @param pName The name of the actuator
     */
    public CO2InFlowRateActuatorImplNew(long pID, String pName) {
        super(pID, pName);
    }

    /**
     * Process the data (apply the actuator value to the target)
     */
    @Override
    protected void processData() {
        float myFilteredValue = getStochasticFilter().randomFilter(myValue);
        getOutput().getCO2ConsumerDefinition().setDesiredFlowRate(
                myFilteredValue, myIndex);
    }

    /**
     * Set the output module and index
     * 
     * @param pConsumer The CO2 consumer
     * @param pIndex The index
     */
    public void setOutput(CO2Consumer pConsumer, int pIndex) {
        myConsumer = pConsumer;
        myIndex = pIndex;
        myValue = getOutput().getCO2ConsumerDefinition().getDesiredFlowRate(myIndex);
    }

    /**
     * Get the output module
     * 
     * @return The output module
     */
    @Override
    public BioModule getOutputModule() {
        return myConsumer;
    }

    /**
     * Get the CO2 consumer
     * 
     * @return The CO2 consumer
     */
    public CO2Consumer getOutput() {
        return myConsumer;
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
        return myConsumer.getCO2ConsumerDefinition().getMaxFlowRate(myIndex);
    }
}
