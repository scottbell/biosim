package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.idl.water.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.util.log.*;
/**
 * The basic Accumulator Implementation.
 * @author    Scott Bell
 */

public abstract class AccumulatorImpl extends BioModuleImpl implements AccumulatorOperations, PowerConsumerOperations, PotableWaterConsumerOperations, GreyWaterConsumerOperations, DirtyWaterConsumerOperations, O2ConsumerOperations, CO2ConsumerOperations, AirConsumerOperations, BiomassConsumerOperations, FoodConsumerOperations, PowerProducerOperations, PotableWaterProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations, O2ProducerOperations, CO2ProducerOperations, AirProducerOperations, BiomassProducerOperations, FoodProducerOperations{
	private LogIndex myLogIndex;
	private PowerStore[] myPowerInputs;
	private PowerStore[] myPowerOutputs;
	private float[] powerOutFlowRates;
	private float[] powerInFlowRates;

	private GreyWaterStore[] myGreyWaterInputs;
	private GreyWaterStore[] myGreyWaterOutputs;
	private float[] greyWaterOutFlowRates;
	private float[] greyWaterInFlowRates;

	private DirtyWaterStore[] myDirtyWaterInputs;
	private DirtyWaterStore[] myDirtyWaterOutputs;
	private float[] dirtyWaterOutFlowRates;
	private float[] dirtyWaterInFlowRates;

	private PotableWaterStore[] myPotableWaterInputs;
	private PotableWaterStore[] myPotableWaterOutputs;
	private float[] potableWaterOutFlowRates;
	private float[] potableWaterInFlowRates;

	private BiomassStore[] myBiomassInputs;
	private BiomassStore[] myBiomassOutputs;
	private float[] biomassOutFlowRates;
	private float[] biomassInFlowRates;

	private FoodStore[] myFoodInputs;
	private FoodStore[] myFoodOutputs;
	private float[] foodOutFlowRates;
	private float[] foodInFlowRates;

	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private float[] airOutFlowRates;
	private float[] airInFlowRates;

	private O2Store[] myO2Inputs;
	private O2Store[] myO2Outputs;
	private float[] O2OutFlowRates;
	private float[] O2InFlowRates;

	private CO2Store[] myCO2Inputs;
	private CO2Store[] myCO2Outputs;
	private float[] CO2OutFlowRates;
	private float[] CO2InFlowRates;

	public AccumulatorImpl(int pID){
		super(pID);
		myPowerOutputs = new PowerStore[0];
		myPowerInputs = new PowerStore[0];
		powerOutFlowRates = new float[0];
		powerInFlowRates = new float[0];

		myGreyWaterOutputs = new GreyWaterStore[0];
		myGreyWaterInputs = new GreyWaterStore[0];
		greyWaterOutFlowRates = new float[0];
		greyWaterInFlowRates = new float[0];

		myDirtyWaterOutputs = new DirtyWaterStore[0];
		myDirtyWaterInputs = new DirtyWaterStore[0];
		dirtyWaterOutFlowRates = new float[0];
		dirtyWaterInFlowRates = new float[0];

		myPotableWaterOutputs = new PotableWaterStore[0];
		myPotableWaterInputs = new PotableWaterStore[0];
		potableWaterOutFlowRates = new float[0];
		potableWaterInFlowRates = new float[0];

		myBiomassOutputs = new BiomassStore[0];
		myBiomassInputs = new BiomassStore[0];
		biomassOutFlowRates = new float[0];
		biomassInFlowRates = new float[0];

		myFoodOutputs = new FoodStore[0];
		myFoodInputs = new FoodStore[0];
		foodOutFlowRates = new float[0];
		foodInFlowRates = new float[0];

		myAirOutputs = new SimEnvironment[0];
		myAirInputs = new SimEnvironment[0];
		airOutFlowRates = new float[0];
		airInFlowRates = new float[0];

		myO2Outputs = new O2Store[0];
		myO2Inputs = new O2Store[0];
		O2OutFlowRates = new float[0];
		O2InFlowRates = new float[0];

		myCO2Outputs = new CO2Store[0];
		myCO2Inputs = new CO2Store[0];
		CO2OutFlowRates = new float[0];
		CO2InFlowRates = new float[0];
	}


	public void tick(){
		getAndPushResources();
		if (isMalfunctioning())
			performMalfunctions();
		if (moduleLogging)
			log();
	}
	
