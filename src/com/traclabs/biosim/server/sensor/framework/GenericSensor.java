package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.util.MathUtils;

public abstract class GenericSensor extends BioModule {
    protected float myValue;
    private MonitoringResult myMonitoringResult = MonitoringResult.NOMINAL;

    // Alarm range boundaries
    private float myWatchMin = 0f;
    private float myWatchMax = 0f;
    private float myWarningMin = 0f;
    private float myWarningMax = 0f;
    private float myDistressMin = 0f;
    private float myDistressMax = 0f;
    private float myCriticalMin = 0f;
    private float myCriticalMax = 0f;
    private float mySevereMin = 0f;
    private float mySevereMax = 0f;

    // Flags to track which alarm ranges are enabled
    private boolean myWatchEnabled = false;
    private boolean myWarningEnabled = false;
    private boolean myDistressEnabled = false;
    private boolean myCriticalEnabled = false;
    private boolean mySevereEnabled = false;

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

    // Watch range getters and setters
    public float getWatchMin() {
        return myWatchMin;
    }

    public void setWatchMin(float min) {
        myWatchMin = min;
        myWatchEnabled = true;
    }

    public float getWatchMax() {
        return myWatchMax;
    }

    public void setWatchMax(float max) {
        myWatchMax = max;
        myWatchEnabled = true;
    }

    public boolean isWatchEnabled() {
        return myWatchEnabled;
    }

    public void setWatchEnabled(boolean enabled) {
        myWatchEnabled = enabled;
    }

    // Warning range getters and setters
    public float getWarningMin() {
        return myWarningMin;
    }

    public void setWarningMin(float min) {
        myWarningMin = min;
        myWarningEnabled = true;
    }

    public float getWarningMax() {
        return myWarningMax;
    }

    public void setWarningMax(float max) {
        myWarningMax = max;
        myWarningEnabled = true;
    }

    public boolean isWarningEnabled() {
        return myWarningEnabled;
    }

    public void setWarningEnabled(boolean enabled) {
        myWarningEnabled = enabled;
    }

    // Distress range getters and setters
    public float getDistressMin() {
        return myDistressMin;
    }

    public void setDistressMin(float min) {
        myDistressMin = min;
        myDistressEnabled = true;
    }

    public float getDistressMax() {
        return myDistressMax;
    }

    public void setDistressMax(float max) {
        myDistressMax = max;
        myDistressEnabled = true;
    }

    public boolean isDistressEnabled() {
        return myDistressEnabled;
    }

    public void setDistressEnabled(boolean enabled) {
        myDistressEnabled = enabled;
    }

    // Critical range getters and setters
    public float getCriticalMin() {
        return myCriticalMin;
    }

    public void setCriticalMin(float min) {
        myCriticalMin = min;
        myCriticalEnabled = true;
    }

    public float getCriticalMax() {
        return myCriticalMax;
    }

    public void setCriticalMax(float max) {
        myCriticalMax = max;
        myCriticalEnabled = true;
    }

    public boolean isCriticalEnabled() {
        return myCriticalEnabled;
    }

    public void setCriticalEnabled(boolean enabled) {
        myCriticalEnabled = enabled;
    }

    // Severe range getters and setters
    public float getSevereMin() {
        return mySevereMin;
    }

    public void setSevereMin(float min) {
        mySevereMin = min;
        mySevereEnabled = true;
    }

    public float getSevereMax() {
        return mySevereMax;
    }

    public void setSevereMax(float max) {
        mySevereMax = max;
        mySevereEnabled = true;
    }

    public boolean isSevereEnabled() {
        return mySevereEnabled;
    }

    public void setSevereEnabled(boolean enabled) {
        mySevereEnabled = enabled;
    }

    public boolean areAlarmRangesValid() {
        if (myWatchEnabled && myWatchMin > myWatchMax) {
            return false;
        }
        if (myWarningEnabled && myWarningMin > myWarningMax) {
            return false;
        }
        if (myDistressEnabled && myDistressMin > myDistressMax) {
            return false;
        }
        if (myCriticalEnabled && myCriticalMin > myCriticalMax) {
            return false;
        }
        return !mySevereEnabled || !(mySevereMin > mySevereMax);
    }

    /**
     * Evaluates the current value against range boundaries and updates the monitoring result.
     */
    protected void evaluateAlarms() {
        // Check if value falls within the severe alarm range
        if (mySevereEnabled && (myValue >= mySevereMin && myValue <= mySevereMax)) {
            myMonitoringResult = MonitoringResult.SEVERE;
        }
        // Check if value falls within the critical alarm range
        else if (myCriticalEnabled && (myValue >= myCriticalMin && myValue <= myCriticalMax)) {
            myMonitoringResult = MonitoringResult.CRITICAL;
        }
        // Check if value falls within the distress alarm range
        else if (myDistressEnabled && (myValue >= myDistressMin && myValue <= myDistressMax)) {
            myMonitoringResult = MonitoringResult.DISTRESS;
        }
        // Check if value falls within the warning alarm range
        else if (myWarningEnabled && (myValue >= myWarningMin && myValue <= myWarningMax)) {
            myMonitoringResult = MonitoringResult.WARNING;
        }
        // Check if value falls within the watch alarm range
        else if (myWatchEnabled && (myValue >= myWatchMin && myValue <= myWatchMax)) {
            myMonitoringResult = MonitoringResult.WATCH;
        }
        // Otherwise, flag as nominal
        else {
            myMonitoringResult = MonitoringResult.NOMINAL;
        }
    }

    public void tick() {
        gatherData();
        super.tick();
        evaluateAlarms();
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
