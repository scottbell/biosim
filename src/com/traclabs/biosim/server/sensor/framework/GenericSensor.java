package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.util.MathUtils;

/**
 * Base class for all sensors.  It now supports independent LOW and HIGH alarm
 * bands for each severity level (watch, warning, distress, critical, severe).
 *
 * A “band” is defined by an inclusive [min .. max] range.  If the sensor value
 * falls inside that range, the corresponding MonitoringResult (e.g. WATCH_LOW,
 * CRITICAL_HIGH, …) is produced.
 *
 * Configuration happens via {@code <alarms>} element in the XML initialisation
 * files using tags such as <watch_low min="0.1" max="0.2"/>,
 * <critical_high min="0.5" max="1.0"/>, etc.  See SensorInitializer.
 *
 * Only LOW/HIGH bands that are explicitly set are enabled and therefore
 * evaluated.  areAlarmRangesValid() ensures min ≤ max on every enabled band.
 */
public abstract class GenericSensor extends BioModule {
    // Current sensor reading
    protected float myValue;

    // Current monitoring status
    private MonitoringResult myMonitoringResult = MonitoringResult.NOMINAL;

    /* ---------------------------------------------------------------------- */
    /*  Alarm band data structures                                            */
    /* ---------------------------------------------------------------------- */
    private static class Band {
        float min  = 0f;
        float max  = 0f;
        boolean enabled = false;

        boolean inRange(float value) {
            return enabled && value >= min && value <= max;
        }

        void set(float pMin, float pMax) {
            min = pMin;
            max = pMax;
            enabled = true;
        }

        boolean isValid() {
            return !enabled || min <= max;
        }
    }

    // Five severity levels × LOW/HIGH  => 10 bands
    private final Band watchLow     = new Band();
    private final Band watchHigh    = new Band();
    private final Band warningLow   = new Band();
    private final Band warningHigh  = new Band();
    private final Band distressLow  = new Band();
    private final Band distressHigh = new Band();
    private final Band criticalLow  = new Band();
    private final Band criticalHigh = new Band();
    private final Band severeLow    = new Band();
    private final Band severeHigh   = new Band();

    public GenericSensor(int pID, String pName) {
        super(pID, pName);
    }

    /* ---------------------------------------------------------------------- */
    /*  Abstract hooks                                                        */
    /* ---------------------------------------------------------------------- */
    protected abstract void gatherData();
    public abstract float getMax();
    public float getMin() { return 0f; }
    public abstract IBioModule getInputModule();

    /* ---------------------------------------------------------------------- */
    /*  Basic accessors                                                       */
    /* ---------------------------------------------------------------------- */
    public float getValue() {
        return myValue;
    }

    public MonitoringResult getMonitoringResult() {
        return myMonitoringResult;
    }

    /* ---------------------------------------------------------------------- */
    /*  Alarm-band setters / getters                                          */
    /* ---------------------------------------------------------------------- */
    // WATCH
    public void setWatchLowMin(float v)  { watchLow.min = v;  watchLow.enabled = true; }
    public void setWatchLowMax(float v)  { watchLow.max = v;  watchLow.enabled = true; }
    public void setWatchHighMin(float v) { watchHigh.min = v; watchHigh.enabled = true; }
    public void setWatchHighMax(float v) { watchHigh.max = v; watchHigh.enabled = true; }

    // WARNING
    public void setWarningLowMin(float v)  { warningLow.min = v;  warningLow.enabled = true; }
    public void setWarningLowMax(float v)  { warningLow.max = v;  warningLow.enabled = true; }
    public void setWarningHighMin(float v) { warningHigh.min = v; warningHigh.enabled = true; }
    public void setWarningHighMax(float v) { warningHigh.max = v; warningHigh.enabled = true; }

    // DISTRESS
    public void setDistressLowMin(float v)  { distressLow.min = v;  distressLow.enabled = true; }
    public void setDistressLowMax(float v)  { distressLow.max = v;  distressLow.enabled = true; }
    public void setDistressHighMin(float v) { distressHigh.min = v; distressHigh.enabled = true; }
    public void setDistressHighMax(float v) { distressHigh.max = v; distressHigh.enabled = true; }

    // CRITICAL
    public void setCriticalLowMin(float v)  { criticalLow.min = v;  criticalLow.enabled = true; }
    public void setCriticalLowMax(float v)  { criticalLow.max = v;  criticalLow.enabled = true; }
    public void setCriticalHighMin(float v) { criticalHigh.min = v; criticalHigh.enabled = true; }
    public void setCriticalHighMax(float v) { criticalHigh.max = v; criticalHigh.enabled = true; }

    // SEVERE
    public void setSevereLowMin(float v)  { severeLow.min = v;  severeLow.enabled = true; }
    public void setSevereLowMax(float v)  { severeLow.max = v;  severeLow.enabled = true; }
    public void setSevereHighMin(float v) { severeHigh.min = v; severeHigh.enabled = true; }
    public void setSevereHighMax(float v) { severeHigh.max = v; severeHigh.enabled = true; }

