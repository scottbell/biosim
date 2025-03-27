package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.framework.InfluentValve;

public class InfluentValveActuator extends GenericActuator  {
	
    private InfluentValve myInfluentValve;
    
    public InfluentValveActuator(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
    	int index = (int)myValue;
    	myInfluentValve.setIndexOfInfluentStore(index);
    }
    
    public float getMax() {
        return Integer.MAX_VALUE;
    }

    public void setOutput(InfluentValve source) {
    	myInfluentValve = source;
        myValue = source.getIndexOfInfluentStore();
    }

    public InfluentValve getOutput() {
        return myInfluentValve;
    }

	public IBioModule getOutputModule() {
		return myInfluentValve;
	}
}