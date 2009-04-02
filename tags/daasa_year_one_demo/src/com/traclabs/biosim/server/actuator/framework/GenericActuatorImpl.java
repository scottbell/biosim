package com.traclabs.biosim.server.actuator.framework;

import com.tietronix.bionet.util.Node;
import com.tietronix.bionet.util.Timeval;
import com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.util.BionetUtils;

public abstract class GenericActuatorImpl extends BioModuleImpl implements
        GenericActuatorOperations {
    protected float myValue;

    protected boolean newValueSet = false;
    
    private Node myBionetNode;

    public GenericActuatorImpl(int pID, String pName) {
        super(pID, pName);
    }
    
    protected void notifyListeners(){
    	if (isBionetEnabled())
    		myBionetNode.setResourceValue(BionetUtils.RESOURCE_ID, Float.valueOf(getValue()), new Timeval());
    }

    public void setValue(float pValue) {
        if (myValue > getMin())
            myValue = Math.min(pValue, getMax());
        else
            myValue = getMin();
        myValue = pValue;
        newValueSet = true;
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }

    public float getValue() {
        return myValue;
    }

    public void tick() {
        super.tick();
        if (newValueSet) {
            processData();
            notifyListeners();
            newValueSet = false;
        }
    }

    protected abstract void processData();

    public abstract BioModule getOutputModule();

    public void log() {
        myLogger.debug(getModuleName() + ":\toutput=" + getValue());
    }
    
    @Override
    public void bionetCallBack(String value) {
    	try {
    		float newValue = Float.parseFloat(value);
    		setValue(newValue);
    	}
    	catch (NumberFormatException e){
    		myLogger.warn("Attempted to set actuator to a non-float value", e);
    	}
	}
    
    public void setBionetEnabled(boolean enabled){
		super.setBionetEnabled(enabled);
		if (isBionetEnabled())
			myBionetNode = BionetUtils.getBionetUtils().registerActuator(this);
	}
}