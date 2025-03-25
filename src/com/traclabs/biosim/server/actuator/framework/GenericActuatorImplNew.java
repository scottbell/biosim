package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.api.BioModule;
import com.traclabs.biosim.server.framework.BioModuleNew;

/**
 * Base class for all actuator implementations.
 */
public abstract class GenericActuatorNew extends BioModuleNew {
    protected float myValue;
    protected boolean newValueSet = false;

    /**
     * Constructor
     * 
     * @param pID The ID of the actuator
     * @param pName The name of the actuator
     */
    public GenericActuatorNew(long pID, String pName) {
        super(pID, pName);
    }
    
    /**
     * Notify listeners of a value change
     */
    protected void notifyListeners() {
        // Override in subclasses if needed
    }

    /**
     * Set the value of the actuator
     * 
     * @param pValue The value to set
     */
    public void setValue(float pValue) {
        if (myValue > getMin())
            myValue = Math.min(pValue, getMax());
        else
            myValue = getMin();
        myValue = pValue;
        newValueSet = true;
    }

    /**
     * Get the maximum value of the actuator
     * 
     * @return The maximum value
     */
    public abstract float getMax();

    /**
     * Get the minimum value of the actuator
     * 
     * @return The minimum value
     */
    public float getMin() {
        return 0f;
    }

    /**
     * Get the current value of the actuator
     * 
     * @return The current value
     */
    public float getValue() {
        return myValue;
    }

    /**
     * Called at every tick of the simulation
     */
    @Override
    public void tick() {
        super.tick();
        if (newValueSet) {
            processData();
            notifyListeners();
            newValueSet = false;
        }
    }

    /**
     * Process the data (apply the actuator value to the target)
     */
    protected abstract void processData();

    /**
     * Get the module that this actuator controls
     * 
     * @return The output module
     */
    public abstract BioModule getOutputModule();

    /**
     * Log the actuator state
     */
    @Override
    public void log() {
        myLogger.debug(getModuleName() + ":\toutput=" + getValue());
    }
}
