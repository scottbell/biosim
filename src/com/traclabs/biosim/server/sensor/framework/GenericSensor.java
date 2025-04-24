package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.util.MathUtils;

public abstract class GenericSensor extends BioModule {
    protected float myValue;
    private MonitoringResult myMonitoringResult = MonitoringResult.NOMINAL;
    private float myNormalizedMonitoringValue = 0.0f;
    
    // Alarm thresholds as percentages of the range between min and max
    private float myWatchThreshold = 0.8f;
    private float myWarningThreshold = 0.85f;
    private float myDistressThreshold = 0.9f;
    private float myCriticalThreshold = 0.95f;
    private float mySevereThreshold = 0.99f;

    public GenericSensor(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected void notifyListeners() {
    }

    public float getValue() {
        return myValue;
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }
    
    /**
     * Gets the current monitoring result for this sensor.
     * 
     * @return The current monitoring result
     */
    public MonitoringResult getMonitoringResult() {
        return myMonitoringResult;
    }
    
    /**
     * Gets the normalized monitoring value, which is a percentage of the range between min and max.
     * 
     * @return The normalized monitoring value (0.0 to 1.0)
     *         or NaN if the range is zero or negative
     */
    public float getNormalizedMonitoringValue() {
        return myNormalizedMonitoringValue;
    }
    
    /**
     * Sets the watch threshold as a percentage of the range between min and max.
     * 
     * @param threshold The threshold value (0.0 to 1.0)
     */
    public void setWatchThreshold(float threshold) {
        myWatchThreshold = threshold;
    }
    
    /**
     * Sets the warning threshold as a percentage of the range between min and max.
     * 
     * @param threshold The threshold value (0.0 to 1.0)
     */
    public void setWarningThreshold(float threshold) {
        myWarningThreshold = threshold;
    }
    
    /**
     * Sets the distress threshold as a percentage of the range between min and max.
     * 
     * @param threshold The threshold value (0.0 to 1.0)
     */
    public void setDistressThreshold(float threshold) {
        myDistressThreshold = threshold;
    }
    
    /**
     * Sets the critical threshold as a percentage of the range between min and max.
     * 
     * @param threshold The threshold value (0.0 to 1.0)
     */
    public void setCriticalThreshold(float threshold) {
        myCriticalThreshold = threshold;
    }
    
    /**
     * Sets the severe threshold as a percentage of the range between min and max.
     * 
     * @param threshold The threshold value (0.0 to 1.0)
     */
    public void setSevereThreshold(float threshold) {
        mySevereThreshold = threshold;
    }
    
    /**
     * Gets the watch threshold.
     * 
     * @return The watch threshold value
     */
    public float getWatchThreshold() {
        return myWatchThreshold;
    }
    
    /**
     * Gets the warning threshold.
     * 
     * @return The warning threshold value
     */
    public float getWarningThreshold() {
        return myWarningThreshold;
    }
    
    /**
     * Gets the distress threshold.
     * 
     * @return The distress threshold value
     */
    public float getDistressThreshold() {
        return myDistressThreshold;
    }
    
    /**
     * Gets the critical threshold.
     * 
     * @return The critical threshold value
     */
    public float getCriticalThreshold() {
        return myCriticalThreshold;
    }
    
    /**
     * Gets the severe threshold.
     * 
     * @return The severe threshold value
     */
    public float getSevereThreshold() {
        return mySevereThreshold;
    }
    
    /**
     * Evaluates the current value against thresholds and updates the monitoring result.
     */
    protected void evaluateAlarmsAndRange() {
        float min = getMin();
        float max = getMax();
        float range = max - min;
        
        if (range <= 0) {
            myMonitoringResult = MonitoringResult.NULL;
            return;
        }
        
        // Calculate the normalized position of the current value in the range
        myNormalizedMonitoringValue = (myValue - min) / range;
        
        // Determine the monitoring result based on thresholds
        if (myNormalizedMonitoringValue >= mySevereThreshold) {
            myMonitoringResult = MonitoringResult.SEVERE;
        } else if (myNormalizedMonitoringValue >= myCriticalThreshold) {
            myMonitoringResult = MonitoringResult.CRITICAL;
        } else if (myNormalizedMonitoringValue >= myDistressThreshold) {
            myMonitoringResult = MonitoringResult.DISTRESS;
        } else if (myNormalizedMonitoringValue >= myWarningThreshold) {
            myMonitoringResult = MonitoringResult.WARNING;
        } else if (myNormalizedMonitoringValue >= myWatchThreshold) {
            myMonitoringResult = MonitoringResult.WATCH;
        } else {
            myMonitoringResult = MonitoringResult.NOMINAL;
        }
    }

    public void tick() {
        gatherData();
        super.tick();
        evaluateAlarmsAndRange();
        notifyListeners();
    }

    public abstract IBioModule getInputModule();

    public void log() {
        myLogger.debug("value=" + getValue() + ", monitoringResult=" + getMonitoringResult());
    }

    protected void performMalfunctions() {
        if (!myMalfunctions.isEmpty()) {
            Double noisyValue = MathUtils.gaussian(myValue, 100);
            myValue = noisyValue.floatValue();
        }
    }
}
