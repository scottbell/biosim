package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentStoreOperations;
import com.traclabs.biosim.server.simulation.framework.StoreImpl;

public class EnvironmentStoreImpl extends StoreImpl implements EnvironmentStoreOperations{

    private final static float idealGasConstant = 8.314f; // J K ^-1 mol -1
    private float totalTaken = 0;
    private float totalAdded = 0;

    protected SimEnvironmentImpl mySimEnvironmentImpl;

	public EnvironmentStoreImpl(SimEnvironmentImpl pSimEnvironmentImpl, String storeName) {
		super(0, pSimEnvironmentImpl.getModuleName()+storeName, 0, Float.MAX_VALUE, false);
		mySimEnvironmentImpl = pSimEnvironmentImpl;
	}

	public float getPressure() {
		return calculatePressure(getCurrentLevel());
	}
	
	public float getInitialPressure() {
		return calculatePressure(getInitialLevel(), mySimEnvironmentImpl.getInitialVolume());
	}
	
    private float calculatePressure(float pNumberOfMoles) {
    	return calculatePressure(pNumberOfMoles, mySimEnvironmentImpl.getCurrentVolume());
    }

    private float calculatePressure(float pNumberOfMoles, float volume) {
    	float temperature = mySimEnvironmentImpl.getTemperatureInKelvin();
        if (volume > 0)
            return (pNumberOfMoles * idealGasConstant * temperature)
                    / volume;
		return 0;
    }
    
    protected void performLeak(float pLeakRate){
    	float afterLeakAmount = getCurrentLevel() - (getCurrentLevel() * pLeakRate);
    	setCurrentLevel(afterLeakAmount);
    }
    
    public float take(float pAmount){
    	float taken = super.take(pAmount);
    	totalTaken += taken;
    	return taken;
    }
    
    public float add(float pAmount){
    	float added = super.add(pAmount);
    	totalAdded += added;
    	return added;
    }
    
    public void reset(){
    	super.reset();
    	totalTaken = 0;
        totalAdded = 0;
    }
    
    public void tick(){
    	super.tick();
    }

}
