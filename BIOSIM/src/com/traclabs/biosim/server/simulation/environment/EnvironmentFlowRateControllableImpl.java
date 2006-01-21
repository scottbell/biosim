package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.framework.SingleFlowRateControllableImpl;

/**
 * @author Scott Bell
 */

public abstract class EnvironmentFlowRateControllableImpl extends
        SingleFlowRateControllableImpl implements
        EnvironmentFlowRateControllableOperations {
    private SimEnvironment[] mySimEnvironments;
    
    public EnvironmentFlowRateControllableImpl(){
        mySimEnvironments = new SimEnvironment[0];
    }

    public SimEnvironment[] getEnvironments() {
        return mySimEnvironments;
    }

    protected void setEnvironments(SimEnvironment[] pSimEnvironments) {
        mySimEnvironments = pSimEnvironments;
        float[] emptyActualFlowRates = new float[pSimEnvironments.length];
        setActualFlowRates(emptyActualFlowRates);
    }
    
    public boolean connectsTo(SimEnvironment pSimEnvironment){
    	for (SimEnvironment environment : mySimEnvironments){
    		if (environment._is_equivalent(pSimEnvironment))
    			return true;
    	}
    	return false;
    }
}