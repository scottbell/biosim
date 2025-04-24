package com.traclabs.biosim.server.sensor.framework;

/**
 * Enum representing the possible monitoring results for a sensor alarm.
 */
public enum MonitoringResult {
    NULL,       // No alarm specification applies
    NOMINAL,  // Value is within limits
    WATCH,      // Value exceeds watch threshold
    WARNING,    // Value exceeds warning threshold
    DISTRESS,   // Value exceeds distress threshold
    CRITICAL,   // Value exceeds critical threshold
    SEVERE      // Value exceeds severe threshold
}
