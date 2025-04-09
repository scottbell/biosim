package com.traclabs.biosim.server.simulation.crew;

public final class Sex {
    public static final int _male = 0;
    public static final Sex male = new Sex(_male);
    public static final int _female = 1;
    public static final Sex female = new Sex(_female);
    private int value = -1;

    public Sex(int i) {
        value = i;
    }

    public static Sex from_int(int value) {
        return switch (value) {
            case _male -> male;
            case _female -> female;
            default -> throw new IllegalArgumentException();
        };
    }

    public int value() {
        return value;
    }

    public String toString() {
        return switch (value) {
            case _male -> "male";
            case _female -> "female";
            default -> throw new IllegalArgumentException();
        };
    }
}
