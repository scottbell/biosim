package com.traclabs.biosim.server.sensor.framework;

import com.tietronix.bionet.util.Node;
import com.tietronix.bionet.util.Timeval;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.util.BionetUtils;

public abstract class GenericSensorImpl extends BioModuleImpl implements
        GenericSensorOperations {
    protected float myValue;
    private Node myBionetNode;

    public GenericSensorImpl(int pID, String pName) {
        super(pID, pName);
    }

    protected abstract void gatherData();

    protected void notifyListeners(){
    	if (isBionetEnabled())
    		myBionetNode.setResourceValue(BionetUtils.RESOURCE_ID, Double.valueOf(4.0f), new Timeval());
    }

    public float getValue() {
        return myValue;
    }

    public abstract float getMax();

    public float getMin() {
        return 0f;
    }

    public void tick() {
        super.tick();
        try {
            gatherData();
            notifyListeners();
        } catch (Exception e) {
            myLogger.error(getModuleName() + " had an exception: " + e);
            e.printStackTrace();
        }
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
}