package biosim.server.simulation.air;

import java.util.Arrays;
import java.util.Iterator;

import biosim.idl.framework.AirConsumerOperations;
import biosim.idl.framework.AirProducerOperations;
import biosim.idl.framework.CO2ConsumerOperations;
import biosim.idl.framework.CO2ProducerOperations;
import biosim.idl.framework.H2ConsumerOperations;
import biosim.idl.framework.H2ProducerOperations;
import biosim.idl.framework.Malfunction;
import biosim.idl.framework.MalfunctionIntensity;
import biosim.idl.framework.MalfunctionLength;
import biosim.idl.framework.O2ProducerOperations;
import biosim.idl.framework.PotableWaterConsumerOperations;
import biosim.idl.framework.PotableWaterProducerOperations;
import biosim.idl.framework.PowerConsumerOperations;
import biosim.idl.simulation.air.AirRSOperations;
import biosim.idl.simulation.air.CO2Store;
import biosim.idl.simulation.air.H2Store;
import biosim.idl.simulation.air.O2Store;
import biosim.idl.simulation.environment.SimEnvironment;
import biosim.idl.simulation.power.PowerStore;
import biosim.idl.simulation.water.PotableWaterStore;
import biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Air Revitalization System Implementation.  Takes in Air (O2, CO2, other) from the environment and
 * power from the Power Production System and produces air with less CO2 and more O2.
 *
 * @author    Scott Bell
 */

public class AirRSImpl extends SimBioModuleImpl implements AirRSOperations, PowerConsumerOperations, PotableWaterConsumerOperations, PotableWaterProducerOperations, AirConsumerOperations, O2ProducerOperations, AirProducerOperations, CO2ProducerOperations, CO2ConsumerOperations, H2ProducerOperations, H2ConsumerOperations{
	private VCCR myVCCR;
	private CRS myCRS;
	private CH4Tank myCH4Tank;
	private OGS myOGS;
	private O2Store[] myO2Stores;
	private PowerStore[] myPowerStores;
	private PotableWaterStore[] myPotableWaterStoreInputs;
	private PotableWaterStore[] myPotableWaterStoreOutputs;
	private CO2Store[] myCO2InputStores;
	private CO2Store[] myCO2OutputStores;
	private H2Store[] myH2InputStores;
	private H2Store[] myH2OutputStores;
	private SimEnvironment[] mySimEnvironmentInputs;
	private SimEnvironment[] mySimEnvironmentOutputs;
	private float[] powerMaxFlowRates;
	private float[] powerActualFlowRates;
	private float[] powerDesiredFlowRates;
	private float[] potableWaterOutMaxFlowRates;
	private float[] potableWaterOutActualFlowRates;
	private float[] potableWaterOutDesiredFlowRates;
	private float[] potableWaterInMaxFlowRates;
	private float[] potableWaterInActualFlowRates;
	private float[] potableWaterInDesiredFlowRates;
	private float[] O2MaxFlowRates;
	private float[] O2ActualFlowRates;
	private float[] O2DesiredFlowRates;
	private float[] CO2InputMaxFlowRates;
	private float[] CO2InputActualFlowRates;
	private float[] CO2InputDesiredFlowRates;
	private float[] CO2OutputMaxFlowRates;
	private float[] CO2OutputActualFlowRates;
	private float[] CO2OutputDesiredFlowRates;
	private float[] H2InputMaxFlowRates;
	private float[] H2InputActualFlowRates;
	private float[] H2InputDesiredFlowRates;
	private float[] H2OutputMaxFlowRates;
	private float[] H2OutputActualFlowRates;
	private float[] H2OutputDesiredFlowRates;
	private float[] airInMaxFlowRates;
	private float[] airInActualFlowRates;
	private float[] airInDesiredFlowRates;
	private float[] airOutMaxFlowRates;
	private float[] airOutActualFlowRates;
	private float[] airOutDesiredFlowRates;
	private static final int NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER = 3;
	private float myProductionRate = 1f;

