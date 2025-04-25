package com.traclabs.biosim.server.sensor.framework;

/**
 * Enum representing the possible monitoring results for a sensor alarm.
 */
public enum MonitoringResult {
    NULL,       // No alarm specification applies
    NOMINAL,    // Value is within normal operating range
    WATCH,      // Value is outside watch range
    WARNING,    // Value is outside warning range
    DISTRESS,   // Value is outside distress range
    CRITICAL,   // Value is outside critical range
    SEVERE      // Value is outside severe range
}
