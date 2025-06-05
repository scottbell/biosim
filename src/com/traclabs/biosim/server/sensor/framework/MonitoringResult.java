package com.traclabs.biosim.server.sensor.framework;

/**
 * Enum representing the possible monitoring results for a sensor alarm.
 *
 * Conventions:
 *   • *_LOW   – the sensor value falls in the low-side band for that severity.
 *   • *_HIGH  – the sensor value falls in the high-side band for that severity.
 *   • NOMINAL – the value is within the normal operating range.
 *   • NULL    – no alarm specification applies (should rarely be used).
 *
 * The bands are configured per-sensor via XML (<alarms> element) with
 * watch_low, watch_high, warning_low, warning_high, etc.
 */
public enum MonitoringResult {
    NOMINAL,        // Value is within normal operating range

    WATCH_LOW,      // Value is in the watch-low band
    WATCH_HIGH,     // Value is in the watch-high band

    WARNING_LOW,    // Value is in the warning-low band
    WARNING_HIGH,   // Value is in the warning-high band

    DISTRESS_LOW,   // Value is in the distress-low band
    DISTRESS_HIGH,  // Value is in the distress-high band

    CRITICAL_LOW,   // Value is in the critical-low band
    CRITICAL_HIGH,  // Value is in the critical-high band

    SEVERE_LOW,     // Value is in the severe-low band
    SEVERE_HIGH,    // Value is in the severe-high band

    NULL            // No alarm specification applies
}
