package biosim.server.power;

import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.server.framework.*;

public class PowerPSImpl extends BioModuleImpl implements PowerPSOperations {
	private float currentPowerProduced = 0f;
	private boolean hasCollectedReferences = false;
	private PowerStore myPowerStore;

	public void tick(){
		currentPowerProduced = 700;
		collectReferences();
		myPowerStore.add(currentPowerProduced);
	}
	
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}
	
	public  float getPowerProduced(){
		return currentPowerProduced;
	}
	
	public String getModuleName(){
		return "PowerPS";
	}
}
