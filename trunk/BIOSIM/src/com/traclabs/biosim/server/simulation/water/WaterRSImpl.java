/**
 * The Water Recovery System takes grey/dirty water and refines it to potable water for the crew members and grey water for the crops.
 *
 * @author    Scott Bell
 */

package biosim.server.water;

import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.server.framework.*;

public class WaterRSImpl extends BioModuleImpl implements WaterRSOperations {
	//The potable water produced (in liters) by the Water RS at the current tick
	private float currentPotableWaterProduced = 0f;
	//The grey water produced (in liters) by the Water RS at the current tick
	private float currentGreyWaterProduced = 0f;
	//The power consumed (in watts) by the Water RS at the current tick
	private float currentPowerConsumed = 0f;
	//The dirty water consumed (in liters) by the Water RS at the current tick
	private float currentDirtyWaterConsumed = 0f;
	//The grey water consumed (in liters) by the Water RS at the current tick
	private float currentGreyWaterConsumed = 0f;
	//Flag switched when the Water RS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//The various subsystems of Water RS that clean the water
	private BWP myBWP;
	private RO myRO;
	private AES myAES;
	private PPS myPPS;
	//References to the servers the Water RS takes/puts resources (power and water)
	private PowerStore myPowerStore;
	private PotableWaterStore myPotableWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private GreyWaterStore myGreyWaterStore;
	
	/**
	* Creates the Water RS and it's subsystems
	*/
	public WaterRSImpl(){
		myBWP = new BWP(this);
		myRO = new RO(this);
		myAES = new AES(this);
		myPPS = new PPS(this);
	}
	
	/**
	* Resets production/consumption levels and resets all the subsystems
	*/
	public void reset(){
		currentPotableWaterProduced = 0f;
		currentGreyWaterProduced = 0f;
		currentPowerConsumed = 0f;
		currentDirtyWaterConsumed = 0f;
		currentGreyWaterConsumed = 0f;
		myBWP.reset();
		myRO.reset();
		myAES.reset();
		myPPS.reset();
	}
	
	/**
	* Checks whether RO subsystem has enough power or not
	* @return <code>true</code> if the RO subsystem has enough power, <code>false</code> if not.
	*/
	public boolean ROHasPower(){
		return myRO.hasPower();
	}
	
	/**
	* Checks whether AES subsystem has enough power or not
	* @return <code>true</code> if the AES subsystem has enough power, <code>false</code> if not.
	*/
	public boolean AESHasPower(){
		return myAES.hasPower();
	}
	
	/**
	* Checks whether PPS subsystem has enough power or not
	* @return <code>true</code> if the PPS subsystem has enough power, <code>false</code> if not.
	*/
	public boolean PPSHasPower(){
		return myPPS.hasPower();
	}
	
	/**
	* Checks whether BWP subsystem has enough power or not
	* @return <code>true</code> if the BWP subsystem has enough power, <code>false</code> if not.
	*/
	public boolean BWPHasPower(){
		return myBWP.hasPower();
	}
	
	/**
	* Checks whether RO subsystem has enough water or not
	* @return <code>true</code> if the RO subsystem has enough water, <code>false</code> if not.
	*/
	public boolean ROHasWater(){
		return myRO.hasWater();
	}
	
	/**
	* Checks whether AES subsystem has enough water or not
	* @return <code>true</code> if the AES subsystem has enough water, <code>false</code> if not.
	*/
	public boolean AESHasWater(){
		return myAES.hasWater();
	}
	
	/**
	* Checks whether PPS subsystem has enough water or not
	* @return <code>true</code> if the PPS subsystem has enough water, <code>false</code> if not.
	*/
	public boolean PPSHasWater(){
		return myPPS.hasWater();
	}
	
	/**
	* Checks whether BWP subsystem has enough water or not
	* @return <code>true</code> if the BWP subsystem has enough water, <code>false</code> if not.
	*/
	public boolean BWPHasWater(){
		return myBWP.hasWater();
	}
	
	/**
	* Returns the RO subsystem
	* @return the RO subsystem
	*/
	public RO getRO(){
		return myRO;
	}
	
	/**
	* Returns the AES subsystem
	* @return the AES subsystem
	*/
	public AES getAES(){
		return myAES;
	}
	
