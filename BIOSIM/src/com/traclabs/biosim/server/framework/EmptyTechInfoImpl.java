package com.traclabs.biosim.server.framework;

import com.traclabs.biosim.idl.framework.TechSpecificInfoPOA;

public class EmptyTechInfoImpl extends TechSpecificInfoPOA {

    public EmptyTechInfoImpl() {
    }

    public String print() {
        return "Nothing";
    }
}