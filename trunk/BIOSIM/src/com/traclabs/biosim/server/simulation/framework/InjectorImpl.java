package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.util.log.*;
/**
 * The basic Injector Implementation.
 * @author    Scott Bell
 */

public abstract class InjectorImpl extends BioModuleImpl implements InjectorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations, LightConsumerOperations{
	private LogIndex myLogIndex;
	private PowerStore[] myPowerInputs;
	private float[] powerFlowRates;
	
	public InjectorImpl(int pID){
		super(pID);
		myPowerInputs = new PowerStore[0];
		powerFlowRates = new float[0];
	}


	public void tick(){
		if (isMalfunctioning())
			performMalfunctions();
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
			returnBuffer.append("Temporary Production Reduction");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Permanent Production Reduction");
		return returnBuffer.toString();
	}

	private void performMalfunctions(){
	}


	/**
	* Resets the level to 0
	*/
	public void reset(){
		super.reset();
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode intHead = myLog.addChild("in");
			//myLogIndex.levelIndex = levelHead.addChild((""+in));
			LogNode outHead = myLog.addChild("out");
			//myLogIndex.capacityIndex = capacityHead.addChild((""+out));
			logInitialized = true;
		}
		else{
			//myLogIndex.intHead.setValue(""+in);
			//myLogIndex.outHead.setValue(""+out);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode inIndex;
		public LogNode outIndex;
	}
	
	public void setPowerInputFlowrate(float watts, int index){
		powerFlowRates[index] = watts;
	}

	public float getPowerInputFlowrate(int index){
		return powerFlowRates[index];
	}

	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerInputs = sources;
		powerFlowRates = flowRates;
	}

	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}
}
