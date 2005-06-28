package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

public class EnvironmentStoreImpl extends StoreImpl implements EnvironmentStoreOperations{

    private final static float idealGasConstant = 8.314f; // J K ^-1 mol -1

    private SimEnvironmentImpl mySimEnvironmentImpl;

	public EnvironmentStoreImpl(SimEnvironmentImpl pSimEnvironmentImpl) {
		super(0, "Unnamed", 0, Float.MAX_VALUE, false);
		mySimEnvironmentImpl = pSimEnvironmentImpl;
	}

	public float getPressure() {
		return calculatePressure(getCurrentLevel());
	}

    private float calculatePressure(float pNumberOfMoles) {
    	float currentVolume = mySimEnvironmentImpl.getCurrentVolume();
    	float temperature = mySimEnvironmentImpl.getTemperature();
        if (currentVolume > 0)
            return (pNumberOfMoles * idealGasConstant * (temperature + 273f))
                    / currentVolume;
		return 0;
    }
    
    protected void performLeak(float pLeakRate){
    	float afterLeakAmount = getCurrentLevel() - (getCurrentLevel() * pLeakRate);
    	setCurrentLevel(afterLeakAmount);
    }

}
