package biosim.server.simulation.water;

import biosim.idl.simulation.water.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import biosim.server.simulation.framework.*;
import java.util.*;
/**
 * The Water Recovery System takes grey/dirty water and refines it to potable water for the crew members and grey water for the crops..
 * Class modeled after the paper:.
 * "Intelligent Control of a Water Recovery System: Three Years in the Trenches" by Bonasso, Kortenkamp, and Thronesbery
 * @author    Scott Bell
 */

public class WaterRSImpl extends SimBioModuleImpl implements WaterRSOperations, PowerConsumerOperations, DirtyWaterConsumerOperations, GreyWaterConsumerOperations, PotableWaterProducerOperations {
	//The various subsystems of Water RS that clean the water
	private BWP myBWP;
	private RO myRO;
	private AES myAES;
	private PPS myPPS;
	private LogIndex myLogIndex;
	private PowerStore[] myPowerInputs;
	private DirtyWaterStore[] myDirtyWaterInputs;
	private GreyWaterStore[] myGreyWaterInputs;
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] powerMaxFlowRates;
	private float[] dirtyWaterMaxFlowRates;
	private float[] greyWaterMaxFlowRates;
	private float[] potableWaterMaxFlowRates;
	private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 3;

	/**
	* Creates the Water RS and it's subsystems
	*/
	public WaterRSImpl(int pID){
		super(pID);
		myPowerInputs = new PowerStore[0];
		myDirtyWaterInputs = new DirtyWaterStore[0];
		myGreyWaterInputs = new GreyWaterStore[0];
		myPotableWaterOutputs = new PotableWaterStore[0];
		powerMaxFlowRates = new float[0];
		dirtyWaterMaxFlowRates = new float[0];
		greyWaterMaxFlowRates = new float[0];
		potableWaterMaxFlowRates = new float[0];
		myBWP = new BWP(this);
		myRO = new RO(this);
		myAES = new AES(this);
		myPPS = new PPS(this);
	}

	/**
	* Resets production/consumption levels and resets all the subsystems
	*/
	public void reset(){
		super.reset();
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
	
	public boolean ROIsEnabled(){
		return myRO.isEnabled();
	}
	
	public void setROEnabled(boolean pEnabled){
		myRO.setEnabled(pEnabled);
	}
	
	public float getROtoAESWater(){
		return myRO.getAESWaterProduced();
	}
	
	public float getROtoPPSWater(){
		return myRO.getPPSWaterProduced();
	}
	
	/**
	* Returns the RO subsystem
	* @return the RO subsystem
	*/
	RO getRO(){
		return myRO;
	}
	
	/**
	* Checks whether RO subsystem has enough water or not
	* @return <code>true</code> if the RO subsystem has enough water, <code>false</code> if not.
	*/
	public boolean ROHasWater(){
		return myRO.hasWater();
	}
	
	/**
	* Returns the AES subsystem
	* @return the AES subsystem
	*/
	AES getAES(){
		return myAES;
	}
	
	/**
	* Checks whether AES subsystem has enough power or not
	* @return <code>true</code> if the AES subsystem has enough power, <code>false</code> if not.
	*/
	public boolean AESHasPower(){
		return myAES.hasPower();
	}
	
	/**
	* Checks whether AES subsystem has enough water or not
	* @return <code>true</code> if the AES subsystem has enough water, <code>false</code> if not.
	*/
	public boolean AESHasWater(){
		return myAES.hasWater();
	}
	
	public boolean AESIsEnabled(){
		return myAES.isEnabled();
	}
	
	public void setAESEnabled(boolean pEnabled){
		myAES.setEnabled(pEnabled);
	}
	
	public float getAEStoPPSWater(){
		return myAES.getPPSWaterProduced();
	}

	/**
	* Checks whether PPS subsystem has enough power or not
	* @return <code>true</code> if the PPS subsystem has enough power, <code>false</code> if not.
	*/
	public boolean PPSHasPower(){
		return myPPS.hasPower();
	}
	
	/**
	* Checks whether PPS subsystem has enough water or not
	* @return <code>true</code> if the PPS subsystem has enough water, <code>false</code> if not.
	*/
	public boolean PPSHasWater(){
		return myPPS.hasWater();
	}
	
	/**
	* Returns the PPS subsystem
	* @return the PPS subsystem
	*/
	PPS getPPS(){
		return myPPS;
	}

	/**
	* Checks whether BWP subsystem has enough power or not
	* @return <code>true</code> if the BWP subsystem has enough power, <code>false</code> if not.
	*/
	public boolean BWPHasPower(){
		return myBWP.hasPower();
	}
	
	/**
	* Checks whether BWP subsystem has enough water or not
	* @return <code>true</code> if the BWP subsystem has enough water, <code>false</code> if not.
	*/
	public boolean BWPHasWater(){
		return myBWP.hasWater();
	}
	
	public float getBWPtoROWater(){
		return myBWP.getROWaterProduced();
	}
	
	public float getBWPtoAESWater(){
		return myBWP.getAESWaterProduced();
	}	
	
	/**
	* Returns the BWP subsystem
	* @return the BWP subsystem
	*/
	BWP getBWP(){
		return myBWP;
	}

	/**
	* Returns the potable water produced (in liters) by the Water RS during the current tick
	* @return the potable water produced (in liters) by the Water RS during the current tick
	*/
	public float getPotableWaterProduced(){
		return myPPS.getPotableWaterProduced();
	}

	/**
	* Returns the grey water produced (in liters) by the Water RS during the current tick
	* @return the grey water produced (in liters) by the Water RS during the current tick
	*/
	public float getGreyWaterProduced(){
		return 0f;
	}

	/**
	* Returns the grey water consumed (in liters) by the Water RS during the current tick
	* @return the grey water consumed (in liters) by the Water RS during the current tick
	*/
	public float getGreyWaterConsumed(){
		return myBWP.getGreyWaterConsumed();
	}

	/**
	* Returns the power consumed (in watts) by the Water RS during the current tick
	* @return the power consumed (in watts) by the Water RS during the current tick
	*/
	public float getPowerConsumed(){
		float powerConsumed = myAES.getPowerConsumed() + myPPS.getPowerConsumed() + 
							myBWP.getPowerConsumed() + myRO.getPowerConsumed();
		return powerConsumed;
	}

	/**
	* Returns the dirty water consumed (in liters) by the Water RS during the current tick
	* @return the dirty water consumed (in liters) by the Water RS during the current tick
	*/
	public float getDirtyWaterConsumed(){
		return myBWP.getDirtyWaterConsumed();
	}

	/**
	* When ticked, the Water RS:
	* 1) ticks each subsystem.
	*/
	public void tick(){
		//tick each system
		myBWP.tick();
		myRO.tick();
		myAES.tick();
		myPPS.tick();
		myAES.setMalfunctioning(false);
		myRO.setMalfunctioning(false);
		if (isMalfunctioning())
			performMalfunctions();
		if (moduleLogging)
			log();
	}
	
	private void performMalfunctions(){
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF){
					myAES.setMalfunctioning(true);
					myRO.setMalfunctioning(true);
				}
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					myRO.setMalfunctioning(true);
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					myAES.setMalfunctioning(true);
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF){
					myAES.setMalfunctioning(true);
					myRO.setMalfunctioning(true);
				}
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					myRO.setMalfunctioning(true);
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					myAES.setMalfunctioning(true);
			}
		}
	}
	
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("AES and RO ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("RO ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("AES ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("malfunctioning (repairable)");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("destroyed");
		return returnBuffer.toString();
	}

	/**
	* Returns the name of this module (WaterRS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "WaterRS"+getID();
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode currentPotableWaterProducedHead = myLog.addChild("potable_water_produced");
			myLogIndex.currentPotableWaterProducedIndex = currentPotableWaterProducedHead.addChild(""+getPotableWaterProduced());
			LogNode currentGreyWaterProducedHead = myLog.addChild("grey_water_produced");
			myLogIndex.currentGreyWaterProducedIndex = currentGreyWaterProducedHead.addChild(""+getGreyWaterProduced());
			LogNode currentPowerConsumedHead = myLog.addChild("power_consumed");
			myLogIndex.currentPowerConsumedIndex = currentPowerConsumedHead.addChild(""+getPowerConsumed());
			LogNode currentDirtyWaterConsumedHead = myLog.addChild("dirty_water_consumed");
			myLogIndex.currentDirtyWaterConsumedIndex = currentDirtyWaterConsumedHead.addChild(""+getDirtyWaterConsumed());
			LogNode currentGreyWaterConsumedHead = myLog.addChild("grey_water_consumed");
			myLogIndex.currentGreyWaterConsumedIndex = currentGreyWaterConsumedHead.addChild(""+getGreyWaterConsumed());
			myLogIndex.AESIndex = myLog.addChild("AES");
			myAES.log(myLogIndex.AESIndex);
			myLogIndex.BWPIndex = myLog.addChild("BWP");
			myBWP.log(myLogIndex.BWPIndex);
			myLogIndex.ROIndex = myLog.addChild("RO");
			myRO.log(myLogIndex.ROIndex);
			myLogIndex.PPSIndex = myLog.addChild("PPS");
			myPPS.log(myLogIndex.PPSIndex);
			logInitialized = true;
		}
		else{
			myLogIndex.currentPotableWaterProducedIndex.setValue(""+getPotableWaterProduced());
			myLogIndex.currentGreyWaterProducedIndex.setValue(""+getGreyWaterProduced());
			myLogIndex.currentPowerConsumedIndex.setValue(""+getPowerConsumed());
			myLogIndex.currentDirtyWaterConsumedIndex.setValue(""+getDirtyWaterConsumed());
			myLogIndex.currentGreyWaterConsumedIndex.setValue(""+getGreyWaterConsumed());
			myAES.log(myLogIndex.AESIndex);
			myBWP.log(myLogIndex.BWPIndex);
			myRO.log(myLogIndex.ROIndex);
			myPPS.log(myLogIndex.PPSIndex);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerNeededIndex;
		public LogNode currentPotableWaterProducedIndex;
		public LogNode currentGreyWaterProducedIndex;
		public LogNode currentPowerConsumedIndex;
		public LogNode currentDirtyWaterConsumedIndex;
		public LogNode currentGreyWaterConsumedIndex;
		public LogNode AESIndex;
		public LogNode PPSIndex;
		public LogNode BWPIndex;
		public LogNode ROIndex;
	}
	
	int getSubsystemsConsumingPower(){
		return NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER;
	}
	
	public void setPowerInputMaxFlowRate(float watts, int index){
		powerMaxFlowRates[index] = watts;
	}

	public float getPowerInputMaxFlowRate(int index){
		return powerMaxFlowRates[index];
	}

	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates){
		myPowerInputs = sources;
		powerMaxFlowRates = maxFlowRates;
	}

	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}
	
	public float[] getPowerInputMaxFlowRates(){
		return powerMaxFlowRates;
	}
	
	public void setDirtyWaterInputMaxFlowRate(float watts, int index){
		dirtyWaterMaxFlowRates[index] = watts;
	}

	public float getDirtyWaterInputMaxFlowRate(int index){
		return dirtyWaterMaxFlowRates[index];
	}

	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] maxFlowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterMaxFlowRates = maxFlowRates;
	}

	public DirtyWaterStore[] getDirtyWaterInputs(){
		return myDirtyWaterInputs;
	}
	
	public float[] getDirtyWaterInputMaxFlowRates(){
		return dirtyWaterMaxFlowRates;
	}
	
	public void setGreyWaterInputMaxFlowRate(float watts, int index){
		greyWaterMaxFlowRates[index] = watts;
	}

	public float getGreyWaterInputMaxFlowRate(int index){
		return greyWaterMaxFlowRates[index];
	}

	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] maxFlowRates){
		myGreyWaterInputs = sources;
		greyWaterMaxFlowRates = maxFlowRates;
	}

	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterInputs;
	}
	
	public float[] getGreyWaterInputMaxFlowRates(){
		return greyWaterMaxFlowRates;
	}
	
	public void setPotableWaterOutputMaxFlowRate(float watts, int index){
		potableWaterMaxFlowRates[index] = watts;
	}

	public float getPotableWaterOutputMaxFlowRate(int index){
		return potableWaterMaxFlowRates[index];
	}

	public void setPotableWaterOutputs(PotableWaterStore[] sources, float[] maxFlowRates){
		myPotableWaterOutputs = sources;
		potableWaterMaxFlowRates = maxFlowRates;
	}

	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}
	
	public float[] getPotableWaterOutputMaxFlowRates(){
		return potableWaterMaxFlowRates;
	}
}