	public AirRSImpl(int pID, String pName){
		super(pID, pName);
		myVCCR = new VCCR(this);
		myCRS = new CRS(this);
		myCH4Tank = new CH4Tank(this);
		myOGS = new OGS(this);

		myO2Stores = new O2Store[0];
		myPowerStores = new PowerStore[0];
		myCO2InputStores = new CO2Store[0];
		myCO2OutputStores = new CO2Store[0];
		mySimEnvironmentInputs = new SimEnvironment[0];
		mySimEnvironmentOutputs = new SimEnvironment[0];
		powerMaxFlowRates = new float[0];
		powerActualFlowRates = new float[0];
		powerDesiredFlowRates = new float[0];
		potableWaterInMaxFlowRates = new float[0];
		potableWaterInActualFlowRates = new float[0];
		potableWaterInDesiredFlowRates = new float[0];
		potableWaterOutMaxFlowRates = new float[0];
		potableWaterOutActualFlowRates = new float[0];
		potableWaterOutDesiredFlowRates = new float[0];
		O2MaxFlowRates = new float[0];
		O2ActualFlowRates = new float[0];
		O2DesiredFlowRates = new float[0];
		CO2InputMaxFlowRates = new float[0];
		CO2InputActualFlowRates = new float[0];
		CO2InputDesiredFlowRates = new float[0];
		CO2OutputMaxFlowRates = new float[0];
		CO2OutputActualFlowRates = new float[0];
		CO2OutputDesiredFlowRates = new float[0];
		H2InputMaxFlowRates = new float[0];
		H2InputActualFlowRates = new float[0];
		H2InputDesiredFlowRates = new float[0];
		H2OutputMaxFlowRates = new float[0];
		H2OutputActualFlowRates = new float[0];
		H2OutputDesiredFlowRates = new float[0];
		airInMaxFlowRates = new float[0];
		airInActualFlowRates = new float[0];
		airInDesiredFlowRates = new float[0];
		airOutMaxFlowRates = new float[0];
		airOutActualFlowRates = new float[0];
		airOutDesiredFlowRates = new float[0];
	}

	public boolean VCCRHasPower(){
		return myVCCR.hasPower();
	}

	public boolean CRSHasPower(){
		return myCRS.hasPower();
	}

	public boolean OGSHasPower(){
		return myOGS.hasPower();
	}

	VCCR getVCCR(){
		return myVCCR;
	}

