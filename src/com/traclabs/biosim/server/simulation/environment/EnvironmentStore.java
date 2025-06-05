package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.simulation.framework.Store;

public class EnvironmentStore extends Store {

    private final static float idealGasConstant = 8.314f; // J K ^-1 mol -1
    protected SimEnvironment mySimEnvironment;
    private float totalTaken = 0;
    private float totalAdded = 0;

    public EnvironmentStore(SimEnvironment pSimEnvironment, String storeName) {
        super(0, pSimEnvironment.getModuleName()+ "." + storeName, 0, Float.MAX_VALUE, false);
        mySimEnvironment = pSimEnvironment;
    }

    public float getPressure() {
        return calculatePressure(getCurrentLevel());
    }

    public float getInitialPressure() {
        return calculatePressure(getInitialLevel(), mySimEnvironment.getInitialVolume());
    }

    private float calculatePressure(float pNumberOfMoles) {
        return calculatePressure(pNumberOfMoles, mySimEnvironment.getCurrentVolume());
    }

    private float calculatePressure(float pNumberOfMoles, float volume) {
        float temperature = mySimEnvironment.getTemperatureInKelvin();
        if (volume > 0)
            return (pNumberOfMoles * idealGasConstant * temperature)
                    / volume;
        return 0;
    }

    protected void performLeak(float pLeakRate) {
        float afterLeakAmount = getCurrentLevel() - (getCurrentLevel() * pLeakRate);
        setCurrentLevel(afterLeakAmount);
    }

    public float take(float pAmount) {
        float taken = super.take(pAmount);
        totalTaken += taken;
        return taken;
    }

    public float add(float pAmount) {
        float added = super.add(pAmount);
        totalAdded += added;
        return added;
    }

    public void reset() {
        super.reset();
        totalTaken = 0;
        totalAdded = 0;
    }

    public void tick() {
        super.tick();
    }

}
