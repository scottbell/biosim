package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.idl.actuator.framework.InfluentValveActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.InfluentValve;

public class InfluentValveActuatorImpl extends GenericActuatorImpl implements InfluentValveActuatorOperations {
	
    private InfluentValve myInfluentValve;
    
    public InfluentValveActuatorImpl(int pID, String pName) {
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

	public BioModule getOutputModule() {
		return myInfluentValve;
	}
}