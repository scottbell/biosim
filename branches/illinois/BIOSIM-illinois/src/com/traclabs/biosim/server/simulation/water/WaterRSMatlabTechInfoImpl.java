package com.traclabs.biosim.server.simulation.water;

import com.traclabs.biosim.idl.framework.TechSpecificInfoPOA;

public class WaterRSMatlabTechInfoImpl extends TechSpecificInfoPOA {
    String dummyString;

    Object dummyObject;

    public WaterRSMatlabTechInfoImpl() {
        dummyString = "Matlab waterRS specific information";
        dummyObject = "am i a string? yes";
    }

    public String print() {
        return dummyString;
    }

    public void changeString(String changeUp) {
        dummyString = changeUp;
    }
}