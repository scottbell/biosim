package biosim.server.power;

import biosim.idl.power.*;
import biosim.idl.util.log.*;
import biosim.idl.environment.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.server.util.*;
import biosim.server.framework.*;
/**
 * The Power Production System creates power from a generator (say a solar panel) and stores it in the power store.
 * This provides power to all the biomodules in the system.
 *
 * @author    Scott Bell
 */

public abstract class PowerPSImpl extends BioModuleImpl implements PowerPSOperations, PowerProducerOperations, LightConsumerOperations {
	//The power produced (in watts) by the Power PS at the current tick
	float currentPowerProduced = 0f;
	//Flag switched when the Power PS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	private LogIndex myLogIndex;
	private PowerStore[] myPowerStores;
	private float[] powerFlowRates;
	private SimEnvironment myLightInput;

	public PowerPSImpl(int pID){
		super(pID);
		myPowerStores = new PowerStore[0];
		powerFlowRates = new float[0];
	}

	/**
	* When ticked, the PowerPS does the following:
	* 1) attempts to collect references to various server (if not already done).
	* 2) creates power and places it into the power store.
	*/
	public void tick(){
		currentPowerProduced = calculatePowerProduced();
		if (isMalfunctioning())
			performMalfunctions();
		float distributedPowerLeft = currentPowerProduced;
		for (int i = 0; (i < myPowerStores.length) && (distributedPowerLeft > 0); i++){
			float powerToDistribute = Math.min(distributedPowerLeft, powerFlowRates[i]);
			distributedPowerLeft -= myPowerStores[i].add(powerToDistribute);
		}
		if (moduleLogging)
			log();
	}

	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Production Rate Decrease (Temporary)");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Production Rate Decrease (Permanent)");
		return returnBuffer.toString();
	}

	private void performMalfunctions(){
		float productionRate = 1f;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
		}
		currentPowerProduced *= productionRate;
	}

	abstract float calculatePowerProduced();

	/**
	* Reset does nothing right now
	*/
	public void reset(){
		super.reset();
		currentPowerProduced = 0f;
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
		return "PowerPS"+getID();
	}

	void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerProducedHead = myLog.addChild("power_produced");
			myLogIndex.powerProducedIndex = powerProducedHead.addChild(""+currentPowerProduced);
			logInitialized = true;
		}
		else{
			myLogIndex.powerProducedIndex.setValue(""+currentPowerProduced);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerProducedIndex;
	}

	public void setPowerOutputFlowrate(float watts, int index){
		powerFlowRates[index] = watts;
	}

	public float getPowerOutputFlowrate(int index){
		return powerFlowRates[index];
	}

	public void setPowerOutputs(PowerStore[] destinations, float[] flowRates){
		myPowerStores = destinations;
		powerFlowRates = flowRates;
	}

	public PowerStore[] getPowerOutputs(){
		return myPowerStores;
	}

	public void setLightInput(SimEnvironment source){
		myLightInput = source;
	}

	public SimEnvironment getLightInput(){
		return myLightInput;
	}
}
