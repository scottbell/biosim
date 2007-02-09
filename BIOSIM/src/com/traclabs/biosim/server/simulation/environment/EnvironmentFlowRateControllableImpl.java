package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.environment.EnvironmentFlowRateControllableOperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.SingleFlowRateControllableImpl;

/**
 * @author Scott Bell
 */

public abstract class EnvironmentFlowRateControllableImpl extends
        SingleFlowRateControllableImpl implements
        EnvironmentFlowRateControllableOperations {
    private SimEnvironment[] mySimEnvironments = new SimEnvironment[0];
    private SimEnvironment[] myInitialSimEnvironments = new SimEnvironment[0];
    
    public EnvironmentFlowRateControllableImpl(BioModuleImpl pModule){
    	super(pModule);
    }

    public SimEnvironment[] getEnvironments() {
        return mySimEnvironments;
    }

    protected void setInitialEnvironments(SimEnvironment[] pSimEnvironments) {
    	myInitialSimEnvironments = pSimEnvironments;
    	mySimEnvironments = new SimEnvironment[myInitialSimEnvironments.length];
        System.arraycopy(myInitialSimEnvironments, 0, mySimEnvironments, 0, myInitialSimEnvironments.length);
        float[] emptyActualFlowRates = new float[pSimEnvironments.length];
        setInitialActualFlowRates(emptyActualFlowRates);
    }
    
    protected void setEnvironment(SimEnvironment pSimEnvironment, int index) {
    	mySimEnvironments[index] = pSimEnvironment;
    }
    
    public boolean connectsTo(SimEnvironment pSimEnvironment){
    	for (SimEnvironment environment : mySimEnvironments){
    		if (environment._is_equivalent(pSimEnvironment))
    			return true;
    	}
    	return false;
    }
    
    public void reset(){
    	super.reset();
    	System.arraycopy(myInitialSimEnvironments, 0, mySimEnvironments, 0, myInitialSimEnvironments.length);
    }
}