	/**
	* Returns the PPS subsystem
	* @return the PPS subsystem
	*/
	public PPS getPPS(){
		return myPPS;
	}
	
	/**
	* Returns the BWP subsystem
	* @return the BWP subsystem
	*/
	public BWP getBWP(){
		return myBWP;
	}
	
	/**
	* Returns the potable water produced (in liters) by the Water RS during the current tick
	* @return the potable water produced (in liters) by the Water RS during the current tick
	*/
	public float getPotableWaterProduced(){
		return currentPotableWaterProduced;
	}
	
	/**
	* Returns the grey water produced (in liters) by the Water RS during the current tick
	* @return the grey water produced (in liters) by the Water RS during the current tick
	*/
	public float getGreyWaterProduced(){
		return currentGreyWaterProduced;
	}
	
	/**
	* Returns the grey water consumed (in liters) by the Water RS during the current tick
	* @return the grey water consumed (in liters) by the Water RS during the current tick
	*/
	public float getGreyWaterConsumed(){
		return currentGreyWaterConsumed;
	}
	
	/**
	* Returns the power consumed (in watts) by the Water RS during the current tick
	* @return the power consumed (in watts) by the Water RS during the current tick
	*/
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}
	
	/**
	* Returns the dirty water consumed (in liters) by the Water RS during the current tick
	* @return the dirty water consumed (in liters) by the Water RS during the current tick
	*/
	public float getDirtyWaterConsumed(){
		return currentDirtyWaterConsumed;
	}
	
	/**
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("DirtyWaterStore"));
				myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("GreyWaterStore"));
				myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PotableWaterStore"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}
	
	/**
	* When ticked, the Water RS
	* 1) attempts to collect references to various server (if not already done)
	* 2) consumes power
	* 3) consumes unpotable water
	* 4) ticks each subsystem
	* 5) distributes the now potable water
	*/
	public void tick(){
		//collect references
		collectReferences();
		//gather power for each system
		gatherPower();
		//gather dirty & clean water for BWP
		gatherUnpotableWater();
		//tick each system
		myBWP.tick();
		myRO.tick();
		myAES.tick();
		myPPS.tick();
		//get clean water from PPS and put in clean water store
		distributePotableWater();
	}
	
	/**
	* Attempts to collect enough power from the Power PS to run each subsystem.
	* If any subsystem can't get enough power, the Water RS ceases to function.
	*/
	private void gatherPower(){
		float powerNeeded = myPPS.getPowerNeeded() + myRO.getPowerNeeded() + myBWP.getPowerNeeded() + myAES.getPowerNeeded();
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			myPPS.addPower(0);
			myRO.addPower(0);
			myBWP.addPower(0);
			myAES.addPower(0);
		}
		else{
			myPPS.addPower(myPPS.getPowerNeeded());
			myRO.addPower(myRO.getPowerNeeded());
			myBWP.addPower(myBWP.getPowerNeeded());
			myAES.addPower(myAES.getPowerNeeded());
		}
	}
	
	/**
	* Attempts to collect enough water from the Dirty Water Store to put into the BWP.
	* If the Dirty Water Store can't provide enough, the Water RS supplements from the Grey Water Store.
	*/
	private void gatherUnpotableWater(){
		//draw as much as we can from dirty water
		if (myDirtyWaterStore.getLevel() >= myBWP.getWaterWanted()){
			currentDirtyWaterConsumed = myDirtyWaterStore.take(myBWP.getWaterWanted());
			currentGreyWaterConsumed = 0;
		}
		//draw from both
		else{
			currentDirtyWaterConsumed = myDirtyWaterStore.take(myBWP.getWaterWanted());
			currentGreyWaterConsumed = myGreyWaterStore.take(myBWP.getWaterWanted() - currentDirtyWaterConsumed);
		}
		myBWP.addWater(currentDirtyWaterConsumed + currentGreyWaterConsumed);
	}
	
	/**
	* Takes clean water from the PPS as add it to the Potable Water Store
	*/
	private void distributePotableWater(){
		currentPotableWaterProduced = myPPS.takeWater();
		myPotableWaterStore.add(currentPotableWaterProduced);
	}
	
	/**
	* Returns the name of this module (WaterRS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "WaterRS";
	}
}
