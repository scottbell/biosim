package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.idl.actuator.framework.EffluentValveActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.simulation.framework.EffluentValve;

public class EffluentValveActuatorImpl extends GenericActuatorImpl implements EffluentValveActuatorOperations {
	
    private EffluentValve myEffluentValve;
    
    public EffluentValveActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected void processData() {
    	int index = (int)myValue;
    	myEffluentValve.setIndexOfEffluentStore(index);
    }
    
    public float getMax() {
        return Integer.MAX_VALUE;
    }

    public void setOutput(EffluentValve source) {
    	myEffluentValve = source;
        myValue = source.getIndexOfEffluentStore();
    }

    public EffluentValve getOutput() {
        return myEffluentValve;
    }

	public BioModule getOutputModule() {
		return myEffluentValve;
	}
}