	private void getAndPushResources(){
		float powerGathered = getMaxResourceFromStore(myPowerInputs, powerInFlowRates);
		float powerPushed = pushResourceToStore(myPowerOutputs, powerOutFlowRates, powerGathered);
		
		float greyWaterGathered = getMaxResourceFromStore(myGreyWaterInputs, greyWaterInFlowRates);
		float greyWaterPushed = pushResourceToStore(myGreyWaterOutputs, greyWaterOutFlowRates, greyWaterGathered);
		
		float potableWaterGathered = getMaxResourceFromStore(myPotableWaterInputs, potableWaterInFlowRates);
		float potableWaterPushed = pushResourceToStore(myPotableWaterOutputs, potableWaterOutFlowRates, potableWaterGathered);
		
		float dirtyWaterGathered = getMaxResourceFromStore(myDirtyWaterInputs, dirtyWaterInFlowRates);
		float dirtyWaterPushed = pushResourceToStore(myDirtyWaterOutputs, dirtyWaterOutFlowRates, dirtyWaterGathered);
		
		float biomassGathered = getMaxResourceFromStore(myBiomassInputs, biomassInFlowRates);
		float biomassPushed = pushResourceToStore(myBiomassOutputs, biomassOutFlowRates, biomassGathered);
		
		float foodGathered = getMaxResourceFromStore(myFoodInputs, foodInFlowRates);
		float foodPushed = pushResourceToStore(myFoodOutputs, foodOutFlowRates, foodGathered);
		
		float O2Gathered = getMaxResourceFromStore(myO2Inputs, O2InFlowRates);
		float O2Pushed = pushResourceToStore(myO2Outputs, O2OutFlowRates, O2Gathered);
		
		float CO2Gathered = getMaxResourceFromStore(myCO2Inputs, CO2InFlowRates);
		float CO2Pushed = pushResourceToStore(myCO2Outputs, CO2OutFlowRates, CO2Gathered);
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
		powerInFlowRates[index] = watts;
	}

