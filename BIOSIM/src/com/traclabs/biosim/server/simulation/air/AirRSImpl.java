package biosim.server.air;

import biosim.idl.air.*;
import biosim.idl.framework.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.idl.util.log.*;
import java.util.*;
import biosim.server.util.*;
import biosim.server.framework.*;

/**
 * The Air Revitalization System Implementation.  Takes in Air (O2, CO2, other) from the environment and
 * power from the Power Production System and produces air with less CO2 and more O2.
 *
 * @author    Scott Bell
 */

public class AirRSImpl extends BioModuleImpl implements AirRSOperations, PowerConsumerOperations, AirConsumerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations{
	private LogIndex myLogIndex;
	private VCCR myVCCR;
	private CO2Tank myCO2Tank;
	private CRS myCRS;
	private H2Tank myH2Tank;
	private CH4Tank myCH4Tank;
	private OGS myOGS;
	private O2Store[] myO2Stores;
	private PowerStore[] myPowerStores;
	private CO2Store[] myCO2Stores;
	private SimEnvironment[] mySimEnvironmentInputs;
	private SimEnvironment[] mySimEnvironmentOutputs;
	private float myProductionRate;
	private float[] powerFlowRates;
	private float[] O2FlowRates;
	private float[] CO2FlowRates;
	private float[] airInFlowRates;
	private float[] airOutFlowRates;
	
	public AirRSImpl(int pID){
		super(pID);
		myVCCR = new VCCR(this);
		myCO2Tank = new CO2Tank(this);
		myCRS = new CRS(this);
		myH2Tank = new H2Tank(this);
		myCH4Tank = new CH4Tank(this);
		myOGS = new OGS(this);
	}
	
	public boolean VCCRHasPower(){
		return myVCCR.hasPower();
	}
	
	public boolean VCCRHasEnoughCO2(){
		return myVCCR.hasEnoughCO2();
	}
	
	public boolean CRSHasPower(){
		return myCRS.hasPower();
	}
	
	public boolean CRSHasEnoughCO2(){
		return myCRS.hasEnoughCO2();
	}
	
	public boolean CRSHasEnoughH2(){
		return myCRS.hasEnoughH2();
	}
	
	public boolean OGSHasPower(){
		return myOGS.hasPower();
	}
	
	public boolean OGSHasEnoughH2O(){
		return myOGS.hasEnoughH2O();
	}
	
	VCCR getVCCR(){
		return myVCCR;
	}
	
	CO2Tank getCO2Tank(){
		return myCO2Tank;
	}
	
	CRS getCRS(){
		return myCRS;
	}
	
	H2Tank getH2Tank(){
		return myH2Tank;
	}
	
	CH4Tank getCH4Tank(){
		return myCH4Tank;
	}
	
	OGS getOGS(){
		return myOGS;
	}
	
	/**
	* Returns the power consumption (in watts) of the AirRS at the current tick.
	* @return the power consumed (in watts) at the current tick
	*/
	public float getPowerConsumed(){
		return myVCCR.getPowerConsumed() + myOGS.getPowerConsumed() + myCRS.getPowerConsumed();
	}
	
	/**
	* Returns the CO2 consumption (in liters) of the AirRS at the current tick.
	* @return the CO2 consumed at the current tick
	*/
	public float getCO2Consumed(){
		return myVCCR.getCO2Consumed();
	}
	
	/**
	* Returns the O2 produced (in liters) of the AirRS at the current tick.
	* @return the O2 produced (in liters) at the current tick
	*/
	public float getO2Produced(){
		return myVCCR.getO2Produced() + myOGS.getO2Produced();
	}
	
	/**
	* Returns the CO2 produced (in liters) of the AirRS at the current tick.
	* @return the CO2 produced (in liters) at the current tick
	*/
	public float getCO2Produced(){
		return 0;
	}
	
	/**
	* Processes a tick by collecting referernces (if needed), resources, and pushing the new air out.
	*/
	public void tick(){
		myVCCR.tick();
		myCO2Tank.tick();
		myCRS.tick();
		myH2Tank.tick();
		myCH4Tank.tick();
		myOGS.tick();
		if (isMalfunctioning())
			performMalfunctions();
		if (moduleLogging)
			log();
	}
	
	public void setProductionRate(float percentage){
		myVCCR.setProductionRate(percentage);
		myOGS.setProductionRate(percentage);
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
		setProductionRate(productionRate);
	}
	
	/**
	* Resets production/consumption levels.
	*/
	public void reset(){
		super.reset();
		myVCCR.reset();
		myCO2Tank.reset();
		myCRS.reset();
		myH2Tank.reset();
		myCH4Tank.reset();
		myOGS.reset();
	}
	
	/**
	* Returns the name of this module (AirRS)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "AirRS"+getID();
	}
	
	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode CO2NeededHead = myLog.addChild("CO2 Needed");
			LogNode currentO2ProducedHead = myLog.addChild("O2 Produced");
			LogNode currentCO2ProducedHead = myLog.addChild("CO2 Produced");
			LogNode currentCO2ConsumedHead = myLog.addChild("CO2 Consumed");
			LogNode currentPowerConsumedHead = myLog.addChild("Power Consumed");
			logInitialized = true;
		}
		else{
		}
		sendLog(myLog);
	}
	
	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode CO2NeededIndex;
		public LogNode currentO2ProducedIndex;
		public LogNode currentCO2ConsumedIndex;
		public LogNode currentCO2ProducedIndex;
		public LogNode currentPowerConsumedIndex;
	}
	
	public void setPowerInputFlowrate(float watts, int index){
		powerFlowRates[index] = watts;
	}
	
	public float getPowerInputFlowrate(int index){
		return powerFlowRates[index];
	}
	
	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerStores = sources;
		powerFlowRates = flowRates;
	}
	
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	
	public void setAirInputFlowrate(float liters, int index){
		airInFlowRates[index] = liters;
	}
	
	public float getAirInputFlowrate(int index){
		return airInFlowRates[index];
	}
	
	public void setAirInputs(SimEnvironment[] sources, float[] flowRates){
		mySimEnvironmentInputs = sources;
		airInFlowRates = flowRates;
	}
	
	public SimEnvironment[] getAirInputs(){
		return mySimEnvironmentInputs;
	}
	
	public void setAirOutputFlowrate(float liters, int index){
		airOutFlowRates[index] = liters;
	}
	
	public float getAirOutputFlowrate(int index){
		return airOutFlowRates[index];
	}
	
	public void setAirOutputs(SimEnvironment[] sources, float[] flowRates){
		mySimEnvironmentOutputs = sources;
		airOutFlowRates = flowRates;
	}
	
	public SimEnvironment[] getAirOutputs(){
		return mySimEnvironmentOutputs;
	}
	
	public void setO2OutputFlowrate(float liters, int index){
		O2FlowRates[index] = liters;
	}
	
	public float getO2OutputFlowrate(int index){
		return O2FlowRates[index];
	}
	
	public void setO2Outputs(O2Store[] sources, float[] flowRates){
		myO2Stores = sources;
		O2FlowRates = flowRates;
	}
	
	public O2Store[] getO2Outputs(){
		return myO2Stores;
	}
	
	public void setCO2OutputFlowrate(float liters, int index){
		CO2FlowRates[index] = liters;
	}
	
	public float getCO2OutputFlowrate(int index){
		return CO2FlowRates[index];
	}
	
	public void setCO2Outputs(CO2Store[] sources, float[] flowRates){
		myCO2Stores = sources;
		CO2FlowRates = flowRates;
	}
	
	public CO2Store[] getCO2Outputs(){
		return myCO2Stores;
	}
}
