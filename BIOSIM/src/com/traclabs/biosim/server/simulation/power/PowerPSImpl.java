/**
 * The Power Production System creates power from a generator (say a solar panel) and stores it in the power store.
 * This provides power to all the biomodules in the system.
 *
 * @author    Scott Bell
 */

package biosim.server.power;

import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.server.framework.*;

public class PowerPSImpl extends BioModuleImpl implements PowerPSOperations {
	//The power produced (in watts) by the Power PS at the current tick
	private float currentPowerProduced = 350f;
	//Flag switched when the Power PS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//References to the PowerStore the Power PS takes/puts power into
	private PowerStore myPowerStore;
	
	/**
	* When ticked, the Food Processor
	* 1) attempts to collect references to various server (if not already done)
	* 4) creates power and places it into the power store
	*/
	public void tick(){
		collectReferences();
		myPowerStore.add(currentPowerProduced);
	}
	
	/**
	* Reset does nothing right now
	*/
	public void reset(){
	}
	
	/**
	* Collects reference to PowerStore needed for putting/getting power.
	*/
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
	
	/**
	* Returns the power produced (in watts) by the Power PS during the current tick
	* @return the power produced (in watts) by the Power PS during the current tick
	*/
	public  float getPowerProduced(){
		return currentPowerProduced;
	}
	
	/**
	* Returns the name of this module (PowerPS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerPS";
	}
}
