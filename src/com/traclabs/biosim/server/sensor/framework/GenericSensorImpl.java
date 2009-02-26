package com.traclabs.biosim.server.sensor.framework;

import com.tietronix.bionet.util.Node;
import com.tietronix.bionet.util.Timeval;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.util.BionetUtils;
import com.traclabs.biosim.server.util.MathUtils;

public abstract class GenericSensorImpl extends BioModuleImpl implements
        GenericSensorOperations {
    protected float myValue;
    private Node myBionetNode;

    public GenericSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected void notifyListeners(){
    	if (isBionetEnabled()){
    		myBionetNode.setResourceValue(BionetUtils.RESOURCE_ID, Float.valueOf(getValue()), new Timeval());
    		BionetUtils.getBionetUtils().reportResourceUpdate(myBionetNode);
    	}
    }

    public float getValue() {
        return myValue;
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }

    public void tick() {
        gatherData();
        super.tick();
        notifyListeners();
    }

    public abstract BioModule getInputModule();

    public void log() {
        myLogger.debug("value=" + getValue());
    }
    
    public void setBionetEnabled(boolean enabled){
		super.setBionetEnabled(enabled);
		if (isBionetEnabled())
			myBionetNode = BionetUtils.getBionetUtils().registerSensor(this);
	}
    
    protected void performMalfunctions() {
    	if (!myMalfunctions.isEmpty()){
            Double noisyValue = MathUtils.gaussian(myValue, 100);
    		myValue = noisyValue.floatValue();
    	}
    }
}