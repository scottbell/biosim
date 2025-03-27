package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.server.framework.IBioModule;
import com.traclabs.biosim.server.simulation.framework.EffluentValve;

public class EffluentValveActuator extends GenericActuator {
	
    private EffluentValve myEffluentValve;
    
    public EffluentValveActuator(int pID, String pName) {
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

	public IBioModule getOutputModule() {
		return myEffluentValve;
	}
}