    /* ---------------------------------------------------------------------- */
    /*  Public getters for alarm-band status                                  */
    /* ---------------------------------------------------------------------- */
    /* WATCH */
    public boolean isWatchLowEnabled()  { return watchLow.enabled; }
    public float   getWatchLowMin()     { return watchLow.min; }
    public float   getWatchLowMax()     { return watchLow.max; }

    public boolean isWatchHighEnabled() { return watchHigh.enabled; }
    public float   getWatchHighMin()    { return watchHigh.min; }
    public float   getWatchHighMax()    { return watchHigh.max; }

    /* WARNING */
    public boolean isWarningLowEnabled()  { return warningLow.enabled; }
    public float   getWarningLowMin()     { return warningLow.min; }
    public float   getWarningLowMax()     { return warningLow.max; }

    public boolean isWarningHighEnabled() { return warningHigh.enabled; }
    public float   getWarningHighMin()    { return warningHigh.min; }
    public float   getWarningHighMax()    { return warningHigh.max; }

    /* DISTRESS */
    public boolean isDistressLowEnabled()  { return distressLow.enabled; }
    public float   getDistressLowMin()     { return distressLow.min; }
    public float   getDistressLowMax()     { return distressLow.max; }

    public boolean isDistressHighEnabled() { return distressHigh.enabled; }
    public float   getDistressHighMin()    { return distressHigh.min; }
    public float   getDistressHighMax()    { return distressHigh.max; }

    /* CRITICAL */
    public boolean isCriticalLowEnabled()  { return criticalLow.enabled; }
    public float   getCriticalLowMin()     { return criticalLow.min; }
    public float   getCriticalLowMax()     { return criticalLow.max; }

    public boolean isCriticalHighEnabled() { return criticalHigh.enabled; }
    public float   getCriticalHighMin()    { return criticalHigh.min; }
    public float   getCriticalHighMax()    { return criticalHigh.max; }

    /* SEVERE */
    public boolean isSevereLowEnabled()  { return severeLow.enabled; }
    public float   getSevereLowMin()     { return severeLow.min; }
    public float   getSevereLowMax()     { return severeLow.max; }

    public boolean isSevereHighEnabled() { return severeHigh.enabled; }
    public float   getSevereHighMin()    { return severeHigh.min; }
    public float   getSevereHighMax()    { return severeHigh.max; }

    /* ---------------------------------------------------------------------- */
    /*  Validation                                                            */
    /* ---------------------------------------------------------------------- */
    /**
     * Detect overlap between the LOW and HIGH bands of the same severity.
     * Equivalent to: low.max >= high.min  (bands touch counts as overlap).
     */
    private boolean bandsOverlap(Band low, Band high) {
        return low.enabled && high.enabled && low.max >= high.min;
    }
    public boolean areAlarmRangesValid() {
        boolean shapeOk =
            watchLow.isValid()   && watchHigh.isValid()   &&
            warningLow.isValid() && warningHigh.isValid() &&
            distressLow.isValid()&& distressHigh.isValid()&&
            criticalLow.isValid()&& criticalHigh.isValid()&&
            severeLow.isValid()  && severeHigh.isValid();

        // Warn (but do not fail) if any low/high pair overlaps
        if (bandsOverlap(watchLow,   watchHigh))   myLogger.warn(getModuleName() + ": WATCH low/high bands overlap");
        if (bandsOverlap(warningLow, warningHigh)) myLogger.warn(getModuleName() + ": WARNING low/high bands overlap");
        if (bandsOverlap(distressLow,distressHigh))myLogger.warn(getModuleName() + ": DISTRESS low/high bands overlap");
        if (bandsOverlap(criticalLow,criticalHigh))myLogger.warn(getModuleName() + ": CRITICAL low/high bands overlap");
        if (bandsOverlap(severeLow,  severeHigh))  myLogger.warn(getModuleName() + ": SEVERE low/high bands overlap");

        return shapeOk;
    }

    /* ---------------------------------------------------------------------- */
    /*  Alarm evaluation                                                      */
    /* ---------------------------------------------------------------------- */
    private void evaluateAlarms() {
        // Highest severity first
        if (severeLow.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.SEVERE_LOW;
        }
        else if (severeHigh.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.SEVERE_HIGH;
        }
        else if (criticalLow.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.CRITICAL_LOW;
        }
        else if (criticalHigh.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.CRITICAL_HIGH;
        }
        else if (distressLow.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.DISTRESS_LOW;
        }
        else if (distressHigh.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.DISTRESS_HIGH;
        }
        else if (warningLow.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.WARNING_LOW;
        }
        else if (warningHigh.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.WARNING_HIGH;
        }
        else if (watchLow.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.WATCH_LOW;
        }
        else if (watchHigh.inRange(myValue)) {
            myMonitoringResult = MonitoringResult.WATCH_HIGH;
        }
        else {
            myMonitoringResult = MonitoringResult.NOMINAL;
        }
    }

    /* ---------------------------------------------------------------------- */
    /*  Lifecycle                                                             */
    /* ---------------------------------------------------------------------- */
    public void tick() {
        gatherData();
        super.tick();
        evaluateAlarms();
        notifyListeners();
    }

    /* ---------------------------------------------------------------------- */
    /*  Misc utilities                                                        */
    /* ---------------------------------------------------------------------- */
    protected void notifyListeners() { /* default: subclasses override */ }

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