	public float getPowerInputFlowrate(int index){
		return powerInFlowRates[index];
	}

	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerInputs = sources;
		powerInFlowRates = flowRates;
	}

	public PowerStore[] getPowerInputs(){
		return myPowerInputs;
	}

	public void setPowerOutputFlowrate(float watts, int index){
		powerOutFlowRates[index] = watts;
	}

	public float getPowerOutputFlowrate(int index){
		return powerOutFlowRates[index];
	}

	public void setPowerOutputs(PowerStore[] destinationss, float[] flowRates){
		myPowerOutputs = destinationss;
		powerOutFlowRates = flowRates;
	}

	public PowerStore[] getPowerOutputs(){
		return myPowerOutputs;
	}

	public void setGreyWaterInputFlowrate(float watts, int index){
		greyWaterInFlowRates[index] = watts;
	}

	public float getGreyWaterInputFlowrate(int index){
		return greyWaterInFlowRates[index];
	}

	public void setGreyWaterInputs(GreyWaterStore[] sources, float[] flowRates){
		myGreyWaterInputs = sources;
		greyWaterInFlowRates = flowRates;
	}

	public GreyWaterStore[] getGreyWaterInputs(){
		return myGreyWaterInputs;
	}

	public void setGreyWaterOutputFlowrate(float watts, int index){
		greyWaterOutFlowRates[index] = watts;
	}

	public float getGreyWaterOutputFlowrate(int index){
		return greyWaterOutFlowRates[index];
	}

	public void setGreyWaterOutputs(GreyWaterStore[] destinationss, float[] flowRates){
		myGreyWaterOutputs = destinationss;
		greyWaterOutFlowRates = flowRates;
	}

	public GreyWaterStore[] getGreyWaterOutputs(){
		return myGreyWaterOutputs;
	}

	public void setDirtyWaterInputFlowrate(float watts, int index){
		dirtyWaterInFlowRates[index] = watts;
	}

	public float getDirtyWaterInputFlowrate(int index){
		return dirtyWaterInFlowRates[index];
	}

	public void setDirtyWaterInputs(DirtyWaterStore[] sources, float[] flowRates){
		myDirtyWaterInputs = sources;
		dirtyWaterInFlowRates = flowRates;
	}

	public DirtyWaterStore[] getDirtyWaterInputs(){
		return myDirtyWaterInputs;
	}

	public void setDirtyWaterOutputFlowrate(float watts, int index){
		dirtyWaterOutFlowRates[index] = watts;
	}

	public float getDirtyWaterOutputFlowrate(int index){
		return dirtyWaterOutFlowRates[index];
	}

	public void setDirtyWaterOutputs(DirtyWaterStore[] destinationss, float[] flowRates){
		myDirtyWaterOutputs = destinationss;
		dirtyWaterOutFlowRates = flowRates;
	}

	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterOutputs;
	}

	public void setPotableWaterInputFlowrate(float watts, int index){
		potableWaterInFlowRates[index] = watts;
	}

	public float getPotableWaterInputFlowrate(int index){
		return potableWaterInFlowRates[index];
	}

	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] flowRates){
		myPotableWaterInputs = sources;
		potableWaterInFlowRates = flowRates;
	}

	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterInputs;
	}

	public void setPotableWaterOutputFlowrate(float watts, int index){
		potableWaterOutFlowRates[index] = watts;
	}

	public float getPotableWaterOutputFlowrate(int index){
		return potableWaterOutFlowRates[index];
	}

	public void setPotableWaterOutputs(PotableWaterStore[] destinationss, float[] flowRates){
		myPotableWaterOutputs = destinationss;
		potableWaterOutFlowRates = flowRates;
	}

	public PotableWaterStore[] getPotableWaterOutputs(){
		return myPotableWaterOutputs;
	}

	public void setFoodInputFlowrate(float watts, int index){
		foodInFlowRates[index] = watts;
	}

	public float getFoodInputFlowrate(int index){
		return foodInFlowRates[index];
	}

	public void setFoodInputs(FoodStore[] sources, float[] flowRates){
		myFoodInputs = sources;
		foodInFlowRates = flowRates;
	}

	public FoodStore[] getFoodInputs(){
		return myFoodInputs;
	}

	public void setFoodOutputFlowrate(float watts, int index){
		foodOutFlowRates[index] = watts;
	}

	public float getFoodOutputFlowrate(int index){
		return foodOutFlowRates[index];
	}

	public void setFoodOutputs(FoodStore[] destinationss, float[] flowRates){
		myFoodOutputs = destinationss;
		foodOutFlowRates = flowRates;
	}

	public FoodStore[] getFoodOutputs(){
		return myFoodOutputs;
	}

	public void setBiomassInputFlowrate(float watts, int index){
		biomassInFlowRates[index] = watts;
	}

	public float getBiomassInputFlowrate(int index){
		return biomassInFlowRates[index];
	}

	public void setBiomassInputs(BiomassStore[] sources, float[] flowRates){
		myBiomassInputs = sources;
		biomassInFlowRates = flowRates;
	}

	public BiomassStore[] getBiomassInputs(){
		return myBiomassInputs;
	}

	public void setBiomassOutputFlowrate(float watts, int index){
		biomassOutFlowRates[index] = watts;
	}

	public float getBiomassOutputFlowrate(int index){
		return biomassOutFlowRates[index];
	}

	public void setBiomassOutputs(BiomassStore[] destinationss, float[] flowRates){
		myBiomassOutputs = destinationss;
		biomassOutFlowRates = flowRates;
	}

	public BiomassStore[] getBiomassOutputs(){
		return myBiomassOutputs;
	}

	public void setAirInputFlowrate(float watts, int index){
		airInFlowRates[index] = watts;
	}

	public float getAirInputFlowrate(int index){
		return airInFlowRates[index];
	}

	public void setAirInputs(SimEnvironment[] sources, float[] flowRates){
		myAirInputs = sources;
		airInFlowRates = flowRates;
	}

	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}

	public void setAirOutputFlowrate(float watts, int index){
		airOutFlowRates[index] = watts;
	}

	public float getAirOutputFlowrate(int index){
		return airOutFlowRates[index];
	}

	public void setAirOutputs(SimEnvironment[] destinationss, float[] flowRates){
		myAirOutputs = destinationss;
		airOutFlowRates = flowRates;
	}

	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}

	public void setO2InputFlowrate(float watts, int index){
		O2InFlowRates[index] = watts;
	}

	public float getO2InputFlowrate(int index){
		return O2InFlowRates[index];
	}

	public void setO2Inputs(O2Store[] sources, float[] flowRates){
		myO2Inputs = sources;
		O2InFlowRates = flowRates;
	}

	public O2Store[] getO2Inputs(){
		return myO2Inputs;
	}

	public void setO2OutputFlowrate(float watts, int index){
		O2OutFlowRates[index] = watts;
	}

	public float getO2OutputFlowrate(int index){
		return O2OutFlowRates[index];
	}

	public void setO2Outputs(O2Store[] destinationss, float[] flowRates){
		myO2Outputs = destinationss;
		O2OutFlowRates = flowRates;
	}

	public O2Store[] getO2Outputs(){
		return myO2Outputs;
	}

	public void setCO2InputFlowrate(float watts, int index){
		CO2InFlowRates[index] = watts;
	}

	public float getCO2InputFlowrate(int index){
		return CO2InFlowRates[index];
	}

	public void setCO2Inputs(CO2Store[] sources, float[] flowRates){
		myCO2Inputs = sources;
		CO2InFlowRates = flowRates;
	}

	public CO2Store[] getCO2Inputs(){
		return myCO2Inputs;
	}

	public void setCO2OutputFlowrate(float watts, int index){
		CO2OutFlowRates[index] = watts;
	}

	public float getCO2OutputFlowrate(int index){
		return CO2OutFlowRates[index];
	}

	public void setCO2Outputs(CO2Store[] destinationss, float[] flowRates){
		myCO2Outputs = destinationss;
		CO2OutFlowRates = flowRates;
	}

	public CO2Store[] getCO2Outputs(){
		return myCO2Outputs;
	}
	
	/**
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "Accumulator"+getID();
	}
}
