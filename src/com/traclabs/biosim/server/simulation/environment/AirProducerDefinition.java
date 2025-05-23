package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.BioModule;

/**
 * @author Scott Bell
 */

public class AirProducerDefinition extends EnvironmentFlowRateControllable {

    public AirProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    private static float calculateTotalAirMoles(Air air) {
        return air.o2Moles + air.co2Moles + air.nitrogenMoles + air.otherMoles + air.vaporMoles;
    }

    public void setAirOutputs(SimEnvironment[] pSimEnvironments,
                              float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialEnvironments(pSimEnvironments);
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
    }

    public void pushAirToEnvironment(Air airToPush, int indexOfEnvironment) {
        if (getEnvironments().length < indexOfEnvironment)
            return;
        float actualFlowrateToEnvironment = 0f;
        SimEnvironment environment = getEnvironments()[indexOfEnvironment];
        actualFlowrateToEnvironment += environment.getO2Store().add(randomFilter(airToPush.o2Moles));
        actualFlowrateToEnvironment += environment.getCO2Store().add(randomFilter(airToPush.co2Moles));
        actualFlowrateToEnvironment += environment.getOtherStore().add(randomFilter(airToPush.otherMoles));
        actualFlowrateToEnvironment += environment.getVaporStore().add(randomFilter(airToPush.vaporMoles));
        actualFlowrateToEnvironment += environment.getNitrogenStore().add(randomFilter(airToPush.nitrogenMoles));
        setActualFlowRate(actualFlowrateToEnvironment, indexOfEnvironment);
    }
}