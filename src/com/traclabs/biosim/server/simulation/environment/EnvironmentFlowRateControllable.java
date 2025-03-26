package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.SingleFlowRateControllable;

/**
 * @author Scott Bell
 */

public abstract class EnvironmentFlowRateControllable extends
        SingleFlowRateControllable  {
    private SimEnvironment[] mySimEnvironments = new SimEnvironment[0];
    private SimEnvironment[] myInitialSimEnvironments = new SimEnvironment[0];
    
    public EnvironmentFlowRateControllable(BioModule pModule){
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
    		if (environment.equals(pSimEnvironment))
    			return true;
    	}
    	return false;
    }
    
    public void reset(){
    	super.reset();
    	System.arraycopy(myInitialSimEnvironments, 0, mySimEnvironments, 0, myInitialSimEnvironments.length);
    }
}