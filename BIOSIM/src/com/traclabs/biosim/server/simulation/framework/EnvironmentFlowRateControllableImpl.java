package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.EnvironmentFlowRateControllableOperations;

/**
 * @author Scott Bell
 */

public abstract class EnvironmentFlowRateControllableImpl extends SingleFlowRateControllableImpl implements EnvironmentFlowRateControllableOperations {
    private SimEnvironment[] mySimEnvironments;
    
    public SimEnvironment[] getEnvironments() {
       return mySimEnvironments;
    }
    
    protected void setEnvironments(SimEnvironment[] pSimEnvironments){
        mySimEnvironments = pSimEnvironments;
    }
    
}