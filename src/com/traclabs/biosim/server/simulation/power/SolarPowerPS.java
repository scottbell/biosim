package com.traclabs.biosim.server.simulation.power;

import com.traclabs.biosim.server.simulation.environment.LightConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;

/**
 * Solar Power Production System
 * 
 * @author Scott Bell
 */

public class SolarPowerPS extends PowerPS implements PowerProducer {

    //Consumers, Producers
    private LightConsumerDefinition myLightConsumerDefinition;

    public SolarPowerPS(int pID, String pName) {
        super(pID, pName);
        myLightConsumerDefinition = new LightConsumerDefinition(this);
    }

    public LightConsumerDefinition getLightConsumerDefinition() {
        return myLightConsumerDefinition;
    }

    protected float calculatePowerProduced() {
        SimEnvironment lightInput = getLightConsumerDefinition()
                .getEnvironments()[0];
        //Varying stream of power
        if (lightInput != null) {
            float powerGenerated = getTickLength() * getCurrentUpperPowerGeneration()
                    * (lightInput.getLightIntensity() / lightInput
                            .getMaxLumens());
            return powerGenerated;
        }
		myLogger.error("SolarPowerPS: no light input!");
		return 0;
    }

    @Override
    public void reset(){
        super.reset();
        myLightConsumerDefinition.reset();
    }
}