	CRS getCRS(){
		return myCRS;
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
	* Returns the CO2 consumption (in moles) of the AirRS at the current tick.
	* @return the CO2 consumed at the current tick
	*/
	public float getCO2Consumed(){
		return myVCCR.getCO2Consumed();
	}

	/**
	* Returns the O2 produced (in moles) of the AirRS at the current tick.
	* @return the O2 produced (in moles) at the current tick
	*/
	public float getO2Produced(){
		return myOGS.getO2Produced();
	}

	/**
	* Returns the CO2 produced (in moles) of the AirRS at the current tick.
	* @return the CO2 produced (in moles) at the current tick
	*/
	public float getCO2Produced(){
		return myVCCR.getCO2Produced();
	}

	/**
	* Processes a tick by collecting referernces (if needed), resources, and pushing the new air out.
	*/
	public void tick(){
		super.tick();
		Arrays.fill(powerActualFlowRates, 0f);
		myVCCR.tick();
		myCRS.tick();
		myCH4Tank.tick();
		myOGS.tick();
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

	protected void performMalfunctions(){
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
		myCRS.reset();
		myCH4Tank.reset();
		myOGS.reset();
	}

	public void log(){
			/*
			LogNode CO2NeededHead = myLog.addChild("CO2 Needed");
			LogNode currentO2ProducedHead = myLog.addChild("O2 Produced");
			LogNode currentCO2ProducedHead = myLog.addChild("CO2 Produced");
			LogNode currentCO2ConsumedHead = myLog.addChild("CO2 Consumed");
			LogNode currentPowerConsumedHead = myLog.addChild("Power Consumed");
			*/
	}

	
	
	int getSubsystemsConsumingPower(){
		return NUMBER_OF_SUBSYSTEMS_CONSUMING_POWER;
	}
	
	//Power Inputs
	public void setPowerInputMaxFlowRate(float watts, int index){
		powerMaxFlowRates[index] = watts;
	}
	public float getPowerInputMaxFlowRate(int index){
		return powerMaxFlowRates[index];
	}
	public float[] getPowerInputMaxFlowRates(){
		return powerMaxFlowRates;
	}
	public void setPowerInputDesiredFlowRate(float watts, int index){
		powerDesiredFlowRates[index] = watts;
	}
	public float getPowerInputDesiredFlowRate(int index){
		return powerDesiredFlowRates[index];
	}
	public float[] getPowerInputDesiredFlowRates(){
		return powerDesiredFlowRates;
	}
	public float getPowerInputActualFlowRate(int index){
		return powerActualFlowRates[index];
	}
	public float[] getPowerInputActualFlowRates(){
		return powerActualFlowRates;
	}
	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPowerStores = sources;
		powerMaxFlowRates = maxFlowRates;
		powerDesiredFlowRates = desiredFlowRates;
		powerActualFlowRates = new float[powerDesiredFlowRates.length];
	}
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	void addPowerInputActualFlowRate(float watts, int index){
		powerActualFlowRates[index] += watts;
	}
	
	//Potable Water Outputs
	public void setPotableWaterOutputMaxFlowRate(float watts, int index){
		potableWaterOutMaxFlowRates[index] = watts;
	}
	public float getPotableWaterOutputMaxFlowRate(int index){
		return potableWaterOutMaxFlowRates[index];
	}
	public float[] getPotableWaterOutputMaxFlowRates(){
		return potableWaterOutMaxFlowRates;
	}
	public void setPotableWaterOutputDesiredFlowRate(float watts, int index){
		potableWaterOutDesiredFlowRates[index] = watts;
	}
	public float getPotableWaterOutputDesiredFlowRate(int index){
		return potableWaterOutDesiredFlowRates[index];
	}
	public float[] getPotableWaterOutputDesiredFlowRates(){
		return potableWaterOutDesiredFlowRates;
	}
	public float getPotableWaterOutputActualFlowRate(int index){
		return potableWaterOutActualFlowRates[index];
	}
	public float[] getPotableWaterOutputActualFlowRates(){
		return potableWaterOutActualFlowRates;
	}
	public void setPotableWaterOutputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPotableWaterStoreOutputs = sources;
		potableWaterOutMaxFlowRates = maxFlowRates;
		potableWaterOutDesiredFlowRates = desiredFlowRates;
		potableWaterOutActualFlowRates = new float[potableWaterOutDesiredFlowRates.length];
	}
	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterStoreOutputs;
	}
	void addPotableWaterOutputActualFlowRate(float watts, int index){
		potableWaterOutActualFlowRates[index] += watts;
	}
	
	//Potable Water Inputs
	public void setPotableWaterInputMaxFlowRate(float watts, int index){
		potableWaterInMaxFlowRates[index] = watts;
	}
	public float getPotableWaterInputMaxFlowRate(int index){
		return potableWaterInMaxFlowRates[index];
	}
	public float[] getPotableWaterInputMaxFlowRates(){
		return potableWaterInMaxFlowRates;
	}
	public void setPotableWaterInputDesiredFlowRate(float watts, int index){
		potableWaterInDesiredFlowRates[index] = watts;
	}
	public float getPotableWaterInputDesiredFlowRate(int index){
		return potableWaterInDesiredFlowRates[index];
	}
	public float[] getPotableWaterInputDesiredFlowRates(){
		return potableWaterInDesiredFlowRates;
	}
	public float getPotableWaterInputActualFlowRate(int index){
		return potableWaterInActualFlowRates[index];
	}
	public float[] getPotableWaterInputActualFlowRates(){
		return potableWaterInActualFlowRates;
	}
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPotableWaterStoreInputs = sources;
		potableWaterInMaxFlowRates = maxFlowRates;
		potableWaterInDesiredFlowRates = desiredFlowRates;
		potableWaterInActualFlowRates = new float[potableWaterInDesiredFlowRates.length];
	}
	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterStoreInputs;
	}
	void addPotableWaterInputActualFlowRate(float watts, int index){
		potableWaterInActualFlowRates[index] += watts;
	}
	
	//Air Inputs
	public void setAirInputMaxFlowRate(float moles, int index){
		airInMaxFlowRates[index] = moles;
	}
	public float getAirInputMaxFlowRate(int index){
		return airInMaxFlowRates[index];
	}
	public float[] getAirInputMaxFlowRates(){
		return airInMaxFlowRates;
	}
	public void setAirInputDesiredFlowRate(float moles, int index){
		airInDesiredFlowRates[index] = moles;
	}
	public float getAirInputDesiredFlowRate(int index){
		return airInDesiredFlowRates[index];
	}
	public float[] getAirInputDesiredFlowRates(){
		return airInDesiredFlowRates;
	}
	public float getAirInputActualFlowRate(int index){
		return airInActualFlowRates[index];
	}
	public float[] getAirInputActualFlowRates(){
		return airInActualFlowRates;
	}
	public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		mySimEnvironmentInputs = sources;
		airInMaxFlowRates = maxFlowRates;
		airInDesiredFlowRates = desiredFlowRates;
		airInActualFlowRates = new float[airInDesiredFlowRates.length];
	}
	public SimEnvironment[] getAirInputs(){
		return mySimEnvironmentInputs;
	}
	void setAirInputActualFlowRate(float moles, int index){
		airInActualFlowRates[index] = moles;
	}
	
	//Air Ouputs
	public void setAirOutputMaxFlowRate(float moles, int index){
		airOutMaxFlowRates[index] = moles;
	}
	public float getAirOutputMaxFlowRate(int index){
		return airOutMaxFlowRates[index];
	}
	public float[] getAirOutputMaxFlowRates(){
		return airOutMaxFlowRates;
	}
	public void setAirOutputDesiredFlowRate(float moles, int index){
		airOutDesiredFlowRates[index] = moles;
	}
	public float getAirOutputDesiredFlowRate(int index){
		return airOutDesiredFlowRates[index];
	}
	public float[] getAirOutputDesiredFlowRates(){
		return airOutDesiredFlowRates;
	}
	public float getAirOutputActualFlowRate(int index){
		return airOutActualFlowRates[index];
	}
	public float[] getAirOutputActualFlowRates(){
		return airOutActualFlowRates;
	}
	public void setAirOutputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		mySimEnvironmentOutputs = sources;
		airOutMaxFlowRates = maxFlowRates;
		airOutDesiredFlowRates = desiredFlowRates;
		airOutActualFlowRates = new float[airOutDesiredFlowRates.length]; 
	}
	public SimEnvironment[] getAirOutputs(){
		return mySimEnvironmentOutputs;
	}
	void setAirOutputActualFlowRate(float moles, int index){
		airOutActualFlowRates[index] = moles;
	}
	
	//O2 Ouputs
	public void setO2OutputMaxFlowRate(float moles, int index){
		O2MaxFlowRates[index] = moles;
	}
	public float getO2OutputMaxFlowRate(int index){
		return O2MaxFlowRates[index];
	}
	public float[] getO2OutputMaxFlowRates(){
		return O2MaxFlowRates;
	}
	public void setO2OutputDesiredFlowRate(float moles, int index){
		O2DesiredFlowRates[index] = moles;
	}
	public float getO2OutputDesiredFlowRate(int index){
		return O2DesiredFlowRates[index];
	}
	public float[] getO2OutputDesiredFlowRates(){
		return O2DesiredFlowRates;
	}
	public float getO2OutputActualFlowRate(int index){
		return O2ActualFlowRates[index];
	}
	public float[] getO2OutputActualFlowRates(){
		return O2ActualFlowRates;
	}
	public void setO2Outputs(O2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myO2Stores = sources;
		O2MaxFlowRates = maxFlowRates;
		O2DesiredFlowRates = desiredFlowRates;
		O2ActualFlowRates = new float[O2DesiredFlowRates.length]; 
	}
	public O2Store[] getO2Outputs(){
		return myO2Stores;
	}
	void setO2OutputActualFlowRate(float moles, int index){
		O2ActualFlowRates[index] = moles;
	}
	
	//CO2 Ouputs
	public void setCO2OutputMaxFlowRate(float moles, int index){
		CO2OutputMaxFlowRates[index] = moles;
	}
	public float getCO2OutputMaxFlowRate(int index){
		return CO2OutputMaxFlowRates[index];
	}
	public float[] getCO2OutputMaxFlowRates(){
		return CO2OutputMaxFlowRates;
	}
	public void setCO2OutputDesiredFlowRate(float moles, int index){
		CO2OutputDesiredFlowRates[index] = moles;
	}
	public float getCO2OutputDesiredFlowRate(int index){
		return CO2OutputDesiredFlowRates[index];
	}
	public float[] getCO2OutputDesiredFlowRates(){
		return CO2OutputDesiredFlowRates;
	}
	public float getCO2OutputActualFlowRate(int index){
		return CO2OutputActualFlowRates[index];
	}
	public float[] getCO2OutputActualFlowRates(){
		return CO2OutputActualFlowRates;
	}
	public void setCO2Outputs(CO2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2OutputStores = sources;
		CO2OutputMaxFlowRates = maxFlowRates;
		CO2OutputDesiredFlowRates = desiredFlowRates;
		CO2OutputActualFlowRates = new float[CO2OutputDesiredFlowRates.length]; 
	}
	public CO2Store[] getCO2Outputs(){
		return myCO2OutputStores;
	}
	void setCO2OutputActualFlowRate(float moles, int index){
		CO2OutputActualFlowRates[index] = moles;
	}
	
	//CO2 Inputs
	public void setCO2InputMaxFlowRate(float moles, int index){
		CO2InputMaxFlowRates[index] = moles;
	}
	public float getCO2InputMaxFlowRate(int index){
		return CO2InputMaxFlowRates[index];
	}
	public float[] getCO2InputMaxFlowRates(){
		return CO2InputMaxFlowRates;
	}
	public void setCO2InputDesiredFlowRate(float moles, int index){
		CO2InputDesiredFlowRates[index] = moles;
	}
	public float getCO2InputDesiredFlowRate(int index){
		return CO2InputDesiredFlowRates[index];
	}
	public float[] getCO2InputDesiredFlowRates(){
		return CO2InputDesiredFlowRates;
	}
	public float getCO2InputActualFlowRate(int index){
		return CO2InputActualFlowRates[index];
	}
	public float[] getCO2InputActualFlowRates(){
		return CO2InputActualFlowRates;
	}
	public void setCO2Inputs(CO2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myCO2InputStores = sources;
		CO2InputMaxFlowRates = maxFlowRates;
		CO2InputDesiredFlowRates = desiredFlowRates;
		CO2InputActualFlowRates = new float[CO2InputDesiredFlowRates.length]; 
	}
	public CO2Store[] getCO2Inputs(){
		return myCO2InputStores;
	}
	void setCO2InputActualFlowRate(float moles, int index){
		CO2InputActualFlowRates[index] = moles;
	}
	
	//H2 Ouputs
	public void setH2OutputMaxFlowRate(float moles, int index){
		H2OutputMaxFlowRates[index] = moles;
	}
	public float getH2OutputMaxFlowRate(int index){
		return H2OutputMaxFlowRates[index];
	}
	public float[] getH2OutputMaxFlowRates(){
		return H2OutputMaxFlowRates;
	}
	public void setH2OutputDesiredFlowRate(float moles, int index){
		H2OutputDesiredFlowRates[index] = moles;
	}
	public float getH2OutputDesiredFlowRate(int index){
		return H2OutputDesiredFlowRates[index];
	}
	public float[] getH2OutputDesiredFlowRates(){
		return H2OutputDesiredFlowRates;
	}
	public float getH2OutputActualFlowRate(int index){
		return H2OutputActualFlowRates[index];
	}
	public float[] getH2OutputActualFlowRates(){
		return H2OutputActualFlowRates;
	}
	public void setH2Outputs(H2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myH2OutputStores = sources;
		H2OutputMaxFlowRates = maxFlowRates;
		H2OutputDesiredFlowRates = desiredFlowRates;
		H2OutputActualFlowRates = new float[H2OutputDesiredFlowRates.length]; 
	}
	public H2Store[] getH2Outputs(){
		return myH2OutputStores;
	}
	void setH2OutputActualFlowRate(float moles, int index){
		H2OutputActualFlowRates[index] = moles;
	}
	
	//H2 Inputs
	public void setH2InputMaxFlowRate(float moles, int index){
		H2InputMaxFlowRates[index] = moles;
	}
	public float getH2InputMaxFlowRate(int index){
		return H2InputMaxFlowRates[index];
	}
	public float[] getH2InputMaxFlowRates(){
		return H2InputMaxFlowRates;
	}
	public void setH2InputDesiredFlowRate(float moles, int index){
		H2InputDesiredFlowRates[index] = moles;
	}
	public float getH2InputDesiredFlowRate(int index){
		return H2InputDesiredFlowRates[index];
	}
	public float[] getH2InputDesiredFlowRates(){
		return H2InputDesiredFlowRates;
	}
	public float getH2InputActualFlowRate(int index){
		return H2InputActualFlowRates[index];
	}
	public float[] getH2InputActualFlowRates(){
		return H2InputActualFlowRates;
	}
	public void setH2Inputs(H2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myH2InputStores = sources;
		H2InputMaxFlowRates = maxFlowRates;
		H2InputDesiredFlowRates = desiredFlowRates;
		H2InputActualFlowRates = new float[H2InputDesiredFlowRates.length]; 
	}
	public H2Store[] getH2Inputs(){
		return myH2InputStores;
	}
	void setH2InputActualFlowRate(float moles, int index){
		H2InputActualFlowRates[index] = moles;
	}
}
