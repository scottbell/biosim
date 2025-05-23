package com.traclabs.biosim.server.simulation.water;

public final class WaterRSOperationMode {
    public static final int _FULL = 0;
    public static final WaterRSOperationMode FULL = new WaterRSOperationMode(_FULL);
    public static final int _OFF = 1;
    public static final WaterRSOperationMode OFF = new WaterRSOperationMode(_OFF);
    public static final int _GREY_WATER_ONLY = 2;
    public static final WaterRSOperationMode GREY_WATER_ONLY = new WaterRSOperationMode(_GREY_WATER_ONLY);
    public static final int _PARTIAL = 3;
    public static final WaterRSOperationMode PARTIAL = new WaterRSOperationMode(_PARTIAL);
    private int value = -1;

    private WaterRSOperationMode(int i) {
        value = i;
    }

    public static WaterRSOperationMode from_int(int value) {
        switch (value) {
            case _FULL:
                return FULL;
            case _OFF:
                return OFF;
            case _GREY_WATER_ONLY:
                return GREY_WATER_ONLY;
            case _PARTIAL:
                return PARTIAL;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _FULL:
                return "FULL";
            case _OFF:
                return "OFF";
            case _GREY_WATER_ONLY:
                return "GREY_WATER_ONLY";
            case _PARTIAL:
                return "PARTIAL";
            default:
                throw new IllegalArgumentException();
        }
    }
}
