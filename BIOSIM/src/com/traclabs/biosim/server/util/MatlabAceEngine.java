package com.traclabs.biosim.server.util;

public class MatlabAceEngine extends Engine {
    public void open() {
    }

    public void put(double[] inputVector) {
        myLogger.debug("matlabAceEngine::put");
    }

    public double[] get() {
        myLogger.debug("matlabAceEngine::get");
        double[] out = null;
        return out;
    }

    public void close() {
    }
}